package netty;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class  TestByteBufferString {
    public static void main(String[] args) {
        // 准备两个字符串
        String str1 = "hello";
        String str2 = "";

        //1.字符串调用getByte方法获得byte数组，将byte数组放入ByteBuffer中
        ByteBuffer buffer1 = ByteBuffer.allocate(16);
        // 通过字符串的getByte方法获得字节数组，放入缓冲区中
        buffer1.put(str1.getBytes());
        ByteBufferUtil.debugAll(buffer1);
        //解码要注意，切换模式，buffer1.flip()

        //2. 通过StandardCharsets的encode方法获得ByteBuffer
        // 此时获得的ByteBuffer为读模式，无需通过flip切换模式
        ByteBuffer buffer2 = StandardCharsets.UTF_8.encode(str1);
        ByteBufferUtil.debugAll(buffer2);

        //3. 通过StandardCharsets的encode方法获得ByteBuffer
        // 此时获得的ByteBuffer为读模式，无需通过flip切换模式
        ByteBuffer buffer3 = ByteBuffer.wrap(str1.getBytes());
        ByteBufferUtil.debugAll(buffer3);

        // 将缓冲区中的数据转化为字符串
        // 通过StandardCharsets解码，获得CharBuffer，再通过toString获得字符串
        str2 = StandardCharsets.UTF_8.decode(buffer2).toString();
        System.out.println(str2);
        ByteBufferUtil.debugAll(buffer1);
    }
}
