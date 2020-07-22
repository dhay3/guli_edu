import request from '@/utils/request'
//通过js跨域调用后端中的api接口
export default {
  //对应api中的参数
  getTeacherListPage(current, size, teacherQuery) {
    return request({
      // url: 'http://localhost:8081/eduservice/teacher/pageTeacherCondition',

      //第一种方式通过拼接参数
      // url: 'http://localhost:8081/eduservice/teacher/pageTeacherCondition'+cur+'/'+size,

      //第二种方式通过es6, 通过${}来嵌入变量
      url: `http://localhost:9001/eduservice/teacher/pageTeacherCondition/${current}/${size}`,
      //提交方式
      method: 'post',
      //对应接口通过RequestBody来接收参数,就要使用data, 和jquery相似
      //data表示将对象转换为json传到后端
      data: teacherQuery
    })
  },
  //删除讲师
  deleteTeacherById(id) {
    return request({
      url: `http://localhost:9001/eduservice/teacher/${id}`,
      //vue中封装了delete无需添加隐藏域
      method: 'delete',
    })
  },
  //增加讲师
  saveTeacher(teacher) {
    return request({
      url: `http://localhost:9001/eduservice/teacher`,
      method: 'post',
      //由于是通过json传递
      data: teacher
    })
  },
  //获取讲师信息
  getTeacherInfo(id) {
    return request({
      //使用nginx对外开放的端口
      //当访问路径中eduservice时,通过nginx代理到8001端口
      url: `http://localhost:9001/eduservice/teacher/${id}`,
      method: 'get',
    })
  },
  //修改讲师
  updateTeacherInfo(teacher) {
    return request({
      url: `http://localhost:9001/eduservice/teacher`,
      method: 'put',
      data: teacher
    })
  },

}




