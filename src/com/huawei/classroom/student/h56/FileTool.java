/**
 * 
 */
package com.huawei.classroom.student.h56;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @author super
 *
 */
public class FileTool {
	private static final String EXTNAME = "txt";
	private String orgStr = "";
	private String targetStr = "";
	 
	/**
	 * 将homeDir 目录下（包括子目录）所有的文本文件（扩展名为.txt，扩展名不是.txt的文件不要动，扩展名区分大小写) 文件中，orgStr替换为 targetStr
	 * 所有文本文件均为UTF-8编码
	 * 例如将某个目录中所有文本文件中的 南开大学 替换为 天津大学
	 * @param homeDir
	 * @param orgStr
	 * @param targetStr
	 */
	public void replaceTxtFileContent(String homeDir,String orgStr,String targetStr) {
		this.orgStr = orgStr;
		this.targetStr = targetStr;
		File home = new File(homeDir);
		readFiles(home);
	}

	private void readFiles(File dir) {
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
				String filename = file.getName();
				if (isTxtFile(filename)) {
					replaceFileContent(file);
				}
			} else {
				readFiles(file);
			}
		}
	}

	private void replaceFileContent(File file) {
		String content = readFromTxt(file);
		if (content.length() == 0) {
			return;
		}
		String newContent = content.replace(orgStr, targetStr);
		try {
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(newContent);
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean isTxtFile(String filename) {
		String[] filenames = filename.split("\\.");
		String ext = filenames[filenames.length - 1];
		return EXTNAME.equalsIgnoreCase(ext);
	}

	private String readFromTxt(File file) {
		Reader reader = null;
		StringBuffer buf = new StringBuffer();
		try {
			char[] chars = new char[1024];
			// InputStream in=new FileInputStream(filename);
			reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
			int readed = reader.read(chars);
			while (readed != -1) {
				buf.append(chars, 0, readed);
				readed = reader.read(chars);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			close(reader);
		}
		return buf.toString();
	}

	private void close(Closeable inout) {
		if (inout != null) {
			try {
				inout.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
