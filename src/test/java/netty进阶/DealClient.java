package netty进阶;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DealClient {
    static final Logger log = LoggerFactory.getLogger(DealClient.class);

    public static void main(String[] args) {
        NioEventLoopGroup worker = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.group(worker);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new LoggingHandler(LogLevel.INFO));
                    ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                        @Override
                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                            // 约定最大长度为16
                            final int maxLength = 16;
                            // 被发送的数据
                            char c = 'a';
                            // 向服务器发送10个报文
                            ByteBuf buffer = ctx.alloc().buffer(maxLength);
                            for (int i = 0; i < 10; i++) {
                                // 定长byte数组，未使用部分会以0进行填充
                                byte[] bytes = new byte[maxLength];
                                // 生成长度为0~15的数据
                                for (int j = 0; j < (int) (Math.random() * (maxLength - 1)); j++) {
                                    bytes[j] = (byte) c;
                                }
                                buffer.writeBytes(bytes);
                                c++;
                                // 将数据发送给服务器
                            }
                            ctx.writeAndFlush(buffer);
                        }
                    });
                }
            });
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 8080).sync();
            channelFuture.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            log.error("client error", e);
        } finally {
            worker.shutdownGracefully();
        }
    }
}
