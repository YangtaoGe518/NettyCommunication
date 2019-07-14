package org.yangtao.ge.NettyLogin.Server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import org.yangtao.ge.NettyLogin.Client.CommunicationClientHandler;

public class LoginServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline().addLast(new CommunicationServerHandler());
    }
}
