import request from '@/utils/request'

export default {
  //查询所有课程分类
  saveCourse(courseInfo) {
    return request({
      url: `http://localhost:9001/eduservice/course`,
      method: 'post',
      data: courseInfo
    })
  },
  //查询所有讲师, 用于下拉列表显示
  getTeacherList() {
    return request({
      url: `http://localhost:9001/eduservice/teacher`,
      method: 'get',
    })
  },
  //根据课程id查询课程信息
  getCourseInfoById(id) {
    return request({
      url: `http://localhost:9001/eduservice/course/${id}`,
      method: 'get'
    })
  },
  //更新课程信息
  updateCourseInfo(courseInfo) {
    return request({
      url: `http://localhost:9001/eduservice/course`,
      method: 'put',
      data: courseInfo
    })
  },
  //获取确认页面封装对象
  getCourseAllInfoById(CourseId) {
    return request({
      url: `http://localhost:9001/eduservice/course/all/${CourseId}`,
      method: 'get',
    })
  },
  //最总发布course, 即将course status 修改为normal
  publishCourse(CourseId) {
    return request({
      url: `http://localhost:9001/eduservice/course/publish/${CourseId}`,
      method: 'post',
    })
  },
  //分页展示课程信息
  pageCourseOnCondition(cur, size, coursePage) {
    return request({
      url: `http://localhost:9001/eduservice/course/${cur}/${size}`,
      method: 'post',
      data: coursePage
    })
  },

  removeCourseInfoByCourseId(courseId){
    return request({
      url: `http://localhost:9001/eduservice/course/${courseId}`,
      method: 'delete',
    })
  }
}




