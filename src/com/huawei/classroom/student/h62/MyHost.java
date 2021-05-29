package com.huawei.classroom.student.h62;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * client
 * @author super
 */
public class MyHost {
    private String ip;
    private int port;
    private String username;
    private String password;
    private boolean valid;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public MyHost() {
        super();
        socket = null;
        in = null;
        out = null;
        valid = false;
    }

    public boolean isValid() {
        return valid;
    }

    private void writeLine(String line) {
        out.write(line + "\r\n");
        out.flush();
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void login() throws IOException {
        socket = new Socket(ip, port);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream());
        writeLine("login" + username + "\t" + password);
        valid = "success\r\n".equals(in.readLine());
    }

    public MyRemoteFile[] getDirByNameAsc(String path) throws IOException {
        writeLine("getAscDir" + path);
        int count = Integer.parseInt(in.readLine());
        MyRemoteFile[] result = new MyRemoteFile[count];
        for (int i = 0; i < count; i++) {
            result[i] = new MyRemoteFile(this, in.readLine());
        }
        return result;
    }

    public int getType(String path) throws IOException {
        writeLine("type" + path);
        String type = in.readLine();
        if ("file".equals(type)) {
            return 1;
        } else if ("dir".equals(type)) {
            return 0;
        } else {
            return -1;
        }
    }

    public void writeByBytes(String path, byte[] bytes) {
    }

    public int getLength(String path) {
    }

    public void delete(String path) {
    }

    public boolean isExist(String path) {
    }
}
