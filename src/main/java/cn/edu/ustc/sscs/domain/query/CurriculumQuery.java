package cn.edu.ustc.sscs.domain.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


import java.io.Serializable;


@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurriculumQuery extends PageQuery implements Serializable {
    @Schema(description = "课程名称")
    private String name;
    @Schema(description = "任课教师姓名")
    private String teacher;
    @Schema(description = "学年")
    private Integer year;
    @Schema(description = "学期")
    private String semester;
    @Schema(name = "上课时间")
    private String time;
    @Schema(description = "上课地点")
    private String place;
    @Schema(description = "选课状态")
    private Integer selectState;
    @Schema(description = "课程状态")
    private Integer courseState;
}
