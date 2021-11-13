package demo.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * <p></p>
 *
 * @Author J.Star
 * @Date 2021-11-11
 */
public class MyServer {

    private final int port;

    public MyServer(int port) {
        this.port = port;
    }

    public void run() throws InterruptedException {
        NioEventLoopGroup boosGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap()
                    .group(boosGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    //http编解码器
                                    .addLast(new HttpServerCodec())
                                     //以块的方式写,添加chunkedWriter处理器
                                    .addLast(new ChunkedWriteHandler())
                                    //http数据在传输过程中如果数据量大是会分段的,这里将多个段聚合起来
                                    .addLast(new HttpObjectAggregator(8192))
                                    //将http协议升级为ws协议,保持长连接
                                    .addLast(new WebSocketServerProtocolHandler("/ws"))
                                    //自定义处理器,处理业务逻辑
                                    .addLast(new MyTextWebSocketFrameHandler());
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
        new MyServer(9000).run();
    }
}
