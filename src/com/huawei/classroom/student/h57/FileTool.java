package com.huawei.classroom.student.h57;

import java.io.File;

/**
 * @author super
 */
public class FileTool {
	private long sum = 0;

	/*
	 * 统计一个目录下所有文件大小的加和
	 */
	public long recursiveCalcFileSize(String homeDir) {
		File home = new File(homeDir);
		calcFiles(home);
		return sum;
	}

	private void calcFiles(File dir) {
		if (!dir.exists() || !dir.isDirectory()) {
			return;
		}
		String[] files = dir.list();
		if (files == null) {
			return;
		}
		for (String s : files) {
			File file = new File(dir, s);
			if (file.isFile()) {
				sum += file.length();
			} else {
				calcFiles(file);
			}
		}
	}
}
