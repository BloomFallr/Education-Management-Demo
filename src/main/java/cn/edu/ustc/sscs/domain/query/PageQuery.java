package cn.edu.ustc.sscs.domain.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageQuery implements Serializable {
    @Schema(description = "当前页数", defaultValue = "1")
    private Integer currentPage = 1;
    @Schema(description = "每页条数", defaultValue = "10")
    private Integer pageSize = 10;
}
