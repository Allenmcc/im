package com.starsea.im.aggregation.service.impl;

import com.starsea.im.aggregation.dto.StudyFormDto;
import com.starsea.im.aggregation.dto.StudyResultDto;
import com.starsea.im.aggregation.service.DiagnoseService;
import com.starsea.im.aggregation.transfor.Transformer;
import com.starsea.im.aggregation.util.MathToolsUtil;
import com.starsea.im.aggregation.util.StudyResultUtil;
import com.starsea.im.biz.dao.DiagnoseDao;
import com.starsea.im.biz.dao.UserDao;
import com.starsea.im.biz.entity.DiagnoseAllChildren;
import com.starsea.im.biz.entity.DiagnoseFinal;
import com.starsea.im.biz.entity.StudyForm;
import com.starsea.im.biz.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by danny on 16/4/28.
 */

@Service("diagnoseService")
public class DiagnoseServiceImpl implements DiagnoseService {
    @Autowired
    private DiagnoseDao diagnoseDao;
    @Autowired
    private UserDao userDao;

    @Override
    public DiagnoseFinal addStudyForm(StudyForm studyForm) {
        //原始分计算
        int fenxin=studyForm.getQuestion12()+studyForm.getQuestion16()+studyForm.getQuestion4()+studyForm.getQuestion8();
        int yaodian=studyForm.getQuestion2()+studyForm.getQuestion6()+studyForm.getQuestion10()+studyForm.getQuestion14()+studyForm.getQuestion18();
        int xinxi=studyForm.getQuestion1()+studyForm.getQuestion5()+studyForm.getQuestion9()+studyForm.getQuestion13()+studyForm.getQuestion17();
        int jiaolv=studyForm.getQuestion3()+studyForm.getQuestion7()+studyForm.getQuestion11()+studyForm.getQuestion15();
        String scoreYs=fenxin+","+yaodian+","+xinxi+","+jiaolv;
        studyForm.setScoreYs(scoreYs);
        //标准分计算
        biaozhun(studyForm);
        int result=diagnoseDao.addStudyFormByTeacher(studyForm);


        //生成学习策略的结果   传回前端
        DiagnoseFinal diagnoseFinal=new DiagnoseFinal();
        StudyForm studyForm1 = diagnoseDao.queryStudyFormByOpenId(studyForm.getOpenId(),studyForm.getChildOpenId());
        int[] ys=new int[4];//原始分
        int[] bz=new int[4];//标准分
        int total=0;//总分

        String[] ysS=studyForm1.getScoreYs().split(",");
        String[] bzS=studyForm1.getScoreBz().split(",");
        for(int i=0;i<ysS.length;i++){
            ys[i]=Integer.parseInt(ysS[i]);
            bz[i]=Integer.parseInt(bzS[i]);
            total+=ys[i];
        }
        diagnoseFinal.setScoreYs(ys);
        diagnoseFinal.setScoreBz(bz);
        diagnoseFinal.setScoreTotal(total);

        List<StudyForm> children=diagnoseDao.queryStudyForm();//获得所有学生的信息（去掉重复记录）
        int number=children.size();
        int[] childrenTotal=new int[number];
        for(int i=0;i<number;i++){
            String[] fen=children.get(i).getScoreYs().split(",");
            childrenTotal[i]=Integer.parseInt(fen[0])+Integer.parseInt(fen[1])+Integer.parseInt(fen[2])+Integer.parseInt(fen[3]);
        }
        Arrays.sort(childrenTotal);
        int range=1;
        for(int i=number-1;i>=0;i--){
            if(total>=childrenTotal[i]){
                break;
            }
            range++;
        }
        diagnoseFinal.setChidrenNumber(number);
        diagnoseFinal.setRange(range);

//        String[] com=new String[4];
//        if(studyForm.getChildOpenId().equals("none")){//家长版
//            if(total<=87&&total>=80){
//                diagnoseFinal.setResult("总体看，您的孩子在学习中策略性很强，善于利用灵活有效的方法、技巧和辅助手段增进学习效率和效果。" +
//                        "希望能继续保持。具体学习策略维度结果及解析请参见以下内容。");
//            }else if(total<=77&&total>=73){
//                diagnoseFinal.setResult("总体看，您的孩子在学习中有较强的策略性，注意利用方法、技巧和辅助手段提高学习效率和效果，但仍有一定的提升空间。" +
//                        "可考虑对其进行相应学习策略维度的训练。具体可参照以下学习策略维度的结果。");
//            }else if(total<=72&&total>=66){
//                diagnoseFinal.setResult("总体看，您的孩子在学习中有一定的策略性，有意识去利用方法、技巧和辅助手段提高学习效率和效果，" +
//                        "但存在较大提升空间。宜对其进行系统学习策略的训练。具体可参照以下学习策略维度的结果。");
//            }else if(total<=65&&total>=59){
//                diagnoseFinal.setResult("总体看，您的孩子在学习中一定的策略意识，也会采用一些方法和手段增进学习效率，" +
//                        "但存在很大的提升空间。宜对其进行系统、深入的学习策略训练。具体可参照以下学习策略维度的结果。");
//            }else if(total<=58&&total>=52){
//                diagnoseFinal.setResult("总体看，您的孩子在学习中的策略性较弱，不太注重利用方法和手段增进学习效果，因此，" +
//                        "对学习策略进行有计划、有步骤地训练，是十分重要的。具体可参照以下学习策略维度的结果。");
//            }else if(total<=51&&total>=45){
//                diagnoseFinal.setResult("总体看，您的孩子在学习中策略性很弱，很少采用方法、技巧、辅助手段等进行有效学习，" +
//                        "宜对其学习策略进行有计划、有步骤的系统训练。具体可参照以下学习策略维度的结果。");
//            }else if(total<=44&&total>=38){
//                diagnoseFinal.setResult("总体看，您的孩子在学习中策略性非常弱，因为方法、技巧和辅助手段的缺乏，" +
//                        "学习效率与效果都存在问题。因此宜对其进行有计划、有步骤的学习策略系统训练。具体可参照以下学习策略维度的结果。");
//            }else if(total<=37&&total>=29){
//                diagnoseFinal.setResult("总体看，您的孩子在学习中策略性特别弱，因为方法、技巧和辅助手段的严重缺乏，" +
//                        "其学习效率与效果都存在明显问题。因此对其进行有计划、有步骤的学习策略系统训练，十分关键。具体可参照以下学习策略维度的结果。");
//            }
//            //分心抑制
//            if(ys[0]<=20&&ys[0]>=18){
//                com[0]="您的孩子在学习过程中，自我控制很好，希望能够保持。";
//            }else if(ys[0]<=17&&ys[0]>=16){
//                com[0]="您的孩子在学习过程中，有较好的专注度，但存在进一步提升的空间。可考虑对其进行一些自控力的适当训练。";
//            }else if(ys[0]<=15&&ys[0]>=13){
//                com[0]="您的孩子在学习过程中，比较容易分心，有较大的提升空间。通过自控力的指导与训练，可望提升孩子学习中的专注度。";
//            }else if(ys[0]<=12&&ys[0]>=11){
//                com[0]="您的孩子在学习过程中，明显容易分心，这会对学习效率和学习效果构成不利影响。建议通过系统的自控力训练，增进孩子在学习过程中的专注程度。";
//            }else if(ys[0]<=10&&ys[0]>=9){
//                com[0]="您的孩子在学习过程中，非常容易分心，这会对其学习效率与效果造成严重影响。特别建议通过系统的自控力指导与训练，改善孩子在学习过程中的专注程度。";
//            }else if(ys[0]<=8&&ys[0]>=6){
//                com[0]="您的孩子在学习过程中，特别容易分心，注意力不能集中，会对学习效率和学习效果造成明显阻碍，强烈建议对孩子进行系统的自控力训练，使其在专注程度上得到提升。";
//            }else if(ys[0]<=5&&ys[0]>=4){
//                com[0]="您的孩子在学习过程中，太容易分心了，注意力起伏不定，这会对其学习效率与学习效果构成严重障碍。强烈建议通过系统的自控力指导训练，以使孩子在专注程度上有所改善。";
//            }
//            //要点抓取
//            if(ys[1]<=25&&ys[1]>=23){
//                com[1]="您的孩子在学习中有明确的要点意识，能比较快速而准确地抓取学习关键点和重要信息。希望能够继续保持。";
//            }else if(ys[1]<=22&&ys[1]>=20){
//                com[1]="您的孩子在学习中有比较明确的要点意识，能有意识地去抓取知识的要点和关键信息，但有进一步提升的空间。系统的学习策略指导与训练，是必要的。";
//            }else if(ys[1]<=19&&ys[1]>=17){
//                com[1]="您的孩子在学习中有一定的要点意识，能尝试去抓取知识要点和学习关键信息，但进步空间很大。建议对其进行系统、循序渐进的要点抓取指导与训练。";
//            }else if(ys[1]<=16&&ys[1]>=14){
//                com[1]="您的孩子在学习中有隐约的要点意识，也有去抓取知识要点和关键信息的努力，但存在明显的提升空间。建议对其进行有步骤、有计划地要点抓取指导与训练。";
//            }else if(ys[1]<=13&&ys[1]>=12){
//                com[1]="您的孩子在学习中有时会有一些要点意识，也有在学习或复习中抓取知识要点和关键信息的尝试，但效果并不理想，这方面的能力需要大幅度提升。特别建议对其开展有计划、有步骤的系统要点抓取指导与训练。";
//            }else if(ys[1]<=11&&ys[1]>=9){
//                com[1]="您的孩子在学习中，对于什么是知识要点和关键信息，不甚敏感，因此，学习中常常不甚明了什么是关键的和重要的，这对学习效果与学习效率有明显阻碍。特别建议对其进行要点抓取方面有计划、有步骤的系统、深入指导与训练。";
//            }else if(ys[1]<=8&&ys[1]>=5){
//                com[1]="您的孩子在学习中，严重缺乏要点意识，因此也很少能够抓到知识关键点和关键信息，这对于其学习效果有极为不利的影响。强烈建议对其进行循序渐进、有计划的知识要点、关键信息的识别与抓取指导和训练。";
//            }
//            //信息加工
//            if(ys[2]<=25&&ys[2]>=23){
//                com[2]="您的孩子学习中能够将新旧知识联系起来，将课堂知识与课外经验联系起来，并有意识地去使用学习窍门以提升自己的学习质量。望继续予以保持。";
//            }else if(ys[2]<=22&&ys[2]>=20){
//                com[2]="您的孩子在学习中能联系新旧知识，也能将所学内容与自身的经验相联系，有比较明确的学习窍门意识，但还存在一定的上升空间。可考虑对其进行信息加工的训练。";
//            }else if(ys[2]<=19&&ys[2]>=18){
//                com[2]="您的孩子在学习中有较明确的新旧知识、知识与经验联结的意识，也注意在学习中使用一定的手段以增强学习效果，但存在较大的上升空间。建议对其进行信息加工特别是举一反三、触类旁通式的学习训练。";
//            }else if(ys[2]<=17&&ys[2]>=15){
//                com[2]="您的孩子在学习中有一定的新旧知识、知识经验联结意识，也会在学习中尝试利用作记号、画示意图的手段加强学习效果，但使用得还不是很充分，存在明显的上升空间。建议对其进行知识联结方面和学习诀窍方面的系统指导。";
//            }else if(ys[2]<=14&&ys[2]>=13){
//                com[2]="您的孩子在学习中，对于新旧知识联结、知识与经验联结，以及学习中的窍门使用，使用得不很充分，在此方面的意识不够强。建议对其进行信息加工和认知策略方面的系统训练与指导。";
//            }else if(ys[2]<=12&&ys[2]>=10){
//                com[2]="您的孩子在学习中比较缺乏新旧知识、知识与经验联结的意识，也不会在学习中采用诸如作记号、画图这样的手段提升学习效率和效果。特别建议对其进行信息加工质量提升和认知加工策略方面的有计划、有步骤训练与指导。";
//            }else if(ys[2]<=9&&ys[2]>=5){
//                com[2]="您的孩子在学习中严重缺乏新旧知识、知识经验联结的意识，也基本不会使用诸如画图或作记号等手段来加强学习效果。强烈建议对其进行系统、深入的认知加工策略的系统、深入训练与指导。";
//            }
//            //焦虑对应
//            if(ys[3]<=20&&ys[3]>=18){
//                com[3]="您的孩子在学习中情绪稳定，复习充分，考试时情绪镇静，考试认真专心。希望能继续保持。";
//            }else if(ys[3]<=17&&ys[3]>=15){
//                com[3]="您的孩子在学习中情绪比较稳定，复习比较充分，考试时不太慌张，通常发挥比较正常，但焦虑应对方面还有提升空间。可对其进行情绪控制方面的指导与训练。";
//            }else if(ys[3]<=14&&ys[3]>=13){
//                com[3]="您的孩子在学习中情绪具有一定的稳定性，复习尚算系统，考试时情绪尚好，但有一定的焦虑和紧张。建议对其进行情绪控制特别是焦虑应对方面的指导与训练。";
//            }else if(ys[3]<=12&&ys[3]>=11){
//                com[3]="您的孩子学习中情绪比较容易紧张，有时考试时比较容易产生焦虑，有时会出现该做对的却不能做对的情况。建议对其进行系统的、有步骤的情绪控制尤其是焦虑应对方面的指导与训练。";
//            }else if(ys[3]<=10&&ys[3]>=8){
//                com[3]="您的孩子在学习中情绪容易紧张，考试时容易慌乱，导致该做对的题目可能会失分，因为担心考砸而不能专心考试。建议对其进行情绪自控特别是焦虑应对方面的有步骤、有计划的指导与训练。";
//            }else if(ys[3]<=7&&ys[3]>=6){
//                com[3]="您的孩子在学习中情绪中极易紧张，在考试时会很慌乱，情绪焦虑，以致不能专心考试。特别建议对其进行有计划、有步骤地系统情绪自控训练尤其是焦虑应对的训练与指导。";
//            }else if(ys[3]<=5&&ys[3]>=4){
//                com[3]="您的孩子在学习中情绪特别紧张，在考试时非常慌乱，常担心自己考砸而不能专心考试，该做的也做不对造成不该有的失分。强烈建议对其进行有计划、有步骤的情绪自控特别是焦虑应对方面的训练和指导。";
//            }
//        }else{//老师版
//            if(total<=87&&total>=80){
//                diagnoseFinal.setResult("总体看，该生在学习中策略性很强，善于利用灵活有效的方法、技巧和辅助手段增进学习效率和效果。希望能继续保持。具体学习策略维度结果及解析请参见以下内容。");
//            }else if(total<=77&&total>=73){
//                diagnoseFinal.setResult("总体看，该生在学习中有较强的策略性，注意利用方法、技巧和辅助手段提高学习效率和效果，但仍有一定的提升空间。可考虑对其进行相应学习策略维度的训练。具体可参照以下学习策略维度的结果。");
//            }else if(total<=72&&total>=66){
//                diagnoseFinal.setResult("总体看，该生在学习中有一定的策略性，有意识去利用方法、技巧和辅助手段提高学习效率和效果，但存在较大提升空间。宜对其进行系统学习策略的训练。具体可参照以下学习策略维度的结果。");
//            }else if(total<=65&&total>=59){
//                diagnoseFinal.setResult("总体看，该生在学习中一定的策略意识，也会采用一些方法和手段增进学习效率，但存在很大的提升空间。宜对其进行系统、深入的学习策略训练。具体可参照以下学习策略维度的结果。");
//            }else if(total<=58&&total>=52){
//                diagnoseFinal.setResult("总体看，该生在学习中的策略性较弱，不太注重利用方法和手段增进学习效果，因此，对学习策略进行有计划、有步骤地训练，是十分重要的。具体可参照以下学习策略维度的结果。");
//            }else if(total<=51&&total>=45){
//                diagnoseFinal.setResult("总体看，该生在学习中策略性很弱，很少采用方法、技巧、辅助手段等进行有效学习，宜对其学习策略进行有计划、有步骤的系统训练。具体可参照以下学习策略维度的结果。");
//            }else if(total<=44&&total>=38){
//                diagnoseFinal.setResult("总体看，该生在学习中策略性非常弱，因为方法、技巧和辅助手段的缺乏，学习效率与效果都存在问题。因此宜对其进行有计划、有步骤的学习策略系统训练。具体可参照以下学习策略维度的结果。");
//            }else if(total<=37&&total>=29){
//                diagnoseFinal.setResult("总体看，该生在学习中策略性特别弱，因为方法、技巧和辅助手段的严重缺乏，其学习效率与效果都存在明显问题。因此对其进行有计划、有步骤的学习策略系统训练，十分关键。具体可参照以下学习策略维度的结果。");
//            }
//            //分心抑制
//            if(ys[0]<=20&&ys[0]>=18){
//                com[0]="该生在学习过程中，自我控制很好，希望能够保持。";
//            }else if(ys[0]<=17&&ys[0]>=16){
//                com[0]="该生在学习过程中，有较好的专注度，但存在进一步提升的空间。可考虑对其进行一些自控力的适当训练。";
//            }else if(ys[0]<=15&&ys[0]>=13){
//                com[0]="该生在学习过程中，比较容易分心，有较大的提升空间。通过自控力的指导与训练，可望提升其学习中的专注度。";
//            }else if(ys[0]<=12&&ys[0]>=11){
//                com[0]="该生在学习过程中，明显容易分心，这会对学习效率和学习效果构成不利影响。建议通过系统的自控力训练，增进其在学习过程中的专注程度。";
//            }else if(ys[0]<=10&&ys[0]>=9){
//                com[0] = "该生在学习过程中，非常容易分心，这会对其学习效率与效果造成严重影响。特别建议通过系统的自控力指导与训练，改善其在学习过程中的专注程度。";
//            }else if(ys[0]<=8 && ys[0] >= 6) {
//                com[0] = "该生在学习过程中，特别容易分心，注意力不能集中，会对学习效率和学习效果造成明显阻碍，强烈建议对其进行系统的自控力训练，使其在专注程度上得到提升。";
//            } else if (ys[0] <= 5 && ys[0] >= 4) {
//                com[0] ="该生在学习过程中，太容易分心了，注意力起伏不定，这会对其学习效率与学习效果构成严重障碍。强烈建议通过系统的自控力指导训练，使其在专注程度上有所改善。";
//            }
//            //要点抓取
//            if(ys[1]<=25&&ys[1]>=23){
//                com[1]="该生在学习中有明确的要点意识，能比较快速而准确地抓取学习关键点和重要信息。希望能够继续保持。";
//            }else if(ys[1]<=22&&ys[1]>=20){
//                com[1]="该生在学习中有比较明确的要点意识，能有意识地去抓取知识的要点和关键信息，但有进一步提升的空间。系统的学习策略指导与训练，是必要的。";
//            }else if(ys[1]<=19&&ys[1]>=17){
//                com[1]="该生在学习中有一定的要点意识，能尝试去抓取知识要点和学习关键信息，但进步空间很大。建议对其进行系统、循序渐进的要点抓取指导与训练。";
//            }else if(ys[1]<=16 && ys[1] >= 14) {
//                com[1] = "该生在学习中有隐约的要点意识，也有去抓取知识要点和关键信息的努力，但存在明显的提升空间。建议对其进行有步骤、有计划地要点抓取指导与训练。";
//            } else if (ys[1] <= 13 && ys[1] >= 12) {
//                com[1] = "该生在学习中有时会有一些要点意识，也有在学习或复习中抓取知识要点和关键信息的尝试，但效果并不理想，这方面的能力需要大幅度提升。特别建议对其开展有计划、有步骤的系统要点抓取指导与训练。";
//            } else if (ys[1]<=11&&ys[1]>=9){
//                com[1]="该生在学习中，对于什么是知识要点和关键信息，不甚敏感，因此，学习中常常不甚明了什么是关键的和重要的，这对学习效果与学习效率有明显阻碍。特别建议对其进行要点抓取方面有计划、有步骤的系统、深入指导与训练。";
//            }else if(ys[1]<=8&&ys[1]>=5){
//                com[1]="该生在学习中，严重缺乏要点意识，因此也很少能够抓到知识关键点和关键信息，这对于其学习效果有极为不利的影响。强烈建议对其进行循序渐进、有计划的知识要点、关键信息的识别与抓取指导和训练。";
//            }
//            //信息加工
//            if(ys[2]<=25&&ys[2]>=23){
//                com[2]="该生学习中能够将新旧知识联系起来，将课堂知识与课外经验联系起来，并有意识地去使用学习窍门以提升自己的学习质量。望继续予以保持。";
//            }else if(ys[2]<=22&&ys[2]>=20){
//                com[2]="该生在学习中能联系新旧知识，也能将所学内容与自身的经验相联系，有比较明确的学习窍门意识，但还存在一定的上升空间。可考虑对其进行信息加工的训练。";
//            }else if(ys[2]<=19&&ys[2]>=18){
//                com[2]="该生在学习中有较明确的新旧知识、知识与经验联结的意识，也注意在学习中使用一定的手段以增强学习效果，但存在较大的上升空间。建议对其进行信息加工特别是举一反三、触类旁通式的学习训练。";
//            }else if(ys[2]<=17&&ys[2]>=15){
//                com[2]="该生在学习中有一定的新旧知识、知识经验联结意识，也会在学习中尝试利用作记号、画示意图的手段加强学习效果，但使用得还不是很充分，存在明显的上升空间。建议对其进行知识联结方面和学习诀窍方面的系统指导。";
//            }else if(ys[2]<=14&&ys[2]>=13){
//                com[2]="该生在学习中，对于新旧知识联结、知识与经验联结，以及学习中的窍门使用，使用得不很充分，在此方面的意识不够强。建议对其进行信息加工和认知策略方面的系统训练与指导。";
//            }else if(ys[2]<=12&&ys[2]>=10){
//                com[2]="该生在学习中比较缺乏新旧知识、知识与经验联结的意识，也不会在学习中采用诸如作记号、画图这样的手段提升学习效率和效果。特别建议对其进行信息加工质量提升和认知加工策略方面的有计划、有步骤训练与指导。";
//            }else if(ys[2]<=9&&ys[2]>=5){
//                com[2]="该生在学习中严重缺乏新旧知识、知识经验联结的意识，也基本不会使用诸如画图或作记号等手段来加强学习效果。强烈建议对其进行系统、深入的认知加工策略的系统、深入训练与指导。";
//            }
//            //焦虑对应
//            if(ys[3]<=20&&ys[3]>=18){
//                com[3]="该生在学习中情绪稳定，复习充分，考试时情绪镇静，考试认真专心。希望能继续保持。";
//            }else if(ys[3]<=17&&ys[3]>=15){
//                com[3]="该生在学习中情绪比较稳定，复习比较充分，考试时不太慌张，通常发挥比较正常，但焦虑应对方面还有提升空间。可对其进行情绪控制方面的指导与训练。";
//            }else if(ys[3]<=14&&ys[3]>=13){
//                com[3]="该生在学习中情绪具有一定的稳定性，复习尚算系统，考试时情绪尚好，但有一定的焦虑和紧张。建议对其进行情绪控制特别是焦虑应对方面的指导与训练。";
//            }else if(ys[3]<=12&&ys[3]>=11){
//                com[3]="该生学习中情绪比较容易紧张，有时考试时比较容易产生焦虑，有时会出现该做对的却不能做对的情况。建议对其进行系统的、有步骤的情绪控制尤其是焦虑应对方面的指导与训练。";
//            }else if(ys[3]<=10&&ys[3]>=8){
//                com[3]="该生在学习中情绪容易紧张，考试时容易慌乱，导致该做对的题目可能会失分，因为担心考砸而不能专心考试。建议对其进行情绪自控特别是焦虑应对方面的有步骤、有计划的指导与训练。";
//            }else if(ys[3]<=7&&ys[3]>=6){
//                com[3]="该生在学习中情绪中极易紧张，在考试时会很慌乱，情绪焦虑，以致不能专心考试。特别建议对其进行有计划、有步骤地系统情绪自控训练尤其是焦虑应对的训练与指导。";
//            }else if(ys[3]<=5&&ys[3]>=4){
//                com[3]="该生在学习中情绪特别紧张，在考试时非常慌乱，常担心自己考砸而不能专心考试，该做的也做不对造成不该有的失分。强烈建议对其进行有计划、有步骤的情绪自控特别是焦虑应对方面的训练和指导。";
//            }
//        }
//        diagnoseFinal.setComment(com);
        return diagnoseFinal;
    }

    private void biaozhun(StudyForm studyForm) {
        String biaozhun="20,25,25,20";
        studyForm.setScoreBz(biaozhun);
    }

    @Override
    public List<StudyForm> queryStudyForm() {

        return diagnoseDao.queryStudyFormByTeacher();
    }

    @Override
      public StudyFormDto queryLastStudyFormByName(String name) {

        StudyForm studyForm = diagnoseDao.queryLastStudyFormByName(name);
        StudyFormDto studyFormDto = new StudyFormDto();
        if (studyForm != null) {
            studyFormDto = Transformer.convertStudyFormDtoFromStudyForm(studyForm);
        }

        return studyFormDto;

    }


    @Override
    public StudyFormDto queryStudyFormByOpenId(String openId,String childOpenId) {
        StudyForm studyForm = diagnoseDao.queryStudyFormByOpenId(openId,childOpenId);
        StudyFormDto studyFormDto = new StudyFormDto();
        if (studyForm != null) {
            studyFormDto = Transformer.convertStudyFormDtoFromStudyForm(studyForm);
        }

        return studyFormDto;

    }

    @Override
    public List<Long> getAvgWithStudent(String openId) {

//        StudyForm studyForm = diagnoseDao.queryStudyFormByOpenId(openId);
        List<Long> Avg = new ArrayList<Long>();
//        Long fenxinAvg = MathToolsUtil.getSum(Transformer.convertListFromStudyFormsOfFenxin(studyForm));
//        Long yaodianAvg = MathToolsUtil.getSum(Transformer.convertListFromStudyFormsOfYaodian(studyForm));
//        Long xinxiAvg = MathToolsUtil.getSum(Transformer.convertListFromStudyFormsOfXinxi(studyForm));
//        Long jiaolvAvg = MathToolsUtil.getSum(Transformer.convertListFromStudyFormsOfJiaolv(studyForm));
//        Avg.add(fenxinAvg);
//        Avg.add(yaodianAvg);
//        Avg.add(xinxiAvg);
//        Avg.add(jiaolvAvg);
        return Avg;
    }

    //均分  分别传入4个纬度需要计算的分数
    @Override
    public List<Long> getAvgWithStudents() {

        List<StudyForm> allStudyForm = diagnoseDao.queryStudyFormByTeacher();
        List<Long> Avg = new ArrayList<Long>();
        Long fenxinAvg = MathToolsUtil.getAvgWithStudents(Transformer.convertListFromStudyFormsOfFenxin(allStudyForm));
        Long yaodianAvg = MathToolsUtil.getAvgWithStudents(Transformer.convertListFromStudyFormsOfYaodian(allStudyForm));
        Long xinxiAvg = MathToolsUtil.getAvgWithStudents(Transformer.convertListFromStudyFormsOfXinxi(allStudyForm));
        Long jiaolvAvg = MathToolsUtil.getAvgWithStudents(Transformer.convertListFromStudyFormsOfJiaolv(allStudyForm));

        Avg.add(fenxinAvg);
        Avg.add(yaodianAvg);
        Avg.add(xinxiAvg);
        Avg.add(jiaolvAvg);
        return Avg;
    }


    //标准差   分别传入4个纬度需要计算的分数
    @Override
    public List<Long> getStdWithStudents() {

        List<StudyForm> allStudyForm = diagnoseDao.queryStudyFormByTeacher();
        List<Long> Std = new ArrayList<Long>();
        Long fenxinStd = MathToolsUtil.getStdWithStudents(Transformer.convertListFromStudyFormsOfFenxin(allStudyForm));
        Long yaodianStd = MathToolsUtil.getStdWithStudents(Transformer.convertListFromStudyFormsOfYaodian(allStudyForm));
        Long xinxiStd = MathToolsUtil.getStdWithStudents(Transformer.convertListFromStudyFormsOfXinxi(allStudyForm));
        Long jiaolvStd = MathToolsUtil.getStdWithStudents(Transformer.convertListFromStudyFormsOfJiaolv(allStudyForm));

        Std.add(fenxinStd);
        Std.add(yaodianStd);
        Std.add(xinxiStd);
        Std.add(jiaolvStd);
        return Std;
    }

    //标准分   对应每个人 分别传入4个纬度需要计算的分数
    @Override
    public List<List<Long>> getStdScore() {

        List<StudyForm> allStudyForm = diagnoseDao.queryStudyFormByTeacher();
        List<List<Long>> stdScore = new ArrayList<List<Long>>();
        List<Long> fenxinStd = MathToolsUtil.getStdScore(Transformer.convertListFromStudyFormsOfFenxin(allStudyForm));
        List<Long> yaodianStd = MathToolsUtil.getStdScore(Transformer.convertListFromStudyFormsOfYaodian(allStudyForm));
        List<Long> xinxiStd = MathToolsUtil.getStdScore(Transformer.convertListFromStudyFormsOfXinxi(allStudyForm));
        List<Long> jiaolvStd = MathToolsUtil.getStdScore(Transformer.convertListFromStudyFormsOfJiaolv(allStudyForm));

        stdScore.add(fenxinStd);
        stdScore.add(yaodianStd);
        stdScore.add(xinxiStd);
        stdScore.add(jiaolvStd);
        return stdScore;
    }

    //转化为常模分数 c
    @Override
    public List<List<Long>> getRegularScore() {

        List<List<Long>> stdScores = getStdScore();
        List<List<Long>> stdScoresRegular = new ArrayList<List<Long>>();
        for (List<Long> stdScore : stdScores) {
            List<Long> stdScoreTemp = MathToolsUtil.getRegularScore(stdScore);
            stdScoresRegular.add(stdScoreTemp);
        }

        return stdScoresRegular;

    }


    //个体最终得分
    @Override
    public List<List<Long>> getFinalRegularScore() {

        List<List<Long>> stdScores = getRegularScore();
        List<List<Long>> stdScoresFinal = new ArrayList<List<Long>>();
        for (List<Long> stdScore : stdScores) {
            List<Long> stdTemp = MathToolsUtil.getFinalRegularScore(stdScore);
            stdScoresFinal.add(stdTemp);
        }
        return stdScoresFinal;

    }

    //个体最终得分_不分四个纬度
    @Override
    public List<Long> getFinalStdScore() {

        List<List<Long>> stdScores = getRegularScore();
        List<List<Long>> stdScoresFinal = new ArrayList<List<Long>>();
        for (List<Long> stdScore : stdScores) {
            List<Long> stdTemp = MathToolsUtil.getFinalRegularScore(stdScore);
            stdScoresFinal.add(stdTemp);
        }
        int length = stdScoresFinal.get(0).size();
        int weiDu = stdScoresFinal.size();
        List<Long> totalRegularScore = new ArrayList<Long>();

        for (int j = 0; j < length; j++) {
            long total = 0;
            for (int i = 0; i < weiDu; i++) {

                total += stdScoresFinal.get(i).get(j);
            }
//            double temp =  (double)(Math.round(total/4.0*10)/10.0);
            totalRegularScore.add(total / 4);
        }

        return totalRegularScore;
    }


    public List<StudyResultDto> getFinalCommentByOpenId(String openId){

        List<Long> goal = getAvgWithStudent(openId);
        List<StudyResultDto> studyResultDtos = new ArrayList<StudyResultDto>();
        for(int i=0;i<4;i++) {
            StudyResultDto studyResultDto = new StudyResultDto();
            studyResultDto.setOpenId(openId);
            studyResultDto.setScore(goal.get(i));
            switch (i){
                case 0:
                    studyResultDto.setContent(StudyResultUtil.getFenXin(goal.get(i)));
                    break;
                case 1:
                    studyResultDto.setContent(StudyResultUtil.getYaoDian(goal.get(i)));
                    break;
                case 2:
                    studyResultDto.setContent(StudyResultUtil.getXingxi(goal.get(i)));
                    break;
                case 3:
                    studyResultDto.setContent(StudyResultUtil.getJiaoLv(goal.get(i)));
                    break;
                default:
                    break;
            }
            studyResultDtos.add(studyResultDto);
        }

      return studyResultDtos;

    }

    public DiagnoseAllChildren queryOneTeacherAllChildren(String openId) {
        DiagnoseAllChildren diagnoseAllChildren=new DiagnoseAllChildren();
        UserEntity userEntity1 = userDao.queryUserByOpenId(openId);//查询老师的名字
        String name=userEntity1.getName();
        List<UserEntity> userEntities=userDao.queryChildrenUsers(name);//查询该老师的学生
        List<String> childrenOpenid=new ArrayList<String>();//存储该老师的学生的家长记录时的openid
        for(UserEntity userEntity:userEntities){
            diagnoseAllChildren.getChildrenName().add(userEntity.getName());//存储学生的姓名
            childrenOpenid.add(userEntity.getOpenId());
        }
        for(int i=0;i<childrenOpenid.size();i++){
            StudyForm studyForm_t=diagnoseDao.queryStudyFormByOpenId(openId,childrenOpenid.get(i));//获得老师写的
            if(studyForm_t==null){
                diagnoseAllChildren.getScoreYs_t().add("none");
                diagnoseAllChildren.getScoreBz_t().add("none");
            }else{
                diagnoseAllChildren.getScoreYs_t().add(studyForm_t.getScoreYs());
                diagnoseAllChildren.getScoreBz_t().add(studyForm_t.getScoreBz());
            }
            StudyForm studyForm_p=diagnoseDao.queryStudyFormByOpenId(childrenOpenid.get(i),"none");//获得家长写的
            if(studyForm_p==null){
                diagnoseAllChildren.getScoreYs_p().add("none");
                diagnoseAllChildren.getScoreBz_p().add("none");
            }else{
                diagnoseAllChildren.getScoreYs_p().add(studyForm_p.getScoreYs());
                diagnoseAllChildren.getScoreBz_p().add(studyForm_p.getScoreBz());
            }
        }
        return diagnoseAllChildren;
    }

    public DiagnoseAllChildren queryAllChildren() {
        DiagnoseAllChildren diagnoseAllChildren=new DiagnoseAllChildren();
        List<UserEntity> userEntities_t=userDao.queryAllTeacher();//查询所有的老师
        List<String> teacherOpenid=new ArrayList<String>();
        for(UserEntity userEntity:userEntities_t){
            teacherOpenid.add(userEntity.getOpenId());
        }
        for(int j=0;j<teacherOpenid.size();j++) {
            UserEntity userEntity1 = userDao.queryUserByOpenId(teacherOpenid.get(j));//查询老师的名字
            String name = userEntity1.getName();
            List<UserEntity> userEntities = userDao.queryChildrenUsers(name);//查询该老师的学生
            List<String> childrenOpenid = new ArrayList<String>();//存储该老师的学生的家长记录时的openid
            for (UserEntity userEntity : userEntities) {
                diagnoseAllChildren.getChildrenName().add(userEntity.getName());//存储学生的姓名
                childrenOpenid.add(userEntity.getOpenId());
            }
            for (int i = 0; i < childrenOpenid.size(); i++) {
                StudyForm studyForm_t = diagnoseDao.queryStudyFormByOpenId(teacherOpenid.get(j), childrenOpenid.get(i));//获得老师写的
                if (studyForm_t == null) {
                    diagnoseAllChildren.getScoreYs_t().add("none");
                    diagnoseAllChildren.getScoreBz_t().add("none");
                } else {
                    diagnoseAllChildren.getScoreYs_t().add(studyForm_t.getScoreYs());
                    diagnoseAllChildren.getScoreBz_t().add(studyForm_t.getScoreBz());
                }
                StudyForm studyForm_p = diagnoseDao.queryStudyFormByOpenId(childrenOpenid.get(i), "none");//获得家长写的
                if (studyForm_p == null) {
                    diagnoseAllChildren.getScoreYs_p().add("none");
                    diagnoseAllChildren.getScoreBz_p().add("none");
                } else {
                    diagnoseAllChildren.getScoreYs_p().add(studyForm_p.getScoreYs());
                    diagnoseAllChildren.getScoreBz_p().add(studyForm_p.getScoreBz());
                }
            }
        }
        return diagnoseAllChildren;
    }
}





