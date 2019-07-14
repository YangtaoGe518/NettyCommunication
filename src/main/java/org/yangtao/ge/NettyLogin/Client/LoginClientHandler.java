package org.yangtao.ge.NettyLogin.Client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.yangtao.ge.NettyLogin.Protocol.LoginRequestPacket;
import org.yangtao.ge.NettyLogin.Protocol.LoginResponsePacket;
import org.yangtao.ge.NettyLogin.Protocol.Packet;
import org.yangtao.ge.NettyLogin.Protocol.PacketFunction;

import java.util.Date;
import java.util.UUID;

public class LoginClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(new Date() + ": Start to login ...");

        //login object for test
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setUserId(UUID.randomUUID().toString());
        loginRequestPacket.setUsername("admin");
        loginRequestPacket.setPassword("password");

        //Buffered
        ByteBuf buffer = PacketFunction.INSTANCE.encode(ctx.alloc(), loginRequestPacket);
        //send message
        ctx.channel().writeAndFlush(buffer);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf responseByteBuf = (ByteBuf) msg;
        Packet packet = PacketFunction.INSTANCE.decode(responseByteBuf);

        if (packet instanceof LoginResponsePacket) {
            LoginResponsePacket loginResponsePacket = (LoginResponsePacket) packet;

            if (loginResponsePacket.isSuccess()) {
                System.out.println(new Date() + ": Login successfully");
            } else {
                System.out.println(new Date() + ": Login fail，because：" + loginResponsePacket.getReason());
            }
        }
    }
}
