package com.psl.employeeinfo.exception;

public class CustomException {
    private int resp_code = 200;
    private String resp_msg = "OK";

    public CustomException(){}

    public CustomException(int resp_code, String resp_msg){
        this.resp_code=resp_code;
        this.resp_msg=resp_msg;
    }

    public int getResp_code() {
        return resp_code;
    }

    public void setResp_code(int resp_code) {
        this.resp_code = resp_code;
    }

    public String getResp_msg() {
        return resp_msg;
    }

    public void setResp_msg(String resp_msg) {
        this.resp_msg = resp_msg;
    }
}
