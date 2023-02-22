
<template>
    <div class="menu">
        <el-aside width="200px">
            <el-menu active-text-color="#ffd04b" background-color="#2578b5" class="el-menu-vertical-demo"
                default-active="2" text-color="#fff" @open="handleOpen" @close="handleClose" router>
                <template v-for="(item,index) in menus" :key="index">
                    <el-sub-menu :index="index + ''" v-if="!item.hidden">
                        <template #title>
                            <!-- <i :class="item.iconClass"></i> -->
                            <span>{{ item.name }}</span>
                        </template>
                        <el-menu-item-group v-for="(child,index) in item.children" :key="index">
                            <el-menu-item :index="child.path" v-if="!child.hidden" >{{ child.name }}</el-menu-item>
                        </el-menu-item-group>
                    </el-sub-menu>
                </template>

            </el-menu>
        </el-aside>
    </div>
</template>

<script>
import { onMounted, reactive } from '@vue/runtime-core'
import { useRouter } from 'vue-router'
export default {
    setup() {
        const handleOpen = (key, keyPath) => {
            // console.log(key, keyPath)
        }
        const handleClose = (key, keyPath) => {
            // console.log(key, keyPath)
        }
        // 获取路由
        const router = useRouter()
        // 定义路由列表
        const menus = reactive([]);

        onMounted(() => {
            // console.log(router.options.routes)
            menus.push(...router.options.routes)
        })

        return { handleOpen, handleClose ,menus}
    }
}
</script>

<style lang="scss">
.menu{
    height: 100%;

    .el-aside{
        height: 100%;

        .el-menu{
            height: 100%;
        }
    }
}
</style>>