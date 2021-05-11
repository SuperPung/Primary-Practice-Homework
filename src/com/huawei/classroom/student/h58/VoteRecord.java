package com.huawei.classroom.student.h58;

import java.io.*;
import java.util.*;

/**
 * @author super
 */
public class VoteRecord {
	/**
	 * fileName是一个投票的明细记录，里面逐行存放了 投票的时间（yyyy-MM-dd HH:mm:ss 格式） +\t+投票的微信ID+\t+候选人
	 * 存放按时间递增（但是可能出现同一秒出现若干条记录的情况）
	 * 现在需要完成投票统计的过程，具体要求如下：
	 * 1个微信ID 1分钟内 最多投1票 多余的票数无效
	 * 1个微信ID 10分钟内 最多只能投5票 多余的票无效
	 * 其中微信ID不固定，候选人姓名不固定
	 * 测试的时候要求10万行记录处理时间不超过3秒 
	 * @param fileName
	 * @return 返回一个map，其中key是候选人名字，value的票数
	 */
	public Map<String,Integer> calcRecording(String fileName){
		List<Record> records = readLines(fileName);
		Set<String> voterIds = new HashSet<>();
		Set<Voter> voters = new HashSet<>();
		Map<String, Voter> voterMap = new HashMap<>();
		Map<String, Integer> candidates = new HashMap<>();

		for (Record record : records) {
			voterIds.add(record.getVoterId());
		}

		for (String voterId : voterIds) {
			Voter voter = new Voter(voterId);
			voters.add(voter);
			voterMap.put(voterId, voter);
		}

		for (Record record : records) {
			Voter voter = voterMap.get(record.getVoterId());
			voter.addRecord(record);
		}

		for (Voter voter : voters) {
			voter.removeInvalidRecords();
			Map<String, Integer> oneCandidates = voter.getCandidates();
//			System.out.println(voter.getRecords().size());
			Set<Map.Entry<String, Integer>> oneCandidatesNameCount = oneCandidates.entrySet();
			for (Map.Entry<String, Integer> entry : oneCandidatesNameCount) {
				String candidate = entry.getKey();
				int count = entry.getValue();
				if (candidates.containsKey(candidate)) {
					count += candidates.get(candidate);
				}
				candidates.put(candidate, count);
			}
		}

		return candidates;
	}

	public List<Record> readLines(String filename) {
		String line = "";
		Reader reader = null;
		List<Record> result = new ArrayList<>();
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
				String[] recordItems = line.split("\t");
				String date = recordItems[0];
				String voterId = recordItems[1];
				String candidate = recordItems[2];
				Record record = new Record(date, voterId, candidate);
				result.add(record);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

}
