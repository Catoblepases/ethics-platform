<template>
  <div class="common-layout">
    <el-container>
      <el-header height="74px"
        ><main-header @onChange="onChange"></main-header
      ></el-header>
      <el-container>
        <el-aside v-show="collapse" width="430px">
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
</script>

<style lang="scss">
:root {
  --el-color-primary: #595f80;
  // --el-color-success: #52775d;
}
.el-button--primary {
  --el-button-font-color: #ffffff;
  --el-button-background-color: #595f80;
  --el-button-border-color: #595f80;
  --el-button-hover-color: #595f80;
  --el-button-active-font-color: #e6e6e6;
  --el-button-active-background-color: #595f80;
  --el-button-active-border-color: #595f80;
}

.el-button--success {
  --el-button-font-color: #ffffff;
  --el-button-background-color: #52775d;
  --el-button-border-color: #52775d;
  --el-button-hover-color: #52775d;
  --el-button-active-font-color: #e6e6e6;
  --el-button-active-background-color: #52775d;
  --el-button-active-border-color: #52775d;
}
</style>