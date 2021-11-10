package demo.groupchat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

/**
 * <p></p>
 *
 * @Author J.Star
 * @Date 2021-11-10
 */
public class GroupChatServer {

    /**
     * 监听端口
     */
    private int port;

    public GroupChatServer(int port) {
        this.port = port;
    }

    /**
     * 处理客户端请求
     */
    public void run() throws InterruptedException {
        NioEventLoopGroup boosGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap()
                    .group(boosGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    //解码器
                                    .addLast("decoder", new StringDecoder())
                                    //编码器
                                    .addLast("encoder", new StringDecoder())
                                    //自己的业务处理器
                                    .addLast(new GroupChatServerHandler());
                        }
                    });
            System.out.println("server is ready");
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            //监听关闭事件
            channelFuture.channel().closeFuture().sync();
        } finally {
            boosGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new GroupChatServer(8888).run();
    }
}
