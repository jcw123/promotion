package com.im.sky.netty.test.test2.codec.java;

import com.im.sky.netty.test.test2.codec.Encoder;
import com.im.sky.netty.test.test2.msg.Invocation;
import com.im.sky.netty.test.test2.msg.MessageHeader;
import com.im.sky.netty.test.test2.msg.RequestMessage;
import com.im.sky.netty.test.test2.msg.ResponseMessage;
import com.im.sky.netty.test.test2.protocol.JoyProtocol;
import com.im.sky.netty.test.test2.util.Constants;
import com.im.sky.netty.test.test2.util.ReflectionUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * @author jiangchangwei
 * @date 2020-6-18 下午 5:17
 **/
public class JavaEncoder implements Encoder {

    @Override
    public byte[] encode(Object obj) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(bos);
            if(obj instanceof RequestMessage) {
                RequestMessage requestMessage = (RequestMessage)obj;
                encodeRequest(requestMessage, oos);
            }else if(obj instanceof ResponseMessage) {
                ResponseMessage responseMessage = (ResponseMessage)obj;
                encodeResponse(responseMessage, oos);
            }else {
                writeObject(obj, oos);
            }
            oos.flush();
            return bos.toByteArray();
        }catch (IOException e) {
            if(oos != null) {
                try {
                    oos.close();
                }catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }
        return new byte[0];
    }

    @Override
    public byte[] encode(Object obj, String classTypeName) {
        return this.encode(obj);
    }

    private void writeUTF(String v, ObjectOutputStream oos) throws IOException {
        if(v == null) {
            oos.writeInt(-1);
        }else {
            oos.writeInt(v.length());
            oos.writeUTF(v);
        }
    }

    private void encodeRequest(RequestMessage req, ObjectOutputStream out) throws IOException {
        Invocation inv = req.getInvocationBody();
        MessageHeader msgHeader = req.getMsgHeader();
        String alias = req.getAlias();
        writeUTF(req.getClassName(), out);
        writeUTF(req.getMethodName(), out);
        writeUTF(ReflectionUtils.getDesc(inv.getArgClasses()), out);
        Object[] args = inv.getArgs();
        if(args != null && args.length > 0) {
            for(int i = 0; i < args.length; i++) {
                writeObject(args[i], out);
            }
        }
        inv.addAttachment("group", alias);
        inv.addAttachment("interface", inv.getClazzName());
        inv.addAttachment("timeout", msgHeader.getAttrByKey(Constants.HeadKey.timeout));
        writeObject(inv.getAttachments(), out);
    }

    private void encodeResponse(ResponseMessage res, ObjectOutputStream out) throws IOException {
        Throwable th = res.getException();
        if(th == null) {
            Object ret = res.getResponse();
            if(ret == null) {
                out.writeByte(JoyProtocol.RESPONSE_NULL_VALUE);
            }else {
                out.writeByte(JoyProtocol.RESPONSE_VALUE);
                writeObject(ret, out);
            }
        }else {
            out.writeByte(JoyProtocol.RESPONSE_WITH_EXCEPTION);
            writeObject(th, out);
        }
    }

    private void writeObject(Object obj, ObjectOutputStream oos) throws IOException {
        if(obj == null) {
            oos.writeByte(0);
        }else {
            oos.writeByte(1);
            oos.writeObject(obj);
        }
    }
}
