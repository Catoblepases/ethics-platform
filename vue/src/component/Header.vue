<template>
  <!-- <el-affix :offset="0"> -->
  <!-- <el-divider /> -->
  <p></p>
  <el-row>
    <el-col :span="1"> </el-col>
    <el-col :span="2">
      <el-dropdown
        max-height="50"
        hide-on-click="false"
        @command="handleCommand"
      >
        <span class="el-dropdown-link">
          file<el-icon class="el-icon--right"><arrow-down /></el-icon>
        </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item command="a">save file</el-dropdown-item>
            <el-dropdown-item command="b">import file</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </el-col>
    <el-col :span="2">
      <el-dropdown
        max-height="50"
        hide-on-click="false"
        @command="handleCommand"
      >
        <span class="el-dropdown-link">
          setting<el-icon class="el-icon--right"><arrow-down /></el-icon>
        </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item command="a">show context</el-dropdown-item>
            <el-dropdown-item command="clingo">run clingo</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </el-col>
    <el-col :span="2">
      <el-switch
        v-model="collapse"
        class="mb-2"
        active-text="side bar"
        inactive-text=""
        @click="update"
      />
    </el-col>
    <el-col :span="2">
      <el-upload
        ref="upload"
        class="upload-demo"
        action="https://jsonplaceholder.typicode.com/posts/"
        :limit="1"
        :on-exceed="handleExceed"
        :auto-upload="false"
      >
        <template #trigger>
          <el-button type="primary">select file</el-button>
        </template>
      </el-upload>
    </el-col>
    <el-col :span="3">
      <el-button type="primary" @click="showDownloadMenu">download</el-button>
    </el-col>
    <el-col :span="1">
      <el-button type="primary">run</el-button>
    </el-col>
    <el-col :span="2">
      <el-button type="primary">run one step</el-button>
    </el-col>
    <el-col :span="1">
      <el-button type="primary">go back</el-button>
    </el-col>
  </el-row>
  <el-divider />

  <download-file></download-file>
  <!-- </el-affix> -->
</template>

<script lang="ts" setup>
import { ElMessage } from "element-plus";
import { ArrowDown } from "@element-plus/icons-vue";
import { ref, provide } from "vue";
import { genFileId } from "element-plus";
import downloadFile from "./downloadFile.vue";
import type { UploadInstance, UploadProps, UploadRawFile } from "element-plus";

let collapse = ref(true);

const downVisible = ref(false);
provide("downVisible", downVisible);

const showDownloadMenu = () => {
  downVisible.value = true;
};

const handleCommand = (command: string | number | object) => {
  switch (command) {
    case "clingo":
      break;

    default:
      break;
  }
  ElMessage(`click on item ${command}`);
};

// upload
const upload = ref<UploadInstance>();

const handleExceed: UploadProps["onExceed"] = (files) => {
  upload.value!.clearFiles();
  const file = files[0] as UploadRawFile;
  file.uid = genFileId();
  upload.value!.handleStart(file);
};

// collapse side bar
const emit = defineEmits(["onChange"]);
const update = () => {
  console.log("onChange:child");
  emit("onChange", collapse.value);
};

defineExpose({ collapse });
</script>

<style scoped>
.example-showcase .el-dropdown-link {
  cursor: pointer;
  color: var(--el-color-primary);
  display: flex;
  align-items: center;
}
</style>
