<template>
  <div class="login">
    <div class="inner">
      <div class="text-title">登录</div>
      <div class="input-box">
        <span>用户名：</span>
        <input type="text" v-model="username">
      </div>
      <div class="input-box">
        <span>密码：</span>
        <input type="text" v-model="password">
      </div>
      <div class="button-box">
        <button @click="submit">登录</button>
      </div>
    </div>
  </div>
</template>


<script>
/* eslint-disable */
import axios from "axios";

axios.defaults.withCredentials = true;
export default {
  name: "login",
  components: {},
  data() {
    return {
      username: "",
      password: ""
    };
  },
  methods: {
    submit() {
      axios({
        method: "post",
        url: "http://localhost:8080/login",
        headers: {
          "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8"
        },
        data: { username: this.username, password: this.password },
        params: { username: this.username, password: this.password }
      }).then(function(response) {
        if (response.data.status == 200) {
          alert("登录成功！");
          window.location.href = "#/";
          window.location.reload();
        } else {
          alert("用户名或密码错误！");
        }
      });
    }
  }
};
</script>
