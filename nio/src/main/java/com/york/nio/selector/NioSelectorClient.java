package com.york.nio.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @Description: Nio Selector 客户端
 * @Author: York.Hwang
 * @Time: 2020/3/15 22:44
 */
public class NioSelectorClient {

    private final static String IP = "127.0.0.1";
    private final static int PORT = 6666;
    private final static String MSG = "Hello,我是York客户端端";


    public static void main(String[] args) throws IOException {

        //创建客户端的SocketChannel
        SocketChannel socketChannel = SocketChannel.open();
        //设置非阻塞
        socketChannel.configureBlocking(false);
        //创建地址
        InetSocketAddress inetSocketAddress = new InetSocketAddress(IP, PORT);

        //开始异步连接
        if(!socketChannel.connect(inetSocketAddress)){
            //等待连上
            while (!socketChannel.finishConnect()){
                System.out.println("=====建立连接中===="+IP+":"+PORT);
            }
        }

        //写入数据
        socketChannel.write(ByteBuffer.wrap(MSG.getBytes()));

        //阻塞等待看效果
        System.in.read();

    }


}
