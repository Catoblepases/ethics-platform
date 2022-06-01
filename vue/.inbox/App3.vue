<template>
  <el-button type="primary" style="margin-left: 16px" @click="setDrawer">
    open
  </el-button>

  <el-drawer
    v-model="drawer"
    title="I am the title"
    :direction="direction"
    :before-close="handleClose"
    :append-to-body="true"
    :close-on-click-modal="false"
  >
    <span>
      <el-input
        v-model="textarea"
        :rows="10"
        type="textarea"
        placeholder="Please input" />
      <el-input
        v-model="input"
        placeholder="Please input"
        @keydown.enter="addToTextArea"
    /></span>
  </el-drawer>
</template>

<script lang="ts" setup>
import { ref } from "vue";
const drawer = ref(false);
</script>

<script lang="ts">
import { defineComponent } from "vue";

export default {
  data() {
    return {
      input: "",
      textarea: "",
      drawer: false,
      direction: "btt",
    };
  },
  methods: {
    addToTextArea() {
      this.textarea = this.textarea + "\n" + this.input;
      this.input = "";
    },
    setDrawer() {
      this.drawer = !this.drawer;
    },
  },
};
</script>
