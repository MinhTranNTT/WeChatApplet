
<template>
  <!-- 产品列表 -->
  <el-table :data="compData" :border="parentBorder" style="width:100%">
    <el-table-column type="expand">
      <template #default="props">
        <div class="detail-wrap">
          <h3>营期信息</h3>
          <el-table :data="props.row.batchList" :border="childBorder" style="width:70%;margin-left: 50px;">
            <el-table-column label="开始日期" prop="beginTime" :formatter="dateFormat" align="center"></el-table-column>
            <el-table-column label="结束日期" prop="endTime" :formatter="dateFormat" align="center"></el-table-column>
            <el-table-column label="库存" prop="stock" align="center"></el-table-column>
            <el-table-column label="总数" prop="stock" align="center"></el-table-column>
          </el-table>
          <div class="location-wrap">
            <div class="location-item">
              <h3>启程地点</h3>
              <el-table :data="props.row.departureMeetingPoint" :border="childBorder" style="width:20%;margin-left: 50px;">
                <el-table-column prop="location" label="地点" align="center"></el-table-column>
              </el-table>
            </div>

            <div class="location-item">
              <h3>返程地点</h3>
              <el-table :data="props.row.returnMeetingPoint" :border="childBorder" style="width:20%;margin-left: 50px;">
                <el-table-column prop="location" label="地点" align="center"></el-table-column>
              </el-table>
            </div>
          </div>

        </div>
      </template>
    </el-table-column>
    <el-table-column type="index" label="序号" align="center"></el-table-column>
    <el-table-column prop="name" label="商品名称" align="center" show-overflow-tooltip="true"></el-table-column>
    <el-table-column prop="price" label="商品价格" align="center"></el-table-column>
    <el-table-column prop="categoryName" label="商品类别" align="center"></el-table-column>
    <el-table-column prop="description" label="商品描述" align="center" show-overflow-tooltip="true"></el-table-column>
    <el-table-column prop="minAge" label="最小年龄" align="center"></el-table-column>
    <el-table-column prop="maxAge" label="最大年龄" align="center"></el-table-column>
    <el-table-column prop="productsOrder" label="产品顺序" align="center"></el-table-column>
    <el-table-column prop="isActivate" label="是否显示" align="center">
      <template #default="scope">
        <span v-if="scope.row.isActivate==0">未展示</span>
        <span v-else>正常展示</span>
      </template>
    </el-table-column>

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

<script>
import axios from 'axios'
import { computed, onMounted, reactive, ref } from 'vue'
import moment from 'moment'
import { ElMessage } from 'element-plus'
import router from '@/router'
export default {
  setup() {
    // 商品列表
    var productList = reactive([])
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

    // 获取商品列表
    const getProductsList = async () => {
      const { data: res } = await axios.get('/products/list')
      // productList = res.data;
      productList.push(...res.data)
      total.value = productList.length
      // console.log(total)
      // console.log(res)
      // console.log(productList)
    
    }

    // 时间格式化
    const dateFormat = (row, column, cellValue) => {
      return moment(cellValue).format('YYYY-MM-DD')
    }

    // 编辑
    const handleEdit = (row) => {
      router.push(`/product/modify/${row.id}`)
      console.log(row)
    }

    // 删除
    const handleDelete = async(row) => {
      await axios.delete(`/products/delete/${row.id}`)
      ElMessage.success('删除成功')
      router.go(0);
    }

    // onMounted
    onMounted(() => {
      getProductsList()
    })

    // 计算属性
    const compData = computed(()=>{
      return productList.slice((currentPage.value-1)*pageSize.value,currentPage.value*pageSize.value)
    })

    return{getProductsList,productList,parentBorder,childBorder,dateFormat,handleEdit,handleDelete,currentPage,pageSize,total,handleSizeChange,handleCurrentChange,small,background,disabled,compData}
  }

}
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