<template>
  <div id="mountNode"></div>
</template>

<script setup lang="ts">
import G6, { GraphData, Graph, Item } from "@antv/g6";
import { onMounted } from "vue";

G6.registerNode(
  "sectionWithGroup",
  {
    draw(cfg, group) {
      let y: number = Math.round(7 / 2);
      let nb = cfg?.nb;
      const size = cfg?.size[0];
      const color = cfg?.color;
      var nbLine = Math.round(Math.sqrt(nb)) + 1;
      var h = Math.round(size / (nbLine + 1));

      const rect = group.addShape("rect", {
        zIndex: 10,
        attrs: {
          x: -12,
          y: -12,
          width: cfg?.size[0],
          height: cfg?.size[1],
          fill: color,
        },
      });

      for (let i = 0; i < nbLine; i++) {
        if (nb <= 0) {
          break;
        }
        for (let j = 0; j < nbLine; j++) {
          if (nb <= 0) {
            break;
          }
          group.addShape("circle", {
            zIndex: 10,
            attrs: {
              x: h * i,
              y: h * j,
              r: 5,
              fill: "lightgreen",
            },
          });
          nb--;
        }
      }

      return rect;
    },
  },
  "single-node"
);

const data = {
  nodes: [
    {
      id: "node5",
      x: 50,
      y: 100,
      label: "oo",
      type: "customNode",
      nb: 5,
      color: "steelblue",
      size: 50,
    }, // 最简单的
    {
      id: "node2",
      x: 150,
      y: 100,
      color: "blue",
      type: "diamond",
      size: [50, 100],
    }, // 添加宽高
    { id: "node3", x: 250, y: 100, color: "red", type: "diamond" }, // 添加颜色
    { id: "node4", x: 350, y: 100, label: "菱形", type: "diamond" }, // 附加文本
  ],
};

const tc = document.createElement("div");
tc.id = "Container";
document.body.appendChild(tc);
const graph = new G6.Graph({
  container: "Container",
  width: 500,
  height: 500,
});
graph.data(data);

graph.render();
</script>
