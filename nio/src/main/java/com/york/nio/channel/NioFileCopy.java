package com.york.nio.channel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Description:使用同一个Buffer拷贝文件
 * @Author: York.Hwang
 * @Time: 2020/3/9 23:36
 */
public class NioFileCopy {
    private static final String SOURCE_FILE_PATH = "a.txt";
    private static final String TARGET_FILE_PATH = "b.txt";

    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(SOURCE_FILE_PATH);
        FileChannel inputChannel = fileInputStream.getChannel();

        FileOutputStream fileOutputStream = new FileOutputStream(TARGET_FILE_PATH);
        FileChannel outputChannel = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
        while (true) {
            //进行复位，继续读
            byteBuffer.clear();
            int read = inputChannel.read(byteBuffer);
            //判断读完了
            if (read == -1) {
                break;
            }
            //进行反转，切换读写
            byteBuffer.flip();
            outputChannel.write(byteBuffer);
        }
        inputChannel.close();
        outputChannel.close();

        System.out.println("文件复制完成！");
    }
}
