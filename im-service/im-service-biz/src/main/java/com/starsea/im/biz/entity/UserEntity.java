package com.starsea.im.biz.entity;

import lombok.*;
import lombok.experimental.Builder;
import org.apache.ibatis.type.Alias;

import java.sql.Timestamp;
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
@Alias("userEntity")
public class UserEntity {
    private int id;

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
