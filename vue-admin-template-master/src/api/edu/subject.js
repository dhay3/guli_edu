import request from '@/utils/request'

export default {
  //查询所有课程分类
  listSubject() {
    return request({
      url: `http://localhost:9001/eduservice/subject`,
      method: 'get',
    })
  },
  //根据title返回二级title
  getSubTitlesByParentTitle(title){
    return request({
      url: `http://localhost:9001/eduservice/subject/${title}`,
      method: 'get',
    })
  }
}




