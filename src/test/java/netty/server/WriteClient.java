package netty.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class WriteClient {
    public static void main(String[] args)throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress(8080));
        int count = 0;
        while(true){
            ByteBuffer buffer = ByteBuffer.allocate(1024*1024);
            count+=socketChannel.read(buffer);
            System.out.println(count);
            buffer.clear();
        }
    }
}
