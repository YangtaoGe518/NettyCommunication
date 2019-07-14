package org.yangtao.ge.NettyLogin.Protocol;

import com.alibaba.fastjson.annotation.JSONField;

public abstract class Packet {
    //Version
    @JSONField(deserialize = false, serialize = false)
    private Byte version = 1; // version for extensions (default is one)


    //Command
    @JSONField(serialize = false)
    public abstract Byte getCommand();

    public Byte getVersion() {
        return version;
    }

    public void setVersion(Byte version) {
        this.version = version;
    }
}
