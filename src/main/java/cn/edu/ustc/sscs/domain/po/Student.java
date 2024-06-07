package cn.edu.ustc.sscs.domain.po;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class Student extends User implements Serializable {
    @Schema(description = "姓名")
    private String name;
    @Schema(description = "学院")
    private String department;
    @Schema(description = "已获学分")
    private Integer obtainedCredit;
    @Schema(description = "不及格学分")
    private Integer failedCredit;
    @Schema(description = "平均绩点")
    private Float gpa;
}
