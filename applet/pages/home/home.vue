<template>
	<view>
		<!-- home页面分为三部分，轮播图，营期类型，商品列表 -->
		<!-- 轮播图 -->
		<swiper :indicator-dots="true" :autoplay="true" :interval="3000" :duration="1000" :circular="true">
			<!-- 渲染轮播图的item项 -->
			<swiper-item v-for="(item,i) in swiperList" :key="i">
				<navigator :url="'/homeSubPkg/products/products?product_id='+item.productId" class="swiper-item">
					<!-- 动态绑定图片的src属性 -->
					<image :src="item.url" mode="widthFix"></image>
				</navigator>
			</swiper-item>
		</swiper>

		<!-- 营期类型 -->
		<view class="campType">
			<!-- 营期类型的item项 -->
			<view class="category-item" v-for="(item,i) in categoryList" :key="i">
				<navigator :url="'/homeSubPkg/category/category?category_id=' + item.id">
					<!-- icon -->
					<view class="category-icon">
						<image :src="item.iconPath"></image>
					</view>
					<!-- name -->
					<view class="category-name">{{item.name}}</view>
				</navigator>
			</view>
		</view>

		<!-- 商品列表 -->
		<view>
			<view class="productList" v-for="(item,i) in productList" :key="i" v-if="item.isActivate==1">
				<!-- 分为上下两部分，上边的图片区域，下边的商品详情区域 -->
				<navigator :url="'/homeSubPkg/products/products?product_id='+item.id">
					<!-- 图片区域 -->
					<view class="product-image">
						<image class="cover" :src="item.bannerList[0].url" mode="widthFix"></image>
						<!-- 年龄、地点、时间 -->
						<view class="age-location-time">
							<!-- 年龄 -->
							<view class="age">
								<image src="../../static/home/child.svg"></image>
								<view>{{item.minAge}}-{{item.maxAge}}岁</view>
							</view>
							<!-- 地点 -->
							<view class="location">
								<image src="../../static/home/location.svg"></image>
								<view>{{item.location}}</view>
							</view>
							<!-- 时间 -->
							<view class="time" v-if="item.batchList!==[]">
								<image src="../../static/home/clock.svg"></image>
								<view>{{getDayAndNight(item.batchList[0].beginTime,item.batchList[0].endTime)}}</view>
							</view>
						</view>
					</view>
					<!-- 商品详情区域 包括名称、类别、营期、价格 -->
					<view class="product-detail">
						<!-- 名称、类别、价格 -->
						<view class="product-name">
							<!-- 名称、类别 -->
							<view class="name-category">
								<view class="name">{{item.name}}</view>
								<view class="category">{{item.categoryName}}</view>
							</view>
							<!-- 价格 -->
							<view class="price">￥{{item.price}}</view>
						</view>
						<!-- 可报营期 -->
						<view class="campList">
							<view class="canSign">可报{{item.batchList.length}}期：</view>
							<view class="product-camp" v-for="(campItem,j) in item.batchList" :key="j" v-if="j<=2">
								<view class="camp">{{campItem.beginTime | dateFormat('MM-DD(dd)')}}</view>
							</view>
						</view>
					</view>
				</navigator>
				<view class="divide"></view>
			</view>
		</view>
		
		<!-- 咨询按钮 -->
		<view class="contact-btn" @tap="contact">
			<uni-icons type="chat" size="25px" color="#ffffff"></uni-icons>
			<view class="contact-text">咨询</view>
		</view>
		
		<!-- 占位 -->
		<view class="empty-area"></view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				// 轮播图列表
				swiperList: [],
				// 商品种类的表
				categoryList: [],
				// 商品列表
				productList: [],
			};
		},
		onLoad() {
			this.getSwiperList();
			this.getCategoryList();
			this.getProductList();
		},
		onShow() {

		},
		onHide() {

		},
		onLaunch() {

		},
		onReady() {

		},
		methods: {
			// 获取轮播图列表
			async getSwiperList() {
				const that = this
				// 发起请求
				const {
					data: swiperRes
				} = await uni.$http.get('/banner/list')
				// 请求失败
				if (swiperRes.state !== 200) return uni.$showMsg()
				// 请求成功
				that.swiperList = swiperRes.data
			},

			// 获取商品种类的表
			async getCategoryList() {
				const that = this;
				// 发起请求
				const {
					data: res
				} = await uni.$http.get('/category/list')
				// 请求失败
				if (res.state !== 200) return uni.$showMsg()
				// 请求成功
				that.categoryList = res.data
			},

			// 获取商品列表
			async getProductList() {
				const that = this;
				// 发起请求
				const {
					data: res
				} = await uni.$http.get("/products/getProductsList")
				// console.log(res)
				// 请求失败
				if (res.state !== 200) return uni.$showMsg()
				// 请求成功
				that.productList = res.data

			},

			// 获取两日期之间有几天几晚
			getDayAndNight(beginTime,endTime) {
				var startTime=new Date(beginTime);
				var endTime=new Date(endTime);
				// 计算时间差
				var timeDiff = endTime.getTime()-startTime.getTime();
				// 计算天数
				var days = Math.floor(timeDiff/(1000*60*60*24));
				var night = days-1;
				if(days==0) days=1;
				if(night<0) night=0;
				return days+"天"+night+"晚";
			},
			
			// 咨询
			contact(){
				wx.openCustomerServiceChat({
					extInfo: {url: 'https://work.weixin.qq.com/kfid/kfcc070d68bc93d187e'},
					corpId: 'ww5b47e3729b081c1b',
				})
			}
		},
	}
</script>

<style lang="scss">
	// 轮播图区域ui
	swiper {
		height: 500rpx;

		swiper-item {
			width: 100%;
			height: 100%;

			image {
				width: 100%;
				height: 100%;
			}
		}
	}


	// 商品种类区域
	.campType {
		margin-top: 30rpx;
		margin-bottom: 30rpx;
		height: 180rpx;
		display: flex;
		flex-direction: row;
		justify-content: space-around;

		.category-item {
			.category-icon {
				background-color: #0054BC;
				width: 120rpx;
				height: 120rpx;
				border-radius: 50%;
				text-align: center;

				// margin-left: 2rpx;
				image {
					width: 80rpx;
					height: 80rpx;
					margin-top: 15rpx;
				}
			}

			.category-name {
				color: #0054BC;
				font-size: 30rpx;
				font-weight: 600;
				text-align: center;
				margin-top: 10rpx;
			}
		}
	}

	// 商品列表区域
	.productList {
		height: 718rpx;
		.product-image{
			width: 100%;
			height: 500rpx;
			position: relative;
			.cover{
				// position: absolute;
				height: 500rpx;
				width: 100%;
			}
			.age-location-time{
				position: absolute;
				bottom: 0;
				left: 0;
				display: flex;
				flex-direction: row;
				// justify-content: center;
				text-align: center;
				align-items: center;
				background-color: rgba(25, 25, 25, 0.4);
				color: #FFFFFF;
				width: 400rpx;
				height: 70rpx;
				margin-bottom: 0;
				margin-top: auto;
				// opacity: 0.3;
				font-size: 25rpx;
				image{
					width: 30rpx;
					height: 30rpx;
					justify-content: center;
					align-items: center;
				}
				.age{
					display: flex;
					flex-direction: row;
					margin-left: 20rpx;
					margin-right: 10rpx;
				}
				.location{
					display: flex;
					flex-direction: row;
					margin-right: 10rpx;
				}
				.time{
					display: flex;
					flex-direction: row;
				}
			}
		}

		.product-detail {
			display: flex;
			flex-direction: column;
			margin-top: 20rpx;
			.product-name {
				display: flex;
				flex-direction: row;
				padding-left: 20rpx;

				.name-category {
					display: flex;
					flex-direction: column;
					width: 550rpx;
					border-right: 1px solid;
					border-image: linear-gradient(#fff, #ddd, #ccc, #ddd, #fff) 1;
					padding-top: 20rpx;
					padding-bottom: 20rpx;

					.name {
						font-size: 32rpx;
						font-weight: 700;
					}

					.category {
						margin-top: 6rpx;
						color: #ACACAC;
						font-size: 28rpx;
						font-weight: 400;

					}
				}

				.price {
					display: flex;
					align-items: center;
					margin-left: auto;
					margin-right: 30rpx;
					color: #F6511D;
				}
			}

			.campList {
				display: flex;
				flex-direction: row;
				padding-left: 20rpx;
				padding-bottom: 20rpx;

				.canSign {
					color: #ACACAC;
					font-size: 26rpx;
					font-weight: 400;
					margin-right: 10rpx;
				}

				.product-camp {
					color: #FCAB10;
					display: flex;
					text-align: center;
					align-items: center;
					font-size: 25rpx;
					padding-left: 2px;
					padding-right: 2px;
					border-right: #FCAB10 1px solid;
				}

				.product-camp:last-child {
					border: none;
				}

			}
		}

		.divide {
			height: 50rpx;
			width: 100%;
			background-color: #EFEFF4;
		}
	}
	
	.empty-area{
		width: 100%;
		height: 150rpx;
		background-color: #EFEFF4;
	}
	
	
	// 咨询按钮
	.contact-btn{
		position: fixed;
		bottom: 100rpx;
		right: 30rpx;
		background-color: #002A50;
		width: 100rpx;
		height: 100rpx;
		display: flex;
		flex-direction: column;
		justify-content: center;
		align-items: center;
		border-radius: 50%;
		.contact-text{
			font-size: 20rpx;
			color: #ffffff;
		}
	}
</style>
