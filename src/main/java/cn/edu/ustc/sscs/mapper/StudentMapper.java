package cn.edu.ustc.sscs.mapper;

import cn.edu.ustc.sscs.domain.po.Student;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StudentMapper extends BaseMapper<Student> {
}
