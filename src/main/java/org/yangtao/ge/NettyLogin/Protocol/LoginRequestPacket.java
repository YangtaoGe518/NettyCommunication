package org.yangtao.ge.NettyLogin.Protocol;

import static org.yangtao.ge.NettyLogin.Protocol.Command.LOGIN_REQUEST;

public class LoginRequestPacket extends Packet{
    private String userId;

    private String username;

    private String password;

    @Override
    public Byte getCommand() {
        return LOGIN_REQUEST;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
