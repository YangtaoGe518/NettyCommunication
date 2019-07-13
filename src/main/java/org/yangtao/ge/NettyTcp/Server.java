package org.yangtao.ge.NettyTcp;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class Server {
    public static void main(String[] args) {

        //boss is a listener
        EventLoopGroup boss = new NioEventLoopGroup();
        //worker is for reading and writing
        EventLoopGroup worker = new NioEventLoopGroup();

        try{
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boss,worker);

            //socket factory
            bootstrap.channel(NioServerSocketChannel.class);

            //pipeline factory
            bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    ChannelPipeline pipeline = socketChannel.pipeline();
                    pipeline.addLast(new StringDecoder());
                    pipeline.addLast(new StringEncoder());
                    //handler class
                    pipeline.addLast(new ServerHandler());
                }
            });

            //TCP setups
            //size of channel pool
            bootstrap.option(ChannelOption.SO_BACKLOG,1024);
            //keep active and kill dead connections
            bootstrap.childOption(ChannelOption.SO_KEEPALIVE,true);
            //No delay
            bootstrap.childOption(ChannelOption.TCP_NODELAY,true);
            //bind port
            ChannelFuture future = bootstrap.bind(8866).sync();
            System.out.println("server start ...... ");

            //waiting for shutdown
            future.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
