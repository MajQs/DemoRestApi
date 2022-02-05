package pl.majkowski.DemoRestApi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Date;

@Getter
@AllArgsConstructor
public class UserDto {

    private Long userId;
    private String name;
    private String surename;
    private Date birth_day;
    private Integer phone_number;
}
