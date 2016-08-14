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

    private int age;

    private String sex;

    private String myClass;

    private String school;

    private String organization;

    private String evaluationPerson;

    private Date createTime;
}
