import request from '@/utils/request'
export const stuLoginService = ({ username, password }) =>
  request.post('/stu/login', { username, password })
export const stuSelectCourseService = (studentId, curriculumId) =>
  request.post('/stu/cur/select/' + studentId + '/' + curriculumId)
export const stuCancelCourseService = (studentId, curriculumId) =>
  request.delete('/stu/cur/cancel/' + studentId + '/' + curriculumId)
export const stuGetCourseListService = ({
  name,
  teacher,
  time,
  place,
  selectState,
  courseState,
  pageSize,
  currentPage
}) =>
  request.get('/stu/cur/page', {
    params: {
      name,
      teacher,
      time,
      place,
      selectState,
      courseState,
      pageSize,
      currentPage
    }
  })
export const stuGetSelectedCourseListService = (id) =>
  request.get('/stu/cur/selected-list/' + id)
export const stuGetInfoService = (id) => request.get('/stu/info/' + id)
export const stuGetGradeService = (id) => request.get('/stu/grade/' + id)
export const stuEditPasswordService = (data) =>
  request.put('/stu/password', data)
