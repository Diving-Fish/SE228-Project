<template>
  <div class="login">
    <div class="inner">
      <div class="text-title">注册</div>
      <div class="input-box">
        <span>用户名：</span>
        <input type="text" v-model="username">
        <p class="info">{{ username_info }}</p>
      </div>
      <div class="lbutton-box" @click="checkvalid">
        <button>检查用户名可用性</button>
        <a id="validtext">{{ valid_text }}</a>
      </div>
      <div class="input-box">
        <span>密码：</span>
        <input type="password" v-model="password">
        <p class="info">{{ password_info }}</p>
      </div>
      <div class="input-box">
        <span>确认密码：</span>
        <input type="password" v-model="re_password">
        <p class="info">{{ re_password_info }}</p>
      </div>
      <div class="input-box">
        <span>邮箱：</span>
        <input type="text" v-model="email">
        <p class="info">{{ email_info }}</p>
      </div>
      <div class="button-box" @click="submit">
        <button>注册</button>
      </div>
    </div>
  </div>
</template>


<script>
/* eslint-disable */
import axios from "axios";
export default {
  name: "register",
  data() {
    return {
      username: "",
      password: "",
      re_password: "",
      email: "",
      valid: -1
    };
  },
  computed: {
    valid_text: function() {
      if (this.valid == 1) {
        return "用户名可以使用";
      } else if (this.valid == 0) {
        return "用户名已被占用";
      } else {
        return "";
      }
    },
    username_info: function() {
      let reg = /^[_a-zA-Z][_a-zA-Z0-9]{3,15}$/;
      this.valid = -1;
      if (this.username != "" && reg.test(this.username) == false) {
        return "仅可使用数字、字母和下划线，长度需在4-16之间";
      }
      return "";
    },
    password_info: function() {
      let reg = /^[a-zA-Z0-9]{4,18}$/;
      if (this.password != "" && reg.test(this.password) == false) {
        return "仅可使用数字、字母，长度需在4-18之间";
      }
      return "";
    },
    re_password_info: function() {
      if (
        this.password != "" &&
        this.re_password != "" &&
        this.password != this.re_password
      ) {
        return "密码与重复密码不一致";
      }
      return "";
    },
    email_info: function() {
      let reg = /^[_a-z0-9-]+(\.[_a-z0-9-]+)*@[a-z0-9-]+(\.[a-z0-9-]+)*(\.[a-z]{2,})$/;
      if (this.email != "" && reg.test(this.email) == false) {
        return "邮箱格式不正确";
      }
      return "";
    }
  },
  methods: {
    checkvalid() {
      if (this.username != "" && this.username_info == "") {
        let that = this;
        axios
          .get("http://localhost:8080/usernamevalid?username=" + that.username)
          .then(function(response) {
            that.valid = response.data.valid ? 1 : 0;
            document.getElementById("validtext").style.color = response.data
              .valid
              ? "green"
              : "red";
          })
          .catch(function(error) {
            console.log(error);
          });
      }
    },
    submit() {
      let {
        username,
        username_info,
        password,
        password_info,
        re_password,
        re_password_info,
        email,
        email_info,
        valid
      } = this;
      if (
        !(
          username_info == "" &&
          password_info == "" &&
          re_password_info == "" &&
          email_info == "" &&
          username != "" &&
          password != "" &&
          re_password != "" &&
          email != "" &&
          valid == 1
        )
      ) {
        alert("表单未填写正确！");
        return;
      }
      let that = this;
      axios
        .post("http://localhost:8080/register", {
          username: that.username,
          password: that.password,
          email: that.email
        })
        .then(function(response) {
          alert("注册成功！");
          window.location.href = "#/login";
        })
        .catch(function(error) {
          console.log(error);
        });
    }
  }
};
</script>
