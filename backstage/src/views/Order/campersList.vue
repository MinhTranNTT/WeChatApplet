<template><!-- 查询框 -->
    <div class="find-item">
        <el-form :inline="true" :model="findForm" class="demo-form-inline">
            <el-form-item prop="productId" label="商品名称">
                <el-select v-model="findForm.productId" placeholder="请选择商品名称">
                    <el-option v-for="item in productNameList" :key="item.id" :label="item.name"
                        :value="item.id"></el-option>
                </el-select>
            </el-form-item>

            <el-form-item prop="batchId" label="批次">
                <el-select v-model="findForm.batchId" placeholder="请选择批次">
                    <el-option v-for="item in getProductBatchByProductId" :key="item.id" :label="dateFormat(item.beginTime)"
                        :value="item.id"></el-option>
                </el-select>
            </el-form-item>

            <el-form-item>
                <el-button type="primary" @click="findCampers">查询</el-button>
            </el-form-item>
        </el-form>

        <div class="export-data">
            <el-button type="primary" @click="exportData">导出数据</el-button>
        </div>
    </div>

    <!-- 表格 -->
    <el-table :data="campersList" :border="parentBorder" style="width:100%" id="campersTable">
        <el-table-column type="index" label="序号" align="center"></el-table-column>
        <el-table-column prop="photoUrl" label="照片" align="center">
            <template #default="scope">
                <img :src="scope.row.photoUrl" alt="" style="width:100px;height:100px">
            </template>
        </el-table-column>
        <el-table-column prop="name" label="姓名" align="center"></el-table-column>
        <el-table-column prop="gender" label="性别" align="center" :formatter="genderFormat"></el-table-column>
        <el-table-column prop="birthday" label="出生日期" align="center" :formatter="dateF"></el-table-column>
        <el-table-column prop="validIdNumber" label="有效证件号" align="center" v-if="!validIdNumber"></el-table-column>
        <el-table-column prop="otherIdNumber" label="有效证件号" align="center" v-else></el-table-column>
        <el-table-column prop="phone" label="联系电话" align="center"></el-table-column>
        <el-table-column prop="clothSize" label="衣服尺寸" align="center" :formatter="clothSizeFormat"></el-table-column>
        <el-table-column prop="school" label="学校" align="center"></el-table-column>
        <el-table-column prop="grade" label="年级" align="center"></el-table-column>
    </el-table>
</template>

<script setup>
import { computed, onMounted, reactive, ref } from 'vue'
import axios from 'axios'
import moment from 'moment'
import { ElMessage } from 'element-plus'
import FileSaver from 'file-saver'
import * as XLSX from 'xlsx'

// 每个营期所报营员列表
var campersList = reactive([])
// 商品名称列表
var productNameList = reactive([])
// 商品批次列表
var productBatchList = reactive([])
// 订单列表
var orderList = reactive([])

// 衣服尺寸列表
var clothSize = reactive([110,120,130,140,150,160,170,180,190,200])

// 查找表单
var findForm = reactive({
    productId: undefined,
    batchId: undefined,
})

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

// 获取所有商品的名称
const getallProductName = () => {
    var list = JSON.parse(localStorage.getItem('productList'))
    for (let i = 0; i < list.length; i++) {
        productNameList.push({
            id: list[i].id,
            name: list[i].name,
            endTime: list[i].endTime
        })
    }
}

// 时间格式化
const dateFormat = (cellValue) => {
    return moment(cellValue).format('YYYY-MM-DD')
}

const dateF = (row,column,cellValue) =>{
    return moment(cellValue).format('YYYY年MM月DD日')
}

// 性别格式化
const genderFormat = (row,column,cellValue) =>{
    if(cellValue == 0){
        return '男'
    }else{
        return '女'
    }
}

// 衣服尺寸格式化
const clothSizeFormat = (row,column,cellValue) =>{
    return clothSize[cellValue]
}

// 导出数据
const exportData = () =>{
    var wb = XLSX.utils.table_to_book(document.querySelector('#campersTable'));
    var wbout = XLSX.write(wb,{bookType:'xlsx',bookSST:true,type:'array'});
    try {
        FileSaver.saveAs(new Blob([wbout],{type:'application/octet-stream'}), '营员信息.xlsx');
    }catch (e) {
        if (typeof console !== 'undefined') console.log(e, wbout);
    }
}

// 查找所有符合条件的营员
const findCampers = () =>{
    if(findForm.productId == undefined || findForm.batchId == undefined){
        ElMessage.error('请选择商品名称和批次')
        return
    }else{
        campersList.length = 0;
        for (let i = 0; i<orderList.length;i++){
            if(orderList[i].productId == findForm.productId && orderList[i].camperInfo.id == findForm.batchId){
                var arr = orderList[i].camperList;
                for(let j = 0;j<arr.length;j++){
                    campersList.push(arr[j])
                }
            }
        }
    }
    console.log(campersList);
}


// 获取订单列表
const getAllOrderList = async () => {
    const { data: res } = await axios.get('/order/list')
    orderList.push(...res.data)
    console.log(orderList);
}


// 根据商品id获取商品批次
const getProductBatchByProductId = computed(() => {
    productBatchList.length = 0;
    var list = JSON.parse(localStorage.getItem('batchList'))
    for (let i = 0; i < list.length; i++) {
        if (list[i].productId == findForm.productId) {
            productBatchList.push({
                id: list[i].id,
                beginTime: list[i].beginTime
            })
        }
    }
    return productBatchList;
})

onMounted(async() => {
    await getAllOrderList();
    getallProductName();
})

</script>

<style lang="scss">
.find-item{
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    
    .export-data{
        margin-right: 20px;
    }
}
</style>