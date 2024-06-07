package cn.edu.ustc.sscs.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "学生成绩单")
public class GradeReportVO implements Serializable {

    private Integer obtainedCredit;
    private Integer failedCredit;
    private Float gpa;
    private List<GradeVO> gradeList;

}
