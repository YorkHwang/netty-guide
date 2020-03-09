package com.york.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description:BIO服务端
 * @Author: York.Hwang
 * @Time: 2020/3/8 21:39
 *
 * 测试
 * 登录：
 * telnet 127.0.0.1 6666
 *
 * 逃逸字符
 * ctrl+]
 *
 * 选择输入
 * send ayt
 *
 * 开始输入字符，服务器可以不断受到打印信息
 */
public class BioServer {

    private static Executor threadPool = Executors.newCachedThreadPool();
    private static final int port = 6666;
    private static AtomicInteger clientCount = new AtomicInteger();

    public static void main(String[] args) throws IOException {
        final ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("服务器启动了！！！");

        while (true) {
            System.out.println("开始监听客户端连接！！！");
            final Socket accept = serverSocket.accept();
            System.out.println("第"+clientCount.incrementAndGet()+"位客户端连上了！！！");
            threadPool.execute(() -> {
                handler(accept);
            });
        }
    }



    /**
     *  @Description:处理请求
     *  @Author: York.Hwang
     *  @Date: 2020/3/8 22:09
     */
    private static void handler(Socket accept) {
        try {
            System.out.println("线程ID="+Thread.currentThread().getName()+"开始处理");
            byte[] bytes = new byte[1024];
            InputStream inputStream = accept.getInputStream();
            while (true) {
                int read = inputStream.read(bytes);
                if (read != -1) {
                    System.out.println("线程ID="+Thread.currentThread().getName()+"读取数据："+new String(bytes, 0, read));
                } else {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                accept.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
