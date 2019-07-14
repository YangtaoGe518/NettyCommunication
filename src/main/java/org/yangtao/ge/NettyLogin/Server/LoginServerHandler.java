package org.yangtao.ge.NettyLogin.Server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.yangtao.ge.NettyLogin.Protocol.LoginRequestPacket;
import org.yangtao.ge.NettyLogin.Protocol.LoginResponsePacket;
import org.yangtao.ge.NettyLogin.Protocol.Packet;
import org.yangtao.ge.NettyLogin.Protocol.PacketFunction;

import java.util.Date;

public class LoginServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(new Date() + ": processing a login process ...");
        ByteBuf requestByteBuf = (ByteBuf) msg;
        // make it as a packet
        Packet packet = PacketFunction.INSTANCE.decode(requestByteBuf);

        // login process
        if (packet instanceof LoginRequestPacket) {
            LoginRequestPacket loginRequestPacket = (LoginRequestPacket) packet;
            LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
            loginResponsePacket.setVersion(packet.getVersion());

            // feedback
            if (valid(loginRequestPacket)) {
                loginResponsePacket.setSuccess(true);
                System.out.println(new Date() + ": login successfully");
                System.out.println(new Date() + ": [username]" + loginRequestPacket.getUsername());
                System.out.print(new Date() + ": [userId]" + loginRequestPacket.getUserId());
            } else {
                loginResponsePacket.setReason("Either your password or username is wrong");
                loginResponsePacket.setSuccess(false);
                System.out.println(new Date() + ": login fail");
            }

            ByteBuf responseByteBuf = PacketFunction.INSTANCE.encode(ctx.alloc(), loginResponsePacket);
            ctx.channel().writeAndFlush(responseByteBuf);
        }
    }

    /**
     * Match the username and password in the database
     * @param loginRequestPacket
     * @return isValid
     */
    private boolean valid(LoginRequestPacket loginRequestPacket){
        boolean isValid = false;
        isValid = true;

        return isValid;
    }
}
