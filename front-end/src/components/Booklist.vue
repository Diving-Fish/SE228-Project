<template>
  <div id="booklist">
    <div style="height: 3em"></div>
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
    </div>
    <div class="bookele" v-for="book in filter_book" :key="book.isbn">
      <div class="inner">
        <div class="book">
          <img :src="book.imagelink" style="width: 200px; height: 200px; margin-left: 20px; margin-right: 20px;">
          <div class="description">
            <router-link :to="'/book/' + book.isbn" :href="book.link" class="title">{{ book.title }}</router-link>
            <div>
              <a class="price">￥{{ book.price.toFixed(2) }}</a>
              <a class="stock">库存{{ book.stock }}本</a>
            </div>
            <a class="author">作者：{{ book.author }}</a>
            <a>{{ book.intro }}</a>
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
  name: "booklist",
  components: {},
  watch: {
    '$route' (to, from) {
      if (to.path == "/booklist" && from.path == "/booklist") {
        if (to.query.category) this.selected_category = to.query.category;
        else this.selected_category = 0;
      }
    }
  },
  data() {
    return {
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
      })
      .catch(function(error) {
        console.log(error);
      });
    if (this.$route.query.category == 1 || this.$route.query.category == 2) {
      this.selected_category = this.$route.query.category;
    }
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
