package org.yangtao.ge.NettyTcp;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.EventExecutorGroup;

public class ClientHandler extends SimpleChannelInboundHandler<String> {
    //receive message and print it out
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("server response ： "+msg);
    }

    //connect to the server
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //send a message
        ctx.channel().writeAndFlush("i am client !");
        System.out.println("channelActive");
    }

    //disconnect from the the server
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelInactive");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.channel().close();
        cause.printStackTrace();
    }
}
