<template>
  <div>
    <el-card>
        <el-breadcrumb :separator-icon="ArrowRight">
            <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item v-for="(item,index) in router.matched" :key="index">{{ item.name }}</el-breadcrumb-item>
        </el-breadcrumb>
    </el-card>
  </div>
</template>

<script>
import { onMounted } from '@vue/runtime-core';
import { useRoute } from 'vue-router';
export default {
    setup(){
        const router = useRoute();

        return { router }
    }

}
</script>

<style>

</style>