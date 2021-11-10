package demo.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

/**
 * 自定义一个handler需要继承netty规定好的
 *
 * @Author J.Star
 * @Date 2021-11-07
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 读取客户端发送的消息
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("thread: " + Thread.currentThread().getName());
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("msg: " + byteBuf.toString(CharsetUtil.UTF_8));
        System.out.println("client address: " + ctx.channel().remoteAddress());
        ctx.channel().eventLoop().execute(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
                System.out.println("execute1 =>" + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        ctx.channel().eventLoop().execute(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
                System.out.println("execute2 =>" + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        ctx.channel().eventLoop().schedule(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("schedule =>" + Thread.currentThread().getName());
        }, 1, TimeUnit.SECONDS);
    }

    /**
     * 数据读取完毕
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //将数据写入缓存中,并刷新
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello, client~", CharsetUtil.UTF_8));
    }

    /**
     * 处理异常
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    @Test
    void oo() {
        System.out.println(1);
        System.out.println(1 << 1);
        System.out.println(1 << 2);
        System.out.println(1 << 3);
        System.out.println(1 << 3 | 1 << 0 | 1 << 1 | 1 << 2);
    }
}
