package com.learn.boardserver.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) // 런타임시에 동작하기 위해 라이프 사이클을 지정
@Target(ElementType.METHOD)
public @interface LoginCheck {

    public static enum UserType{
        USER, ADMIN
    }

    UserType type();
}
