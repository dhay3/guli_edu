package com.chz.eduservice.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chz.eduservice.entity.domain.Course;
import com.chz.eduservice.entity.frontVo.CourseWebVo;
import com.chz.eduservice.entity.frontVo.FrontCourseQueryVo;
import com.chz.eduservice.entity.vo.ChapterVo;
import com.chz.eduservice.feign.OrderClientFeign;
import com.chz.eduservice.service.ChapterService;
import com.chz.eduservice.service.CourseService;
import com.chz.response.ResponseBo;
import com.chz.utils.JwtUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @Author: CHZ
 * @DateTime: 2020/7/26 18:09
 * @Description: TODO
 */
@RestController
@RequestMapping("/eduservice/front/course")
public class FrontCourseController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private ChapterService chapterService;
    @Autowired
    private OrderClientFeign orderClientFeign;

    @ApiOperation("带条件分页查询")
    @PostMapping("/{currentPage}/{pageSize}")
    public ResponseBo getFrontCoursesByCondition(@PathVariable Integer currentPage,
                                                 @PathVariable Integer pageSize,
                                                 @RequestBody(required = false) FrontCourseQueryVo frontCourseQueryVo) {
        Page<Course> coursePage = new Page<>(currentPage, pageSize);
        Map<String, Object> map = courseService.getFrontCoursesByCondition(coursePage, frontCourseQueryVo);
        return ResponseBo.ok().data(map);
    }

    @GetMapping("/{courseId}")
    public ResponseBo getFrontCourseDetailsByCourseId(@PathVariable String courseId,
                                                      HttpServletRequest request) {
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        //根据课程id查询课程信息
        CourseWebVo courseWebVo = courseService.getFrontCourseDetailsByCourseId(courseId);
        //根据课程id查询章节和小节
        List<ChapterVo> ChapterVideoList = chapterService.getChapterAndVideoByCourseId(courseId);
        //根据课程id和用户id查询当前课程是否已经支付过了
        Object status = orderClientFeign.getOrderStatusInDB(courseId, memberId)
                .getData();
        return ResponseBo.ok().data("courseWebVo", courseWebVo)
                .data("ChapterVideoList", ChapterVideoList)
                .data("status", status);
    }


}
