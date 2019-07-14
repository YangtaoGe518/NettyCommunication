package org.yangtao.ge.NettyLogin.Server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.nio.NioSocketChannel;

public class LoginServerInitializer extends ChannelInitializer<NioSocketChannel> {

    @Override
    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
        // test connection
        //nioSocketChannel.pipeline().addLast(new CommunicationServerHandler());

        nioSocketChannel.pipeline().addLast(new LoginServerHandler());
    }
}
