import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

// 解决导航栏或者底部导航tabBar中的vue-router在3.0版本以上频繁点击菜单报错的问题。
const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push (location) {
  return originalPush.call(this, location).catch(err => err)
}

const routes = [
  {
    path: '/',
    name: 'Manager',
    component: () => import('../views/Manager.vue'),
    redirect: '/login',  // 重定向到主页
    children: [
      { path: '403', name: 'NoAuth', meta: { name: '无权限' }, component: () => import('../views/manager/403') },
      { path: 'home', name: 'Home', meta: { name: '系统首页' }, component: () => import('../views/manager/Home') },
      { path: 'admin', name: 'Admin', meta: { name: '管理员信息' }, component: () => import('../views/manager/Admin') },
      { path: 'adminPerson', name: 'AdminPerson', meta: { name: '个人信息' }, component: () => import('../views/manager/AdminPerson') },
      { path: 'password', name: 'Password', meta: { name: '修改密码' }, component: () => import('../views/manager/Password') },
      { path: 'notice', name: 'Notice', meta: { name: '公告信息' }, component: () => import('../views/manager/Notice') },
      { path: 'user', name: 'User', meta: { name: '用户信息' }, component: () => import('../views/manager/User') },
      { path: 'category', name: 'Category', meta: { name: '分类信息' }, component: () => import('../views/manager/Category') },
      { path: 'goods', name: 'Goods', meta: { name: '商品信息' }, component: () => import('../views/manager/Goods') },
      { path: 'circles', name: 'Circles', meta: { name: '圈子信息' }, component: () => import('../views/manager/Circles') },
      { path: 'posts', name: 'Posts', meta: { name: '帖子信息' }, component: () => import('../views/manager/Posts') },
      { path: 'help', name: 'Help', meta: { name: '求助信息' }, component: () => import('../views/manager/Help') },
      { path: 'address', name: 'Address', meta: { name: '地址信息' }, component: () => import('../views/manager/Address') },
      { path: 'feedBack', name: 'FeedBack', meta: { name: '反馈信息' }, component: () => import('../views/manager/FeedBack') },
      { path: 'comment', name: 'Comment', meta: { name: '评论信息' }, component: () => import('../views/manager/Comment') },
      { path: 'orders', name: 'Orders', meta: { name: '订单信息' }, component: () => import('../views/manager/Orders') },
    ]
  },
  {
    path: '/front',
    name: 'Front',
    component: () => import('../views/Front.vue'),
    children: [
      { path: 'home', name: 'Home', component: () => import('../views/front/Home') },
      { path: 'person', name: 'Person', component: () => import('../views/front/Person') },
      { path: 'goodsDetail', name: 'GoodsDetail', component: () => import('../views/front/GoodsDetail') },
      { path: 'collect', name: 'Collect', component: () => import('../views/front/Collect') },
      { path: 'notice', name: 'Notice', component: () => import('../views/front/Notice') },
      { path: 'addGoods', name: 'AddGoods', component: () => import('../views/front/AddGoods') },
      { path: 'goods', name: 'Goods', component: () => import('../views/front/Goods') },
      { path: 'address', name: 'Address', component: () => import('../views/front/Address') },
      { path: 'orders', name: 'Orders', component: () => import('../views/front/Orders') },
      { path: 'posts', name: 'Posts', component: () => import('../views/front/Posts') },
      { path: 'userPosts', name: 'UserPosts', component: () => import('../views/front/UserPosts') },
      { path: 'postsDetail', name: 'PostsDetail', component: () => import('../views/front/PostsDetail') },
      { path: 'help', name: 'Help', component: () => import('../views/front/Help') },
      { path: 'userHelp', name: 'UserHelp', component: () => import('../views/front/UserHelp') },
      { path: 'feedBack', name: 'FeedBack', component: () => import('../views/front/FeedBack') },
      { path: 'userFeedBack', name: 'UserFeedBack', component: () => import('../views/front/UserFeedBack') },
      { path: 'chat', name: 'Chat', component: () => import('../views/front/Chat') },
    ]
  },
  { path: '/login', name: 'Login', meta: { name: '登录' }, component: () => import('../views/Login.vue') },
  { path: '/register', name: 'Register', meta: { name: '注册' }, component: () => import('../views/Register.vue') },
  { path: '*', name: 'NotFound', meta: { name: '无法访问' }, component: () => import('../views/404.vue') },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

// 注：不需要前台的项目，可以注释掉该路由守卫
// 路由守卫
// router.beforeEach((to ,from, next) => {
//   let user = JSON.parse(localStorage.getItem("xm-user") || '{}');
//   if (to.path === '/') {
//     if (user.role) {
//       if (user.role === 'USER') {
//         next('/front/home')
//       } else {
//         next('/home')
//       }
//     } else {
//       next('/login')
//     }
//   } else {
//     next()
//   }
// })

export default router
