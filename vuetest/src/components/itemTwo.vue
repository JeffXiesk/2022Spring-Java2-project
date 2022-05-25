<template>
  <el-container>
    <el-header>
      <el-main>
        <h2>
          All site information
        </h2>
      </el-main>
    </el-header>
    <el-main>
      <el-table :data="tableData" border style="width: 50%;left: 25%" size="large" stripe="true" table-layout="fix">
        <el-table-column prop="info_name" label="info_name" width="auto"/>
        <el-table-column prop="numeric_data" label="numeric_data" width="auto"/>
      </el-table>
    </el-main>
  </el-container>
</template>

<script>
import axios from "axios";
import * as echarts from 'echarts';

export default {
  data() {
    return {
      tableData: null
    }
  },
  methods: {
    async getdata() {
      const _this = this;
      await axios.get('http://localhost:8181/Topic_Info/StackOverflow_Info/Get_All_Information').then(res => {
        _this.tableData = res.data;
      })
    }
  },
  created() {
    const _this = this;
    _this.getdata()
  }
}
</script>

<style scoped>
h2 {
  height: .6rem;
  /*color: #fff;*/
  line-height: 0.25rem;
  text-align: center;
}

.chart {
  height: 4.5rem;
}
</style>