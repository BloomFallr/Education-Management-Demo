package cn.edu.ustc.sscs.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentVO implements Serializable {
    private Integer id;
    private String username;
    private String name;
    private String department;
    private Integer obtainedCredit;
    private Integer failedCredit;
    private Float gpa;
}
