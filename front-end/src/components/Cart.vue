<template>
  <div class="cart">
    <div style="height: 3em"></div>
    <div class="header">购物车</div>
    <div class="itemelem" v-for="item in cart" :key="item.isbn">
      <div class="pic"><img :src="item.imagelink" style="width: 120px; height: 120px"></div>
      <div>
        <div class="title">{{ item.title }}</div>
        <div style="display: flex; font-size: 18px" class="operate">
          购买数量：
          <button @click="subitem(item)" class="amount">-</button>
            <input v-model="item.amount">
          <button @click="additem(item)" class="amount">+</button>
          <button class="delete" @click="delitem(item)">删除</button>
        </div>
      </div>
      <div class="price">￥{{ (item.amount * item.price).toFixed(2) }} </div>
    </div>
    <p style="margin-left: 30px; font-size: 20px" v-show="cart.length == 0">购物车为空~</p>
    <div style="display: flex">
      <p class="allprice" v-show="cart.length != 0">总价：￥{{ allprice }}</p>
      <button class="submit" @click="submit" v-show="cart.length != 0">提交</button>
    </div>
  </div>
</template>

<script>
/* eslint-disable */
import axios from "axios";
export default {
  name: "cart",
  data() {
    return {
      cart: []
    };
  },
  created: function() {
    let that = this;
    axios
      .get("http://localhost:8080/getcart")
      .then(function(response) {
        that.cart = response.data.cart;
      }).catch(function (error) {
        
      });
  },
  computed: {
    allprice: function() {
      var p = 0;
      for (let item of this.cart) {
        p += item.amount * item.price;
      }
      return p.toFixed(2);
    }
  },
  methods: {
    additem(item) {
      axios.post("http://localhost:8080/cartadd?isbn=" + item.isbn + "&amount=1");
      item.amount++;
    },
    subitem(item) {
      if (item.amount == 1) return;
      axios.post("http://localhost:8080/cartadd?isbn=" + item.isbn + "&amount=-1");
      item.amount--;
    },
    delitem(item) {
      axios.post("http://localhost:8080/cartdelete?isbn=" + item.isbn);
      this.cart.splice(this.cart.indexOf(item), 1)
    },
    submit() {
      let that = this;
      axios.post("http://localhost:8080/submitorder", {
        cart: that.cart
      }).then(function(response) {
        if (response.data.status = 200) {
          window.alert("购买成功！可前往订单管理查看已购买的书籍~");
          axios.post("http://localhost:8080/emptycart");
          window.location.href = "#/order";
        }
      })
    }
  }
};
</script>
