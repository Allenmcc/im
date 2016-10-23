package com.starsea.im.aggregation.dto;

import lombok.*;
import lombok.experimental.Builder;

import java.util.Date;

/**
 * Created by danny on 16/4/24.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class WatchFormDto {
    private String name;
    private String teacher;
    private String evaluationTime;
    private int now_score[];
    private String now_comment[];

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEvaluationTime() {
        return evaluationTime;
    }

    public void setEvaluationTime(String evaluationTime) {
        this.evaluationTime = evaluationTime;
    }


    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public int[] getNow_score() {
        return now_score;
    }

    public void setNow_score(int[] now_score) {
        this.now_score = now_score;
    }

    public String[] getNow_comment() {
        return now_comment;
    }

    public void setNow_comment(String[] now_comment) {
        this.now_comment = now_comment;
    }
}
