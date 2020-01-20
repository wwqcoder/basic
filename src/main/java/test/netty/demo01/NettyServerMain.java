package test.netty.demo01;

/**
 * @创建人 zhaojingen
 * @创建时间 2020/1/2
 * @描述
 */
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import java.util.Scanner;

public class NettyServerMain {
    public static void main(String[] args) throws Exception {
        int port = 7001;
        //主线程组，接收网络请求
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //worker线程组，对接收到的请求进行读写处理
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        //启动服务的启动类（辅助类）
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup)  // 添加主线程组和worker线程组
                .channel(NioServerSocketChannel.class)  //设置channel为服务端NioServerSocketChannel
                .childHandler(new ChannelInitializer<NioSocketChannel>() { //绑定io事件处理类
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        ChannelPipeline pipeline = nioSocketChannel.pipeline();

                        //以指定分隔符$拆包和粘包
//                        pipeline.addLast(new DelimiterBasedFrameDecoder(1024, Unpooled.copiedBuffer("$".getBytes())));
                        //以固定长度拆包和粘包
//                        pipeline.addLast(new FixedLengthFrameDecoder(10));
                        //以换行符拆包和粘包
                        pipeline.addLast(new LineBasedFrameDecoder(1024));

                        pipeline.addLast(new StringDecoder()); //将收到对象转为字符串
                        pipeline.addLast(new IODisposeHandler()); //添加io处理器
                    }
                })
                .option(ChannelOption.SO_BACKLOG, 128) //设置日志
                .option(ChannelOption.SO_SNDBUF, 32 * 1024) //设置发送缓存
                .option(ChannelOption.SO_RCVBUF, 32 * 1024)  //接收缓存
                .childOption(ChannelOption.SO_KEEPALIVE, true);  //是否保持连接

        //绑定端口，同步等待成功
        ChannelFuture future = bootstrap.bind(port).sync();
        System.out.println("服务启动,等待连接");

        //关闭监听端口，同步等待
        future.channel().closeFuture().sync();

        //退出，释放线程资源
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }


    /**
     * io事件处理
     */
    static class IODisposeHandler extends ChannelHandlerAdapter {

        WriteThread writeThread;

        /**
         * 建立连接
         *
         * @param ctx
         * @throws Exception
         */
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            System.out.println("收到连接:" + ctx.channel());

            //新起写数据线程
            writeThread = new WriteThread(ctx);
            writeThread.start();
        }


        /**
         * 消息读取
         *
         * @param ctx
         * @param msg
         * @throws Exception
         */
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            System.out.println("server receive msg:" + msg);


//            System.out.println("server receive msg:"+((ByteBuf)msg).toString(CharsetUtil.UTF_8));  //不使用StringDecoder解码时，则需使用此类解码
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            System.out.println("连接出错");
            writeThread.runFlag = false;
            ctx.close();
        }
    }


    /**
     * 写数据线程
     */
    static class WriteThread extends Thread {
        ChannelHandlerContext ctx;
        //线程关闭标志位
        volatile boolean runFlag = true;

        public WriteThread(ChannelHandlerContext ctx) {
            this.ctx = ctx;
        }

        @Override
        public void run() {
            try {
                Scanner scanner = new Scanner(System.in);
                while (runFlag) {
                    System.out.print("server send msg:");
                    String msg = scanner.nextLine();
                    msg += System.lineSeparator();
                    //发送数据
                    ctx.channel().writeAndFlush(Unpooled.copiedBuffer(msg.getBytes()));
                }
            } catch (Exception e) {
            }
        }
    }
}
