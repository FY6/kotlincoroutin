package com.qihe.kotlinncoroutines;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Service implements Runnable {

    private final ServerSocket mServerSocket;
    private boolean isRun = true;

    public Service(int port) throws IOException {
        mServerSocket = new ServerSocket(port);
    }

    public void run() {
        while (isRun) {
            try {
                Log.e("tag", "服务启动");
                Socket socket = mServerSocket.accept();
                new Thread(new HandleClient(socket)).start();
            } catch (IOException e) {
                e.printStackTrace();
                isRun = false;
                try {
                    if (mServerSocket != null && !mServerSocket.isClosed()) {
                        mServerSocket.close();
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    class HandleClient implements Runnable {
        private Socket socket;

        public HandleClient(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                InputStream in = socket.getInputStream();
                byte[] bytes = new byte[in.available()];
                in.read(bytes);
                Log.e("tag", "收到：" + new String(bytes));
                OutputStream out = socket.getOutputStream();
                byte[] buffer = new byte[1024];
                int n;
                while ((n = in.read(buffer)) > 0) {
                    out.write(buffer, 0, n);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
