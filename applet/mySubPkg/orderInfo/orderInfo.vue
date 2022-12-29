<template>
	<view>
		<!-- 商品的信息，订单的信息，联系客服 -->
		<!-- 订单状态 -->
		<view class="order-status">
			<view v-if="orderInfo.refundStatus===0" class="stat">
				<view class="status" v-if="orderInfo.status===0">待支付</view>
				<view class="status" v-else-if="orderInfo.status===1">已支付</view>
				<view class="status" v-else-if="orderInfo.status===2">已完成</view>
				<view class="status" v-else>已取消</view>
			</view>
			<view v-else-if="orderInfo.refundStatus===1" class="stat">
				<view class="status" id="refund">已申请退款</view>
			</view>
			<view v-else-if="orderInfo.refundStatus===2" class="stat">
				<view class="status" id="refund">退款成功</view>
			</view>
<!-- 			<view v-if="orderInfo.status===0">待付款</view>
			<view v-else-if="orderInfo.status===1">已付款</view>
			<view v-else-if="orderInfo.status===2">已完成</view>
			<view v-else>已取消</view> -->
		</view>
		
		<!-- 商品的信息 -->
		<view class="product-info">
			<image :src="orderInfo.productImage"></image>
			<view class="product-name">
				<view>{{orderInfo.productName}}</view>
			</view>
			<view class="product-price">
				<view class="price">￥{{Number((orderInfo.originalPrice/100).toFixed(2))}}</view>
				<view class="num">x{{orderInfo.productCount}}</view>
			</view>
		</view>
		
		<!-- 营期信息 时间 ,启程集合时间，返程集合时间-->
		<view class="order-info">
			<view class="title">营期信息</view>
			<!-- 时间 -->
			<view class="order-item" id="first">
				<view class="item-text">营期时间</view>
				<view class="item-info">{{orderInfo.camperInfo.beginTime | dateFormat('YYYY-MM-DD')}} ~ {{orderInfo.camperInfo.endTime | dateFormat('YYYY-MM-DD')}}</view>
			</view>
			<!-- 启程集合地点 -->
			<view class="order-item">
				<view class="item-text">启程集合地点</view>
				<view class="item-info">{{orderInfo.registerLocation}}</view>
			</view>
			<!-- 返程集合地点 -->
			<view class="order-item" id="last">
				<view class="item-text">返程集合地点</view>
				<view class="item-info">{{orderInfo.returnLocation}}</view>
			</view>
		</view>
		
		<!-- 营员信息 -->
		<view class="camper-info">
			<view class="camper-title">营员信息</view>
			<view class="camper-info-item" v-for="(item,i) in orderInfo.camperList" :key="i">
				<!-- 营员姓名 -->
				<view class="name">{{item.name}}</view>
				<view class="id">
					<view class="idcard" v-if="item.validIdNumber">{{item.validIdNumber}}</view>
					<view class="idcard" v-else>{{item.otherIdNumber}}</view>
				</view>
				<view class="gender">
					<image src="../../static/my/male.svg" v-if="item.gender===0"></image>
					<image src="../../static/my/female.svg" v-else></image>
				</view>
			</view>
		</view>
		
		<!-- 订单信息 -->
		<view class="order-info">
			<!-- 订单号，订单总价，订单编号，创建时间，支付时间，完成时间 -->
			<!-- 订单总价 -->
			<view class="title">订单信息</view>
			<view class="order-item" id="first">
				<view class="item-text">商品总价</view>
				<view class="item-info" id="price">￥ {{Number(orderInfo.payPrice/100).toFixed(2)}}</view>
			</view>
			<!-- 订单号 -->
			<view class="order-item">
				<view class="item-text">订单编号</view>
				<text selectable="true" class="item-info">{{orderInfo.sn}}</text>
			</view>
			<!-- 支付交易号 -->
			<view class="order-item" v-if="orderInfo.status==1||orderInfo.status==2">
				<view class="item-text">支付交易号</view>
				<view class="item-info">{{orderInfo.payOrderId}}</view>
			</view>
			<!-- 订单创建时间 -->
			<view class="order-item">
				<view class="item-text">创建时间</view>
				<view class="item-info">{{orderInfo.createTime | dateFormat('YYYY-MM-DD HH:mm:ss')}}</view>
			</view>
			<!-- 订单支付时间 -->
			<view class="order-item" v-if="orderInfo.status===1 || orderInfo.status===2">
				<view class="item-text">支付时间</view>
				<view class="item-info">{{orderInfo.payTime |  dateFormat('YYYY-MM-DD HH:mm:ss')}}</view>
			</view>
			<!-- 订单完成时间 -->
			<view class="order-item" v-if="orderInfo.status===2">
				<view class="item-text">完成时间</view>
				<view class="item-info">{{orderInfo.finishTime |  dateFormat('YYYY-MM-DD HH:mm:ss')}}</view>
			</view>
			<!-- 订单取消时间 -->
			<view class="order-item" v-if="orderInfo.status===3" id="last">
				<view class="item-text">取消时间</view>
				<view class="item-info">{{orderInfo.calcelTime |  dateFormat('YYYY-MM-DD HH:mm:ss')}}</view>
			</view>
			<!-- 选项 -->
			<view v-if="orderInfo.refundStatus===0">
				<view class="options" v-if="orderInfo.status==0">
					<!-- <view class="time">{{orderInfo.createTime | dateFormat("YYYY-MM-DD HH:mm:ss")}}</view> -->
					<button id="one" type="default" plain @tap="cancleOrder()">取消订单</button>
					<button id="two" type="default" plain @tap="payOrder()">去付款</button>
				</view>
				<view class="options" v-else-if="orderInfo.status == 1">
					<!-- <view class="time">{{orderInfo.createTime | dateFormat("YYYY-MM-DD HH:mm:ss")}}</view> -->
					<button id="one" type="default" plain @tap="deleteOrder">删除订单</button>
					<button id="two" type="default" plain @tap="applyRefund"> 申请退款 </button>
				</view>
				<view class="options" v-else>
					<!-- <view class="time">{{orderInfo.createTime | dateFormat("YYYY-MM-DD HH:mm:ss")}}</view> -->
					<button id="three" type="default" plain @tap="deleteOrder">删除订单</button>
					<!-- <button id="two" type="default" plain> 退款 </button> -->
				</view>
				<view class="tips" v-if="orderInfo.status===0">
					<view>订单会保留30分钟，请及时支付</view>
				</view>
			</view>
			<view v-else-if="orderInfo.refundStatus===1">
				<view class="options">
					<!-- <view class="time">{{orderInfo.createTime | dateFormat("YYYY-MM-DD HH:mm:ss")}}</view> -->
					<button id="three" type="default" plain @tap="deleteOrder">取消申请</button>
				</view>
			</view>
			<view v-else>
				<view class="options">
					<!-- <view class="time">{{orderInfo.createTime | dateFormat("YYYY-MM-DD HH:mm:ss")}}</view> -->
					<button id="three" type="default" plain @tap="deleteOrder">删除订单</button>
				</view>
			</view>

<!-- 			<view class="options" v-if="orderInfo.status==0">
				<button type="default" plain>去付款</button>
				<button type="default" plain>取消订单</button>
			</view>
			<view class="options" v-else>
				<button type="default" plain>删除订单</button>
			</view> -->
		</view>
		
		<!-- 联系客服 -->
		<view class="contact" @tap="contact">
			<button class="contact-btn" type="default" plain>联系客服</button>
		</view>
		
		<!-- 占位 -->
		<empty-area></empty-area>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				// 订单信息
				orderInfo:{},
			};
		},
		onLoad(e) {
			this.getOrderInfo(e.order_id)
			// console.log(Number(e.order_id))
			
		},
		methods:{
			// 根据订单id获取订单信息
			async getOrderInfo(order_id){
				const that=this;
				var orderList = await uni.getStorageSync('orderList');
				// console.log(orderList[1].sn)
				for(let i = 0;i<orderList.length;i++){
					if(orderList[i].sn ===order_id){
						that.orderInfo=orderList[i]
					}
				}
				// const {data:res} = await uni.$http.get('/user/order/get/'+50);
				// that.orderInfo=res.data;
			},
				
			// 取消订单
			async cancleOrder(){
				const that=this;
				var orderInfo = that.orderInfo;
				await uni.$http.put('/user/order/cancel',orderInfo);
				uni.redirectTo({
					url:'/mySubPkg/orderManage/orderManage'
				})
			},
			
			// 删除订单
			async deleteOrder(e){
				const that=this;
				var orderInfo = that.orderInfo;
				await uni.$http.put('/user/order/delete',orderInfo);
				uni.redirectTo({
					url:'/mySubPkg/orderManage/orderManage'
				})
			},
			
			// 重新支付订单
			async payOrder(e){
				const that=this;
				var orderInfo = that.orderInfo;
				var sn = orderInfo.sn;
				console.log(sn)
			
				const  rePayOrder = await uni.getStorageSync("order"+sn);
				if(new Date().getTime()>rePayOrder.expirationTime){
					uni.showToast({
						title:'订单已过期,请刷新重试',
						icon:'none',
						mask:true
					})
					return
				}
				console.log(rePayOrder)
				// 否则，订单可继续重新付款
				await uni.requestPayment({
					appId:rePayOrder.data.appId,
					nonceStr:rePayOrder.data.nonceStr,
					package:rePayOrder.data.package,
					paySign:rePayOrder.data.paySign,
					signType:rePayOrder.data.signType,
					timeStamp:rePayOrder.data.timeStamp,
					success(payRes) {
						// 支付成功，设置订单支付状态为已支付，并跳转到订单界面。
						uni.$showMsg("支付成功")
						uni.removeStorageSync("order"+rePayOrder.data.sn)
						uni.redirectTo({
							url:"/mySubPkg/orderManage/orderManage"
						})
					},
					fail(e) {
						// 取消付款，直接跳转到待付款订单界面，设置订单保留时间为15分钟，到期自动取消订单并将订单状态设置为取消状态
						// 设置有效时间为30分钟
						uni.$showMsg("取消支付")
						uni.redirectTo({
							url:"/mySubPkg/orderManage/orderManage"
						})
					}
				})
				
			},
			// 申请退款
			async applyRefund(e){
				const that=this;
				var orderInfo = that.orderInfo;
				await uni.$http.post('/user/wxRefund/refund',orderInfo);
			},
			
			// 取消申请退款
			cancleApply(e){
				
			},
			
			// 联系客服
			contact(){
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
	background-color: #cccccc;
}
.order-status{
	display: flex;
	flex-direction: row;
	justify-content: center;
	align-items: center;
	width: 704;
	height: 100rpx;
	font-size: 40rpx;
	color: red;
	margin: 20rpx 23rpx 20rpx 23rpx;
	border-radius: 20rpx;
	background-color: #ffffff;
	.stat{
		#refund{
			color: #0EB117;
		}
	}
}

.product-info{
	display: flex;
	flex-direction: row;
	background-color: #ffffff;
	width: 704rpx;
	height: 250rpx;
	margin: 20rpx 23rpx 20rpx 23rpx;
	border-radius: 30rpx;
	align-items: center;
	image{
		width: 200rpx;
		height: 200rpx;
		margin: 0 20rpx 0 20rpx;
		border-radius: 30rpx;
	}
	.product-name{
		width: 350rpx;
		height: 80%;
		display: flex;
		flex-direction: column;
		margin-top: 50rpx;
		font-size: 28rpx;
	}
	.product-price{
		display: flex;
		flex-direction: column;
		align-items: flex-end;
		margin: 50rpx 20rpx 0 auto;
		height: 80%;
		.price{
			color: #FF0035;
			font-size: 40rpx;
		}
		.num{
			color: #999999;
			font-size: 30rpx;
			margin: 10rpx 10rpx 0 0;
		}
	}
}


.order-info{
	display: flex;
	flex-direction: column;
	background-color: #ffffff;
	width: 704rpx;
	margin: 20rpx 23rpx 20rpx 23rpx;
	border-radius: 30rpx;
	justify-content: center;	
	.title{
		margin: 20rpx 20rpx 20rpx 20rpx;
		border-bottom: #cccccc dashed 3rpx;
		font-size: 35rpx;
		font-weight: bold;
	}
	.order-item{
		margin: 10rpx 20rpx 10rpx 20rpx;
		display: flex;
		flex-direction: row;
		align-items: center;
		height: 50rpx;
		
		#price{
			color: #FF0035;
		}
		.item-text{
			margin-left: 10rpx;
			font-size: 30rpx;
			font-weight: bold;
		}
		.item-info{
			margin: 0 10rpx 0 auto;
			font-size: 27rpx;
			color: #999999;
			max-width: 450rpx;
			white-space: nowrap;
			overflow: hidden;
			text-overflow: ellipsis;
		}
	}
	.options{
			display: flex;
			flex-direction: row;
			justify-content: flex-end;
			align-items: center;
			margin: 10rpx 0 20rpx 0;

			// height: 50rpx;
			#one {
				margin: 0 30rpx 0 auto;
			}

			#two {
				margin: 0 20rpx 0 0rpx;
			}

			#three {
				margin: 0 20rpx 0 auto;
			}

			.time {
				margin: 0 auto 0 20rpx;
				font-size: 28rpx;
				color: #999999;
			}

			button {
				display: flex;
				flex-direction: row;
				border: #999999 solid 1px;
				font-size: 25rpx;
				font-weight: bold;
			}
	}
}

.camper-info{
	display: flex;
	flex-direction: column;
	background-color: #ffffff;
	width: 704rpx;
	margin: 20rpx 23rpx 20rpx 23rpx;
	border-radius: 30rpx;
	justify-content: center;
	.camper-title{
		margin: 20rpx 20rpx 0 20rpx;
		border-bottom: #cccccc dashed 3rpx;
		font-size: 35rpx;
		font-weight: bold;
	}
	
	.camper-info-item{
		display: flex;
		flex-direction: row;
		margin: 10rpx 20rpx 10rpx 20rpx;
		.name{
			margin: 0 auto 0 10rpx;
		}
		.gender{
			margin: 0 20rpx 0 auto;
			image{
				width: 45rpx;
				height: 45rpx;
			}
		}
	}
}
.contact{
	display: flex;
	flex-direction: column;
	background-color: #ffffff;
	width: 704rpx;
	margin: 20rpx 23rpx 20rpx 23rpx;
	border-radius: 30rpx;
	justify-content: center;
	.contact-btn{
		// border: #ffffff solid 1rpx;
		border: none;
		border-radius: 30rpx;
		background-color: #ffffff;
		width: 100%;
	}
}
</style>
