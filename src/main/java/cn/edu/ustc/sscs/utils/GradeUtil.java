package cn.edu.ustc.sscs.utils;

import cn.edu.ustc.sscs.common.constant.GradeConstant;
import cn.edu.ustc.sscs.domain.vo.GradeVO;
import cn.hutool.core.collection.CollUtil;

import java.util.List;

/**
 * 成绩计算工具类
 */
public class GradeUtil {

    public static Integer calObtainedCredit(List<GradeVO> gradeList) {
        if(CollUtil.isEmpty(gradeList)){
            return GradeConstant.OBTAINED_CREDITS_DEFAULT;
        }
        Integer obtainedCredit = 0;
        for(GradeVO grade : gradeList) {
            if(grade.getIsPassed()) {
                obtainedCredit += grade.getCredit();
            }
        }
        return obtainedCredit;
    }

    public static Integer calFailedCredit(List<GradeVO> gradeList) {
        if(CollUtil.isEmpty(gradeList)){
            return GradeConstant.FAILED_CREDITS_DEFAULT;
        }
        Integer failedCredit = 0;
        for(GradeVO grade : gradeList) {
            if(!grade.getIsPassed()) {
                failedCredit += grade.getCredit();
            }
        }
        return failedCredit;
    }

    public static Float calGpa(List<GradeVO> gradeList) {
        if(CollUtil.isEmpty(gradeList)){
            return GradeConstant.GPA_DEFAULT;
        }
        Integer totalCredit = 0;
        float totalWeightedGradePoint = 0f;
        for(GradeVO grade : gradeList) {
            if(grade.getIsPassed()) {
                totalWeightedGradePoint += grade.getCredit() * grade.getGradePoint();
            }
            totalCredit += grade.getCredit();
        }
        return totalWeightedGradePoint / totalCredit;
    }
}
