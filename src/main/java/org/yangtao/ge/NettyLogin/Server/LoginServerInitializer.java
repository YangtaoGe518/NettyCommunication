package org.yangtao.ge.NettyLogin.Server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class LoginServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        // test connection
        //socketChannel.pipeline().addLast(new CommunicationServerHandler());

        socketChannel.pipeline().addLast(new LoginServerHandler());
    }
}
