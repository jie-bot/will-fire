(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-d89dd37c"],{2543:function(t,e,n){"use strict";n.r(e);var a=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",[n("div",{staticStyle:{"margin-bottom":"20px"}},[t.isBoss?n("el-select",{staticStyle:{"margin-right":"10px"},attrs:{placeholder:"评论来源类型"},model:{value:t.pagination.commentType,callback:function(e){t.$set(t.pagination,"commentType",e)},expression:"pagination.commentType"}},[n("el-option",{key:"1",attrs:{label:"文章评论",value:"article"}}),n("el-option",{key:"2",attrs:{label:"树洞留言",value:"message"}}),n("el-option",{key:"3",attrs:{label:"家庭祝福",value:"love"}})],1):t._e(),n("el-input",{staticClass:"my-input",staticStyle:{width:"140px","margin-right":"10px"},attrs:{type:"number",placeholder:"评论来源标识"},model:{value:t.pagination.source,callback:function(e){t.$set(t.pagination,"source",e)},expression:"pagination.source"}}),n("el-button",{attrs:{type:"primary",icon:"el-icon-search"},on:{click:function(e){return t.searchComments()}}},[t._v("搜索")]),n("el-button",{attrs:{type:"danger"},on:{click:function(e){return t.clearSearch()}}},[t._v("清除参数")])],1),n("el-table",{staticClass:"table",attrs:{data:t.comments,border:"","header-cell-class-name":"table-header"}},[n("el-table-column",{attrs:{prop:"id",label:"ID",width:"55",align:"center"}}),n("el-table-column",{attrs:{prop:"source",label:"评论来源标识",align:"center"}}),n("el-table-column",{attrs:{prop:"type",label:"评论来源类型",align:"center"}}),n("el-table-column",{attrs:{prop:"userId",label:"发表用户ID",align:"center"}}),n("el-table-column",{attrs:{prop:"likeCount",label:"点赞数",align:"center"}}),n("el-table-column",{attrs:{prop:"commentContent",label:"评论内容",align:"center"}}),n("el-table-column",{attrs:{prop:"commentInfo",label:"评论额外信息",align:"center"}}),n("el-table-column",{attrs:{prop:"createTime",label:"创建时间",align:"center"}}),n("el-table-column",{attrs:{label:"操作",width:"180",align:"center"},scopedSlots:t._u([{key:"default",fn:function(e){return[n("el-button",{staticStyle:{color:"var(--orangeRed)"},attrs:{type:"text",icon:"el-icon-delete"},on:{click:function(n){return t.handleDelete(e.row)}}},[t._v(" 删除 ")])]}}])})],1),n("div",{staticClass:"pagination"},[n("el-pagination",{attrs:{background:"",layout:"total, prev, pager, next","current-page":t.pagination.current,"page-size":t.pagination.size,total:t.pagination.total},on:{"current-change":t.handlePageChange}})],1)],1)},o=[],s={data:function(){return{isBoss:this.$store.state.currentAdmin.isBoss,pagination:{current:1,size:10,total:0,source:null,commentType:""},comments:[]}},computed:{},watch:{},created:function(){this.getComments()},mounted:function(){},methods:{clearSearch:function(){this.pagination={current:1,size:10,total:0,source:null,commentType:""},this.getComments()},getComments:function(){var t=this,e="";e=this.isBoss?"/admin/comment/boss/list":"/admin/comment/user/list",this.$http.post("http://47.96.86.223:8081/api"+e,this.pagination,!0).then((function(e){t.$common.isEmpty(e.data)||(t.comments=e.data.records,t.pagination.total=e.data.total)})).catch((function(e){t.$message({message:e.message,type:"error"})}))},handlePageChange:function(t){this.pagination.current=t,this.getComments()},searchComments:function(){this.pagination.total=0,this.pagination.current=1,this.getComments()},handleDelete:function(t){var e=this,n="";n=this.isBoss?"/admin/comment/boss/deleteComment":"/admin/comment/user/deleteComment",this.$confirm("删除评论后，所有该评论的回复均不可见。确认删除？","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"success",center:!0}).then((function(){e.$http.get("http://47.96.86.223:8081/api"+n,{id:t.id},!0).then((function(t){e.pagination.current=1,e.getComments(),e.$message({message:"删除成功！",type:"success"})})).catch((function(t){e.$message({message:t.message,type:"error"})}))})).catch((function(){e.$message({type:"success",message:"已取消删除!"})}))}}},i=s,l=(n("9bef"),n("2877")),c=Object(l["a"])(i,a,o,!1,null,"3090fb45",null);e["default"]=c.exports},4119:function(t,e,n){},"9bef":function(t,e,n){"use strict";n("4119")}}]);