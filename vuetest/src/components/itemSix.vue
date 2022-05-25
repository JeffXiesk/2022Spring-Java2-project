<template>
  <el-container>
    <el-header>
      <el-main>
        <h2>
          Stackoverflow Privilege Level
        </h2>
      </el-main>
    </el-header>
    <el-main>
        <el-table :data="tableData" height="550"  style="width: 100%" size="large" table-layout="auto">
          <el-table-column prop="reputation" label="Reputation" align="center" width="180px"/>
          <el-table-column prop="description" label="Description" align="center" width="auto"/>
        </el-table>
    </el-main>
  </el-container>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      tableData: null
    }
  },
  methods: {
    async getdata() {
      const _this = this;
      await axios.get('http://localhost:8181/Topic_Info/Privileges_Info/Get_Privileges_Info').then(res => {
        _this.tableData = res.data
      })
    }
  },
  created() {
    const _this = this;
    _this.getdata();
  }
}
</script>

<style scoped>

</style>