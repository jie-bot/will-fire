(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-19e7f218"],{"0b25":function(t,e,r){var n=r("da84"),i=r("5926"),o=r("50c4"),a=n.RangeError;t.exports=function(t){if(void 0===t)return 0;var e=i(t),r=o(e);if(e!==r)throw a("Wrong length or index");return r}},"0eb6":function(t,e,r){"use strict";var n=r("23e7"),i=r("7c37"),o=r("d066"),a=r("d039"),c=r("7c73"),s=r("5c6c"),u=r("9bf2").f,f=r("cb2d"),d=r("edd0"),h=r("1a2d"),l=r("19aa"),y=r("825a"),p=r("aa1f"),v=r("e391"),g=r("cf98"),b=r("c770"),A=r("69f3"),m=r("83ab"),w=r("c430"),E="DOMException",T="DATA_CLONE_ERR",R=o("Error"),x=o(E)||function(){try{var t=o("MessageChannel")||i("worker_threads").MessageChannel;(new t).port1.postMessage(new WeakMap)}catch(e){if(e.name==T&&25==e.code)return e.constructor}}(),_=x&&x.prototype,C=R.prototype,I=A.set,D=A.getterFor(E),M="stack"in R(E),O=function(t){return h(g,t)&&g[t].m?g[t].c:0},S=function(){l(this,N);var t=arguments.length,e=v(t<1?void 0:arguments[0]),r=v(t<2?void 0:arguments[1],"Error"),n=O(r);if(I(this,{type:E,name:r,message:e,code:n}),m||(this.name=r,this.message=e,this.code=n),M){var i=R(e);i.name=E,u(this,"stack",s(1,b(i.stack,1)))}},N=S.prototype=c(C),U=function(t){return{enumerable:!0,configurable:!0,get:t}},k=function(t){return U((function(){return D(this)[t]}))};m&&(d(N,"code",k("code")),d(N,"message",k("message")),d(N,"name",k("name"))),u(N,"constructor",s(1,S));var L=a((function(){return!(new x instanceof R)})),W=L||a((function(){return C.toString!==p||"2: 1"!==String(new x(1,2))})),B=L||a((function(){return 25!==new x(1,"DataCloneError").code})),Y=L||25!==x[T]||25!==_[T],$=w?W||B||Y:L;n({global:!0,constructor:!0,forced:$},{DOMException:$?S:x});var F=o(E),P=F.prototype;for(var V in W&&(w||x===F)&&f(P,"toString",p),B&&m&&x===F&&d(P,"code",U((function(){return O(y(this).name)}))),g)if(h(g,V)){var q=g[V],j=q.s,z=s(6,q.c);h(F,j)||u(F,j,z),h(P,j)||u(P,j,z)}},1448:function(t,e,r){var n=r("dfb9"),i=r("b6b7");t.exports=function(t,e){return n(i(t),e)}},"145e":function(t,e,r){"use strict";var n=r("7b0b"),i=r("23cb"),o=r("07fa"),a=Math.min;t.exports=[].copyWithin||function(t,e){var r=n(this),c=o(r),s=i(t,c),u=i(e,c),f=arguments.length>2?arguments[2]:void 0,d=a((void 0===f?c:i(f,c))-u,c-s),h=1;u<s&&s<u+d&&(h=-1,u+=d-1,s+=d-1);while(d-- >0)u in r?r[s]=r[u]:delete r[s],s+=h,u+=h;return r}},"170b":function(t,e,r){"use strict";var n=r("ebb5"),i=r("50c4"),o=r("23cb"),a=r("b6b7"),c=n.aTypedArray,s=n.exportTypedArrayMethod;s("subarray",(function(t,e){var r=c(this),n=r.length,s=o(t,n),u=a(r);return new u(r.buffer,r.byteOffset+s*r.BYTES_PER_ELEMENT,i((void 0===e?n:o(e,n))-s))}))},"182d":function(t,e,r){var n=r("da84"),i=r("f8cd"),o=n.RangeError;t.exports=function(t,e){var r=i(t);if(r%e)throw o("Wrong offset");return r}},"18d8":function(t,e,r){},"219c":function(t,e,r){"use strict";var n=r("da84"),i=r("e330"),o=r("d039"),a=r("59ed"),c=r("addb"),s=r("ebb5"),u=r("04d1"),f=r("d998"),d=r("2d00"),h=r("512c"),l=s.aTypedArray,y=s.exportTypedArrayMethod,p=n.Uint16Array,v=p&&i(p.prototype.sort),g=!!v&&!(o((function(){v(new p(2),null)}))&&o((function(){v(new p(2),{})}))),b=!!v&&!o((function(){if(d)return d<74;if(u)return u<67;if(f)return!0;if(h)return h<602;var t,e,r=new p(516),n=Array(516);for(t=0;t<516;t++)e=t%4,r[t]=515-t,n[t]=t-2*e+3;for(v(r,(function(t,e){return(t/4|0)-(e/4|0)})),t=0;t<516;t++)if(r[t]!==n[t])return!0})),A=function(t){return function(e,r){return void 0!==t?+t(e,r)||0:r!==r?-1:e!==e?1:0===e&&0===r?1/e>0&&1/r<0?1:-1:e>r}};y("sort",(function(t){return void 0!==t&&a(t),b?v(this,t):c(l(this),A(t))}),!b||g)},"25a1":function(t,e,r){"use strict";var n=r("ebb5"),i=r("d58f").right,o=n.aTypedArray,a=n.exportTypedArrayMethod;a("reduceRight",(function(t){var e=arguments.length;return i(o(this),t,e,e>1?arguments[1]:void 0)}))},2954:function(t,e,r){"use strict";var n=r("ebb5"),i=r("b6b7"),o=r("d039"),a=r("f36a"),c=n.aTypedArray,s=n.exportTypedArrayMethod,u=o((function(){new Int8Array(1).slice()}));s("slice",(function(t,e){var r=a(c(this),t,e),n=i(this),o=0,s=r.length,u=new n(s);while(s>o)u[o]=r[o++];return u}),u)},3280:function(t,e,r){"use strict";var n=r("ebb5"),i=r("2ba4"),o=r("e58c"),a=n.aTypedArray,c=n.exportTypedArrayMethod;c("lastIndexOf",(function(t){var e=arguments.length;return i(o,a(this),e>1?[t,arguments[1]]:[t])}))},"34be":function(t,e,r){"use strict";r("18d8")},"3a7b":function(t,e,r){"use strict";var n=r("ebb5"),i=r("b727").findIndex,o=n.aTypedArray,a=n.exportTypedArrayMethod;a("findIndex",(function(t){return i(o(this),t,arguments.length>1?arguments[1]:void 0)}))},"3c5d":function(t,e,r){"use strict";var n=r("da84"),i=r("c65b"),o=r("ebb5"),a=r("07fa"),c=r("182d"),s=r("7b0b"),u=r("d039"),f=n.RangeError,d=n.Int8Array,h=d&&d.prototype,l=h&&h.set,y=o.aTypedArray,p=o.exportTypedArrayMethod,v=!u((function(){var t=new Uint8ClampedArray(2);return i(l,t,{length:1,0:3},1),3!==t[1]})),g=v&&o.NATIVE_ARRAY_BUFFER_VIEWS&&u((function(){var t=new d(2);return t.set(1),t.set("2",1),0!==t[0]||2!==t[1]}));p("set",(function(t){y(this);var e=c(arguments.length>1?arguments[1]:void 0,1),r=s(t);if(v)return i(l,this,r,e);var n=this.length,o=a(r),u=0;if(o+e>n)throw f("Wrong length");while(u<o)this[e+u]=r[u++]}),!v||g)},"3fcc":function(t,e,r){"use strict";var n=r("ebb5"),i=r("b727").map,o=r("b6b7"),a=n.aTypedArray,c=n.exportTypedArrayMethod;c("map",(function(t){return i(a(this),t,arguments.length>1?arguments[1]:void 0,(function(t,e){return new(o(t))(e)}))}))},"5cc6":function(t,e,r){var n=r("74e8");n("Uint8",(function(t){return function(e,r,n){return t(this,e,r,n)}}))},"5f96":function(t,e,r){"use strict";var n=r("ebb5"),i=r("e330"),o=n.aTypedArray,a=n.exportTypedArrayMethod,c=i([].join);a("join",(function(t){return c(o(this),t)}))},"60bd":function(t,e,r){"use strict";var n=r("da84"),i=r("d039"),o=r("e330"),a=r("ebb5"),c=r("e260"),s=r("b622"),u=s("iterator"),f=n.Uint8Array,d=o(c.values),h=o(c.keys),l=o(c.entries),y=a.aTypedArray,p=a.exportTypedArrayMethod,v=f&&f.prototype,g=!i((function(){v[u].call([1])})),b=!!v&&v.values&&v[u]===v.values&&"values"===v.values.name,A=function(){return d(y(this))};p("entries",(function(){return l(y(this))}),g),p("keys",(function(){return h(y(this))}),g),p("values",A,g||!b,{name:"values"}),p(u,A,g||!b,{name:"values"})},"621a":function(t,e,r){"use strict";var n=r("da84"),i=r("e330"),o=r("83ab"),a=r("a981"),c=r("5e77"),s=r("9112"),u=r("6964"),f=r("d039"),d=r("19aa"),h=r("5926"),l=r("50c4"),y=r("0b25"),p=r("77a7"),v=r("e163"),g=r("d2bb"),b=r("241c").f,A=r("9bf2").f,m=r("81d5"),w=r("4dae"),E=r("d44e"),T=r("69f3"),R=c.PROPER,x=c.CONFIGURABLE,_=T.get,C=T.set,I="ArrayBuffer",D="DataView",M="prototype",O="Wrong length",S="Wrong index",N=n[I],U=N,k=U&&U[M],L=n[D],W=L&&L[M],B=Object.prototype,Y=n.Array,$=n.RangeError,F=i(m),P=i([].reverse),V=p.pack,q=p.unpack,j=function(t){return[255&t]},z=function(t){return[255&t,t>>8&255]},G=function(t){return[255&t,t>>8&255,t>>16&255,t>>24&255]},H=function(t){return t[3]<<24|t[2]<<16|t[1]<<8|t[0]},Q=function(t){return V(t,23,4)},X=function(t){return V(t,52,8)},Z=function(t,e){A(t[M],e,{get:function(){return _(this)[e]}})},J=function(t,e,r,n){var i=y(r),o=_(t);if(i+e>o.byteLength)throw $(S);var a=_(o.buffer).bytes,c=i+o.byteOffset,s=w(a,c,c+e);return n?s:P(s)},K=function(t,e,r,n,i,o){var a=y(r),c=_(t);if(a+e>c.byteLength)throw $(S);for(var s=_(c.buffer).bytes,u=a+c.byteOffset,f=n(+i),d=0;d<e;d++)s[u+d]=f[o?d:e-d-1]};if(a){var tt=R&&N.name!==I;if(f((function(){N(1)}))&&f((function(){new N(-1)}))&&!f((function(){return new N,new N(1.5),new N(NaN),tt&&!x})))tt&&x&&s(N,"name",I);else{U=function(t){return d(this,k),new N(y(t))},U[M]=k;for(var et,rt=b(N),nt=0;rt.length>nt;)(et=rt[nt++])in U||s(U,et,N[et]);k.constructor=U}g&&v(W)!==B&&g(W,B);var it=new L(new U(2)),ot=i(W.setInt8);it.setInt8(0,2147483648),it.setInt8(1,2147483649),!it.getInt8(0)&&it.getInt8(1)||u(W,{setInt8:function(t,e){ot(this,t,e<<24>>24)},setUint8:function(t,e){ot(this,t,e<<24>>24)}},{unsafe:!0})}else U=function(t){d(this,k);var e=y(t);C(this,{bytes:F(Y(e),0),byteLength:e}),o||(this.byteLength=e)},k=U[M],L=function(t,e,r){d(this,W),d(t,k);var n=_(t).byteLength,i=h(e);if(i<0||i>n)throw $("Wrong offset");if(r=void 0===r?n-i:l(r),i+r>n)throw $(O);C(this,{buffer:t,byteLength:r,byteOffset:i}),o||(this.buffer=t,this.byteLength=r,this.byteOffset=i)},W=L[M],o&&(Z(U,"byteLength"),Z(L,"buffer"),Z(L,"byteLength"),Z(L,"byteOffset")),u(W,{getInt8:function(t){return J(this,1,t)[0]<<24>>24},getUint8:function(t){return J(this,1,t)[0]},getInt16:function(t){var e=J(this,2,t,arguments.length>1?arguments[1]:void 0);return(e[1]<<8|e[0])<<16>>16},getUint16:function(t){var e=J(this,2,t,arguments.length>1?arguments[1]:void 0);return e[1]<<8|e[0]},getInt32:function(t){return H(J(this,4,t,arguments.length>1?arguments[1]:void 0))},getUint32:function(t){return H(J(this,4,t,arguments.length>1?arguments[1]:void 0))>>>0},getFloat32:function(t){return q(J(this,4,t,arguments.length>1?arguments[1]:void 0),23)},getFloat64:function(t){return q(J(this,8,t,arguments.length>1?arguments[1]:void 0),52)},setInt8:function(t,e){K(this,1,t,j,e)},setUint8:function(t,e){K(this,1,t,j,e)},setInt16:function(t,e){K(this,2,t,z,e,arguments.length>2?arguments[2]:void 0)},setUint16:function(t,e){K(this,2,t,z,e,arguments.length>2?arguments[2]:void 0)},setInt32:function(t,e){K(this,4,t,G,e,arguments.length>2?arguments[2]:void 0)},setUint32:function(t,e){K(this,4,t,G,e,arguments.length>2?arguments[2]:void 0)},setFloat32:function(t,e){K(this,4,t,Q,e,arguments.length>2?arguments[2]:void 0)},setFloat64:function(t,e){K(this,8,t,X,e,arguments.length>2?arguments[2]:void 0)}});E(U,I),E(L,D),t.exports={ArrayBuffer:U,DataView:L}},"649e":function(t,e,r){"use strict";var n=r("ebb5"),i=r("b727").some,o=n.aTypedArray,a=n.exportTypedArrayMethod;a("some",(function(t){return i(o(this),t,arguments.length>1?arguments[1]:void 0)}))},"72f7":function(t,e,r){"use strict";var n=r("ebb5").exportTypedArrayMethod,i=r("d039"),o=r("da84"),a=r("e330"),c=o.Uint8Array,s=c&&c.prototype||{},u=[].toString,f=a([].join);i((function(){u.call({})}))&&(u=function(){return f(this)});var d=s.toString!=u;n("toString",u,d)},"735e":function(t,e,r){"use strict";var n=r("ebb5"),i=r("c65b"),o=r("81d5"),a=n.aTypedArray,c=n.exportTypedArrayMethod;c("fill",(function(t){var e=arguments.length;return i(o,a(this),t,e>1?arguments[1]:void 0,e>2?arguments[2]:void 0)}))},"74e8":function(t,e,r){"use strict";var n=r("23e7"),i=r("da84"),o=r("c65b"),a=r("83ab"),c=r("8aa7"),s=r("ebb5"),u=r("621a"),f=r("19aa"),d=r("5c6c"),h=r("9112"),l=r("eac5"),y=r("50c4"),p=r("0b25"),v=r("182d"),g=r("a04b"),b=r("1a2d"),A=r("f5df"),m=r("861d"),w=r("d9b5"),E=r("7c73"),T=r("3a9b"),R=r("d2bb"),x=r("241c").f,_=r("a078"),C=r("b727").forEach,I=r("2626"),D=r("9bf2"),M=r("06cf"),O=r("69f3"),S=r("7156"),N=O.get,U=O.set,k=D.f,L=M.f,W=Math.round,B=i.RangeError,Y=u.ArrayBuffer,$=Y.prototype,F=u.DataView,P=s.NATIVE_ARRAY_BUFFER_VIEWS,V=s.TYPED_ARRAY_CONSTRUCTOR,q=s.TYPED_ARRAY_TAG,j=s.TypedArray,z=s.TypedArrayPrototype,G=s.aTypedArrayConstructor,H=s.isTypedArray,Q="BYTES_PER_ELEMENT",X="Wrong length",Z=function(t,e){G(t);var r=0,n=e.length,i=new t(n);while(n>r)i[r]=e[r++];return i},J=function(t,e){k(t,e,{get:function(){return N(this)[e]}})},K=function(t){var e;return T($,t)||"ArrayBuffer"==(e=A(t))||"SharedArrayBuffer"==e},tt=function(t,e){return H(t)&&!w(e)&&e in t&&l(+e)&&e>=0},et=function(t,e){return e=g(e),tt(t,e)?d(2,t[e]):L(t,e)},rt=function(t,e,r){return e=g(e),!(tt(t,e)&&m(r)&&b(r,"value"))||b(r,"get")||b(r,"set")||r.configurable||b(r,"writable")&&!r.writable||b(r,"enumerable")&&!r.enumerable?k(t,e,r):(t[e]=r.value,t)};a?(P||(M.f=et,D.f=rt,J(z,"buffer"),J(z,"byteOffset"),J(z,"byteLength"),J(z,"length")),n({target:"Object",stat:!0,forced:!P},{getOwnPropertyDescriptor:et,defineProperty:rt}),t.exports=function(t,e,r){var a=t.match(/\d+$/)[0]/8,s=t+(r?"Clamped":"")+"Array",u="get"+t,d="set"+t,l=i[s],g=l,b=g&&g.prototype,A={},w=function(t,e){var r=N(t);return r.view[u](e*a+r.byteOffset,!0)},T=function(t,e,n){var i=N(t);r&&(n=(n=W(n))<0?0:n>255?255:255&n),i.view[d](e*a+i.byteOffset,n,!0)},D=function(t,e){k(t,e,{get:function(){return w(this,e)},set:function(t){return T(this,e,t)},enumerable:!0})};P?c&&(g=e((function(t,e,r,n){return f(t,b),S(function(){return m(e)?K(e)?void 0!==n?new l(e,v(r,a),n):void 0!==r?new l(e,v(r,a)):new l(e):H(e)?Z(g,e):o(_,g,e):new l(p(e))}(),t,g)})),R&&R(g,j),C(x(l),(function(t){t in g||h(g,t,l[t])})),g.prototype=b):(g=e((function(t,e,r,n){f(t,b);var i,c,s,u=0,d=0;if(m(e)){if(!K(e))return H(e)?Z(g,e):o(_,g,e);i=e,d=v(r,a);var h=e.byteLength;if(void 0===n){if(h%a)throw B(X);if(c=h-d,c<0)throw B(X)}else if(c=y(n)*a,c+d>h)throw B(X);s=c/a}else s=p(e),c=s*a,i=new Y(c);U(t,{buffer:i,byteOffset:d,byteLength:c,length:s,view:new F(i)});while(u<s)D(t,u++)})),R&&R(g,j),b=g.prototype=E(z)),b.constructor!==g&&h(b,"constructor",g),h(b,V,g),q&&h(b,q,s);var M=g!=l;A[s]=g,n({global:!0,constructor:!0,forced:M,sham:!P},A),Q in g||h(g,Q,a),Q in b||h(b,Q,a),I(s)}):t.exports=function(){}},"77a7":function(t,e,r){var n=r("da84"),i=n.Array,o=Math.abs,a=Math.pow,c=Math.floor,s=Math.log,u=Math.LN2,f=function(t,e,r){var n,f,d,h=i(r),l=8*r-e-1,y=(1<<l)-1,p=y>>1,v=23===e?a(2,-24)-a(2,-77):0,g=t<0||0===t&&1/t<0?1:0,b=0;t=o(t),t!=t||t===1/0?(f=t!=t?1:0,n=y):(n=c(s(t)/u),d=a(2,-n),t*d<1&&(n--,d*=2),t+=n+p>=1?v/d:v*a(2,1-p),t*d>=2&&(n++,d/=2),n+p>=y?(f=0,n=y):n+p>=1?(f=(t*d-1)*a(2,e),n+=p):(f=t*a(2,p-1)*a(2,e),n=0));while(e>=8)h[b++]=255&f,f/=256,e-=8;n=n<<e|f,l+=e;while(l>0)h[b++]=255&n,n/=256,l-=8;return h[--b]|=128*g,h},d=function(t,e){var r,n=t.length,i=8*n-e-1,o=(1<<i)-1,c=o>>1,s=i-7,u=n-1,f=t[u--],d=127&f;f>>=7;while(s>0)d=256*d+t[u--],s-=8;r=d&(1<<-s)-1,d>>=-s,s+=e;while(s>0)r=256*r+t[u--],s-=8;if(0===d)d=1-c;else{if(d===o)return r?NaN:f?-1/0:1/0;r+=a(2,e),d-=c}return(f?-1:1)*r*a(2,d-e)};t.exports={pack:f,unpack:d}},"7c37":function(t,e,r){var n=r("605d");t.exports=function(t){try{if(n)return Function('return require("'+t+'")')()}catch(e){}}},"81b2":function(t,e,r){var n=r("23e7"),i=r("d066"),o=r("e330"),a=r("d039"),c=r("577e"),s=r("1a2d"),u=r("d6d6"),f=r("b917").ctoi,d=/[^\d+/a-z]/i,h=/[\t\n\f\r ]+/g,l=/[=]+$/,y=i("atob"),p=String.fromCharCode,v=o("".charAt),g=o("".replace),b=o(d.exec),A=a((function(){return""!==y(" ")})),m=!a((function(){y("a")})),w=!A&&!m&&!a((function(){y()})),E=!A&&!m&&1!==y.length;n({global:!0,enumerable:!0,forced:A||m||w||E},{atob:function(t){if(u(arguments.length,1),w||E)return y(t);var e,r,n=g(c(t),h,""),o="",a=0,A=0;if(n.length%4==0&&(n=g(n,l,"")),n.length%4==1||b(d,n))throw new(i("DOMException"))("The string is not correctly encoded","InvalidCharacterError");while(e=v(n,a++))s(f,e)&&(r=A%4?64*r+f[e]:f[e],A++%4&&(o+=p(255&r>>(-2*A&6))));return o}})},"81d5":function(t,e,r){"use strict";var n=r("7b0b"),i=r("23cb"),o=r("07fa");t.exports=function(t){var e=n(this),r=o(e),a=arguments.length,c=i(a>1?arguments[1]:void 0,r),s=a>2?arguments[2]:void 0,u=void 0===s?r:i(s,r);while(u>c)e[c++]=t;return e}},"82f8":function(t,e,r){"use strict";var n=r("ebb5"),i=r("4d64").includes,o=n.aTypedArray,a=n.exportTypedArrayMethod;a("includes",(function(t){return i(o(this),t,arguments.length>1?arguments[1]:void 0)}))},"8aa7":function(t,e,r){var n=r("da84"),i=r("d039"),o=r("1c7e"),a=r("ebb5").NATIVE_ARRAY_BUFFER_VIEWS,c=n.ArrayBuffer,s=n.Int8Array;t.exports=!a||!i((function(){s(1)}))||!i((function(){new s(-1)}))||!o((function(t){new s,new s(null),new s(1.5),new s(t)}),!0)||i((function(){return 1!==new s(new c(2),1,void 0).length}))},"8bd4":function(t,e,r){var n=r("d066"),i=r("d44e"),o="DOMException";i(n(o),o)},"907a":function(t,e,r){"use strict";var n=r("ebb5"),i=r("07fa"),o=r("5926"),a=n.aTypedArray,c=n.exportTypedArrayMethod;c("at",(function(t){var e=a(this),r=i(e),n=o(t),c=n>=0?n:r+n;return c<0||c>=r?void 0:e[c]}))},"96a2":function(t,e,r){"use strict";r.r(e);var n=function(){var t=this,e=t.$createElement,r=t._self._c||e;return r("div",[r("div",{staticStyle:{padding:"5px"},on:{mousemove:function(e){return t.canvasOutMove(e)},touchmove:function(e){return t.canvasOutMove(e)}}},[r("div",{staticClass:"graffiti-container"},[r("canvas",{attrs:{id:"canvas",width:"766",height:"400"},on:{mousedown:function(e){return t.canvasDown(e)},mouseup:function(e){return t.canvasUp(e)},mousemove:function(e){return t.canvasMove(e)},touchstart:function(e){return t.canvasDown(e)},touchend:function(e){return t.canvasUp(e)},touchmove:function(e){return t.canvasMove(e)}}})])]),r("div",{staticStyle:{padding:"10px 0 0 5px"}},[r("div",{staticClass:"graffiti-tools"},[r("span",{staticClass:"graffiti-title"},[t._v("画笔颜色")]),r("div",{staticClass:"myCenter",staticStyle:{"margin-left":"2rem"}},t._l(t.colors,(function(e,n){return r("div",{key:n,staticClass:"graffiti-color",class:{activeColor:t.config.lineColor===e},style:{background:e},on:{click:function(r){return t.setColor(e)}}})})),0)]),r("div",{staticClass:"graffiti-tools"},[r("span",{staticClass:"graffiti-title"},[t._v("画笔大小")]),r("div",{staticClass:"myCenter",staticStyle:{"margin-left":"2rem"}},t._l(t.brushSize,(function(e,n){return r("i",{key:n,staticClass:"graffiti-size",class:[e.className,{activeSize:t.config.lineWidth===e.lineWidth}],on:{click:function(r){return t.setBrush(e.lineWidth)}}})})),0)]),r("div",{staticClass:"graffiti-tools"},[r("span",{staticClass:"graffiti-title"},[t._v("操作")]),r("div",{staticClass:"myCenter",staticStyle:{"margin-left":"3.7rem"}},t._l(t.controls,(function(e,n){return r("i",{key:n,staticClass:"graffiti-operate",class:e.className,attrs:{title:e.title},on:{click:function(r){return t.controlCanvas(e.action)}}})})),0)]),r("div",{staticClass:"graffiti-tools",staticStyle:{"justify-content":"center"}},[r("proButton",{staticStyle:{"margin-right":"6px"},attrs:{info:"文字",before:t.$constant.before_color_1,after:t.$constant.after_color_1},nativeOn:{click:function(e){return t.showComment()}}}),r("proButton",{attrs:{info:"提交",before:t.$constant.before_color_2,after:t.$constant.after_color_2},nativeOn:{click:function(e){return t.getImage()}}})],1)])])},i=[],o=(r("d3b7"),r("3ca3"),r("ddb0"),r("99af"),r("ac1f"),r("1276"),r("466d"),r("81b2"),r("0eb6"),r("b7ef"),r("8bd4"),r("5cc6"),r("907a"),r("9a8c"),r("a975"),r("735e"),r("c1ac"),r("d139"),r("3a7b"),r("d5d6"),r("82f8"),r("e91f"),r("60bd"),r("5f96"),r("3280"),r("3fcc"),r("ca91"),r("25a1"),r("cd26"),r("3c5d"),r("2954"),r("649e"),r("219c"),r("170b"),r("b39a"),r("72f7"),r("5319"),function(){return r.e("chunk-94e299d0").then(r.bind(null,"ff66"))}),a={components:{proButton:o},data:function(){return{context:{},canvasMoveUse:!1,preDrawAry:[],nextDrawAry:[],middleAry:[],config:{lineWidth:5,lineColor:"#8154A3",shadowBlur:2},colors:["#8154A3","#fef4ac","#0018ba","#ffc200","#f32f15","#cccccc","#5ab639"],brushSize:[{className:"small el-icon-edit",lineWidth:5},{className:"middle el-icon-edit",lineWidth:10},{className:"big el-icon-edit",lineWidth:15}]}},computed:{controls:function(){return[{title:"上一步",action:"prev",className:this.preDrawAry.length?"active el-icon-arrow-left":"ban el-icon-arrow-left"},{title:"下一步",action:"next",className:this.nextDrawAry.length?"active el-icon-arrow-right":"ban el-icon-arrow-right"},{title:"清除",action:"clear",className:this.preDrawAry.length||this.nextDrawAry.length?"active el-icon-refresh":"ban el-icon-refresh"}]}},mounted:function(){var t=document.querySelector("#canvas");this.context=t.getContext("2d",{willReadFrequently:!0}),this.initDraw(),this.setCanvasStyle()},created:function(){},methods:{canvasOutMove:function(t){var e=document.querySelector("#canvas");t.target!==e&&(this.canvasMoveUse=!1)},initDraw:function(){var t=this.context.getImageData(0,0,1200,600);this.middleAry.push(t)},canvasUp:function(t){var e=this.context.getImageData(0,0,1200,600);this.nextDrawAry.length?(this.middleAry=[],this.middleAry=this.middleAry.concat(this.preDrawAry),this.middleAry.push(e),this.nextDrawAry=[]):this.middleAry.push(e),this.canvasMoveUse=!1,this.context.beginPath()},canvasDown:function(t){this.canvasMoveUse=!0,this.setCanvasStyle(),this.context.beginPath(),this.context.moveTo(t.layerX,t.layerY);var e=this.context.getImageData(0,0,1200,600);this.preDrawAry.push(e)},canvasMove:function(t){this.canvasMoveUse&&(this.context.lineTo(t.layerX,t.layerY),this.context.stroke())},setCanvasStyle:function(){this.context.lineWidth=this.config.lineWidth,this.context.shadowBlur=this.config.shadowBlur,this.context.shadowColor=this.config.lineColor,this.context.strokeStyle=this.config.lineColor},setColor:function(t){this.config.lineColor=t},setBrush:function(t){this.config.lineWidth=t},controlCanvas:function(t){switch(t){case"prev":if(this.preDrawAry.length){var e=this.preDrawAry.pop(),r=this.middleAry[this.preDrawAry.length+1];this.nextDrawAry.push(r),this.context.putImageData(e,0,0)}break;case"next":if(this.nextDrawAry.length){var n=this.nextDrawAry.pop(),i=this.middleAry[this.middleAry.length-this.nextDrawAry.length-2];this.preDrawAry.push(i),this.context.putImageData(n,0,0)}break;case"clear":this.clearContext(),this.middleAry=[this.middleAry[0]];break}},clearContext:function(){this.context.clearRect(0,0,this.context.canvas.width,this.context.canvas.height),this.preDrawAry=[],this.nextDrawAry=[]},showComment:function(){this.clearContext(),this.$emit("showComment")},getImage:function(){if(this.$common.isEmpty(this.$store.state.currentUser))this.$message({message:"请先登录！",type:"error"});else if(this.preDrawAry.length<1)this.$message({message:"你还没画呢~",type:"warning"});else{var t=document.querySelector("#canvas"),e=t.toDataURL("image/png"),r=e.split(","),n=r[0].match(/:(.*?);/)[1],i=atob(r[1]),o=i.length,a=new Uint8Array(o);while(o--)a[o]=i.charCodeAt(o);var c=new Blob([a],{type:n}),s="graffiti/"+this.$store.state.currentUser.username.replace(/[^a-zA-Z]/g,"")+this.$store.state.currentUser.id+(new Date).getTime()+Math.floor(1e3*Math.random())+".png",u=localStorage.getItem("defaultStoreType"),f=new FormData;f.append("file",c),f.append("key",s),f.append("relativePath",s),f.append("type","graffiti"),f.append("storeType",u),"local"===u?this.saveLocal(f):"qiniu"===u&&this.saveQiniu(f)}},saveLocal:function(t){var e=this;this.$http.upload("http://47.96.86.223:8081/api/resource/upload",t).then((function(t){if(!e.$common.isEmpty(t.data)){e.clearContext();var r=t.data,n="[你画我猜,"+r+"]";e.$emit("addGraffitiComment",n)}})).catch((function(t){e.$message({message:t.message,type:"error"})}))},saveQiniu:function(t){var e=this;this.$http.get("http://47.96.86.223:8081/api/qiniu/getUpToken",{key:t.get("key")}).then((function(r){e.$common.isEmpty(r.data)||(t.append("token",r.data),e.$http.uploadQiniu(e.$store.state.sysConfig.qiniuUrl,t).then((function(r){if(!e.$common.isEmpty(r.key)){e.clearContext();var n=e.$store.state.sysConfig["qiniu.downloadUrl"]+r.key,i=t.get("file");e.$common.saveResource(e,"graffiti",n,i.size,i.type,null,"qiniu");var o="[你画我猜,"+n+"]";e.$emit("addGraffitiComment",o)}})).catch((function(t){e.$message({message:t.message,type:"error"})})))})).catch((function(t){e.$message({message:t.message,type:"error"})}))}}},c=a,s=(r("34be"),r("2877")),u=Object(s["a"])(c,n,i,!1,null,"487825b3",null);e["default"]=u.exports},"9a8c":function(t,e,r){"use strict";var n=r("e330"),i=r("ebb5"),o=r("145e"),a=n(o),c=i.aTypedArray,s=i.exportTypedArrayMethod;s("copyWithin",(function(t,e){return a(c(this),t,e,arguments.length>2?arguments[2]:void 0)}))},a078:function(t,e,r){var n=r("0366"),i=r("c65b"),o=r("5087"),a=r("7b0b"),c=r("07fa"),s=r("9a1f"),u=r("35a1"),f=r("e95a"),d=r("ebb5").aTypedArrayConstructor;t.exports=function(t){var e,r,h,l,y,p,v=o(this),g=a(t),b=arguments.length,A=b>1?arguments[1]:void 0,m=void 0!==A,w=u(g);if(w&&!f(w)){y=s(g,w),p=y.next,g=[];while(!(l=i(p,y)).done)g.push(l.value)}for(m&&b>2&&(A=n(A,arguments[2])),r=c(g),h=new(d(v))(r),e=0;r>e;e++)h[e]=m?A(g[e],e):g[e];return h}},a975:function(t,e,r){"use strict";var n=r("ebb5"),i=r("b727").every,o=n.aTypedArray,a=n.exportTypedArrayMethod;a("every",(function(t){return i(o(this),t,arguments.length>1?arguments[1]:void 0)}))},a981:function(t,e){t.exports="undefined"!=typeof ArrayBuffer&&"undefined"!=typeof DataView},aa1f:function(t,e,r){"use strict";var n=r("83ab"),i=r("d039"),o=r("825a"),a=r("7c73"),c=r("e391"),s=Error.prototype.toString,u=i((function(){if(n){var t=a(Object.defineProperty({},"name",{get:function(){return this===t}}));if("true"!==s.call(t))return!0}return"2: 1"!==s.call({message:1,name:2})||"Error"!==s.call({})}));t.exports=u?function(){var t=o(this),e=c(t.name,"Error"),r=c(t.message);return e?r?e+": "+r:e:r}:s},b39a:function(t,e,r){"use strict";var n=r("da84"),i=r("2ba4"),o=r("ebb5"),a=r("d039"),c=r("f36a"),s=n.Int8Array,u=o.aTypedArray,f=o.exportTypedArrayMethod,d=[].toLocaleString,h=!!s&&a((function(){d.call(new s(1))})),l=a((function(){return[1,2].toLocaleString()!=new s([1,2]).toLocaleString()}))||!a((function(){s.prototype.toLocaleString.call([1,2])}));f("toLocaleString",(function(){return i(d,h?c(u(this)):u(this),c(arguments))}),l)},b6b7:function(t,e,r){var n=r("ebb5"),i=r("4840"),o=n.TYPED_ARRAY_CONSTRUCTOR,a=n.aTypedArrayConstructor;t.exports=function(t){return a(i(t,t[o]))}},b7ef:function(t,e,r){"use strict";var n=r("23e7"),i=r("d066"),o=r("5c6c"),a=r("9bf2").f,c=r("1a2d"),s=r("19aa"),u=r("7156"),f=r("e391"),d=r("cf98"),h=r("c770"),l=r("c430"),y="DOMException",p=i("Error"),v=i(y),g=function(){s(this,b);var t=arguments.length,e=f(t<1?void 0:arguments[0]),r=f(t<2?void 0:arguments[1],"Error"),n=new v(e,r),i=p(e);return i.name=y,a(n,"stack",o(1,h(i.stack,1))),u(n,this,g),n},b=g.prototype=v.prototype,A="stack"in p(y),m="stack"in new v(1,2),w=A&&!m;n({global:!0,constructor:!0,forced:l||w},{DOMException:w?g:v});var E=i(y),T=E.prototype;if(T.constructor!==E)for(var R in l||a(T,"constructor",o(1,E)),d)if(c(d,R)){var x=d[R],_=x.s;c(E,_)||a(E,_,o(6,x.c))}},b917:function(t,e){for(var r="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=",n={},i=0;i<66;i++)n[r.charAt(i)]=i;t.exports={itoc:r,ctoi:n}},c1ac:function(t,e,r){"use strict";var n=r("ebb5"),i=r("b727").filter,o=r("1448"),a=n.aTypedArray,c=n.exportTypedArrayMethod;c("filter",(function(t){var e=i(a(this),t,arguments.length>1?arguments[1]:void 0);return o(this,e)}))},ca91:function(t,e,r){"use strict";var n=r("ebb5"),i=r("d58f").left,o=n.aTypedArray,a=n.exportTypedArrayMethod;a("reduce",(function(t){var e=arguments.length;return i(o(this),t,e,e>1?arguments[1]:void 0)}))},cd26:function(t,e,r){"use strict";var n=r("ebb5"),i=n.aTypedArray,o=n.exportTypedArrayMethod,a=Math.floor;o("reverse",(function(){var t,e=this,r=i(e).length,n=a(r/2),o=0;while(o<n)t=e[o],e[o++]=e[--r],e[r]=t;return e}))},cf98:function(t,e){t.exports={IndexSizeError:{s:"INDEX_SIZE_ERR",c:1,m:1},DOMStringSizeError:{s:"DOMSTRING_SIZE_ERR",c:2,m:0},HierarchyRequestError:{s:"HIERARCHY_REQUEST_ERR",c:3,m:1},WrongDocumentError:{s:"WRONG_DOCUMENT_ERR",c:4,m:1},InvalidCharacterError:{s:"INVALID_CHARACTER_ERR",c:5,m:1},NoDataAllowedError:{s:"NO_DATA_ALLOWED_ERR",c:6,m:0},NoModificationAllowedError:{s:"NO_MODIFICATION_ALLOWED_ERR",c:7,m:1},NotFoundError:{s:"NOT_FOUND_ERR",c:8,m:1},NotSupportedError:{s:"NOT_SUPPORTED_ERR",c:9,m:1},InUseAttributeError:{s:"INUSE_ATTRIBUTE_ERR",c:10,m:1},InvalidStateError:{s:"INVALID_STATE_ERR",c:11,m:1},SyntaxError:{s:"SYNTAX_ERR",c:12,m:1},InvalidModificationError:{s:"INVALID_MODIFICATION_ERR",c:13,m:1},NamespaceError:{s:"NAMESPACE_ERR",c:14,m:1},InvalidAccessError:{s:"INVALID_ACCESS_ERR",c:15,m:1},ValidationError:{s:"VALIDATION_ERR",c:16,m:0},TypeMismatchError:{s:"TYPE_MISMATCH_ERR",c:17,m:1},SecurityError:{s:"SECURITY_ERR",c:18,m:1},NetworkError:{s:"NETWORK_ERR",c:19,m:1},AbortError:{s:"ABORT_ERR",c:20,m:1},URLMismatchError:{s:"URL_MISMATCH_ERR",c:21,m:1},QuotaExceededError:{s:"QUOTA_EXCEEDED_ERR",c:22,m:1},TimeoutError:{s:"TIMEOUT_ERR",c:23,m:1},InvalidNodeTypeError:{s:"INVALID_NODE_TYPE_ERR",c:24,m:1},DataCloneError:{s:"DATA_CLONE_ERR",c:25,m:1}}},d139:function(t,e,r){"use strict";var n=r("ebb5"),i=r("b727").find,o=n.aTypedArray,a=n.exportTypedArrayMethod;a("find",(function(t){return i(o(this),t,arguments.length>1?arguments[1]:void 0)}))},d58f:function(t,e,r){var n=r("da84"),i=r("59ed"),o=r("7b0b"),a=r("44ad"),c=r("07fa"),s=n.TypeError,u=function(t){return function(e,r,n,u){i(r);var f=o(e),d=a(f),h=c(f),l=t?h-1:0,y=t?-1:1;if(n<2)while(1){if(l in d){u=d[l],l+=y;break}if(l+=y,t?l<0:h<=l)throw s("Reduce of empty array with no initial value")}for(;t?l>=0:h>l;l+=y)l in d&&(u=r(u,d[l],l,f));return u}};t.exports={left:u(!1),right:u(!0)}},d5d6:function(t,e,r){"use strict";var n=r("ebb5"),i=r("b727").forEach,o=n.aTypedArray,a=n.exportTypedArrayMethod;a("forEach",(function(t){i(o(this),t,arguments.length>1?arguments[1]:void 0)}))},dfb9:function(t,e,r){var n=r("07fa");t.exports=function(t,e){var r=0,i=n(e),o=new t(i);while(i>r)o[r]=e[r++];return o}},e58c:function(t,e,r){"use strict";var n=r("2ba4"),i=r("fc6a"),o=r("5926"),a=r("07fa"),c=r("a640"),s=Math.min,u=[].lastIndexOf,f=!!u&&1/[1].lastIndexOf(1,-0)<0,d=c("lastIndexOf"),h=f||!d;t.exports=h?function(t){if(f)return n(u,this,arguments)||0;var e=i(this),r=a(e),c=r-1;for(arguments.length>1&&(c=s(c,o(arguments[1]))),c<0&&(c=r+c);c>=0;c--)if(c in e&&e[c]===t)return c||0;return-1}:u},e91f:function(t,e,r){"use strict";var n=r("ebb5"),i=r("4d64").indexOf,o=n.aTypedArray,a=n.exportTypedArrayMethod;a("indexOf",(function(t){return i(o(this),t,arguments.length>1?arguments[1]:void 0)}))},eac5:function(t,e,r){var n=r("861d"),i=Math.floor;t.exports=Number.isInteger||function(t){return!n(t)&&isFinite(t)&&i(t)===t}},ebb5:function(t,e,r){"use strict";var n,i,o,a=r("a981"),c=r("83ab"),s=r("da84"),u=r("1626"),f=r("861d"),d=r("1a2d"),h=r("f5df"),l=r("0d51"),y=r("9112"),p=r("cb2d"),v=r("9bf2").f,g=r("3a9b"),b=r("e163"),A=r("d2bb"),m=r("b622"),w=r("90e3"),E=s.Int8Array,T=E&&E.prototype,R=s.Uint8ClampedArray,x=R&&R.prototype,_=E&&b(E),C=T&&b(T),I=Object.prototype,D=s.TypeError,M=m("toStringTag"),O=w("TYPED_ARRAY_TAG"),S=w("TYPED_ARRAY_CONSTRUCTOR"),N=a&&!!A&&"Opera"!==h(s.opera),U=!1,k={Int8Array:1,Uint8Array:1,Uint8ClampedArray:1,Int16Array:2,Uint16Array:2,Int32Array:4,Uint32Array:4,Float32Array:4,Float64Array:8},L={BigInt64Array:8,BigUint64Array:8},W=function(t){if(!f(t))return!1;var e=h(t);return"DataView"===e||d(k,e)||d(L,e)},B=function(t){if(!f(t))return!1;var e=h(t);return d(k,e)||d(L,e)},Y=function(t){if(B(t))return t;throw D("Target is not a typed array")},$=function(t){if(u(t)&&(!A||g(_,t)))return t;throw D(l(t)+" is not a typed array constructor")},F=function(t,e,r,n){if(c){if(r)for(var i in k){var o=s[i];if(o&&d(o.prototype,t))try{delete o.prototype[t]}catch(a){try{o.prototype[t]=e}catch(u){}}}C[t]&&!r||p(C,t,r?e:N&&T[t]||e,n)}},P=function(t,e,r){var n,i;if(c){if(A){if(r)for(n in k)if(i=s[n],i&&d(i,t))try{delete i[t]}catch(o){}if(_[t]&&!r)return;try{return p(_,t,r?e:N&&_[t]||e)}catch(o){}}for(n in k)i=s[n],!i||i[t]&&!r||p(i,t,e)}};for(n in k)i=s[n],o=i&&i.prototype,o?y(o,S,i):N=!1;for(n in L)i=s[n],o=i&&i.prototype,o&&y(o,S,i);if((!N||!u(_)||_===Function.prototype)&&(_=function(){throw D("Incorrect invocation")},N))for(n in k)s[n]&&A(s[n],_);if((!N||!C||C===I)&&(C=_.prototype,N))for(n in k)s[n]&&A(s[n].prototype,C);if(N&&b(x)!==C&&A(x,C),c&&!d(C,M))for(n in U=!0,v(C,M,{get:function(){return f(this)?this[O]:void 0}}),k)s[n]&&y(s[n],O,n);t.exports={NATIVE_ARRAY_BUFFER_VIEWS:N,TYPED_ARRAY_CONSTRUCTOR:S,TYPED_ARRAY_TAG:U&&O,aTypedArray:Y,aTypedArrayConstructor:$,exportTypedArrayMethod:F,exportTypedArrayStaticMethod:P,isView:W,isTypedArray:B,TypedArray:_,TypedArrayPrototype:C}},f8cd:function(t,e,r){var n=r("da84"),i=r("5926"),o=n.RangeError;t.exports=function(t){var e=i(t);if(e<0)throw o("The argument can't be less than 0");return e}}}]);