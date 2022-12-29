<template>
	<view>
		<!-- 三部分：选择营员的提示，营员列表，完成或者返回 -->
		<!-- 提示 -->
		<view class="tips">
			<view class="tip-text">
				<view id="one">需选择 {{acquiredCamperNum}} 位营员</view>
				<view id="one">已选择 {{selectedCamperNum}} 位营员</view>
			</view>
			<!-- 新增营员的按钮 -->
			<navigator class="add-btn" url="../../mySubPkg/addCampers/addCampers">
				<image src="../../static/my/add-camper.svg"></image>
				<view class="text">新增营员</view>
			</navigator>
		</view>
		
		<!-- 营员列表 -->
		<view class="camper-list">
			<view class="camper-item" v-for="(item,i) in campersList" :key="i">
				<!-- 五个东西：头像，[[名字，性别],身份证号],修改的图标 -->
				<navigator class="navigator" :url="'../../mySubPkg/updateCamper/updateCamper?campersList_index='+i">
					<uni-icons type="compose" size="28px" color="#22AC38"></uni-icons>
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
				</navigator>
				<checkbox class="checkbox" @tap="selectCamper(i)"></checkbox>
			</view>
		</view>
		
		<!-- 最下方的按钮 -->
		<view class="confirm-cancle">
			<button class="cancle" type="default" @tap="cancle">返回</button>
			<button class="complite" type="primary" @tap="complite">完成</button>
		</view>
		<empty-area></empty-area>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				// 需要选择的营员的个数
				acquiredCamperNum:0,
				// 已选择的营员的个数
				selectedCamperNum:0,
				// 营员列表
				campersList:[],
				// 选择的营员列表
				checkList:[{"id":0,"isSelected":false}],
			};
		},
		onLoad(e) {
			this.acquiredCamperNum=Number(e.acquireCamperNum);
			this.onLoadOrder();
			uni.removeStorageSync("selectedCamper")
		},
		onShow() {
			this.onLoadOrder();
		},
		methods:{
			// onLoad执行顺序
			async onLoadOrder(){
				const that=this;
				await that.getCampersList();
			},
			// 从缓存中获取营员列表
			async getCampersList(){
				const that = this;
				that.campersList =await uni.getStorageSync("campersList")
			},
			
			// 选择营员的函数
			selectCamper(i){
				const that=this;
				// console.log(i)
				for(let j=0;j<that.checkList.length;j++){
					if(that.checkList[j].id==i){
						that.checkList[j].isSelected = !that.checkList[j].isSelected;
						if(that.checkList[j].isSelected===true){
							 that.selectedCamperNum++;
						}else{
							that.selectedCamperNum--;
						}
						return
					}
				}
				var item ={"id":i,"isSelected":true};
				that.checkList.push(item);
				that.selectedCamperNum++;
			},
			
			// 返回
			async cancle(){
				await uni.removeStorageSync("selectedCamper")
				uni.navigateBack();
			},
			
			// 确认
			async complite(){
				const that=this;
				if(that.selectedCamperNum!==that.acquiredCamperNum){
					uni.showToast({
						title:"所选人数不符，请重新选择",
						icon:'none'
					})
					return
				}
				var selectedCamper=[];
				for(let i=0;i<that.checkList.length;i++){
					if(that.checkList[i].isSelected==true){
						selectedCamper.push(that.campersList[that.checkList[i].id]);
					}
				}
				await uni.setStorageSync("selectedCamper",selectedCamper);
				uni.navigateBack()
			}
		}
		
	}
</script>

<style lang="scss">
.tips{
	display: flex;
	flex-direction: row;
	align-items: center;
	width: 100%;
	height: 160rpx;
	background-color: #ffffff;
	
	.tip-text{
		margin-left: 30rpx;
		#one{
			margin: 10rpx 0 10rpx 0;
		}
	}
	.add-btn{
		background-color: #22AC38;
		display: flex;
		flex-direction: column;
		justify-content: center;
		align-items: center;
		width: 100rpx;
		height: 100rpx;
		margin-left: auto;
		margin-right: 70rpx;
		border-radius: 10rpx;
		box-shadow: #ccc 5rpx 5rpx 5rpx;
		image{
			width: 50rpx;
			height: 50rpx;
		}
		.text{
			font-size: 25rpx;
			color: #FEFFFE;
			margin-bottom: 10rpx;
		}
		
	}
}
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
		height: 140rpx;
		width: 90%;
		margin: 20rpx 0 10rpx 0;
		align-items: center;
		.navigator{
			display: flex;
			flex-direction: row;
			align-items: center;
			width: 540rpx;
			uni-icons{
				margin-left: 30rpx;
				margin-right: 20rpx;
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
		}
		.checkbox{
			margin-left: auto;
			margin-right: 45rpx;
		}
	}
}

.confirm-cancle{
	display: flex;
	flex-direction: row;
	position: fixed;
	bottom: 0;
	height: 120rpx;
	width: 100%;
	align-items: center;
	background-color: #ffffff;
	button{
		font-size: 28rpx;
		border: #1AAD19 0.5px solid;
	}
	.cancle{
		width: 200rpx;
		margin-left: 40rpx;
		margin-right: auto;
		color: #1AAD19;
	}
	.complite{
		width: 400rpx;
		margin-left: auto;
		margin-right: 40rpx;
	}
}
</style>
