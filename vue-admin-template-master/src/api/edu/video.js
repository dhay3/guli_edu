import request from '@/utils/request'

export default {

  //添加小节
  saveVideo(video) {
    return request({
      url: `http://localhost:9001/eduservice/video`,
      method: 'post',
      data: video
    })
  },
  //添加小节
  updateVideo(video) {
    return request({
      url: `http://localhost:9001/eduservice/video`,
      method: 'put',
      data: video
    })
  },
  //添加小节
  deleteVideoById(id) {
    return request({
      url: `http://localhost:9001/eduservice/video/${id}`,
      method: 'delete',
    })
  },
  //根据id查询video信息
  getVideoById(id) {
    return request({
      url: `http://localhost:9001/eduservice/video/${id}`,
      method: 'get',
    })
  },
  //删除aliyun中的视频
  deleteVoDByVideoId(id) {
    return request({
      url: `http://localhost:9001/eduvod/video/${id}`,
      method: 'delete',
    })
  },
}




