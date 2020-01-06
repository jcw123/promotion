package com.im.sky.netty.zs.msg;

import com.im.sky.netty.zs.util.enums.HeadKey;

public class RequestMessage extends BaseMessage {

    private Invocation invocationBody;

    private long receiveTime;

    private String targetAddress;

    public RequestMessage(boolean initHeader) {
        super(initHeader);
    }

    public RequestMessage() {
        super(true);
    }

    public Invocation getInvocationBody() {

        if(invocationBody == null) return null;

        if(invocationBody.getClazzName() == null){
            invocationBody.setClazzName(this.getClassName());
        }
        if(invocationBody.getMethodName() == null){
            invocationBody.setMethodName(this.getMethodName());
        }
        return invocationBody;
    }

    public Integer getClientTimeout(){
        Integer timeout = (Integer)this.getMsgHeader().getAttrByKey(HeadKey.TIMEOUT);
        if(timeout==null) return null;
        if(timeout<=0) timeout = 1000;
        return timeout;
    }

    public void setInvocationBody(Invocation invocationBody) {
        this.invocationBody = invocationBody;
    }

    public String getClassName(){
        return invocationBody.getClazzName();
    }

    public String getMethodName(){
        return invocationBody.getMethodName();
    }

    public String getAlias(){
        return invocationBody.getAlias();
    }

    public void setClassName(String className){
        invocationBody.setClazzName(className);
    }

    public void setMethodName(String methodName){
        invocationBody.setMethodName(methodName);

    }

    public void setAlias(String alias){
        invocationBody.setAlias(alias);
    }

    public long getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(long receiveTime) {
        this.receiveTime = receiveTime;
    }

}
