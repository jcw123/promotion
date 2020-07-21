package com.im.sky.netty.test.test2.codec.java;

import com.im.sky.netty.test.test2.codec.Codec;
import com.im.sky.netty.test.test2.codec.Decoder;
import com.im.sky.netty.test.test2.msg.Invocation;
import com.im.sky.netty.test.test2.msg.MessageHeader;
import com.im.sky.netty.test.test2.msg.RequestMessage;
import com.im.sky.netty.test.test2.msg.ResponseMessage;
import com.im.sky.netty.test.test2.protocol.JoyProtocol;
import com.im.sky.netty.test.test2.util.Constants;
import com.im.sky.netty.test.test2.util.ReflectionUtils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Map;

/**
 * @author jiangchangwei
 * @date 2020-6-18 下午 5:17
 **/
public class JavaDecoder implements Decoder {
    @Override
    public Object decode(byte[] data, Class clazz) {
        return null;
    }

    @Override
    public Object decode(byte[] data, String classTypeName) {
        return null;
    }

    private RequestMessage decodeRequest(ObjectInputStream input) throws IOException {
        RequestMessage req = new RequestMessage();
        Invocation inv = new Invocation();
        MessageHeader msgHeader = new MessageHeader();
        req.setInvocationBody(inv);
        req.setMsgHeader(msgHeader);
        String path = readUTF(input);
        req.setClassName(path);
        String methodName = readUTF(input);
        req.setMethodName(methodName);
        try {
            Object[] args;
            Class<?>[] pts;
            String desc = readUTF(input);
            if(desc == null || desc.length() == 0) {
                pts = Codec.EMPTY_CLASS_ARRAY;
                args = Codec.EMPTY_OBJECT_ARRAY;
            }else {
                pts = ReflectionUtils.desc2classArray(desc);
                args = new Object[pts.length];
                for(int i = 0; i < args.length; i++) {
                    args[i] = readObject(input);
                }
            }
            inv.setArgs(args);
            inv.setArgsType(pts);
            Map<String, Object> attachments = (Map<String, Object>)readObject(input);
            String group = (String)attachments.get("group");
            req.setAlias(group);
            inv.setClazzName((String)attachments.get("interface"));
            msgHeader.addHeadKey(Constants.HeadKey.timeout, attachments.get("timeout"));
        }catch (ClassNotFoundException e) {
            throw new IOException("read invocation data failed.", e);
        }
        return req;
    }

    private ResponseMessage decodeResponse(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ResponseMessage res = new ResponseMessage();
        byte status = ois.readByte();
        switch (status) {
            case JoyProtocol.RESPONSE_VALUE:
                res.setResponse(readObject(ois));
            case JoyProtocol.RESPONSE_WITH_EXCEPTION:
                res.setException((Throwable)readObject(ois));
            default:
                break;
        }
        return res;
    }

    private String readUTF(ObjectInputStream ois) throws IOException {
        int len = ois.readInt();
        if(len < 0) {
            return null;
        }
        return ois.readUTF();
    }

    private Object readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        byte b = ois.readByte();
        if(b == 0) {
            return null;
        }
        return ois.readObject();
    }
}
