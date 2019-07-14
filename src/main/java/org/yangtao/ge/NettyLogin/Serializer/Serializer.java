package org.yangtao.ge.NettyLogin.Serializer;

public interface Serializer {
    Serializer DEFAULT = new JSONSerializer();
    /**
     * SerializerAlgorithm
     * @return the Algorithm used for serializing
     */
    byte getSerializerAlgorithm();

    /**
     * convert from objects to Bytes
     */
    byte[] serialize(Object object);

    /**
     * convert from Bytes back to objects
     */
    <T> T deserialize(Class<T> tClass, byte[] bytes);
}
