<template>
  <div id="mountNode"></div>
</template>

<script setup lang="ts">
import G6, { GraphData, Graph, Item } from "@antv/g6";
import { onMounted } from "vue";

G6.registerNode("customNode", {
  draw(item, group) {
    const rect = group.addShape("rect", {
      zIndex: 10,
      attrs: {
        x: 0,
        y: 0,
        width: 50,
        height: 50,
        fill: "red",
      },
    });

    const circle = group.addShape("circle", {
      zIndex: 10,
      attrs: {
        x: 0,
        y: 0,
        r: 10,
        fill: "blue",
      },
    });

    return rect;
  },
});

// for (let i = 0; i < nbLine; i++) {
//   for (let j = 0; j < nbLine; j++) {
//     gc.fillOval(hx * i, hy * j, hx, hy);
//     --nb;
//     if (nb <= 0) break;
//   }
//   if (nb <= 0) break;
// }

G6.registerNode(
  "group",
  {
    draw(cfg, group) {
      // let nb = cfg?.nb;
      // const size = cfg?.size;
      // const color = cfg?.color;

      // console.log(nb);

      // var nbLine = Math.sqrt(nb) + 1;
      // var hx = size / (nbLine + 1);
      // var hy = size / (nbLine + 1);

      // let keyShape;
      // for (let i = 0; i < nbLine; i++) {
      //   for (let j = 0; j < nbLine; j++) {
      //     if (i === 0 && j === 0) {
      //       keyShape = group.addShape("circle", {
      //         attrs: {
      //           x: hx * i,
      //           y: hy * j,
      //           r: 50,
      //           fill: color,
      //         },
      //         name: "circle-shape" + nb,
      //       });
      //     } else {
      //       group.addShape("circle", {
      //         attrs: {
      //           x: hx * i,
      //           y: hy * j,
      //           r: 50,
      //           fill: color,
      //         },
      //         name: "circle-shape" + nb,
      //       });
      //     }
      //     nb = nb - 1;
      //     if (nb <= 0) break;
      //   }
      //   if (nb <= 0) break;
      // }
      const keyShape = group.addShape("circle", {
        zIndex: 10,
        attrs: {
          x: 0,
          y: 0,
          r: 10,
          fill: "blue",
        },
      });
      return keyShape;
    },
  },
);

const data = {
  nodes: [
    { id: "node1", x: 50, y: 100, type: "group", nb: 5, size: 50 }, // 最简单的
    { id: "node5", x: 50, y: 100, type: "customNode", nb: 5 }, // 最简单的
    { id: "node2", x: 150, y: 100, type: "diamond", size: [50, 100] }, // 添加宽高
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
