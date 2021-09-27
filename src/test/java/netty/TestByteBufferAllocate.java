package netty;

import java.nio.ByteBuffer;

public class TestByteBufferAllocate {
    public static void main(String[] args) {
        System.out.println(ByteBuffer.allocate(16).getClass());
        System.out.println(ByteBuffer.allocateDirect(16).getClass());
        /*
        class java.nio.HeapByteBuffer  - java 堆内存，读写效率低，会收到读写影响
        class java.nio.DirectByteBuffer  - 直接内,读写效率高（少一次拷贝），不会受GC影响，存在分配效率第的情况，因为要调用操作系统的函数
         */
    }
}
