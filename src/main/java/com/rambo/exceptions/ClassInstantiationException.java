package com.rambo.exceptions;

/**
 * 代表实例化类时失败的异常。
 */
public class ClassInstantiationException extends RuntimeException {
    private static final long serialVersionUID = 7146764761693834500L;

    public ClassInstantiationException() {
        super();
    }

    public ClassInstantiationException(String message) {
        super(message);
    }

    public ClassInstantiationException(Throwable cause) {
        super(cause);
    }

    public ClassInstantiationException(String message, Throwable cause) {
        super(message, cause);
    }
}
