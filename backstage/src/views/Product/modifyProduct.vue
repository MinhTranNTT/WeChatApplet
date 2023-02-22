<template>
  <div class="product-form">
    <el-form :model="productInfo" :rules="productRules" ref="productForm" label-width="120px" class="login-form"
      size="default">

      <el-form-item label="商品名称" prop="name">
        <el-input v-model="productInfo.name" placeholder="请输入商品名称"></el-input>
      </el-form-item>
      <el-form-item label="商品价格" prop="price">
        <el-input v-model.number="productInfo.price" placeholder="请输入商品价格,单位为元" type="number"></el-input>
      </el-form-item>
      <el-form-item label="商品描述" prop="description">
        <el-input v-model="productInfo.description" placeholder="请输入商品描述"></el-input>
      </el-form-item>
      <el-form-item label="营地地点" prop="location">
        <el-input v-model="productInfo.location" placeholder="请输入营地地点"></el-input>
      </el-form-item>
      <el-form-item label="商品类别" prop="categoryId">
        <el-select v-model="productInfo.categoryId" placeholder="请选择商品类别">
          <el-option v-for="item in categoryList" :key="item.id" :label="item.name" :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>

      <!-- 商品排次 -->
      <el-form-item label="商品排次" prop="productsOrder">
        <el-input-number v-model="productInfo.productsOrder" :min="1"></el-input-number>
      </el-form-item>
      <!-- 年龄限制 -->
      <el-form-item label="最小年龄" prop="minAge">
        <el-input-number v-model="productInfo.minAge" :min="1" :max="99" />
      </el-form-item>

      <el-form-item label="最大年龄" prop="maxAge">
        <el-input-number v-model="productInfo.maxAge" :min="1" :max="99" />
      </el-form-item>

      <!-- 集合地点 -->
      <el-form-item label="启程集合地点" prop="departureMeetingPoint">
        <div v-for="(item,index) in productInfo.departureMeetingPoint" :key="index">
          <el-card style="margin:5px 5px 3px" @click="deleteDepartureLocationCard(index)">{{ item.location }}</el-card>
        </div>

        <div class="add-location" @click="openDepartureLocationDialog=true"><el-icon size="40px"><Plus/></el-icon></div>

        <el-dialog v-model="openDepartureLocationDialog" title="请输入启程集合地点">
          <el-input v-model="location" placeholder="请输入启程集合地点"></el-input>
          <template #footer>
            <el-button @click="openDepartureLocationDialog = false">取 消</el-button>
            <el-button type="primary" @click="addDepartureMeetingPoint">确 定</el-button>
          </template>
        </el-dialog>
      </el-form-item>

      <el-form-item label="返程集合地点" prop="returnMeetingPoint">
        <div v-for="(item,index) in productInfo.returnMeetingPoint" :key="index">
          <el-card style="margin:5px 5px 3px" @click="deleteReturnLocationCard(index)">{{ item.location }}</el-card>
        </div>

        <div class="add-location" @click="openReturnLocationDialog=true"><el-icon size="40px"><Plus/></el-icon></div>

        <el-dialog v-model="openReturnLocationDialog" title="请输入返程集合地点">
          <el-input v-model="location" placeholder="请输入返程集合地点"></el-input>
          <template #footer>
            <el-button @click="openReturnLocationDialog = false">取 消</el-button>
            <el-button type="primary" @click="addReturnMeetingPoint">确 定</el-button>
          </template>
        </el-dialog>
      </el-form-item>

      <el-form-item label="轮播图列表" prop="bannerList">
        <el-upload :file-list="productInfo.bannerList" :action="uploadUrl" list-type="picture-card"
          :on-preview="handlePictureCardPreview" :on-remove="handleBannerRemove" :on-success="onBannerSuccess">
          <el-icon>
            <Plus />
          </el-icon>
        </el-upload>
        <el-dialog v-model="dialogVisible">
          <el-image :src="dialogImageUrl" :initial-index="0"></el-image>
        </el-dialog>
      </el-form-item>

      <el-form-item label="商品详情图图片" prop="detailList">
        <el-upload :file-list="productInfo.detailList" :action="uploadUrl" list-type="picture-card"
          :on-preview="handlePictureCardPreview" :on-remove="handleDetailRemove" :on-success="onDetailSuccess">
          <el-icon>
            <Plus />
          </el-icon>
        </el-upload>

        <el-dialog v-model="dialogVisible">
          <el-image :src="dialogImageUrl" :initial-index="0"></el-image>
        </el-dialog>
      </el-form-item>

      <el-form-item label="商品海报图片" prop="productPoster">
        <el-upload class="avatar-uploader" :action="uploadUrl" :show-file-list="false" :on-success="handlePosterSuccess">
          <img v-if="productInfo.productPoster" :src="productInfo.productPoster" class="avatar"/>
          <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
        </el-upload>
      </el-form-item>

      <div class="commit">
        <el-button class="commit-button" type="primary" @click="modifyProduct">修 改</el-button>
      </div>
    </el-form>
  </div>

</template>

<script setup>
import router from '@/router'
import { onMounted, reactive, ref } from '@vue/runtime-core'
import axios from 'axios'
import base from '@/views/api/base.js';

const uploadUrl = base.host + '/file/upload';
// token
const token = localStorage.getItem('token');

// 商品信息
const productInfo = reactive({
  id: undefined,
  name: '',
  price: '',
  description: '',
  categoryId: '',
  minAge: '',
  maxAge: '',
  location: '',
  bannerList: [],
  detailList: [],
  productPoster: '',
  departureMeetingPoint: [],
  returnMeetingPoint: [],
  productsOrder: ''
})
// 商品类别
const categoryList = reactive([])

// 地点
const location = ref('');
// 新增表单的ref
const productForm = ref(null);
// 商品信息校验规则
const productRules = reactive({
  name: [
    { required: true, message: '请输入商品名称', trigger: 'blur' },
    { min: 2, max: 30, message: '长度在 2 到 30 个字符', trigger: 'blur' }
  ],
  price: [
    { required: true, message: '请输入商品价格', trigger: 'blur' },
    { type: 'number', message: '请输入数字值', trigger: 'blur' }
  ],
  description: [
    { required: true, message: '请输入商品描述', trigger: 'blur' },
    { min: 2, max: 200, message: '长度在 2 到 200 个字符', trigger: 'blur' }
  ],
  location: [
    { required: true, message: '请输入集合地点', trigger: 'blur' },
    { min: 2, max: 50, message: '长度在 2 到 50 个字符', trigger: 'blur' }
  ],
  categoryId: [
    { required: true, message: '请选择商品类别', trigger: 'blur' }
  ],
  minAge: [
    { required: true, message: '请输入最小年龄', trigger: 'blur' },
    { type: 'number', message: '请输入数字值', trigger: 'blur' }
  ],
  maxAge: [
    { required: true, message: '请输入最大年龄', trigger: 'blur' },
    { type: 'number', message: '请输入数字值', trigger: 'blur' }
  ],
  bannerList: [
    { required: true, message: '请上传轮播图', trigger: 'blur' }
  ],
  detailList: [
    { required: true, message: '请上传商品详情图', trigger: 'blur' }
  ],
  productPoster: [
    { required: true, message: '请上传商品海报', trigger: 'blur' }
  ],
  
})
const dialogImageUrl = ref('')
const dialogVisible = ref(false)
const disabled = ref(false)

// 打开添加集合地点的弹窗
const openDepartureLocationDialog = ref(false)
// 打开添加返程集合地点的弹窗
const openReturnLocationDialog = ref(false)

const handleBannerRemove= async(uploadFile, uploadFiles) => {
  var url = '';
  if (uploadFile.response) {
    url = uploadFile.response;
  } else {
    url = uploadFile.url;
  }
  await axios.post('/file/delete', { url: url })
  for (var i = 0; i < productInfo.bannerList.length; i++) {
    if (productInfo.bannerList[i].url == url) {
      productInfo.bannerList.splice(i, 1);
    }
  }
  // console.log(productInfo.bannerList);
}

const handleDetailRemove = async(uploadFile) =>{
  var url = '';
  if(uploadFile.response){
    url = uploadFile.response;
  }else{
    url = uploadFile.url;
  }
  await axios.post('/file/delete',{url:url})
  for (var i = 0;i<productInfo.detailList.length;i++){
    if(productInfo.detailList[i].url == url){
      productInfo.detailList.splice(i,1);
    }
  }
}

const handlePictureCardPreview =(uploadFile) => {
  dialogImageUrl.value = uploadFile.url
  dialogVisible.value = true
}

// banner图片success
const onBannerSuccess = (res, file) => {
  var bannerObj = {};
  bannerObj.url = res;
  productInfo.bannerList.push(bannerObj);
  // console.log(productInfo.bannerList);
}

// 详情图上传success
const onDetailSuccess = (res,file) =>{
  var detailObj = {};
  detailObj.url = res;
  productInfo.detailList.push(detailObj);
}

const handlePosterSuccess = async(res,file) =>{
  // console.log(res);
  if(productInfo.productPoster){
    await axios.post('/file/delete',{url:productInfo.productPoster})
  }
  productInfo.productPoster = res
}

// 年龄
const handleChange = (value) => {
  console.log(value);
  console.log(productInfo);
}

// 添加启程地点
const addDepartureMeetingPoint = () =>{
  if(!location.value){
    ElMessage.error('请输入地点');
    return;
  }
  var locationObj = {};
  locationObj.location = location.value;
  productInfo.departureMeetingPoint.push(locationObj)
  location.value = '';
  // 关闭弹窗
  openDepartureLocationDialog.value = false;
}

// 添加返程地点
const addReturnMeetingPoint = () =>{
  if(!location.value){
    ElMessage.error('请输入地点');
    return;
  }
  var locationObj = {};
  locationObj.location = location.value;
  productInfo.returnMeetingPoint.push(locationObj)
  location.value='';
  // 关闭弹窗
  openReturnLocationDialog.value = false;
}
// 删除启程地点
const deleteDepartureLocationCard = (index) =>{
  productInfo.departureMeetingPoint.splice(index,1);
}

// 删除返程地点
const deleteReturnLocationCard = (index) =>{
  productInfo.returnMeetingPoint.splice(index,1);
}

// 保存商品信息
const modifyProduct = async() => {
  try {
    productForm.value.validate((valid)=>{
      if(!valid){
        ElMessage.info('请完善商品信息');
        return false;
      }
      const { data: res } = axios.put('/products/update', productInfo)
    });
    router.push('/product/list')
  } catch (error) {
    console.log(error);
  }
}

// 根据id获取商品信息
const getProductInfoById = async(id) => {
  const {data:res} = await axios.get('/products/get/'+id)
  productInfo.id= res.data.id
  productInfo.name = res.data.name
  productInfo.price = res.data.price
  productInfo.description = res.data.description
  productInfo.location = res.data.location
  productInfo.categoryId = res.data.categoryId
  productInfo.minAge = res.data.minAge
  productInfo.maxAge = res.data.maxAge
  productInfo.departureMeetingPoint = [...res.data.departureMeetingPoint]
  productInfo.returnMeetingPoint = [...res.data.returnMeetingPoint]
  productInfo.productPoster = res.data.productPoster
  productInfo.detailList = [...res.data.detailList]
  productInfo.bannerList = [...res.data.bannerList]
  productInfo.productsOrder = res.data.productsOrder
  // console.log(productInfo);
}

  // 获取商品类别
  const getCategoryList = async () => {
    const { data: res } = await axios.get('/products/category')
    categoryList.push(...res.data);
  }

onMounted(() => {
  // console.log(router.currentRoute.value.params.id)
  var id = Number(router.currentRoute.value.params.id)
  getProductInfoById(id);
  getCategoryList();
})

</script>

<style lang="scss">
.product-form {
  width: 80%;
  margin: 0 auto;
}
.avatar{
  width: 178px;
  height: 178px;
}

.avatar-uploader .el-upload {
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}

.avatar-uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  text-align: center;
}

.add-location{
  border: #8c939d 1px dashed;
  border-radius: 6px;
  height: 70px;
  width:70px;
  text-align: center;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  color: #8c939d;
}

.commit{
  width: 100%;
  height: 100%;
  // background-color: #409eff;
  // color: #fff;
  border-radius: 6px;
  border: none;
  // margin: 0 auto;
  // margin-top: 20px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  .commit-button{
    width: 100px;
    height: 40px;
  }
}
</style>