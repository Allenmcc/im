package com.starsea.im.biz.dao;

import com.starsea.im.biz.annotation.DataSource;
import com.starsea.im.biz.annotation.Single;
import com.starsea.im.biz.entity.StudyForm;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by danny on 16/4/28.
 */
@Repository
@Single
public interface DiagnoseDao {

    @DataSource("write")
    public int addStudyFormByTeacher(StudyForm studyForm);

    @DataSource("read")
    public List<StudyForm> queryStudyFormByTeacher();

    @DataSource("read")
    public StudyForm queryLastStudyFormByName(String name);

    @DataSource("read")
    public StudyForm queryStudyFormByOpenId(@Param("openId") String openId,@Param("childOpenId") String childOpenId);

    @DataSource("read")
    public List<String> queryStudyFormAllOpenId();

    @DataSource("read")
    public List<StudyForm> queryStudyForm();
}
