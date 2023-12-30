package com.learn.boardserver.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {

    private int id;
    private String name;
    private int isAdmin;
    private String contents;
    private Date createTime;
    private int views;
    private int categoryId;
    private int userId;
    private int fileId;
    private Date updateTime;
}
