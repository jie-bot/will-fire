(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-65dd8b0a"],{"24c4":function(t,e,a){"use strict";a.r(e);var n=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",[a("div",{staticClass:"handle-box"},[a("el-select",{staticClass:"mrb10",staticStyle:{width:"120px"},attrs:{placeholder:"是否推荐"},model:{value:t.pagination.recommendStatus,callback:function(e){t.$set(t.pagination,"recommendStatus",e)},expression:"pagination.recommendStatus"}},[a("el-option",{key:"1",attrs:{label:"是",value:!0}}),a("el-option",{key:"2",attrs:{label:"否",value:!1}})],1),a("el-select",{staticClass:"mrb10",staticStyle:{width:"140px"},attrs:{placeholder:"请选择分类"},model:{value:t.pagination.sortId,callback:function(e){t.$set(t.pagination,"sortId",e)},expression:"pagination.sortId"}},t._l(t.sorts,(function(t){return a("el-option",{key:t.id,attrs:{label:t.sortName,value:t.id}})})),1),a("el-select",{staticClass:"mrb10",staticStyle:{width:"140px"},attrs:{placeholder:"请选择标签"},model:{value:t.pagination.labelId,callback:function(e){t.$set(t.pagination,"labelId",e)},expression:"pagination.labelId"}},t._l(t.labelsTemp,(function(t){return a("el-option",{key:t.id,attrs:{label:t.labelName,value:t.id}})})),1),a("el-input",{staticClass:"handle-input mrb10",attrs:{placeholder:"文章标题"},model:{value:t.pagination.searchKey,callback:function(e){t.$set(t.pagination,"searchKey",e)},expression:"pagination.searchKey"}}),a("el-button",{attrs:{type:"primary",icon:"el-icon-search"},on:{click:function(e){return t.searchArticles()}}},[t._v("搜索")]),a("el-button",{attrs:{type:"danger"},on:{click:function(e){return t.clearSearch()}}},[t._v("清除参数")]),a("el-button",{attrs:{type:"primary"},on:{click:function(e){return t.$router.push({path:"/postEdit"})}}},[t._v("新增文章")])],1),a("el-table",{staticClass:"table",attrs:{data:t.articles,border:"","header-cell-class-name":"table-header"}},[a("el-table-column",{attrs:{prop:"id",label:"ID",width:"55",align:"center"}}),a("el-table-column",{attrs:{prop:"username",label:"作者",align:"center"}}),a("el-table-column",{attrs:{prop:"articleTitle",label:"文章标题",align:"center"}}),a("el-table-column",{attrs:{prop:"sort.sortName",label:"分类",align:"center"}}),a("el-table-column",{attrs:{prop:"label.labelName",label:"标签",align:"center"}}),a("el-table-column",{attrs:{prop:"viewCount",label:"浏览量",align:"center"}}),a("el-table-column",{attrs:{prop:"likeCount",label:"点赞数",align:"center"}}),a("el-table-column",{attrs:{label:"是否可见",align:"center"},scopedSlots:t._u([{key:"default",fn:function(e){return[a("el-tag",{attrs:{type:!1===e.row.viewStatus?"danger":"success","disable-transitions":""}},[t._v(" "+t._s(!1===e.row.viewStatus?"不可见":"可见")+" ")]),a("el-switch",{nativeOn:{click:function(a){return t.changeStatus(e.row,1)}},model:{value:e.row.viewStatus,callback:function(a){t.$set(e.row,"viewStatus",a)},expression:"scope.row.viewStatus"}})]}}])}),a("el-table-column",{attrs:{label:"封面",align:"center"},scopedSlots:t._u([{key:"default",fn:function(t){return[a("el-image",{staticClass:"table-td-thumb",attrs:{lazy:"",src:t.row.articleCover,fit:"cover"}})]}}])}),a("el-table-column",{attrs:{label:"是否启用评论",align:"center"},scopedSlots:t._u([{key:"default",fn:function(e){return[a("el-tag",{attrs:{type:!1===e.row.commentStatus?"danger":"success","disable-transitions":""}},[t._v(" "+t._s(!1===e.row.commentStatus?"否":"是")+" ")]),a("el-switch",{nativeOn:{click:function(a){return t.changeStatus(e.row,2)}},model:{value:e.row.commentStatus,callback:function(a){t.$set(e.row,"commentStatus",a)},expression:"scope.row.commentStatus"}})]}}])}),a("el-table-column",{attrs:{label:"是否推荐",align:"center"},scopedSlots:t._u([{key:"default",fn:function(e){return[a("el-tag",{attrs:{type:!1===e.row.recommendStatus?"danger":"success","disable-transitions":""}},[t._v(" "+t._s(!1===e.row.recommendStatus?"否":"是")+" ")]),a("el-switch",{nativeOn:{click:function(a){return t.changeStatus(e.row,3)}},model:{value:e.row.recommendStatus,callback:function(a){t.$set(e.row,"recommendStatus",a)},expression:"scope.row.recommendStatus"}})]}}])}),a("el-table-column",{attrs:{prop:"commentCount",label:"评论数",align:"center"}}),a("el-table-column",{attrs:{prop:"createTime",label:"创建时间",align:"center"}}),a("el-table-column",{attrs:{prop:"updateTime",label:"最终修改时间",align:"center"}}),a("el-table-column",{attrs:{label:"操作",width:"180",align:"center"},scopedSlots:t._u([{key:"default",fn:function(e){return[a("el-button",{attrs:{type:"text",icon:"el-icon-edit"},on:{click:function(a){return t.handleEdit(e.row)}}},[t._v("编辑")]),a("el-button",{staticStyle:{color:"var(--orangeRed)"},attrs:{type:"text",icon:"el-icon-delete"},on:{click:function(a){return t.handleDelete(e.row)}}},[t._v(" 删除 ")])]}}])})],1),a("div",{staticClass:"pagination"},[a("el-pagination",{attrs:{background:"",layout:"total, prev, pager, next","current-page":t.pagination.current,"page-size":t.pagination.size,total:t.pagination.total},on:{"current-change":t.handlePageChange}})],1)],1)},s=[],l=(a("4de4"),a("d3b7"),{data:function(){return{isBoss:this.$store.state.currentAdmin.isBoss,pagination:{current:1,size:10,total:0,searchKey:"",recommendStatus:null,sortId:null,labelId:null},articles:[],sorts:[],labels:[],labelsTemp:[]}},computed:{},watch:{"pagination.sortId":function(t){this.pagination.labelId=null,this.$common.isEmpty(t)||this.$common.isEmpty(this.labels)||(this.labelsTemp=this.labels.filter((function(e){return e.sortId===t})))}},created:function(){this.getArticles(),this.getSortAndLabel()},mounted:function(){},methods:{getSortAndLabel:function(){var t=this;this.$http.get("http://47.96.86.223:8081/api/webInfo/listSortAndLabel").then((function(e){t.$common.isEmpty(e.data)||(t.sorts=e.data.sorts,t.labels=e.data.labels)})).catch((function(e){t.$message({message:e.message,type:"error"})}))},clearSearch:function(){this.pagination={current:1,size:10,total:0,searchKey:"",recommendStatus:null,sortId:null,labelId:null},this.getArticles()},getArticles:function(){var t=this,e="";e=this.isBoss?"/admin/article/boss/list":"/admin/article/user/list",this.$http.post("http://47.96.86.223:8081/api"+e,this.pagination,!0).then((function(e){t.$common.isEmpty(e.data)||(t.articles=e.data.records,t.pagination.total=e.data.total)})).catch((function(e){t.$message({message:e.message,type:"error"})}))},handlePageChange:function(t){this.pagination.current=t,this.getArticles()},searchArticles:function(){this.pagination.total=0,this.pagination.current=1,this.getArticles()},changeStatus:function(t,e){var a,n=this;1===e?a={articleId:t.id,viewStatus:t.viewStatus}:2===e?a={articleId:t.id,commentStatus:t.commentStatus}:3===e&&(a={articleId:t.id,recommendStatus:t.recommendStatus}),this.$http.get("http://47.96.86.223:8081/api/admin/article/changeArticleStatus",a,!0).then((function(t){1===e?n.$message({duration:0,showClose:!0,message:"修改成功！注意，文章不可见时必须设置密码才能访问！",type:"warning"}):n.$message({message:"修改成功！",type:"success"})})).catch((function(t){n.$message({message:t.message,type:"error"})}))},handleDelete:function(t){var e=this;this.$confirm("确认删除？","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"success",center:!0}).then((function(){e.$http.get("http://47.96.86.223:8081/api/article/deleteArticle",{id:t.id},!0).then((function(t){e.pagination.current=1,e.getArticles(),e.$message({message:"删除成功！",type:"success"})})).catch((function(t){e.$message({message:t.message,type:"error"})}))})).catch((function(){e.$message({type:"success",message:"已取消删除!"})}))},handleEdit:function(t){this.$router.push({path:"/postEdit",query:{id:t.id}})}}}),i=l,o=(a("4982"),a("2877")),r=Object(o["a"])(i,n,s,!1,null,"15af350a",null);e["default"]=r.exports},4982:function(t,e,a){"use strict";a("afd2")},afd2:function(t,e,a){}}]);