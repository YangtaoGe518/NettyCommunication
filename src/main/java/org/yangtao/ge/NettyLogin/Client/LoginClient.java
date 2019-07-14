package org.yangtao.ge.NettyLogin.Client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class LoginClient {

    private static final int MAX_RETRY = 5; // 5 retries are applied maximum

    public static void main(String[] args){
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap
                .group(workerGroup)
                .channel(NioSocketChannel.class)
                .handler(new LoginClientInitializer());

        //try connect to the server
        connect(bootstrap,"localhost", 1024, MAX_RETRY);
    }

    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("Connect successfully!");
            } else if (retry == 0) {
                System.err.println("Run out of retry times, abandon retry process!");
            } else {
                // retry times
                int order = (MAX_RETRY - retry) + 1;
                // time gap
                int delay = 1 << order;
                System.err.println(new Date() + ": Connection failure，try " + order + " time(s) retry");
                bootstrap.config().group().schedule(() -> connect(bootstrap, host, port, retry - 1), delay, TimeUnit
                        .SECONDS);
            }
        });
    }
}
