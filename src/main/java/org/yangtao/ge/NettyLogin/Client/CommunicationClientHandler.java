package org.yangtao.ge.NettyLogin.Client;


import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.Date;

public class CommunicationClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf byteBuf = (ByteBuf) msg;

        System.out.println(new Date() + ": Read data from server -> " + byteBuf.toString(Charset.forName("utf-8")));
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(new Date() + ": Sent data from Client");

        ByteBuf byteBuf = getByteBuf(ctx);
        ctx.channel().writeAndFlush(byteBuf);
    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx){
        //allocate a buffer
        ByteBuf byteBuf = ctx.alloc().buffer();
        //from string to byte
        byte[] bytes = "Hello Yangtao".getBytes(Charset.forName("utf-8"));
        //full the buffer
        byteBuf.writeBytes(bytes);

        return byteBuf;
    }
}
