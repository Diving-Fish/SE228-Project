<template>
  <div class="manage">
    <div style="height: 3em"></div>
    <div class="header">
      <a :style="(selecting == 'book') ? 'color: #1a66b3' : 'color: grey'">书籍管理</a>
      <a :style="(selecting == 'user') ? 'color: #1a66b3' : 'color: grey'">用户管理</a>
    </div>
    <div v-show="selecting == 'book'" class="bookmanager">
      <div style="margin-left: 2em; padding: 5px; margin: 20px">
        分类：
        <select v-model="selected_category" class="selected_category">
          <option v-for="c in category_list" :value="c.value" :key="c.value">{{ c.name }}</option>
        </select>
        <input
          class="search"
          name="text"
          type="search"
          aria-label="查找书籍……"
          placeholder="查找书籍……"
          v-model="searchName"
        >
      </div>
      <div class="sort">
        <div>排序：</div>
        <div @click="setSortType(1)">
          价格
          <img :src="price_sortimage" style="width: 12px; height: 12px; margin-left: 3px;">
        </div>
        <div @click="setSortType(3)">
          库存
          <img :src="stock_sortimage" style="width: 12px; height: 12px; margin-left: 3px;">
        </div>
        <button class="create"><router-link to="/manage/book/new" style="text-decoration: none; color: white">添加新书</router-link></button>
      </div>
      <div class="bookelem"></div>
      <div class="bookelem">
        <div class="isbn">ISBN</div>
        <div class="title">书名</div>
        <div class="author">作者</div>
        <div class="pic">封面</div>
        <div class="stock">库存</div>
        <div class="category">分类</div>
        <div class="button">操作</div>
      </div>
      <div class="bookelem" v-for="book in filter_book" :key="book.isbn">
        <div class="isbn">{{ book.isbn }}</div>
        <div class="title">{{ book.title }}</div>
        <div class="author">{{ book.author }}</div>
        <div class="pic"><img :src="book.picpath" style="width: 80px; height: 80px"></div>
        <div class="stock">{{ book.stock }}</div>
        <div class="category">{{ category_list[book.category].name }}</div>
        <div class="button">
          <button class="modify"><router-link :to="'/manage/book/' + book.isbn" style="text-decoration: none; color: white">编辑本书</router-link></button>
          <button class="delete">删除本书</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
/* eslint-disable */
import axios from "axios";
export default {
  name: "manage",
  components: {},
  data() {
    return {
      selecting: 'book',
      selected_category: 0,
      sortType: 0,
      searchName: "",
      category_list: [
        { name: "全部", value: 0 },
        { name: "教材", value: 1 },
        { name: "其他书籍", value: 2 }
      ],
      books: []
    };
  },
  created: function() {
    let that = this;
    axios
      .get("http://localhost:8080/booklist/")
      .then(function(response) {
        that.books = response.data;
        for (let book of that.books) {
          book.picpath = "./images/" + book.isbn + ".jpg";
        }
      })
      .catch(function(error) {
        console.log(error);
      });
  },
  computed: {
    price_sortimage: function() {
      if (this.sortType === 1) {
        return "./images/up.png";
      } else if (this.sortType === 2) {
        return "./images/down.png";
      }
      return "";
    },
    stock_sortimage: function() {
      if (this.sortType === 3) {
        return "./images/up.png";
      } else if (this.sortType === 4) {
        return "./images/down.png";
      }
      return "";
    },
    filter_book: function() {
      const { books, searchName, sortType, selected_category } = this;
      var arr = this.books;
      if (searchName.trim()) {
        var arr = arr.filter(
          p =>
            p.title.indexOf(searchName) !== -1 ||
            p.author.indexOf(searchName) !== -1
        );
      }
      if (selected_category) {
        arr = arr.filter(p => p.category == selected_category);
      }
      if (sortType) {
        const vals = [
          0,
          "p1.price - p2.price",
          "p2.price - p1.price",
          "p1.stock - p2.stock",
          "p2.stock - p1.stock"
        ];
        arr.sort(function(p1, p2) {
          return eval(vals[sortType]);
        });
      }
      return arr;
    }
  },
  methods: {
    setSortType(sortType) {
      if (this.sortType == sortType) {
        this.sortType += 1;
      } else if (this.sortType == sortType + 1) {
        this.sortType = 0;
      } else {
        this.sortType = sortType;
      }
    }
  }
};
</script>
