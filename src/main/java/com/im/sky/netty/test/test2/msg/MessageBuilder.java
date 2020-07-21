package com.im.sky.netty.test.test2.msg;

import com.im.sky.netty.test.test2.util.ClassTypeUtils;
import com.im.sky.netty.test.test2.util.Constants;

/**
 * @author jiangchangwei
 * @date 2020-6-24 下午 5:02
 **/
public class MessageBuilder {
    public static RequestMessage buildRequest(Class clazz, String methodName, Class[] methodParamTypes, Object[] args) {
        Invocation invocationBody = new Invocation();
        invocationBody.setArgs(args == null ? new Object[0] : args);
        invocationBody.setArgsType(methodParamTypes);
        RequestMessage requestMessage = new RequestMessage();
        requestMessage.setInvocationBody(invocationBody);
        requestMessage.setClassName(ClassTypeUtils.getTypeStr(clazz));
        requestMessage.setMethodName(methodName);

        requestMessage.getMsgHeader().setMsgType(Constants.REQUEST_MSG);
        return requestMessage;

    }

    public static ResponseMessage buildResponse(RequestMessage request) {
        ResponseMessage response = new ResponseMessage(false);
        response.setMsgHeader(request.getMsgHeader().clone());
        response.getMsgHeader().setMsgType(Constants.RESPONSE_MSG);
        return response;
    }

    public static ResponseMessage buildResponse(MessageHeader header) {
        ResponseMessage response = new ResponseMessage(false);
        response.setMsgHeader(header.clone());
        response.getMsgHeader().setMsgType(Constants.RESPONSE_MSG);
        return response;
    }
}
