package com.huawei.classroom.student.h52;

import java.util.HashSet;
import java.util.Set;

/**
 * @author super
 */
public class NumDecompose {
	/**
	 * 将num进行质因数分解，将分解到的质因数放到Set里面返回
	 */
	public Set<Integer> decompose(int num) {
		Set<Integer> result = new HashSet<>();
		if (num < 0) {
			return null;
		} else if (num == 1) {
			result.add(1);
		} else {
			int i = 2;
			while (i <= num) {
				if (num % i == 0) {
					result.add(i);
					num = num / i;
					i = 2;
				} else {
					i++;
				}
			}
		}
		return result;
	}
}
