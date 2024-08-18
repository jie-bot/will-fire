(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-b598f5c0"],{"868a":function(e,t,a){"use strict";a.r(t);var i=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",[a("el-tag",{staticClass:"my-tag",attrs:{effect:"dark"}},[a("svg",{staticStyle:{"vertical-align":"-3px"},attrs:{viewBox:"0 0 1024 1024",width:"20",height:"20"}},[a("path",{attrs:{d:"M0 0h1024v1024H0V0z",fill:"#202425",opacity:".01"}}),a("path",{attrs:{d:"M682.666667 204.8h238.933333a34.133333 34.133333 0 0 1 34.133333 34.133333v648.533334a68.266667 68.266667 0 0 1-68.266666 68.266666h-204.8V204.8z",fill:"#FFAA44"}}),a("path",{attrs:{d:"M68.266667 921.6a34.133333 34.133333 0 0 0 34.133333 34.133333h785.066667a68.266667 68.266667 0 0 1-68.266667-68.266666V102.4a34.133333 34.133333 0 0 0-34.133333-34.133333H102.4a34.133333 34.133333 0 0 0-34.133333 34.133333v819.2z",fill:"#11AA66"}}),a("path",{attrs:{d:"M238.933333 307.2a34.133333 34.133333 0 0 0 0 68.266667h136.533334a34.133333 34.133333 0 1 0 0-68.266667H238.933333z m0 204.8a34.133333 34.133333 0 1 0 0 68.266667h409.6a34.133333 34.133333 0 1 0 0-68.266667H238.933333z m0 204.8a34.133333 34.133333 0 1 0 0 68.266667h204.8a34.133333 34.133333 0 1 0 0-68.266667H238.933333z",fill:"#FFFFFF"}})]),e._v(" 文章信息 ")]),a("el-form",{ref:"ruleForm",staticClass:"demo-ruleForm",attrs:{model:e.article,rules:e.rules,"label-width":"150px"}},[a("el-form-item",{attrs:{label:"标题",prop:"articleTitle"}},[a("el-input",{attrs:{maxlength:"30"},model:{value:e.article.articleTitle,callback:function(t){e.$set(e.article,"articleTitle",t)},expression:"article.articleTitle"}})],1),a("el-form-item",{attrs:{label:"视频链接",prop:"videoUrl"}},[a("el-input",{attrs:{maxlength:"1000"},model:{value:e.article.videoUrl,callback:function(t){e.$set(e.article,"videoUrl",t)},expression:"article.videoUrl"}})],1),a("el-form-item",{attrs:{label:"内容",prop:"articleContent"}},[a("mavon-editor",{ref:"md",on:{imgAdd:e.imgAdd},model:{value:e.article.articleContent,callback:function(t){e.$set(e.article,"articleContent",t)},expression:"article.articleContent"}})],1),a("el-form-item",{attrs:{label:"是否启用评论",prop:"commentStatus"}},[a("el-tag",{attrs:{type:!1===e.article.commentStatus?"danger":"success","disable-transitions":""}},[e._v(" "+e._s(!1===e.article.commentStatus?"否":"是")+" ")]),a("el-switch",{model:{value:e.article.commentStatus,callback:function(t){e.$set(e.article,"commentStatus",t)},expression:"article.commentStatus"}})],1),a("el-form-item",{attrs:{label:"是否推荐",prop:"recommendStatus"}},[a("el-tag",{attrs:{type:!1===e.article.recommendStatus?"danger":"success","disable-transitions":""}},[e._v(" "+e._s(!1===e.article.recommendStatus?"否":"是")+" ")]),a("el-switch",{model:{value:e.article.recommendStatus,callback:function(t){e.$set(e.article,"recommendStatus",t)},expression:"article.recommendStatus"}})],1),a("el-form-item",{attrs:{label:"是否可见",prop:"viewStatus"}},[a("el-tag",{attrs:{type:!1===e.article.viewStatus?"danger":"success","disable-transitions":""}},[e._v(" "+e._s(!1===e.article.viewStatus?"否":"是")+" ")]),a("el-switch",{model:{value:e.article.viewStatus,callback:function(t){e.$set(e.article,"viewStatus",t)},expression:"article.viewStatus"}})],1),!1===e.article.viewStatus?a("el-form-item",{attrs:{label:"不可见时的访问密码",prop:"password"}},[a("el-input",{attrs:{maxlength:"30"},model:{value:e.article.password,callback:function(t){e.$set(e.article,"password",t)},expression:"article.password"}})],1):e._e(),!1===e.article.viewStatus?a("el-form-item",{attrs:{label:"密码提示",prop:"tips"}},[a("el-input",{attrs:{maxlength:"60"},model:{value:e.article.tips,callback:function(t){e.$set(e.article,"tips",t)},expression:"article.tips"}})],1):e._e(),a("el-form-item",{attrs:{label:"封面",prop:"articleCover"}},[a("div",{staticStyle:{display:"flex"}},[a("el-input",{model:{value:e.article.articleCover,callback:function(t){e.$set(e.article,"articleCover",t)},expression:"article.articleCover"}}),a("el-image",{staticClass:"table-td-thumb",staticStyle:{"margin-left":"10px"},attrs:{lazy:"","preview-src-list":[e.article.articleCover],src:e.article.articleCover,fit:"cover"}})],1),a("uploadPicture",{staticStyle:{"margin-top":"10px"},attrs:{isAdmin:!0,prefix:"articleCover",maxSize:2,maxNumber:1},on:{addPicture:e.addArticleCover}})],1),a("el-form-item",{attrs:{label:"分类",prop:"sortId"}},[a("el-select",{attrs:{placeholder:"请选择分类"},model:{value:e.article.sortId,callback:function(t){e.$set(e.article,"sortId",t)},expression:"article.sortId"}},e._l(e.sorts,(function(e){return a("el-option",{key:e.id,attrs:{label:e.sortName,value:e.id}})})),1)],1),a("el-form-item",{attrs:{label:"标签",prop:"labelId"}},[a("el-select",{attrs:{placeholder:"请选择标签"},model:{value:e.article.labelId,callback:function(t){e.$set(e.article,"labelId",t)},expression:"article.labelId"}},e._l(e.labelsTemp,(function(e){return a("el-option",{key:e.id,attrs:{label:e.labelName,value:e.id}})})),1)],1)],1),a("div",{staticClass:"myCenter",staticStyle:{"margin-bottom":"22px"}},[a("el-button",{attrs:{type:"primary"},on:{click:function(t){return e.submitForm("ruleForm")}}},[e._v("保存")]),a("el-button",{attrs:{type:"danger"},on:{click:function(t){return e.resetForm("ruleForm")}}},[e._v("重置所有修改")])],1)],1)},r=[],s=(a("d3b7"),a("3ca3"),a("ddb0"),a("4de4"),a("b0c0"),a("ac1f"),a("5319"),function(){return a.e("chunk-4279e0f6").then(a.bind(null,"6034"))}),l={components:{uploadPicture:s},data:function(){return{id:this.$route.query.id,article:{articleTitle:"",articleContent:"",commentStatus:!0,recommendStatus:!1,viewStatus:!0,password:"",tips:"",articleCover:"",videoUrl:"",sortId:null,labelId:null},sorts:[],labels:[],labelsTemp:[],rules:{articleTitle:[{required:!0,message:"请输入标题",trigger:"change"}],articleContent:[{required:!0,message:"请输入内容",trigger:"change"}],commentStatus:[{required:!0,message:"是否启用评论",trigger:"change"}],recommendStatus:[{required:!0,message:"是否推荐",trigger:"change"}],viewStatus:[{required:!0,message:"是否可见",trigger:"change"}],articleCover:[{required:!0,message:"封面",trigger:"change"}],sortId:[{required:!0,message:"分类",trigger:"change"}],labelId:[{required:!0,message:"标签",trigger:"blur"}]}}},computed:{},watch:{"article.sortId":function(e,t){null!==t&&(this.article.labelId=null),this.$common.isEmpty(e)||this.$common.isEmpty(this.labels)||(this.labelsTemp=this.labels.filter((function(t){return t.sortId===e})))}},created:function(){this.getSortAndLabel()},mounted:function(){},methods:{imgAdd:function(e,t){var a="";-1!==t.name.lastIndexOf(".")&&(a=t.name.substring(t.name.lastIndexOf(".")));var i="articlePicture/"+this.$store.state.currentAdmin.username.replace(/[^a-zA-Z]/g,"")+this.$store.state.currentAdmin.id+(new Date).getTime()+Math.floor(1e3*Math.random())+a,r=localStorage.getItem("defaultStoreType"),s=new FormData;s.append("file",t),s.append("originalName",t.name),s.append("key",i),s.append("relativePath",i),s.append("type","articlePicture"),s.append("storeType",r),"local"===r?this.saveLocal(e,s):"qiniu"===r&&this.saveQiniu(e,s)},saveLocal:function(e,t){var a=this;this.$http.upload("http://47.96.86.223:8081/api/resource/upload",t,!0).then((function(t){if(!a.$common.isEmpty(t.data)){var i=t.data;a.$refs.md.$img2Url(e,i)}})).catch((function(e){a.$message({message:e.message,type:"error"})}))},saveQiniu:function(e,t){var a=this;this.$http.get("http://47.96.86.223:8081/api/qiniu/getUpToken",{key:t.get("key")},!0).then((function(i){a.$common.isEmpty(i.data)||(t.append("token",i.data),a.$http.uploadQiniu(a.$store.state.sysConfig.qiniuUrl,t).then((function(i){if(!a.$common.isEmpty(i.key)){var r=a.$store.state.sysConfig["qiniu.downloadUrl"]+i.key,s=t.get("file");a.$common.saveResource(a,"articlePicture",r,s.size,s.type,s.name,"qiniu",!0),a.$refs.md.$img2Url(e,r)}})).catch((function(e){a.$message({message:e.message,type:"error"})})))})).catch((function(e){a.$message({message:e.message,type:"error"})}))},addArticleCover:function(e){this.article.articleCover=e},getSortAndLabel:function(){var e=this;this.$http.get("http://47.96.86.223:8081/api/webInfo/listSortAndLabel").then((function(t){e.$common.isEmpty(t.data)||(e.sorts=t.data.sorts,e.labels=t.data.labels,e.$common.isEmpty(e.id)||e.getArticle())})).catch((function(t){e.$message({message:t.message,type:"error"})}))},getArticle:function(){var e=this;this.$http.get("http://47.96.86.223:8081/api/admin/article/getArticleById",{id:this.id},!0).then((function(t){e.$common.isEmpty(t.data)||(e.article=t.data)})).catch((function(t){e.$message({message:t.message,type:"error"})}))},submitForm:function(e){var t=this;!1===this.article.viewStatus&&this.$common.isEmpty(this.article.password)?this.$message({message:"文章不可见时必须输入密码！",type:"error"}):this.$refs[e].validate((function(e){e?t.$common.isEmpty(t.id)?t.saveArticle(t.article,"/article/saveArticle"):(t.article.id=t.id,t.saveArticle(t.article,"/article/updateArticle")):t.$message({message:"请完善必填项！",type:"error"})}))},resetForm:function(e){this.$refs[e].resetFields(),this.$common.isEmpty(this.id)||this.getArticle()},saveArticle:function(e,t){var a=this;this.$confirm("确认保存？","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"success",center:!0}).then((function(){a.$http.post("http://47.96.86.223:8081/api"+t,e,!0).then((function(e){a.$message({message:"保存成功！",type:"success"}),a.$router.push({path:"/postList"})})).catch((function(e){a.$message({message:e.message,type:"error"})}))})).catch((function(){a.$message({type:"success",message:"已取消保存!"})}))}}},c=l,n=(a("8edf"),a("2877")),o=Object(n["a"])(c,i,r,!1,null,"19e5157c",null);t["default"]=o.exports},"8edf":function(e,t,a){"use strict";a("e288")},e288:function(e,t,a){}}]);