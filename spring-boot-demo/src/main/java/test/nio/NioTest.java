package test.nio;

import cn.hutool.core.util.RandomUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

/**
 * <p></p>
 *
 * @Author J.Star
 * @Date 2021-10-31
 */
//@SpringBootTest
public class NioTest {

    /**
     * FileChannel读取数据到Buffer
     *
     * @throws IOException
     */
    @Test
    void testFileChannelRead() throws IOException {
        try (
                //创建FileChannel
                RandomAccessFile file = new RandomAccessFile("a.txt", "rw");
        ) {
            FileChannel channel = file.getChannel();
            System.out.println("当前索引:" + channel.position());
            System.out.println(channel.size());
            channel.truncate(10);
            //创建Buffer
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            //读取数据到Buffer中
            int read;
            while ((read = channel.read(buffer)) != -1) {
                System.out.println("read:" + read);
                buffer.flip();
                while (buffer.hasRemaining()) {
                    System.out.println((char) buffer.get());
                }
                buffer.clear();
            }
            file.close();
            System.out.println("over");
        }

    }

    /**
     * FIleChannel写操作
     */
    @Test
    void testFileChannelWrite() {
        //打开FileChannel
        try (RandomAccessFile file = new RandomAccessFile("a.txt", "rw");) {
            FileChannel channel = file.getChannel();
            System.out.println("索引位置:" + channel.position());
            //创建buffer
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            String data = "haha\nnihao";
            buffer.put(data.getBytes(StandardCharsets.UTF_8));
            //模式转换
            buffer.flip();
            while (buffer.hasRemaining()) {
                channel.write(buffer);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void anyTest() {
        //打开FileChannel
        try (RandomAccessFile aTxt = new RandomAccessFile("a.txt", "rw");
             RandomAccessFile bTxt = new RandomAccessFile("c.txt", "rw")) {
            FileChannel fromChannel = aTxt.getChannel();
            FileChannel toChannel = bTxt.getChannel();
            fromChannel.transferTo(0, fromChannel.size(), toChannel);
            System.out.println("transfer over");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("ServerSocketChannel")
    void testServerSocketChannel() throws IOException, InterruptedException {
        //端口号设置
        int port = 8888;

        //buffer
        ByteBuffer buffer = ByteBuffer.wrap("hello".getBytes(StandardCharsets.UTF_8));

        try (ServerSocketChannel ssc = ServerSocketChannel.open()) {
            //绑定
            ssc.socket().bind(new InetSocketAddress(port));

            //非阻塞运行
            ssc.configureBlocking(false);

            //监听是否有新连接
            while (true) {
                System.out.println("等待连接...");
                SocketChannel sc = ssc.accept();
                if (sc == null) {
                    TimeUnit.SECONDS.sleep(10);
                    System.out.println("没有新的连接");
                } else {
                    System.out.println(sc.socket().getRemoteSocketAddress() + "已连接");
                    buffer.rewind();//指针0
                    sc.write(buffer);
                    sc.close();
                }
            }
        }

    }

    /**
     * 1.连接socket套接字
     * 2.处理网络I/O的通道
     * 3.基于TCP连接传输
     * 4.实现了可选择通道,可以被多路复用
     */
    @Test
    @DisplayName("SocketChannel")
    void testSocketChannel() {
        try (
                SocketChannel sc = SocketChannel.open(new InetSocketAddress(8888));
        ) {
            System.out.println("isOpen:" + sc.isOpen());
            System.out.println("isConnectionPending:" + sc.isConnectionPending());
            System.out.println("isConnected:" + sc.isConnected());
            System.out.println("finishConnect:" + sc.finishConnect());
            sc.configureBlocking(false);
            ByteBuffer byteBuffer = ByteBuffer.allocate(16);
            while (sc.read(byteBuffer) != -1) {
                byteBuffer.flip();
                while (byteBuffer.hasRemaining()) {
                    System.out.print((char) byteBuffer.get());
                }
                byteBuffer.clear();
            }
            System.out.println();
            System.out.println("read over");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * udp 无连接
     */
    @Test
    @DisplayName("DatagramChannel")
    void testDatagramChannel() throws IOException {
        DatagramChannel datagramChannel = DatagramChannel.open();
        //bind
        datagramChannel.bind(new InetSocketAddress(9999));
        //connect
        datagramChannel.connect(new InetSocketAddress("127.0.0.1", 9999));
        //写
        datagramChannel.write(ByteBuffer.wrap("hello".getBytes(StandardCharsets.UTF_8)));
        //读
        ByteBuffer readBuffer = ByteBuffer.allocate(1024);
        while (true) {
            readBuffer.clear();
            datagramChannel.read(readBuffer);
            readBuffer.flip();
            System.out.println(StandardCharsets.UTF_8.decode(readBuffer));
        }
    }

    @Test
    void sendDatagram() {
        try (DatagramChannel sendChannel = DatagramChannel.open()) {
            InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 9999);
            while (true) {
                sendChannel.send(ByteBuffer.wrap(RandomUtil.randomString(5).getBytes(StandardCharsets.UTF_8)), inetSocketAddress);
                TimeUnit.SECONDS.sleep(1);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    void receiveDatagram() {
        try (DatagramChannel receiveChannel = DatagramChannel.open()) {
            InetSocketAddress inetSocketAddress = new InetSocketAddress(9999);
            receiveChannel.bind(inetSocketAddress);
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            while (true) {

                byteBuffer.clear();

                SocketAddress socketAddress = receiveChannel.receive(byteBuffer);

                byteBuffer.flip();

                System.out.println(socketAddress.toString());

                System.out.println(StandardCharsets.UTF_8.decode(byteBuffer));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testBuffer() {
        try (
                RandomAccessFile randomAccessFile = new RandomAccessFile("a.txt", "rw");
        ) {
            FileChannel channel = randomAccessFile.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            while (channel.read(buffer) != -1) {
                //read模式
                buffer.flip();
                System.out.print(new String(buffer.array(), 0, buffer.limit()));
                buffer.clear();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void bioFileCopyServer() throws IOException {
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress(10010));
        while (true) {
            try {
                System.out.println("准备接受消息");
                Socket socket = serverSocket.accept();
                InputStream inputStream = socket.getInputStream();
                byte[] bytes = new byte[1024];
                while (true) {
                    try {
                        int read = inputStream.read(bytes);
                        if (read == -1) {
                            break;
                        }
                        System.out.println(new String(bytes, 0, read));
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("客户端已断开连接");
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    @Test
    void bioFileCopyClient() throws IOException, InterruptedException {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress("localhost", 10010));
        FileInputStream fileInputStream = new FileInputStream("E:\\downloads\\elasticsearch-7.11.2-linux-x86_64.tar.gz");
        byte[] bytes = new byte[1024];
        Instant instant = Instant.now();
        int read;
        while ((read = fileInputStream.read(bytes)) != -1) {
            socket.getOutputStream().write(bytes, 0, read);
        }
        System.out.println("size: " + fileInputStream.getChannel().size() + "cost " + Duration.between(instant, Instant.now()).toMillis());
        TimeUnit.SECONDS.sleep(60);
    }

    @Test
    void nioServer() throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(10012));
        System.out.println("nio server ready");
        SocketChannel socketChannel = serverSocketChannel.accept();
        ByteBuffer buffer = ByteBuffer.allocate(10240);
        while (socketChannel.read(buffer) != -1) {
            buffer.clear();
            System.out.println(new String(buffer.array()));
        }
    }

    @Test
    void nioClient() throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost", 10012));
        FileInputStream fileInputStream = new FileInputStream("E:\\downloads\\elasticsearch-7.11.2-linux-x86_64.tar.gz");
        FileChannel channel = fileInputStream.getChannel();
        Instant start = Instant.now();
        long t = 0;
        while (t < channel.size()) {
            //windows系统单次最多传输8M
            long count = channel.size() - t > 8092 * 1024 ? 8092 * 1024 : channel.size() - t;
            System.out.println("p:" + t + " c:" + count);
            long l = channel.transferTo(t, count, socketChannel);
            t += l;
        }
        System.out.println("transfer size: " + t + " cost: " + Duration.between(start, Instant.now()).toMillis());
    }

}
