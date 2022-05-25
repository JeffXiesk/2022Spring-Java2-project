<template>
  <el-container>
    <el-header>
      <el-main>
        <h2 style="text-align: center;font-size: 20px">The Popular Word Query by Tag</h2>
      </el-main>
    </el-header>

    <el-main>
      <el-input v-model="input" placeholder="Please input the query keywords" style="width: 50%"/>
      <el-main>
        <el-footer>
          <el-button type="success" style="width: 80px" @click="handle_click">serach</el-button>
        </el-footer>
      </el-main>
      <div class="chart" id="main" style="height: 500px"/>
    </el-main>
  </el-container>
</template>

<script>
import 'echarts-wordcloud'
import * as echarts from "echarts";
import axios from "axios";

export default {
  data() {
    return {
      input: '',
      dataAxis: [],
      data: [],
      name: 'Farrah Abraham',
      value: 366
    }
  },

  created() {
  },
  methods: {
    handle_click() {
      const _this = this;
      console.log(_this.input)
      _this.getData().then(() => {
        _this.createEcharts()
      })
    },
    async getData() {
      let _this = this
      await axios.get('http://localhost:8181/Topic_Info/StackOverflow_Info/Select_Popular_Words/'+_this.input).then(res => {
        console.log(res.data)
        for (let i = 0; i < res.data.length; i++) {
          _this.dataAxis.push(res.data[i]['key'])
          _this.data.push(res.data[i]['value'])
        }
        console.log(_this.dataAxis)
        console.log(_this.data)
      })
    },
    createEcharts() {
      const _this = this

      let chart = echarts.init(document.getElementById('main'));

      chart.setOption({
        title: {
          text: 'Tag Related Popular Word',
          left: 'center'
        },
        toolbox: {
          show: true,
          feature: {
            mark: {show: true},
            dataView: {show: true, readOnly: false},

            restore: {show: true},
            saveAsImage: {show: true}
          }
        },
        xAxis: {
          data: _this.dataAxis,
          axisLabel: {
            inside: true,
            color: '#fff'
          },
          axisTick: {
            show: false
          },
          axisLine: {
            show: false
          },
          z: 10
        },
        yAxis: {
          axisLine: {
            show: false
          },
          axisTick: {
            show: false
          },
          axisLabel: {
            color: '#999'
          }
        },
        dataZoom: [
          {
            type: 'inside'
          }
        ],
        series: [
          {
            type: 'bar',
            showBackground: true,
            itemStyle: {
              color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                {offset: 0, color: '#c6e2ff'},
                {offset: 0.5, color: '#a0cfff'},
                {offset: 1, color: '#337ecc'}
              ])
            },
            emphasis: {
              itemStyle: {
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                  {offset: 0, color: '#c6e2ff'},
                  {offset: 0.7, color: '#a0cfff'},
                  {offset: 1, color: '#337ecc'}
                ])
              }
            },
            data: _this.data
          }
        ]
      });
    }
  }
}
</script>

<!-- Add 'scoped' attribute to limit CSS to this component only -->
<style scoped>
.echarts {
  width: 1000px;
  height: 1000px;
  margin: 10px;
}

ul {
  list-style-type: none;
  padding: 0;
}

li {
  display: inline-block;
  margin: 0 10px;
}

a {
  color: #42b983;
}

.chart {
  height: 700px;
}
</style>
