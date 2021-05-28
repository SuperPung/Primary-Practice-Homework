package com.huawei.classroom.student.h62;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author super
 */
public class MyDeamonConfigVo {
    private String root;
    private int port;
    private List<String> users = new ArrayList<>();

    public MyDeamonConfigVo() {
        super();
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public String getRoot() {
        return root;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getPort() {
        return port;
    }

    public void setPasswordFile(String passwordFile) throws IOException {
        this.users = readLines(passwordFile);
    }

    public List<String> getUsers() {
        return users;
    }

    private List<String> readLines(String filePath) throws IOException {
        List<String> result = new ArrayList<>();
        String line;
        Reader reader = new FileReader(filePath);
        LineNumberReader lineReader = new LineNumberReader(reader);

        while (true) {
            line = lineReader.readLine();
            if (line == null) {
                break;
            }
            if (line.trim().length() == 0 || line.startsWith("#")) {
                continue;
            }
            result.add(line);
        }
        return result;
    }
}
