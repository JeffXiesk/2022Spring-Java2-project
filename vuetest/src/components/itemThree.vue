<template>
  <div class='echarts'>
    <div class="chart" id="main2">
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
      name: [],
      data1: []
    }
  },
  methods: {
    async getData() {
      const _this = this;
      await axios.get('http://localhost:8181/Topic_Info/User_Reputation_Grouped_Info/Select_All').then(res => {
        for (let i = 0; i < res.data.length; i++) {
          var str = res.data[i]['min_repu'];
          // var str = 'range:' + res.data[i]['min_repu'] + '-' + res.data[i]['max_repu'];
          _this.name.push(str);
          _this.data1.push({value: res.data[i]['num'], name: str})
        }
      })
    },
    createChart() {
      const _this = this;
      let chart = echarts.init(document.getElementById('main2'));
      chart.setOption({
        title: {
          text: 'User Reputation Group',
          subtext: 'The ratio of the user',
          left: 'center'
        },
        tooltip: {
          trigger: 'item',
          formatter: '{a} <br/>{b} : {c} ({d}%)'
        },
        legend: {
          // left: 'center',
          top: 'bottom'
          // data: _this.name
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
        series: [
          {
            name: 'Radius Mode',
            type: 'pie',
            radius: [20, 200],
            center: ['50%', '50%'],
            // roseType: '',
            itemStyle: {
              borderRadius: 8
            },
            // label: {
            //   show: false
            // },
            // emphasis: {
            //   label: {
            //     show: true
            //   }
            // },
            data: _this.data1
          }
        ]
      });
    }
  },
  created() {
    const _this = this;
    this.getData().then(() => {
      this.createChart();
    })
  }
}
</script>

<!-- Add 'scoped' attribute to limit CSS to this component only -->
<style scoped>
.echarts {
  width: 800px;
  height: 800px;
  margin: 0 auto;
}

h1, h2 {
  font-weight: normal;
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
  height: 600px;
}
</style>
