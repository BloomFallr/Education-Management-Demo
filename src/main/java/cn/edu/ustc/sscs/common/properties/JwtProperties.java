package cn.edu.ustc.sscs.common.properties;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * JWT 配置项
 */
@Component
@Data
@ConfigurationProperties(prefix = "sscs.jwt")
public class JwtProperties {
    @Schema(description = "管理员密钥")
    private String admSecret;
    @Schema(description = "管理员请求头名称")
    private String admTokenName;
    @Schema(description = "学生密钥")
    private String stuSecret;
    @Schema(description = "学生请求头名称")
    private String stuTokenName;
    @Schema(description = "教师密钥")
    private String teaSecret;
    @Schema(description = "教师请求头名称")
    private String teaTokenName;
    @Schema(description = "超时时间")
    private Long ttlMillis;
}
