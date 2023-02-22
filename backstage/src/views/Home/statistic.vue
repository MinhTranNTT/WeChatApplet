<template>
  <div class="data-view">
    <el-card>
      <div id="main1"></div>
    </el-card>
  </div>
</template>

<script setup>
import { onMounted, reactive } from "vue";
import axios from "axios";
import * as echarts from "echarts";

// 绘制图表
const drawChart = () => {
  // 基于准备好的dom，初始化echarts实例
  var myChart = echarts.init(document.getElementById("main1"));
  // 绘制图表
  myChart.setOption({
    title: {
      text: "商品销量统计",
    },
    tooltip: {},
    legend: {
      data: ["销量"],
    },
    xAxis: {
      data: productNameList,
      name:'商品名称',
      nameLocation: 'end',
      axisLabel: {
        interval: 0,
        rotate: 40
      }
    },
    yAxis: {},
    series: [
      {
        name: "销量",
        type: "bar",
        data: productSalesList,
      },
    ],
  });
};

// 商品列表
var productList = reactive([]);
// 订单列表
var orderList = reactive([]);
// 商品名称列表
var productNameList = [];
var productIdAndNameList = reactive([]);
// 商品销量列表
var productSalesList = [];

// 获取商品列表
const getProductsList = async () => {
  const { data: res } = await axios.get('/products/list')
  productList.push(...res.data)
  // 将商品列表存入localStorage
  localStorage.setItem('productList', JSON.stringify(productList))
  for(let i = 0;i<productList.length;i++){
    productNameList.push(productList[i].name)
    productIdAndNameList.push({
      id: productList[i].id,
      name: productList[i].name,
      count: 0
    })
  }
}

// 获取订单列表
const getOrdersList = async () => {
  var arr = []
  productSalesList = new Array(productNameList.length).fill(0)
  const { data: res } = await axios.get('/order/list')
  arr.push(...res.data)
  for(let i = 0;i<arr.length;i++){
    for(let j = 0;j<productIdAndNameList.length;j++){
      if(arr[i].productId == productIdAndNameList[j].id){
        productIdAndNameList[j].count += arr[i].productCount
      }
    }
  }
  for(let i = 0;i<productIdAndNameList.length;i++){
    productSalesList[i] = productIdAndNameList[i].count
  }
  console.log(productSalesList);
}



onMounted(async() => {
  await getProductsList();
  await getOrdersList();
  drawChart();
});
</script>

<style lang="scss">
.data-view{
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  #main1{
    width: 1000px;
    height: 500px;
  }
}
</style>