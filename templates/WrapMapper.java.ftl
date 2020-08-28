
package com.cfyy.common;

import com.cfyy.common.ServiceResult;
import com.baomidou.mybatisplus.core.toolkit.StringUtils ;


/** The class Wrap mapper. */
public class WrapMapper {

  /** Instantiates a new wrap mapper. */
  private WrapMapper() {}

  /**
   * Wrap.
   *
   * @param <E> the element type
   * @param status the status
   * @param message the message
   * @param o the o
   * @return the wrapper
   */
  public static <E> ServiceResult<E> wrap(int status, String message, E o) {

    return wrap(status, message, o, "");
  }

  /**
   * Wrap.
   *
   * @param attache attache msg
   * @param <E> the element type
   * @param status the status
   * @param message the message
   * @param o the o
   * @return the wrapper
   */
  private static <E> ServiceResult<E> wrap(int status, String message, E o, String attache) {

    return new ServiceResult<>(status, message, o, attache);
  }

  /**
   * Wrap.
   *
   * @param <E> the element type
   * @param status the status
   * @param message the message
   * @return the wrapper
   */
  public static <E> ServiceResult<E> wrap(int status, String message) {

    return wrap(status, message, null, "");
  }

  /**
   * Wrap.
   *
   * @param <E> the element type
   * @param status the status
   * @return the wrapper
   */
  public static <E> ServiceResult<E> wrap(int status) {

    return wrap(status, null);
  }

  /**
   * Wrap.
   *
   * @param <E> the element type
   * @param e the e
   * @return the wrapper
   */
  public static <E> ServiceResult<E> wrap(Exception e) {

    return new ServiceResult<>(CommonConstants.SERVER_ERROR_CODE, e.getMessage());
  }

  /**
   * Un wrapper.
   *
   * @param <E> the element type
   * @param wrapper the wrapper
   * @return the e
   */
  public static <E> E unWrap(ServiceResult<E> wrapper) {

    return wrapper.getData();
  }

  /**
   * Wrap ERROR. status=100
   *
   * @param <E> the element type
   * @return the wrapper
   */
  public static <E> ServiceResult<E> illegalArgument() {

    return wrap(CommonConstants.ILLEGAL_ARGUMENT_ERROR_CODE_, CommonConstants.ILLEGAL_ARGUMENT_ERROR_MESSAGE);
  }

  /**
   * Wrap ERROR. status=500
   *
   * @param <E> the element type
   * @return the wrapper
   */
  public static <E> ServiceResult<E> error() {

    return wrap(CommonConstants.SERVER_ERROR_CODE, CommonConstants.SERVER_ERROR_MSG);
  }

  /**
   * Error wrapper.
   *
   * @param <E> the type parameter
   * @param message the message
   * @return the wrapper
   */
  public static <E> ServiceResult<E> error(String message) {

    return wrap(CommonConstants.SERVER_ERROR_CODE, StringUtils.isBlank(message) ? CommonConstants.SERVER_ERROR_MSG : message);
  }

  /**
   * Error wrapper.
   *
   * @param <E> the type parameter
   * @param message the message
   * @return the wrapper
   */
  public static <E> ServiceResult<E> error(int status, String message) {

    return wrap(status, StringUtils.isBlank(message) ? CommonConstants.SERVER_ERROR_MSG : message);
  }

  /**
   * Error wrapper.
   *
   * @param <E> the type parameter
   * @param message the message
   * @return the wrapper
   */
  public static <E> ServiceResult<E> error(int status, String message, String attache) {

    return wrap(status, StringUtils.isBlank(message) ?CommonConstants.SERVER_ERROR_MSG : message, null, attache);
  }

  /**
   * Wrap SUCCESS. status=200
   *
   * @param <E> the element type
   * @return the wrapper
   */
  public static <E> ServiceResult<E> ok() {

    return new ServiceResult();
  }

  /**
   * Wrap SUCCESS. status=200
   *
   * @param <E> the element type
   * @return the wrapper
   */
  public static <E> ServiceResult<E> ok(int status, E o, String msg) {

    return new ServiceResult(status, msg, o);
  }

  /**
   * Ok wrapper.
   *
   * @param <E> the type parameter
   * @param o the o
   * @return the wrapper
   */
  public static <E> ServiceResult<E> ok(E o) {

    return new ServiceResult<>(CommonConstants.SUCCESS_CODE, CommonConstants.SUCCESS_MESSAGE, o);
  }
  /**
   * Ok wrapper.
   *
   * @param <E> the type parameter
   * @param o the o
   * @return the wrapper
   */
  public static <E> ServiceResult<E> ok(E o, String message) {

    return new ServiceResult<>(CommonConstants.SUCCESS_CODE, message, o);
  }
}
