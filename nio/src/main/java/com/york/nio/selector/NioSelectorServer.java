package com.york.nio.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description: Nio Selector 服务端
 * @Author: York.Hwang
 * @Time: 2020/3/15 22:15
 */
public class NioSelectorServer {
    private static int SERVER_PORT = 6666;
    private final static AtomicInteger CLIENT_COUNTER = new AtomicInteger();

    public static void main(String[] args) throws IOException {

        //新建 ServerSocketChannel
        ServerSocketChannel serviceSocketChannel = ServerSocketChannel.open();
        //绑定端口
        serviceSocketChannel.bind(new InetSocketAddress(SERVER_PORT));
        //设置为非阻塞
        serviceSocketChannel.configureBlocking(false);

        //实例化一个 Selector
        Selector selector = Selector.open();
        //ServerSocketChannel 接受连接实践注册到 Selector
        serviceSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            //监听无连接，继续监听
            if (selector.select(2000) == 0) {
                System.out.println("=====等待2秒，暂无连接上来======");
                continue;
            }

            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();

                if (key.isAcceptable()) {
                    System.out.println("=====第"+CLIENT_COUNTER.incrementAndGet()+"个连接上来======"+key.hashCode());
                    SocketChannel socketChannel = serviceSocketChannel.accept();
                    //设置异步
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(32));
                }

                if (key.isReadable()) {
                    SocketChannel channel = (SocketChannel)key.channel();
                    ByteBuffer byteBuffer = (ByteBuffer)key.attachment();
                    channel.read(byteBuffer);
                    System.out.println(key.isValid()+"客户端"+key.hashCode()+"消息:"+new String(byteBuffer.array()));
                }

                iterator.remove();
            }
        }

    }
}
