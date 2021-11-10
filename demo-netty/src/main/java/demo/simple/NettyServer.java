package demo.simple;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * netty服务端
 *
 * @Author J.Star
 * @Date 2021-11-07
 */
public class NettyServer {

    public static void main(String[] args) throws InterruptedException {
        //创建BossGroup何WorkerGroup
        /*
            说明:
            创建两个线程组 bossGroup何workerGroup
            bossGroup仅处理连接请求
            workerGroup处理真正的客户端业务
            两个都是无线循环处理
         */
        EventLoopGroup boosGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            //创建服务器端启动的对象,配置参数
            ServerBootstrap serverBootstrap = new ServerBootstrap();

            //使用链式编程来进行设置
            serverBootstrap.group(boosGroup, workerGroup)
                    //使用NioServerSocketChannel做服务器通道实现
                    .channel(NioServerSocketChannel.class)
                    //设置线程队列得到的连接个数
                    .option(ChannelOption.SO_BACKLOG, 128)
                    //设置保持活动连接状态
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    //给workerGroup的EventLoop对应的管道设置处理器
                    .childHandler(new ChannelInitializer<SocketChannel>() {//创建一个通道初始化对象
                        //给pipeline设置处理器
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new NettyServerHandler());
                        }
                    });
            System.out.println("server is ready");
            //绑定一个端口并且同步
            ChannelFuture channelFuture = serverBootstrap.bind(6668).sync();
            //对关闭通道进行监听
            channelFuture.channel().closeFuture().sync();
        } finally {
            boosGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
