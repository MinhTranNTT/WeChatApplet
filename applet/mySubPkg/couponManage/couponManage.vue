<template>
	<view>
		<!-- 优惠券的状态 -->
		<view class="order-status">
			<view :class="i===selecetdCouponStatus? 'activate':'order-item'" v-for="(item,i) in couponStatus" :key="i"
				@tap="couponList(i)">
				<view>{{item.text}}</view>
			</view>
		</view>

		<!-- 优惠券的列表 -->
		<view class="coupon-List">
			<view class="coupon-item" v-for="(item,i) in couponsList" :key="i">
				<view class="wrap">
					<!-- 优惠券金额 左边部分 -->
					<view class="coupon-price">
						<!-- 类型为满减时，显示优惠券面额 -->
						<view class="price" v-if="item.couponType===0">
							<view class="value">￥{{item.money}}</view>
							<view class="limit" v-if="item.atLeast === 0">无金额限制</view>
							<view class="limit" v-else>满{{item.atLeast}}可用</view>
						</view>
						<!-- 类型为折扣时，显示优惠券折扣 -->
						<view class="price" v-else-if="item.couponType===1">
							<view class="value">{{item.discount}}折</view>
							<view class="limit" v-if="item.atLeast === 0">无金额限制</view>
							<view class="limit" v-else>满{{item.atLeast}}可用</view>
						</view>
					</view>

					<!-- 优惠券细节 右边部分 -->
					<view class="coupon-info">
						<!-- 优惠券的名称 -->
						<view class="coupon-name">
							<view class="value">{{item.couponName}}</view>
						</view>
						<!-- 到期时间 -->
						<view class="time">
							<view class="value">{{item.endTime | dateFormat("YYYY/MM/DD")}}到期</view>
						</view>
						<!-- 使用规则 -->
						<view class="type" @tap="showCouponDesc(i)">
							<view class="value" v-if="item.goodsType===0">全商品可用</view>
							<view class="value" v-if="item.goodsType===1">指定商品可用</view>
							<view class="value" v-if="item.goodsType===2">指定商品不可用</view>
							<uni-icons type="forward" size="28rpx" color="#999999"></uni-icons>
						</view>
					</view>
					<!-- 根据优惠券状态显示具体的操作 -->
					<view class="functions">
						<view class="un-used" v-if="item.status===0" @tap="useCoupoon">
							<view>去使用</view>
						</view>
						<image src="../../static/my/used.svg" v-else-if="item.status===1"></image>
						<image src="../../static/my/expired.svg" v-else-if="item.status===2"></image>
					</view>
				</view>
				<view class="coupon-detail" v-if="wetherShowDesc[i].flag">
					<view class="info">{{item.couponDesc}}</view>
				</view>
			</view>
		</view>

	</view>
</template>

<script>
	export default {
		data() {
			return {
				// 优惠券的状态
				couponStatus: [{
						id: 0,
						text: "未使用"
					},
					{
						id: 1,
						text: "已使用"
					},
					{
						id: 2,
						text: "已过期"
					},
					{
						id: 3,
						text: "兑换券码"
					}
				],
				// 具体的优惠券的列表
				couponsList: [],
				// 已选择的优惠券的状态
				selecetdCouponStatus: 0,
				// 是否展示优惠券的描述列表
				wetherShowDesc: [],
			};
		},
		onLoad() {
			this.onLoadOrder();
		},
		methods: {
			// onLoad的执行顺序
			async onLoadOrder() {
				const that = this;
				that.getUnUsedCouponList();
			},

			// 从缓存中获取未使用的优惠券列表，并赋值给couponsList
			async getUnUsedCouponList() {
				const that = this;
				var couponList = await uni.getStorageSync('couponList');
				for (let i = 0; i < couponList.length; i++) {
					if (couponList[i].status === 0) {
						that.couponsList.push(couponList[i])
					}
				}
				that.initWetherShowDesc(that.couponsList)
			},

			// 根据优惠券列表初始化一个是否展示的列表
			async initWetherShowDesc(orderList) {
				const that = this;
				that.wetherShowDesc = [];
				for (let i = 0; i < orderList.length; i++) {
					var item = {
						"id": i,
						"flag": false
					};
					that.wetherShowDesc.push(item)
				}
			},

			// 获取对应状态的优惠券的列表
			async couponList(e){
				const that = this;
				that.couponsList=[];
				if(e!==3){
					//从缓存中获取对应状态的优惠券
					var couponList = await uni.getStorageSync('couponList');
					for (let i = 0; i < couponList.length; i++) {
						if (couponList[i].status === e) {
							that.couponsList.push(couponList[i])
						}
					}
					that.initWetherShowDesc(that.couponsList)
				}else{
					uni.navigateTo({
						url:'/mySubPkg/getCoupon/getCoupon'
					})
				}
				that.selecetdCouponStatus = e;
			},

			// 是否显示优惠券详情
			showCouponDesc(i) {
				const that = this;
				that.wetherShowDesc[i].flag = !that.wetherShowDesc[i].flag;
			},
			
			// 跳转到首页
			useCoupoon(){
				uni.switchTab({
					url:'/pages/home/home'
				})
			}
		}
	}
</script>

<style lang="scss">
	page {
		background-color: #EFEFF4;
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
			width: 25%;
			height: 80rpx;
			color: #7F7F7F;
		}

		.activate {
			display: flex;
			flex-direction: column;
			justify-content: center;
			align-items: center;
			width: 25%;
			height: 80rpx;
			color: #0EB117;
			border-bottom: #0EB117 solid 4rpx;
		}
	}
	
	.coupon-List{
		width: 100%;
		display: flex;
		flex-direction: column;
		align-items: center;
		margin-top: 30rpx;
		.coupon-item{
			display: flex;
			flex-direction: column;
			// align-items: center;
			width: 90%;
			margin-bottom: 30rpx;
			.wrap{
				display: flex;
				flex-direction: row;
				
				.coupon-price{
					width: 230rpx;
					height: 220rpx;
					// background-color: #D11E22;
					// background: linear-gradient(to right, #D11E22, #E32324);
					background: radial-gradient(circle at top right, transparent 30rpx, #D11E22 0) top right, radial-gradient(circle at bottom right, transparent 30rpx, #D11E22 0) bottom right;
					background-size: 100% 60%;
					background-repeat: no-repeat;
					color: #000000;
					width: 210rpx;
					border-radius: 10px 0 0 10px;
					.price{
						width: 100%;
						height: 100%;
						display: flex;
						flex-direction: column;
						justify-content: center;
						align-items: center;
						.value{
							font-size: 40rpx;
							font-weight: bold;
							color: #ffffff;
							margin-bottom: 20rpx;
						}
						.limit{
							color: #ffffff;
							font-size: 27rpx;
						}
					}
				}
				
				.coupon-info{
					height: 220rpx;
					width: 270rpx;
					background-color: #ffffff;
					background: radial-gradient(circle at top left, transparent 30rpx, #ffffff 0) top left, radial-gradient(circle at bottom left, transparent 30rpx, #ffffff 0) bottom left;
					background-size: 100% 60%;
					background-repeat: no-repeat;
					color: #000000;
					display: flex;
					flex-direction: column;
					justify-content: center;
					.coupon-name{
						// width: 70rpx;
						height: 40rpx;
						background: linear-gradient(to right, #C81B21, #E72425);;
						border-radius: 15rpx;
						margin: 10rpx auto 20rpx 40rpx;
						font-size: 25rpx;
						display: flex;
						flex-direction: row;
						justify-content: center;
						align-items: center;
						color: #ffffff;
						.value{
							margin: 4rpx 4rpx 4rpx 4rpx;
						}
					}
					.time{
						margin: 10rpx aut0 20rpx 20rpx;
						font-size: 25rpx;
						.value{
							margin-left: 40rpx;
						}
					}
					
					.type{
						// margin: 0 aut0 0 20rpx;
						display: flex;
						flex-direction: row;
						font-size: 25rpx;
						color: #999999;
						.value{
							margin: 20rpx 0 0 40rpx;
						}
						uni-icons{
							margin: 22rpx auto 0 0;
						}
					}
				}
				
				.functions{
					width: 195rpx;
					height: 220rpx;
					display: flex;
					border-radius: 0 10px 10px 0;
					flex-direction: column;
					justify-content: center;
					background-color: #ffffff;
					.un-used{
						margin: 0 30rpx 0 auto;
						color: #ffffff;
						width: 120rpx;
						height: 50rpx;
						background-color: #FA3534;
						border-radius: 20rpx;
						display: flex;
						flex-direction: row;
						justify-content: center;
						align-items: center;
					}
					
					image{
						width: 100rpx;
						height: 100rpx;
						margin: 0 30rpx 0 auto;
					}
				}
			}
			
			.coupon-detail{
				display: flex;
				flex-direction: row;
				width: 100%;
				background-color: #ffffff;
				border-radius: 10px;
				.info{
					margin: 20rpx 20rpx 20rpx 20rpx;
					color: #999999;
					font-size: 27rpx;
				}
			}
		}
	}
	
</style>
