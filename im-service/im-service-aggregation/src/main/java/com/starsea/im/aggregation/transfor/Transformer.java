package com.starsea.im.aggregation.transfor;

import com.starsea.im.aggregation.dto.*;
import com.starsea.im.biz.entity.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by danny on 16/4/21.
 */
public class Transformer {

    /**
     * 日期格式字符串转换成时间戳
     * @param timestamp
     * @param format 如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String timeStampToDate(String timestamp,String format) {
        if(timestamp == null || timestamp.isEmpty() || timestamp.equals("null")){
            return "";
        }
        if(format == null || format.isEmpty()) format = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(timestamp)));
    }


    public static StudyForm enrichStudyForm(String name, String teacher, String evaluationTime, String openId, int[] hc,String childOpenId) throws ParseException {

        StudyForm studyForm = new StudyForm();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(evaluationTime);
        studyForm.setName(name);
        studyForm.setTeacher(teacher);
        studyForm.setEvaluationTime(date);
        studyForm.setOpenId(openId);
        studyForm.setQuestion1(hc[0]);
        studyForm.setQuestion2(hc[1]);
        studyForm.setQuestion3(hc[2]);
        studyForm.setQuestion4(hc[3]);
        studyForm.setQuestion5(hc[4]);
        studyForm.setQuestion6(hc[5]);
        studyForm.setQuestion7(hc[6]);
        studyForm.setQuestion8(hc[7]);
        studyForm.setQuestion9(hc[8]);
        studyForm.setQuestion10(hc[9]);
        studyForm.setQuestion11(hc[10]);
        studyForm.setQuestion12(hc[11]);
        studyForm.setQuestion13(hc[12]);
        studyForm.setQuestion14(hc[13]);
        studyForm.setQuestion15(hc[14]);
        studyForm.setQuestion16(hc[15]);
        studyForm.setQuestion17(hc[16]);
        studyForm.setQuestion18(hc[17]);
        studyForm.setChildOpenId(childOpenId);
        return studyForm;

    }

    public static AdvantageForm enrichAdvantage(String school,String myClass,String name, String sex, int age, String openId,int[] now_score) throws ParseException {

        AdvantageForm advantageForm = new AdvantageForm();
        advantageForm.setSchool(school);
        advantageForm.setBanji(myClass);
        advantageForm.setName(name);
        advantageForm.setSex(sex);
        advantageForm.setAge(age);
        advantageForm.setOpenId(openId);
        advantageForm.setQuestion1(now_score[0]);
        advantageForm.setQuestion2(now_score[1]);
        advantageForm.setQuestion3(now_score[2]);
        advantageForm.setQuestion4(now_score[3]);
        advantageForm.setQuestion5(now_score[4]);
        advantageForm.setQuestion6(now_score[5]);
        advantageForm.setQuestion7(now_score[6]);
        advantageForm.setQuestion8(now_score[7]);
        advantageForm.setQuestion9(now_score[8]);
        advantageForm.setQuestion10(now_score[9]);
        advantageForm.setQuestion11(now_score[10]);
        advantageForm.setQuestion12(now_score[11]);
        advantageForm.setQuestion13(now_score[12]);
        advantageForm.setQuestion14(now_score[13]);
        advantageForm.setQuestion15(now_score[14]);
        advantageForm.setQuestion16(now_score[15]);
        advantageForm.setQuestion17(now_score[16]);
        advantageForm.setQuestion18(now_score[17]);
        advantageForm.setQuestion19(now_score[18]);
        advantageForm.setQuestion20(now_score[19]);
        advantageForm.setQuestion21(now_score[20]);
        advantageForm.setQuestion22(now_score[21]);
        advantageForm.setQuestion23(now_score[22]);
        advantageForm.setQuestion24(now_score[23]);
        advantageForm.setQuestion25(now_score[24]);
        return advantageForm;

    }

    public static WatchForm enrichWatchForm(String name, String teacher, String evaluationTime,String openId, int[] now_score,String[] now_comment) throws ParseException {

        WatchForm watchForm = new WatchForm();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(evaluationTime);
        watchForm.setName(name);
        watchForm.setTeacher(teacher);
        watchForm.setEvaluationTime(date);
        watchForm.setOpenId(openId);
        watchForm.setQuestion1(now_score[0]);
        watchForm.setQuestion2(now_score[1]);
        watchForm.setQuestion3(now_score[2]);
        watchForm.setQuestion4(now_score[3]);
        watchForm.setQuestion5(now_score[4]);
        watchForm.setQuestion6(now_score[5]);
        watchForm.setQuestion7(now_score[6]);
        watchForm.setQuestion8(now_score[7]);
        watchForm.setQuestion9(now_score[8]);
        watchForm.setQuestion10(now_score[9]);
        watchForm.setQuestion11(now_score[10]);
        watchForm.setQuestion12(now_score[11]);
        watchForm.setQuestion13(now_score[12]);
        watchForm.setQuestion14(now_score[13]);
        watchForm.setQuestion15(now_score[14]);
        watchForm.setQuestion16(now_score[15]);
        watchForm.setQuestion17(now_score[16]);
        watchForm.setQuestion18(now_score[17]);
        watchForm.setQuestion19(now_score[18]);
        watchForm.setQuestion20(now_score[19]);
        watchForm.setQuestion21(now_score[20]);
        watchForm.setQuestion22(now_score[21]);
        watchForm.setQuestion23(now_score[22]);
        watchForm.setQuestion24(now_score[23]);
        watchForm.setQuestion25(now_score[24]);
        watchForm.setQuestion26(now_score[25]);
        watchForm.setQuestion27(now_score[26]);
        watchForm.setQuestion28(now_score[27]);
        watchForm.setQuestion29(now_score[28]);
        watchForm.setQuestion30(now_score[29]);
        watchForm.setQuestion31(now_score[30]);
        watchForm.setQuestion32(now_score[31]);
        watchForm.setQuestion33(now_score[32]);
        watchForm.setQuestion34(now_score[33]);
        watchForm.setQuestion35(now_score[34]);
        watchForm.setQuestion36(now_score[35]);
        watchForm.setQuestion37(now_score[36]);
        watchForm.setQuestion38(now_score[37]);
        watchForm.setQuestion39(now_score[38]);
        watchForm.setQuestion40(now_score[39]);
        watchForm.setQuestion41(now_score[40]);
        watchForm.setQuestion42(now_score[41]);
        watchForm.setQuestion43(now_score[42]);
        watchForm.setQuestion44(now_score[43]);
        watchForm.setQuestion45(now_score[44]);
        watchForm.setQuestion46(now_score[45]);

        watchForm.setComment1(now_comment[0]);
        watchForm.setComment2(now_comment[1]);
        watchForm.setComment3(now_comment[2]);
        watchForm.setComment4(now_comment[3]);
        watchForm.setComment5(now_comment[4]);
        watchForm.setComment6(now_comment[5]);
        watchForm.setComment7(now_comment[6]);


        return watchForm;

    }

    public static StudyFormDto convertStudyFormDtoFromStudyForm(StudyForm studyForm){

        int[] now_score = new int[18];
        int total = 0;
        StudyFormDto studyFormDto = new StudyFormDto();
        studyFormDto.setName(studyForm.getName());
        studyFormDto.setTeacher(studyForm.getTeacher());
        studyFormDto.setEvaluationTime(timeStampToDate(String.valueOf(studyForm.getEvaluationTime().getTime()), null));
        studyFormDto.setScoreYs(studyForm.getScoreYs());
        studyFormDto.setScoreBz(studyForm.getScoreBz());
        studyFormDto.setChildOpenId(studyForm.getChildOpenId());
        now_score[0] = studyForm.getQuestion1();
        now_score[1] = studyForm.getQuestion2();
        now_score[2] = studyForm.getQuestion3();
        now_score[3] = studyForm.getQuestion4();
        now_score[4] = studyForm.getQuestion5();
        now_score[5] = studyForm.getQuestion6();
        now_score[6] = studyForm.getQuestion7();
        now_score[7] = studyForm.getQuestion8();
        now_score[8] = studyForm.getQuestion9();
        now_score[9] = studyForm.getQuestion10();
        now_score[10] = studyForm.getQuestion11();
        now_score[11] = studyForm.getQuestion12();
        now_score[12] = studyForm.getQuestion13();
        now_score[13] = studyForm.getQuestion14();
        now_score[14] = studyForm.getQuestion15();
        now_score[15] = studyForm.getQuestion16();
        now_score[16] = studyForm.getQuestion17();
        now_score[17] = studyForm.getQuestion18();
        studyFormDto.setNow_score(now_score);
        for (int i = 0; i < now_score.length; i++) {
            total += now_score[i];
        }
        studyFormDto.setScoreTotal(total);
        return studyFormDto;

    }

    public static WatchFormDto convertWatchFormDtoFromWatchForm(WatchForm watchForm){

        int[] now_score = new int[46];
        String[] now_comment = new String[7];
        WatchFormDto watchFormDto = new WatchFormDto();
        watchFormDto.setName(watchForm.getName());
        watchFormDto.setTeacher(watchForm.getTeacher());
        watchFormDto.setEvaluationTime(timeStampToDate(String.valueOf(watchForm.getEvaluationTime().getTime()), null));
        now_score[0]= watchForm.getQuestion1();
        now_score[1]= watchForm.getQuestion2();
        now_score[2]= watchForm.getQuestion3();
        now_score[3]= watchForm.getQuestion4();
        now_score[4]= watchForm.getQuestion5();
        now_score[5]= watchForm.getQuestion6();
        now_score[6]= watchForm.getQuestion7();
        now_score[7]= watchForm.getQuestion8();
        now_score[8]= watchForm.getQuestion9();
        now_score[9]= watchForm.getQuestion10();
        now_score[10]= watchForm.getQuestion11();
        now_score[11]= watchForm.getQuestion12();
        now_score[12]= watchForm.getQuestion13();
        now_score[13]= watchForm.getQuestion14();
        now_score[14]= watchForm.getQuestion15();
        now_score[15]= watchForm.getQuestion16();
        now_score[16]= watchForm.getQuestion17();
        now_score[17]= watchForm.getQuestion18();
        now_score[18]= watchForm.getQuestion19();
        now_score[19]= watchForm.getQuestion20();
        now_score[20]= watchForm.getQuestion21();
        now_score[21]= watchForm.getQuestion22();
        now_score[22]= watchForm.getQuestion23();
        now_score[23]= watchForm.getQuestion24();
        now_score[24]= watchForm.getQuestion25();
        now_score[25]= watchForm.getQuestion26();
        now_score[26]= watchForm.getQuestion27();
        now_score[27]= watchForm.getQuestion28();
        now_score[28]= watchForm.getQuestion29();
        now_score[29]= watchForm.getQuestion30();
        now_score[30]= watchForm.getQuestion31();
        now_score[31]= watchForm.getQuestion32();
        now_score[32]= watchForm.getQuestion33();
        now_score[33]= watchForm.getQuestion34();
        now_score[34]= watchForm.getQuestion35();
        now_score[35]= watchForm.getQuestion36();
        now_score[36]= watchForm.getQuestion37();
        now_score[37]= watchForm.getQuestion38();
        now_score[38]= watchForm.getQuestion39();
        now_score[39]= watchForm.getQuestion40();
        now_score[40]= watchForm.getQuestion41();
        now_score[41]= watchForm.getQuestion42();
        now_score[42]= watchForm.getQuestion43();
        now_score[43]= watchForm.getQuestion44();
        now_score[44]= watchForm.getQuestion45();
        now_score[45]= watchForm.getQuestion46();


        now_comment[0]= watchForm.getComment1();
        now_comment[1]= watchForm.getComment2();
        now_comment[2]= watchForm.getComment3();
        now_comment[3]= watchForm.getComment4();
        now_comment[4]= watchForm.getComment5();
        now_comment[5]= watchForm.getComment6();
        now_comment[6]= watchForm.getComment7();




        watchFormDto.setNow_score(now_score);
        watchFormDto.setNow_comment(now_comment);

        return watchFormDto;

    }


    public static List<Integer> converListFromIntArray(int [] arrays){

        List<Integer> IntegerList = new ArrayList<Integer>();

        for(int array:arrays){
            Integer temp = array;
            IntegerList.add(temp);
        }

        return IntegerList;

    }


    public static AdvantageFormDto converAdvantageFormDtoFromAdvantageForm(AdvantageForm advantageForm){

        int[] now_score = new int[25];
        int score = 0;
        AdvantageFormDto advantageFormDto = new AdvantageFormDto();
        advantageFormDto.setName(advantageForm.getName());
        advantageFormDto.setAge(advantageForm.getAge());
        advantageFormDto.setSex(advantageForm.getSex());
        advantageFormDto.setBanji(advantageForm.getBanji());
        advantageFormDto.setSchool(advantageForm.getSchool());
        now_score[0]= advantageForm.getQuestion1();
        now_score[1]= advantageForm.getQuestion2();
        now_score[2]= advantageForm.getQuestion3();
        now_score[3]= advantageForm.getQuestion4();
        now_score[4]= advantageForm.getQuestion5();
        now_score[5]= advantageForm.getQuestion6();
        now_score[6]= advantageForm.getQuestion7();
        now_score[7]= advantageForm.getQuestion8();
        now_score[8]= advantageForm.getQuestion9();
        now_score[9]= advantageForm.getQuestion10();
        now_score[10]= advantageForm.getQuestion11();
        now_score[11]= advantageForm.getQuestion12();
        now_score[12]= advantageForm.getQuestion13();
        now_score[13]= advantageForm.getQuestion14();
        now_score[14]= advantageForm.getQuestion15();
        now_score[15]= advantageForm.getQuestion16();
        now_score[16]= advantageForm.getQuestion17();
        now_score[17]= advantageForm.getQuestion18();
        now_score[18]= advantageForm.getQuestion19();
        now_score[19]= advantageForm.getQuestion20();
        now_score[20]= advantageForm.getQuestion21();
        now_score[21]= advantageForm.getQuestion22();
        now_score[22]= advantageForm.getQuestion23();
        now_score[23]= advantageForm.getQuestion24();
        now_score[24]= advantageForm.getQuestion25();

        advantageFormDto.setNow_score(now_score);

        for (int i = 0; i < now_score.length; i++) {
            score += now_score[i];
        }
        advantageFormDto.setScoreTotal(score);

        return advantageFormDto;

    }


    public static LabelDto convertLabelDtoFromLabelEntity(LabelEntity labelEntity) {
        LabelDto labelDto = new LabelDto();
        labelDto.setId(labelEntity.getId());
        labelDto.setLabelOne(labelEntity.getLabelOne());
        labelDto.setLabelTwo(labelEntity.getLabelTwo());
        labelDto.setContent(labelEntity.getContent());

        return labelDto;
    }

    public static List<List<Integer>> convertListFromStudyFormsOfFenxin(List<StudyForm> allStudyForm){

        List<List<Integer>> resultStudyForms = new ArrayList<List<Integer>>();
        for (StudyForm studyForm:allStudyForm){
            List<Integer> everyStudy = new ArrayList<Integer>();
            //分心抑制
            everyStudy.add(studyForm.getQuestion12());
            everyStudy.add(studyForm.getQuestion8());
            everyStudy.add(studyForm.getQuestion4());
            everyStudy.add(studyForm.getQuestion6());
            resultStudyForms.add(everyStudy);
        }
        return resultStudyForms;

    }
    public static List<List<Integer>> convertListFromStudyFormsOfYaodian(List<StudyForm> allStudyForm){

        List<List<Integer>> resultStudyForms = new ArrayList<List<Integer>>();
        for (StudyForm studyForm:allStudyForm){
            List<Integer> everyStudy = new ArrayList<Integer>();
            //要点
            everyStudy.add(studyForm.getQuestion6());
            everyStudy.add(studyForm.getQuestion10());
            everyStudy.add(studyForm.getQuestion2());
            everyStudy.add(studyForm.getQuestion18());
            everyStudy.add(studyForm.getQuestion14());
            resultStudyForms.add(everyStudy);
        }
        return resultStudyForms;

    }
    public static List<List<Integer>> convertListFromStudyFormsOfXinxi(List<StudyForm> allStudyForm){

        List<List<Integer>> resultStudyForms = new ArrayList<List<Integer>>();
        for (StudyForm studyForm:allStudyForm){
            List<Integer> everyStudy = new ArrayList<Integer>();
            //信心
            everyStudy.add(studyForm.getQuestion5());
            everyStudy.add(studyForm.getQuestion1());
            everyStudy.add(studyForm.getQuestion17());
            everyStudy.add(studyForm.getQuestion9());
            everyStudy.add(studyForm.getQuestion13());
            resultStudyForms.add(everyStudy);
        }
        return resultStudyForms;

    }

    public static List<List<Integer>> convertListFromStudyFormsOfJiaolv(List<StudyForm> allStudyForm){

        List<List<Integer>> resultStudyForms = new ArrayList<List<Integer>>();
        for (StudyForm studyForm:allStudyForm){
            List<Integer> everyStudy = new ArrayList<Integer>();
            //焦虑
            everyStudy.add(studyForm.getQuestion11());
            everyStudy.add(studyForm.getQuestion13());
            everyStudy.add(studyForm.getQuestion7());
            everyStudy.add(studyForm.getQuestion15());
            resultStudyForms.add(everyStudy);
        }
        return resultStudyForms;

    }

    public static List<Integer> convertListFromStudyFormsOfFenxin(StudyForm studyForm){

            List<Integer> everyStudy = new ArrayList<Integer>();
            //分心抑制
            everyStudy.add(studyForm.getQuestion12());
            everyStudy.add(studyForm.getQuestion8());
            everyStudy.add(studyForm.getQuestion4());
            everyStudy.add(studyForm.getQuestion6());
        return everyStudy;

    }
    public static List<Integer> convertListFromStudyFormsOfYaodian(StudyForm studyForm){

            List<Integer> everyStudy = new ArrayList<Integer>();
            //要点
            everyStudy.add(studyForm.getQuestion6());
            everyStudy.add(studyForm.getQuestion10());
            everyStudy.add(studyForm.getQuestion2());
            everyStudy.add(studyForm.getQuestion18());
            everyStudy.add(studyForm.getQuestion14());
        return everyStudy;

    }
    public static List<Integer> convertListFromStudyFormsOfXinxi(StudyForm studyForm){

            List<Integer> everyStudy = new ArrayList<Integer>();
            //信心
            everyStudy.add(studyForm.getQuestion5());
            everyStudy.add(studyForm.getQuestion1());
            everyStudy.add(studyForm.getQuestion17());
            everyStudy.add(studyForm.getQuestion9());
            everyStudy.add(studyForm.getQuestion13());
        return everyStudy;

    }

    public static List<Integer> convertListFromStudyFormsOfJiaolv(StudyForm studyForm){

            List<Integer> everyStudy = new ArrayList<Integer>();
            //焦虑
            everyStudy.add(studyForm.getQuestion11());
            everyStudy.add(studyForm.getQuestion13());
            everyStudy.add(studyForm.getQuestion7());
            everyStudy.add(studyForm.getQuestion15());
        return everyStudy;

    }

    public static LogEntity convertLogEntityFromLogDto(LogDto logDto) {
        LogEntity logEntity=new LogEntity();
        logEntity.setOpenId(logDto.getOpenId());
        logEntity.setChildOpenId(logDto.getChildOpenId());
        String log="{"+logDto.getTitle()+"#"+logDto.getLabels()+"#"+logDto.getContent()+"#"+logDto.getTime()+"}";
        logEntity.setLog(log);
        return logEntity;
    }
}