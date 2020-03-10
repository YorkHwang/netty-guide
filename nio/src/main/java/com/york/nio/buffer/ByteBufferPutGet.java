package com.york.nio.buffer;

import java.nio.ByteBuffer;

/**
 * @Description: ByteBuffer Put和Get
 * 1、ByteBuffer的Put和Get 顺序类型要一致,否则容易抛出异常：java.nio.BufferUnderflowException
 * @Author: York.Hwang
 * @Time: 2020/3/10 09:41
 */
public class ByteBufferPutGet {

    public static void main(String[] args) {

        ByteBuffer byteBuffer = ByteBuffer.allocate(512);

        byteBuffer.putChar('我');
        byteBuffer.putInt(100);
        byteBuffer.putLong(1000L);

        //转换成写
        byteBuffer.flip();

        System.out.println(byteBuffer.getChar());
        System.out.println(byteBuffer.getInt());
        System.out.println(byteBuffer.getLong());
    }
}
