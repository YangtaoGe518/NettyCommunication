import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import org.yangtao.ge.NettyLogin.Model.User;
import org.junit.Assert;
import org.junit.Test;
import org.yangtao.ge.NettyLogin.Model.UserFactory;
import org.yangtao.ge.NettyLogin.Protocol.LoginRequestPacket;
import org.yangtao.ge.NettyLogin.Protocol.Packet;
import org.yangtao.ge.NettyLogin.Protocol.PacketFunction;
import org.yangtao.ge.NettyLogin.Serializer.JSONSerializer;
import org.yangtao.ge.NettyLogin.Serializer.Serializer;

import java.util.UUID;

public class test {
    @Test
    public void testing(){
        User user1 = new User("YangtaoGe", "abcd");
        User user2 = new User("TerryGe", "abcd");
        User user3 = new User("GavinGe","abcd");

        UserFactory.addUsers(user1);
        UserFactory.addUsers(user2);
        UserFactory.addUsers(user3);



        Serializer serializer = new JSONSerializer();
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();

        loginRequestPacket.setVersion(((byte) 1));
        loginRequestPacket.setUserId(user1.getUserId());
        loginRequestPacket.setUsername(user1.getUsername());
        loginRequestPacket.setPassword(user1.getPassword());

        PacketFunction packetCodeC = PacketFunction.INSTANCE;
        ByteBuf byteBuf = packetCodeC.encode(ByteBufAllocator.DEFAULT, loginRequestPacket);
        Packet decodedPacket = packetCodeC.decode(byteBuf);

        // test whether request packet is equal to decoded packet
        Assert.assertArrayEquals(serializer.serialize(loginRequestPacket), serializer.serialize(decodedPacket));

    }
}
