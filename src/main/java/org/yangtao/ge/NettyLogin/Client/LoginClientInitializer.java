package org.yangtao.ge.NettyLogin.Client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class LoginClientInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        // test connection
        //socketChannel.pipeline().addLast(new CommunicationClientHandler());

        socketChannel.pipeline().addLast(new LoginClientHandler());
    }
}
