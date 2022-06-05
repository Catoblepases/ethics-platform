<template>
  <el-tabs type="border-card" tab-position="left" class="demo-tabs">
    <el-tab-pane label="Event" @click="getEvent">
      <event-menu></event-menu>
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
import EventMenu from "./EventMenu.vue";
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
