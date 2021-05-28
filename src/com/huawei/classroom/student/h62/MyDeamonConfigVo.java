package com.huawei.classroom.student.h62;

/**
 * @author super
 */
public class MyDeamonConfigVo {
    private String root;
    private int port;
    private String passwordFile;

    public MyDeamonConfigVo() {
        super();
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getPort() {
        return port;
    }

    public void setPasswordFile(String passwordFile) {
        this.passwordFile = passwordFile;
    }
}
