// #ifndef VUE3
import Vue from 'vue'
import App from './App'
// 导入黑马的网络请求包
import {
	$http
} from '@escook/request-miniprogram'

Vue.config.productionTip = false

App.mpType = 'app'

uni.$http = $http
// 配置网络请求包的根路径
$http.baseUrl = 'http://localhost:80'
$http.baseUrl = 'https://www.zl21.club:8081'


// 请求开始之前显示数据加载loading
$http.beforeRequest = function(options) {
	if(options.url.indexOf('/user')!==-1){
		// 从缓存中拿token，判断token是否过期，如果没过期，则将token放在请求头中，否则，调用登录，重新登录
		const tokenCache = uni.getStorageSync('token');
		// 检查token是否过期
		if(new Date().getTime()>tokenCache.expirationTime){
			// uni.$showMsg("登录过期")
			// 登录过期，直接调用登录接口
			uni.$login();
		}else{
			// 否则，token有效，将token放在请求头中。
			options.header={
				"Authorization":tokenCache.data
			}
		}
	}
	uni.showLoading({
		title: '数据加载中...',
		mask:true
	})
}
// 请求完成后隐藏loading
$http.afterRequest = function() {
	uni.hideLoading()
}

// 封装展示消息提示的方法
uni.$showMsg = function(title = "数据加载失败！", duration = 1500) {
	uni.showToast({
		title,
		duration,
		icon: 'none',
	})
}

// 全局时间格式过滤器
Vue.filter('dateFormat', function(timestamp, format) {
	// Convert the timestamp to a date object
	var date = new Date(timestamp);
	
	// Get the year, month, and day from the date object
	var year = date.getFullYear();
	var month = String(date.getMonth() + 1).padStart(2,'0');
	var day = String(date.getDate()).padStart(2,'0');
	var hour = String(date.getHours()).padStart(2,'0');
	var minute = String(date.getMinutes()).padStart(2,'0');
	var second = String(date.getSeconds()).padStart(2,'0');
	var dayOfWeek = date.getDay();
	const weekdays = ['周日', '周一', '周二', '周三', '周四', '周五', '周六'];
	var dayOfWeekText = weekdays[dayOfWeek];

	// Replace the placeholders in the format string with the corresponding values
	format = format.replace('YYYY', year);
	format = format.replace('MM', month);
	format = format.replace('DD', day);
	format = format.replace('HH', hour);
	format = format.replace('mm', minute);
	format = format.replace('ss', second);
	format = format.replace('dd', dayOfWeekText)
	return format;

})

// 用户登录，并将token存入缓存
uni.$login=async function(){
	const [err, loginRes] =await uni.login().catch(err => err)
	// console.log(err)
	// console.log(loginRes)
	if(err || loginRes.errMsg !=="login:ok") return uni.$showMsg("登陆失败");
	const {data:res} = await uni.$http.post('/login',loginRes.code)
	// console.log(res);
	if(res.state!==200) uni.$showMsg("登陆失败，请重试...");
	// 登录成功，将token存入缓存，并设置缓存有效时间为1天
	const expirationTime = new Date().getTime()+86400*1000;
	// const expirationTime = new Date().getTime()+20*1000;
	uni.setStorageSync('token',{
		"data":res.data,
		"expirationTime":expirationTime
	})
}

// 上传文件
uni.$uploadFile=async function(tempFilePath){
	var tokenCache = uni.getStorageSync('token');
	// 检查token是否过期
	if(new Date().getTime()>tokenCache.expirationTime){
		// uni.$showMsg("登录过期")
		// 登录过期，直接调用登录接口
		await uni.$login();
		tokenCache = uni.getStorageSync('token')
	}
	uni.showLoading({
		title:"上传中...",
		mask:true
	})
	const [err,res] = await uni.uploadFile({
		url:getApp().globalData.BASE_URL+'/user/file/upload',
		name:'file',
		filePath:tempFilePath,
		header:{
			'Authorization':tokenCache.data,
			"Content-Type": "multipart/form-data"
		},
		// success(res) {
		// 	return res;
		// }
	})
	uni.hideLoading();
	return [err,res]
}

// 图片预览功能
uni.$previewImage = function(image){
	var arr = [];
	arr.push(image)
	uni.previewImage({
		urls:arr,
		current:0
	})
}

const app = new Vue({
	...App
})
app.$mount()
// #endif

// #ifdef VUE3
import {
	createSSRApp
} from 'vue'
import App from './App.vue'
export function createApp() {
	const app = createSSRApp(App)
	return {
		app
	}
}
// #endif
