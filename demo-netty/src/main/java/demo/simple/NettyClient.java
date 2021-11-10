package demo.simple;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * netty客户端
 *
 * @Author J.Star
 * @Date 2021-11-07
 */
public class NettyClient {

    public static void main(String[] args) throws InterruptedException {
        //客户端只需要一个事件循环组
        NioEventLoopGroup group = new NioEventLoopGroup();

        try {
            //客户端启动对象
            Bootstrap bootstrap = new Bootstrap();

            //设置参数
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            //加入自己的处理器
                            ch.pipeline().addLast(new NettyClientHandler());
                        }
                    });

            System.out.println("client is ready");

            //连接服务器
            ChannelFuture channelFuture = bootstrap.connect("localhost", 6668).sync();

            //对关闭通道进行监听
            channelFuture.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }

    }
}
