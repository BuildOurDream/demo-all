package demo.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;
import org.junit.jupiter.api.Test;

/**
 * <p></p>
 *
 * @Author J.Star
 * @Date 2021-11-10
 */
public class OtherTest {

    @Test
    void unpooledDemo() {
        ByteBuf buffer = Unpooled.buffer(10);
        for (int i = 0; i < 10; i++) {
            buffer.writeByte(i);
        }

        buffer = Unpooled.copiedBuffer("hello", CharsetUtil.UTF_8);

        System.out.println(buffer.capacity());
        System.out.println(buffer.readableBytes());
        System.out.println(buffer.writerIndex());
        int readableBytes = buffer.readableBytes();
        for (int i = 0; i < readableBytes; i++) {
            System.out.println(buffer.readByte());
        }
    }
}
