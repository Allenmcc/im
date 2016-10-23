package com.starsea.im.biz.entity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/10/2.
 */
public class DiagnoseAllChildren {
    private ArrayList<String> childrenName=new ArrayList<String>();
    private ArrayList<String> scoreYs_t=new ArrayList<String>();
    private ArrayList<String> scoreBz_t=new ArrayList<String>();
    private ArrayList<String> scoreYs_p=new ArrayList<String>();
    private ArrayList<String> scoreBz_p=new ArrayList<String>();


    public ArrayList<String> getChildrenName() {
        return childrenName;
    }

    public void setChildrenName(ArrayList<String> childrenName) {
        this.childrenName = childrenName;
    }

    public ArrayList<String> getScoreYs_t() {
        return scoreYs_t;
    }

    public void setScoreYs_t(ArrayList<String> scoreYs_t) {
        this.scoreYs_t = scoreYs_t;
    }

    public ArrayList<String> getScoreBz_t() {
        return scoreBz_t;
    }

    public void setScoreBz_t(ArrayList<String> scoreBz_t) {
        this.scoreBz_t = scoreBz_t;
    }

    public ArrayList<String> getScoreYs_p() {
        return scoreYs_p;
    }

    public void setScoreYs_p(ArrayList<String> scoreYs_p) {
        this.scoreYs_p = scoreYs_p;
    }

    public ArrayList<String> getScoreBz_p() {
        return scoreBz_p;
    }

    public void setScoreBz_p(ArrayList<String> scoreBz_p) {
        this.scoreBz_p = scoreBz_p;
    }
}
