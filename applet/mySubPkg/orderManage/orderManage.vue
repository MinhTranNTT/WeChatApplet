<template>
	<view>
		<!-- 待付款，已付款，已完成，已取消 -->
		<!-- 顶部的四个状态栏 -->
		<view class="order-status">
			<view :class="i===selecetdOrderStatus? 'activate':'order-item'" v-for="(item,i) in orderStatus" :key="i"
				@tap="orderList(i)">
				<view>{{item.text}}</view>
			</view>
		</view>

		<!-- 具体的订单 -->
		<!-- 最上边的订单编号和订单状态，下边的营期信息，以及选项 -->
		<view class="order-info">
			<!-- 最上边的订单编号和订单状态 -->
			<view class="order-info-item" v-for="(item,i) in ordersList" :key="i" v-if="item.isDelete==0">
				<navigator :url="'/mySubPkg/orderInfo/orderInfo?order_id='+item.sn">
					<view class="order">
						<view class="sn">
							<view class="title">订单号：</view>
							<view class="value">{{item.sn}}</view>
						</view>
						<view v-if="item.refundStatus===0" class="stat">
							<view class="status" v-if="item.status===0">待支付</view>
							<view class="status" v-else-if="item.status===1">已支付</view>
							<view class="status" v-else-if="item.status===2">已完成</view>
							<view class="status" v-else>已取消</view>
						</view>
						<view v-else-if="item.refundStatus===1" class="stat">
							<view class="status" id="refund">已申请退款</view>
						</view>
						<view v-else-if="item.refundStatus===2" class="stat">
							<view class="status" id="refund">退款成功</view>
						</view>
					</view>
					<!-- 营期信息 -->
					<view class="camp-info">
						<image :src="item.productImage"></image>
						<view class="product-name">
							<view>{{item.productName}}</view>
						</view>
						<view class="product-price">
							<view class="price">￥{{Number((item.originalPrice/100).toFixed(2))}}</view>
							<view class="num">x{{item.productCount}}</view>
						</view>
					</view>

					<!-- 总支付价格 -->
					<view class="total-price">
						<view class="text">总付款金额:</view>
						<view class="t-price">￥{{Number((item.payPrice/100).toFixed(2))}}</view>
					</view>
				</navigator>
				<!-- 选项 -->
				<view v-if="item.refundStatus===0">
					<view class="options" v-if="item.status==0">
						<view class="time">{{item.createTime | dateFormat("YYYY-MM-DD HH:mm:ss")}}</view>
						<button id="one" type="default" plain @tap="cancleOrder(i)">取消订单</button>
						<button id="two" type="default" plain @tap="payOrder(i)">去付款</button>
					</view>
					<view class="options" v-else-if="item.status == 1">
						<view class="time">{{item.createTime | dateFormat("YYYY-MM-DD HH:mm:ss")}}</view>
						<button id="one" type="default" plain @tap="deleteOrder(i)">删除订单</button>
						<button id="two" type="default" plain @tap="applyRefund(i)">申请退款</button>
					</view>
					<view class="options" v-else>
						<view class="time">{{item.createTime | dateFormat("YYYY-MM-DD HH:mm:ss")}}</view>
						<button id="three" type="default" plain @tap="deleteOrder(i)">删除订单</button>
						<!-- <button id="two" type="default" plain> 退款 </button> -->
					</view>
					<view class="tips" v-if="item.status===0">
						<view>订单会保留30分钟，请及时支付</view>
					</view>
				</view>
				<view v-else-if="item.refundStatus===1">
					<view class="options">
						<view class="time">{{item.createTime | dateFormat("YYYY-MM-DD HH:mm:ss")}}</view>
						<button id="three" type="default" plain @tap="cancleApply()">取消申请</button>
					</view>
				</view>
				<view v-else>
					<view class="options">
						<view class="time">{{item.createTime | dateFormat("YYYY-MM-DD HH:mm:ss")}}</view>
						<button id="three" type="default" plain @tap="deleteOrder(i)">删除订单</button>
					</view>
				</view>

				<!-- 倒计时 -->
				<!-- <uni-countdown :showDay="false" :minute="getMinusAndSecond((unPaidOrderCache.order+'item.sn').expirationTime)[0]"></uni-countdown> -->
			</view>

		</view>

		<empty-area></empty-area>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				// 顶部的订单状态列表
				orderStatus: [{
						id: 4,
						text: "全部"
					},
					{
						id: 0,
						text: "待付款"
					},
					{
						id: 1,
						text: "已付款"
					},
					{
						id: 2,
						text: "已完成"
					},
					{
						id: 3,
						text: "已取消"
					}
				],
				// 具体的订单列表
				ordersList: [],
				// 选中的订单状态
				selecetdOrderStatus: 0,
			};
		},
		onLoad() {
			this.getAllOrder();
			this.getAllUnPaidOrder();

		},
		onReady() {

		},
		methods: {
			// 获取所有以order开头的缓存
			async getAllUnPaidOrder() {
				var keys = await uni.getStorageInfoSync().keys;
				var orderKeys = keys.filter(key => key.startsWith("order"))
				var orderCache = {}
				for (let key of orderKeys) {
					var isExpiredOrder = await uni.getStorageSync(key);
					if (new Date().getTime() > isExpiredOrder.expirationTime) {
						await uni.removeStorageSync(key)
					}
					orderCache[key] = isExpiredOrder;
				}
			},



			// 获取所有订单	
			async getAllOrder() {
				const that = this;
				const {
					data: totalRes
				} = await uni.$http.get('/user/order/getList')
				that.ordersList = totalRes.data;
				// 将所有订单列表放入缓存中
				await uni.setStorageSync("orderList", totalRes.data);
			},

			// 获取对应的订单的列表
			async orderList(e) {
				const that = this;
				// console.log(e)
				if (e === 0) {
					that.ordersList = uni.getStorageSync('orderList');

				} else {
					that.ordersList = [];
					const orderList = uni.getStorageSync('orderList');
					for (let i = 0; i < orderList.length; i++) {
						if (orderList[i].status === that.orderStatus[e].id) {
							that.ordersList.push(orderList[i])
						}
					}
				}
				that.selecetdOrderStatus = e;
			},

			// 获取分秒
			getMinusAndSecond(timeStamp) {
				var date = new Date().getTime();
				if (timeStamp > date) {
					const diff = timeStamp - date;
					const munutes = Math.floor(diff / (60 * 1000))
					const seconds = Math.floor((diff % (60 * 1000)) / 1000)
					return [munutes, seconds]
				} else {
					return [0, 0]
				}
			},

			// 取消订单
			async cancleOrder(e) {
				const that = this;
				var orderInfo = that.ordersList[e];
				await uni.$http.put('/user/order/cancel', orderInfo);
				uni.redirectTo({
					url: '/mySubPkg/orderManage/orderManage'
				})
			},

			// 删除订单
			async deleteOrder(e) {
				const that = this;
				var orderInfo = that.ordersList[e];
				await uni.$http.put('/user/order/delete', orderInfo);
				uni.redirectTo({
					url: '/mySubPkg/orderManage/orderManage'
				})
			},

			// 重新支付订单
			async payOrder(e) {
				const that = this;
				var orderInfo = that.ordersList[e];
				var sn = orderInfo.sn;
				console.log(sn)

				const rePayOrder = await uni.getStorageSync("order" + sn);
				if (new Date().getTime() > rePayOrder.expirationTime) {
					uni.showToast({
						title: '订单已过期,请刷新重试',
						icon: 'none',
						mask: true
					})
					return
				}
				console.log(rePayOrder)
				// 否则，订单可继续重新付款
				await uni.requestPayment({
					appId: rePayOrder.data.appId,
					nonceStr: rePayOrder.data.nonceStr,
					package: rePayOrder.data.package,
					paySign: rePayOrder.data.paySign,
					signType: rePayOrder.data.signType,
					timeStamp: rePayOrder.data.timeStamp,
					success(payRes) {
						// 支付成功，设置订单支付状态为已支付，并跳转到订单界面。
						uni.$showMsg("支付成功")
						uni.removeStorageSync("order" + rePayOrder.data.sn)
						uni.redirectTo({
							url: "/mySubPkg/orderManage/orderManage"
						})
					},
					fail(e) {
						// 取消付款，直接跳转到待付款订单界面，设置订单保留时间为15分钟，到期自动取消订单并将订单状态设置为取消状态
						// 设置有效时间为30分钟
						uni.$showMsg("取消支付")
						uni.redirectTo({
							url: "/mySubPkg/orderManage/orderManage"
						})
					}
				})

			},
			// 申请退款
			async applyRefund(e) {
				const that = this;
				var orderInfo = that.ordersList[e];
				await uni.$http.post('/user/wxRefund/refund', orderInfo);
				uni.$showMsg("已申请退款")
			},

			// 取消申请退款
			cancleApply(e) {

			}
		}
	}
</script>

<style lang="scss">
	page {
		background-color: #cccccc;
	}

	.order-status {
		display: flex;
		flex-direction: row;
		justify-content: center;
		align-items: center;
		width: 100%;
		background-color: #ffffff;

		.order-item {
			display: flex;
			flex-direction: column;
			justify-content: center;
			align-items: center;
			width: 20%;
			height: 80rpx;
			color: #7F7F7F;
		}

		.activate {
			display: flex;
			flex-direction: column;
			justify-content: center;
			align-items: center;
			width: 20%;
			height: 80rpx;
			color: #0EB117;
			border-bottom: #0EB117 solid 4rpx;
		}
	}

	.order-info {
		display: flex;
		flex-direction: column;
		justify-content: center;
		width: 704rpx;
		margin: 30rpx 23rpx 0 23rpx;

		.order-info-item {
			display: flex;
			flex-direction: column;
			justify-content: center;
			background-color: #ffffff;
			border-radius: 30rpx;
			margin: 10rpx 0 10rpx 0;

			.order {
				display: flex;
				flex-direction: row;
				margin: 10rpx 20rpx 10rpx 20rpx;

				.sn {
					// margin: ;
					display: flex;
					flex-direction: row;
					font-size: 25rpx;
					color: #999999;
				}

				// #refund{
				// 	color: #0EB117;
				// }
				.stat {
					margin: 0 0 0 auto;

					.status {
						color: red;
						font-weight: bold;
					}

					#refund {
						color: #0EB117;
					}
				}

			}

			.camp-info {
				display: flex;
				flex-direction: row;
				margin: 10rpx 20rpx 10rpx 20rpx;
				align-items: center;

				image {
					width: 170rpx;
					height: 170rpx;
					// margin: 0 10rpx 0 20rpx;
					margin-right: 10rpx;
					border-radius: 20rpx;

				}

				.product-name {
					width: 370rpx;
					font-size: 28rpx;
				}

				.product-price {
					margin: 0 0 0 auto;
					display: flex;
					flex-direction: column;
					align-items: flex-end;

					.price {
						font-size: 35rpx;
						color: #FF0035;
					}

					.num {
						font-size: 28rpx;
						color: #999999;
						margin: 10rpx 10rpx 0 0;
					}
				}
			}

			.total-price {
				display: flex;
				flex-direction: row;
				align-items: center;
				margin: 5rpx 0 10rpx 20rpx;

				.text {
					font-size: 28rpx;
				}

				.t-price {
					margin: 0 20rpx 0 auto;
					font-size: 28rpx;
					color: #FF0035;
				}
			}
		}

		.options {
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

		.tips {
			display: flex;
			flex-direction: row;
			justify-content: center;
			font-size: 25rpx;
			color: #FF0035;
		}
	}
</style>
