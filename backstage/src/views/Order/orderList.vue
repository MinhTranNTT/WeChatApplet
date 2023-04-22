<template>
  <!-- 查询框 -->
  <div class="find-item">
    <el-form :inline="true" :model="findForm" class="demo-form-inline">
      <el-form-item label="订单状态">
        <el-select v-model="findForm.status" placeholder="请选择">
          <el-option label="全部" value=9></el-option>
          <el-option label="待付款" value=0></el-option>
          <el-option label="已付款" value=1></el-option>
          <el-option label="已完成" value=2></el-option>
          <el-option label="已取消" value=3></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="订单号">
        <el-input v-model="findForm.sn" placeholder="请输入订单号"></el-input>
      </el-form-item>
      <el-form-item label="用户id">
        <el-input v-model="findForm.openid" placeholder="请输入用户id"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="findOrder">查询</el-button>
      </el-form-item>
    </el-form>
  </div>
  <el-table :data="compData" :border="parentBorder" style="width:100%">
    <!-- <el-table-column type="expand">
      <template #default="props">
        <div class="detail-wrap">
          <h3>营期信息</h3>
          <el-table :data="props.row.camperInfo" :border="childBorder" style="width:70%;margin-left: 50px;">
            <el-table-column label="开始日期" prop="beginTime" :formatter="dateFormat" align="center"></el-table-column>
            <el-table-column label="结束日期" prop="endTime" :formatter="dateFormat" align="center"></el-table-column>
            <el-table-column label="库存" prop="stock" align="center"></el-table-column>
            <el-table-column label="总数" prop="stock" align="center"></el-table-column>
          </el-table>
        </div>
      </template>
    </el-table-column> -->

    <el-table-column type="index" label="序号" align="center"></el-table-column>
    <el-table-column prop="status" label="订单状态" align="center" :formatter="statusFormat"></el-table-column>
    <el-table-column prop="sn" label="订单号" align="center" show-overflow-tooltip="true"></el-table-column>
    <el-table-column prop="openid" label="用户id" align="center" show-overflow-tooltip="true"></el-table-column>
    <el-table-column prop="productName" label="商品名称" align="center" show-overflow-tooltip="true"></el-table-column>
    <el-table-column prop="productCount" label="数量" align="center"></el-table-column>
    <el-table-column prop="originalPrice" label="商品总价" align="center" :formatter="priceFormat"></el-table-column>
    <el-table-column prop="payPrice" label="支付总价" align="center" :formatter="priceFormat"></el-table-column>
    <el-table-column prop="createTime" label="创建时间" align="center" :formatter="dateFormat"></el-table-column>
    <el-table-column prop="payTime" label="支付时间" align="center" :formatter="dateFormat"></el-table-column>
    <el-table-column prop="finishTime" label="完成时间" align="center" :formatter="dateFormat"></el-table-column>
    <el-table-column prop="calcelTime" label="取消时间" align="center" :formatter="dateFormat"></el-table-column>
    <el-table-column prop="cancelType" label="取消原因" align="center" :formatter="cancelTypeFormat"></el-table-column>
    <el-table-column prop="refundPrice" label="退款金额" align="center" :formatter="priceFormat"></el-table-column>
  </el-table>

  <!-- 分页 -->
  <el-pagination
  v-model:current-page="currentPage"
  v-model:page-size="pageSize"
  :page-sizes="[5, 10, 20, 30, 40, 50]"
  :small="small"
  :disabled="disabled"
  :background="background"
  layout="total, sizes, prev, pager, next, jumper"
  :total="total"
  @size-change="handleSizeChange"
  @current-change="handleCurrentChange"
  />

</template>

<script setup>
import axios from 'axios';
import { ElMessage } from 'element-plus';
import moment from 'moment'
import { onMounted, reactive, ref, computed} from 'vue';

var orderList = reactive([])
var orderFormList = reactive([])

// 表格border
const parentBorder = ref(true)
const childBorder = ref(false)

// 分页
const currentPage = ref(4)
const pageSize = ref(20)
const total = ref(400)
const small = ref(false)
const background = ref(false)
const disabled = ref(false)

const handleSizeChange = (val) => {
  // console.log(`${val} items per page`)
  pageSize.value = val
  currentPage.value = 1
}
const handleCurrentChange = (val) => {
  // console.log(`current page: ${val}`)
  currentPage.value = val
}

// 查询表单
const findForm = reactive({
  status:'9',
  sn:'',
  openid:''
})

// 在某一订单状态中按照订单号或者用户id查询订单
const findOrderByStatus = (status) =>{
  orderFormList.length=0
  if(findForm.sn && findForm.openid){
    for(let i = 0;i<orderList.length;i++){
      if(orderList[i].status == status && orderList[i].sn == findForm.sn && orderList[i].openid == findForm.openid){
        orderFormList.push(orderList[i])
      }
    }
  }else if(findForm.sn || findForm.openid){
    for(let i = 0;i<orderList.length;i++){
      if(orderList[i].status == status && (orderList[i].sn == findForm.sn || orderList[i].openid == findForm.openid)){
        orderFormList.push(orderList[i])
      }
    }
  }else{
    for(let i = 0;i<orderList.length;i++){
      if(orderList[i].status == status){
        orderFormList.push(orderList[i])
      }
    }
  }
  total.value = orderFormList.length
}

// 查找订单
const findOrder = () =>{
  orderFormList.length=0
  if(findForm.status == '9'){
    if(findForm.sn && findForm.openid){
      for(let i = 0;i<orderList.length;i++){
        if(orderList[i].sn == findForm.sn && orderList[i].openid == findForm.openid){
          orderFormList.push(orderList[i])
        }
      }
    }else if(findForm.sn || findForm.openid){
      for(let i = 0;i<orderList.length;i++){
        if(orderList[i].sn == findForm.sn || orderList[i].openid == findForm.openid){
          orderFormList.push(orderList[i])
        }
      }
    }else{
      // orderFormList = orderList;
      orderFormList.push(...orderList)
    }
  }else{
    findOrderByStatus(findForm.status)
  }
  total.value = orderFormList.length
}

// 获取订单列表
const getAllOrderList = async()=>{
  const {data:res} = await axios.get('/order/list')
  orderList.push(...res.data)
  orderFormList.push(...res.data)
  total.value = orderFormList.length;
  console.log(orderList);
}

// 时间格式化
const dateFormat = (row, column, cellValue) => {
  if(cellValue){
    return moment(cellValue).format('YYYY-MM-DD HH:mm:ss')
  }else{
    return null
  }
  
}
// 价格格式化
const priceFormat = (row,column,cellValue) => {
  if(cellValue){
    return (cellValue / 100).toFixed(2)
  }else{
    return null
  }
}
// 订单状态格式化
const statusFormat = (row,column,cellValue) =>{
  if(cellValue===0){
    return '待付款'
  }else if(cellValue===1){
    return '已付款'
  }else if(cellValue===2){
    return '已完成'
  }else{
    return '已取消'
  }
}
// 取消类型格式化
const cancelTypeFormat=(row,column,cellValue)=>{
  if(cellValue===0){
    return '超时未支付'
  }else if(cellValue===1){
    return '退款关闭'
  }else{
    return '买家取消'
  }
}

// 计算属性
const compData = computed(()=>{
  return orderFormList.slice((currentPage.value-1)*pageSize.value,currentPage.value*pageSize.value)
})

onMounted(()=>{
  getAllOrderList();
})
</script>

<style lang="scss">
.detail-wrap {
  padding: 20px;
  h3 {
    // text-align: center;
    margin-left: 50px;
    margin-bottom: 10px;
  }
}
.location-wrap {
  display: flex;
  justify-content: center;
  margin-top: 20px;
  .location-item {
    width: 50%;
    h3 {
      // text-align: center;
      margin-left: 50px;
    }
  }
}

.el-pagination{
  margin-top: 20px;
  text-align: center;
}
</style>