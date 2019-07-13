package org.yangtao.ge.NettyChat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class ChatServerHandler extends SimpleChannelInboundHandler<String> {

    public static final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        for (Channel ch : channels){
            ch.writeAndFlush(
                    "[" + channel.remoteAddress() + "] " + "has joined");
        }
        channels.add(channel);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        for (Channel ch : channels) {
            ch.writeAndFlush(
                    "[" + channel.remoteAddress() + "] " + "has left");
        }
        channels.remove(channel);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println("[" + channel.remoteAddress() + "] " + "online");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println("[" + channel.remoteAddress() + "] " + "offline");
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String message) throws Exception {
        Channel channel = ctx.channel();
        for (Channel ch : channels) {
            if (ch == channel) {
                ch.writeAndFlush("[you]：" + message + "\n");
            } else {
                ch.writeAndFlush(
                        "[" + channel.remoteAddress() + "]: " + message + "\n");
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
