export const formateDate = (time) => {
  const year = time.getFullYear()
  const month = (time.getMonth() + 1).toString().padStart(2, '0')
  const day = time.getDate().toString().padStart(2, '0')
  const week = '星期' + '日一二三四五六'.charAt(time.getDay())
  return year + '年' + month + '月' + day + '日' + ' ' + week
}
