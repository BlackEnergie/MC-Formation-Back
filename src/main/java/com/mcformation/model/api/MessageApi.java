package com.mcformation.model.api;

import com.mcformation.utils.JsonUtils;

public class MessageApi {

    public MessageApi(){
    }

    public MessageApi(int code, String message){
        this.code = code;
        this.message = message;
    }

    private int code;
    private String message;
    private String data;

    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(Object data) {
        if (!(data instanceof String)) {
            this.data = JsonUtils.objectToJson(data);
        } else {
            this.data = (String) data;
        }
    }

}
