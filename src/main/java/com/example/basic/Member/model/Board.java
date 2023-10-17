package com.example.basic.Member.model;


import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Board {
    @Id
    @GeneratedValue
    int id;
    String title;
    String content;
    String writer;

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    List<Comment> comments = new ArrayList<>();



}
