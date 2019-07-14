package org.yangtao.ge.NettyLogin.Client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.yangtao.ge.NettyLogin.Model.User;
import org.yangtao.ge.NettyLogin.Protocol.LoginRequestPacket;
import org.yangtao.ge.NettyLogin.Protocol.LoginResponsePacket;
import org.yangtao.ge.NettyLogin.Protocol.Packet;
import org.yangtao.ge.NettyLogin.Protocol.PacketFunction;

import java.util.Date;
import java.util.Scanner;

public class LoginClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(new Date() + ": Start to login ...");

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the user name: ");
        String userName = scanner.nextLine();
        System.out.println("Enter the password: ");
        String password = scanner.nextLine();

        // make a login user
        User loginUser = new User(userName,password);

        //login object for test
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setUserId(loginUser.getUserId());
        loginRequestPacket.setUsername(loginUser.getUsername());
        loginRequestPacket.setPassword(loginUser.getPassword());

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
                System.out.println(new Date() + ":[UserId]" + loginResponsePacket.getUserId() + " Login successfully");
            } else {
                System.out.println(new Date() + ": Login fail，because：" + loginResponsePacket.getReason());
            }
        }
    }
}
