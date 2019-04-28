<template>
  <div class="order">
    <div style="height: 3em"></div>
    <div class="header">我的订单
      <div class="time_filter">
        <a>时间范围：</a>
        <input type="datetime-local" v-model="start_time">
        <a>到</a>
        <input type="datetime-local" v-model="end_time">
      </div>
    </div>
    <div v-for="group in filter_groups" :key = "group.groupid" class="groupitem">
      <div class="header">
        <a>{{ format_to_str(group.date) }}</a>
        <a>订单号：{{ group.groupid }}</a>
      </div>
      <div class="orderitem">
        <div class="pic"></div>
        <div class="title">书名</div>
        <div class="amount">数量</div>
        <div class="price">单价</div>
        <div class="allprice">总价</div>
      </div>
      <div v-for="order in group.orders" :key = "order.orderid" class="orderitem">
        <div class="pic"><img :src="'images/' + order.book.isbn + '.jpg'" style="width: 80px; height: 80px"></div>
        <div class="title">{{ order.book.title }}</div>
        <div class="amount"> {{ order.amount }}</div>
        <div class="price"> {{ order.book.price.toFixed(2) }}</div>
        <div class="allprice"> {{ (order.amount * order.book.price).toFixed(2) }}</div>
      </div>
    </div>
  </div>
</template>

<script>
/* eslint-disable */
import axios from "axios";
export default {
  name: 'order',
  data() {
    return {
      groups: [],
      start_time: "",
      end_time: "",
    }
  },
  created: function() {
    let that = this;
    axios.get("http://localhost:8080/getorder").then(function(response) {
      that.groups = response.data.groups;
      var min_date = new Date();
      for (let group of that.groups) {
        group.date = new Date(group.date);
        if (group.date < min_date) {
          min_date = group.date;
        }
      }
      that.start_time = that.format_to_local(min_date);
      that.end_time = that.format_to_local(new Date(Date()));
    });
  },
  computed: {
    filter_groups: function() {
      var arr = this.groups;
      var start = new Date(this.start_time);
      var end = new Date(this.end_time);
      arr = arr.filter(p => (p.date <= end && p.date >= start));
      arr.sort(function(p1, p2) {
        return p2.date - p1.date;
      });
      console.log(arr);
      return arr;
      
    }
  },
  methods: {
    format_to_str(date) {
      return date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate() + " " + date.getHours() + ':' + date.getMinutes();
    },
    format_to_local(date) {
      return date.getFullYear() + "-" + this.add_prefix(date.getMonth() + 1) + "-" + this.add_prefix(date.getDate()) + "T" + this.add_prefix(date.getHours()) + ':' + this.add_prefix(date.getMinutes());
    },
    add_prefix(num) {
      return (num > 10) ? "" + num : "0" + num;
    }
  }
}
</script>
