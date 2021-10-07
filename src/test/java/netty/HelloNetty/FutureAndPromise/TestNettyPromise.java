package netty.HelloNetty.FutureAndPromise;

import io.netty.channel.EventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultPromise;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
@Slf4j
public class TestNettyPromise {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 创建EventLoop
        NioEventLoopGroup group = new NioEventLoopGroup();
        EventLoop eventLoop = group.next();

        // 创建Promise对象，用于存放结果
        DefaultPromise<Integer> promise = new DefaultPromise<>(eventLoop);

        new Thread(()->{
            try {
                // int = 1 / 0;
                TimeUnit.SECONDS.sleep(1);
                // 自定义线程向Promise中存放结果
                promise.setSuccess(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
                //放错误结果
                promise.setFailure(e);
            }
        }).start();

        // 主线程从Promise中获取结果
        log.info(Thread.currentThread().getName() + " " + promise.get());
    }
}
