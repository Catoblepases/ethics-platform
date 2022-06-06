<template>
  <div id="mountNode"></div>
  <edit-menu
    ref="childRef"
    :dialogFormVisible="dialogFormVisible"
    :nodeId="nodeId"
    @onUpdate="onUpdate"
  ></edit-menu>
</template>

<script setup lang="ts">
import {
  defineComponent,
  onBeforeMount,
  onMounted,
  getCurrentInstance,
  provide,
  ref,
  defineAsyncComponent,
  onBeforeUpdate,
} from "vue";

import G6, { GraphData, TreeGraphData, Graph, Item } from "@antv/g6";
import axios from "axios";
import EditMenu from "./EditMenu.vue";
import insertCss from "insert-css";

insertCss(`
  .g6-component-toolbar li {
    list-style-type: none !important;
  }
`);

var graph: Graph;
let data: GraphData;

const onUpdate = () => {
  updateGraph();
  console.log("update:Graph");
};

const childRef = ref<any>();

const updateEditMenu = () => {
  childRef.value.update();
};

var carriageData = ref({});
provide("carriageData", carriageData);

var dialogFormVisible = ref(false);
provide("dialogFormVisible", dialogFormVisible);

var nodeId = ref("");
provide("nodeId", nodeId);

function runnigAlgorithme(position: Array<Item>, graph: Graph): void {
  setInterval(() => {
    position.forEach((pos) => {
      let item = graph.findById(pos.id);
      item.get("model").x = pos.x;
      item.get("model").y = pos.y;
      graph.refresh();
    });
    // graph.changeData(data);
  }, 600);
}

G6.registerNode(
  "carriageWithGroup",
  {
    draw(cfg, group) {
      let y: number = Math.round(7 / 2);
      let nb = cfg?.nb;
      const size = cfg?.size[0];
      var nbLine = Math.round(Math.sqrt(nb)) + 1;
      var h = Math.round(size / (nbLine + 1));

      const rect = group.addShape("rect", {
        zIndex: 10,
        attrs: {
          x: -12,
          y: -12,
          width: cfg?.size[0],
          height: cfg?.size[1],
          fill: cfg?.fill,
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
              x: h * j,
              y: h * i,
              r: 5,
              fill: "lightgreen",
            },
          });
          nb--;
        }
      }

      group.addShape("text", {
        attrs: {
          text: cfg?.label,
          x: -10,
          y: 0,
          fontSize: 8,
          textAlign: "left",
          textBaseline: "middle",
          fill: "white",
        },
        // must be assigned in G6 3.3 and later versions. it can be any value you want
        name: "text-shape",
      });

      return rect;
    },
  },
  "rect"
);

G6.registerNode("carriageWithbridge", {
  draw(cfg, group) {
    let y: number = Math.round(7 / 2);
    let nb = cfg?.nb;
    const size = cfg?.size[0];
    var nbLine = Math.round(Math.sqrt(nb)) + 1;
    var h = Math.round(size / (nbLine + 1));

    const rect = group.addShape("rect", {
      zIndex: 10,
      attrs: {
        x: -12,
        y: -12,
        width: cfg?.size[0],
        height: cfg?.size[1],
        fill: cfg?.fill,
      },
    });

    group.addShape("rect", {
      attrs: {
        x: -2,
        y: -15,
        width: 10,
        height: 30,
        fill: "#6bccdb",
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
            x: h * j,
            y: h * i,
            r: 5,
            fill: "lightgreen",
          },
        });
        nb--;
      }
    }

    group.addShape("text", {
      attrs: {
        text: cfg?.label,
        x: -10,
        y: 0,
        fontSize: 8,
        textAlign: "left",
        textBaseline: "middle",
        fill: "white",
      },
      // must be assigned in G6 3.3 and later versions. it can be any value you want
      name: "text-shape",
    });

    return rect;
  },
});

function changeStyleByType(nodes: Array<Node>) {
  nodes.forEach((node: Node) => {
    if (!node.style) {
      node.style = { fill: node.fill };
    }
    node.x = 120;
    node.y = 120;
    switch (node.typeName) {
      case "track": {
        node.type = "rect";
        node.size = [35, 20];
        if (node.infoCarriage.bridge != null) {
          node.type = "carriageWithbridge";
          node.nb = 1;
        }
        if (node.infoCarriage.group != null) {
          node.type = "carriageWithGroup";
          node.nb = node.infoCarriage.group.size;
        }
        break;
      }
      case "bridge": {
        node.type = "rect";
        node.size = [35, 20];
        break;
      }
      case "train": {
        node.type = "ellipse";
        node.size = [35, 20];
        break;
      }
    }
  });
}

const g6 = (data: GraphData | TreeGraphData | undefined) => {
  graph = new G6.Graph({
    container: "mountNode",
    width: 1300,
    height: 600,
    fitViewPadding: [20, 40, 50, 200],
    // fitView: true,
    plugins: [contextMenu, toolbar],
    layout: {
      type: "dagre",
      rankdir: "LR",
      align: "DL",
      nodesep: 10,
      ranksep: 20,
      controlPoints: true,
    },
    // animate: true,
    defaultNode: {
      shape: "circle",
      size: [100],
      color: "#5B8FF9",
      style: {
        fill: "steelblue",
        stroke: "#666",
        lineWidth: 1,
      },
      // Label text configuration on nodes
      labelCfg: {
        style: {
          fill: "#fff",
          fontSize: 9,
        },
      },
    },
    defaultEdge: {
      style: {
        stroke: "#e2e2e2",
        endArrow: true,
        startArrow: false,
      },
    },
    modes: {
      default: [
        "drag-canvas",
        "zoom-canvas",
        "drag-node",
        {
          type: "tooltip",
          formatText(model) {
            const text =
              "label: " + model.label + "<br/> typeName: " + model.typeName;
            return text;
          },
        },
      ],
    },
  });
  graph.data(data);
  graph.render();

  let canvas = graph.get("canvas");
  return graph;
};

const toolbar = new G6.ToolBar({
  position: { x: 0, y: 400 },
});

function sleep(ms: number) {
  return new Promise((resolve) => setTimeout(resolve, ms));
}

const contextMenu = new G6.Menu({
  getContent(evt) {
    let header;
    if (evt.target && evt.target.isCanvas && evt.target.isCanvas()) {
      header = "Canvas ContextMenu";
    } else if (evt.item) {
      const itemType = evt.item.getType();
      header = `${itemType.toUpperCase()} ContextMenu`;
    }
    return `
  <div id="contextmenu" class="menu">
      <div class="contextmenu_item" @click="cha(currentData)">edit carriage</div>
      <div class="contextmenu_item" @click="cha(currentData)">edit bridge</div>
      <div class="contextmenu_item" @click="cha(currentData)">edit switch</div>
      <div class="contextmenu_item" @click="cha(currentData)">edit group</div>
      <div class="contextmenu_item" @click="cha(currentData)">original position</div>
  </div>
  `;
  },
  handleMenuClick: (target, item) => {
    console.log(target.innerText, item.getModel().id);
    switch (target.innerText) {
      case "edit carriage":
        break;
      case "edit bridge":
        console.log(2);
        break;
      case "edit switch":
        console.log(3);
        break;
      case "edit group":
        console.log(4);
        break;
      case "original position":
        console.log(5);
        break;
    }
  },
  // offsetX and offsetY include the padding of the parent container
  offsetY: 0,
  itemTypes: ["node"],
});

const openContextMenu = () => {};

const openMenu = () => {
  dialogFormVisible.value = true;
};

const setNodeId = (id: string) => {
  nodeId.value = id;
};

const setCarriageData = (data: any) => {
  carriageData.value = data;
};

async function getData() {
  await axios.post("api/Node/addAll/test").then((res) => {});
  var _nodes = (await axios.get("api/Node")).data;
  var _edges = (await axios.get("api/Edge")).data;
  changeStyleByType(_nodes);
  data = { nodes: _nodes, edges: _edges };
  return data;
}

const initGraph = () => {
  axios.post("api/Node/addAll/test").then((res) => {
    axios.get("api/Node").then((res1) => {
      axios.get("api/Edge").then((res2) => {
        var _nodes = res1.data;
        var _edges = res2.data;
        changeStyleByType(_nodes);
        data = { nodes: _nodes, edges: _edges };
        graph = g6(data);
        graph.on("node:contextmenu", function (evt) {
          openContextMenu();
        });
        graph.on("node:dblclick", function (evt) {
          var id: string = evt.item?.getModel().id!;
          setNodeId(id);
          axios.get("api/carriage/" + id).then((res) => {
            setCarriageData(res.data);
            updateEditMenu();
            openMenu();
          });
        });
      });
    });
  });
  console.log("run:initGraph");
};


const updateGraph = () => {
  axios.post("api/Node/addAll/test").then((res) => {
    axios.get("api/Node").then((res1) => {
      axios.get("api/Edge").then((res2) => {
        var _nodes = res1.data;
        var _edges = res2.data;
        changeStyleByType(_nodes);
        data = { nodes: _nodes, edges: _edges };
        graph.changeData(data);
        graph.on("node:contextmenu", function (evt) {
          openContextMenu();
        });
        graph.on("node:dblclick", function (evt) {
          var id: string = evt.item?.getModel().id!;
          setNodeId(id);
          axios.get("api/carriage/" + id).then((res) => {
            setCarriageData(res.data);
            updateEditMenu();
            openMenu();
          });
        });
      });
    });
  });
  console.log("run:updateGraph");
};

initGraph();

onBeforeUpdate(() => {
  updateGraph();
});
</script>

<style>
/* 提示框的样式 */
.g6-tooltip {
  border: 1px solid #e2e2e2;
  border-radius: 4px;
  font-size: 12px;
  color: #545454;
  background-color: rgba(255, 255, 255, 0.9);
  padding: 10px 8px;
  box-shadow: rgb(174, 174, 174) 0px 0px 10px;
}

.menu {
  position: absolute;
  background-color: #fff;
  width: 100px;
  /*height: 106px;*/
  font-size: 12px;
  color: #444040;
  border-radius: 4px;
  -webkit-box-sizing: border-box;
  box-sizing: border-box;
  border-radius: 3px;
  border: 1px solid rgba(0, 0, 0, 0.15);
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.175);
  white-space: nowrap;
  z-index: 1000;
}

.contextmenu_item {
  display: block;
  line-height: 34px;
  text-align: center;
}

.el-row {
  margin-bottom: 20px;
}
.el-row:last-child {
  margin-bottom: 0;
}
.el-col {
  border-radius: 4px;
}

.el-dropdown {
  border-color: #66b1ff;
  color: #31475e;
  /* background: #66b1ff; */
}

.grid-content {
  border-radius: 4px;
  min-height: 36px;
}
</style>
