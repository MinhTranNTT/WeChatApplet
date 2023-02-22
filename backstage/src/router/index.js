import { createRouter, createWebHistory } from 'vue-router'
import Login from '../views/Login/index.vue'

const routes = [
  {
    path: '/',
    name: 'login',
    hidden: true,
    component: Login,
  },
  {
    path: '/home',
    name: '统计信息',
    redirect: '/static',
    iconCLass: 'fa fa-users',
    component: () => import('../views/Home/index.vue'),
    children: [
      {
        path: '/static',
        name: '数据概览',
        component: () => import('../views/Home/statistic.vue'),
      }
    ]
  },
  {
    path: '/home',
    name: '产品信息',
    // redirect: '/product/list',
    iconCLass: 'fa-brands fa-product-hunt',
    component: () => import('../views/Home/index.vue'),
    children: [
      {
        path: '/product/list',
        name: '产品列表',
        component: () => import('../views/Product/productsList.vue'),
      },
      {
        path: '/product/add',
        name: '添加产品',
        component: () => import('../views/Product/addProduct.vue'),
      },
      {
        path: '/product/modify/:id',
        name: '编辑产品',
        hidden: true,
        component: () => import('../views/Product/modifyProduct.vue'),
      }
    ]
  },
  {
    path: '/home',
    name: '批次信息',
    component: () => import('../views/Home/index.vue'),
    children: [
      {
        path: '/batch/list',
        name: '批次列表',
        component: () => import('../views/Batch/batchList.vue'),
      },
      {
        path: '/batch/add',
        name: '添加批次',
        component: () => import('../views/Batch/addBatch.vue'),
      },
      {
        path: '/batch/modify/:id',
        name: '编辑批次',
        hidden: true,
        component: () => import('../views/Batch/modifyBatch.vue'),
      }
    ]
  },
  {
    path: '/home',
    name: '订单信息',
    component: () => import('../views/Home/index.vue'),
    children: [
      {
        path: '/order/list',
        name: '订单列表',
        component: () => import('../views/Order/orderList.vue'),
      },
      {
        path: '/order/campersList',
        name: '报名营员列表',
        component: () => import('../views/Order/campersList.vue'),
      }
    ]
  },
  {
    path: '/home',
    name: '用户信息',
    component: () => import('../views/Home/index.vue'),
    children: [
      {
        path: '/user/list',
        name: '用户列表',
        component: () => import('../views/User/userList.vue'),
      }
    ]
  }
 
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
