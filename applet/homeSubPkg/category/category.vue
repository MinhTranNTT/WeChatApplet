<template>
	<view>
<!-- 商品列表 -->
		<view>
			<view class="productList" v-for="(item,i) in productsList" :key="i">
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
							<view class="product-camp" v-for="(campItem,j) in item.batchList" :key="j" v-if="j<3">
								<view class="camp">{{campItem.beginTime | dateFormat('MM-DD(dd)')}}</view>
							</view>
						</view>
					</view>
				</navigator>
				<view class="divide"></view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				// 商品列表
				productsList: [],
			};
		},
		onLoad(e) {
			// console.log(e)
			this.getProductsListByCategoryId(e.category_id)
		},
		methods: {
			//根据商品类别获取所有的商品列表
			async getProductsListByCategoryId(category_id) {
				const that = this;
				const {
					data: res
				} = await uni.$http.get('/products/getProductsListByType/' + category_id)
				// console.log(res)
				if (res.state !== 200) return uni.$showMsg();
				that.productsList = res.data
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
			}
		},
	}
</script>

<style lang="scss">
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
</style>
