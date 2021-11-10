package demo.groupchat;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * <p></p>
 *
 * @Author J.Star
 * @Date 2021-11-10
 */
public class GroupChatServerHandler extends SimpleChannelInboundHandler<String> {

    //定义一个channel组,管理所有的channel
    //GlobalEventExecutor.INSTANCE 全局的事件执行器,是一个单例
    private static final ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss SSS");

    /**
     * 处理器添加时完成后调用
     * 将当前的channel加入到channelGroup
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //将该用户加入聊天的信息推送给其他用户
        channelGroup.writeAndFlush(LocalDateTime.now().format(DATE_TIME_FORMATTER) + " [用户]" + channel.remoteAddress() + "加入群聊\n");
        channelGroup.add(channel);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        channelGroup.writeAndFlush(LocalDateTime.now().format(DATE_TIME_FORMATTER) + " [用户]" + ctx.channel().remoteAddress() + "退出群聊\n");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(LocalDateTime.now().format(DATE_TIME_FORMATTER) + " [用户]" + ctx.channel().remoteAddress() + "已上线\n");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(LocalDateTime.now().format(DATE_TIME_FORMATTER) + " [用户]" + ctx.channel().remoteAddress() + "离线\n");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();
        //遍历channelGroup分情况推送消息
        System.out.println(LocalDateTime.now().format(DATE_TIME_FORMATTER) + " " + channel.remoteAddress() + ": " + msg);
        channelGroup.forEach(ch -> {
            if (ch != channel) {
                ch.writeAndFlush(Unpooled.copiedBuffer(LocalDateTime.now().format(DATE_TIME_FORMATTER) + " [用户]" + channel.remoteAddress() + ": " + msg + "\n", CharsetUtil.UTF_8));
            } else {
                ch.writeAndFlush(Unpooled.copiedBuffer(LocalDateTime.now().format(DATE_TIME_FORMATTER) + " [自己]: " + msg + "\n", CharsetUtil.UTF_8));
            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
