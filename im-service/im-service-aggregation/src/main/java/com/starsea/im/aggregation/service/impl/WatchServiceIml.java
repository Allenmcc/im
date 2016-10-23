package com.starsea.im.aggregation.service.impl;

import com.starsea.im.aggregation.dto.WatchFormDto;
import com.starsea.im.aggregation.transfor.Transformer;
import com.starsea.im.aggregation.util.MathToolsUtil;
import com.starsea.im.biz.dao.UserDao;
import com.starsea.im.biz.dao.WatchDao;
import com.starsea.im.biz.entity.UserEntity;
import com.starsea.im.biz.entity.WatchAllChildren;
import com.starsea.im.biz.entity.WatchZheXian;
import com.starsea.im.biz.entity.WatchForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by danny on 16/4/28.
 */
@Service("watchService")
public class WatchServiceIml implements com.starsea.im.aggregation.service.impl.WatchService {



    @Autowired
    private WatchDao watchDao;
    @Autowired
    private UserDao userDao;

    @Override
    public  int addWatchForm(WatchForm watchForm){

        return watchDao.addWatchForm(watchForm);
    }



    @Override
    public WatchFormDto queryLastWatchFormByName(String name) {
        WatchForm watchForm = watchDao.queryLastWatchFormByName(name);
        WatchFormDto watchFormDto = new WatchFormDto();
        if(watchForm!=null){
            watchFormDto =  Transformer.convertWatchFormDtoFromWatchForm(watchForm);
        }
        return watchFormDto;
    }

    @Override
    public WatchFormDto queryWatchFormByOpenId(String openId) {
        WatchForm watchForm = watchDao.queryWatchFormByOpenId(openId);
        WatchFormDto watchFormDto = new WatchFormDto();
        if(watchForm!=null){
            watchFormDto =  Transformer.convertWatchFormDtoFromWatchForm(watchForm);
        }
        return watchFormDto;
    }

    @Override
    public List<WatchFormDto> queryLastWatchFormByNameWeek(String name) {
        Date fDateEnd = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(fDateEnd);
        cal.add(Calendar.DAY_OF_MONTH, -7);
        Date fDateStart = cal.getTime();

        List<WatchForm> watchForms = watchDao.queryLastWatchFormByNameWeek(name, fDateStart, fDateEnd);
        List<WatchFormDto> watchFormDtos = new ArrayList<WatchFormDto>();
        if(watchForms.size()!= 0){

            for (WatchForm watchForm:watchForms){
                WatchFormDto watchFormDto = Transformer.convertWatchFormDtoFromWatchForm(watchForm);
                watchFormDtos.add(watchFormDto);
            }

        }
        return watchFormDtos;

    }


    @Override
    public List<WatchFormDto> queryLastWatchFormByNameMonth(String name) {
        Date fDateEnd = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(fDateEnd);
        cal.add(Calendar.MONTH, -1);
        Date fDateStart = cal.getTime();

        List<WatchForm> watchForms = watchDao.queryLastWatchFormByNameWeek(name,fDateStart,fDateEnd);
        List<WatchFormDto> watchFormDtos = new ArrayList<WatchFormDto>();
        if(watchForms.size()!= 0){

            for (WatchForm watchForm:watchForms){
                WatchFormDto watchFormDto = Transformer.convertWatchFormDtoFromWatchForm(watchForm);
                watchFormDtos.add(watchFormDto);
            }

        }
        return watchFormDtos;

    }


    @Override
    public Long queryAvgWatchFormByNameDay(String name,int day) {
        Date fDateEnd = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(fDateEnd);
        cal.add(Calendar.DATE, -(day));
        Date fDateStart = cal.getTime();

        List<WatchForm> watchForms = watchDao.queryLastWatchFormByNameWeek(name, fDateStart, fDateEnd);
        List<WatchFormDto> watchFormDtos = new ArrayList<WatchFormDto>();
        List<Integer> avgWatchForm = new ArrayList<Integer>();
        if(watchForms.size()!= 0){

            for (WatchForm watchForm:watchForms){
                WatchFormDto watchFormDto = Transformer.convertWatchFormDtoFromWatchForm(watchForm);
                watchFormDtos.add(watchFormDto);
            }

            for(WatchFormDto watchFormDto:watchFormDtos){
                Long temp = MathToolsUtil.getAvg(Transformer.converListFromIntArray(watchFormDto.getNow_score()));
                avgWatchForm.add(Integer.parseInt(temp.toString()));
            }
        }

        Long avg = MathToolsUtil.getAvg(avgWatchForm);

        return avg;
    }


    @Override
    public WatchZheXian queryLastWatchFormByOpenIdWeek(String openId) {
        WatchZheXian watchEntity=new WatchZheXian();
        Date today = new Date();
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(today);
        cal1.add(Calendar.DAY_OF_MONTH,-1);
        Date dateEnd=cal1.getTime();
        cal1.add(Calendar.DAY_OF_MONTH, -6);
        Date dateStart = cal1.getTime();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String start=format.format(dateStart);
        String end=format.format(dateEnd);
        String now=start;
        List<WatchForm> watchForms = watchDao.queryLastWatchFormByOpenIdWeek(openId, dateStart, dateEnd);
//        List<WatchFormDto> watchFormDtos = new ArrayList<WatchFormDto>();
        if(watchForms.size()!= 0){
//            for (WatchForm watchForm:watchForms){
//                WatchFormDto watchFormDto = Transformer.convertWatchFormDtoFromWatchForm(watchForm);
//                watchFormDtos.add(watchFormDto);
//            }
            int i=0;
            for (WatchForm watchForm:watchForms){
                int f=0;//标志此次数据处理结束
                while(f==0) {
                    if (format.format(watchForm.getEvaluationTime()).equals(now)) {//日期从7天前开始匹配
                        if (watchEntity.getDate()[i] == null) {//如果某一天 有多个记录 只取该天一个最新的记录
                            watchEntity.getDate()[i] = now.toString().substring(5);
                        }
                        String s[] = new String[5];
                        s[0] = watchForm.getQuestion15() + "";
                        s[1] = watchForm.getQuestion22() + "";
                        s[2] = watchForm.getQuestion28() + "";
                        s[3] = watchForm.getQuestion34() + "";
                        s[4] = watchForm.getQuestion40() + "";
                        watchEntity.getScore()[i] = s;
                        f=1;
                    } else {
                        if (watchEntity.getDate()[i] == null) {//某一天没有记录
                            watchEntity.getDate()[i] = now.toString().substring(5);
                        }
                        i++;
                        cal1.add(Calendar.DAY_OF_MONTH, +1);
                        Date time = cal1.getTime();
                        now = format.format(time);
                    }
                }
            }
            while(!now.equals(end)){
                i++;
                cal1.add(Calendar.DAY_OF_MONTH, +1);
                Date time=cal1.getTime();
                now=format.format(time);
                watchEntity.getDate()[i]=now.toString().substring(5);
            }

        }else{
            int i=0;
            while(!now.equals(end)){
                watchEntity.getDate()[i]=now.toString().substring(5);
                cal1.add(Calendar.DAY_OF_MONTH, +1);
                Date time = cal1.getTime();
                now=format.format(time);
                i++;
            }
            watchEntity.getDate()[i]=now.toString().substring(5);
        }
        return watchEntity;

    }



    @Override
    public WatchZheXian queryLastWatchFormByOpenIdMonth(String openId) {
        WatchZheXian watchEntity=new WatchZheXian();
        Date today = new Date();
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(today);
        cal1.add(Calendar.DAY_OF_MONTH,-1);
        Date dateEnd=cal1.getTime();
        cal1.add(Calendar.DAY_OF_MONTH, -29);
        Date dateStart = cal1.getTime();


        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String start=format.format(dateStart);
        String end=format.format(dateEnd);
        String now=start;
        List<WatchForm> watchForms = watchDao.queryLastWatchFormByOpenIdWeek(openId, dateStart, dateEnd);
//        List<WatchFormDto> watchFormDtos = new ArrayList<WatchFormDto>();
        if(watchForms.size()!= 0){
//            for (WatchForm watchForm:watchForms){
//                WatchFormDto watchFormDto = Transformer.convertWatchFormDtoFromWatchForm(watchForm);
//                watchFormDtos.add(watchFormDto);
//            }
            int i=0;
            for (WatchForm watchForm:watchForms){
                int f=0;//标志此次数据处理结束
                while(f==0) {
                    if (format.format(watchForm.getEvaluationTime()).equals(now)) {//日期从7天前开始匹配
                        if (watchEntity.getDate_m()[i] == null) {//如果某一天 有多个记录 只取该天一个最新的记录
                            watchEntity.getDate_m()[i] = now.toString().substring(5);
                        }
                        String s[] = new String[5];
                        s[0] = watchForm.getQuestion15() + "";
                        s[1] = watchForm.getQuestion22() + "";
                        s[2] = watchForm.getQuestion28() + "";
                        s[3] = watchForm.getQuestion34() + "";
                        s[4] = watchForm.getQuestion40() + "";
                        watchEntity.getScore_m()[i] = s;
                        f=1;
                    } else {
                        if (watchEntity.getDate_m()[i] == null) {//某一天没有记录
                            watchEntity.getDate_m()[i] = now.toString().substring(5);
                        }
                        i++;
                        cal1.add(Calendar.DAY_OF_MONTH, +1);
                        Date time = cal1.getTime();
                        now = format.format(time);
                    }
                }
            }
            while(!now.equals(end)){
                i++;
                cal1.add(Calendar.DAY_OF_MONTH, +1);
                Date time=cal1.getTime();
                now=format.format(time);
                watchEntity.getDate_m()[i]=now.toString().substring(5);
            }

        }else{
            int i=0;
            while(!now.equals(end)){
                watchEntity.getDate_m()[i]=now.toString().substring(5);
                cal1.add(Calendar.DAY_OF_MONTH, +1);
                Date time = cal1.getTime();
                now=format.format(time);
                i++;
            }
            watchEntity.getDate_m()[i]=now.toString().substring(5);
        }
        return watchEntity;

    }



    @Override
    public Long queryAvgWatchFormByOpenIdDay(String openId,int day) {
        Date fDateEnd = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(fDateEnd);
        cal.add(Calendar.DATE, -(day));
        Date fDateStart = cal.getTime();

        List<WatchForm> watchForms = watchDao.queryLastWatchFormByOpenIdWeek(openId, fDateStart, fDateEnd);
        List<WatchFormDto> watchFormDtos = new ArrayList<WatchFormDto>();
        List<Integer> avgWatchForm = new ArrayList<Integer>();
        if(watchForms.size()!= 0){

            for (WatchForm watchForm:watchForms){
                WatchFormDto watchFormDto = Transformer.convertWatchFormDtoFromWatchForm(watchForm);
                watchFormDtos.add(watchFormDto);
            }

            for(WatchFormDto watchFormDto:watchFormDtos){
                Long temp = MathToolsUtil.getAvg(Transformer.converListFromIntArray(watchFormDto.getNow_score()));
                avgWatchForm.add(Integer.parseInt(temp.toString()));
            }
        }

        Long avg = MathToolsUtil.getAvg(avgWatchForm);

        return avg;
    }

    @Override
    public WatchAllChildren queryOneTeacherAllChildrenByOpenIdWeek(String openId) {
        WatchAllChildren watchAll_week=new WatchAllChildren();
        UserEntity userEntity1 = userDao.queryUserByOpenId(openId);//查询老师的名字
        String name=userEntity1.getName();
        List<UserEntity> userEntities=userDao.queryChildrenUsers(name);//查询该老师的学生
        if(userEntities.size()==0){
            Date today = new Date();
            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(today);
            cal1.add(Calendar.DAY_OF_MONTH,-1);
            Date dateEnd=cal1.getTime();
            cal1.add(Calendar.DAY_OF_MONTH, -6);
            Date dateStart = cal1.getTime();

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String start=format.format(dateStart);
            String end=format.format(dateEnd);
            String now=start;
            while(!now.equals(end)){
                watchAll_week.getDate().add(now.toString().substring(5));
                cal1.add(Calendar.DAY_OF_MONTH, +1);
                Date time = cal1.getTime();
                now=format.format(time);
            }
            watchAll_week.getDate().add(now.toString().substring(5));
        }
        List<String> childrenOpenid=new ArrayList<String>();//存储该老师的学生的家长记录时的openid
        for(UserEntity userEntity:userEntities){
            watchAll_week.getChildrenName().add(userEntity.getName());//存储学生的姓名
            childrenOpenid.add(userEntity.getOpenId());
        }
        for(int i=0;i<childrenOpenid.size();i++){
            WatchZheXian watchZheXian=queryLastWatchFormByOpenIdWeek(childrenOpenid.get(i));//获得一个学生的一周的记录
            if(i==0) {//存储一周的日期
                for (int j = 0; j < watchZheXian.getDate().length; j++) {
                    watchAll_week.getDate().add(watchZheXian.getDate()[j]);
                }
            }
            ArrayList<String> score1=new ArrayList<String>();
            //存储一个学生的自主力
            for(int k=0;k<7;k++){
                score1.add(watchZheXian.getScore()[k][0]);
            }
            watchAll_week.getZizhu().add(score1);
            ArrayList<String> score2=new ArrayList<String>();
            //专注力
            for(int k=0;k<7;k++){
                score2.add(watchZheXian.getScore()[k][1]);
            }
            watchAll_week.getZhuanzhu().add(score2);
            ArrayList<String> score3=new ArrayList<String>();
            //意志力
            for(int k=0;k<7;k++){
                score3.add(watchZheXian.getScore()[k][2]);
            }
            watchAll_week.getYizhi().add(score3);
            ArrayList<String> score4=new ArrayList<String>();
            //情绪力
            for(int k=0;k<7;k++){
                score4.add(watchZheXian.getScore()[k][3]);
            }
            watchAll_week.getQingxu().add(score4);
            ArrayList<String> score5=new ArrayList<String>();
            //人际力
            for(int k=0;k<7;k++){
                score5.add(watchZheXian.getScore()[k][4]);
            }
            watchAll_week.getRenji().add(score5);
        }

        return watchAll_week;
    }

    public WatchAllChildren queryOneTeacherAllChildrenByOpenIdMonth(String openId) {
        WatchAllChildren watchAll_week=new WatchAllChildren();
        UserEntity userEntity1 = userDao.queryUserByOpenId(openId);//查询老师的名字
        String name=userEntity1.getName();
        List<UserEntity> userEntities=userDao.queryChildrenUsers(name);//查询该老师的学生
        if(userEntities.size()==0){
            Date today = new Date();
            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(today);
            cal1.add(Calendar.DAY_OF_MONTH,-1);
            Date dateEnd=cal1.getTime();
            cal1.add(Calendar.DAY_OF_MONTH, -29);
            Date dateStart = cal1.getTime();

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String start=format.format(dateStart);
            String end=format.format(dateEnd);
            String now=start;
            while(!now.equals(end)){
                watchAll_week.getDate().add(now.toString().substring(5));
                cal1.add(Calendar.DAY_OF_MONTH, +1);
                Date time = cal1.getTime();
                now=format.format(time);
            }
            watchAll_week.getDate().add(now.toString().substring(5));
        }
        List<String> childrenOpenid=new ArrayList<String>();//存储该老师的学生的家长记录时的openid
        for(UserEntity userEntity:userEntities){
            watchAll_week.getChildrenName().add(userEntity.getName());//存储学生的姓名
            childrenOpenid.add(userEntity.getOpenId());
        }
        for(int i=0;i<childrenOpenid.size();i++){
            WatchZheXian watchZheXian=queryLastWatchFormByOpenIdMonth(childrenOpenid.get(i));//获得一个学生的一月的记录
            if(i==0) {//存储一月的日期
                for (int j = 0; j < watchZheXian.getDate_m().length; j++) {
                    watchAll_week.getDate().add(watchZheXian.getDate_m()[j]);
                }
            }
            ArrayList<String> score1=new ArrayList<String>();
            //存储一个学生的自主力
            for(int k=0;k<30;k++){
                score1.add(watchZheXian.getScore_m()[k][0]);
            }
            watchAll_week.getZizhu().add(score1);
            ArrayList<String> score2=new ArrayList<String>();
            //专注力
            for(int k=0;k<30;k++){
                score2.add(watchZheXian.getScore_m()[k][1]);
            }
            watchAll_week.getZhuanzhu().add(score2);
            ArrayList<String> score3=new ArrayList<String>();
            //意志力
            for(int k=0;k<30;k++){
                score3.add(watchZheXian.getScore_m()[k][2]);
            }
            watchAll_week.getYizhi().add(score3);
            ArrayList<String> score4=new ArrayList<String>();
            //情绪力
            for(int k=0;k<30;k++){
                score4.add(watchZheXian.getScore_m()[k][3]);
            }
            watchAll_week.getQingxu().add(score4);
            ArrayList<String> score5=new ArrayList<String>();
            //人际力
            for(int k=0;k<30;k++){
                score5.add(watchZheXian.getScore_m()[k][4]);
            }
            watchAll_week.getRenji().add(score5);
        }

        return watchAll_week;
    }

    public WatchAllChildren queryAllChildrenByOpenIdWeek() {
        WatchAllChildren watchAll=new WatchAllChildren();
        List<UserEntity> userEntities=userDao.queryAllChildren();//查询所有的学生
        List<String> childrenOpenid=new ArrayList<String>();//存储所有学生的家长记录时的openid
        if(userEntities.size()==0){
            Date today = new Date();
            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(today);
            cal1.add(Calendar.DAY_OF_MONTH,-1);
            Date dateEnd=cal1.getTime();
            cal1.add(Calendar.DAY_OF_MONTH, -6);
            Date dateStart = cal1.getTime();

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String start=format.format(dateStart);
            String end=format.format(dateEnd);
            String now=start;
            while(!now.equals(end)){
                watchAll.getDate().add(now.toString().substring(5));
                cal1.add(Calendar.DAY_OF_MONTH, +1);
                Date time = cal1.getTime();
                now=format.format(time);
            }
            watchAll.getDate().add(now.toString().substring(5));
        }
        for(UserEntity userEntity:userEntities){
            watchAll.getChildrenName().add(userEntity.getName());//存储学生的姓名
            childrenOpenid.add(userEntity.getOpenId());
        }
        for(int i=0;i<childrenOpenid.size();i++){
            WatchZheXian watchZheXian=queryLastWatchFormByOpenIdWeek(childrenOpenid.get(i));//获得一个学生的一周的记录
            if(i==0) {//存储一周的日期
                for (int j = 0; j < watchZheXian.getDate().length; j++) {
                    watchAll.getDate().add(watchZheXian.getDate()[j]);
                }
            }
            ArrayList<String> score1=new ArrayList<String>();
            //存储一个学生的自主力
            for(int k=0;k<7;k++){
                score1.add(watchZheXian.getScore()[k][0]);
            }
            watchAll.getZizhu().add(score1);
            ArrayList<String> score2=new ArrayList<String>();
            //专注力
            for(int k=0;k<7;k++){
                score2.add(watchZheXian.getScore()[k][1]);
            }
            watchAll.getZhuanzhu().add(score2);
            ArrayList<String> score3=new ArrayList<String>();
            //意志力
            for(int k=0;k<7;k++){
                score3.add(watchZheXian.getScore()[k][2]);
            }
            watchAll.getYizhi().add(score3);
            ArrayList<String> score4=new ArrayList<String>();
            //情绪力
            for(int k=0;k<7;k++){
                score4.add(watchZheXian.getScore()[k][3]);
            }
            watchAll.getQingxu().add(score4);
            ArrayList<String> score5=new ArrayList<String>();
            //人际力
            for(int k=0;k<7;k++){
                score5.add(watchZheXian.getScore()[k][4]);
            }
            watchAll.getRenji().add(score5);
        }

        return watchAll;
    }

    public WatchAllChildren queryAllChildrenByOpenIdMonth() {
        WatchAllChildren watchAll_week=new WatchAllChildren();
        List<UserEntity> userEntities=userDao.queryAllChildren();//查询该老师的学生
        if(userEntities.size()==0){
            Date today = new Date();
            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(today);
            cal1.add(Calendar.DAY_OF_MONTH,-1);
            Date dateEnd=cal1.getTime();
            cal1.add(Calendar.DAY_OF_MONTH, -6);
            Date dateStart = cal1.getTime();

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String start=format.format(dateStart);
            String end=format.format(dateEnd);
            String now=start;
            while(!now.equals(end)){
                watchAll_week.getDate().add(now.toString().substring(5));
                cal1.add(Calendar.DAY_OF_MONTH, +1);
                Date time = cal1.getTime();
                now=format.format(time);
            }
            watchAll_week.getDate().add(now.toString().substring(5));
        }
        List<String> childrenOpenid=new ArrayList<String>();//存储所有学生的家长记录时的openid
        for(UserEntity userEntity:userEntities){
            watchAll_week.getChildrenName().add(userEntity.getName());//存储学生的姓名
            childrenOpenid.add(userEntity.getOpenId());
        }
        for(int i=0;i<childrenOpenid.size();i++){
            WatchZheXian watchZheXian=queryLastWatchFormByOpenIdMonth(childrenOpenid.get(i));//获得一个学生的一月的记录
            if(i==0) {//存储一月的日期
                for (int j = 0; j < watchZheXian.getDate_m().length; j++) {
                    watchAll_week.getDate().add(watchZheXian.getDate_m()[j]);
                }
            }
            ArrayList<String> score1=new ArrayList<String>();
            //存储一个学生的自主力
            for(int k=0;k<30;k++){
                score1.add(watchZheXian.getScore_m()[k][0]);
            }
            watchAll_week.getZizhu().add(score1);
            ArrayList<String> score2=new ArrayList<String>();
            //专注力
            for(int k=0;k<30;k++){
                score2.add(watchZheXian.getScore_m()[k][1]);
            }
            watchAll_week.getZhuanzhu().add(score2);
            ArrayList<String> score3=new ArrayList<String>();
            //意志力
            for(int k=0;k<30;k++){
                score3.add(watchZheXian.getScore_m()[k][2]);
            }
            watchAll_week.getYizhi().add(score3);
            ArrayList<String> score4=new ArrayList<String>();
            //情绪力
            for(int k=0;k<30;k++){
                score4.add(watchZheXian.getScore_m()[k][3]);
            }
            watchAll_week.getQingxu().add(score4);
            ArrayList<String> score5=new ArrayList<String>();
            //人际力
            for(int k=0;k<30;k++){
                score5.add(watchZheXian.getScore_m()[k][4]);
            }
            watchAll_week.getRenji().add(score5);
        }

        return watchAll_week;
    }
}
