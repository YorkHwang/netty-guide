package com.york.nio.buffer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * @Description:
 * Scattering: 数据写入ByteBuffer时，可以写入ByteBuffer数组
 * Gathering: 从ByteBuffer读数据时，可以从ByteBuffer数组读取
 * @Author: York.Hwang
 * @Time: 2020/3/10 23:20
 */
public class ScaGatBuffer {

    public static void main(String[] args) throws IOException {
        final ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(7002);
        serverSocketChannel.socket().bind(inetSocketAddress);
        System.out.println("======服务端已经启动======监听端口="+inetSocketAddress.getPort());
        SocketChannel socketChannel = serverSocketChannel.accept();
        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0]=ByteBuffer.allocate(3);
        byteBuffers[1]=ByteBuffer.allocate(7);
        int maxLen = 10;

        while (true) {
            int byteRead = 0;
            while(byteRead < maxLen){
                //读取到ByteBuffer数组
                long len = socketChannel.read(byteBuffers);
                byteRead += len;
                System.out.println("byteRead"+byteRead);
                Arrays.asList(byteBuffers).stream().map(bf->"position="+bf.position()+", limit="+bf.limit())
                        .forEach(System.out::println);
            }
            //读写转换
            Arrays.asList(byteBuffers).forEach(bf->bf.flip());

            int byteWrite = 0;
            while (byteWrite<maxLen){
                //写到ByteBuffer数组
                long writeLen = socketChannel.write(byteBuffers);
                byteWrite += writeLen;
            }

            //读完后清除
            Arrays.asList(byteBuffers).forEach(bf->bf.clear());
            System.out.println("byteRead="+byteRead+" byteWrite="+byteWrite+" maxLen="+maxLen);
        }
    }

}
