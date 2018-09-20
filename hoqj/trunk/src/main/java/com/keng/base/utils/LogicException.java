/**
 * 
 */
package com.keng.base.utils;
/**
 * @Copyright: 禁止转发.
 * @Version: $Id$
 */
public class LogicException extends RuntimeException {
    /**
     * 创建一个新的实例LogicException.
     * @param string
     */
    public LogicException(String message) {
        super(message);
    }

    /**
     * 创建一个新的实例LogicException.
     * @param string
     */
    public LogicException() {
        super();
    }
    /**
     * 序列化ID
     */
    private static final long serialVersionUID = 1L;
}
