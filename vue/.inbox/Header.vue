<template>
  <div class="tool-bar" id="tool-bar">
    <p></p>
    <el-row>
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
        <el-divider direction="vertical" />
      </el-col>
      <el-col :span="2">
        <el-button type="success" @click="updateClingo">update</el-button>
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
  </div>
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
  Refresh,
} from "@element-plus/icons-vue";
import { ref, provide } from "vue";
import { genFileId } from "element-plus";
import downloadFile from "./downloadFile.vue";
import clingoAnalyse from "./ClingoAnalyse.vue";
import clingoResult from "./ClingoResult.vue";
import type { UploadInstance, UploadProps, UploadRawFile } from "element-plus";
import axios from "axios";

let collapse = ref(true);
let currentSimulationName = ref("");
const simulationNames = ref<Array<string>>([""]);
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

async function updateSimulations() {
  await axios.get("api/section/simulation").then((res) => {
    let data = res.data.data;
    let names: Array<string> = [];
    for (let index = 0; index < data.length; index++) {
      const element = data[index];
      names.push(element.name);
    }
    simulationNames.value = names;
    getSimulation();
  });
}

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

const updateClingo = () => {
  axios.post("api/section/updateAction").then(() => {
    axios.post("api/clingo/update").then(() => {
      console.log("update:clingo");
    });
  });
};

const handleExceed: UploadProps["onExceed"] = (files) => {
  upload.value!.clearFiles();
  const file = files[0] as UploadRawFile;
  file.uid = genFileId();
  upload.value!.handleStart(file);
};

// collapse side bar
const emit = defineEmits([
  "onChange",
  "initSimulation",
  "runOneStep",
  "runOneStepBack",
  "runAll",
  "getSimulation",
]);
const update = () => {
  console.log("onChange:child");
  emit("onChange", collapse.value);
};

const initSimulation = () => {
  console.log("init:g6simulation");
  emit("initSimulation");
};

const runOneStep = () => {
  console.log("header:runOneStep");
  emit("runOneStep");
};

const runOneStepBack = () => {
  console.log("header:runOneStepBack");
  emit("runOneStepBack");
};

const runAll = () => {
  console.log("header:runAll");
  emit("runAll");
};

function getSimulation() {
  emit("getSimulation");
}

function getCurrentSimulationName() {
  return currentSimulationName.value;
}

defineExpose({ collapse, getCurrentSimulationName, currentSimulationName });
</script>

<style scoped>
.example-showcase .el-dropdown-link {
  cursor: pointer;
  color: var(--el-color-primary);
  display: flex;
  align-items: center;
}
</style>
