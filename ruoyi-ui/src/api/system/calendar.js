import request from '@/utils/request'

// 查询日历列表
export function listCalendar(query) {
  return request({
    url: '/system/calendar/list',
    method: 'get',
    params: query
  })
}

// 查询日历详细
export function getCalendar(id) {
  return request({
    url: '/system/calendar/' + id,
    method: 'get'
  })
}

// 新增日历
export function addCalendar(data) {
  return request({
    url: '/system/calendar',
    method: 'post',
    data: data
  })
}

// 修改日历
export function updateCalendar(data) {
  return request({
    url: '/system/calendar',
    method: 'put',
    data: data
  })
}

// 删除日历
export function delCalendar(id) {
  return request({
    url: '/system/calendar/' + id,
    method: 'delete'
  })
}
