<template>
  <el-tabs type="border-card" tab-position="left" class="demo-tabs">
    <el-tab-pane label="Event" @click="getEvent">
      <el-table :data="tableData" style="width: 100%">
        <el-table-column prop="eventType" label="eventType" width="120" />
        <el-table-column
          prop="eventDescription"
          label="eventDescription"
          width="120"
        />
        <el-table-column align="right" width="180">
          <template #header>
            <el-input
              v-model="search"
              size="small"
              placeholder="Type to search"
            />
          </template>
          <template #default="scope">
            <el-button size="small" @click="handleEdit(scope.$index, scope.row)"
              >Edit</el-button
            >
            <el-button
              size="small"
              type="danger"
              @click="handleDelete(scope.$index, scope.row)"
              >Delete</el-button
            >
          </template>
        </el-table-column>
      </el-table>
    </el-tab-pane>
    <el-tab-pane label="Comand">
      <el-input
        v-model="textarea"
        :rows="10"
        type="textarea"
        placeholder="Please input"
      />
      <el-input
        v-model="input"
        placeholder="Please input"
        @keydown.enter="addToTextArea"
      />
    </el-tab-pane>
    <el-tab-pane label="Clingo">
      <clingo-menu></clingo-menu>
    </el-tab-pane>
    <el-tab-pane label="Ethics">
      <a
        href="api/Node"
        target="_blank"
        rel="external nofollow"
        style="text-decoration: none; color: #606266"
        download="allNode.json"
        >download</a
      >
    </el-tab-pane>
  </el-tabs>
</template>

<script lang="ts" setup>
import { Calendar } from "@element-plus/icons-vue";
import ClingoMenu from "./Clingo.vue";
import { ref, onMounted, computed } from "vue";
import axios from "axios";

let input = ref("");
let textarea = ref("");

function addToTextArea() {
  axios.post("api/carriage/command", input.value).then(() => {
    textarea.value = textarea.value.concat("\n" + input.value);
    input.value = "";
  });
}

interface Event {
  eventType: string;
  eventDescription: string;
}

let tableData = ref([]);

const setTableData = (data: any) => {
  tableData.value = data;
  console.log(tableData.value);
};

const getEvent = () => {
  axios.get("api/carriage/events").then((res) => {
    setTableData(res.data);
  });
};

const search = ref("");
const filterTableData = computed(() =>
  tableData.value.filter(
    (data) =>
      !search.value ||
      data.eventType.toLowerCase().includes(search.value.toLowerCase())
  )
);
const handleEdit = (index: number, row: Event) => {
  console.log(index, row);
};
const handleDelete = (index: number, row: Event) => {
  console.log(index, row);
};

onMounted(() => {
  getEvent();
});
</script>
<style>
.demo-tabs > .el-tabs__content {
  padding: 32px;
  color: #6b778c;
  font-size: 32px;
  font-weight: 600;
}
.demo-tabs .custom-tabs-label .el-icon {
  vertical-align: middle;
}
.demo-tabs .custom-tabs-label span {
  vertical-align: middle;
  margin-left: 10px;
}
</style>
