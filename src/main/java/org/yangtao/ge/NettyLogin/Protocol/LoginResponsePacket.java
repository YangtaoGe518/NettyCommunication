package org.yangtao.ge.NettyLogin.Protocol;

import static org.yangtao.ge.NettyLogin.Protocol.Command.LOGIN_RESPONSE;

public class LoginResponsePacket extends Packet {
    private boolean isSuccess;

    private String reason;

    private String userId;

    @Override
    public Byte getCommand() {
        return LOGIN_RESPONSE;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
