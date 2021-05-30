package com.huawei.classroom.student.h62;

import java.io.IOException;

/**
 * @author super
 */
public class MyRemoteFile {
    private final MyHost host;
    private final String path;

    public MyRemoteFile(MyHost host, String path) throws IOException {
        /**
         * 这里不能直接login，因为不只调用这个类一次，不能每次都login
         */
        if (host.isInvalid()) {
            host.login();
            if (host.isInvalid()) {
                throw new IOException("host login failed!");
            }
        }
        this.host = host;
        this.path = path;
    }

    public MyRemoteFile[] dirByNameAsc() throws IOException, InterruptedException {
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

    public int length() throws IOException {
        return host.getLength(path);
    }

    public void delete() {
        host.delete(path);
    }

    public boolean exists() throws IOException {
        return host.isExist(path);
    }
}
