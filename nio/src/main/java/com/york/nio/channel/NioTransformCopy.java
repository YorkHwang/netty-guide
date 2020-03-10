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
public class NioTransformCopy {
    private static final String SOURCE_FILE_PATH = "a.jpg";
    private static final String TARGET_FILE_PATH = "copy.jpg";

    public static void main(String[] args) throws IOException {
        FileInputStream srcStream = new FileInputStream(SOURCE_FILE_PATH);
        FileChannel srcChannel = srcStream.getChannel();

        FileOutputStream targetStream = new FileOutputStream(TARGET_FILE_PATH);
        FileChannel targetChannel = targetStream.getChannel();

        //转换方式拷贝
        targetChannel.transferFrom(srcChannel,0, srcChannel.size());

        //关闭通道和输入输出流
        srcChannel.close();
        targetChannel.close();
        srcStream.close();
        targetStream.close();

        System.out.println("图片文件复制完成！");
    }
}
