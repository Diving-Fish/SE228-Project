<template>
  <div>
    <header id="globalHeader">
      <link type="text/css" rel="styleSheet" href="index.css">
      <div class="inner">
        <div class="left">
          <router-link to="/" class="logo">
            <strong>E-book</strong>
          </router-link>
          <router-link to="/booklist">所有书籍</router-link>
          <router-link to="/booklist?category=1">教材</router-link>
          <router-link to="/booklist?category=2">其他书籍</router-link>
        </div>
        <div class="right">
          <router-link to="/login" v-show="this.userinfo.status != 200">登录</router-link>
          <router-link to="/register" v-show="this.userinfo.status != 200">注册</router-link>
          <a v-show="this.userinfo.status == 200">你好，{{ userinfo.username }}</a>
          <router-link to="/cart" v-show="this.userinfo.status == 200">购物车</router-link>
          <router-link to="/order" v-show="this.userinfo.status == 200">我的订单</router-link>
          <router-link to="/orderstatistic" v-show="this.userinfo.status == 200 && this.userinfo.role == 0">订单统计</router-link>
          <router-link to="/manage" v-show="this.userinfo.status == 200 && this.userinfo.role == 0">管理系统</router-link>
          <a class="pointer" @click="logout" v-show="this.userinfo.status == 200">登出</a>
        </div>
      </div>
    </header>
    <router-view/>
  </div>
</template>


<script>
import axios from 'axios';
export default {
  watch: {
    '$route' (to, from) {
      if (to.path == "/manage" || to.path == "/managebook" || to.path == "/orderstatistic") {
        if (this.userinfo.role != 0) window.location.href = "#/404";
      }
    }
  },
  data () {
    return {
      userinfo: {},
    }
  },
  created: function() {
    let that = this;
    axios.get("http://localhost:8080/userinfo").then(function (response) {
      that.userinfo = response.data;
    });
  },
  methods: {
    logout() {
      axios.post("http://localhost:8080/logout");
      alert("登出成功！");
      window.location.href = "#/";
      window.location.reload();
    }
  }
}
</script>
