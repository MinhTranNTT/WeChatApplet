<template>
	<view>
		<!-- 头像信息，订单管理，营员管理，工具与服务，余额与优惠券 -->
		<!-- 头像信息 -->
		<view class="user-info">
			<!-- 头像 -->
			<view class="avatar">
				<button class="show-avatar-button" plain open-type="chooseAvatar" @chooseavatar="chooseAvatar" v-if="userInfo.avatar==null">
					<image class="show-avatar" src="../../static/my/user-avatar.svg"></image>
				</button>
				<image class="show-avatar" v-else :src="userInfo.avatar"></image>
			</view>
			<view class="nickname">
				<input class="show-nickname" type="nickname" @blur="getNickname" placeholder="点击修改用户名" v-if="userInfo.nickname==null"/>
				<view class="show-nickname" v-else>{{userInfo.nickname}}</view>
			</view>
			<view class="uni-icons">
				<uni-icons type="forward" size="24px" color="#ccc"></uni-icons>
			</view>
		</view>
		
		<!-- 订单管理 -->
		<navigator class="order-camper" id="order" url="../../mySubPkg/orderManage/orderManage">
			<view class="title">订单管理</view>
			<view class="subtitle">
				<view class="subt">查看全部订单</view>
				<uni-icons type="forward"></uni-icons>
			</view>
		</navigator>
		
		<!-- 营员管理 -->
		<navigator class="order-camper" id="camper" url="../../mySubPkg/campersManage/campersManage">
			<view class="title">营员管理</view>
			<view class="subtitle">
				<view class="subt">共{{camperInfo.length}}人</view>
				<uni-icons type="forward"></uni-icons>
			</view>
		</navigator>
		
		<!-- 工具与服务部分 -->
		<view class="tools-service">
			<!-- 两个部分：名字，和下边的具体服务 -->
			<view class="title-name">工具和服务</view>
			<view class="tools">
				<!-- 联系客服 -->
				<button type="default" plain class="tools-item" @tap="cantact">
					<image src="../../static/my/customer.svg"></image>
					<view class="name">联系客服</view>
				</button>
				<!-- 营员照片 -->
				<button type="default" plain class="tools-item">
					<image src="../../static/my/photo.svg"></image>
					<view class="name">营员照片</view>
				</button>
				<!-- 行程信息 -->
				<button type="default" plain class="tools-item">
					<image src="../../static/my/journey.svg"></image>
					<view class="name">行程信息</view>
				</button>
				
			</view>
		</view>
		
		<!-- 优惠券 -->
		<navigator class="order-camper" id="camper" url="../../mySubPkg/couponManage/couponManage">
			<view class="title">优惠券</view>
			<view class="subtitle">
				<view class="subt">共{{couponList.length}}张</view>
				<uni-icons type="forward"></uni-icons>
			</view>
		</navigator>
		
		<!-- 余额 -->
		<view class="order-camper" id="camper">
			<view class="title">余额</view>
			<view class="subtitle">
				<uni-icons type="forward"></uni-icons>
			</view>
		</view>
	</view>
	
</template>

<script>
	import {BASE_URL} from '@/common/global.js'
	export default {
		data() {
			return {
				// 用户信息
				userInfo: {},
				// 营员信息
				camperInfo:[],
				// 优惠券列表
				couponList:[],
			};
		},
		onLoad() {
			// 用户点到 my 界面后，先判断缓存中是否有token，如果没有就调用登录接口，否则什么都不做。在请求之前都会进行token是否存在的校验，因此，不需要再校验token是否有效
			this.onLoadOrder();
		},
		onPullDownRefresh() {
			// this.getUserInfo();
			this.onLoadOrder();
			uni.stopPullDownRefresh();
		},
		methods: {
			// onload中的顺序操作
			async onLoadOrder(){
				const that=this;
				await that.login();
				await that.getUserInfo();
				await that.getAllCampers();
				await that.getCouponList();
			},
			
			// 登录请求
			async login() {
				if (!uni.getStorageSync('token') || !uni.getStorageSync('token').data) {
					// 没有登录，直接调用登录
					await uni.$login();
					uni.$showMsg("登录成功")
				}
			},

			// 获取用户信息
			async getUserInfo() {
				const that = this;
				const {
					data: res
				} = await uni.$http.get('/user/info');
				if (res.state !== 200) return uni.$showMsg();
				// console.log(res);
				that.userInfo = res.data;
			},
			
			// 获取微信头像并更新到用户信息表中
			chooseAvatar(e){
				// console.log(e)
				// console.log(BASE_URL)
				const that=this;
				const tempAvatarPath = e.detail.avatarUrl;
				uni.uploadFile({
					// url:getApp.globalData.BASE_URL+'/user/file/upload',
					url:'https://www.zl21.club:8081/user/file/upload',
					filePath:tempAvatarPath,
					name:"file",
					header:{
						'Authorization':uni.getStorageSync("token").data,
						"Content-Type": "multipart/form-data"
					},
					success(uploadRes) {
						console.log(uploadRes)
						const data={
							"avatar":uploadRes.data,
						}
						// 头像上传成功，将头像地址更新到用户表中
						uni.$http.post('/user/update',data);
						// 重新获取用户信息
					}
				});
			},
			
			// 获取微信昵称并保存。
			async getNickname(e) {
				// console.log(e)
				const data={
					"nickname":e.detail.value,
				}
				await uni.$http.post('/user/update',data);
			},
			
			// 获取本用户的所有营员信息，并将营员信息保存在缓存中
			async getAllCampers(){
				const that=this;
				// console.log(uni.getStorageSync("campersList"))
				var campersList = uni.getStorageSync("campersList");
				if(!campersList){
					const {data:res} = await uni.$http.get("/user/camper/list");
					// console.log(res);
					that.camperInfo=res.data;
					await uni.setStorageSync("campersList",res.data);
				}else{
					that.camperInfo=campersList;
					if(campersList.length===0){
						uni.removeStorageSync("campersList")
					}
					
				}
			},
			
			// 获取优惠券列表
			async getCouponList(){
				const that=this;
				// var couponList = await uni.getStorageSync('couponList');
				// that.couponList=couponList;
				// if(!couponList){
					const {data:res} = await uni.$http.get('/user/coupon/list');
					// console.log(res)
					that.couponList=res.data;
					// 将优惠券列表放入缓存中。
					await uni.setStorageSync("couponList",res.data);
				// }
	
			},
			
			// 联系客服
			cantact(){
				wx.openCustomerServiceChat({
					extInfo: {url: 'https://work.weixin.qq.com/kfid/kfcc070d68bc93d187e'},
					corpId: 'ww5b47e3729b081c1b',
				})
			}
		}
	}
</script>

<style lang="scss">
	page{
		background-color: #FAFAFA;
	}
	.user-info{
		display: flex;
		flex-direction: row;
		height: 200rpx;
		width: 674rpx;
		background-color: #0053B8;
		margin: 20rpx 38rpx 20rpx 38rpx;
		border-radius: 20rpx;
		box-shadow: #A59F9F 5px 5px 5px;
		align-items: center;
		.avatar{
			button{
				background-color: #0053B8;
				display: flex;
				align-items: center;
				justify-content: center;
				border: none;
				outline: none;
			}
			image{
				width: 150rpx;
				height: 150rpx;
				border-radius: 50%;
				margin-left: 30rpx;
			}
		}
		.nickname{
			color: #cccfcc;
			margin-left: 20rpx;
			.show-nickname{
			  max-width: 100px;
			  white-space: nowrap;
			  overflow: hidden;
			  text-overflow: ellipsis;
			}
		}
		.uni-icons{
			display: flex;
			align-items: center;
			margin: auto 0 auto auto;
			width: 180rpx;
			height: 100%;
			uni-icons{
				margin-left: 100rpx;
			}
		}
	}
	
	.order-camper{
		display: flex;
		flex-direction: row;
		height: 100rpx;
		width: 674rpx;
		background-color: #fff;
		margin: 20rpx 38rpx 20rpx 38rpx;
		border-radius: 15rpx;
		box-shadow: #E7E2E2 5px 5px 5px;
		align-items: center;
		.title{
			margin: 10rpx 0 10rpx 20rpx;
			font-weight: bold;
		}
		.subtitle{
			display: flex;
			flex-direction: row;
			margin: auto 20rpx auto auto;
			.subt{
				color: #ACACAC;
				font-size: 28rpx;
				margin-right: 10rpx;
			}
		}
	}
	#order{
		margin-top: 40rpx;
	}
	
	.tools-service{
		display: flex;
		flex-direction: column;
		width: 674rpx;
		background-color: #fff;
		margin: 20rpx 38rpx 20rpx 38rpx;
		border-radius: 15rpx;
		box-shadow: #E7E2E2 5px 5px 5px;
		.title-name{
			margin: 20rpx 0 20rpx 20rpx;
			font-weight: bold;
		}
		.tools{
			height: 200rpx;
			width: 100%;
			display: flex;
			flex-direction: row;
			padding: 10rpx 20rpx 20rpx 20rpx;
			// justify-content: center;
			button{
				border: none;
			}
			.tools-item{
				display: flex;
				flex-direction: column;
				// justify-content: center;
				align-items: center;
				margin: 20rpx 30rpx 20rpx 30rpx;
				image{
					width: 60rpx;
					height: 60rpx;
				}
				.name{
					font-size: 23rpx;
					
				}
			}
		}
		
	}
</style>
