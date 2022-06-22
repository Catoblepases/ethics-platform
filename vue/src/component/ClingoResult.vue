<template>
  <el-dialog
    v-model="resultVisible"
    title="Clingo Result"
    width="90%"
    height="90%"
    :before-close="handleClose"
  >
    <el-table :data="tableData" style="width: 100%">
      <el-table-column
        v-for="item in items"
        :key="item"
        :prop="item"
        :label="item"
      />
    </el-table>

    <template #footer>
      <span class="dialog-footer">
        <el-button @click="resultVisible = false">Cancel</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
import {
  inject,
  onBeforeMount,
  onMounted,
  ref,
  reactive,
} from "@vue/runtime-core";
import axios from "axios";

const resultVisible = inject("result");
let form: any;

const items = ref([]);

let tableData = ref([]);

const changeData = (data: any) => {
  tableData.value = data;
};
const changeItems = (dataItems: any) => {
  items.value = dataItems;
};

const initGraphAnalyse = () => {
  if (form === undefined) {
    let dataT = { name: "./resultTroll.lp" };
    axios.post("api/clingo/result", dataT).then((res) => {
      changeData(res.data.data);
      changeItems(res.data.items);
    });
  }
};

onMounted(() => {
  initGraphAnalyse();
});

defineExpose({ initGraphAnalyse });
</script>
