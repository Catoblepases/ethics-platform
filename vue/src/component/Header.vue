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
    <el-col :span="1">
      <el-upload
        ref="upload"
        class="upload-demo"
        action="https://jsonplaceholder.typicode.com/posts/"
        :limit="1"
        :on-exceed="handleExceed"
        :auto-upload="false"
      >
        <template #trigger>
          <el-tooltip
            class="box-item"
            effect="dark"
            content="select file"
            placement="bottom"
          >
            <el-button type="primary" :icon="Upload" circle></el-button>
          </el-tooltip>
        </template>
      </el-upload>
    </el-col>
    <el-col :span="1">
      <el-tooltip
        class="box-item"
        effect="dark"
        content="download"
        placement="bottom"
      >
        <el-button
          type="primary"
          :icon="Download"
          circle
          @click="showDownloadMenu"
        ></el-button>
      </el-tooltip>
    </el-col>
    <el-col :span="1">
      <el-divider direction="vertical" />
    </el-col>
    <el-col :span="1">
      <el-tooltip
        class="box-item"
        effect="dark"
        content="run/simulation"
        placement="bottom"
      >
        <el-button :icon="VideoPlay" circle></el-button>
      </el-tooltip>
    </el-col>
    <el-col :span="1">
      <el-tooltip
        class="box-item"
        effect="dark"
        content="run one step"
        placement="bottom"
      >
        <el-button :icon="ArrowRight" circle></el-button>
      </el-tooltip>
    </el-col>
    <el-col :span="1">
      <el-tooltip
        class="box-item"
        effect="dark"
        content="back one step"
        placement="bottom"
      >
        <el-button :icon="ArrowLeft" circle></el-button>
      </el-tooltip>
    </el-col>
    <el-col :span="1">
      <el-divider direction="vertical" />
    </el-col>
    <el-col :span="2">
      <el-button type="success" @click="showAnalyse">causal tree</el-button>
    </el-col>
    <el-col :span="2">
      <el-button type="success" @click="showResult">clingo result</el-button>
    </el-col>
  </el-row>
  <el-divider />

  <download-file></download-file>
  <clingo-analyse ref="ARef"></clingo-analyse>
  <clingo-result ref="RRef"></clingo-result>
  <!-- </el-affix> -->
</template>

<script lang="ts" setup>
import { ElMessage } from "element-plus";
import {
  ArrowDown,
  ArrowLeft,
  ArrowRight,
  VideoPlay,
  Upload,
  Download,
} from "@element-plus/icons-vue";
import { ref, provide } from "vue";
import { genFileId } from "element-plus";
import downloadFile from "./downloadFile.vue";
import clingoAnalyse from "./ClingoAnalyse.vue";
import clingoResult from "./ClingoResult.vue";
import type { UploadInstance, UploadProps, UploadRawFile } from "element-plus";
import { tr } from "element-plus/lib/locale";

let collapse = ref(true);
const ARef = ref<any>();
const RRef = ref<any>();

let analyse = ref(false);
provide("analyse", analyse);

let result = ref(false);
provide("result", result);

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

const showAnalyse = () => {
  analyse.value = true;
  ARef.value.initGraphAnalyse();
};

const showResult = () => {
  result.value = true;
  RRef.value.initGraphAnalyse();
};

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
