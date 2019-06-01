<template>
  <div class="bookinstance">
    <div style="height: 3em"></div>
    <div class="inner">
      <p style="margin-bottom: 2em">所有书籍 > {{ category_list[category] }} > {{ title }}</p>
      <div class="book">
        <div class="image">
          <img :src="imagelink">
        </div>
        <div class="description">
          <div class="part1">
            <a class="title">{{ title }}</a>
            <a class="comment">{{ (intro == '') ? "本书暂无简介！" : intro}}</a>
          </div>
          <a class="author">作者：{{ author }}</a>
          <a class="price">￥{{ (price * amount).toFixed(2) }}</a>
          <a class="stock">库存{{ stock }}本</a>
          <div class="part2">
            <button @click="amount--">-</button>
            <input v-model="amount">
            <button @click="amount++">+</button>
            <div class="buy" @click="buy">立即购买</div>
            <div class="cart" @click="cart">加入购物车</div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
/* eslint-disable */
import axios from "axios";
export default {
  name: "bookdetail",
  components: {},
  data() {
    return {
      amount: 1,
      isbn: 0,
      category_list: ["未分类", "教材", "其他书籍"],
      title: "",
      intro: "",
      author: "",
      price: 0,
      category: 0,
      picpath: "",
      stock: 0,
      old_amount: 1,
      imagelink: ""
    };
  },
  watch: {
    amount: function(val) {
      if (val < 1) {
        this.amount = 1;
      } else if (val > this.stock) {
        this.amount = this.stock;
      } else if (isNaN(val)) {
        this.amount = this.old_amount;
        return;
      } else if (this.amount.toString().indexOf(".") != -1) {
        this.amount = this.old_amount;
        return;
      }
      this.old_amount = this.amount;
    }
  },
  created: function() {
    let that = this;
    that.isbn = that.$route.params.isbn;
    axios
      .get("http://localhost:8080/getbook/?isbn=" + that.isbn)
      .then(function(response) {
        that.title = response.data.title;
        that.intro = response.data.intro;
        that.author = response.data.author;
        that.price = response.data.price;
        that.category = response.data.category;
        that.imagelink = response.data.imagelink;
        that.stock = response.data.stock;
      }).catch(function (error) {
        window.location.href="#/404";
      });
  },
  methods: {
    cartadd() {
      if (isNaN(this.amount) || this.amount < 1) {
        window.alert("请输入正确的数量！");
        return false;
      }
      axios.post("http://localhost:8080/cartadd?isbn=" + this.isbn + "&amount=" + this.amount).then(function(response) {
        if (response.data.status == "unlogin") {
          window.alert("请登录后再进行此操作");
          window.location.href="#/login";
          return false;
        }
      })
      return true;
    },
    buy() {
      if (this.cartadd()) window.location.href="#/cart";
    },
    cart() {
      if (this.cartadd()) window.alert("已添加，在购物车等您~");
    }
  },
};
</script>

