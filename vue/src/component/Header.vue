<template>
  <el-affix :offset="0">
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
              <el-dropdown-item command="b">run clingo</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </el-col>
    </el-row>

    <el-divider />
  </el-affix>
</template>

<script lang="ts" setup>
import { ElMessage } from "element-plus";
import { ArrowDown } from "@element-plus/icons-vue";

const handleCommand = (command: string | number | object) => {
  ElMessage(`click on item ${command}`);
};
</script>
<style scoped>
.example-showcase .el-dropdown-link {
  cursor: pointer;
  color: var(--el-color-primary);
  display: flex;
  align-items: center;
}
</style>
