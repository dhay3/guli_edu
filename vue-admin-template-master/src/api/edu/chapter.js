import request from '@/utils/request'

export default {
  //查询对应courseId所有章节和小节
  listSubject(id) {
    return request({
      url: `http://localhost:9001/eduservice/chapter/${id}`,
      method: 'get',
    })
  },
  //添加章节
  saveChapter(chapter){
    return request({
      url: `http://localhost:9001/eduservice/chapter`,
      method: 'post',
      data: chapter
    })
  },
  //添加章节
  updateChapter(chapter){
    return request({
      url: `http://localhost:9001/eduservice/chapter`,
      method: 'put',
      data: chapter
    })
  },
  //添加章节
  deleteChapterById(id){
    return request({
      url: `http://localhost:9001/eduservice/chapter/${id}`,
      method: 'delete',
    })
  },
  //根据章节id查询对应章节信息,用于回显数据
  getChapterInfoById(id){
    return request({
      url: `http://localhost:9001/eduservice/chapter/callback/${id}`,
      method: 'get',
    })
  }
}




