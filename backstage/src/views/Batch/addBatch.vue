<template>
    <div class="product-form">
        <el-form :model="batchInfo" :rules="batchRules" ref="batchForm" label-width="120px" class="login-form"
            size="default">
            <el-form-item prop="name" label="商品名称">
                <el-select v-model="batchInfo.productId" placeholder="请选择商品类别" style="width:400px">
                    <el-option v-for="item in productNameList" :key="item.id" :label="item.name" :value="item.id">
                    </el-option>
                </el-select>
            </el-form-item>

            <el-form-item prop="beginTime" label="开始日期">
                <el-date-picker v-model="batchInfo.beginTime" type="date" placeholder="请选择营期开始日期"></el-date-picker>
            </el-form-item>

            <el-form-item prop="endTime" label="结束日期">
                <el-date-picker v-model="batchInfo.endTime" type="date" placeholder="请选择营期结束日期"></el-date-picker>
            </el-form-item>

            <el-form-item prop="stock" label="总人数">
                <el-input-number v-model="batchInfo.stock" :min="1"></el-input-number>
            </el-form-item>

            <div class="commit">
                <el-button class="commit-button" type="primary" @click="addBatch">提 交</el-button>
            </div>
        </el-form>
    </div>
</template>

<script setup>
import router from '@/router'
import axios from 'axios'
import { ElMessage } from 'element-plus'
import { onMounted, reactive,ref } from 'vue'

// 批次信息
var batchInfo = reactive({
    productId: undefined,
    beginTime: undefined,
    endTime: undefined,
    stock: 1,
    remains:-1
})

const batchForm = ref('null')

// 所有商品名称
var productNameList = reactive([])

// 获取所有商品的名称和id
const getAllProductNameAndId = () => {
    var productList = JSON.parse(localStorage.getItem('productList'))
    for (let i = 0; i < productList.length; i++) {
        productNameList.push(
            {
                id: productList[i].id,
                name: productList[i].name
            }
        )
    }
}

// 提交表单
const addBatch = () =>{
    try{
        batchForm.value.validate((valid)=>{
            if(!valid){
                ElMessage.error('请根据提示完善信息');
                return false;
            }
            batchInfo.remains = batchInfo.stock;
            axios.post('/batch/add',batchInfo);
            ElMessage.success('添加成功')
            router.push('/batch/list')
        })
    }catch(error){
        ElMessage.error('出问题啦！')
    }
}

onMounted(() => {
    getAllProductNameAndId();
})
</script>

<style lang="scss">
.product-form {
    width: 80%;
    margin: 0 auto;
}

.commit {
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

    .commit-button {
        width: 100px;
        height: 40px;
    }
}
</style>