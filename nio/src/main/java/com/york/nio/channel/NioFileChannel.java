package com.york.nio.channel;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Description:FileChannel写文件实例
 * @Author: York.Hwang
 * @Time: 2020/3/9 23:13
 */
public class NioFileChannel {

    private static String CONTENT = "FileChannel的写文件实例";
    private static String FILE_PATH = "a.txt";
    private static String UTF8 = "UTF-8";


    public static void main(String[] args) throws IOException {
        //写文件
        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
        byteBuffer.put(CONTENT.getBytes(UTF8));
        byteBuffer.flip();

        FileOutputStream fileOutputStream = new FileOutputStream(FILE_PATH);
        FileChannel fileOutChannel = fileOutputStream.getChannel();
        fileOutChannel.write(byteBuffer);
        fileOutputStream.close();
        System.out.println("写文件完成！");

        //读文件
        File file = new File(FILE_PATH);
        FileInputStream fileInputStream = new FileInputStream(file);
        FileChannel fileInputChannel = fileInputStream.getChannel();

        ByteBuffer fileInputBuffer = ByteBuffer.allocate((int)file.length());
        fileInputChannel.read(fileInputBuffer);
        fileInputStream.close();

        System.out.println("读取文件的内容："+new String(fileInputBuffer.array()));

    }

}
