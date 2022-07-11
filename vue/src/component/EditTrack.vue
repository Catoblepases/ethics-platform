<template>
  <el-dialog v-model="trackEditVisible" title="add new Track">
    <el-form :inline="true">
      <el-form-item label="name">
        <el-input v-model="form.name"></el-input>
      </el-form-item>
      <el-form-item label="length" v-model="form.length">
        <el-input-number v-model="form.length"></el-input-number>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="trackEditVisible = false">Cancel</el-button>
        <el-button type="primary" @click="apply">Apply</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
import axios from "axios";
import { ElMessage } from "element-plus";
import { inject, reactive } from "vue";
import { updateClingo } from "./updateFile";

const trackEditVisible = inject("trackEditVisible");

const form = reactive({ name: "", length: 0 });

const emit = defineEmits(["updateGraph"]);

function apply() {
  if (form.name === "" || form.length === 0) {
    ElMessage("bad format!");
    return;
  }
  axios
    .post("api/section/addTrack", { name: form.name, length: form.length })
    .then(() => {
      ElMessage("submit track success!");
      updateClingo();
      emit("updateGraph");
    });
}
</script>
