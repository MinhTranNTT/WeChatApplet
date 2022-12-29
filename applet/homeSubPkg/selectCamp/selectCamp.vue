<template>
	<view>
		<!-- 分为四个部分：选择参营日期、添加营员、优惠券和余额、协议部分 -->
		<!-- 选择参营日期部分 -->
		<view class="select-camp">
			<!-- 商品名称 -->
			<view class="camp-name">{{product.name}}</view>
			<!-- 参营日期选择 -->
			<view class="camp-date">
				<view class="camp-date-text">参营日期</view>
				<!-- 日期部分 -->
				<view class="camp-date-list" v-for="(item,i) in product.batchList" :key="item.id">
					<view class="camp-item">
						<view :class="i==campIndex? 'activate':'camp-date-item'" @tap="chooseCamp(i)"
							v-if="item.remains>-1">
							{{item.beginTime | dateFormat("MM月DD日")}}~{{item.endTime | dateFormat('MM月DD日')}} |
							剩余{{item.remains}}个名额</view>
						<view :class="i==campIndex? 'activate':'camp-date-item'" @tap="chooseCamp(i)" v-else>
							{{item.beginTime | dateFormat("MM月DD日")}}~{{item.endTime | dateFormat('MM月DD日')}} | 满额排队
						</view>
					</view>
				</view>
			</view>
		</view>
		<!-- 添加营员部分 -->
		<view class="add-campers" v-if="!isEmpty(selectedCampInfo) && selectedCampInfo.remains>-1">
			<!-- 数量 -->
			<view class="campers-num">
				<view class="camper-text">数量</view>
				<uni-number-box :value="selectedNum" min="1" :max="maxSelectableNum" @blur="modifyRemains" @change="modifyRemains"></uni-number-box>
			</view>
			<!-- 营员 -->
			<view class="select-campers">
				<view class="select-campers-text">营员</view>
				<view class="select-campers-text2">请添加{{selectedNum}}位营员</view>
				<button type="primary" size="mini" @tap="selectCampers">选择营员</button>
			</view>
			<!-- 显示已选择的营员 -->
			<view class="show-selected-campers" v-if="selectedCamperList">
				<view class="camper-item" v-for="(item,i) in selectedCamperList" :key="i">
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
			<!-- 启程集合地点 -->
			<picker class="select-location" mode="selector" :range="product.departureMeetingPoint" range-key="location"
				@change="departurePicker">
				<view class="select-loca">
					<view class="select-location-text">启程集合地点</view>
					<view class="select">
						<view class="selectedLocation" v-if="selectedDepartureLocation==''">请选择</view>
						<view class="selectedLocation" v-else>{{selectedDepartureLocation}}</view>
						<uni-icons type="forward"></uni-icons>
					</view>
				</view>
			</picker>
			<!-- 返程集合地点 -->
			<picker class="select-location" mode="selector" :range="product.returnMeetingPoint" range-key="location"
				@change="returnPicker">
				<view class="select-loca">
					<view class="select-location-text">返程集合地点</view>
					<view class="select">
						<view class="selectedLocation" v-if="selectedReturnLocation==''">请选择</view>
						<view class="selectedLocation" v-else>{{selectedReturnLocation}}</view>
						<uni-icons type="forward"></uni-icons>
					</view>
				</view>
			</picker>
		</view>
		<!-- 优惠券和余额 -->
		<view class="coupon">
			<!-- 有几张优惠券可用 -->
			<view class="coupon-item" @tap="showCouponPop">
				<view class="coupon-item-text">优惠券</view>
				<view class="coup">
					<view class="coupon-item-text1">有{{availableCoupons.length}}张优惠券可用</view>
					<uni-icons type="forward"></uni-icons>
				</view>
			</view>
			<!-- 余额 -->
			<view class="coupon-item">
				<view class="coupon-item-text">余额</view>
				<view class="coup">
					<view class="coupon-item-text1">暂无余额</view>
					<uni-icons type="forward"></uni-icons>
				</view>
			</view>
		</view>
		<!-- 协议部分 -->
		<view class="protocol">
			<checkbox-group>
				<label class="checkboxLabel" @tap="readProtocol">
					<checkbox v-model="checkBoxValue" :checked="checkBoxValue"></checkbox>
					<view class="protocol-text">已阅读并同意</view>
				</label>
			</checkbox-group>
			<view class="protocol-paper" @tap="protocolPopup">《训练营协议书》</view>
		</view>

		<!-- 协议详情 -->
		<uni-popup ref="protocolPopup" type="bottom" class="protocol-popup">
			<scroll-view scroll-y="true" show-scrollbar="true" class="protocol-scroll">
				<view class="title">《训练营协议书》</view>
				<my-protocol></my-protocol>
			</scroll-view>
		</uni-popup>
		
		<!-- 遮罩层 -->
		<view class="mask" v-if="popup"></view>

		<!-- 协议 -->
		<view class="proto" v-if="popup">
			<view class="title">
				<view class="proto-name">训练营协议书</view>
				<uni-icons type="closeempty" @click="closeProtocol"></uni-icons>
			</view>

			<scroll-view scroll-y="true" show-scrollbar="true" @scrolltolower="scrolltoBottom">
				<view class="scroll-wrap">
					<!-- <image src="https://zl21.club/file/test.jpg" mode="widthFix"></image> -->
					<my-protocol></my-protocol>
				</view>
			</scroll-view>
			<view class="unbottom-wrap" v-if="!isToBottom">
				<view class="unbottom-text" >请阅读并滚动到协议底部</view>
			</view>
			<view class="bottom-wrap" v-if="isToBottom" @click="closePopup">
				<view class="bottom-text">已阅读并同意</view>
			</view>
		</view>
		
		<!-- 优惠券 -->
		<uni-popup ref="couponPopup" type="bottom" class="coupon-Popup">
			<scroll-view scroll-y="true" show-scrollbar="true" class="coupon-scroll">
				<view class="coupon-item" v-for="(item,i) in availableCoupons" :key="i">
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
							<view class="type">
								<view class="value" v-if="item.goodsType===0">全商品可用</view>
								<view class="value" v-if="item.goodsType===1">指定商品可用</view>
								<view class="value" v-if="item.goodsType===2">指定商品不可用</view>
								<!-- <uni-icons type="forward" size="28rpx" color="#999999"></uni-icons> -->
							</view>
						</view>
						<!-- 根据优惠券状态显示具体的操作 -->
						<view class="functions">
							<checkbox size="30px" @tap="selectCoupon(i)"></checkbox>
						</view>
					</view>
					
				</view>
			</scroll-view>
		</uni-popup>

		<!-- 提交订单部分 -->
		<view class="commit">
			<view class="total-price-wrap">
				<!-- <view class="total-price">￥{{product.price*selectedNum}}</view> -->
				<view class="total-price">￥{{totalPrice}}</view>
				<view class="price-text">实付金额：</view>
			</view>
			<view class="commit-btn-wrap" @tap="submitOrder">
				<view class="commit-btn">提交订单</view>
			</view>
		</view>
		
		<!-- 占位区域 -->
		<view class="new-area"></view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				// 营期信息
				product: {},
				// 选择哪一个营期
				campIndex: 15,
				// 选中的营期信息
				selectedCampInfo: {},
				// 选择的营员人数
				selectedNum: 1,
				// 选中的营员的列表
				selectedCamperList:[],
				// 当前营期的剩余人数
				remains:[],
				// 选择的启程地点
				selectedDepartureLocation: "",
				// 选择的返程地点
				selectedReturnLocation: "",

				// 手机屏幕高度
				screenHeight: 0,
				// 协议书部分参数
				// 是否阅读到底部
				isToBottom: false,
				// 点击协议进行弹窗
				popup: false,
				// 是否选择已读
				checkBoxValue:false,
				// 总价钱
				totalPrice:0,
				// 可选的营员人数的最大值
				maxSelectableNum:0,
				
				// 优惠券列表
				availableCoupons:[],
				// 选择的优惠券的信息
				selectedCoupon:{},
				// 是否选择
				wetherSelect:false,
			};
		},
		onLoad(e) {
			// console.log(e)
			const that = this;
			uni.removeStorageSync("selectedCamper")
			that.getCampInfo(e.product_id)
			// that.getCampInfo(1)
			uni.getSystemInfo({
				success(res) {
					// console.log(res)
					that.screenHeight = res.windowHeight * 2 * 0.75;
				}
			})
			// 获取未使用的优惠券并保存
			that.getAvailableCoupons();
		},
		onShow() {
			this.selectedCamperList=uni.getStorageSync("selectedCamper");
		},
		onUnload() {
			uni.removeStorageSync("selectedCamper")
		},
		methods: {
			// 获取营期信息
			async getCampInfo(product_id) {
				const that = this;
				const {
					data: res
				} = await uni.$http.get('/products/getProductsListByBatch/' + product_id);
				// console.log(res);
				if (res.state !== 200) return uni.$showMsg();
				that.product = res.data;
				that.totalPrice=res.data.price;
				for(let i=0;i<that.product.batchList.length;i++){
					var remainItem = {"id":i,"remains":that.product.batchList[i].remains}
					that.remains.push(remainItem)
				}
			},
			
			// 获取可用的优惠券
			async getAvailableCoupons(){
				const that=this;
				const couponList = await uni.getStorageSync("couponList");
				if(couponList){
					for (let i = 0; i < couponList.length; i++) {
						if (couponList[i].status === 0) {
							that.availableCoupons.push(couponList[i])
						}
					}
				}else{
					// 还没获取到优惠券，先请求优惠券，
					const {data:res} = await uni.$http.get('/user/coupon/list');
					var coupons = res.data;
					for (let i = 0; i < coupons.length; i++) {
						if (coupons[i].status === 0) {
							that.availableCoupons.push(coupons[i])
						}
					}
					await uni.setStorageSync('couponList',coupons);
				}
			},
			
			// 弹出选择优惠券的弹窗
			showCouponPop(){
				const that=this;
				if(that.selectedCamperList.length===0){
					uni.$showMsg("请先选择营员")
					return
				}
				that.$refs.couponPopup.open();
			},
			
			// 根据优惠券类型更改总价
			updateTotalPriceByCoupon(totalPrice){
				const that=this;
				if(that.selectedCoupon !=={}){
					if(that.selectedCoupon.couponType===0){
						// 满减券
						if(that.selectedCoupon.atLeast>totalPrice){
							// 不符合要求
							uni.$showMsg("该优惠券不可用")
							that.selectedCoupon={};
						}else{
							// 可用
							totalPrice = totalPrice-that.selectedCoupon.money;
							return totalPrice;
						}
					}else if(that.selectedCoupon.couponType===1){
						// 折扣券
						totalPrice = totalPrice*that.selectedCoupon.discount;
						return totalPrice;
					}
				}
				return totalPrice;
			},
			
			// 选择优惠券
			selectCoupon(e){
				const that=this;
				// console.log(e)
				that.wetherSelect=!that.wetherSelect;
				if(that.wetherSelect){
					that.selectedCoupon=that.availableCoupons[e];
					// 选择成功，更新总价
					if(that.selectedCoupon.couponType===0){
						// 满减
						if(that.selectedCoupon.atLeast>that.totalPrice){
							uni.$showMsg("所选的优惠券不可用")
							return
						}else{
							// 可用
							that.totalPrice=(that.totalPrice-that.selectedCoupon.money).toFixed(2);
						}
					}else if(that.selectedCoupon.couponType===1){
						// 折扣券
						that.totalPrice=(that.totalPrice*that.selectedCoupon.discount).toFixed(2);
					}
				}else{
					that.selectedCoupon={};
					that.totalPrice=(that.product.price*that.selectedNum).toFixed(2)
				}
				
				that.$refs.couponPopup.close();
			},

			// 选择营期的函数
			async chooseCamp(i) {
				const that = this;
				if(that.campIndex!==15){
					that.product.batchList[that.campIndex].remains = that.remains[that.campIndex].remains;
				}
				// console.log(i)
				that.campIndex = i;
				// 获取选中的batch对应的id
				var batchid = that.product.batchList[i].id;
				// 请求batch数据
				const {
					data: res
				} = await uni.$http.get('/batch/getBatchById/' + batchid);
				if (res.state !== 200) return uni.$showMsg();
				that.selectedCampInfo = res.data;
				that.maxSelectableNum=res.data.remains;
				if(that.selectedNum>0 && that.product.batchList[that.campIndex].remains>0){
					that.product.batchList[that.campIndex].remains -= that.selectedNum;
					that.selectedCampInfo.remains -= that.selectedNum;
				}
				// that.remains=res.data.remains;

			},
			
			// 剩余人数随着选择更改
			modifyRemains(e){
				const that = this;
				const total = that.remains[that.campIndex].remains;
				var totalPrice = that.product.price;
				that.selectedNum=e;
				var rem = total-that.selectedNum;
				// that.product.batchList[that.campIndex].remains = total-that.selectedNum;
				// that.selectedCampInfo.remains = total-that.selectedNum;
				// 如果只剩最后一个，当已选择的数量大于1时，将最大可选数目设置为已选择的数量，否则，将可选值设为1，并且将剩余值设置为1
				
				that.product.batchList[that.campIndex].remains =rem;
				that.selectedCampInfo.remains =rem;
				that.totalPrice=(totalPrice*that.selectedNum).toFixed(2);
				that.selectedCoupon={};
				
				if(that.wetherSelect){
					uni.$showMsg("订单状态发生变化，请重新选择优惠券");
				}
				// that.totalPrice = that.updateTotalPriceByCoupon(totalPrice*that.selectedNum);
				// that.totalPrice=that.totalPrice.toFixed(2);
			},
			
			// 跳转到选择营员的界面
			selectCampers(){
				const that=this;
				uni.navigateTo({
					url:"/homeSubPkg/selectCampers/selectCampers?acquireCamperNum="+that.selectedNum,
				})
			},

			// 选择出发地点的函数
			departurePicker(e) {
				const that = this
				// console.log(e)
				that.selectedDepartureLocation = that.product.departureMeetingPoint[e.detail.value].location;
			},

			// 选择返程地点的函数
			returnPicker(e) {
				const that = this
				// console.log(e)
				that.selectedReturnLocation = that.product.returnMeetingPoint[e.detail.value].location;
			},
		

		
			// 判断一个对象是否为空
			isEmpty(obj) {
				return JSON.stringify(obj) === '{}'
			},

			// 弹出协议
			readProtocol() {
				const that = this;
				if(that.isToBottom){
					that.checkBoxValue=false;
					that.isToBottom=false;
				}else{
					that.popup=true;
					that.checkBoxValue=true;
				}
			},
			
			// 打开协议详情
			protocolPopup(){
				const that=this;
				that.$refs.protocolPopup.open()
			},
			
			// 关闭协议
			closeProtocol(){
				const that=this;
				// that.checkBoxValue=1;
				that.popup=false;
				that.checkBoxValue=false;
				that.isToBottom=false;
			},
			
			// 协议页面滑动到了最下边
			scrolltoBottom(){
				const that=this;
				that.isToBottom=true;
			},
			// 滑动到最下边，点击完成关闭弹窗
			closePopup(){
				const that=this;
				that.popup=false;
			},
			
			// 提交订单的函数
			async submitOrder(){
				const that=this;
				// 判断是否选择了营期
				if(that.campIndex===15){
					uni.$showMsg("请选择营期")
					return
				}
				// 判断是否选择了营员

				// 判断选择的营员的数量与上面选择的数量是否一致
				if(that.selectedCamperList.length!==that.selectedNum){
					uni.$showMsg("所选营员数量不一致，请重新选择!")
					return
				}
				// 判断是否选择了启程集合地点
				if(!that.selectedDepartureLocation){
					uni.$showMsg("请选择启程集合地点")
					return
				}
				// 判断是否选择了返程集合地点
				if(!that.selectedReturnLocation){
					uni.$showMsg("请选择返程集合地点")
					return
				}
				// 判断是否同意了协议书
				if(!that.checkBoxValue){
					uni.$showMsg("请阅读并同意协议书")
					return
				}
				
				// 调用生成预支付订单函数
				const preOrderRes = await that.generatePreOrder();
				if(preOrderRes.state!==200){
					uni.$showMsg("订单生成失败，请重试!")
					return
				}
				var info = preOrderRes.data;
				// console.log(info)
				await uni.requestPayment({
					// provider:'wxpay',
					appId:info.appId,
					nonceStr:info.nonceStr,
					package:info.package,
					paySign:info.paySign,
					signType:info.signType,
					timeStamp:info.timeStamp,
					success(payRes) {
						// 支付成功，设置订单支付状态为已支付，并跳转到订单界面。
						uni.removeStorageSync('couponList')
						uni.redirectTo({
							url:"/mySubPkg/orderManage/orderManage"
						})
					},
					fail(e) {
						// 取消付款，直接跳转到待付款订单界面，设置订单保留时间为15分钟，到期自动取消订单并将订单状态设置为取消状态
						// 设置有效时间为30分钟
						const expirationTime = new Date().getTime()+30*60*1000;
						uni.setStorageSync("order"+info.sn,{
							data:info,
							expirationTime:expirationTime
						})
						uni.redirectTo({
							url:"/mySubPkg/orderManage/orderManage"
						})
					}
					
				})
				
			},
			
			// 生成预支付订单的函数
			async generatePreOrder(){
				const that=this;
				var campInfo = JSON.parse(JSON.stringify(that.selectedCampInfo));
				var camperList = JSON.parse(JSON.stringify(that.selectedCamperList));
				// 构造订单所需的变量
				var orderInfo={
					orderType:0,
					productId:that.product.id,  // 商品的id
					productName:that.product.name,  //商品的名字
					productImage:that.product.bannerList[0].url, //商品的图片
					registerLocation:that.selectedDepartureLocation,
					returnLocation:that.selectedReturnLocation,
					camperInfo:campInfo, //选择的营期信息
					productCount:that.selectedNum, //选择的商品的数量
					camperList:camperList,  //选择的营员的列表
					originalPrice:that.product.price*100, //商品原价
					refundStatus:0, //退款状态设置为未申请退款
					payPrice:that.totalPrice*100, //总价
				}
				
				if(that.wetherSelect){
					orderInfo["coupon"] = that.selectedCoupon;
					orderInfo["promotionPrice"] = (that.totalPrice - that.product.price*that.selectedNum)*100;
					orderInfo["couponId"] = that.selectedCoupon.id;
				}
				// console.log(orderInfo)
				// 调用后端接口，将订单信息传给后端
				const {data:res} = await uni.$http.post('/user/wxPay/pay',orderInfo);
				// console.log(res);
				return res;
			}
		}
	}
</script>

<style lang="scss">
	page {
		background-color: #F7F7F7;
	}

	.select-camp {
		display: flex;
		flex-direction: column;
		margin: 20rpx 20rpx 20rpx 20rpx;
		background-color: #ffffff;
		border-radius: 20rpx;

		.camp-name {
			font-size: 30rpx;
			font-weight: bold;
			margin: 20rpx 20rpx 20rpx 20rpx;
			padding-bottom: 20rpx;
			border-bottom: #EEEEEE 1rpx solid;
		}

		.camp-date {
			margin-left: 20rpx;
			margin-bottom: 20rpx;
			display: flex;
			flex-direction: column;

			.camp-date-text {
				font-size: 35rpx;
				font-weight: bold;
				margin-bottom: 20rpx;

			}

			.camp-date-list {
				display: flex;
				flex-direction: column;
				font-size: 25rpx;
				margin: 10rpx 15rpx 10rpx 15rpx;

				.camp-item {
					display: flex;

					.camp-date-item {
						background-color: #EEEEEE;
						margin-left: 5rpx;
						border: #ffffff solid 1px;
						padding: 10rpx 15rpx 10rpx 15rpx;
						border-radius: 24rpx;
					}

					.activate {
						background-color: #FFEDE6;
						color: #FF4800;
						border: #FF4800 solid 1px;
						border-radius: 24rpx;
						margin-left: 5rpx;
						padding: 10rpx 15rpx 10rpx 15rpx;
					}
				}
			}
		}
	}

	.add-campers {
		display: flex;
		flex-direction: column;
		margin: 20rpx 20rpx 20rpx 20rpx;
		background-color: #ffffff;
		border-radius: 20rpx;
	
		.campers-num {
			display: flex;
			flex-direction: row;
			margin: 20rpx 20rpx 20rpx 20rpx;
			padding-bottom: 15rpx;
			border-bottom: #F7F7F7 1px solid;

			.camper-text {
				font-weight: bold;
			}

			uni-number-box {
				margin-left: auto;
				margin-right: 20rpx;
			}
		}

		.select-campers {
			display: flex;
			flex-direction: row;
			margin: 5rpx 20rpx 20rpx 20rpx;
			align-items: center;
			padding-bottom: 15rpx;
			border-bottom: #F7F7F7 1px solid;

			.select-campers-text {
				font-size: 30rpx;
				font-weight: bold;
				margin-right: 15rpx;
			}

			.select-campers-text2 {
				color: #CCCCCC;
				font-size: 27rpx;
			}

			button {
				margin-left: auto;
				margin-right: 20rpx;
			}
		}
		.show-selected-campers{
			display: flex;
			flex-direction: column;
			justify-content: center;
			width: 80%;
			margin-left: 10%;
			margin-right: 10%;
			margin-bottom: 20rpx;
			.camper-item{
				justify-content: center;
				align-items: center;
				display: flex;
				flex-direction: row;
				height: 50rpx;
				border-bottom: #F7F7F7 1px solid;
				.name{
					margin-right: 20rpx;
					width: 120rpx;
					max-width: 120rpx;
				}
				.id{
					display: flex;
					flex-direction: row;
					align-items: center;
					width: 270rpx;
					margin: 0 20rpx 0 20rpx;
					font-size: 27rpx;
					color: #999999;
				}
				.gender{
					margin-left: 30rpx;
					display: flex;
					flex-direction: row;
					align-items: center;
					image{
						width: 40rpx;
						height: 40rpx;
					}
				}
			}
		}
		.select-location {
			margin: 5rpx 20rpx 20rpx 20rpx;
			align-items: center;
			padding-bottom: 15rpx;
			border-bottom: #F7F7F7 1px solid;

			.select-loca {
				display: flex;
				flex-direction: row;
				width: 100%;

				.select-location-text {
					font-size: 30rpx;
					font-weight: bold;
				}

				.select {
					display: flex;
					flex-direction: row;
					margin-left: auto;
					margin-right: 20rpx;
					.selectedLocation{
						font-size: 28rpx;
						color: #cccccc;
					}
				}
			}

		}
	}

	.coupon {
		display: flex;
		flex-direction: column;
		margin: 20rpx 20rpx 20rpx 20rpx;
		background-color: #ffffff;
		border-radius: 20rpx;

		.coupon-item {
			display: flex;
			flex-direction: row;
			margin: 20rpx 20rpx 0rpx 20rpx;
			padding-bottom: 15rpx;
			border-bottom: #F7F7F7 1px solid;

			.coupon-item-text {
				font-size: 30rpx;
				font-weight: bold;
			}

			.coup {
				margin-left: auto;
				margin-right: 20rpx;
				display: flex;
				flex-direction: row;

				.coupon-item-text1 {
					font-size: 27rpx;
					color: #FF4800;
				}

			}

		}
	}


	// 协议部分
	.protocol {
		display: flex;
		flex-direction: row;
		justify-content: center;
		align-items: center;
		.checkboxLabel{
			display: flex;
			flex-direction: row;
			align-items: center;
			justify-content: center;
			.protocol-text{
				font-size: 27rpx;
				color: #9B9B9B;
			}
		}
		.protocol-paper {
			font-size: 27rpx;
			color: #42A5F5;
		}
	}

	// 协议
	.proto {
		width: 86%;
		height: 70%;
		position: fixed;
		top: 15%;
		left: 7%;
		border-radius: 30rpx;
		display: flex;
		flex-direction: column;
		background-color: #ffffff;
		z-index: 1001;
		.title {
			display: flex;
			height: 8%;
			flex-direction: row;
			margin-top: 10rpx;
			margin-bottom: 10rpx;
			.proto-name {
				font-size: 35rpx;
				font-weight: bold;
				margin-left: 217.5rpx;
			}
			uni-icons {
				margin-left: auto;
				margin-right: 20rpx;
			}
		}
		scroll-view{
			height: 82%;
			display: flex;
			flex-direction: row;
			justify-content: center;
			.scroll-wrap{
				image{
					width: 100%;
					height: auto;
				}
				my-protocol{
					width: 90%;
				}
			}
		}
		.unbottom-wrap{
			display: flex;
			justify-content: center;
			align-items: center;
			text-align: center;
			height: 10%;
			background-color: #B5B5B5;
			border-radius: 0 0 30rpx 30rpx;
			.unbottom-text{
				display: flex;
				text-align: center;
				align-items: center;
			}
		}
		.bottom-wrap{
			display: flex;
			justify-content: center;
			align-items: center;
			text-align: center;
			height: 10%;
			background-color: #09BB07;
			border-radius: 0 0 30rpx 30rpx;
			.bottom-text{
				display: flex;
				text-align: center;
				align-items: center;
			}
		}
	}
	
	// 提交订单部分
	.commit{
		position: fixed;
		bottom: 0;
		display: flex;
		width: 100%;
		height: 50px;
		flex-direction: row;
		background-color: #ffffff;
		box-shadow: 0 0 20px #ccc;
		.total-price-wrap{
			margin-left: 20rpx;
			display: flex;
			flex-direction: column;
			align-items: center;
			justify-content: center;
			text-align: center;
			.total-price{
				color: #F87E57;
				font-size: 35rpx;
			}
			.price-text{
				font-size: 25rpx;
				margin-left: 20rpx;
			}
		}
		.commit-btn-wrap{
			height: 50px;
			width: 150px;
			display: flex;
			justify-content: center;
			align-items: center;
			background-color: #E94F4F;
			color: #ffffff;
			margin-left: auto;
			margin-right: 0;
			.commit-btn{
			}
		}

	}
	
	// 占位区域
	.new-area{
		height: 300rpx;
	}
	
	// 遮罩层
	.mask{
		position: fixed;
		top: 0;
		left: 0;
		right: 0;
		bottom: 0;
		background-color: rgba(0,0,0,0.5);
		z-index: 1000;
	}
	
	// 优惠券
	.coupon-Popup{
		display: flex;
		flex-direction: row;
		.coupon-scroll{
			height: 600rpx;
			width: 100%;
			background-color: #EFEFF4;
			display: flex;
			flex-direction: column;
			align-items: center;
			margin-top: 30rpx;
			border-radius: 30rpx 30rpx 0 0;
			.coupon-item{
				display: flex;
				flex-direction: column;
				// align-items: center;
				width: 674;
				margin: 30rpx 38rpx 0 38rpx;
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
						checkbox{
							margin-left: 100rpx;
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
	}
	
	.protocol-popup{
		display: flex;
		.protocol-scroll{
			height: 600rpx;
			width: 100%;
			background-color: #EFEFF4;
			display: flex;
			flex-direction: column;
			align-items: center;
			margin-top: 30rpx;
			justify-content: center;
			border-radius: 30rpx 30rpx 0 0 ;
			.title{
				margin-left: 250rpx;
				margin-top: 20rpx;
				margin-bottom: 20rpx;
			}
			// my-protocol{
			// 	border-radius: 20rpx 20rpx 0 0 ;
			// }
		}

	}
</style>
