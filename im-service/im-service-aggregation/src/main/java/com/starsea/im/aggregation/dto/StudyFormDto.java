package com.starsea.im.aggregation.dto;

import lombok.*;
import lombok.experimental.Builder;


/**
 * Created by danny on 16/4/27.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class StudyFormDto {
    private String name;
    private String teacher;
    private String evaluationTime;
    private int now_score[];
    private int scoreTotal;
    private String scoreYs;
    private String scoreBz;
    private String childOpenId;

    public String getScoreYs() {
        return scoreYs;
    }

    public void setScoreYs(String scoreYs) {
        this.scoreYs = scoreYs;
    }

    public String getScoreBz() {
        return scoreBz;
    }

    public void setScoreBz(String scoreBz) {
        this.scoreBz = scoreBz;
    }

    public String getChildOpenId() {
        return childOpenId;
    }

    public void setChildOpenId(String childOpenId) {
        this.childOpenId = childOpenId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getEvaluationTime() {
        return evaluationTime;
    }

    public void setEvaluationTime(String evaluationTime) {
        this.evaluationTime = evaluationTime;
    }

    public int[] getNow_score() {
        return now_score;
    }

    public void setNow_score(int[] now_score) {
        this.now_score = now_score;
    }

    public int getScoreTotal() {
        return scoreTotal;
    }

    public void setScoreTotal(int scoreTotal) {
        this.scoreTotal = scoreTotal;
    }
}
