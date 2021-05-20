package com.huawei.classroom.student.h60;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;
import java.util.Set;

/**
 * @author super
 */

public class ServerThread extends Thread {
    private final Socket socket;
    private final Map<String, String> passwd;

    public ServerThread(Socket socket, Map<String, String> passwd) {
        super();
        this.socket = socket;
        this.passwd = passwd;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            String line = in.readLine();
            String passwdStr = line;
//            System.out.println("server received username and password: " + passwdStr);
            String isLoggedIn = pass(passwdStr);
            out.write(isLoggedIn + "\r\n");
            out.flush();
            if ("0".equals(isLoggedIn)) {
                return;
            }
            while (true) {
                line = in.readLine();
                // \r\n is necessary
                for (Socket client : ChatServer.clients) {
                    PrintWriter clientOut = new PrintWriter(client.getOutputStream());
                    clientOut.write(line + "\r\n");
                    clientOut.flush();
                }
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

    /**
     * @param passwdStr username + \t + password
     * @return "1" or "0"
     */
    private String pass(String passwdStr) {
        String[] userPasswd = passwdStr.split("\t");
        if (userPasswd.length != 2) {
            return "0";
        }
        String username = userPasswd[0];
        String password = userPasswd[1];
        Set<Map.Entry<String, String>> passwdEntry = passwd.entrySet();
        for (Map.Entry<String, String> entry : passwdEntry) {
            if (username.equals(entry.getKey())) {
                if (password.equals(entry.getValue())) {
                    return "1";
                } else {
                    return "0";
                }
            }
        }
        return "0";
    }
}
