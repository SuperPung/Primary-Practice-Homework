package com.huawei.classroom.student.h62;

import java.util.List;

/**
 * @author super
 */
public class MyDaemon extends Thread {
    private String root;
    private int port;
    private List<String> users;

    public MyDaemon(MyDeamonConfigVo config) {
        root = config.getRoot();
        port = config.getPort();
        users = config.getUsers();
    }

    @Override
    public void run() {

    }
}
