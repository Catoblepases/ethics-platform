<template>
  <div v-if="showCommand">
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
  </div>
</template>

<script lang="ts" setup>
import { ref } from "vue";
let input = "";
let textarea = "";
function addToTextArea() {
  textarea = textarea.concat("\n" + input);
  input = "";
}
</script>
