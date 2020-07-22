import request from '@/utils/request'
//通过modules\user.js来调用
export function login(data) {
  return request({
    // url: '/vue-admin-template/user/login',
    //修改登入url到后端对应地址
    url: 'http://localhost:9001/eduservice/user/login',
    method: 'post',
    data
  })
}

export function getInfo(token) {
  return request({
    url: 'http://localhost:9001/eduservice/user/info',
    method: 'get',
    params: { token }
  })
}

export function logout() {
  return request({
    url: '/vue-admin-template/user/logout',
    method: 'post'
  })
}
