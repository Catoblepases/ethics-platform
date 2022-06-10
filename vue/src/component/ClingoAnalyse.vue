<template>
  <el-dialog
    v-model="analyse"
    title="Clingo Result Analyse"
    width="90%"
    height="90%"
    :before-close="handleClose"
  >
    <el-tabs v-model="editableTabsValue" type="card" class="demo-tabs">
      <el-tab-pane
        v-for="item in items"
        :key="item.name"
        :label="item.title"
        :name="item.name"
      >
        <div :id="item.name"></div>
      </el-tab-pane>
    </el-tabs>

    <template #footer>
      <span class="dialog-footer">
        <el-button @click="analyse = false">Cancel</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
import G6, { GraphData, TreeGraphData, Graph, Item, Edge } from "@antv/g6";
import { inject, onBeforeMount, onMounted, ref } from "@vue/runtime-core";
import axios from "axios";

let analyse = inject("analyse");
const editableTabsValue = ref("s0");

class tabItem {
  name: string = "clingo";
  title: string = "clingo";
  data: any = { nodes: [], edges: [] };
}
var items = ref(new Array<tabItem>());

function changeStyleByType(nodes: Array<Node>) {
  nodes.forEach((node: Node) => {
    if (!node.style) {
      node.style = {};
    }
    node.x = 120;
    node.y = 120;
    node.type = "rect";
    node.size = [100, 30];
  });
}

function changeEdgeStyleByType(edges: Array<Edge>) {
  edges.forEach((edge: Edge) => {
    edge.label = edge.connectType;
    edge.labelCfg = { autoRotate: true, style: { fill: "grey" } };
    if (!edge.style) {
      edge.style = { stroke: "#e2e2e2", endArrow: true, startArrow: false };
      switch (edge.connectType) {
        case "rcauses":
          edge.style.stroke = "#9b2b09";
          edge.labelCfg.style.fill = "red";
          break;
        case "rprevents":
          edge.style.stroke = "#089620";
          edge.labelCfg.style.fill = "green";
          break;
        case "transTerm":
          edge.style.stroke = "#0a62aa";
          edge.labelCfg.style.fill = "steelblue";
          break;
        default:
          break;
      }
    }
  });
}

const g6 = (data: GraphData | TreeGraphData | undefined, container: string) => {
  const graph = new G6.Graph({
    container: container,
    width: 1200,
    height: 400,
    fitViewPadding: [20, 40, 50, 10],
    // fitView: true,
    plugins: [toolbar],
    layout: {
      type: "dagre",
      rankdir: "TB",
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

var data: GraphData = {};
var graph: Graph | undefined = undefined;

const initGraphAnalyse = () => {
  // graph = undefined;
  if (graph === undefined) {
    let dataT = { name: "traceTrollC.lp" };
    axios.post("api/causal", dataT).then((res) => {
      console.log(res);
      let causalTrees: Array<any> = res.data.causalTrees;

      for (let index = 0; index < causalTrees.length; index++) {
        const tr = causalTrees[index];
        console.log(tr);

        const item = new tabItem();
        item.name = tr.name;
        item.title = tr.name;
        let nodes = tr.nodeEventList;
        let edges = tr.edgeEvents;
        changeEdgeStyleByType(edges);
        changeStyleByType(nodes);
        item.data = {
          nodes: nodes,
          edges: edges,
        };
        items.value.push(item);
      }
      setTimeout(() => {
        for (let index = 0; index < items.value.length; index++) {
          const element = items.value[index];
          console.log(element.data);
          graph = g6(element.data, element.name);
        }
      }, 1);
    });
  }
};

onMounted(() => {});

defineExpose({ initGraphAnalyse });
</script>
