package test.netty.demo01;

/**
 * @创建人 zhaojingen
 * @创建时间 2020/1/2
 * @描述
 */
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

import java.util.Scanner;

public class NettyClientMain {
    public static void main(String[] args) throws Exception {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workerGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        ChannelPipeline pipeline = nioSocketChannel.pipeline();

                        //以指定分隔符$拆包和粘包
//                        pipeline.addLast(new DelimiterBasedFrameDecoder(1024, Unpooled.copiedBuffer("$".getBytes())));
                        //以固定长度拆包和粘包
//                        pipeline.addLast(new FixedLengthFrameDecoder(10));
                        //以换行符拆包和粘包
                        pipeline.addLast(new LineBasedFrameDecoder(1024));

                        pipeline.addLast(new StringDecoder());
//                        pipeline.addLast(new StringEncoder());
                        pipeline.addLast(new IODisposeHandler());
                    }
                });
        ChannelFuture future = bootstrap.connect("127.0.0.1", 7001).sync();
        future.channel().closeFuture().sync();
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
