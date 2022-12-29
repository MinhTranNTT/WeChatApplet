<template>
	<view>
		<view class="adv">
			<view class="tips">
				<view class="adv-time">{{advTime}}秒后 </view>
				<view class="jump" @tap="jump">跳转</view>
			</view>
			<image class="adv-image" :src="adv.imageUrl"></image>
		</view>
		
	</view>
</template>

<script>
	export default {
		data() {
			return {
				// adv信息
				adv:{},
				// 跳转时间
				advTime:3,
				// 定时器
				interval:null,
			};
		},
		onLaunch() {
			// this.getAdv();
		},
		onLoad() {
			this.getAdv();
		},
		onShow() {
			const that=this;
			that.interval =  setInterval(function(){
				that.advTime--;
				if(that.advTime===0){
					clearInterval(that.interval);
					uni.switchTab({
						url:'/pages/home/home',
					})
				}
			},1000)
		},
		methods:{
			//获取adv
			getAdv(){
				const that=this;
				uni.request({
					url:getApp().globalData.BASE_URL+"/ads/getAds",
					method:'GET',
					success(res) {
						that.adv=res.data.data;
						that.advTime = res.data.data.jumpTime;
					}
				})
			},
			
			// 跳转
			jump(){
				const that=this;
				clearInterval(that.interval)
				uni.switchTab({
					url:'/pages/home/home'
				})
			}
		}
	}
</script>

<style lang="scss">
.adv{
	width: 100%;
	height: 100%;
	display: flex;
	flex-direction: row;
	
	.tips{
		position: fixed;
		z-index: 2;
		top: 20rpx;
		right: 20rpx;
		display: flex;
		flex-direction: row;
		font-size: 26rpx;
		color: #ffffff;
		border-radius: 20rpx;
		background-color: rgba(138, 137, 137,0.5);
		.adv-time{
			margin: 10rpx 5rpx 10rpx 10rpx;
		}
		.jump{
			margin: 10rpx 10rpx 10rpx 6rpx;
		}
	}
	
	.adv-image{
		position: relative;
		z-index: 0;
		width: 100vw;
		height: 100vh;
	}
}
</style>
