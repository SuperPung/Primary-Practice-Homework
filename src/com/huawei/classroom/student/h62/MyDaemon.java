package com.huawei.classroom.student.h62;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author super
 */
public class MyDaemon extends Thread {
    private final MyDeamonConfigVo config;

    public MyDaemon(MyDeamonConfigVo config) {
       this.config = config;
    }

    @Override
    public void run() {
        while (true) {
            Socket socket = null;
            try {
                ServerSocket server = new ServerSocket(config.getPort());
                socket = server.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
            DaemonThread daemonThread = new DaemonThread(socket, config);
            daemonThread.start();
        }
    }
}
