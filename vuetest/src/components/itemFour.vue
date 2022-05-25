<template>
  <div>
    <div class="chart" id="main1">
      container
    </div>
  </div>
</template>

<script>
import * as echarts from "echarts";
import axios from "axios";

export default {
  data() {
    return {
      tabledata: [],
      name: [],
      value: null
    }
  }, methods: {
    async getData() {
      const _this = this;
      await axios.get('http://localhost:8181/user_num/getall').then(res => {
        for (let i = 0; i < res.data.length; i++) {
          _this.value = res.data[i]['number'];
          var str = String(2007 + parseInt((res.data[i]['id'] + 7) / 12)) + '-' + String((7 + res.data[i]['id']) % 12)
          _this.name.push(str);
          _this.tabledata.push(_this.value);
        }
      })
    },
    createEcharts() {
      const _this = this;
      let chart = echarts.init(document.getElementById('main1'));
      let option = {
        title: {
          text: 'Stackoverflow User Amount',
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
          type: 'category',
          data: _this.name
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            data: _this.tabledata,
            type: 'line',
            smooth: true
          }
        ]
      };
      chart.setOption(option)
    }
  },
  created() {
    const _this = this;
    _this.getData().then(() => {
      _this.createEcharts();
    })
  }
}
</script>

<style scoped>

.chart {
  height: 600px;
}
</style>