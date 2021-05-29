package com.huawei.classroom.student.h62;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * server
 * @author super
 */
public class DaemonThread extends Thread {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private String root;
    private int port;
    private List<String> users;

    public DaemonThread(Socket socket, MyDeamonConfigVo config) {
        this.socket = socket;
        root = config.getRoot();
        port = config.getPort();
        users = config.getUsers();
        in = null;
        out = null;
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
            while (true) {
                String line = in.readLine();
                if (line == null) {
                    break;
                }
                readLine(line);
            }
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readLine(String line) {
        if (line.startsWith("login")) {
            checkLogin(line.substring(5));
        } else if (line.startsWith("getAscDir")) {
            listFiles(line.substring(9));
        } else if (line.startsWith("type")) {
            getFileType(line.substring(4));
        }
    }

    private void checkLogin(String line) {
        String result = "failed";
        for (String user: users) {
            if (line.equals(user)) {
                result = "success";
                break;
            }
        }
        writeLine(result);
    }

    private void listFiles(String filepath) {
        List<File> files = getFiles(filepath);
        List<File> resortFiles = sortFiles(files);
        writeLine(String.valueOf(resortFiles.size()));
        for (File resortFile : resortFiles) {
            writeLine(resortFile.getAbsolutePath());
        }
    }

    private List<File> getFiles(String filepath) {
        List<File> result = new ArrayList<>();
        File file = new File(root + filepath);
        if (!file.isFile()) {
            File[] files = file.listFiles();
            if (files != null) {
                result.addAll(Arrays.asList(files));
            }
        } else {
            result.add(file);
        }
        return result;
    }

    private List<File> sortFiles(List<File> fileList) {
        List<File> result = new ArrayList<>();
        List<File> files = new ArrayList<>();
        List<File> dirs = new ArrayList<>();

        for (File file: fileList) {
            if (file.isFile()) {
                files.add(file);
            } else {
                dirs.add(file);
            }
        }
        for (int i = 0; i < dirs.size(); i++) {
            File dir = dirs.get(0);
            for (File file: dirs) {
                if (dir.getAbsolutePath().compareTo(file.getAbsolutePath()) > 0) {
                    dir = file;
                }
            }
            result.add(dir);
            dirs.remove(dir);
        }
        for (int i = 0; i < files.size(); i++) {
            File file = files.get(0);
            for (File file1: dirs) {
                if (file.getAbsolutePath().compareTo(file1.getAbsolutePath()) > 0) {
                    file = file1;
                }
            }
            result.add(file);
            files.remove(file);
        }
        return result;
    }

    private void getFileType(String filepath) {
        File file = new File(root + filepath);
        if (file.isFile()) {
            writeLine("file");
        } else {
            writeLine("dir");
        }
    }

    private void writeLine(String line) {
        out.write(line + "\r\n");
        out.flush();
    }
}
