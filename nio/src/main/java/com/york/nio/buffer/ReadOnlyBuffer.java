package com.york.nio.buffer;

import java.nio.Buffer;
import java.nio.ByteBuffer;

/**
 * @Description: 只读Buffer: byteBuffer.asReadOnlyBuffer()得到只读Buffer,如果继续put将抛出异常： java.nio.ReadOnlyBufferException
 * @Author: York.Hwang
 * @Time: 2020/3/10 22:51
 */
public class ReadOnlyBuffer {
    private static int LIMIT = 32;

    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        for(int i=0; i<LIMIT; i++){
            byteBuffer.putInt(i);
        }

        byteBuffer.flip();
        //转换为只读的Buffer
        ByteBuffer readOnlyBuffer = byteBuffer.asReadOnlyBuffer();
        System.out.println(readOnlyBuffer.getClass());

        while (readOnlyBuffer.hasRemaining()){
            System.out.println(readOnlyBuffer.getInt());
        }

        readOnlyBuffer.put((byte)1);
    }
}
