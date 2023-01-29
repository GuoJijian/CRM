package com.guojijian.crm.commons.pojo;

public class ReturnObject {
    private String code;
    private String message;
    private Object retObject;

    public ReturnObject() {
    }

    public ReturnObject(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public Object getRetObject() {
        return retObject;
    }

    public void setRetObject(Object retObject) {
        this.retObject = retObject;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ReturnObject{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", retObject=" + retObject +
                '}';
    }
}
