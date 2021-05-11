package com.huawei.classroom.student.h55;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author super
 */
public class PoetryAnalysis {
	private String[] verses = new String[0];
	private Set<String> charSet = new HashSet<>();
	/**
	 * 
	 * @param pathFilename 包含诗歌内容的源文件
	 * @param chars 需要统计的字 以半角分号分割 
	 * 统计  
	 * 
	 */
	public void analysis(String pathFilename, String chars) {
		String content = readFromTxt(pathFilename);
		List<Map.Entry<String, Integer>> result = new ArrayList<>();
		charSet = new HashSet<>(Arrays.asList(chars.split(";")));
		verses = content.split("[，。]");
		result = getTopNWords(10);
		for (Map.Entry<String, Integer> res : result) {
			System.out.println(res.getKey() + "\t" + res.getValue());
		}
	}

	public List<Map.Entry<String, Integer>> getTopNWords(int n){
		int i, j;
		Map<String, Integer> map = new HashMap<>();
		List<Map.Entry<String, Integer>> mapList;
		List<Map.Entry<String, Integer>> ans = new ArrayList<>();
		for (i = 1; i < this.verses.length; i++){
			String content = this.verses[i];
			for (j = 0; j < content.length() - 1; j++) {
				String str = content.substring(j, j + 2);
				if (!charSet.contains(str.substring(0, 1)) && !charSet.contains(str.substring(1))) {
					continue;
				}
				int count;
				count = map.getOrDefault(str, 0);
				map.put(str, count + 1);
			}
		}

		mapList = new ArrayList<>(map.entrySet());
		mapList.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));

		for (i = 0; i < n; i++) {
			ans.add(mapList.get(i));
		}

		return ans;
	}

	private String readFromTxt(String filename) {
		Reader reader = null;
		StringBuffer buf = new StringBuffer();
		try {
			char[] chars = new char[1024];
			reader = new InputStreamReader(new FileInputStream(filename), StandardCharsets.UTF_8);
			int readed = reader.read(chars);
			while (readed != -1) {
				buf.append(chars, 0, readed);
				readed = reader.read(chars);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
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
