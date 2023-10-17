package com.example.basic.Member.model;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity @Data
public class Comment {
    @Id
    @GeneratedValue
    int id;
    String content;
    String writer;
    Date creDate;

    @ManyToOne
    Board board;
}
