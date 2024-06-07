import request from '@/utils/request'
export const admLoginService = ({ username, password }) =>
  request.post('/adm/login', { username, password })
export const admGetCourseListService = ({
  name,
  teacher,
  year,
  semester,
  time,
  place,
  selectState,
  courseState,
  pageSize,
  currentPage
}) =>
  request.get('/adm/cur/page', {
    params: {
      name,
      teacher,
      year,
      semester,
      time,
      place,
      selectState,
      courseState,
      pageSize,
      currentPage
    }
  })
export const admChangeSelectStateService = (ids, state) =>
  request.put('/adm/cur/selectstate/' + ids + '/' + state)
export const admChangeCourseStateService = (ids, state) =>
  request.put('/adm/cur/coursestate/' + ids + '/' + state)
export const admUpdateCourseService = (data) => request.put('/adm/cur', data)
export const admInsertCourseService = (data) => request.post('/adm/cur', data)
export const admDeleteCourseService = (id) => request.delete('/adm/cur/' + id)
export const admGetTeacherListService = ({ name, pageSize, currentPage }) =>
  request.get('/adm/tea/page', { params: { name, pageSize, currentPage } })
export const admUpdateTeacherService = (data) => request.put('/adm/tea', data)
export const admInsertTeacherService = (data) => request.post('/adm/tea', data)
export const admDeleteTeacherService = (id) => request.delete('/adm/tea/' + id)
export const admGetStudentListService = ({
  name,
  department,
  pageSize,
  currentPage
}) =>
  request.get('/adm/stu/page', {
    params: { name, department, pageSize, currentPage }
  })
export const admInsertStudentService = (data) => request.post('/adm/stu', data)
export const admUpdateStudentService = (data) => request.put('/adm/stu', data)
export const admDeleteStudentService = (id) => request.delete('/adm/stu/' + id)
export const admEditPasswordService = (data) =>
  request.put('/adm/password', data)
export const admGetInfoService = (id) => request.get('/adm/info/' + id)
