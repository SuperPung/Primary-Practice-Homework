package com.huawei.classroom.student.h62;

import java.io.IOException;

/**
 * @author super
 */
public class MyRemoteFile {
    private final MyHost host;
    private final String path;

    public MyRemoteFile(MyHost host, String path) throws IOException {
        host.login();
        if (!host.isValid()) {
            throw new IOException("host login failed!");
        }
        this.host = host;
        this.path = path;
    }

    public MyRemoteFile[] dirByNameAsc() throws IOException {
        return host.getDirByNameAsc(path);
    }

    public boolean isDirectory() throws IOException {
        return host.getType(path) == 0;
    }

    public boolean isFile() throws IOException {
        return host.getType(path) == 1;
    }

    public String getPathFileName() {
        return path;
    }

    public void writeByBytes(byte[] bytes) {
        host.writeByBytes(path, bytes);
    }

    public int length() {
        return host.getLength(path);
    }

    public void delete() {
        host.delete(path);
    }

    public boolean exists() {
        return host.isExist(path);
    }
}
