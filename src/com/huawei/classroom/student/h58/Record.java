package com.huawei.classroom.student.h58;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author super
 */
public class Record implements Comparable<Record>{
    private Date date;
    private final String voterId;
    private final String candidate;
    private boolean valid;

    public Record(String date, String voterId, String candidate) {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            this.date = fmt.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.voterId = voterId;
        this.candidate = candidate;
        valid = true;
    }

    public Date getDate() {
        return date;
    }

    public String getVoterId() {
        return voterId;
    }

    public String getCandidate() {
        return candidate;
    }

    public boolean isValid() {
        return valid;
    }

    public void setInvalid() {
        valid = false;
    }

    @Override
    public int compareTo(Record o) {
        return date.compareTo(o.date);
    }
}
