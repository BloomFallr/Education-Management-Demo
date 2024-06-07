package cn.edu.ustc.sscs.domain.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentQuery extends PageQuery{
    @Schema(description = "姓名")
    private String name;
    @Schema(description = "学院")
    private String department;
}
