package com.nos.home.common.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.nos.home.common.response.code.IErrorCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse<T> {
    private Result result;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T body;

    public static <T> ApiResponse<T> OK(){
        var api         = new ApiResponse<T>();
        api.result      = Result.OK();
        return api;
    }

    public static <T> ApiResponse<T> OK(T data){
        var api = new ApiResponse<T>();
        api.result = Result.OK();
        api.body = data;
        return api;
    }

    public static <T> ApiResponse<T> ERROR(Result result){
        var api = new ApiResponse<T>();
        api.result = result;
        return api;
    }

    public static <T> ApiResponse<T> ERROR(IErrorCode errorCodeIfs){
        var api = new ApiResponse<T>();
        api.result = Result.ERROR(errorCodeIfs);
        return api;
    }

    public static <T> ApiResponse<T> ERROR(IErrorCode errorCodeIfs, Throwable tx){
        var api = new ApiResponse<T>();
        api.result = Result.ERROR(errorCodeIfs, tx);
        return api;
    }

    public static <T> ApiResponse<T> ERROR(IErrorCode errorCodeIfs, String description){
        var api = new ApiResponse<T>();
        api.result = Result.ERROR(errorCodeIfs, description);
        return api;
    }
}