package com.learn.boardserver.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
public class BoardServerException extends RuntimeException{
    HttpStatus code;
    String msg;
}
