/**
 * 
 */
package com.huawei.classroom.student.h54;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Administrator
 *
 */
public class PasswordChecker {
	/**
	 * 判断一个口令是否是一个复杂度合法的口令，复杂度合法的口令有如下要求：
	 * 1  长度>=8
	 * 2 最少包含一个数字
	 * 3 最少包含一个小写英文字母
	 * 4 虽少包含一个大写英文字母
	 * 5 最少包含一个特殊字符 特殊字符定义为   ~!@#$%^&*()_+
	 * 
	 *   
	 */
	private static final Set<Character> CHAR_SET = new HashSet<>(Arrays.asList
			('~', '!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '_', '+'));

	public boolean isValidPassword(String password){
		boolean isLengthNotLess8, isContainDigital, isContainLowerLetter, isContainUpperLetter, isContainSpecialChar;
		int digitalCount = 0, lowerLetterCount = 0, upperLetterCount = 0, specialCharCount = 0;
		isLengthNotLess8 = password.length() >= 8;
		for (int i = 0; i < password.length(); i++) {
			char cha = password.charAt(i);
			if (cha <= '9' && cha >= '0') {
				digitalCount++;
			} else if (cha <= 'z' && cha >= 'a') {
				lowerLetterCount++;
			} else if (cha <= 'Z' && cha >= 'A') {
				upperLetterCount++;
			} else if (CHAR_SET.contains(cha)) {
				specialCharCount++;
			}
		}
		isContainDigital = digitalCount >= 1;
		isContainLowerLetter = lowerLetterCount >= 1;
		isContainUpperLetter = upperLetterCount >= 1;
		isContainSpecialChar = specialCharCount >= 1;

		return isLengthNotLess8 && isContainDigital && isContainLowerLetter && isContainUpperLetter && isContainSpecialChar;
	}
}
