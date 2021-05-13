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
		List<Reaction> reactions = readLines(reactionFile);
		Set<String> result = new HashSet<>(initComponents);
		int newAddCount = initComponents.size();
		while (newAddCount != 0) {
			newAddCount = 0;
			for (Reaction reaction : reactions) {
				// contain or not contain, that is a question
				if (result.containsAll(reaction.getReactant()) && !result.containsAll(reaction.getProduct())) {
					result.addAll(reaction.getProduct());
					newAddCount++;
				} else if (result.containsAll(reaction.getProduct()) && !result.containsAll(reaction.getReactant())) {
					result.addAll(reaction.getReactant());
					newAddCount++;
				}
			}
		}
		return result;
	}

	public List<Reaction> readLines(String filename) {
		String line;
		Reader reader = null;
		List<Reaction> result = new ArrayList<>();
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
				result.add(new Reaction(leftSet, rightSet));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
}

