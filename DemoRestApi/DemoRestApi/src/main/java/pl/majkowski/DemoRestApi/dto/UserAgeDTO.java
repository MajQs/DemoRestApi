package pl.majkowski.DemoRestApi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Date;

@Getter
@AllArgsConstructor
public class UserAgeDTO {

    private Long userId;
    private String name;
    private String surename;
    private Integer age;
    private Integer phone_number;
}
