package NIO.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/*
客户端
 */
public class Client {
//    public static void main(String[] args) {
//        try (SocketChannel socketChannel = SocketChannel.open()) {
//            // 建立连接
//            socketChannel.connect(new InetSocketAddress("localhost", 8080));
//            System.out.println("waiting...");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        // 建立连接
        socketChannel.connect(new InetSocketAddress("localhost", 8080));
        System.out.println("waiting...");
        socketChannel.write(Charset.defaultCharset().encode("hellosd asjhdsjhdsajdh123213\n"));
        //接收键盘输入，阻塞方法
        System.in.read();
    }
}