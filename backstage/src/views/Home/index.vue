<template>
  <!-- 顶部header，左侧menu，右边content，下边footer -->
  <div class="home-wrap">
    <el-container class="main-container">
      <div class="header">
        <Header></Header>
      </div>
      <el-container class="down-container">
        <el-aside width="200px">
            <Menu></Menu>
        </el-aside>
        <el-container class="right-container">
          <el-main>
            <Bread></Bread>
            <div class="router">
              <router-view></router-view>
            </div>
          </el-main>
          <el-footer>
            <Footer></Footer>
          </el-footer>
        </el-container>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import Header from '@/views/Home/header';
import Menu from '@/views/Home/menu';
import Content from '@/views/Home/content';
import Footer from '@/views/Home/footer';
import Bread from '@/common/breadCrumb.vue'
export default {
    components: {
        Header,
        Menu,
        Content,
        Footer,
        Bread
    },
    setup() {
      
        return {}
    }
}
</script>

<style lang="scss" scoped>
.home-wrap{
    width: 100%;
    height: 100%;
    .main-container{
        height: 100%;
        width: 100%;
        display: flex;
        flex-direction: column;

        .header{
            height: 50px;
            width: 100%;
        }

        .down-container{
            display: flex;
            flex-direction: row;
            // height: 100%;
            position: absolute;
            top: 50px;
            bottom: 0;
            left: 0;
            right: 0;
        }

        .right-container{
          .router{
            margin: 20px 0;
          }
        }
    }
}


</style>>
