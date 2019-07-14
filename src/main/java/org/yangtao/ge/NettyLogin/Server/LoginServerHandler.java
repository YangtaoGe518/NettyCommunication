package org.yangtao.ge.NettyLogin.Server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.yangtao.ge.NettyLogin.Encryptor;
import org.yangtao.ge.NettyLogin.Model.User;
import org.yangtao.ge.NettyLogin.Model.UserFactory;
import org.yangtao.ge.NettyLogin.Protocol.LoginRequestPacket;
import org.yangtao.ge.NettyLogin.Protocol.LoginResponsePacket;
import org.yangtao.ge.NettyLogin.Protocol.Packet;
import org.yangtao.ge.NettyLogin.Protocol.PacketFunction;

import java.util.ArrayList;
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
                loginResponsePacket.setUserId(loginRequestPacket.getUserId());
                System.out.println(new Date() + ": login successfully");
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

        testDataBase(); // apply the simulating database

        String loginUserName = loginRequestPacket.getUsername();
        String loginPassWord = Encryptor.EncryptedInMd5(loginRequestPacket.getPassword()); // Use encryptors
        ArrayList<User> users = UserFactory.getUsers();

        for (User user: users){
            if(user.getUsername().equals(loginUserName)){
                if(user.getPassword().equals(loginPassWord)){
                    isValid = true;
                    System.out.println("Login Successful");
                }
                else{
                    isValid = false;
                    System.out.println("Warning: Found the username, but input password is wrong");
                }
            }
            else{
                System.out.println("Warning: There not exist the input username");
                isValid = false;
            }
        }

        return isValid;
    }

    /**
     * Make a very small data base in the server, and encrypt the password
     */
    private void testDataBase(){

        User user1 = new User("YangtaoGe", "abcd");
        User user2 = new User("TerryGe", "efgh");
        User user3 = new User("GavinGe","ijkl");
        User admin = new User("admin", "password");


        UserFactory.addUsers(user1);
        UserFactory.addUsers(user2);
        UserFactory.addUsers(user3);
        UserFactory.addUsers(admin);

        ArrayList<User> users = UserFactory.getUsers();

        for (User user: users){
            String encryptedPassword = Encryptor.EncryptedInMd5(user.getPassword());
            user.setPassword(encryptedPassword);
        }
    }
}
