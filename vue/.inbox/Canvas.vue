<template>
  <canvas id="canvas" width="1280" height="480" />
</template>

<script lang="ts">
import { defineComponent, onMounted } from "vue";
import { fabric } from "fabric";
import axios from "axios";

export default defineComponent({
  setup() {
    onMounted(() => {
      let canvas = new fabric.Canvas("canvas");
      const rect = new fabric.Rect({
        left: 100,
        top: 100,
        fill: "red",
        width: 20,
        height: 20,
        hasControls: false,
      });
      canvas.add(rect);

      axios.get("/data/test.json").then((res) => {
        res.data.forEach((Element: string) => {
          console.log(Element);
        });
      });
    });
  },
});
</script>
