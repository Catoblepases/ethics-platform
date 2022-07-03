<template>
  <q-dialog v-model="resultVisible" full-width>
    <q-card style="width: 700px">
      <q-toolbar>
        <q-toolbar-title>clingo result</q-toolbar-title>
        <q-btn flat round dense icon="close" v-close-popup />
      </q-toolbar>
      <q-table flat :rows="tableData" row-key="event" />
    </q-card>
  </q-dialog>
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
