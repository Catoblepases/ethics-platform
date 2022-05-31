<template>
  <div class="common-layout">
    <el-container>
      <el-header height="74px"><main-header @onChange="onChange"></main-header></el-header>
      <el-container>
        <el-aside v-show="collapse" width="300px">
          <el-scrollbar><side-menu></side-menu></el-scrollbar>
        </el-aside>
        <el-main>
          <el-scrollbar> <main-canvas></main-canvas> </el-scrollbar
        ></el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script setup lang="ts">
import SideMenu from "./component/SideMenu.vue";
import MainCanvas from "./component/g6Canvas.vue";
import MainHeader from "./component/Header.vue";
import axios from "axios";
import { ref, onMounted } from "vue";

const childRef = ref();

const collapse = ref(true);

const onChange = (params: boolean) => {
  console.log("onchange", params);
  collapse.value = params;
};

function getAllUser() {
  axios.get("/api/User").then((response) => {
    console.log(response.data);
  });
}
</script>

<style scoped>
svg {
  width: 20px;
  height: 20px;
  color: rgb(90, 99, 121);
}

.layout-container-demo .el-header {
  position: relative;
  background-color: var(--el-color-primary-light-7);
  color: var(--el-text-color-primary);
}
.layout-container-demo .el-aside {
  color: var(--el-text-color-primary);
  background: var(--el-color-primary-light-8);
}
.layout-container-demo .el-menu {
  border-right: none;
}
.layout-container-demo .el-main {
  padding: 0;
}
.layout-container-demo .toolbar {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  height: 100%;
  right: 20px;
}
</style>

function mounted() { throw new Error('Function not implemented.'); }
