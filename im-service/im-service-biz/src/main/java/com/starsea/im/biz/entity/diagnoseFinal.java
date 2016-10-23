package com.starsea.im.biz.entity;

/**
 * Created by Administrator on 2016/9/30.
 */
public class DiagnoseFinal{
    private int[] scoreYs=new int[4];
    private int[] scoreBz=new int[4];
    private int range;
    private int chidrenNumber;
    private int scoreTotal;
//    private String result;
//    private String[] comment=new String[4];

//    public String getResult() {
//        return result;
//    }
//
//    public void setResult(String result) {
//        this.result = result;
//    }
//
//    public String[] getComment() {
//        return comment;
//    }
//
//    public void setComment(String[] comment) {
//        this.comment = comment;
//    }

    public int[] getScoreYs() {
        return scoreYs;
    }

    public void setScoreYs(int[] scoreYs) {
        this.scoreYs = scoreYs;
    }

    public int[] getScoreBz() {
        return scoreBz;
    }

    public void setScoreBz(int[] scoreBz) {
        this.scoreBz = scoreBz;
    }

    public int getChidrenNumber() {
        return chidrenNumber;
    }

    public void setChidrenNumber(int chidrenNumber) {
        this.chidrenNumber = chidrenNumber;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public int getScoreTotal() {
        return scoreTotal;
    }

    public void setScoreTotal(int scoreTotal) {
        this.scoreTotal = scoreTotal;
    }
}
