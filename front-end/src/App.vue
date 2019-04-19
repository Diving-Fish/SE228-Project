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
          <router-link to="/login">登录</router-link>
          <router-link to="/register">注册</router-link>
          <router-link to="/cart">购物车</router-link>
        </div>
      </div>
    </header>
    <router-view/>
  </div>
</template>
