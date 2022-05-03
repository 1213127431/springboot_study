package com.tj.model.response;

import lombok.Data;

/**
 * 通用接口响应类
 *
 * @author tangjie
 * @version 0.0.1
 * @since 2022/5/2 21:22
 **/
@Data
public class Response<T> {

    /**
     * 响应结果 true|成功 false|失败
     */
    private Boolean success = true;

    /**
     * 状态码 0|成功 其他|失败
     */
    private String code = "0";

    /**
     * 状态信息，若响应失败，返回失败原因
     */
    private String message = "ok";

    /**
     * 响应数据
     */
    private T data;

    /**
     * 构造成功响应
     *
     * @param data 响应数据
     * @param <T>  响应数据类型
     * @return 响应
     */
    public static <T> Response<T> buildSuccessResponse(T data) {
        Response<T> response = new Response<>();
        response.setData(data);
        return response;
    }

    /**
     * 构造失败响应
     *
     * @param code    错误码
     * @param message 错误信息
     * @param <T>     响应数据类型
     * @return 响应
     */
    public static <T> Response<T> buildErrorResponse(String code, String message) {
        Response<T> response = new Response<>();
        response.setSuccess(false);
        response.setCode(code);
        response.setMessage(message);
        return response;
    }
}
