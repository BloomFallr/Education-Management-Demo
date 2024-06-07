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
@Schema(name = "课程选课记录")
public class EnrollmentVO implements Serializable {
    private Integer id;
    private String studentUsername;
    private String studentName;
    private Boolean isPassed;
    private Float gradePoint;
}
