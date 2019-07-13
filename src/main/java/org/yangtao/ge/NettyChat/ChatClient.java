package org.yangtao.ge.NettyChat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ChatClient {

    private final String host;
    private final int port;
    private boolean stop = false;

    public ChatClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void run() throws Exception{
        EventLoopGroup worker= new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap()
                .group(worker)
                .channel(NioSocketChannel.class)
                .handler(new ChatClientInitializer());
        try{
            Channel channel = bootstrap.connect(host, port).sync().channel();

            while (true) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                String input = reader.readLine();
                if (input != null) {
                    if ("quit".equals(input)) {
                        System.exit(1);
                    }
                    channel.writeAndFlush(input);
                    //System.out.println(input);
                }
            }
        }finally {
            worker.shutdownGracefully();
        }
    }

    public boolean isStop() {
        return stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }

    public static void main(String[] args) throws Exception{
        new ChatClient("localhost", 9999).run();
    }
}
