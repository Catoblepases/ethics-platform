<template>
  <el-container>
    <el-header>
      <main-header></main-header>
    </el-header>
    <el-container>
      <el-main><main-canvas></main-canvas></el-main>
      <el-footer
        >Footer
        <el-button icon="VideoPlay" @click="getAllUser"></el-button>
      </el-footer>
    </el-container>
  </el-container>
</template>

<script lang="ts">
import SideBar from "./component/SideBar.vue";
import MainCanvas from "./component/g6Canvas.vue";
import MainHeader from "./component/Header.vue";
import axios from "axios";

function getAllUser() {
  axios.get("/api/User").then((response) => {
    console.log(response.data);
  });
}

export default {
  components: {
    SideBar,
    MainCanvas,
    MainHeader,
  },
  methods: {
    getAllUser,
  },
};
</script>

<style scoped>
svg {
  width: 20px;
  height: 20px;
  color: rgb(90, 99, 121);
}
</style>
