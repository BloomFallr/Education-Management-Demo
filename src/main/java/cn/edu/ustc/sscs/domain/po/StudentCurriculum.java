package cn.edu.ustc.sscs.domain.po;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentCurriculum {
    @Schema(description = "编号")
    private Integer id;
    @Schema(description = "学生编号")
    private Integer studentId;
    @Schema(description = "课程编号")
    private Integer curriculumId;
    @Schema(description = "是否及格")
    private Boolean isPassed;
    @Schema(description = "绩点")
    private Float gradePoint;
}
