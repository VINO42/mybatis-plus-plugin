
package com.cfyy.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 服务统一包装类
 *
 * @param <T>
 */
@Data
@Builder
@ToString
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServiceResult
    <T> implements Serializable {

    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    /**
     * 编号.
     */
    private int status;

    /**
     * 信息.
     */
    private String message;

    /**
     * 结果数据
     */
    private T data;

    /**
     * 附加信息 如 exception
     */
    private String attache;

    /**
     * traceId
     */
    private String requestId;

    public ServiceResult() {

        this.startTime = LocalDateTime.now();
    }

    /**
     * Instantiates a new wrapper.
     *
     * @param status  the code
     * @param message the message
     */
    public ServiceResult(int status, String message) {

        this(status, message, null);
    }

    /**
     * Instantiates a new wrapper.
     *
     * @param status  the code
     * @param message the message
     * @param result  the result
     */
    public ServiceResult(int status, String message, T result) {

        super();
        this.startTime = LocalDateTime.now();
        this.code(status).message(message).result(result);
    }

    /**
     * Instantiates a new wrapper.
     *
     * @param status  the code
     * @param message the message
     * @param result  the result
     */
    public ServiceResult(int status, String message, T result, String attache) {

        super();
        this.startTime = LocalDateTime.now();
        this.code(status).message(message).result(result);
        this.attache = attache;
    }

    /**
     * Sets the 编号 , 返回自身的引用.
     *
     * @param code the new 编号
     * @return the wrapper
     */
    private ServiceResult
        <T> code(int code) {

        this.setStatus(code);
        this.startTime = LocalDateTime.now();
        return this;
    }

    /**
     * Sets the 信息 , 返回自身的引用.
     *
     * @param message the new 信息
     * @return the wrapper
     */
    private ServiceResult
        <T> message(String message) {

        this.setMessage(message);
        this.startTime = LocalDateTime.now();
        return this;
    }

    /**
     * Sets the 结果数据 , 返回自身的引用.
     *
     * @param data the new 结果数据
     * @return the wrapper
     */
    public ServiceResult
        <T> result(T data) {

        this.startTime = LocalDateTime.now();
        this.setData(data);
        return this;
    }

    /**
     * 判断是否成功： 依据 Wrapper.SUCCESS_CODE == this.code
     *
     * @return code =200,true;否则 false.
     */
    @JsonIgnore
    public boolean success() {

        return SUCCESS_CODE == this.status;
    }

    /**
     * 判断是否成功： 依据 Wrapper.SUCCESS_CODE != this.code
     *
     * @return code !=200,true;否则 false.
     */
    @JsonIgnore
    public boolean error() {

        return !success();
    }
}
