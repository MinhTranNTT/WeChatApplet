<template>
	<view>
		<!-- 名字| 姓名、性别、出生日期、衣服尺寸、证件类型、证件号、在读年纪、监护人姓名、监护人手机号、营员正面照 -->
		<view class="title">
			<view class="main-title">新增营员</view>
			<view class="sub-title">
				<view id="one">（标有</view>
				<view id="two">*</view>
				<view id="one">为必填）</view>
			</view>
		</view>
		
		<!-- 表单 -->
		<view class="form-data">
			<uni-forms ref="camperForm" :modelValue="camperInfo" validateTrigger="bind" labelWidth="103px" labelAlign="left" labelPosition="left">
				<!-- 姓名 -->
				<uni-forms-item label="姓名" name="name" required>
					<uni-easyinput type="text" v-model="camperInfo.name" placeholder="请输入姓名"></uni-easyinput>
				</uni-forms-item>
				<!-- 性别 -->
				<uni-forms-item label="性别" name="gender" required>
					<uni-data-checkbox v-model="camperInfo.gender" :localdata="genderRange"></uni-data-checkbox>
				</uni-forms-item>
				<!-- 出生日期 -->
				<uni-forms-item label="出生日期" name="birthday" required>
					<uni-datetime-picker type="date" v-model="camperInfo.birthday"></uni-datetime-picker>
				</uni-forms-item>
				<!-- 衣服尺寸 -->
				<uni-forms-item label="衣服尺寸" name="clothSize" required>
					<uni-data-select v-model="camperInfo.clothSize" :localdata="clothSizeRange"></uni-data-select>
				</uni-forms-item>
				<!-- 证件类型 -->
				<uni-forms-item label="证件类型" name="typeOfCerti" required>
					<uni-data-select v-model="camperInfo.typeOfCerti" :localdata="certificateTypeRange"></uni-data-select>
				</uni-forms-item>
				<!-- 有效证件号 -->
				<uni-forms-item label="有效证件号" name="validIdNumber" required v-if="camperInfo.typeOfCerti==0 || camperInfo.typeOfCerti==10">
					<uni-easyinput type="idcard" v-model="camperInfo.validIdNumber" placeholder="请输入有效证件号" ></uni-easyinput>
				</uni-forms-item>
				<!-- 其他类型的证件号 -->
				<uni-forms-item label="有效证件号" name="otherIdNumber" required v-else>
					<uni-easyinput type="idcard" v-model="camperInfo.otherIdNumber" placeholder="请输入有效证件号"></uni-easyinput>
				</uni-forms-item>
				<!-- 在读学校 -->
				<uni-forms-item label="在读学校" name="school" required>
					<uni-easyinput type="text" v-model="camperInfo.school" placeholder="请输入在读学校"></uni-easyinput>
				</uni-forms-item>
				<!-- 在读年纪 -->
				<uni-forms-item label="在读年级" name="grade" required>
					<uni-easyinput type="text" v-model="camperInfo.grade" placeholder="请输入在读年级"></uni-easyinput>
				</uni-forms-item>
				<!-- 监护人姓名 -->
				<uni-forms-item label="监护人姓名" name="guardianName" required>
					<uni-easyinput type="text" v-model="camperInfo.guardianName" placeholder="请输入监护人姓名"></uni-easyinput>
				</uni-forms-item>
				<!-- 监护人手机号 -->
				<uni-forms-item label="监护人手机号" name="phone" required>
					<uni-easyinput type="text" v-model="camperInfo.phone" placeholder="请输入监护人手机号"></uni-easyinput>
				</uni-forms-item>
				<!-- 上传营员正面照 -->
				<uni-forms-item label="营员正面照" name="photoUrl" required>
					<uni-file-picker v-model="photoPath" title="请上传一张营员照片"
					fileMediatype="image" mode="grid" :imageStyles="imageStyles"
					disablePreview :del-icon="false" returnType = "object"
					:limit="1" @select="select" @progress="progress"
					@success="success">选择照片</uni-file-picker>
				</uni-forms-item>
				<button type="primary" @tap="submit">提交</button>
			</uni-forms>
		</view>
		
		<!-- 空白区域 -->
		<view class="empty-area"></view>
	</view>
</template>

<script>
	import {BASE_URL} from '@/common/global.js'
	export default {
		data() {
			return {
				// 营员信息
				camperInfo:{
					photoUrl:""
				},
				// 所有营员的列表
				campersList:[],
				// 营员照片
				photoPath:{},
				// 性别选项
				genderRange:[{"value":0,"text":"男"},{"value":1,"text":"女"}],
				// 衣服尺寸
				clothSizeRange:[{"value":0,"text":"110"},{"value":1,"text":"120"},{"value":2,"text":"130"},
								{"value":3,"text":"140"},{"value":4,"text":"150"},{"value":5,"text":"160"},
								{"value":6,"text":"170"},{"value":7,"text":"180"},{"value":8,"text":"190"}],
				certificateTypeRange:[{"value":0,"text":"身份证"},{"value":1,"text":"护照"},
									{"value":2,"text":"台胞证"},{"value":3,"text":"回乡证"}],
				// 上传照片组件的样式
				imageStyles:{
					width:90,
					height:90,
					border:{
						color:"#EEEEEE",
						width:2,
						style:'dashed',
						radius:'1px'
					}
				},
				// 自定义表单验证规则
				customRules:{
					// 校验姓名
					name:{
						rules:[{
							required:true,
							errorMessage:'请输入姓名'
						},{
							minLength:1,
							maxLength:20,
							errorMessage:"姓名长度在{minLength}到{maxLength}个字符之间"
						}]
					},
					// 校验性别
					gender:{
						rules:[{
							required:true,
							errorMessage:"请选择性别"
						}]
					},
					// 校验出生日期
					birthday:{
						rules:[{
							required:true,
							errorMessage:"请选择出生日期",
						}]
					},
					// 校验衣服尺寸
					clothSize:{
						rules:[{
							required:true,
							errorMessage:"请选择衣服尺寸"
						}]
					},
					// 校验证件类型
					typeOfCerti:{
						rules:[{
							required:true,
							errorMessage:"请选择证件类型"
						}]
					},
					// 校验身份证
					validIdNumber:{
						rules:[{
							required:true,
							errorMessage:"请输入有效证件号"
						},{
							validateFunction:function(rule,value,data,callback){
								if(value.length!==18){
									callback("身份证位数应为18位")
								}else{
									var reg = /^\d{6}(18|19|20)?\d{2}(0[1-9]|1[012])(0[1-9]|[12]\d|3[01])\d{3}(\d|X)$/;
									var city = { 11: "北京", 12: "天津", 13: "河北", 14: "山西", 15: "内蒙古", 21: "辽宁", 22: "吉林", 23: "黑龙江 ", 31: "上海", 32: "江苏", 33: "浙江", 34: "安徽", 35: "福建", 36: "江西", 37: "山东", 41: "河南", 42: "湖北 ", 43: "湖南", 44: "广东", 45: "广西", 46: "海南", 50: "重庆", 51: "四川", 52: "贵州", 53: "云南", 54: "西藏 ", 61: "陕西", 62: "甘肃", 63: "青海", 64: "宁夏", 65: "新疆", 71: "台湾", 81: "香港", 82: "澳门", 91: "国外" };
									if(!reg.test(value)){
										callback("身份证格式错误")
									}else if(!city[value.substr(0,2)]){
										callback("身份证地址编码错误")
									}else{
										// var code=value.split('');
										var weights = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2];
										var checkCodes = ['1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'];
										var sum = 0;
										for(var i=0;i<17;i++){
											sum += value[i]*weights[i];
										}
										var checkCode = checkCodes[sum % 11];
										if(checkCode !==value[17]){
											callback("身份证校验位错误")
										}
									}
									
								}
								return true
							}
						},{
							
						}]
					},
					// 校验其他身份类型的号码
					otherIdNumber:{
						rules:[{
							required:true,
							errorMessage:"请输入有效证件号"
						}]
					},
					// 校验学校
					school:{
						rules:[{
							required:true,
							errorMessage:"请输入在读学校"
						}]
					},
					// 校验年纪
					grade:{
						rules:[{
							required:true,
							errorMessage:"请输入在读年级"
						}]
					},
					// 校验监护人姓名
					guardianName:{
						rules:[{
							required:true,
							errorMessage:"请输入监护人姓名"
						}]
					},
					// 校验监护人手机号
					phone:{
						rules:[{
							required:true,
							errorMessage:"请输入监护人手机号"
						},{
							validateFunction:function(rule,value,data,callback){
								const phoneNumberPattern = /^1[3456789]\d{9}$/;
								if(!phoneNumberPattern.test(value)){
									callback("手机号格式不正确")
								}
							}
						}]
					},
					// 校验营员正面照
					photoUrl:{
						rules:[{
							required:true,
							errorMessage:"请上传营员照片"
						}]
					}
				},
			};
		},
		onLoad(e) {
			
		},
		onReady() {
			this.$refs.camperForm.setRules(this.customRules);
		},
		methods:{
			// onLoad的顺序
			async onLoadOrder(){
				
			},
			// 从缓存中获取campersList并保存
			
			// 保存用户信息
			async saveCamper(info){
				await uni.$http.post('/user/camper/save',info);
				const {data:res} = await uni.$http.get('/user/camper/list');
				console.log(res)
				await uni.setStorageSync("campersList",res.data);
			},
			
			// 提交表单数据
			async submit(){
				const that=this;
				const res = await this.$refs.camperForm.validate().catch(err=>{
					uni.showToast({
						title:"请根据提示完善信息",
						icon:'none'
					})
					return
				});
				if(res){
					await that.saveCamper(res);
					uni.showToast({
						title:"上传成功",
						icon:'none'
					})
					uni.navigateBack()
				}
				// await that.saveCamper(res);
				
				// this.$refs.camperForm.validate().then(res=>{
				// 	console.log('表单数据信息',res);
				// 	// 如果camperInfo中的id不存在，则为添加成员，此时调用添加接口，
				// 		const {data:saveRes} =uni.$http.post('/user/camper/save',res);
				// 		// 重新请求所有的camper信息
				// 		const {data:getRes} =uni.$http.get('/user/camper/list');
				// 		uni.setStorageSync("campersList",getRes.data);
				// }).catch(err=>{
				// 	console.log("表单错误信息",err);
				// })
			},
			
			// 上传照片的函数
			// 选择时
			async select(e){
				const that=this;
				const photo = that.camperInfo.photoUrl;
				if(!e || photo){
					// var savedPath = that.camperInfo.photoUrl;
					await uni.$http.post('/user/file/delete',photo)
				}
				var photoPath = e.tempFilePaths[0];
				const [err,res] = await uni.$uploadFile(photoPath);
				that.camperInfo.photoUrl = res.data;
			},
			// 上传时
			progress(e){
				console.log(e)
				uni.showLoading({
					title:"长传中。。。"
				})
			},
			// 成功时
			success(e){
				console.log("上传成功"+e)
				uni.hideLoading()
			},
			
		}
	}
</script>

<style lang="scss">
.title{
	display: flex;
	flex-direction: row;
	height: 100rpx;
	width: 674rpx;
	justify-content: center;
	margin: 0 38rpx 0 38rpx;
	border-bottom: #999999 solid 1px;
	align-items: center;
	.main-title{
		font-size: 35rpx;
		font-weight: bold;
	}
	.sub-title{
		display: flex;
		flex-direction: row;
		height: 45rpx;
		align-items: flex-end;
		#one{
			font-size: 25rpx;
			color: #999999;
		}
		#two{
			columns: #ff3b3b;
			font-size: 25rpx;
		}
	}
}

.form-data{
	width: 674rpx;
	margin: 30rpx 38rpx 30rpx 38rpx;
}

.empty-area{
	height: 100rpx;
}
</style>
