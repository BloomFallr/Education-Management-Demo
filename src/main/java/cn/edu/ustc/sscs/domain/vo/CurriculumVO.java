package cn.edu.ustc.sscs.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CurriculumVO implements Serializable {
    private Integer id;
    private String classId;
    private String name;
    private String teacher;
    private Integer credit;
    private Integer selectedStudents;
    private Integer maxStudents;
    private Integer year;
    private String semester;
    private String time;
    private String place;
    private Short selectState;
    private Short courseState;
}
