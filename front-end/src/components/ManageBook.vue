<template>
  <div class="manage">
    <div style="height: 3em"></div>
    <div class="header">
      <a style="color: #1a66b3">书籍管理</a>
      <a style="color: grey">用户管理</a>
    </div>
    <div class="bookmodifier">
        <img :src="imagelink" style="width: 150px; height: 150px; margin: 30px 0px 0px 90px;">
      <div class="input-box">
        <span>ISBN：</span>
        <input type="number" v-model="isbn">
      </div>
      <div class="input-box">
        <span>书名：</span>
        <input type="text" v-model="title">
      </div>
      <div class="input-box">
        <span>作者：</span>
        <input type="text" v-model="author">
      </div>
      <div class="input-box">
        <span>封面：</span>
        <input type="text" v-model="imagelink">
      </div>
      <div class="input-box">
        <span>库存：</span>
        <input type="number" v-model="stock">
      </div>
      <div class="input-box">
        <span>价格：</span>
        <input type="number" v-model="price">
      </div>
      <div class="input-box">
        <span>分类：</span>
        <select v-model="category" class="selected_category">
          <option v-for="c in category_list" :value="c.value" :key="c.value">{{ c.name }}</option>
        </select>
      </div>
      <div class="input-box">
        <span>介绍：</span>
        <textarea type="text" v-model="intro"></textarea>
      </div>
      <button class="submit" @click="submit">提交</button>
    </div>
  </div>
</template>

<script>
/* eslint-disable */
import axios from "axios";
export default {
  name: "managebook",
  components: {},
  data() {
    return {
      selecting: "book",
      isbn: 0,
      category: 0,
      category_list: [
        { name: "未分类", value: 0 },
        { name: "教材", value: 1 },
        { name: "其他书籍", value: 2 }
      ],
      title: "",
      intro: "",
      author: "",
      price: 0,
      imagelink: "",
      stock: 0,
    };
  },
  created: function() {
    let that = this;
    that.isbn = that.$route.params.isbn;
    if (that.isbn == "new") {
        that.isbn = 0;
        return;
    }
    axios
      .get("http://localhost:8080/getbook/?isbn=" + that.isbn)
      .then(function(response) {
        that.title = response.data.title;
        that.intro = response.data.intro;
        that.author = response.data.author;
        that.price = response.data.price;
        that.category = response.data.category;
        that.stock = response.data.stock;
        that.imagelink = response.data.imagelink;
      }).catch(function (error) {
        window.location.href="#/404";
      });
  },
  methods: {
    submit() {
    axios.post("http://localhost:8080/modifybook", {
        isbn: this.isbn,
        title: this.title,
        author: this.author,
        stock: this.stock,
        category: this.category,
        intro: this.intro,
        price: this.price,
        imagelink: this.imagelink
    }).then(response => {
        window.location.href = "#/manage";
        window.location.reload();
    }).catch(error => {

    })
    }
  }
};
</script>
