import request from '@/utils/request'
export const teaLoginService = ({ username, password }) =>
  request.post('/tea/login', { username, password })
export const teaGetTaughtCourseListService = (id) =>
  request.get('/tea/cur/taught-list/' + id)
export const teaUpdateGradeService = (data) => request.put('/tea/grade', data)
export const teaGetEnrollmentService = (id) =>
  request.get('/tea/cur/enrollment/' + id)
export const teaGetInfoService = (id) => request.get('/tea/info/' + id)
export const teaEditPasswordService = (data) =>
  request.put('/tea/password', data)
export const teaFinishCourseService = (id) =>
  request.put('/tea/cur/finish/' + id)
