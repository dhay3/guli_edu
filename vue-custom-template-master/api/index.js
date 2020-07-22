import request from '@/utils/request'

export default {
  //查询热门课程和名师
  getTopDESC() {
    return request({
      url: `/eduservice/front`,
      method: 'get'
    })
  }
}
