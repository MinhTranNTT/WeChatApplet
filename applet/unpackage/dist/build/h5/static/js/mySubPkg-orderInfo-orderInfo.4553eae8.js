(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["mySubPkg-orderInfo-orderInfo"],{1319:function(t,e,i){var n=i("9823");n.__esModule&&(n=n.default),"string"===typeof n&&(n=[[t.i,n,""]]),n.locals&&(t.exports=n.locals);var a=i("4f06").default;a("2b39d8b5",n,!0,{sourceMap:!1,shadowMode:!1})},"29c7":function(t,e,i){"use strict";i.r(e);var n=i("9e6a"),a=i.n(n);for(var r in n)["default"].indexOf(r)<0&&function(t){i.d(e,t,(function(){return n[t]}))}(r);e["default"]=a.a},"33a2":function(t,e,i){var n=i("a740");n.__esModule&&(n=n.default),"string"===typeof n&&(n=[[t.i,n,""]]),n.locals&&(t.exports=n.locals);var a=i("4f06").default;a("64c0559d",n,!0,{sourceMap:!1,shadowMode:!1})},"3f9e":function(t,e,i){"use strict";i.r(e);var n=i("6067"),a=i.n(n);for(var r in n)["default"].indexOf(r)<0&&function(t){i.d(e,t,(function(){return n[t]}))}(r);e["default"]=a.a},"53e5":function(t,e,i){"use strict";i.r(e);var n=i("d56f"),a=i("29c7");for(var r in a)["default"].indexOf(r)<0&&function(t){i.d(e,t,(function(){return a[t]}))}(r);i("83c8");var o=i("f0c5"),s=Object(o["a"])(a["default"],n["b"],n["c"],!1,null,"83544afa",null,!1,n["a"],void 0);e["default"]=s.exports},"5d16":function(t,e,i){"use strict";i.r(e);var n=i("fe8b"),a=i("3f9e");for(var r in a)["default"].indexOf(r)<0&&function(t){i.d(e,t,(function(){return a[t]}))}(r);i("a81b");var o=i("f0c5"),s=Object(o["a"])(a["default"],n["b"],n["c"],!1,null,"56b182d5",null,!1,n["a"],void 0);e["default"]=s.exports},6067:function(t,e,i){"use strict";i("7a82");var n=i("4ea4").default;Object.defineProperty(e,"__esModule",{value:!0}),e.default=void 0;var a=n(i("c7eb")),r=n(i("1da1")),o={data:function(){return{orderInfo:{}}},onLoad:function(t){this.getOrderInfo(t.order_id)},methods:{getOrderInfo:function(t){var e=this;return(0,r.default)((0,a.default)().mark((function i(){var n,r,o;return(0,a.default)().wrap((function(i){while(1)switch(i.prev=i.next){case 0:return n=e,i.next=3,uni.getStorageSync("orderList");case 3:for(r=i.sent,o=0;o<r.length;o++)r[o].sn===t&&(n.orderInfo=r[o]);case 5:case"end":return i.stop()}}),i)})))()},cancleOrder:function(){var t=this;return(0,r.default)((0,a.default)().mark((function e(){var i,n;return(0,a.default)().wrap((function(e){while(1)switch(e.prev=e.next){case 0:return i=t,n=i.orderInfo,e.next=4,uni.$http.put("/user/order/cancel",n);case 4:uni.redirectTo({url:"/mySubPkg/orderManage/orderManage"});case 5:case"end":return e.stop()}}),e)})))()},deleteOrder:function(t){var e=this;return(0,r.default)((0,a.default)().mark((function t(){var i,n;return(0,a.default)().wrap((function(t){while(1)switch(t.prev=t.next){case 0:return i=e,n=i.orderInfo,t.next=4,uni.$http.put("/user/order/delete",n);case 4:uni.redirectTo({url:"/mySubPkg/orderManage/orderManage"});case 5:case"end":return t.stop()}}),t)})))()},payOrder:function(t){var e=this;return(0,r.default)((0,a.default)().mark((function t(){var i,n,r,o;return(0,a.default)().wrap((function(t){while(1)switch(t.prev=t.next){case 0:return i=e,n=i.orderInfo,r=n.sn,console.log(r),t.next=6,uni.getStorageSync("order"+r);case 6:if(o=t.sent,!((new Date).getTime()>o.expirationTime)){t.next=10;break}return uni.showToast({title:"订单已过期,请刷新重试",icon:"none",mask:!0}),t.abrupt("return");case 10:return console.log(o),t.next=13,uni.requestPayment({appId:o.data.appId,nonceStr:o.data.nonceStr,package:o.data.package,paySign:o.data.paySign,signType:o.data.signType,timeStamp:o.data.timeStamp,success:function(t){uni.$showMsg("支付成功"),uni.removeStorageSync("order"+o.data.sn),uni.redirectTo({url:"/mySubPkg/orderManage/orderManage"})},fail:function(t){uni.$showMsg("取消支付"),uni.redirectTo({url:"/mySubPkg/orderManage/orderManage"})}});case 13:case"end":return t.stop()}}),t)})))()},applyRefund:function(t){var e=this;return(0,r.default)((0,a.default)().mark((function t(){var i,n;return(0,a.default)().wrap((function(t){while(1)switch(t.prev=t.next){case 0:return i=e,n=i.orderInfo,t.next=4,uni.$http.post("/user/wxRefund/refund",n);case 4:case"end":return t.stop()}}),t)})))()},cancleApply:function(t){},contact:function(){wx.openCustomerServiceChat({extInfo:{url:"https://work.weixin.qq.com/kfid/kfcc070d68bc93d187e"},corpId:"ww5b47e3729b081c1b"})}}};e.default=o},"83c8":function(t,e,i){"use strict";var n=i("33a2"),a=i.n(n);a.a},9823:function(t,e,i){var n=i("24fb");e=n(!1),e.push([t.i,'@charset "UTF-8";\n/* 水平间距 */\n/* 水平间距 */uni-page-body[data-v-56b182d5]{background-color:#ccc}body.?%PAGE?%[data-v-56b182d5]{background-color:#ccc}.order-status[data-v-56b182d5]{display:flex;flex-direction:row;justify-content:center;align-items:center;width:704;height:%?100?%;font-size:%?40?%;color:red;margin:%?20?% %?23?% %?20?% %?23?%;border-radius:%?20?%;background-color:#fff}.order-status .stat #refund[data-v-56b182d5]{color:#0eb117}.product-info[data-v-56b182d5]{display:flex;flex-direction:row;background-color:#fff;width:%?704?%;height:%?250?%;margin:%?20?% %?23?% %?20?% %?23?%;border-radius:%?30?%;align-items:center}.product-info uni-image[data-v-56b182d5]{width:%?200?%;height:%?200?%;margin:0 %?20?% 0 %?20?%;border-radius:%?30?%}.product-info .product-name[data-v-56b182d5]{width:%?350?%;height:80%;display:flex;flex-direction:column;margin-top:%?50?%;font-size:%?28?%}.product-info .product-price[data-v-56b182d5]{display:flex;flex-direction:column;align-items:flex-end;margin:%?50?% %?20?% 0 auto;height:80%}.product-info .product-price .price[data-v-56b182d5]{color:#ff0035;font-size:%?40?%}.product-info .product-price .num[data-v-56b182d5]{color:#999;font-size:%?30?%;margin:%?10?% %?10?% 0 0}.order-info[data-v-56b182d5]{display:flex;flex-direction:column;background-color:#fff;width:%?704?%;margin:%?20?% %?23?% %?20?% %?23?%;border-radius:%?30?%;justify-content:center}.order-info .title[data-v-56b182d5]{margin:%?20?% %?20?% %?20?% %?20?%;border-bottom:#ccc dashed %?3?%;font-size:%?35?%;font-weight:700}.order-info .order-item[data-v-56b182d5]{margin:%?10?% %?20?% %?10?% %?20?%;display:flex;flex-direction:row;align-items:center;height:%?50?%}.order-info .order-item #price[data-v-56b182d5]{color:#ff0035}.order-info .order-item .item-text[data-v-56b182d5]{margin-left:%?10?%;font-size:%?30?%;font-weight:700}.order-info .order-item .item-info[data-v-56b182d5]{margin:0 %?10?% 0 auto;font-size:%?27?%;color:#999;max-width:%?450?%;white-space:nowrap;overflow:hidden;text-overflow:ellipsis}.order-info .options[data-v-56b182d5]{display:flex;flex-direction:row;justify-content:flex-end;align-items:center;margin:%?10?% 0 %?20?% 0}.order-info .options #one[data-v-56b182d5]{margin:0 %?30?% 0 auto}.order-info .options #two[data-v-56b182d5]{margin:0 %?20?% 0 %?0?%}.order-info .options #three[data-v-56b182d5]{margin:0 %?20?% 0 auto}.order-info .options .time[data-v-56b182d5]{margin:0 auto 0 %?20?%;font-size:%?28?%;color:#999}.order-info .options uni-button[data-v-56b182d5]{display:flex;flex-direction:row;border:#999 solid 1px;font-size:%?25?%;font-weight:700}.camper-info[data-v-56b182d5]{display:flex;flex-direction:column;background-color:#fff;width:%?704?%;margin:%?20?% %?23?% %?20?% %?23?%;border-radius:%?30?%;justify-content:center}.camper-info .camper-title[data-v-56b182d5]{margin:%?20?% %?20?% 0 %?20?%;border-bottom:#ccc dashed %?3?%;font-size:%?35?%;font-weight:700}.camper-info .camper-info-item[data-v-56b182d5]{display:flex;flex-direction:row;margin:%?10?% %?20?% %?10?% %?20?%}.camper-info .camper-info-item .name[data-v-56b182d5]{margin:0 auto 0 %?10?%}.camper-info .camper-info-item .gender[data-v-56b182d5]{margin:0 %?20?% 0 auto}.camper-info .camper-info-item .gender uni-image[data-v-56b182d5]{width:%?45?%;height:%?45?%}.contact[data-v-56b182d5]{display:flex;flex-direction:column;background-color:#fff;width:%?704?%;margin:%?20?% %?23?% %?20?% %?23?%;border-radius:%?30?%;justify-content:center}.contact .contact-btn[data-v-56b182d5]{border:none;border-radius:%?30?%;background-color:#fff;width:100%}',""]),t.exports=e},"9e6a":function(t,e,i){"use strict";i("7a82"),Object.defineProperty(e,"__esModule",{value:!0}),e.default=void 0;e.default={name:"empty-area",data:function(){return{}}}},a740:function(t,e,i){var n=i("24fb");e=n(!1),e.push([t.i,'@charset "UTF-8";\n/* 水平间距 */\n/* 水平间距 */.empty[data-v-83544afa]{height:%?150?%}',""]),t.exports=e},a81b:function(t,e,i){"use strict";var n=i("1319"),a=i.n(n);a.a},b8cb:function(t,e,i){t.exports=i.p+"static/img/female.22262995.svg"},d56f:function(t,e,i){"use strict";i.d(e,"b",(function(){return n})),i.d(e,"c",(function(){return a})),i.d(e,"a",(function(){}));var n=function(){var t=this.$createElement,e=this._self._c||t;return e("v-uni-view",[e("v-uni-view",{staticClass:"empty"})],1)},a=[]},fe27:function(t,e,i){t.exports=i.p+"static/img/male.b37df959.svg"},fe8b:function(t,e,i){"use strict";i.d(e,"b",(function(){return a})),i.d(e,"c",(function(){return r})),i.d(e,"a",(function(){return n}));var n={emptyArea:i("53e5").default},a=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("v-uni-view",[n("v-uni-view",{staticClass:"order-status"},[0===t.orderInfo.refundStatus?n("v-uni-view",{staticClass:"stat"},[0===t.orderInfo.status?n("v-uni-view",{staticClass:"status"},[t._v("待支付")]):1===t.orderInfo.status?n("v-uni-view",{staticClass:"status"},[t._v("已支付")]):2===t.orderInfo.status?n("v-uni-view",{staticClass:"status"},[t._v("已完成")]):n("v-uni-view",{staticClass:"status"},[t._v("已取消")])],1):1===t.orderInfo.refundStatus?n("v-uni-view",{staticClass:"stat"},[n("v-uni-view",{staticClass:"status",attrs:{id:"refund"}},[t._v("已申请退款")])],1):2===t.orderInfo.refundStatus?n("v-uni-view",{staticClass:"stat"},[n("v-uni-view",{staticClass:"status",attrs:{id:"refund"}},[t._v("退款成功")])],1):t._e()],1),n("v-uni-view",{staticClass:"product-info"},[n("v-uni-image",{attrs:{src:t.orderInfo.productImage}}),n("v-uni-view",{staticClass:"product-name"},[n("v-uni-view",[t._v(t._s(t.orderInfo.productName))])],1),n("v-uni-view",{staticClass:"product-price"},[n("v-uni-view",{staticClass:"price"},[t._v("￥"+t._s(Number((t.orderInfo.originalPrice/100).toFixed(2))))]),n("v-uni-view",{staticClass:"num"},[t._v("x"+t._s(t.orderInfo.productCount))])],1)],1),n("v-uni-view",{staticClass:"order-info"},[n("v-uni-view",{staticClass:"title"},[t._v("营期信息")]),n("v-uni-view",{staticClass:"order-item",attrs:{id:"first"}},[n("v-uni-view",{staticClass:"item-text"},[t._v("营期时间")]),n("v-uni-view",{staticClass:"item-info"},[t._v(t._s(t._f("dateFormat")(t.orderInfo.camperInfo.beginTime,"YYYY-MM-DD"))+" ~ "+t._s(t._f("dateFormat")(t.orderInfo.camperInfo.endTime,"YYYY-MM-DD")))])],1),n("v-uni-view",{staticClass:"order-item"},[n("v-uni-view",{staticClass:"item-text"},[t._v("启程集合地点")]),n("v-uni-view",{staticClass:"item-info"},[t._v(t._s(t.orderInfo.registerLocation))])],1),n("v-uni-view",{staticClass:"order-item",attrs:{id:"last"}},[n("v-uni-view",{staticClass:"item-text"},[t._v("返程集合地点")]),n("v-uni-view",{staticClass:"item-info"},[t._v(t._s(t.orderInfo.returnLocation))])],1)],1),n("v-uni-view",{staticClass:"camper-info"},[n("v-uni-view",{staticClass:"camper-title"},[t._v("营员信息")]),t._l(t.orderInfo.camperList,(function(e,a){return n("v-uni-view",{key:a,staticClass:"camper-info-item"},[n("v-uni-view",{staticClass:"name"},[t._v(t._s(e.name))]),n("v-uni-view",{staticClass:"id"},[e.validIdNumber?n("v-uni-view",{staticClass:"idcard"},[t._v(t._s(e.validIdNumber))]):n("v-uni-view",{staticClass:"idcard"},[t._v(t._s(e.otherIdNumber))])],1),n("v-uni-view",{staticClass:"gender"},[0===e.gender?n("v-uni-image",{attrs:{src:i("fe27")}}):n("v-uni-image",{attrs:{src:i("b8cb")}})],1)],1)}))],2),n("v-uni-view",{staticClass:"order-info"},[n("v-uni-view",{staticClass:"title"},[t._v("订单信息")]),n("v-uni-view",{staticClass:"order-item",attrs:{id:"first"}},[n("v-uni-view",{staticClass:"item-text"},[t._v("商品总价")]),n("v-uni-view",{staticClass:"item-info",attrs:{id:"price"}},[t._v("￥ "+t._s(Number(t.orderInfo.payPrice/100).toFixed(2)))])],1),n("v-uni-view",{staticClass:"order-item"},[n("v-uni-view",{staticClass:"item-text"},[t._v("订单编号")]),n("v-uni-text",{staticClass:"item-info",attrs:{selectable:"true"}},[t._v(t._s(t.orderInfo.sn))])],1),1==t.orderInfo.status||2==t.orderInfo.status?n("v-uni-view",{staticClass:"order-item"},[n("v-uni-view",{staticClass:"item-text"},[t._v("支付交易号")]),n("v-uni-view",{staticClass:"item-info"},[t._v(t._s(t.orderInfo.payOrderId))])],1):t._e(),n("v-uni-view",{staticClass:"order-item"},[n("v-uni-view",{staticClass:"item-text"},[t._v("创建时间")]),n("v-uni-view",{staticClass:"item-info"},[t._v(t._s(t._f("dateFormat")(t.orderInfo.createTime,"YYYY-MM-DD HH:mm:ss")))])],1),1===t.orderInfo.status||2===t.orderInfo.status?n("v-uni-view",{staticClass:"order-item"},[n("v-uni-view",{staticClass:"item-text"},[t._v("支付时间")]),n("v-uni-view",{staticClass:"item-info"},[t._v(t._s(t._f("dateFormat")(t.orderInfo.payTime,"YYYY-MM-DD HH:mm:ss")))])],1):t._e(),2===t.orderInfo.status?n("v-uni-view",{staticClass:"order-item"},[n("v-uni-view",{staticClass:"item-text"},[t._v("完成时间")]),n("v-uni-view",{staticClass:"item-info"},[t._v(t._s(t._f("dateFormat")(t.orderInfo.finishTime,"YYYY-MM-DD HH:mm:ss")))])],1):t._e(),3===t.orderInfo.status?n("v-uni-view",{staticClass:"order-item",attrs:{id:"last"}},[n("v-uni-view",{staticClass:"item-text"},[t._v("取消时间")]),n("v-uni-view",{staticClass:"item-info"},[t._v(t._s(t._f("dateFormat")(t.orderInfo.calcelTime,"YYYY-MM-DD HH:mm:ss")))])],1):t._e(),0===t.orderInfo.refundStatus?n("v-uni-view",[0==t.orderInfo.status?n("v-uni-view",{staticClass:"options"},[n("v-uni-button",{attrs:{id:"one",type:"default",plain:!0},on:{click:function(e){arguments[0]=e=t.$handleEvent(e),t.cancleOrder()}}},[t._v("取消订单")]),n("v-uni-button",{attrs:{id:"two",type:"default",plain:!0},on:{click:function(e){arguments[0]=e=t.$handleEvent(e),t.payOrder()}}},[t._v("去付款")])],1):1==t.orderInfo.status?n("v-uni-view",{staticClass:"options"},[n("v-uni-button",{attrs:{id:"one",type:"default",plain:!0},on:{click:function(e){arguments[0]=e=t.$handleEvent(e),t.deleteOrder.apply(void 0,arguments)}}},[t._v("删除订单")]),n("v-uni-button",{attrs:{id:"two",type:"default",plain:!0},on:{click:function(e){arguments[0]=e=t.$handleEvent(e),t.applyRefund.apply(void 0,arguments)}}},[t._v("申请退款")])],1):n("v-uni-view",{staticClass:"options"},[n("v-uni-button",{attrs:{id:"three",type:"default",plain:!0},on:{click:function(e){arguments[0]=e=t.$handleEvent(e),t.deleteOrder.apply(void 0,arguments)}}},[t._v("删除订单")])],1),0===t.orderInfo.status?n("v-uni-view",{staticClass:"tips"},[n("v-uni-view",[t._v("订单会保留30分钟，请及时支付")])],1):t._e()],1):1===t.orderInfo.refundStatus?n("v-uni-view",[n("v-uni-view",{staticClass:"options"},[n("v-uni-button",{attrs:{id:"three",type:"default",plain:!0},on:{click:function(e){arguments[0]=e=t.$handleEvent(e),t.deleteOrder.apply(void 0,arguments)}}},[t._v("取消申请")])],1)],1):n("v-uni-view",[n("v-uni-view",{staticClass:"options"},[n("v-uni-button",{attrs:{id:"three",type:"default",plain:!0},on:{click:function(e){arguments[0]=e=t.$handleEvent(e),t.deleteOrder.apply(void 0,arguments)}}},[t._v("删除订单")])],1)],1)],1),n("v-uni-view",{staticClass:"contact",on:{click:function(e){arguments[0]=e=t.$handleEvent(e),t.contact.apply(void 0,arguments)}}},[n("v-uni-button",{staticClass:"contact-btn",attrs:{type:"default",plain:!0}},[t._v("联系客服")])],1),n("empty-area")],1)},r=[]}}]);