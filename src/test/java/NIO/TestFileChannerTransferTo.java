package NIO;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

//文件编程的TransfereTo()方法
public class TestFileChannerTransferTo {
    public static void main(String[] args) {
        try (FileInputStream fis = new FileInputStream("file\\date.txt");
             FileOutputStream fos = new FileOutputStream("file\\student.txt");
             FileChannel inputChannel = fis.getChannel();
             FileChannel outputChannel = fos.getChannel()) {
            // 参数：inputChannel的起始位置，传输数据的大小，目的channel
            // 返回值为传输的数据的字节数
            // transferTo一次只能传输2G的数据
            inputChannel.transferTo(0, inputChannel.size(), outputChannel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
