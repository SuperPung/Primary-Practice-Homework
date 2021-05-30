package com.huawei.classroom.student.h62;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author super
 */
public class MyDaemon extends Thread {
    private final MyDeamonConfigVo config;
    private ServerSocket server;

    public MyDaemon(MyDeamonConfigVo config) throws IOException {
       this.config = config;
       /**
        * server should be initialized here
        */
       server = new ServerSocket(config.getPort());
    }

    @Override
    public void run() {
        while (true) {
            Socket socket = null;
            try {
                socket = server.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
            DaemonThread daemonThread = new DaemonThread(socket, config);
            daemonThread.start();
        }
    }
}
