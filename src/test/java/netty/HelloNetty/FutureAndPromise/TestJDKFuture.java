package netty.HelloNetty.FutureAndPromise;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * 加深JDK Future的理解
 */
@Slf4j
public class TestJDKFuture {
    public static void main(String[] args) throws Exception {
        ExecutorService service = Executors.newFixedThreadPool(2);

        // 获得Future对象
        Future<Integer> future = service.submit(new Callable<Integer>() {

            @Override
            public Integer call() throws Exception {
                Thread.sleep(1);
                log.info("执行计算");
                return 50;
            }
        });

        // 通过阻塞的方式，获得运行结果
        log.info("结果是 {}",future.get());
    }
}
