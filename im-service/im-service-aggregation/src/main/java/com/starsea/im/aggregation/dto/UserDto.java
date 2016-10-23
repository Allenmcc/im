package com.starsea.im.aggregation.dto;


import lombok.*;
import lombok.experimental.Builder;

import java.util.Date;

/**
 * Created by beigua on 2015/8/5.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserDto {

    private String openId;

    private String name;


    private String myClass;

    private String school;

    private String organization;


    private String createTime;
    private String command;
    private String teacher;
    private String role;
}
