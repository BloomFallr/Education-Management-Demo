package cn.edu.ustc.sscs.service;

import cn.edu.ustc.sscs.domain.vo.CurriculumVO;
import cn.edu.ustc.sscs.domain.result.PageResult;
import cn.edu.ustc.sscs.domain.po.Curriculum;
import cn.edu.ustc.sscs.domain.query.CurriculumQuery;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface ICurriculumService extends IService<Curriculum> {

    /**
     * 管理员或学生分页条件查询课程
     * @param query
     * @return
     */
    PageResult<CurriculumVO> queryCurriculumPage(CurriculumQuery query);

    /**
     * 学生查询已选且进行中课程
     * @param id
     * @return
     */
    List<CurriculumVO> querySelectedCurriculum(Integer id);

    /**
     * 教师查询任教且进行中课程
     * @param id
     * @return
     */
    List<CurriculumVO> queryTaughtCurriculum(Integer id);

    /**
     * 管理员添加课程
     * @param curriculum
     */
    void saveCurriculum(Curriculum curriculum);

    /**
     * 管理员删除课程
     * @param id
     */
    void deleteCurriculumById(Integer id);

    /**
     * 管理员更新课程
     * @param curriculum
     */
    void updateCurriculumById(Curriculum curriculum);

    /**
     * 管理员修改课程选课状态
     * @param ids
     * @param state
     */
    void updateSelectStateBatchById(List<Integer> ids, Integer state);

    /**
     * 教师结束课程
     * @param id
     */
    void finishCourseById(Integer id);

}
