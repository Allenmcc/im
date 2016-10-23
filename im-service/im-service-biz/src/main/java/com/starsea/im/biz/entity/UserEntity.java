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


    private String myClass;

    private String school;

    private String organization;


    private String createTime;
    private String command;
    private String teacher;
    private String role;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getMyClass() {
        return myClass;
    }

    public void setMyClass(String myClass) {
        this.myClass = myClass;
    }
}
