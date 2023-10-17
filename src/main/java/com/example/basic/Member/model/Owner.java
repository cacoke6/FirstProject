package com.example.basic.Member.model;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Entity
@Data
public class Owner {
    @Id @GeneratedValue
    int id;


    String name;

    String pwd;

    @Email
    String email;

    String phone;

    Date date;



}