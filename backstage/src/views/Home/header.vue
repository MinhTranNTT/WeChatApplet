<template>
  <div class="header-wrap">
    <div class="name">信游研学管理后台</div>
    <div class="user-name">{{ userInfo }}</div>
    <div></div>
  </div>
</template>

<script>
import { onMounted, reactive, ref } from '@vue/runtime-core';
export default {
    name: 'Header',
    setup(){
        const userInfo = ref();


        onMounted(()=>{
            userInfo.value = localStorage.getItem('userName')
            // console.log(userInfo);
        })

        return {userInfo}
    }

}
</script>

<style lang="scss" scoped>
.header-wrap{
    // width: 100%;
    height: 100%;
    display: flex;
    flex-direction: row;
    align-items: center;
    background-color: #2578b5;
    .name{
        margin-right: auto;
        margin-left: 10px;
        text-align: center;
        font-size: 24px;
        font-weight: bold;
    }
    .user-name{
        text-align: center;
        font-size: 15px;
        font-weight: bold;
        margin-left: auto;
        margin-right: 20px;
    }
}
</style>