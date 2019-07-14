package org.yangtao.ge.NettyLogin.Protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import org.yangtao.ge.NettyLogin.Serializer.JSONSerializer;
import org.yangtao.ge.NettyLogin.Serializer.Serializer;

import java.util.HashMap;
import java.util.Map;

import static org.yangtao.ge.NettyLogin.Protocol.Command.LOGIN_REQUEST;
import static org.yangtao.ge.NettyLogin.Protocol.Command.LOGIN_RESPONSE;

/**
 * This class uses a standard structure of customized Protocol
 *  0                   1                   2
 *  0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9
 *  +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *  |MagNum |V|A|C|Length |     Actual Data (with in bound)
 *  +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *
 *  MagNum : Magic Number
 *  V : Version
 *  A : SerializerAlgorithm
 *  C : Command
 *  length: the length of the packet
 */
public class PacketFunction {

    private static final int MAGIC_NUMBER = 0x12345678;
    public static final PacketFunction INSTANCE = new PacketFunction();

    private final Map<Byte, Class<? extends Packet>> packetTypeMap;
    private final Map<Byte, Serializer> serializerMap;

    private PacketFunction() {
        //init for packetType
        packetTypeMap = new HashMap<>();
        packetTypeMap.put(LOGIN_REQUEST, LoginRequestPacket.class);
        packetTypeMap.put(LOGIN_RESPONSE, LoginResponsePacket.class);

        //init for serializer
        serializerMap = new HashMap<>();
        Serializer serializer = new JSONSerializer();
        serializerMap.put(serializer.getSerializerAlgorithm(), serializer);
    }

    public ByteBuf encode(ByteBufAllocator byteBufAllocator, Packet packet){
        //make ByteBuf Object
        ByteBuf byteBuf = byteBufAllocator.ioBuffer();
        //Serialize Object
        byte[] bytes = Serializer.DEFAULT.serialize(packet);

        //encode
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);

        return byteBuf;
    }

    public Packet decode(ByteBuf byteBuf){
        byteBuf.skipBytes(4); //skip magic number
        byteBuf.skipBytes(1); //skip version number

        //SerializerAlgorithm
        byte serializerAlgorithm = byteBuf.readByte();
        //command
        byte command = byteBuf.readByte();
        //length
        int length = byteBuf.readInt();
        //read the actual data
        byte[] data = new byte[length];
        byteBuf.readBytes(data); // actual data is stored in 'data' variable

        Class<? extends Packet> requestType = getRequestType(command); // read command
        Serializer serializer = getSerializer(serializerAlgorithm); // read the corresponding algorithm

        if (requestType != null && serializer != null) {
            return serializer.deserialize(requestType, data);
        }

        return null; // get nothing --- error
    }

    private Serializer getSerializer(byte serializerAlgorithm) {

        return serializerMap.get(serializerAlgorithm);
    }

    private Class<? extends Packet> getRequestType(byte command) {

        return packetTypeMap.get(command);
    }

}
