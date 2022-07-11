<template>
  <el-dialog
    v-model="downVisible"
    title="Download"
    width="30%"
    :before-close="handleClose"
  >
    <el-row>
      <el-col span="4">
        <el-form-item label="fileName">
          <el-input v-model="fileName"></el-input>
        </el-form-item>
      </el-col>
      <el-col span="4">
        <el-cascader
          v-model="value"
          :options="options"
          @change="handleChange"
        /> </el-col
    ></el-row>

    <template #footer>
      <span class="dialog-footer">
        <el-button @click="downVisible = false">Cancel</el-button>
        <el-button type="primary" @click="download">Confirm</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script lang="ts" setup>
import { inject, ref } from "vue";
import { ElMessageBox } from "element-plus";
import axios from "axios";

const downVisible = inject("downVisible");

const value = ref("");
const fileName = ref("");
const options = [
  { value: ".json", label: ".json" },
  { value: ".lp", label: ".lp" },
];

const download = async () => {
  const path = "./download/" + fileName.value + value.value;

  switch (value.value.toString()) {
    case ".json":
      console.log(path);
      const { data } = await axios.get("api/Node");
      console.log(data);
      window.sessionStorage.setItem(path, data);
      break;
    case ".lp":
      axios.post("api/section/lp", { name: path });
      console.log(path);
      break;
    default:
      break;
  }
};
</script>
<style scoped>
.dialog-footer button:first-child {
  margin-right: 10px;
}
</style>
