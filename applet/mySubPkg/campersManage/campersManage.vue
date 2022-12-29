<template>
	<view>
		<!-- 两部分：营员列表，添加营员按钮 -->
		<!-- 营员列表 -->
		<view class="camper-list">
			<navigator class="camper-item" v-for="(item,i) in campersList" :key="i" @longpress="deleteCamper(i)" :url="'../updateCamper/updateCamper?campersList_index='+i">
				<!-- 五个东西：头像，[[名字，性别],身份证号],修改的图标 -->
				<image class="avatar" :src="item.photoUrl"></image>
				<view class="name-gender-idcard">
					<view class="name-gender">
						<view class="name">{{item.name}}</view>
						<image src="../../static/my/male.svg" v-if="item.gender==0"></image>
						<image src="../../static/my/female.svg" v-else></image>
					</view>
					<!-- 身份证 -->
					<view  class="idcard" v-if="item.validIdNumber">{{item.validIdNumber}}</view>
					<view class="idcard" v-else>{{item.otherIdNumber}}</view>
				</view>
				<!-- 修改的图标 -->
				<uni-icons type="compose" size="24px"></uni-icons>
			</navigator>
		</view>
		
		<view class="tip">
			<view>=== 长按可删除营员 ===</view>
		</view>
		<!-- 添加营员 -->
		<navigator class="add-campers" url="../addCampers/addCampers">
			<view class="add-text">添加营员</view>
		</navigator>
		<empty-area></empty-area>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				// 营员列表
				campersList:[],
			};
		},
		onLoad() {
			this.onLoadOrder();
		},
		onShow() {
			this.onLoadOrder();
		},
		methods:{
			// onLoad中的操作顺序
			async onLoadOrder(){
				const that=this;
				await that.getCampersList();
			},
			// 从缓存中获取营员列表
			async getCampersList(){
				const that=this;
				that.campersList= uni.getStorageSync('campersList');
			},
			
			// 长按删除营员
			deleteCamper(i){
				console.log(i)
				const that=this;
				var id = that.campersList[i].id;
				console.log(id)
				uni.showModal({
					title:"删除营员?",
					content:"确定删除该营员信息吗?",
					success(res) {
						if(res.confirm){
							console.log("确认")
							uni.$http.delete('/user/camper/delete/'+id);
							// 将该营员信息从campersList列表中删除
							that.campersList.splice(i,1);
							// 更新缓存信息
							uni.setStorageSync("campersList",that.campersList);
						}else if(res.cancel){
							// console.log("取消")
							return
						}
					}
				})
			}
		}
	}
</script>

<style lang="scss">
page{
	background-color: #EFEFF4;
}
//提示
.tip{
	display: flex;
	flex-direction: row;
	justify-content: center;
	width: 100%;
	font-size: 25rpx;
	color: #5b5b5b;
}

// 营员列表
.camper-list{
	display: flex;
	flex-direction: column;
	width: 100%;
	margin: 30rpx 0 30rpx 0;
	justify-content: center;
	align-items: center;
	.camper-item{
		display: flex;
		flex-direction: row;
		background-color: #fff;
		border-radius: 10rpx;
		height: 160rpx;
		width: 90%;
		margin: 20rpx 0 10rpx 0;
		align-items: center;
		image{
			width: 120rpx;
			height: 120rpx;
			margin: 0 24rpx 0 28rpx;
			border-radius: 50%;
		}
		.name-gender-idcard{
			display: flex;
			flex-direction: column;
			justify-content: center;
			.name-gender{
				display: flex;
				flex-direction: row;
				align-items: center;
				height: 60rpx;
				image{
					width: 40rpx;
					height: 40rpx;
				}
				.name{
					width: 120rpx;
					max-width: 120px;
					white-space: nowrap;
					overflow: hidden;
					text-overflow: ellipsis;
				}
			}
			.idcard{
				// margin-top: 10rpx;
				height: 60rpx;
				font-size: 30rpx;
				color: #999999;
			}
		}
		uni-icons{
			margin-left: auto;
			margin-right: 30rpx;
		}
	}
}

// 添加营员的按钮
.add-campers{
	background-color: #13BB0E;
	width: 100%;
	height: 100rpx;
	display: flex;
	justify-content: center;
	align-items: center;
	position: fixed;
	bottom: 0;
	.add-text{
		font-size: 35rpx;
		color: #fff;
	}
}
</style>
