package cn.edu.ustc.sscs.domain.result;

import cn.edu.ustc.sscs.common.constant.ResultConstant;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 统一响应结果封装类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result implements Serializable {
    @Schema(description = "响应码")
    private Integer code;
    @Schema(description = "返回信息")
    private String msg;
    @Schema(description = "返回数据")
    private Object data;

    public static Result success(){
        return new Result(ResultConstant.SUCCESS, null, null);
    }
    public static Result success(Object data){
        return new Result(ResultConstant.SUCCESS, null, data);
    }
    public static Result error(String msg){
        return new Result(ResultConstant.FAILURE, msg, null);
    }
}
