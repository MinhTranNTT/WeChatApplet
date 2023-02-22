<template>
  <el-table :data="compData" :border="parentBorder" style="width:100%">
    <el-table-column type="index" label="序号" align="center"></el-table-column>
    <el-table-column prop="name" label="商品名称" align="center" show-overflow-tooltip="true"></el-table-column>
    <el-table-column prop="beginTime" label="开始时间" align="center" :formatter="dateFormat"></el-table-column>
    <el-table-column prop="endTime" label="结束时间" align="center" :formatter="dateFormat"></el-table-column>
    <el-table-column prop="stock" label="总数" align="center"></el-table-column>
    <el-table-column prop="remains" label="剩余可报" align="center" :formatter="remainsFormat"></el-table-column>

    <el-table-column label="操作" align="center">
      <template #default="scope">
        <el-button type="primary" size="small" @click="handleEdit(scope.row)">编辑</el-button>
        <el-button type="danger" size="small" @click="handleDelete(scope.row)">删除</el-button>
      </template>
    </el-table-column>
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
import { onMounted,reactive,ref,computed } from 'vue';
import axios from 'axios';
import moment from 'moment'
import router from '@/router';
import { ElMessage } from 'element-plus';

// 批次列表
var batchList = reactive([]);
// 商品名称及id
var productNameList = reactive([]);
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

// 时间格式化
const dateFormat = (row, column, cellValue) => {
  return moment(cellValue).format('YYYY-MM-DD')
}

// 剩余人数格式化
const remainsFormat = (row,column,cellValue) =>{
  if(cellValue == -1){
    return 0;
  }else{
    return cellValue;
  }
}

// 修改
const handleEdit = (row) => {
  router.push(`/batch/modify/${row.id}`)
  // console.log(row)
}

// 删除
const handleDelete = async(row) => {
  await axios.delete(`/batch/delete/${row.id}`)
  // ElMessage.success('删除成功')
  ElMessage.success('删除成功')
  router.go(0);
}

// 获取批次列表
const getBatchList = async () => {
  const { data: res } = await axios.get('/batch/list');
  batchList.push(...res.data);
  total.value = batchList.length;
  // console.log(batchList);
};

// 从localStorage中获取商品名称及id
const getProductNameList = () => {
  const productList = JSON.parse(localStorage.getItem('productList'));
  for(let i = 0; i< batchList.length;i++){
    for(let j = 0;j < productList.length;j++){
      if(batchList[i].productId == productList[j].id){
        batchList[i].name = productList[j].name;
      }
    }
  }
  // console.log(33333333333);
  localStorage.setItem('batchList',JSON.stringify(batchList))
  // console.log(JSON.parse(localStorage.getItem('batchList')));
  // console.log(productNameList);
}

// 计算属性
const compData = computed(()=>{
  return batchList.slice((currentPage.value-1)*pageSize.value,currentPage.value*pageSize.value)
})

onMounted( async() => {
  await getBatchList();
  getProductNameList();
});
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