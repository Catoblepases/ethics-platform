<template>
  <el-scrollbar>
    <el-form :model="form" label-width="120px" width="600px">
      <el-form-item label="action theory">
        <el-row>
          <el-col span="8">
            <el-input v-model="form.actionTheory" disabled />
          </el-col>
          <el-col span="2"> <span class="text-gray-500"> - </span> </el-col>
          <el-col span="4">
            <el-upload
              ref="upload"
              class="upload-demo"
              action="https://jsonplaceholder.typicode.com/posts/"
              :limit="1"
              :auto-upload="false"
            >
              <template #trigger>
                <el-button type="primary">select file</el-button>
              </template>
            </el-upload>
          </el-col>
        </el-row>
      </el-form-item>

      <el-form-item label="Context">
        <el-row>
          <el-col span="8">
            <el-input v-model="form.context" disabled />
          </el-col>
          <el-col span="2"> <span class="text-gray-500"> - </span> </el-col>
          <el-col span="4">
            <el-upload
              ref="upload"
              class="upload-demo"
              action="https://jsonplaceholder.typicode.com/posts/"
              :limit="1"
              :auto-upload="false"
            >
              <template #trigger>
                <el-button type="primary">select file</el-button>
              </template>
            </el-upload>
          </el-col>
        </el-row>
      </el-form-item>

      <el-form-item label="causal theory">
        <el-row>
          <el-col span="8">
            <el-input v-model="form.causalTheory" disabled />
          </el-col>
          <el-col span="2"> <span class="text-gray-500"> - </span> </el-col>
          <el-col span="4">
            <el-upload
              ref="upload"
              class="upload-demo"
              action="https://jsonplaceholder.typicode.com/posts/"
              :limit="1"
              :auto-upload="false"
            >
              <template #trigger>
                <el-button type="primary">select file</el-button>
              </template>
            </el-upload>
          </el-col>
        </el-row>
      </el-form-item>

      <el-form-item label="ethical spec">
        <el-row>
          <el-col span="8">
            <el-input v-model="form.ethicalSpec" disabled />
          </el-col>
          <el-col span="2"> <span class="text-gray-500"> - </span> </el-col>
          <el-col span="4">
            <el-upload
              ref="upload"
              class="upload-demo"
              action="https://jsonplaceholder.typicode.com/posts/"
              :limit="1"
              :auto-upload="false"
            >
              <template #trigger>
                <el-button type="primary">select file</el-button>
              </template>
            </el-upload>
          </el-col>
        </el-row>
      </el-form-item>

      <el-form-item label="ethical theories">
        <el-row>
          <el-col span="8">
            <el-input v-model="form.ethicalTheories" disabled />
          </el-col>
          <el-col span="2"> <span class="text-gray-500"> - </span> </el-col>
          <el-col span="4">
            <el-upload
              ref="upload"
              class="upload-demo"
              action="https://jsonplaceholder.typicode.com/posts/"
              :limit="1"
              :auto-upload="false"
            >
              <template #trigger>
                <el-button type="primary">select file</el-button>
              </template>
            </el-upload>
          </el-col>
        </el-row>
      </el-form-item>

      <el-form-item label="trace">
        <el-row>
          <el-col span="8">
            <el-input v-model="form.trace" disabled />
          </el-col>
          <el-col span="2"> <span class="text-gray-500"> - </span> </el-col>
          <el-col span="4">
            <el-upload
              ref="upload"
              class="upload-demo"
              action="https://jsonplaceholder.typicode.com/posts/"
              :limit="1"
              :auto-upload="false"
            >
              <template #trigger>
                <el-button type="primary">select file</el-button>
              </template>
            </el-upload>
          </el-col>
        </el-row>
      </el-form-item>

      <el-form-item label="causal trace">
        <el-row>
          <el-col span="8">
            <el-input v-model="form.causalTrace" disabled />
          </el-col>
          <el-col span="2"> <span class="text-gray-500"> - </span> </el-col>
          <el-col span="4">
            <el-upload
              ref="upload"
              class="upload-demo"
              action="https://jsonplaceholder.typicode.com/posts/"
              :limit="1"
              :auto-upload="false"
            >
              <template #trigger>
                <el-button type="primary">select file</el-button>
              </template>
            </el-upload>
          </el-col>
        </el-row>
      </el-form-item>

      <el-form-item label="output">
        <el-col span="10">
          <el-input v-model="form.causalTrace" disabled />
        </el-col>
      </el-form-item>

      <el-form-item>
        <el-row>
          <el-button type="primary" @click="onSubmit">Download</el-button>
          <el-button type="primary" @click="onSubmit">upload</el-button>
          <el-button>Cancel</el-button>
        </el-row>
      </el-form-item>
    </el-form>
  </el-scrollbar>
</template>

<script lang="ts" setup>
import { reactive } from "vue";

// do not use same name with ref
const form = reactive({
  actionTheory: "",
  context: "",
  causalTheory: "",
  ethicalSpec: "",
  ethicalTheories: false,
  trace: [],
  causalTrace: "",
  results: "",
  outPut: "",
});

const onSubmit = () => {
  console.log("submit!");
};
</script>
