<template>
  <el-container>
    <el-header>
      <el-main>
        <h2 style="text-align: center;font-size: 20px">The Recently Related Question Query by Tag</h2>
      </el-main>
    </el-header>
    <el-main>
      <el-input v-model="input" placeholder="Please input the query keywords" style="width: 50%"/>
      <el-main>
        <el-footer>
          <el-button type="success" style="width: 80px" @click="getdata">serach</el-button>
        </el-footer>
        <h2 style="text-align: center;font-size: 25px">Result</h2>
      </el-main>
      <ul class="infinite-list" style="overflow: auto">
        <li v-for="item in tabledata" :key="item" class="infinite-list-item">
          <el-main>
            <el-link type="primary" :href="item.at(2)" style="top:10%;margin-right: 8px;font-size: 18px">
              {{ item[3] }}
            </el-link>
          </el-main>
        </li>
      </ul>
    </el-main>
  </el-container>
</template>

<script>
import axios from "axios";

export default {
  name: "itemFive",
  data() {
    return {
      input: '',
      tabledata: [],
      // tabledata: []
    }
  },
  methods: {
    async getdata() {
      const _this = this
      await axios.get('http://localhost:8181/Topic_Info/StackOverflow_Info/Select_Latest_ten_quesionts/' + _this.input).then(res => {
        // for (let i=0;i<res.data.length;i++){
        //   _this.tabledata = res.data[i]
        // }
        _this.tabledata = res.data;
      })
    }
  },
  created() {
    // const _this = this
    // _this.getdata()
  }
}
</script>

<style scoped>
.infinite-list {
  height: 300px;
  padding: 0;
  margin: 0;
  list-style: none;
}

.infinite-list .infinite-list-item {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 50px;
  background: var(--el-color-primary-light-9);
  margin: 10px;
  color: var(--el-color-primary);
}

.infinite-list .infinite-list-item + .list-item {
  margin-top: 10px;
}
</style>