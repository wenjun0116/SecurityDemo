import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

export const constantRouters = [
  {
    path: '/',
    name: 'HelloWorld',
    component: (resolve) => require(['@/components/HelloWorld'], resolve),
  },
  {
    path: '/login',
    name: 'login',
    component: (resolve) => require(['@/views/login'], resolve),
  },
  {
    path: '/registry',
    name: 'registry',
    component: (resolve) => require(['@/views/registry'], resolve),
  }
]

export default new Router({
  mode: 'history', // 去掉url中的#
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouters
})
