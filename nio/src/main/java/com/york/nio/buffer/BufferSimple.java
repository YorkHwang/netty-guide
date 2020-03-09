package com.york.nio.buffer;

import java.nio.IntBuffer;

/**
 * @Description:Buffer初探
 * @Author: York.Hwang
 * @Time: 2020/3/8 22:53
 */
public class BufferSimple {
    private static final int bufferSize = 5;
    public static void main(String[] args) {
        IntBuffer intBuffer = IntBuffer.allocate(bufferSize);

        for(int i=0; i<bufferSize; i++){
            intBuffer.put(i*2);
        }

        intBuffer.flip();
        intBuffer.position(1);
        intBuffer.limit(4);
        while (intBuffer.hasRemaining()){
            System.out.println(intBuffer.get());
        }
    }
}
