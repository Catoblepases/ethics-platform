<template>
  <div ref="childRef">
    <ul>
      <li v-for="item in jsonData" :key="item.name">
        {{ item.name }} - {{ item.age }}
      </li>
    </ul>
  </div>
</template>

<script setup>
import { syncRefs } from "@vueuse/core";
import { ref } from "vue";

function fetchData() {
  return axios.post("api/Node/addAll/test").data;
}

ref: jsonData = await fetchData();
defineExpose({ jsonData });
</script>
