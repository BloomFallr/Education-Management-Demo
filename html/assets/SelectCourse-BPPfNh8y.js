import{r as d,G as O,z as q,L as m,I as n,o as _,H as e,t as i,w as A,k as J,x as K}from"./index-CauhVxhf.js";import{b as Q,E as W,a as X,c as Y,v as Z}from"./el-message-box-72ezgMD2.js";import{E as ee,a as te,b as ae}from"./el-main-1zPLywyB.js";import{E as le}from"./el-pagination-tu8S2-45.js";import{E as ne,a as oe}from"./el-message-BlXH8VcA.js";import{E as se,a as ue}from"./el-table-column-DDc4vDH_.js";import"./el-popper-CqdONIHR.js";import{E as re}from"./el-empty-DlBGokpv.js";import{E as ie}from"./el-progress-Dgvb9y-8.js";import{E as de,a as ce}from"./el-form-item-C075O6SC.js";import{a as pe,b as me,c as _e,d as fe}from"./stu-BOJDAIHm.js";import"./el-overlay-IdPhgn1o.js";import{E as S}from"./request-BnFEnbez.js";import"./constants-DWAz7ei8.js";import"./aria-nkjrUMQ-.js";import"./focus-trap-CBJl3iZD.js";import"./index-r5W6hzzQ.js";import"./vnode-RFtRU_sW.js";const ge={style:{color:"black"}},Le={__name:"SelectCourse",setup(ve){const f=d(!1),b=d([]),g=d([]),y=d(0),v=O(),t=d({name:"",teacher:"",time:"",place:"",selectState:"",courseState:1,currentPage:1,pageSize:10}),u=async()=>{f.value=!0;const l=await pe(t.value);b.value=l.data.data.list,y.value=l.data.data.total,f.value=!1;const o=await me(v.id);g.value=o.data.data},C=l=>g.value.length===0?!1:g.value.some(o=>o.id===l),x=()=>{t.value.currentPage=1,u()},V=()=>{t.value.currentPage=1,t.value.name="",t.value.teacher="",t.value.time="",t.value.place="",t.value.selectState="",t.value.currentPage=1,t.value.pageSize=10,u()};q(()=>{u()});const z=l=>{t.value.currentPage=l,u()},k=l=>{t.value.pageSize=l,u()},w=l=>parseInt((l.selectedStudents/l.maxStudents).toFixed(1)*100),P=l=>l.selectedStudents===l.maxStudents?"exception":"success",I=l=>l.selectedStudents+"/"+l.maxStudents,U=async l=>{if(l.selectedStudents===l.maxStudents){S.warning("该课程已满");return}await _e(v.id,l.id),S.success("选课成功"),u()},B=async l=>{await Q.confirm("您确认退课吗？","温馨提示",{confirmButtonText:"确认",cancelButtonText:"取消",type:"warning"}).then(async()=>{await fe(v.id,l.id),S.success("退课成功")}),u()};return(l,o)=>{const c=ne,r=de,E=W,T=X,p=oe,D=ce,H=te,s=se,L=ie,h=Y,M=re,F=ue,G=le,N=ae,R=ee,$=Z;return _(),m(R,null,{default:n(()=>[e(H,null,{default:n(()=>[e(D,{inline:""},{default:n(()=>[e(r,null,{default:n(()=>[e(c,{modelValue:t.value.name,"onUpdate:modelValue":o[0]||(o[0]=a=>t.value.name=a),placeholder:"课程名称"},null,8,["modelValue"])]),_:1}),e(r,null,{default:n(()=>[e(c,{modelValue:t.value.teacher,"onUpdate:modelValue":o[1]||(o[1]=a=>t.value.teacher=a),placeholder:"任课教师"},null,8,["modelValue"])]),_:1}),e(r,null,{default:n(()=>[e(c,{modelValue:t.value.time,"onUpdate:modelValue":o[2]||(o[2]=a=>t.value.time=a),placeholder:"上课时间"},null,8,["modelValue"])]),_:1}),e(r,null,{default:n(()=>[e(c,{modelValue:t.value.place,"onUpdate:modelValue":o[3]||(o[3]=a=>t.value.place=a),placeholder:"上课地点"},null,8,["modelValue"])]),_:1}),e(r,null,{default:n(()=>[e(T,{modelValue:t.value.selectState,"onUpdate:modelValue":o[4]||(o[4]=a=>t.value.selectState=a),placeholder:"选课状态",style:{width:"200px"},clearable:""},{default:n(()=>[e(E,{label:"选课未结束",value:"1"}),e(E,{label:"选课已结束",value:"0"})]),_:1},8,["modelValue"])]),_:1}),e(r,{style:{float:"inline-end"}},{default:n(()=>[e(p,{type:"primary",onClick:V,round:"",plain:""},{default:n(()=>[i("重置")]),_:1})]),_:1}),e(r,{style:{float:"inline-end"}},{default:n(()=>[e(p,{type:"primary",onClick:x,round:"",plain:""},{default:n(()=>[i("搜索")]),_:1})]),_:1})]),_:1})]),_:1}),e(N,null,{default:n(()=>[A((_(),m(F,{data:b.value,border:"",style:{width:"100%"},height:"550px"},{empty:n(()=>[e(M,{description:"没有数据"})]),default:n(()=>[e(s,{prop:"classId",label:"课堂号",width:"160"}),e(s,{prop:"name",label:"课程名称",width:"160"}),e(s,{prop:"teacher",label:"授课老师",width:"150"}),e(s,{prop:"credit",label:"学分",width:"75"}),e(s,{prop:"year",label:"学年",width:"100"}),e(s,{prop:"semester",label:"学期",width:"150"}),e(s,{prop:"time",label:"上课时间",width:"300"}),e(s,{prop:"place",label:"上课地点",width:"100"}),e(s,{label:"已选/人数上限",width:"200"},{default:n(({row:a})=>[e(L,{"text-inside":!0,"stroke-width":18,percentage:w(a),status:P(a)},{default:n(()=>[J("span",ge,K(I(a)),1)]),_:2},1032,["percentage","status"])]),_:1}),e(s,{label:"状态",width:"85"},{default:n(({row:a})=>[a.selectState==1?(_(),m(h,{key:0,type:"success",round:""},{default:n(()=>[i("正在选课")]),_:1})):(_(),m(h,{key:1,type:"warning",round:""},{default:n(()=>[i("选课结束")]),_:1}))]),_:1}),e(s,{fixed:"right",label:"操作",width:"140"},{default:n(({row:a})=>[e(p,{size:"small",type:"primary",plain:"",onClick:j=>U(a),disabled:C(a.id)||a.selectState==0},{default:n(()=>[i("选课")]),_:2},1032,["onClick","disabled"]),e(p,{size:"small",type:"danger",plain:"",onClick:j=>B(a),disabled:!C(a.id)||a.selectState==0},{default:n(()=>[i("退课")]),_:2},1032,["onClick","disabled"])]),_:1})]),_:1},8,["data"])),[[$,f.value]]),e(G,{"current-page":t.value.currentPage,"onUpdate:currentPage":o[5]||(o[5]=a=>t.value.currentPage=a),"page-size":t.value.pageSize,"onUpdate:pageSize":o[6]||(o[6]=a=>t.value.pageSize=a),background:!0,"page-sizes":[10,15,20,25],layout:"sizes, total, prev, pager, next, jumper",total:y.value,onCurrentChange:z,onSizeChange:k,style:{float:"inline-end","margin-top":"10px"}},null,8,["current-page","page-size","total"])]),_:1})]),_:1})}}};export{Le as default};
