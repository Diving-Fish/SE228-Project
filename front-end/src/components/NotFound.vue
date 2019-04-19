<template>
<body>
  <div style="height: 10em"></div>
  <table cellspacing="0" cellpadding="0">
    <tr>
      <td>
        <table cellspacing="0" cellpadding="0">
          <tr>
            <td>
              <h1>404</h1>
              <h3>未找到页面</h3>
              <p>
                请检查您访问的链接是否正确~
                <br>
                <router-link to="/">回到主页 ></router-link>
              </p>
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
</body>
</template>

<style scoped>
* {
  font-family: "Microsoft Yahei";
  margin: 0;
  font-weight: lighter;
  text-decoration: none;
  text-align: center;
  line-height: 2.2em;
}
html,
body {
  height: 100%;
}
h1 {
  font-size: 100px;
  line-height: 1em;
}
table {
  width: 100%;
  height: 100%;
  border: 0;
}
</style>

<script>
export default {
  name: "notfound"
};
</script>
