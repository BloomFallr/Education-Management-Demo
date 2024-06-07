<script setup>
import { ref, onMounted } from 'vue'
import { stuGetSelectedCourseListService } from '@/api/stu'
import { useUserStore } from '@/stores'

const objectSpanMethod = ({ row, rowIndex, columnIndex }) => {
  if (columnIndex === 0) {
    if (rowIndex <= 4) {
      if (rowIndex === 0) {
        return {
          rowspan: 5,
          colspan: 1
        }
      } else {
        return {
          rowspan: 0,
          colspan: 0
        }
      }
    } else if (rowIndex >= 5 && rowIndex <= 9) {
      if (rowIndex === 5) {
        return {
          rowspan: 5,
          colspan: 1
        }
      } else {
        return {
          rowspan: 0,
          colspan: 0
        }
      }
    } else {
      if (rowIndex === 10) {
        return {
          rowspan: 3,
          colspan: 1
        }
      } else {
        return {
          rowspan: 0,
          colspan: 0
        }
      }
    }
  } else if (columnIndex === 2) {
    if (row.mon.name !== undefined) {
      return {
        rowspan: row.mon.end - row.mon.start + 1,
        colspan: 1
      }
    } else {
      return {
        rowspan: 1,
        colspan: 1
      }
    }
  } else if (columnIndex === 3) {
    if (row.tue.name !== undefined) {
      return {
        rowspan: row.tue.end - row.tue.start + 1,
        colspan: 1
      }
    } else {
      return {
        rowspan: 1,
        colspan: 1
      }
    }
  } else if (columnIndex === 4) {
    if (row.wed.name !== undefined) {
      return {
        rowspan: row.wed.end - row.wed.start + 1,
        colspan: 1
      }
    } else {
      return {
        rowspan: 1,
        colspan: 1
      }
    }
  } else if (columnIndex === 5) {
    if (row.thu.name !== undefined) {
      return {
        rowspan: row.thu.end - row.thu.start + 1,
        colspan: 1
      }
    } else {
      return {
        rowspan: 1,
        colspan: 1
      }
    }
  } else if (columnIndex === 6) {
    if (row.fri.name !== undefined) {
      return {
        rowspan: row.fri.end - row.fri.start + 1,
        colspan: 1
      }
    } else {
      return {
        rowspan: 1,
        colspan: 1
      }
    }
  } else if (columnIndex === 7) {
    if (row.sat.name !== undefined) {
      return {
        rowspan: row.sat.end - row.sat.start + 1,
        colspan: 1
      }
    } else {
      return {
        rowspan: 1,
        colspan: 1
      }
    }
  } else if (columnIndex === 8) {
    if (row.sun.name !== undefined) {
      return {
        rowspan: row.sun.end - row.sun.start + 1,
        colspan: 1
      }
    } else {
      return {
        rowspan: 1,
        colspan: 1
      }
    }
  }
}
//设置样式
const tableCellStyle = ({ row, rowIndex, columnIndex }) => {
  if (columnIndex === 0) {
    if (rowIndex === 0) {
      return {
        background: '#ffffc1',
        color: 'black',
        fontWeight: 600
      }
    } else if (rowIndex === 5) {
      return {
        background: '#ade5ce',
        color: 'black',
        fontWeight: 600
      }
    } else if (rowIndex === 10) {
      return {
        background: '#f2bfbf',
        color: 'black',
        fontWeight: 600
      }
    }
  } else if (columnIndex === 1) {
    return {
      background: '#909589',
      color: 'black',
      fontWeight: 400
    }
  } else if (columnIndex === 2) {
    if (row.mon.name !== undefined) {
      return {
        background: '#d9e5fd',
        color: 'black',
        fontWeight: 400
      }
    }
  } else if (columnIndex === 3) {
    if (row.tue.name !== undefined) {
      return {
        background: '#d9e5fd',
        color: 'black',
        fontWeight: 400
      }
    }
  } else if (columnIndex === 4) {
    if (row.wed.name !== undefined) {
      return {
        background: '#d9e5fd',
        color: 'black',
        fontWeight: 400
      }
    }
  } else if (columnIndex === 5) {
    if (row.thu.name !== undefined) {
      return {
        background: '#d9e5fd',
        color: 'black',
        fontWeight: 400
      }
    }
  } else if (columnIndex === 6) {
    if (row.fri.name !== undefined) {
      return {
        background: '#d9e5fd',
        color: 'black',
        fontWeight: 400
      }
    }
  } else if (columnIndex === 7) {
    if (row.sat.name !== undefined) {
      return {
        background: '#d9e5fd',
        color: 'black',
        fontWeight: 400
      }
    }
  } else if (columnIndex === 8) {
    if (row.sun.name !== undefined) {
      return {
        background: '#d9e5fd',
        color: 'black',
        fontWeight: 400
      }
    }
  }
}
const timetable = [
  {
    period: '上午',
    section: '1',
    mon: {},
    tue: {},
    wed: {},
    thu: {},
    fri: {},
    sat: {},
    sun: {}
  },
  {
    period: '上午',
    section: '2',
    mon: {},
    tue: {},
    wed: {},
    thu: {},
    fri: {},
    sat: {},
    sun: {}
  },
  {
    period: '上午',
    section: '3',
    mon: {},
    tue: {},
    wed: {},
    thu: {},
    fri: {},
    sat: {},
    sun: {}
  },
  {
    period: '上午',
    section: '4',
    mon: {},
    tue: {},
    wed: {},
    thu: {},
    fri: {},
    sat: {},
    sun: {}
  },
  {
    period: '上午',
    section: '5',
    mon: {},
    tue: {},
    wed: {},
    thu: {},
    fri: {},
    sat: {},
    sun: {}
  },
  {
    period: '下午',
    section: '6',
    mon: {},
    tue: {},
    wed: {},
    thu: {},
    fri: {},
    sat: {},
    sun: {}
  },
  {
    period: '下午',
    section: '7',
    mon: {},
    tue: {},
    wed: {},
    thu: {},
    fri: {},
    sat: {},
    sun: {}
  },
  {
    period: '下午',
    section: '8',
    mon: {},
    tue: {},
    wed: {},
    thu: {},
    fri: {},
    sat: {},
    sun: {}
  },
  {
    period: '下午',
    section: '9',
    mon: {},
    tue: {},
    wed: {},
    thu: {},
    fri: {},
    sat: {},
    sun: {}
  },
  {
    period: '下午',
    section: '10',
    mon: {},
    tue: {},
    wed: {},
    thu: {},
    fri: {},
    sat: {},
    sun: {}
  },
  {
    period: '晚上',
    section: '11',
    mon: {},
    tue: {},
    wed: {},
    thu: {},
    fri: {},
    sat: {},
    sun: {}
  },
  {
    period: '晚上',
    section: '12',
    mon: {},
    tue: {},
    wed: {},
    thu: {},
    fri: {},
    sat: {},
    sun: {}
  },
  {
    period: '晚上',
    section: '13',
    mon: {},
    tue: {},
    wed: {},
    thu: {},
    fri: {},
    sat: {},
    sun: {}
  }
]
const timeTableData = ref([])
const userStore = useUserStore()
const week = ['mon', 'tue', 'wed', 'thu', 'fri', 'sat', 'sun']
const dayOfWeekMap = {
  一: 1,
  二: 2,
  三: 3,
  四: 4,
  五: 5,
  六: 6,
  日: 7
}
const regex1 = /周(一|二|三|四|五|六|日)第(\d+)到(\d+)节/
const regex2 = /周(一|二|三|四|五|六|日)第(\d+)节/
const merge = async () => {
  const res = await stuGetSelectedCourseListService(userStore.id)
  timeTableData.value = res.data.data
  console.log(timeTableData.value)
  for (let i = 0; i < timeTableData.value.length; i++) {
    let substrings = timeTableData.value[i].time.split(',')
    for (let j = 0; j < substrings.length; j++) {
      let matches1 = regex1.exec(substrings[j])
      let matches2 = regex2.exec(substrings[j])
      if (matches1) {
        let dayOfWeek = dayOfWeekMap[matches1[1]]
        let sectionStart = parseInt(matches1[2])
        let sectionEnd = parseInt(matches1[3])
        let day = week[dayOfWeek - 1]
        timetable[sectionStart - 1][day] = {
          name: timeTableData.value[i].name,
          teacher: timeTableData.value[i].teacher,
          place: timeTableData.value[i].place,
          start: sectionStart,
          end: sectionEnd
        }
      } else if (matches2) {
        let dayOfWeek = dayOfWeekMap[matches2[1]]
        let section = parseInt(matches2[2])
        let day = week[dayOfWeek - 1]
        timetable[section - 1][day] = {
          name: timeTableData.value[i].name,
          teacher: timeTableData.value[i].teacher,
          place: timeTableData.value[i].place,
          start: section,
          end: section
        }
      }
    }
  }
}
onMounted(() => {
  merge()
})
</script>
<template>
  <div class="timetable">
    <el-table
      :data="timetable"
      :header-cell-style="{
        background: '#b69e9e',
        color: 'black',
        fontWeight: 1000
      }"
      :span-method="objectSpanMethod"
      :cell-style="tableCellStyle"
      :row-style="{ height: '80px' }"
      border
      align="center"
    >
      <el-table-column prop="period" label="时间" width="50" align="center">
      </el-table-column>
      <el-table-column prop="section" label="节次" width="50" align="center">
      </el-table-column>
      <el-table-column prop="mon" label="星期一" width="150" align="center">
        <template v-slot="scope">
          <h4>{{ scope.row.mon.name }}</h4>
          <h5>{{ scope.row.mon.teacher }}</h5>
          <h5>{{ scope.row.mon.place }}</h5>
        </template>
      </el-table-column>
      <el-table-column prop="tue" label="星期二" width="150" align="center">
        <template v-slot="scope">
          <h4>{{ scope.row.tue.name }}</h4>
          <h5>{{ scope.row.tue.teacher }}</h5>
          <h5>{{ scope.row.tue.place }}</h5>
        </template>
      </el-table-column>
      <el-table-column prop="wed" label="星期三" width="150" align="center">
        <template v-slot="scope">
          <h4>{{ scope.row.wed.name }}</h4>
          <h5>{{ scope.row.wed.teacher }}</h5>
          <h5>{{ scope.row.wed.place }}</h5>
        </template>
      </el-table-column>
      <el-table-column prop="thu" label="星期四" width="150" align="center">
        <template v-slot="scope">
          <h4>{{ scope.row.thu.name }}</h4>
          <h5>{{ scope.row.thu.teacher }}</h5>
          <h5>{{ scope.row.thu.place }}</h5>
        </template>
      </el-table-column>
      <el-table-column prop="fri" label="星期五" width="150" align="center">
        <template v-slot="scope">
          <h4>{{ scope.row.fri.name }}</h4>
          <h5>{{ scope.row.fri.teacher }}</h5>
          <h5>{{ scope.row.fri.place }}</h5>
        </template>
      </el-table-column>
      <el-table-column prop="sat" label="星期六" width="150" align="center">
        <template v-slot="scope">
          <h4>{{ scope.row.sat.name }}</h4>
          <h5>{{ scope.row.sat.teacher }}</h5>
          <h5>{{ scope.row.sat.place }}</h5>
        </template>
      </el-table-column>
      <el-table-column prop="sun" label="星期日" width="150" align="center">
        <template v-slot="scope">
          <h4>{{ scope.row.sun.name }}</h4>
          <h5>{{ scope.row.sun.teacher }}</h5>
          <h5>{{ scope.row.sun.place }}</h5>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>
<style scoped></style>
