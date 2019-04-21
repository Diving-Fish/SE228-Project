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
