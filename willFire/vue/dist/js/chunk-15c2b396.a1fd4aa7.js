(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-15c2b396"],{"57d1":function(t,e,a){"use strict";a.r(e);var n=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",[a("div",[a("el-tag",{staticClass:"my-tag",attrs:{effect:"dark"}},[a("svg",{staticStyle:{"vertical-align":"-4px"},attrs:{viewBox:"0 0 1024 1024",width:"20",height:"20"}},[a("path",{attrs:{d:"M767.1296 808.6528c16.8448 0 32.9728 2.816 48.0256 8.0384 20.6848 7.1168 43.52 1.0752 57.1904-15.9744a459.91936 459.91936 0 0 0 70.5024-122.88c7.8336-20.48 1.0752-43.264-15.9744-57.088-49.6128-40.192-65.0752-125.3888-31.3856-185.856a146.8928 146.8928 0 0 1 30.3104-37.9904c16.2304-14.5408 22.1696-37.376 13.9264-57.6a461.27104 461.27104 0 0 0-67.5328-114.9952c-13.6192-16.9984-36.4544-22.9376-57.0368-15.8208a146.3296 146.3296 0 0 1-48.0256 8.0384c-70.144 0-132.352-50.8928-145.2032-118.7328-4.096-21.6064-20.736-38.5536-42.4448-41.8304-22.0672-3.2768-44.6464-5.0176-67.6864-5.0176-21.4528 0-42.5472 1.536-63.232 4.4032-22.3232 3.1232-40.2432 20.48-43.52 42.752-6.912 46.6944-36.0448 118.016-145.7152 118.4256-17.3056 0.0512-33.8944-2.9696-49.3056-8.448-21.0432-7.4752-44.3904-1.4848-58.368 15.9232A462.14656 462.14656 0 0 0 80.4864 348.16c-7.6288 20.0192-2.7648 43.008 13.4656 56.9344 55.5008 47.8208 71.7824 122.88 37.0688 185.1392a146.72896 146.72896 0 0 1-31.6416 39.168c-16.8448 14.7456-23.0912 38.1952-14.5408 58.9312 16.896 41.0112 39.5776 79.0016 66.9696 113.0496 13.9264 17.3056 37.2736 23.1936 58.2144 15.7184 15.4112-5.4784 32-8.4992 49.3056-8.4992 71.2704 0 124.7744 49.408 142.1312 121.2928 4.9664 20.48 21.4016 36.0448 42.24 39.168 22.2208 3.328 44.9536 5.0688 68.096 5.0688 23.3984 0 46.4384-1.792 68.864-5.1712 21.3504-3.2256 38.144-19.456 42.7008-40.5504 14.8992-68.8128 73.1648-119.7568 143.7696-119.7568z",fill:"#8C7BFD"}}),a("path",{attrs:{d:"M511.8464 696.3712c-101.3248 0-183.7568-82.432-183.7568-183.7568s82.432-183.7568 183.7568-183.7568 183.7568 82.432 183.7568 183.7568-82.432 183.7568-183.7568 183.7568z m0-265.1648c-44.8512 0-81.3568 36.5056-81.3568 81.3568S466.9952 593.92 511.8464 593.92s81.3568-36.5056 81.3568-81.3568-36.5056-81.3568-81.3568-81.3568z",fill:"#FFE37B"}})]),t._v(" 基础信息 ")]),a("el-form",{ref:"ruleForm",staticClass:"demo-ruleForm",attrs:{model:t.webInfo,rules:t.rules,"label-width":"100px"}},[a("el-form-item",{attrs:{label:"网站名称",prop:"webName"}},[a("el-input",{model:{value:t.webInfo.webName,callback:function(e){t.$set(t.webInfo,"webName",e)},expression:"webInfo.webName"}})],1),a("el-form-item",{attrs:{label:"网站标题",prop:"webTitle"}},[a("el-input",{model:{value:t.webInfo.webTitle,callback:function(e){t.$set(t.webInfo,"webTitle",e)},expression:"webInfo.webTitle"}})],1),a("el-form-item",{attrs:{label:"页脚",prop:"footer"}},[a("el-input",{model:{value:t.webInfo.footer,callback:function(e){t.$set(t.webInfo,"footer",e)},expression:"webInfo.footer"}})],1),a("el-form-item",{attrs:{label:"状态",prop:"status"}},[a("el-switch",{nativeOn:{click:function(e){return t.changeWebStatus(t.webInfo)}},model:{value:t.webInfo.status,callback:function(e){t.$set(t.webInfo,"status",e)},expression:"webInfo.status"}})],1),a("el-form-item",{attrs:{label:"背景",prop:"backgroundImage"}},[a("div",{staticStyle:{display:"flex"}},[a("el-input",{model:{value:t.webInfo.backgroundImage,callback:function(e){t.$set(t.webInfo,"backgroundImage",e)},expression:"webInfo.backgroundImage"}}),a("el-image",{staticClass:"table-td-thumb",staticStyle:{"margin-left":"10px"},attrs:{lazy:"","preview-src-list":[t.webInfo.backgroundImage],src:t.webInfo.backgroundImage,fit:"cover"}})],1),a("uploadPicture",{staticStyle:{"margin-top":"15px"},attrs:{isAdmin:!0,prefix:"webBackgroundImage",maxSize:5,maxNumber:1},on:{addPicture:t.addBackgroundImage}})],1),a("el-form-item",{attrs:{label:"头像",prop:"avatar"}},[a("div",{staticStyle:{display:"flex"}},[a("el-input",{model:{value:t.webInfo.avatar,callback:function(e){t.$set(t.webInfo,"avatar",e)},expression:"webInfo.avatar"}}),a("el-image",{staticClass:"table-td-thumb",staticStyle:{"margin-left":"10px"},attrs:{lazy:"","preview-src-list":[t.webInfo.avatar],src:t.webInfo.avatar,fit:"cover"}})],1),a("uploadPicture",{staticStyle:{"margin-top":"15px"},attrs:{isAdmin:!0,prefix:"webAvatar",maxSize:5,maxNumber:1},on:{addPicture:t.addAvatar}})],1),a("el-form-item",{attrs:{label:"提示",prop:"waifuJson"}},[a("div",{staticStyle:{display:"flex"}},[a("el-input",{attrs:{disabled:t.disabled,rows:6,type:"textarea"},model:{value:t.webInfo.waifuJson,callback:function(e){t.$set(t.webInfo,"waifuJson",e)},expression:"webInfo.waifuJson"}}),a("i",{staticClass:"el-icon-edit my-icon",on:{click:function(e){t.disabled=!t.disabled}}})],1)])],1),a("div",{staticClass:"myCenter",staticStyle:{"margin-bottom":"22px"}},[a("el-button",{attrs:{type:"primary"},on:{click:function(e){return t.submitForm("ruleForm")}}},[t._v("保存基本信息")])],1)],1),a("div",[a("el-tag",{staticClass:"my-tag",attrs:{effect:"dark"}},[a("svg",{staticStyle:{"vertical-align":"-4px"},attrs:{viewBox:"0 0 1024 1024",width:"20",height:"20"}},[a("path",{attrs:{d:"M767.1296 808.6528c16.8448 0 32.9728 2.816 48.0256 8.0384 20.6848 7.1168 43.52 1.0752 57.1904-15.9744a459.91936 459.91936 0 0 0 70.5024-122.88c7.8336-20.48 1.0752-43.264-15.9744-57.088-49.6128-40.192-65.0752-125.3888-31.3856-185.856a146.8928 146.8928 0 0 1 30.3104-37.9904c16.2304-14.5408 22.1696-37.376 13.9264-57.6a461.27104 461.27104 0 0 0-67.5328-114.9952c-13.6192-16.9984-36.4544-22.9376-57.0368-15.8208a146.3296 146.3296 0 0 1-48.0256 8.0384c-70.144 0-132.352-50.8928-145.2032-118.7328-4.096-21.6064-20.736-38.5536-42.4448-41.8304-22.0672-3.2768-44.6464-5.0176-67.6864-5.0176-21.4528 0-42.5472 1.536-63.232 4.4032-22.3232 3.1232-40.2432 20.48-43.52 42.752-6.912 46.6944-36.0448 118.016-145.7152 118.4256-17.3056 0.0512-33.8944-2.9696-49.3056-8.448-21.0432-7.4752-44.3904-1.4848-58.368 15.9232A462.14656 462.14656 0 0 0 80.4864 348.16c-7.6288 20.0192-2.7648 43.008 13.4656 56.9344 55.5008 47.8208 71.7824 122.88 37.0688 185.1392a146.72896 146.72896 0 0 1-31.6416 39.168c-16.8448 14.7456-23.0912 38.1952-14.5408 58.9312 16.896 41.0112 39.5776 79.0016 66.9696 113.0496 13.9264 17.3056 37.2736 23.1936 58.2144 15.7184 15.4112-5.4784 32-8.4992 49.3056-8.4992 71.2704 0 124.7744 49.408 142.1312 121.2928 4.9664 20.48 21.4016 36.0448 42.24 39.168 22.2208 3.328 44.9536 5.0688 68.096 5.0688 23.3984 0 46.4384-1.792 68.864-5.1712 21.3504-3.2256 38.144-19.456 42.7008-40.5504 14.8992-68.8128 73.1648-119.7568 143.7696-119.7568z",fill:"#8C7BFD"}}),a("path",{attrs:{d:"M511.8464 696.3712c-101.3248 0-183.7568-82.432-183.7568-183.7568s82.432-183.7568 183.7568-183.7568 183.7568 82.432 183.7568 183.7568-82.432 183.7568-183.7568 183.7568z m0-265.1648c-44.8512 0-81.3568 36.5056-81.3568 81.3568S466.9952 593.92 511.8464 593.92s81.3568-36.5056 81.3568-81.3568-36.5056-81.3568-81.3568-81.3568z",fill:"#FFE37B"}})]),t._v(" 公告 ")]),t._l(t.notices,(function(e,n){return a("el-tag",{key:n,attrs:{closable:"","disable-transitions":!1},on:{close:function(a){return t.handleClose(t.notices,e)}}},[t._v(" "+t._s(e)+" ")])})),t.inputNoticeVisible?a("el-input",{ref:"saveNoticeInput",staticClass:"input-new-tag",attrs:{size:"small"},on:{blur:t.handleInputNoticeConfirm},nativeOn:{keyup:function(e){return!e.type.indexOf("key")&&t._k(e.keyCode,"enter",13,e.key,"Enter")?null:t.handleInputNoticeConfirm.apply(null,arguments)}},model:{value:t.inputNoticeValue,callback:function(e){t.inputNoticeValue=e},expression:"inputNoticeValue"}}):a("el-button",{staticClass:"button-new-tag",attrs:{size:"small"},on:{click:function(e){return t.showNoticeInput()}}},[t._v("+ 公告")]),a("div",{staticClass:"myCenter",staticStyle:{"margin-bottom":"22px"}},[a("el-button",{attrs:{type:"primary"},on:{click:function(e){return t.saveNotice()}}},[t._v("保存公告")])],1)],2),a("div",[a("el-tag",{staticClass:"my-tag",attrs:{effect:"dark"}},[a("svg",{staticStyle:{"vertical-align":"-4px"},attrs:{viewBox:"0 0 1024 1024",width:"20",height:"20"}},[a("path",{attrs:{d:"M767.1296 808.6528c16.8448 0 32.9728 2.816 48.0256 8.0384 20.6848 7.1168 43.52 1.0752 57.1904-15.9744a459.91936 459.91936 0 0 0 70.5024-122.88c7.8336-20.48 1.0752-43.264-15.9744-57.088-49.6128-40.192-65.0752-125.3888-31.3856-185.856a146.8928 146.8928 0 0 1 30.3104-37.9904c16.2304-14.5408 22.1696-37.376 13.9264-57.6a461.27104 461.27104 0 0 0-67.5328-114.9952c-13.6192-16.9984-36.4544-22.9376-57.0368-15.8208a146.3296 146.3296 0 0 1-48.0256 8.0384c-70.144 0-132.352-50.8928-145.2032-118.7328-4.096-21.6064-20.736-38.5536-42.4448-41.8304-22.0672-3.2768-44.6464-5.0176-67.6864-5.0176-21.4528 0-42.5472 1.536-63.232 4.4032-22.3232 3.1232-40.2432 20.48-43.52 42.752-6.912 46.6944-36.0448 118.016-145.7152 118.4256-17.3056 0.0512-33.8944-2.9696-49.3056-8.448-21.0432-7.4752-44.3904-1.4848-58.368 15.9232A462.14656 462.14656 0 0 0 80.4864 348.16c-7.6288 20.0192-2.7648 43.008 13.4656 56.9344 55.5008 47.8208 71.7824 122.88 37.0688 185.1392a146.72896 146.72896 0 0 1-31.6416 39.168c-16.8448 14.7456-23.0912 38.1952-14.5408 58.9312 16.896 41.0112 39.5776 79.0016 66.9696 113.0496 13.9264 17.3056 37.2736 23.1936 58.2144 15.7184 15.4112-5.4784 32-8.4992 49.3056-8.4992 71.2704 0 124.7744 49.408 142.1312 121.2928 4.9664 20.48 21.4016 36.0448 42.24 39.168 22.2208 3.328 44.9536 5.0688 68.096 5.0688 23.3984 0 46.4384-1.792 68.864-5.1712 21.3504-3.2256 38.144-19.456 42.7008-40.5504 14.8992-68.8128 73.1648-119.7568 143.7696-119.7568z",fill:"#8C7BFD"}}),a("path",{attrs:{d:"M511.8464 696.3712c-101.3248 0-183.7568-82.432-183.7568-183.7568s82.432-183.7568 183.7568-183.7568 183.7568 82.432 183.7568 183.7568-82.432 183.7568-183.7568 183.7568z m0-265.1648c-44.8512 0-81.3568 36.5056-81.3568 81.3568S466.9952 593.92 511.8464 593.92s81.3568-36.5056 81.3568-81.3568-36.5056-81.3568-81.3568-81.3568z",fill:"#FFE37B"}})]),t._v(" 随机名称 ")]),t._l(t.randomName,(function(e,n){return a("el-tag",{key:n,attrs:{effect:"dark",closable:"","disable-transitions":!1,type:t.types[Math.floor(5*Math.random())]},on:{close:function(a){return t.handleClose(t.randomName,e)}}},[t._v(" "+t._s(e)+" ")])})),t.inputRandomNameVisible?a("el-input",{ref:"saveRandomNameInput",staticClass:"input-new-tag",attrs:{size:"small"},on:{blur:t.handleInputRandomNameConfirm},nativeOn:{keyup:function(e){return!e.type.indexOf("key")&&t._k(e.keyCode,"enter",13,e.key,"Enter")?null:t.handleInputRandomNameConfirm.apply(null,arguments)}},model:{value:t.inputRandomNameValue,callback:function(e){t.inputRandomNameValue=e},expression:"inputRandomNameValue"}}):a("el-button",{staticClass:"button-new-tag",attrs:{size:"small"},on:{click:t.showRandomNameInput}},[t._v("+ 随机名称")]),a("div",{staticClass:"myCenter",staticStyle:{"margin-bottom":"22px"}},[a("el-button",{attrs:{type:"primary"},on:{click:function(e){return t.saveRandomName()}}},[t._v("保存随机名称")])],1)],2),a("div",[a("el-tag",{staticClass:"my-tag",attrs:{effect:"dark"}},[a("svg",{staticStyle:{"vertical-align":"-4px"},attrs:{viewBox:"0 0 1024 1024",width:"20",height:"20"}},[a("path",{attrs:{d:"M767.1296 808.6528c16.8448 0 32.9728 2.816 48.0256 8.0384 20.6848 7.1168 43.52 1.0752 57.1904-15.9744a459.91936 459.91936 0 0 0 70.5024-122.88c7.8336-20.48 1.0752-43.264-15.9744-57.088-49.6128-40.192-65.0752-125.3888-31.3856-185.856a146.8928 146.8928 0 0 1 30.3104-37.9904c16.2304-14.5408 22.1696-37.376 13.9264-57.6a461.27104 461.27104 0 0 0-67.5328-114.9952c-13.6192-16.9984-36.4544-22.9376-57.0368-15.8208a146.3296 146.3296 0 0 1-48.0256 8.0384c-70.144 0-132.352-50.8928-145.2032-118.7328-4.096-21.6064-20.736-38.5536-42.4448-41.8304-22.0672-3.2768-44.6464-5.0176-67.6864-5.0176-21.4528 0-42.5472 1.536-63.232 4.4032-22.3232 3.1232-40.2432 20.48-43.52 42.752-6.912 46.6944-36.0448 118.016-145.7152 118.4256-17.3056 0.0512-33.8944-2.9696-49.3056-8.448-21.0432-7.4752-44.3904-1.4848-58.368 15.9232A462.14656 462.14656 0 0 0 80.4864 348.16c-7.6288 20.0192-2.7648 43.008 13.4656 56.9344 55.5008 47.8208 71.7824 122.88 37.0688 185.1392a146.72896 146.72896 0 0 1-31.6416 39.168c-16.8448 14.7456-23.0912 38.1952-14.5408 58.9312 16.896 41.0112 39.5776 79.0016 66.9696 113.0496 13.9264 17.3056 37.2736 23.1936 58.2144 15.7184 15.4112-5.4784 32-8.4992 49.3056-8.4992 71.2704 0 124.7744 49.408 142.1312 121.2928 4.9664 20.48 21.4016 36.0448 42.24 39.168 22.2208 3.328 44.9536 5.0688 68.096 5.0688 23.3984 0 46.4384-1.792 68.864-5.1712 21.3504-3.2256 38.144-19.456 42.7008-40.5504 14.8992-68.8128 73.1648-119.7568 143.7696-119.7568z",fill:"#8C7BFD"}}),a("path",{attrs:{d:"M511.8464 696.3712c-101.3248 0-183.7568-82.432-183.7568-183.7568s82.432-183.7568 183.7568-183.7568 183.7568 82.432 183.7568 183.7568-82.432 183.7568-183.7568 183.7568z m0-265.1648c-44.8512 0-81.3568 36.5056-81.3568 81.3568S466.9952 593.92 511.8464 593.92s81.3568-36.5056 81.3568-81.3568-36.5056-81.3568-81.3568-81.3568z",fill:"#FFE37B"}})]),t._v(" 随机头像 ")]),t._l(t.randomAvatar,(function(e,n){return a("div",{key:n,staticStyle:{display:"flex"}},[a("el-tag",{staticStyle:{"white-space":"normal",height:"unset"},attrs:{closable:"","disable-transitions":!1},on:{close:function(a){return t.handleClose(t.randomAvatar,e)}}},[t._v(" "+t._s(e)+" ")]),a("div",[a("el-image",{staticClass:"table-td-thumb",staticStyle:{margin:"10px"},attrs:{lazy:"","preview-src-list":[e],src:e,fit:"cover"}})],1)],1)})),t.inputRandomAvatarVisible?a("el-input",{ref:"saveRandomAvatarInput",staticClass:"input-new-tag",attrs:{size:"small"},on:{blur:t.handleInputRandomAvatarConfirm},nativeOn:{keyup:function(e){return!e.type.indexOf("key")&&t._k(e.keyCode,"enter",13,e.key,"Enter")?null:t.handleInputRandomAvatarConfirm.apply(null,arguments)}},model:{value:t.inputRandomAvatarValue,callback:function(e){t.inputRandomAvatarValue=e},expression:"inputRandomAvatarValue"}}):a("el-button",{staticClass:"button-new-tag",attrs:{size:"small"},on:{click:t.showRandomAvatarInput}},[t._v("+ 随机头像")]),a("uploadPicture",{staticStyle:{margin:"10px"},attrs:{isAdmin:!0,prefix:"randomAvatar",maxSize:5,maxNumber:5},on:{addPicture:t.addRandomAvatar}}),a("div",{staticClass:"myCenter",staticStyle:{"margin-bottom":"22px"}},[a("el-button",{attrs:{type:"primary"},on:{click:function(e){return t.saveRandomAvatar()}}},[t._v("保存随机头像")])],1)],2),a("div",[a("el-tag",{staticClass:"my-tag",attrs:{effect:"dark"}},[a("svg",{staticStyle:{"vertical-align":"-4px"},attrs:{viewBox:"0 0 1024 1024",width:"20",height:"20"}},[a("path",{attrs:{d:"M767.1296 808.6528c16.8448 0 32.9728 2.816 48.0256 8.0384 20.6848 7.1168 43.52 1.0752 57.1904-15.9744a459.91936 459.91936 0 0 0 70.5024-122.88c7.8336-20.48 1.0752-43.264-15.9744-57.088-49.6128-40.192-65.0752-125.3888-31.3856-185.856a146.8928 146.8928 0 0 1 30.3104-37.9904c16.2304-14.5408 22.1696-37.376 13.9264-57.6a461.27104 461.27104 0 0 0-67.5328-114.9952c-13.6192-16.9984-36.4544-22.9376-57.0368-15.8208a146.3296 146.3296 0 0 1-48.0256 8.0384c-70.144 0-132.352-50.8928-145.2032-118.7328-4.096-21.6064-20.736-38.5536-42.4448-41.8304-22.0672-3.2768-44.6464-5.0176-67.6864-5.0176-21.4528 0-42.5472 1.536-63.232 4.4032-22.3232 3.1232-40.2432 20.48-43.52 42.752-6.912 46.6944-36.0448 118.016-145.7152 118.4256-17.3056 0.0512-33.8944-2.9696-49.3056-8.448-21.0432-7.4752-44.3904-1.4848-58.368 15.9232A462.14656 462.14656 0 0 0 80.4864 348.16c-7.6288 20.0192-2.7648 43.008 13.4656 56.9344 55.5008 47.8208 71.7824 122.88 37.0688 185.1392a146.72896 146.72896 0 0 1-31.6416 39.168c-16.8448 14.7456-23.0912 38.1952-14.5408 58.9312 16.896 41.0112 39.5776 79.0016 66.9696 113.0496 13.9264 17.3056 37.2736 23.1936 58.2144 15.7184 15.4112-5.4784 32-8.4992 49.3056-8.4992 71.2704 0 124.7744 49.408 142.1312 121.2928 4.9664 20.48 21.4016 36.0448 42.24 39.168 22.2208 3.328 44.9536 5.0688 68.096 5.0688 23.3984 0 46.4384-1.792 68.864-5.1712 21.3504-3.2256 38.144-19.456 42.7008-40.5504 14.8992-68.8128 73.1648-119.7568 143.7696-119.7568z",fill:"#8C7BFD"}}),a("path",{attrs:{d:"M511.8464 696.3712c-101.3248 0-183.7568-82.432-183.7568-183.7568s82.432-183.7568 183.7568-183.7568 183.7568 82.432 183.7568 183.7568-82.432 183.7568-183.7568 183.7568z m0-265.1648c-44.8512 0-81.3568 36.5056-81.3568 81.3568S466.9952 593.92 511.8464 593.92s81.3568-36.5056 81.3568-81.3568-36.5056-81.3568-81.3568-81.3568z",fill:"#FFE37B"}})]),t._v(" 随机封面 ")]),t._l(t.randomCover,(function(e,n){return a("div",{key:n,staticStyle:{display:"flex"}},[a("el-tag",{staticStyle:{"white-space":"normal",height:"unset"},attrs:{closable:"","disable-transitions":!1},on:{close:function(a){return t.handleClose(t.randomCover,e)}}},[t._v(" "+t._s(e)+" ")]),a("div",[a("el-image",{staticClass:"table-td-thumb",staticStyle:{margin:"10px"},attrs:{lazy:"","preview-src-list":[e],src:e,fit:"cover"}})],1)],1)})),t.inputRandomCoverVisible?a("el-input",{ref:"saveRandomCoverInput",staticClass:"input-new-tag",attrs:{size:"small"},on:{blur:t.handleInputRandomCoverConfirm},nativeOn:{keyup:function(e){return!e.type.indexOf("key")&&t._k(e.keyCode,"enter",13,e.key,"Enter")?null:t.handleInputRandomCoverConfirm.apply(null,arguments)}},model:{value:t.inputRandomCoverValue,callback:function(e){t.inputRandomCoverValue=e},expression:"inputRandomCoverValue"}}):a("el-button",{staticClass:"button-new-tag",attrs:{size:"small"},on:{click:t.showRandomCoverInput}},[t._v("+ 随机封面")]),a("uploadPicture",{staticStyle:{margin:"10px"},attrs:{isAdmin:!0,prefix:"randomCover",maxSize:5,maxNumber:5},on:{addPicture:t.addRandomCover}}),a("div",{staticClass:"myCenter",staticStyle:{"margin-bottom":"40px"}},[a("el-button",{attrs:{type:"primary"},on:{click:function(e){return t.saveRandomCover()}}},[t._v("保存随机封面")])],1)],2),a("div",[a("el-button",{attrs:{type:"danger"},on:{click:function(e){return t.resetForm("ruleForm")}}},[t._v("重置所有修改")])],1)])},i=[],s=(a("d3b7"),a("3ca3"),a("ddb0"),a("a434"),a("e9c4"),function(){return a.e("chunk-7d8c1492").then(a.bind(null,"6034"))}),o={components:{uploadPicture:s},data:function(){return{disabled:!0,types:["","success","info","danger","warning"],inputNoticeVisible:!1,inputNoticeValue:"",inputRandomNameVisible:!1,inputRandomNameValue:"",inputRandomAvatarVisible:!1,inputRandomAvatarValue:"",inputRandomCoverVisible:!1,inputRandomCoverValue:"",webInfo:{id:null,webName:"",webTitle:"",footer:"",backgroundImage:"",avatar:"",waifuJson:"",status:!1},notices:[],randomAvatar:[],randomName:[],randomCover:[],rules:{webName:[{required:!0,message:"请输入网站名称",trigger:"blur"},{min:1,max:10,message:"长度在 1 到 10 个字符",trigger:"change"}],webTitle:[{required:!0,message:"请输入网站标题",trigger:"blur"}],footer:[{required:!0,message:"请输入页脚",trigger:"blur"}],backgroundImage:[{required:!0,message:"请输入背景",trigger:"change"}],status:[{required:!0,message:"请设置网站状态",trigger:"change"}],avatar:[{required:!0,message:"请上传头像",trigger:"change"}]}}},computed:{},watch:{},created:function(){this.getWebInfo()},mounted:function(){},methods:{addBackgroundImage:function(t){this.webInfo.backgroundImage=t},addAvatar:function(t){this.webInfo.avatar=t},addRandomAvatar:function(t){this.randomAvatar.push(t)},addRandomCover:function(t){this.randomCover.push(t)},changeWebStatus:function(t){var e=this;this.$http.post("http://47.96.86.223:8081/api/webInfo/updateWebInfo",{id:t.id,status:t.status},!0).then((function(t){e.getWebInfo(),e.$message({message:"保存成功！",type:"success"})})).catch((function(t){e.$message({message:t.message,type:"error"})}))},getWebInfo:function(){var t=this;this.$http.get("http://47.96.86.223:8081/api/admin/webInfo/getAdminWebInfo",{},!0).then((function(e){t.$common.isEmpty(e.data)||(t.webInfo.id=e.data.id,t.webInfo.webName=e.data.webName,t.webInfo.webTitle=e.data.webTitle,t.webInfo.footer=e.data.footer,t.webInfo.backgroundImage=e.data.backgroundImage,t.webInfo.avatar=e.data.avatar,t.webInfo.waifuJson=e.data.waifuJson,t.webInfo.status=e.data.status,t.notices=JSON.parse(e.data.notices),t.randomAvatar=JSON.parse(e.data.randomAvatar),t.randomName=JSON.parse(e.data.randomName),t.randomCover=JSON.parse(e.data.randomCover))})).catch((function(e){t.$message({message:e.message,type:"error"})}))},submitForm:function(t){var e=this;this.$refs[t].validate((function(t){t?e.updateWebInfo(e.webInfo):e.$message({message:"请完善必填项！",type:"error"})}))},resetForm:function(t){this.$refs[t].resetFields(),this.getWebInfo()},handleClose:function(t,e){t.splice(t.indexOf(e),1)},handleInputNoticeConfirm:function(){this.inputNoticeValue&&this.notices.push(this.inputNoticeValue),this.inputNoticeVisible=!1,this.inputNoticeValue=""},showNoticeInput:function(){var t=this;this.inputNoticeVisible=!0,this.$nextTick((function(){t.$refs.saveNoticeInput.$refs.input.focus()}))},saveNotice:function(){var t={id:this.webInfo.id,notices:JSON.stringify(this.notices)};this.updateWebInfo(t)},handleInputRandomNameConfirm:function(){this.inputRandomNameValue&&this.randomName.push(this.inputRandomNameValue),this.inputRandomNameVisible=!1,this.inputRandomNameValue=""},showRandomNameInput:function(){var t=this;this.inputRandomNameVisible=!0,this.$nextTick((function(){t.$refs.saveRandomNameInput.$refs.input.focus()}))},saveRandomName:function(){var t={id:this.webInfo.id,randomName:JSON.stringify(this.randomName)};this.updateWebInfo(t)},handleInputRandomAvatarConfirm:function(){this.inputRandomAvatarValue&&this.randomAvatar.push(this.inputRandomAvatarValue),this.inputRandomAvatarVisible=!1,this.inputRandomAvatarValue=""},showRandomAvatarInput:function(){var t=this;this.inputRandomAvatarVisible=!0,this.$nextTick((function(){t.$refs.saveRandomAvatarInput.$refs.input.focus()}))},saveRandomAvatar:function(){var t={id:this.webInfo.id,randomAvatar:JSON.stringify(this.randomAvatar)};this.updateWebInfo(t)},handleInputRandomCoverConfirm:function(){this.inputRandomCoverValue&&this.randomCover.push(this.inputRandomCoverValue),this.inputRandomCoverVisible=!1,this.inputRandomCoverValue=""},showRandomCoverInput:function(){var t=this;this.inputRandomCoverVisible=!0,this.$nextTick((function(){t.$refs.saveRandomCoverInput.$refs.input.focus()}))},saveRandomCover:function(){var t={id:this.webInfo.id,randomCover:JSON.stringify(this.randomCover)};this.updateWebInfo(t)},updateWebInfo:function(t){var e=this;this.$confirm("确认保存？","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"success",center:!0}).then((function(){e.$http.post("http://47.96.86.223:8081/api/webInfo/updateWebInfo",t,!0).then((function(t){e.getWebInfo(),e.$message({message:"保存成功！",type:"success"})})).catch((function(t){e.$message({message:t.message,type:"error"})}))})).catch((function(){e.$message({type:"success",message:"已取消保存!"})}))}}},r=o,l=(a("b8e3"),a("2877")),u=Object(l["a"])(r,n,i,!1,null,"eca06c7a",null);e["default"]=u.exports},"9d20":function(t,e,a){},a434:function(t,e,a){"use strict";var n=a("23e7"),i=a("da84"),s=a("23cb"),o=a("5926"),r=a("07fa"),l=a("7b0b"),u=a("65f0"),c=a("8418"),d=a("1dde"),m=d("splice"),f=i.TypeError,p=Math.max,b=Math.min,v=9007199254740991,h="Maximum allowed length exceeded";n({target:"Array",proto:!0,forced:!m},{splice:function(t,e){var a,n,i,d,m,g,w=l(this),y=r(w),I=s(t,y),C=arguments.length;if(0===C?a=n=0:1===C?(a=0,n=y-I):(a=C-2,n=b(p(o(e),0),y-I)),y+a-n>v)throw f(h);for(i=u(w,n),d=0;d<n;d++)m=I+d,m in w&&c(i,d,w[m]);if(i.length=n,a<n){for(d=I;d<y-n;d++)m=d+n,g=d+a,m in w?w[g]=w[m]:delete w[g];for(d=y;d>y-n+a;d--)delete w[d-1]}else if(a>n)for(d=y-n;d>I;d--)m=d+n-1,g=d+a-1,m in w?w[g]=w[m]:delete w[g];for(d=0;d<a;d++)w[d+I]=arguments[d+2];return w.length=y-n+a,i}})},b8e3:function(t,e,a){"use strict";a("9d20")}}]);