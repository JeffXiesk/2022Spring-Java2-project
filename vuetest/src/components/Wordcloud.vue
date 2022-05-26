<template>
  <div class='echarts'>
    <div class="chart" id="main">
    </div>
  </div>
</template>

<script>
import 'echarts-wordcloud'
import * as echarts from "echarts";
import axios from "axios";
import {onMounted, onBeforeMount, ref} from 'vue';

export default {
  data() {
    // const that = this
    return {
      wordcloud: [{
        name: 'Farrah Abraham',
        value: 366
      }]
    }
  },

  created() {
    const _this = this;
    _this.getData().then(()=>{_this.createEcharts()})
  },
  methods: {
    async getData() {
      let _this = this
      await axios.get('http://localhost:8181/Topic_Info/Tag_Info/Get_Tag_Rank').then(res => {
        _this.wordcloud = ref(res.data);
      })
    },
    createEcharts () {
      const _this = this

      let chart = echarts.init(document.getElementById('main'));

      chart.setOption({
        title: {
          text: 'Tag Frequency',
          left: 'center'
        },
        grid: {
          top: 'center',
          left: 'center',
          right: 'center',
          bottom: 'center',
          containLabel: true
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
        series: [{
          type: 'wordCloud',
          shape: 'circle',
          keepAspect: false,
          // maskImage: maskImage,
          left: 'center',
          top: 'center',
          width: '70%',
          height: '70%',
          right: null,
          bottom: null,
          sizeRange: [12, 50],
          rotationRange: [-90, 90],
          rotationStep: 45,
          gridSize: 15,
          drawOutOfBound: true,
          layoutAnimation: true,
          textStyle: {
            fontFamily: 'sans-serif',
            fontWeight: 'bold',
            color: function () {
              return 'rgb(' + [
                Math.round(Math.random() * 160),
                Math.round(Math.random() * 160),
                Math.round(Math.random() * 160)
              ].join(',') + ')';
            }
          },
          emphasis: {
            // focus: 'self',
            textStyle: {
              textShadowBlur: 10,
              textShadowColor: '#333'
            }
          },
          data: _this.wordcloud
        }]
      });
    }
  }
}
</script>

<!-- Add 'scoped' attribute to limit CSS to this component only -->
<style scoped>
.echarts {
  width: auto;
  height: auto;
  margin: 10px;
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
  height: 700px;
}
</style>