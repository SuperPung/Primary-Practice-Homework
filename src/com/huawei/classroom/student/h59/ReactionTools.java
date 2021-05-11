package com.huawei.classroom.student.h59;

import java.io.*;
import java.util.*;

/**
 * @author super
 */
public class ReactionTools {

	/**
	 * 根据reactionFile给出的一系列反应， 判断一个体系中根据init物质，判断出最后可能都存在什么物质
	 * @param reactionFile 体系中初始反应物
	 * @param initComponents 体系中初始反应物
	 * @return 最后体系中存在的全部物质
	 */
	public Set<String> findAllComponents(String reactionFile,Set<String> initComponents){
		Map<Set<String>, Set<String>> reactions = readLines(reactionFile);
		Set<String> result = new HashSet<>(initComponents);
		Set<Map.Entry<Set<String>, Set<String>>> reactionsEntrySet = reactions.entrySet();
		int newAddCount = initComponents.size();
		while (newAddCount != 0) {
			newAddCount = 0;
			for (Map.Entry<Set<String>, Set<String>> entry : reactionsEntrySet) {
				// contain or not contain, that is a question
				if (result.containsAll(entry.getKey()) && !result.containsAll(entry.getValue())) {
					result.addAll(entry.getValue());
					newAddCount++;
				} else if (result.containsAll(entry.getValue()) && !result.containsAll(entry.getKey())) {
					result.addAll(entry.getKey());
					newAddCount++;
				}
			}
		}
		return result;
	}

	public Map<Set<String>, Set<String>> readLines(String filename) {
		String line;
		Reader reader = null;
		Map<Set<String>, Set<String>> result = new HashMap<>();
		try {
			reader = new FileReader(filename);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		if (reader == null) {
			return null;
		}
		LineNumberReader lineReader = new LineNumberReader(reader);

		try {
			while (true) {
				line = lineReader.readLine();
				if (line == null) {
					break;
				}
				if (line.trim().length() == 0 || line.startsWith("#")) {
					continue;
				}
				String[] reaction = line.split("=");
				String left = reaction[0];
				String right = reaction[1];
				String[] lefts = left.split("\\ \\+\\ ");// regular
				Set<String> leftSet = new HashSet<>();
				for (String s : lefts) {
					leftSet.add(s.trim());
				}
				String[] rights = right.split("\\ \\+\\ ");
				Set<String> rightSet = new HashSet<>();
				for (String s : rights) {
					rightSet.add(s.trim());
				}
				result.put(leftSet, rightSet);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
}

