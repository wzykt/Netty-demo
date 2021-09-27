package netty;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

@Slf4j
public class TestByteBuffer {
    public static void main(String[] args) {
        //练习读文件 FileChannel
        //两种方式 1.输入输出流 2.RandomAccessFile
        try (FileChannel channel = new FileInputStream("date.txt").getChannel()) {
            //准备缓冲区
            ByteBuffer byteBuffer = ByteBuffer.allocate(10);
            //while存在的意义是，缓冲区不可能跟着文件大小改变，但是一次读取不可能全部读取完毕。
            while (true) {
                //从channel读取数据，向buffer中写入
                int len = channel.read(byteBuffer);
                log.info("读取到的字节数{}", len);
                //保证循环可以退出，-1表示缓冲区没有值可以读
                if (len == -1) {
                    break;
                }
                //打印buffer的内容
                byteBuffer.flip();//切换至读模式
                while (byteBuffer.hasRemaining()) {//是否缓冲区还有值
                    byte b = byteBuffer.get();
                    log.info("实际字节{}", (char) b);
                    //System.out.println((char) b);
                }
                byteBuffer.clear();//切换为写模式
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
