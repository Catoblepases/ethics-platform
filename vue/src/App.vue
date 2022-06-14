<template>
  <div class="common-layout">
    <el-container>
      <el-header height="74px"
        ><main-header
          ref="headerRef"
          @onChange="onChange"
          @initSimulation="initSimulation"
          @runOneStep="runOneStep"
          @runOneStepBack="runOneStepBack"
          @runAll="runAll"
          @getSimulation="getSimulation"
        ></main-header
      ></el-header>
      <el-container>
        <el-aside v-show="collapse" width="430px">
          <el-scrollbar><side-menu></side-menu></el-scrollbar>
        </el-aside>
        <el-main>
          <el-scrollbar>
            <main-canvas
              ref="g6Ref"
              @getSimulation="getSimulation"
              :currentSimulationName="currentSimulationName"
            ></main-canvas> </el-scrollbar
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
import { ElMessage } from "element-plus";

const headerRef = ref<any>();
const g6Ref = ref<any>();
const currentSimulationName = ref("s1");

function getSimulation(): void {
  currentSimulationName.value = headerRef.value.getCurrentSimulationName();
}

function initSimulation(): void {
  console.log("App:initSimulation");
  g6Ref.value.initSimulation();
}

function runOneStep(): void {
  console.log("App:sunOneStep");
  g6Ref.value.runOneStep();
}

const runOneStepBack = () => {
  console.log("App:run one step back");
  g6Ref.value.runOneStepBack();
};

const runAll = () => {
  console.log("App:runAll");
  g6Ref.value.runAll();
};

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
