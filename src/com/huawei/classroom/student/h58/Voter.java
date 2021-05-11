package com.huawei.classroom.student.h58;

import java.io.Serializable;
import java.util.*;

/**
 * @author super
 */
public class Voter implements Serializable {
    private final String id;
    private final List<Record> records = new ArrayList<>();
    private static final int MAX_RECORDS_IN_TEN_MINUTES = 5;

    public Voter(String id) {
        this.id = id;
    }

    public void addRecord(Record record) {
        records.add(record);
    }

    public String getId() {
        return id;
    }

    public List<Record> getRecords() {
        return records;
    }

    /*public void removeInvalidRecords() {
        records.sort(Record::compareTo);
        Date curDate = records.get(0).getDate();
        Date oneMinLater = new Date(curDate.getTime() + 60000);
        Date tenMinLater = new Date(curDate.getTime() + 600000);
        int recordsIn10 = 1;
        for (int i = 1; i < records.size(); i++) {
            Date nextDate = records.get(i).getDate();
            if (nextDate.compareTo(oneMinLater) < 0) {
                records.get(i).setInvalid();
            } else if (nextDate.compareTo(tenMinLater) < 0 && recordsIn10 < 5) {
                oneMinLater = new Date(nextDate.getTime() + 60000);
                recordsIn10++;
            } else if (nextDate.compareTo(tenMinLater) < 0 && recordsIn10 >= 5){
                records.get(i).setInvalid();
            } else {
                tenMinLater = new Date(nextDate.getTime() + 600000);
                recordsIn10 = 1;
            }
        }
    }*/

    public void removeInvalidRecords() {
        // 按时间排序
        records.sort(Record::compareTo);
        // 容量为5的buffer，滑动窗口
        List<Record> buffer = new LinkedList<>();
        int next = 0;
        for (int i = 0; i < Math.min(MAX_RECORDS_IN_TEN_MINUTES, records.size()); i++) {
            buffer.add(records.get(next++));
        }
        // 每条记录判断
        do {
            int i, j;
            Date curDate = buffer.get(0).getDate();
            Date oneMinLater = new Date(curDate.getTime() + 60000);
            Date tenMinLater = new Date(curDate.getTime() + 600000);
            boolean isBufFinish = false;
            while (!isBufFinish) {
                // 去除不满足1分钟条件的记录
                for (i = 1; i < buffer.size(); i++) {
                    Date nextDate = buffer.get(i).getDate();
                    if (nextDate.compareTo(oneMinLater) < 0) {
                        buffer.get(i).setInvalid();
                        buffer.remove(i--);
                        if (next < records.size()) {
                            buffer.add(records.get(next++));
                        }
                    } else {
                        oneMinLater = new Date(nextDate.getTime() + 60000);
                    }
                }
                // 判断buffer内是否超过10分钟，不设无效，只移动
                for (i = 1; i < buffer.size(); i++) {
                    Date nextDate = buffer.get(i).getDate();
                    if (nextDate.compareTo(tenMinLater) >= 0) {
                        tenMinLater = new Date(nextDate.getTime() + 600000);
                        for (j = 0; j < i; j++) {
                            buffer.remove(j);
                            buffer.add(records.get(next++));
                        }
                        isBufFinish = false;
                        break;
                    } else {
                        isBufFinish = true;
                    }
                }

            }
            // 判断buffer后的下一条记录是否超过10分钟
            while (next < records.size()) {
                Record nextRecord = records.get(next);
                if (nextRecord.getDate().compareTo(tenMinLater) < 0) {
                    nextRecord.setInvalid();
                    next++;
                } else {
                    buffer.remove(0);
                    buffer.add(nextRecord);
                    next++;
                    break;
                }
            }
        } while (next < records.size());
    }

    public Map<String, Integer> getCandidates() {
        Map<String, Integer> candidates = new HashMap<>();
        for (Record record : records) {
            if (!record.isValid()) {
                continue;
            }
            String candidate = record.getCandidate();
            int count = candidates.getOrDefault(candidate, 0);
            candidates.put(candidate, count + 1);
        }
        return candidates;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Voter)) {
            return false;
        }
        return ((Voter) o).id.equals(id);
    }
}
