package cn.edu.ustc.sscs.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "学生单科成绩")
public class GradeVO implements Serializable {
    private String classId;
    private String name;
    private Integer credit;
    private Integer year;
    private String semester;
    private Boolean isPassed;
    private Float gradePoint;
}
