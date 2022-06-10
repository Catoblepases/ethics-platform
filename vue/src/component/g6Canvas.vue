<template>
  <el-row>
    <el-col span="1">
      <el-tooltip content="editMode" placement="right">
        <el-icon :size="20" :color="color">
          <Edit @click="switchMode" />
        </el-icon>
      </el-tooltip>
    </el-col>
    <el-col span="1">
      <el-tooltip content="refresh layout" placement="right">
        <el-icon :size="20" :color="color">
          <Refresh @click="refreshLayout" />
        </el-icon>
      </el-tooltip>
    </el-col>
    <el-col span="12">
      <el-scrollbar>
        <div id="mountNode"></div>
      </el-scrollbar>
    </el-col>
  </el-row>
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
import { stateStyle, registerNodes } from "./nodes";

var graph: Graph;
let data: GraphData;

let editMode = ref(true);
let color = ref("#409EFC");

registerNodes(G6);

const switchMode = () => {
  if (editMode.value === true) {
    color.value = "";
    editMode.value = false;
    // graph.setMode("animation");
    fixNodesPosition();
  } else {
    color.value = "#409EFC";
    editMode.value = true;
    graph.setMode("default");
  }
};

function refreshLayout() {
  graph.updateLayout({});
}

function fixNodesPosition() {
  let array: Array<any> = data.nodes;
  for (let index = 0; index < array.length; index++) {
    const element = array[index];
    let item = graph.findById(element.id);
    const model = item.get("model");
    
    model.fx = model.x;
    model.fy = model.y;
    item.getModel().fx = model.x;
    item.getModel().fy = model.y;
    item.refresh();
  }
}

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

function changeStyleByType(nodes: Array<Node>) {
  nodes.forEach((node: Node) => {
    if (!node.style) {
      node.style = { fill: node.fill, cfill: node.fill, gcolor: "lightgreen" };
    }
    node.x = undefined;
    node.y = undefined;
    switch (node.typeName) {
      case "track": {
        node.type = "rect";
        node.size = [35, 20];
        if (node.infoCarriage.group != null) {
          node.type = "carriageWithGroup";
          node.nb = node.infoCarriage.group.size;
        }
        if (node.infoCarriage.bridge != null) {
          if (node.infoCarriage.bridge.group != null) {
            node.type = "carriageWithbridgeandgroup";
            node.infoCarriage.bridge.group.size;
          }
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

const dagreLayout = {
  type: "dagre",
  rankdir: "LR",
  align: "DL",
  layer: 0,
  nodesep: 10,
  ranksep: 20,
  controlPoints: true,
};

const g6 = (data: GraphData | TreeGraphData | undefined) => {
  graph = new G6.Graph({
    container: "mountNode",
    width: 1300,
    height: 600,
    fitViewPadding: [20, 40, 50, 200],
    // fitView: true,
    plugins: [toolbar],
    layout: dagreLayout,
    animate: true,
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
    nodeStateStyles: stateStyle,
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
      animation: ["click-select"],
    },
  });
  graph.data(data);
  graph.render();

  let canvas = graph.get("canvas");
  let circle = canvas.addShape("circle", {
    attrs: {
      x: 150 + 200,
      y: 150,
      r: 20,
      stroke: "black",
    },
  });
  graph.refresh();

  let item: Item = graph.findById("main(3)");
  // graph.setItemState(item, "group", "dead");

  setTimeout(() => {
    let item: Item = graph.findById("main(3)");
    // let posX = item.get("model").x;
    // let posY = item.get("model").y;

    graph.setItemState(item, "group", "dead");
  }, 1000);

  return graph;
};

const toolbar = new G6.ToolBar({
  position: { x: 0, y: 400 },
});

function sleep(ms: number) {
  return new Promise((resolve) => setTimeout(resolve, ms));
}

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
