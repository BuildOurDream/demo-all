package demo.groupchat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * <p></p>
 *
 * @Author J.Star
 * @Date 2021-11-11
 */
public class IdleEventHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            switch (event.state()) {
                case READER_IDLE:
                    System.out.println("读空闲");
                    ctx.close();
                    break;
                case WRITER_IDLE:
                    System.out.println("写空闲");
                    ctx.close();
                    break;
                case ALL_IDLE:
                    System.out.println("读写空闲");
                    ctx.close();
                    break;
            }
        }
    }
}
