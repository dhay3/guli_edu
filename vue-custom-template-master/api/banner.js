import request from '@/utils/request'

export default {
  //查询前四条banner
  getBannersTopDESC() {
    return request({
      url: `/edusmc/bannercustom`,
      method: 'get'
    })
  }
}
