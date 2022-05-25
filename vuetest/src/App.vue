<template>
  <div id="backcont">
  <header>
    <h1>Java2 Final Project</h1>
  </header>
    <router-view/>
  </div>
</template>

<script>
import {provide} from 'vue'
import * as echarts from 'echarts'
import axios from "axios";

axios.defaults.baseURL = 'http://localhost:8181'
export default {
  setup(){
    provide('echarts',echarts)
  },
  components: {
  }
}
</script>

<style>

*{
  margin: 0px;
  padding: 0px;
  box-sizing: border-box;
}
</style>
