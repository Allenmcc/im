package com.starsea.im.biz.entity;

/**
 * Created by Administrator on 2016/10/23.
 */
public class TeacherRegisterEntity {
    private int id;
    private String openId;
    private String teacherName;
    private String teacherSchool;
    private String teacherCommand;
    private String createTime;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherSchool() {
        return teacherSchool;
    }

    public void setTeacherSchool(String teacherSchool) {
        this.teacherSchool = teacherSchool;
    }

    public String getTeacherCommand() {
        return teacherCommand;
    }

    public void setTeacherCommand(String teacherCommand) {
        this.teacherCommand = teacherCommand;
    }
}
