import Vue from 'vue'
import Router from 'vue-router'
import index from './components/Index.vue'
import login from './components/Login.vue'
import booklist from './components/Booklist.vue'
import bookdetail from './components/BookDetail.vue'
import notfound from './components/NotFound.vue'
import register from './components/Register.vue'
import manage from './components/Manage.vue'
import managebook from './components/ManageBook.vue'
import cart from './components/Cart.vue'
import order from './components/Order.vue'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'home',
      component: index
    },
    {
      path: '/login',
      name: 'login',
      component: login
    },
    {
      path: '/register',
      name: 'register',
      component: register
    },
    {
      path: '/booklist',
      name: 'booklist',
      component: booklist
    },
    {
      path: '/book/:isbn',
      name: 'bookdetail',
      component: bookdetail
    },
    {
      path: '/cart',
      name: 'cart',
      component: cart
    },
    {
      path: '/order',
      name: 'order',
      component: order
    },
    {
      path: '/manage',
      name: 'manage',
      component: manage
    },
    {
      path: '/manage/book/:isbn',
      name: 'managebook',
      component: managebook
    },
    {
      path: '*',
      name: '404',
      component: notfound
    }
  ]
})
