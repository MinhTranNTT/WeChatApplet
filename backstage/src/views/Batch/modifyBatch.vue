<template>
    <div class="product-form">
        <el-form :model="batchInfo" :rules="batchRules" ref="batchForm" label-width="120px" class="login-form"
            size="default">
            <el-form-item prop="name" label="商品名称">
                <el-input v-model="batchInfo.name" disabled="true"></el-input>
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

            <el-form-item prop="remains" label="剩余可报">
                <el-input-number v-model="batchInfo.remains" :min="-1"></el-input-number>
            </el-form-item>

            <div class="commit">
                <el-button class="commit-button" type="primary" @click="modifyBatch">修 改</el-button>
            </div>
        </el-form>
    </div>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue';
import router from '@/router';
import axios from 'axios';
import { ElMessage } from 'element-plus';

// 批次信息
var batchInfo = reactive({
    id:undefined,
    productId: undefined,
    name: '',
    beginTime: undefined,
    endTime: undefined,
    stock: undefined,
    remains: undefined
});
// 表单ref
const batchForm = ref('null')

// 批次表单验证规则
const batchRules = reactive({
    name:[{ required: true, message: '请输入商品名称', trigger: 'blur' }],
    beginTime:[{required:true,message:'请选择开始日期',trigger:'blur'}],
    endTime:[{required:true,message:'请选择结束日期',trigger:'blur'}],
    stock:[{required:true,message:'请输入可报总人数',trigger:'blur'}]
})

// 读取localStorage 获取BatchList
const getBatchListFromLocalStorage = (id) => {
    var batchList = JSON.parse(localStorage.getItem('batchList'))
    for (let i = 0; i < batchList.length; i++) {
        if (id == batchList[i].id) {
            batchInfo.id = id;
            batchInfo.productId = batchList[i].productId
            batchInfo.name = batchList[i].name;
            batchInfo.beginTime = batchList[i].beginTime;
            batchInfo.endTime = batchList[i].endTime;
            batchInfo.stock = batchList[i].stock;
            batchInfo.remains = batchList[i].remains;
        }
    }
    // console.log(batchInfo);
}

// 提交表单
const modifyBatch = async() =>{
    try{
        batchForm.value.validate((valid)=>{
            if(!valid){
                ElMessage.error('请完善信息')
                return false
            }
            axios.put('/batch/update',batchInfo)
            ElMessage.success('修改成功')
            router.push('/batch/list')
        })
    }catch(error){
        console.log(error);
    }
}

onMounted(() => {
    // console.log(router.currentRoute.value.params.id);
    var id = router.currentRoute.value.params.id;
    getBatchListFromLocalStorage(id);
})
</script>

<style lang="scss">
.product-form {
    width: 80%;
    margin: 0 auto;
}

.avatar {
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

.add-location {
    border: #8c939d 1px dashed;
    border-radius: 6px;
    height: 70px;
    width: 70px;
    text-align: center;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    color: #8c939d;
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