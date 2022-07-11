<template>
  <q-input
    v-model="textarea"
    type="textarea"
    readonly
    clearable
    placeholder="Please input"
  />
  <q-input
    v-model="input"
    placeholder="Please input"
    clear-icon="close"
    @keydown.enter="addToTextArea"
  />
</template>

<script lang="ts" setup>
import axios from "axios";
import { ref } from "vue";
import { updateClingo } from "./updateFile";
let input = ref("");
let textarea = ref("");

const emit = defineEmits(["updateGraph"]);

function addToTextArea() {
  textarea.value = textarea.value.concat("\n" + input.value);
  axios.post("api/section/command", { content: input.value }).then(() => {
    input.value = "";
    updateClingo();
    emit("updateGraph");
  });
}
</script>
