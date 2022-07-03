<template>
  <el-table :data="tableData" style="width: 100%">
    <el-table-column align="left" width="80">
      <template #header>
        <el-button
          :icon="Plus"
          type="primary"
          circle
          @click="addSimulation"
        ></el-button>
        <el-button :icon="Refresh" circle @click="getSimulations"></el-button>
      </template>
      <template #default="scope">
        <el-button
          size="small"
          :icon="Edit"
          @click="handleEdit(scope.$index, scope.row)"
          circle
        ></el-button>
        <el-button
          size="small"
          type="danger"
          :icon="Delete"
          @click="handleDelete(scope.$index, scope.row)"
          circle
        ></el-button>
      </template>
    </el-table-column>
    <el-table-column
      v-for="item in items"
      :key="item"
      :prop="item"
      :label="item"
    />
  </el-table>
  <edit-simulation ref="editS" @onChange="onChange"></edit-simulation>
</template>

<script lang="ts" setup>
import { computed, onMounted, ref, provide } from "vue";
import {
  Check,
  Close,
  Plus,
  Edit,
  Delete,
  Refresh,
} from "@element-plus/icons-vue";
import axios from "axios";
import EditSimulation from "./EditSimulation.vue";

let tableData = ref([]);
let items = ref([]);
let editS = ref<any>();
let editSimulationVisible = ref(false);
let activeSimulation = ref({});
provide("editSimulationVisible", editSimulationVisible);
provide("activeSimulation", activeSimulation);

let editMode = ref(false);
provide("editMode", editMode);

const setTableData = (data: any) => {
  tableData.value = data;
  console.log(tableData.value);
};

const setItems = (dataItems: any) => {
  items.value = dataItems;
};

const getSimulations = () => {
  axios.get("api/carriage/simulation").then((res) => {
    setItems(res.data.columns);
    setTableData(res.data.data);
    console.log("getSimulations");
  });
};

const onChange = (params: boolean) => {
  console.log("onchange:data of Simulaion", params);
  getSimulations();
};

const addSimulation = () => {
  editMode.value = false;
  editS.value.updateEditSimulation();
  editSimulationVisible.value = true;
};

const handleEdit = (index: number, row: Event) => {
  console.log(index, row);
  activeSimulation.value = tableData.value[index];
  editMode.value = true;
  editS.value.updateEditSimulation();
  editSimulationVisible.value = true;
};

const handleDelete = (index: number, row: Event) => {
  console.log(index, row);
  var id: string = tableData.value[index].name;
  axios.put("api/carriage/simulation/delete/" + id).then((res) => {
    console.log(res);
    getSimulations();
  });
};

onMounted(() => {
  getSimulations();
  console.log(tableData);
  // setInterval(() => {
  //   getSimulations();
  // }, 100);
});
</script>
