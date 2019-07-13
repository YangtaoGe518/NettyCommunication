package org.yangtao.ge.NettyTcp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;

public class Client {
    public static void main(String[] args) {

        //worker is for IO
        EventLoopGroup worker = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(worker);

            //socket factory
            bootstrap.channel(NioSocketChannel.class);

            //set up pipelines
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    ChannelPipeline pipeline = socketChannel.pipeline();
                    pipeline.addLast(new StringDecoder());
                    pipeline.addLast(new StringEncoder());
                    //handler class
                    pipeline.addLast(new ClientHandler());
                }
            });

            //connection
            ChannelFuture future = bootstrap.connect(new InetSocketAddress("127.0.0.1",8866)).sync();

            //waiting for shutdown of server
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            worker.shutdownGracefully();
        }
    }
}

