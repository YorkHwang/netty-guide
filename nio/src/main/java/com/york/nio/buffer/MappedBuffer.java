package com.york.nio.buffer;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Description:堆外内存MappedByteBuffer，可以直接在堆外内存修改，操作系统不需要拷贝到内存
 * @Author: York.Hwang
 * @Time: 2020/3/10 23:03
 */
public class MappedBuffer {
    private static String ACCESS_FILE = "b.txt";

    public static void main(String[] args) throws Exception {
        RandomAccessFile randomAccessFile = new RandomAccessFile(ACCESS_FILE, "rw");
        FileChannel fileChannel = randomAccessFile.getChannel();

        //设置读写模式操作堆外文件，开始位置和可操作长度
        MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 10);
        mappedByteBuffer.put(0, (byte) 1);
        mappedByteBuffer.put(5, (byte) 'h');

        randomAccessFile.close();
        System.out.println("对外写文件成功");
    }
}
