package cn.edu.ustc.sscs.domain.po;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Curriculum implements Serializable {
    @Schema(description = "编号")
    private Integer id;
    @Schema(description = "课堂号")
    private String classId;
    @Schema(description = "课程名称")
    private String name;
    @Schema(description = "任课教师姓名")
    private String teacher;
    @Schema(description = "学分")
    private Integer credit;
    @Schema(description = "已选人数")
    private Integer selectedStudents;
    @Schema(description = "人数上限")
    private Integer maxStudents;
    @Schema(description = "学年")
    private Integer year;
    @Schema(description = "学期")
    private String semester;
    @Schema(description = "时间")
    private String time;
    @Schema(description = "地点")
    private String place;
    @Schema(description = "选课状态")
    private Integer selectState;
    @Schema(description = "课程状态")
    private Integer courseState;
}
