<template>
	<view v-if="product.batchList.length">
		<view class="wrap">
		<!-- 分为五个部分 -->
		<!-- 轮播图区域 -->
		<view class="banner">
			<!-- 轮播图 -->
			<swiper :indicator-dots="true" :autoplay="true" :interval="3000" :duration="1000" :circular="true">
				<swiper-item v-for="(item,i) in product.bannerList" :key="i">
					<image class="banner-image" :src="item.url" mode="widthFix" @tap="previewImageBan(i)"></image>
				</swiper-item>
			</swiper>
			<!-- 轮播图左下角的信息 -->
			<view class="age-location-time">
				<!-- 年龄 -->
				<view class="age">
					<image src="../../static/home/child.svg"></image>
					<view>{{product.minAge}}-{{product.maxAge}}岁</view>
				</view>
				<!-- 地点 -->
				<view class="location">
					<image src="../../static/home/location.svg"></image>
					<view>{{product.location}}</view>
				</view>
				<!-- 时间 -->
				<view class="time">
					<image src="../../static/home/clock.svg"></image>
					<view v-if="product.batchList.length">{{getDayAndNight(product.batchList[0].beginTime,product.batchList[0].endTime)}}</view>
				</view>
			</view>
		</view>
		<!-- 商品详情区域 -->
		<view class="product-info">
			<!-- 价格 -->
			<view class="product-price">
				<view class="signal">￥</view>
				<view class="price">{{product.price}}</view>
			</view>
			<!-- 名称 -->
			<view class="product-name">{{product.name}}</view>
			<!-- 类型 -->
			<view class="product-type">{{product.categoryName}}</view>
		</view>
		<view class="divide-line"></view>
		<!-- 个人主页以及优惠区域 -->
		<view class="user-info">
			<view class="discount">
				<view class="discount-text">优惠</view>
				<view class="discount-dis">最高优惠200元</view>
				<uni-icons type="more-filled"></uni-icons>
			</view>
			<view class="user" @tap="navigatroToMy">
				<view>个人中心</view>
				<uni-icons type="forward"></uni-icons>
			</view>
		</view>
		<!-- 分隔区域 -->
		<view class="divide-area"></view>
		<!-- 营期选择区域 -->
		<navigator class="choose-camp" :url="'/homeSubPkg/selectCamp/selectCamp?product_id='+product.id">
			<view class="name-camp">
				<view class="name">
					<image src="../../static/home/cal.svg"></image>
					<view class="name-text">选择营期</view>
				</view>
				<view class="camp-list">
					<view v-for="(item,i) in product.batchList" :key="i" v-if="i<3">
						<view class="camp-date">{{item.beginTime | dateFormat("MM月DD日,")}}</view>
					</view>
					<view v-if="product.batchList.length>1">多日期出营</view>
					<view v-else>出营</view>
				</view>
			</view>
			<uni-icons type="forward"></uni-icons>
		</navigator>
		<!-- 分隔区域 -->
		<view class="divide-area"></view>
		<!-- 产品详情图区域 -->
		<view class="product-detail">
			<view class="detail-text">商品详情</view>
			<view class="divide-line"></view>
			<view class="detail-image" v-for="(item,i) in product.detailList" :key="i">
				<image :src="item.url" mode="widthFix" @tap="previewImageDetail(i)"></image>
			</view>
		</view>
		
		<!-- 最下方的下单 咨询、分享、立即报名-->
		<view class="register-now">
			<!-- 咨询 -->
			<button class="consult" plain id="one" @tap="consult">
				<image src="../../static/home/weixin.svg"></image>
				<view class="text">咨询</view>
			</button>
			<!-- 分享 -->
			<button class="consult" @tap="popupBtn" plain>
				<image src="../../static/home/share.svg"></image>
				<view class="text">分享</view>
			</button>
			
			<!-- 底部弹出框 -->
			<uni-popup ref="popup" type="bottom">
				<button open-type="share" class="option">
					<view class="popcontent">发送给朋友</view>
				</button>
				<view class="option" @tap="poster">
					<view class="popcontent">生成海报</view>
				</view>
				<view class="option" @tap="calcle">
					<view class="popcontent">取消</view>
				</view>
			</uni-popup>
			
			<!-- 支付订单 -->
			<navigator class="register" :url="'/homeSubPkg/selectCamp/selectCamp?product_id='+product.id">
				<view class="text-1">￥ {{product.price}} 立即报名</view>
			</navigator>
		</view>
		
		<!-- 海报 -->
		<uni-popup ref="posterPopup" type="center" class="posterPopup" :isMaskClick="false">
			<view class="quit">
				<image src="../../static/home/cross.svg" @tap="closePosterPopUp"></image>
			</view>
			<image class="poster" :src="product.productPoster" @longpress="savePosterToAlbum"></image>
			<view class="poster-tip">
				<view class="text">长按图片可保存到手机</view>
			</view>
		</uni-popup>
		</view>
		
		
		
<!-- 		<wm-poster :imgSrc="product.bannerList[0].url"
					QrSrc="https://www.zl21.club/file/QrCode1.jpg" 
					:Title="product.name" :PriceTxt="product.price"
					Referrer="这个营期很不错呦!!" @success="posterImage">
		</wm-poster>
		
		<button type="default" @tap="saveImage">saveImage</button> -->
		
	</view>
</template>

<script>
	// import wmPoster from "@/components/wm-poster/wm-poster.vue"
	export default {
		// components:{wmPoster},
		data() {
			return {
				// 商品的信息
				product: {
					batchList:[],
				},
				// // 商品的价格
				// price:0,
				
				// 海报的路径
				posterImageUrl:'',
			};
		},
		onLoad(e) {
			this.getProductsDetail(e.product_id)
			// this.getProductsDetail(1)
		},
		methods: {
			// 保存海报图
			saveImage(){
				const that=this;
				uni.saveImageToPhotosAlbum({
					filePath:that.posterImageUrl,
				})
			},
			
			// banner图片预览功能
			previewImageBan(index){
				const that=this;
				let photoList = that.product.bannerList.map(item => {
								return item.url;
							});
				uni.previewImage({
					urls:photoList,
					current:index
				})
			},
			
			// 详情图预览功能
			previewImageDetail(index){
				const that=this;
				let photoList = that.product.detailList.map(item => {
								return item.url;
							});
				uni.previewImage({
					urls:photoList,
					current:index
				})
			},
			
			// 获取商品信息
			async getProductsDetail(product_id) {
				const that = this;
				const {
					data: res
				} = await uni.$http.get('/products/getProductById/' + product_id);
				// console.log(res)
				if (res.state !== 200) return uni.$showMsg();
				that.product = res.data;
				// that.price = res.data.price;
			},

			// 获取两日期之间有几天几晚
			getDayAndNight(beginTime, endTime) {
				var startTime = new Date(beginTime);
				var endTime = new Date(endTime);
				// 计算时间差
				var timeDiff = endTime.getTime() - startTime.getTime();
				// 计算天数
				var days = Math.floor(timeDiff / (1000 * 60 * 60 * 24));
				var night = days - 1;
				if (days == 0) days = 1;
				if (night < 0) night = 0;
				return days + "天" + night + "晚";
			},
			
			// 跳转到我的页面
			navigatroToMy(){
				uni.switchTab({
					url:'/pages/my/my'
				})
			},
			
			// 咨询客服
			consult(){
				wx.openCustomerServiceChat({
					extInfo: {url: 'https://work.weixin.qq.com/kfid/kfcc070d68bc93d187e'},
					corpId: 'ww5b47e3729b081c1b',
				})
			},
			
			// 转发弹出层
			popupBtn(){
				const that=this;
				that.$refs.popup.open()
			},
			
			// 生成海报
			poster(){
				const that=this;
				that.$refs.posterPopup.open()
			},
			
			// 关闭海报弹窗
			closePosterPopUp(){
				const that=this;
				that.$refs.posterPopup.close();
				that.$refs.popup.close()
			},
			
			// 将海报保存到相册
			savePosterToAlbum(){
				const that=this;
				uni.downloadFile({
					url:that.product.productPoster,
					success(res) {
						// console.log(res)
						uni.saveImageToPhotosAlbum({
							filePath:res.tempFilePath,
							success() {
								uni.$showMsg("保存成功")
							},
							fail() {
								uni.$showMsg("保存失败")
							}
						})
					}
				})

			},
			
			// 取消分享
			calcle(){
				const that=this;
				that.$refs.popup.close()
			},
			
			posterImage(e){
				// console.log(e)
				this.posterImageUrl=e.tempFilePath;
			}
		},
		onShareAppMessage() {
			return{
				title:this.product.name,
				// path:"/homeSubPkg/products/products"
			}
		}
	}
</script>

<style lang="scss">
	page{
		background-color: #F5F5F5;
	}
	// .wrap{
	// 	z-index: 999;
	// 	background-color: #F5F5F5;
	// 	position: relative;
	// 轮播图区域
	.banner {
		height: 500rpx;
		position: relative;

		swiper {
			height: 500rpx;

			.banner-image {
				height: 100%;
				width: 100%;
			}
		}

		.age-location-time {
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

			image {
				width: 30rpx;
				height: 30rpx;
				justify-content: center;
				align-items: center;
			}

			.age {
				display: flex;
				flex-direction: row;
				margin-left: 20rpx;
				margin-right: 10rpx;
			}

			.location {
				display: flex;
				flex-direction: row;
				margin-right: 10rpx;
			}

			.time {
				display: flex;
				flex-direction: row;
			}

		}
	}
	
	// 商品详情区域
	.product-info{
		height: 200rpx;
		display: flex;
		flex-direction: column;
		background-color: #F5F5F5;
		margin-left: 20rpx;
		.product-price{
			display: flex;
			flex-direction: row;
			color: #F6511D;
			margin-top: 20rpx;
			margin-bottom: 10rpx;
			.signal{
				display: flex;
				font-size: 30rpx;
				align-items: center;
				text-align: center;
			}
			.price{
				font-size: 45rpx;
			}
		}
		
		.product-name{
			font-size: 37rpx;
			font-weight: bold;
			margin-bottom: 10rpx;
		}
		
		.product-type{
			margin-top: 5rpx;
			color: #ACACAC;
			font-size: 28rpx;
			font-weight: 400;
		}
	}
	
	// 分割线
	.divide-line{
		width: 100%;
		height: 1px;
		background-color: #ddd;
	}
	
	// 个人首页以及优惠券
	.user-info{
		margin-left: 20rpx;
		margin-bottom: 20rpx;
		background-color: #F5F5F5;
		uni-icons{
			margin-right: 40rpx;
			margin-left: auto;
		}
		.discount{
			display: flex;
			flex-direction: row;
			margin-top: 10rpx;
			margin-bottom: 10rpx;
			.discount-text{
				font-size: 35rpx;
				font-weight: bold;
				margin-right: 10rpx;
			}
			.discount-dis{
				display: flex;
				align-items: center;
				justify-content: center;
				text-align: center;
				font-size: 25rpx;
			}
		}
		
		.user{
			display: flex;
			flex-direction: row;
			border-left: #F6511D 1px solid;
			padding-left: 30rpx;
			margin-top: 30rpx;
		}
	}
	
	// 分隔区域
	.divide-area{
		height: 30rpx;
		width: 100%;
		background-color: #EFEFF4;
	}
	
	// 营期选择区域
	.choose-camp{
		display: flex;
		flex-direction: row;
		margin-left: 20rpx;
		margin-top: 20rpx;
		margin-bottom: 20rpx;
		background-color: #F5F5F5;
		.name-camp{
			display: flex;
			flex-direction: column;
			.name{
				display: flex;
				flex-direction: row;
				text-align: center;
				// justify-content: center;
				align-items: center;
				image{
					display: flex;
					width: 30rpx;
					height: 30rpx;
				}
				.name-text{
					font-size: 35rpx;
					font-weight: bold;
				}
			}
			.camp-list{
				display: flex;
				flex-direction: row;
				font-size: 25rpx;
				color: #ACACAC;
				font-weight: 400;
			}
		}
		uni-icons{
			display: flex;
			align-items: center;
			margin-left: auto;
			margin-right: 40rpx;
		}
	}
	// 最后的图片列表
	.product-detail{
		height: auto;
		width: 100%;
		.detail-text{
			margin-left: 20rpx;
			font-size: 40rpx;
			border-left: #F6511D 1px solid ;
			padding-left: 30rpx;
		}
		.detail-image{
			display: flex;
			flex-direction: column;
			image{
				width: 96%;
				height: auto;
				margin-left: auto;
				margin-right: 2%;
			}
		}
	}
	
	.register-now{
		border-top: #CCC solid 1rpx;
		position: fixed;
		bottom: 0;
		display: flex;
		flex-direction: row;
		align-items: center;
		background-color: #ffffff;
		width: 100%;
		height: 100rpx;
		font-size: 25rpx;
		
		// #one{
		// 	margin-left: 50rpx;
		// 	margin-right: 40rpx;
		// }

		.consult{
			height: 90rpx;
			background-color: #ffffff;
			display: flex;
			flex-direction: column;
			justify-content: center;
			align-items: center;
			border: none;
			margin-top: 10rpx;
			// margin: 0 20rpx 0 20rpx;
			image{
				width: 40rpx;
				height: 40rpx;
			}
			.text{
				font-size: 23rpx;
				color: #999999;
			}
		}
		
		.register{
			width: 450rpx;
			height: 70rpx;
			display: flex;
			flex-direction: row;
			justify-content: center;
			align-items: center;
			margin-left: auto;
			margin-right: 30rpx;
			border-radius: 42rpx;
			background: linear-gradient(to right, #FFCA1E, #F01625);
			.text-1{
				font-size: 33rpx;
				color: #ffffff;
			}
		}
		
		// 弹出层
		uni-popup{
			display: flex;
			flex-direction: column;
			justify-content: center;
			align-items: center;
			background-color: #990c55;
			.option{
				width: 80%;
				height: 40px;
				display: flex;
				flex-direction: column;
				justify-content: center;
				align-items: center;
				background-color: #ffffff;
				border-radius: 20rpx;
				margin: 10rpx 10% 10rpx 10%;
				.popcontent{
					font-size: 25rpx;
					
				}
			}
		}
	}
	// poster
	.posterPopup{
		display: flex;
		flex-direction: column;
		.quit{
			width: 650rpx;
			image{
				width: 50rpx;
				height: 50rpx;
				margin-left: 600rpx;
			}
		}
		.poster{
			width: 650rpx;
			height: 800rpx;
		}
		.poster-tip{
			font-size: 25rpx;
			color: #ffffff;
			.text{
				margin-left: 210rpx;
			}
		}
	}
	// }
	// wm-poster{
	// 	position: fixed;
	// 	bottom: 0;
	// 	z-index: 1;
	// 	position: absolute;
	// }
	

</style>
