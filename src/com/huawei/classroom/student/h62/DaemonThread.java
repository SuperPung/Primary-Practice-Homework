package com.huawei.classroom.student.h62;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * server
 * @author super
 */
public class DaemonThread extends Thread {
    private final Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private final String root;
    private final List<String> users;

    public DaemonThread(Socket socket, MyDeamonConfigVo config) {
        this.socket = socket;
        root = config.getRoot();
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
                readLine(line);
                if (1 == 0) {
                    break;
                }
            }
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readLine(String line) throws IOException {
        if (line.startsWith("login")) {
            checkLogin(line.substring(5));
        } else if (line.startsWith("getAscDir")) {
            listFiles(line.substring(9));
        } else if (line.startsWith("type")) {
            getFileType(line.substring(4));
        } else if (line.startsWith("write")) {
            writeFile(line.substring(5));
        } else if (line.startsWith("delete")) {
            delete(line.substring(6));
        } else if (line.startsWith("length")) {
            getLength(line.substring(6));
        } else if (line.startsWith("exist")) {
            isExist(line.substring(5));
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
            String path = filepath + resortFile.getName();
            if (!resortFile.isFile()) {
                path += "/";
            }
            writeLine(path);
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
        /**
         * it will be changed
         */
        int dirCount = dirs.size();
        int fileCount = files.size();

        for (int i = 0; i < dirCount; i++) {
            File dir = dirs.get(0);
            for (File file: dirs) {
                if (dir.getAbsolutePath().compareTo(file.getAbsolutePath()) > 0) {
                    dir = file;
                }
            }
            result.add(dir);
            dirs.remove(dir);
        }
        for (int i = 0; i < fileCount; i++) {
            File file = files.get(0);
            for (File file1: files) {
                if (file.getAbsolutePath().compareTo(file1.getAbsolutePath()) >= 0) {
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

    private void writeFile(String pathAndContent) throws IOException {
        String[] pathContent = pathAndContent.split(":");
        String path = pathContent[0];
        String content = pathContent[1];
        File file = new File(root + path);
        if (!file.exists()) {
            if (file.createNewFile()) {
                FileOutputStream outFileStream = new FileOutputStream(file);
                outFileStream.write(content.getBytes(StandardCharsets.UTF_8));
                outFileStream.flush();
            }
        }
    }

    private void delete(String filepath) {
        File file = new File(root + filepath);
        /**
         * write must be read
         */
        if (file.delete()) {
        }
    }

    private void getLength(String filepath) {
        File file = new File(root + filepath);
        if (file.exists()) {
            writeLine(String.valueOf(file.length()));
        } else {
            writeLine("0");
        }
    }

    private void isExist(String filepath) {
        File file = new File(root + filepath);
        if (file.exists()) {
            writeLine("exist");
        } else {
            writeLine("not exist");
        }
    }

    private void writeLine(String line) {
        out.write(line + "\r\n");
        out.flush();
    }
}
