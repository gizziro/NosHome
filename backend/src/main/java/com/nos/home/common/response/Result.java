package com.nos.home.common.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.nos.home.common.response.code.CommonErrorCode;
import com.nos.home.common.response.code.IErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Result {
    private String          status;
    private String          code;
    private String          message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String          description;

    public static Result of(String status, String code, String message, String description){
        return Result.builder()
                .status(status)
                .code(code)
                .message(message)
                .description(description).build();
    }

    public static Result OK(){
        return Result.builder()
                .status("200")
                .code(CommonErrorCode.OK.getCode())
                .message(CommonErrorCode.OK.getMessage())
                .build();
    }

    public static Result ERROR(IErrorCode errorCodeIfs){
        return Result.builder()
                .status(errorCodeIfs.getStatus())
                .code(errorCodeIfs.getCode())
                .message(errorCodeIfs.getMessage())
                .description(errorCodeIfs.getDescription())
                .build();
    }

    public static Result ERROR(IErrorCode errorCodeIfs, Throwable tx){
        return Result.builder()
                .status(errorCodeIfs.getStatus())
                .code(errorCodeIfs.getCode())
                .message(errorCodeIfs.getMessage())
                .description(tx.getLocalizedMessage())
                .build();
    }

    public static Result ERROR(IErrorCode errorCodeIfs, String description){
        return Result.builder()
                .status(errorCodeIfs.getStatus())
                .code(errorCodeIfs.getCode())
                .message(errorCodeIfs.getDescription())
                .description(description)
                .build();
    }
}
