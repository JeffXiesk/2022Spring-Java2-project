<template>
  <div>
    <h2>Chart1</h2>
    <div class="chart" id="oneChart">
      container
    </div>
  </div>
</template>

<script>
import axios from "axios";
import * as echarts from 'echarts';
import {ref} from "vue";

export default {
  data() {
    return {
      name: null,
      cnt: null
    }
  },
  created() {
    const _this = this;
    setInterval(function () {
      _this.getData().then(() => {
        _this.createEcharts()
      });
    }, 1000);
  },
  methods: {
    async getData() {
      let _this = this
      await axios.get('http://localhost:8181/User_Account/Find_Active_User').then(res => {
        _this.name = res.data.map(({user_name}) => user_name);
        _this.cnt = res.data.map(({search_Cnt}) => search_Cnt);
      })
    },
    createEcharts() {
      const _this = this
      let myChart = echarts.init(document.getElementById("oneChart"));
      myChart.setOption({
        title: {
          text: 'Most Active Users',
          left: 'center'
        },
        grid: {
          top: '15%',
          left: '5%',
          right: '10%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: {
          type: 'value'
        },
        yAxis: {
          type: 'category',
          data: _this.name
        },
        toolbox: {
          show: true,
          feature: {
            saveAsImage: {show: true}
          }
        },
        series: [
          {
            data: _this.cnt,
            type: 'bar',
            itemStyle: {
              normal: {
                barBorderRadius: [0, 20, 20, 0],
                color: new echarts.graphic.LinearGradient(
                    0, 0, 1, 0,
                    [
                      {offset: 1, color: '#a0cfff'},
                      {offset: 0.5, color: '#d9ecff'},
                      {offset: 0, color: '#ecf5ff'}
                    ]
                )
              }
            },
            showBackground: true,
            backgroundStyle: {
              color: 'rgba(180, 180, 180, 0.2)'
            }
          }
        ]
      })

    }
  }
}
</script>

<style scoped>
h2 {
  height: .6rem;
  color: #fff;
  line-height: 0.25rem;
  text-align: center;
}

.chart {
  height: 240px;
}
</style>