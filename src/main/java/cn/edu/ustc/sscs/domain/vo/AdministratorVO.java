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
public class AdministratorVO implements Serializable {
    private Integer id;
    private String username;
}
