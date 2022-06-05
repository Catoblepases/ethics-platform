<template>
  <el-form-item :label="clabel">
    <el-row>
      <el-col span="8">
        <el-input v-model="cmodel" :disabled="disabled" />
      </el-col>
      <el-col span="4">
        <el-upload
          ref="upload"
          class="upload-demo"
          action="https://jsonplaceholder.typicode.com/posts/"
          :limit="1"
          :auto-upload="false"
        >
          <template #trigger>
            <el-tooltip
              class="box-item"
              effect="dark"
              content="select file"
              placement="bottom"
            >
              <el-button :icon="Upload" circle></el-button>
            </el-tooltip>
          </template>
        </el-upload>
      </el-col>
    </el-row>
  </el-form-item>
</template>

<script lang="ts" setup>
import { ref } from "vue";
import { Upload } from "@element-plus/icons-vue";
const props = defineProps({
  cmodel: String,
  clabel: String,
  disabled: Boolean,
});
</script>
