<template>
  <el-dialog v-model="analyse" title="Clingo Result Analyse" width="90%" height="90%">
    <el-tabs v-model="editableTabsValue" type="card" class="demo-tabs">
      <el-tab-pane
        v-for="item in items"
        :key="item.name"
        :label="item.title"
        :name="item.name"
      >
        <el-switch
          v-model="transterm"
          @click="updateTransterm"
          inactive-text="hide Transterm"
        ></el-switch>
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
const transterm = ref(true);

class tabItem {
  name: string = "clingo";
  title: string = "clingo";
  data: any = { nodes: [], edges: [] };
  graph: Graph | undefined = undefined;
}

var items = ref(new Array<tabItem>());

function deleteTransterm(data: Array<any>) {
  let res = [];
  for (let index = 0; index < data.length; index++) {
    const element = data[index];
    if (element.connectType != "transTerm") {
      res.push(element);
    }
  }
  return res;
}

function hideSeparatedNode(g: Graph) {
  let nnodes = g.getNodes();
  for (let index = 0; index < nnodes.length; index++) {
    const element = nnodes[index];
    const edges = element.getEdges();
    let visible = false;
    for (let idx = 0; idx < edges.length; idx++) {
      const edge = edges[idx];
      if (edge.isVisible()) {
        visible = true;
        break;
      }
    }
    if (visible) {
      element.show();
    } else {
      element.hide();
    }
  }
}

function showAllNodes(g: Graph) {
  let nnodes = g.getNodes();
  for (let index = 0; index < nnodes.length; index++) {
    const element = nnodes[index];
    element.show();
  }
}

function hideTransterm(data: Array<any>, g: Graph, show: boolean) {
  for (let index = 0; index < data.length; index++) {
    const element = data[index];
    if (element.connectType === "transTerm") {
      const item = g.findById(element.id);
      if (show) {
        item.show();
      } else {
        item.hide();
      }
    }
  }
  g.refresh();
  g.updateLayout();
}

function updateTransterm() {
  console.log("update");
  for (let index = 0; index < items.value.length; index++) {
    const element = items.value[index];
    showAllNodes(element.graph);
    hideTransterm(element.data.edges, element.graph, transterm.value);
    hideSeparatedNode(element.graph);
  }
}

function changeStyleByType(nodes: Array<Node>) {
  nodes.forEach((node: Node) => {
    if (!node.style) {
      node.style = { fill: node.fill };
    }
    node.label = node.id;
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
          edge.style.stroke = "#089620";
          edge.labelCfg.style.fill = "green";
          break;
        case "rprevents":
          edge.style.stroke = "#9b2b09";
          edge.labelCfg.style.fill = "red";
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

const initGraphAnalyse = async () => {
  let dataT = { name: "traceTrollC.lp" };
  items.value = [];
  await axios.post("api/causal", dataT).then((res) => {
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
        element.graph = g6(element.data, element.name);
      }
      updateTransterm();
    }, 10);
  });
};

onMounted(() => {});

defineExpose({ initGraphAnalyse });
</script>
