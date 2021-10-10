package Test;

/*测试自定义编解码器
 */

public class TsetMessageCodec {
/*    public static void main(String[] args) throws Exception {
        //对于不记录状态的Handler可以提出来,作为公共的Handler
        //LengthFieldBasedFrameDecoder()因为会记录状态,当出现半包时,消息不会交由下一个Handler,而是保存在当前Handler,多线程情况下,就可能出现错误
        LoggingHandler loggingHandler = new LoggingHandler(LogLevel.INFO);
        MessageCodec messageCodec = new MessageCodec();

        EmbeddedChannel embeddedChannel = new EmbeddedChannel(
                //解决粘包半包问题
                new LengthFieldBasedFrameDecoder(1024, 12, 4, 0, 0)
                //, new LoggingHandler(LogLevel.INFO)
                //, new MessageCodec()
                , loggingHandler
                , messageCodec);

        //encode
        LoginRequestMessage message = new LoginRequestMessage("wzy", "123");
        //模拟发送消息，消息出站
        embeddedChannel.writeOutbound(message);

        //decode
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        new MessageCodec().encode(null, message, buf);
        embeddedChannel.writeInbound(buf);

    }*/
}
