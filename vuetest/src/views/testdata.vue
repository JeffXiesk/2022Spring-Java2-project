<template>
  <div>
    <table>
      <tr>
        <td>a</td>
        <td>b</td>
        <td>c</td>
      </tr>
      <tr v-for="item in all">
        <td>{{ item.id }}</td>
        <td>{{ item.name }}</td>
        <td>{{ item.author }}</td>
      </tr>
    </table>
  </div>
</template>

<script>
import { getCurrentInstance } from 'vue'

export default {
  name: "testdata",
  data() {
    return {
      msg: 'Hello Vue',
      all: [
        {
          id: 1,
          name: 'aaa',
          author: 'aaaaaa'
        },
        {
          id: 2,
          name: 'bbb',
          author: 'bbbbbb'
        },
        {
          id: 3,
          name: 'ccc',
          author: 'ccccccc'
        }
      ]
    }
  }
  ,
  created() {
    const _this = this
    const { ctx, proxy } = getCurrentInstance();
    proxy.axios.get('http://localhost:8181/testdata/findAll').then(function (resp){
      console.log(resp)
      _this.all = resp.data
    })
  }
}
</script>

<style scoped>

</style>