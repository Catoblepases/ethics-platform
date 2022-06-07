<template>
  <el-table :data="tableData" style="width: 100%">
    <el-table-column label="valid" align="right" width="80">
      <template #default="scope">
        <el-switch
          v-model="tableData[scope.$index].valid"
          class="mt-2"
          style="margin-left: 24px"
          inline-prompt
          :active-icon="Check"
          :inactive-icon="Close"
          @click="handleValid(scope.$index, scope.row)"
        />
      </template>
    </el-table-column>
    <el-table-column
      prop="eventDescription"
      label="eventDescription"
      width="120"
    />
    <el-table-column prop="time" label="time" width="150">
      <template #default="scope">
        <el-input-number
          size="small"
          v-model="tableData[scope.$index].time"
          controls-position="right"
        ></el-input-number>
      </template>
    </el-table-column>
    <el-table-column align="right" width="180">
      <template #header>
        <el-input v-model="search" size="small" placeholder="Type to search" />
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
</template>

<script lang="ts" setup>
import { computed, onMounted, ref } from "vue";
import { Check, Close } from "@element-plus/icons-vue";
import axios from "axios";

interface Event {
  valid: boolean;
  eventType: string;
  eventDescription: string;
  time: number;
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
const handleValid = (index: number, row: Event) => {
  console.log(index, row.valid);
};

onMounted(() => {
  getEvent();
  console.log(tableData);
});
</script>
