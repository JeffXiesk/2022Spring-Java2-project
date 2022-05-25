<template>
  <div>
    <section class="container">
      <section class="itemleft">
        <el-header>
          <el-main>
            <h2 style="text-align: center;font-size: 20px">Please Choose a Topic</h2>
          </el-main>
        </el-header>
        <el-main>
          <el-menu :default-active="activeIndex"
                   @select="handleselect"
                   style="height: 500px"
          >
            <el-menu-item index="1">{{ eventValue[0] }}</el-menu-item>
            <el-menu-item index="2">{{ eventValue[1] }}</el-menu-item>
            <el-menu-item index="3">{{ eventValue[2] }}</el-menu-item>
            <el-menu-item index="4">{{ eventValue[3] }}</el-menu-item>
            <el-menu-item index="5">{{ eventValue[4] }}</el-menu-item>
            <el-menu-item index="6">{{ eventValue[5] }}</el-menu-item>
            <el-menu-item index="7">{{ eventValue[6] }}</el-menu-item>
          </el-menu>
        </el-main>
      </section>
      <section class="itemcenter">
        <div v-if="type==='1'">
          <item-two></item-two>
        </div>
        <div v-else-if="type==='2'">
          <item-four></item-four>
        </div>
        <div v-else-if="type==='3'">
          <wordcloud></wordcloud>
        </div>
        <div v-else-if="type==='4'">
          <item-six></item-six>
        </div>
        <div v-else-if="type==='5'">
          <item-three></item-three>
        </div>
        <div v-else-if="type==='6'">
          <item-five></item-five>
        </div>
        <div v-else-if="type==='7'">
          <item-seven></item-seven>
        </div>
        <div v-else>
          <h2>
            Please choose an operation !
          </h2>
        </div>
      </section>
      <section class="itemright">
        <item-page style="background-color: white;border: aliceblue 10px;border-radius: 20px">
          <div>
            <el-container>
              <el-header style="border-radius: 20px">
                <el-main>
                  <div>
                    <h2 style="text-align: center;font-size: 20px">User Information</h2>
                  </div>
                </el-main>
              </el-header>
              <el-main>
                <el-container>
                  <el-aside width="100px">
                    <el-avatar :size="50" :src="circleUrl" style="left: 10px"/>
                    <el-button type="default" @click="logout">
                      Log out
                    </el-button>
                  </el-aside>
                  <el-container>
                    <el-header>
                      <el-main>
                        <div>
                          <h2 style="text-align: center;font-size: 15px">User: {{ currentUser }}</h2>
                        </div>
                      </el-main>
                    </el-header>
                    <el-main>
                      <h3 style="font-size: 18px">
                        History record:<br>
                      </h3>
                      <el-carousel :interval="2000" height="60px" indicator-position="none">
                        <el-carousel-item v-for="item in historyrecord" :key="item">
                          <el-link type="primary" style="top:30%;margin-right: 8px;font-size: 20px"
                                   @click="handle_his_click($event,item)">{{ item }}
                          </el-link>
                        </el-carousel-item>
                      </el-carousel>
                    </el-main>
                  </el-container>
                </el-container>
              </el-main>
            </el-container>
          </div>
        </item-page>
        <item-page style="background-color: white;border: aliceblue 10px;border-radius: 20px">
          <item-one></item-one>
        </item-page>
      </section>
    </section>
  </div>
</template>

<script>
import itemPage from "@/components/itemPage";
import itemOne from "@/components/itemOne";
import itemTwo from "@/components/itemTwo";
import itemThree from "@/components/itemThree";
import itemFour from "@/components/itemFour";
import itemFive from "@/components/itemFive";
import itemSix from "@/components/itemSix";
import itemSeven from "@/components/itemSeven";
import {inject} from 'vue'
import router from "@/router";
import axios from "axios";
import wordcloud from '../components/Wordcloud'


export default {

  data() {
    return {
      activeIndex: '1',
      type: 0,
      count: 10,
      input: '',
      _router: null,
      tableData: null,
      historyrecord: null,
      eventValue: ['Basic information of StackOverflow', 'Get all user amount', 'Tag Information', 'Privilege Level', 'User Reputation Grouped Information', 'Search Tag Information', 'Search Tag Popular Word'],
      circleUrl:
          'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png',
      currentUser: ''
    }
  },
  created() {
    let $echarts = inject('echarts');
    const _this = this;
    axios.get('http://localhost:8181/User_Account/Get_User_Name').then(res => {
      _this.currentUser = res.data;
    })

    setInterval(function () {
      _this.getBrowserHis()
    }, 2000);
  },
  components: {
    itemPage, itemOne, itemTwo, itemThree, itemFour, itemFive, itemSix, itemSeven, wordcloud
  },
  methods: {
    async getBrowserHis() {
      const _this = this;
      await axios.get('http://localhost:8181/User_Account/Browse_History').then(res => {
        _this.historyrecord = [];
        for (let i = 0; i < res.data.length; i = i + 1) {
          _this.historyrecord.push(res.data.at(i).at(1))
        }
      })
    },
    handleselect(key, value) {
      const _this = this;
      _this.type = key;
      _this.tableData = null;

    },
    logout() {
      alert('Log out successfully ')
      axios.post('http://localhost:8181/User_Account/Log_out');
      router.push('/')
    },
    created() {
    },
    handle_his_click: function (e, value) {
      // Need to judge type value.
      for (let i = 0; i < this.eventValue.length; i = i + 1) {
        if (this.eventValue[i] === value) {
          this.type = String(i + 1);
          break;
        }
      }
    }
  }
}
</script>
<style>
h2 {
  margin: 0;
  border: 0;
  padding: 0;
}

h3 {
  margin: 8px;
  border: 0;
  padding: 0;
}

.every_item {
  background-color: white;
}

header {
  height: 1rem;
  width: 100%;
  background-color: aliceblue;
}

h1 {
  font-size: 0.475rem;
  text-align: center;
  line-height: 1rem;
}

.container {
  min-height: 800px;
  max-height: 10px;
  margin: 0 auto;
  /*border: 1px solid red;*/
  padding: 10px 10px 0;
  height: 500px;
  /*background-color: gray;*/
  display: flex;

}

.itemleft {
  flex: 3;
}

.itemright {
  flex: 4;
}

.itemcenter {
  flex: 6;
  height: 11rem;
  /*border: 1px solid red;*/
  padding: 0.125rem;
  margin: .25rem;
}

.infinite-list {
  height: 110px;
  padding: 0;
  margin: 0;
  list-style: none;
}

.infinite-list .infinite-list-item {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 40px;
  background: var(--el-color-primary-light-9);
  margin: 10px;
  color: var(--el-color-primary);
}

.infinite-list .infinite-list-item + .list-item {
  margin-top: 10px;
}

</style>
