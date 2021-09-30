package netty.server;

import netty.ByteBufferUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 处理消息边界的问题
 * 以分隔符的形式去处理
 */
public class Server2 {
    /*

    当我们采用分隔符的形式来划分消息的时候，此时发送的消息长度大于缓冲区的长度，就会导致读取的问题，只有后半部分的内容会被读取到
     */
//    public static void main(String[] args) {
//        // 获得服务器通道
//        try (ServerSocketChannel server = ServerSocketChannel.open()) {
//            server.bind(new InetSocketAddress(8080));
//            Selector selector = Selector.open();
//            server.configureBlocking(false);
//            server.register(selector, SelectionKey.OP_ACCEPT);
//            while (true) {
//                int ready = selector.select();
//                Set<SelectionKey> selectionKeys = selector.selectedKeys();
//                Iterator<SelectionKey> iterator = selectionKeys.iterator();
//                while (iterator.hasNext()) {
//                    SelectionKey key = iterator.next();
//                    if (key.isAcceptable()) {
//                        ServerSocketChannel channel = (ServerSocketChannel) key.channel();
//                        System.out.println("before accepting...");
//                        SocketChannel socketChannel = channel.accept();
//                        System.out.println("after accepting...");
//                        socketChannel.configureBlocking(false);
//                        ByteBuffer buffer = ByteBuffer.allocate(16);
//                        socketChannel.register(selector, SelectionKey.OP_READ, buffer);
//                        iterator.remove();
//                    } else if (key.isReadable()) {
//                        SocketChannel channel = (SocketChannel) key.channel();
//                        System.out.println("before reading...");
//                        ByteBuffer buffer = ByteBuffer.allocate(16);
//                        int read = channel.read(buffer);
//                        if (read == -1) {
//                            key.cancel();
//                            channel.close();
//                        } else {
//                            split(buffer);
//                        }
//                        System.out.println("after reading...");
//                        iterator.remove();
//                    }
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    /*
    具体解决方案，提升ByteBuffer的作用域，通过ByteBuffer和channel绑定，此时就不再是每次事件都对应一个Buffer，而是一个channel对应一个buffer
    如果两个分隔符之间的字符数量大于ByteBuffer的大小，就对ByteBuffer进行扩容（循环扩），并将新的ByteBuffer和channel进行绑定就可以了

    Q:BytBuffer会不停的扩大，就有问题，netty就可以动态的扩大或者缩小
     */
    public static void main(String[] args) {
        // 获得服务器通道
        try (ServerSocketChannel server = ServerSocketChannel.open()) {
            server.bind(new InetSocketAddress(8080));
            // 创建选择器
            Selector selector = Selector.open();
            // 通道必须设置为非阻塞模式
            server.configureBlocking(false);
            // 将通道注册到选择器中，并设置感兴趣的事件
            server.register(selector, SelectionKey.OP_ACCEPT);
            // 为serverKey设置感兴趣的事件
            while (true) {
                // 若没有事件就绪，线程会被阻塞，反之不会被阻塞。从而避免了CPU空转
                // 返回值为就绪的事件个数
                int ready = selector.select();
                System.out.println("selector ready counts : " + ready);
                // 获取所有事件
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                // 使用迭代器遍历事件
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    // 判断key的类型
                    if (key.isAcceptable()) {
                        // 获得key对应的channel
                        ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                        System.out.println("before accepting...");
                        // 获取连接
                        SocketChannel socketChannel = channel.accept();
                        System.out.println("after accepting...");
                        // 设置为非阻塞模式，同时将连接的通道也注册到选择其中，同时设置附件
                        socketChannel.configureBlocking(false);
                        ByteBuffer buffer = ByteBuffer.allocate(16);
                        socketChannel.register(selector, SelectionKey.OP_READ, buffer);
                        // 处理完毕后移除
                        iterator.remove();
                    } else if (key.isReadable()) {
                        SocketChannel channel = (SocketChannel) key.channel();
                        System.out.println("before reading...");
                        // 通过key获得附件（buffer）
                        ByteBuffer buffer = (ByteBuffer) key.attachment();
                        int read = channel.read(buffer);
                        if (read == -1) {
                            key.cancel();
                            channel.close();
                        } else {
                            // 通过分隔符来分隔buffer中的数据
                            split(buffer);
                            // 如果缓冲区太小，就进行扩容
                            if (buffer.position() == buffer.limit()) {
                                ByteBuffer newBuffer = ByteBuffer.allocate(buffer.capacity() * 2);
                                // 将旧buffer中的内容放入新的buffer中
                                buffer.flip();
                                newBuffer.put(buffer);
                                // 将新buffer放到key中作为附件
                                key.attach(newBuffer);
                            }
                        }
                        System.out.println("after reading...");
                        // 处理完毕后移除
                        iterator.remove();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //遍历分隔符
    private static void split(ByteBuffer buffer) {
        buffer.flip();
        for (int i = 0; i < buffer.limit(); i++) {
            // 遍历寻找分隔符
            // get(i)不会移动position
            if (buffer.get(i) == '\n') {
                // 缓冲区长度
                int length = i + 1 - buffer.position();
                ByteBuffer target = ByteBuffer.allocate(length);
                // 将前面的内容写入target缓冲区
                for (int j = 0; j < length; j++) {
                    // 将buffer中的数据写入target中
                    target.put(buffer.get());
                }
                // 打印结果
                ByteBufferUtil.debugAll(target);
            }
        }
        // 切换为写模式，但是缓冲区可能未读完，这里需要使用compact
        buffer.compact();
    }
}
