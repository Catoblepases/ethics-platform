<template>
  <q-toolbar class="text-dark">
    <q-btn flat round dense icon="refresh" @click="initSimulation">
      <q-tooltip>refresh simulation</q-tooltip>
    </q-btn>
    <q-btn flat round dense icon="play_arrow" @click="runAll">
      <q-tooltip>run/simulation</q-tooltip>
    </q-btn>
    <q-btn flat round dense icon="chevron_right" @click="runOneStep">
      <q-tooltip>run one step</q-tooltip>
    </q-btn>
    <q-btn flat round dense icon="chevron_left" @click="runOneStepBack">
      <q-tooltip>run one step back</q-tooltip>
    </q-btn>
    <q-toolbar-title>
      <q-select
        borderless
        v-model="currentSimulationName"
        :options="simulationNames"
        loading="true"
        @virtual-scroll="updateSimulations"
        style="max-width: 150px"
      >
        <template v-slot:after>
          <q-badge color="dark"> simulation </q-badge>
        </template>
      </q-select>
    </q-toolbar-title>
    <q-btn flat round dense icon="edit" @click="switchMode" :style="editStyle">
      <q-tooltip>switch mode</q-tooltip>
    </q-btn>
    <q-btn flat round dense icon="refresh" @click="refreshLayout">
      <q-tooltip>refreshLayout</q-tooltip>
    </q-btn>
    <q-btn flat round dense icon="more_vert" />
  </q-toolbar>
  <div id="mountNode"></div>
  <edit-menu
    ref="childRef"
    :dialogFormVisible="dialogFormVisible"
    :nodeId="nodeId"
    @onUpdate="updateGraph"
  ></edit-menu>
  <edit-track @updateGraph="updateGraph"></edit-track>
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
import EditTrack from "./EditTrack.vue";
import {
  stateStyle,
  registerNodes,
  trainSetup,
  dagreLayout,
  changeStyleByType,
  defaultNodeStyle,
  defaultEdgeStyle,
  defaultMode,
} from "./nodes";
import { Notify } from "quasar";
import { updateClingo } from "./updateFile";
// ("");

var graph: Graph;
let data: GraphData;

let editMode = ref(true);
let trackEditVisible = ref(false);
const currentSimulationName = ref("");
const simulationNames = ref<Array<string>>([""]);

provide("trackEditVisible", trackEditVisible);

var timeSim = 0;
const editStyle = ref({ color: "dark" });

registerNodes(G6);

const switchMode = () => {
  if (editMode.value === true) {
    initSimulation().then(() => {});
  } else {
    editStyle.value.color = "black";
    editMode.value = true;
    graph.setMode("default");
    graph.removeItem(trainSetup.id);
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

const childRef = ref<any>();

const updateEditMenu = () => {
  console.log("update:editMenu");
  childRef.value.update();
};

var sectionData = ref({});
provide("sectionData", sectionData);

var dialogFormVisible = ref(false);
provide("dialogFormVisible", dialogFormVisible);

var nodeId = ref("");
provide("nodeId", nodeId);

const g6 = (data: GraphData | TreeGraphData | undefined) => {
  graph = new G6.Graph(graphStyle);
  graph.data(data);
  graph.render();
  graph.refresh();
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

const setSectionData = (data: any) => {
  sectionData.value = data;
};

async function getData() {
  await axios.post("api/Node/addAll/test").then((res) => {});
  var _nodes = (await axios.get("api/Node")).data;
  var _edges = (await axios.get("api/Edge")).data;
  changeStyleByType(_nodes);
  data = { nodes: _nodes, edges: _edges };
  return data;
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
      <div class="contextmenu_item">add section</div>
      <div class="contextmenu_item">add new track</div>
      <div class="contextmenu_item">delete track</div>
      <div class="contextmenu_item">delete section</div>
  </div>
  `;
  },
  handleMenuClick: (target, item) => {
    console.log(target.innerText, item.getModel().id);
    switch (target.innerText) {
      case "add section":
        axios.post("api/section/addSection", { name: item.getModel().id });
        break;
      case "add new track":
        trackEditVisible.value = true;
        break;
      case "delete track":
        axios.post("api/section/deleteTrack", { name: item.getModel().id });
        break;
      case "delete section":
        axios.delete("api/section/" + item.getModel().id).then(() => {
          Notify.create("delete " + item.getModel().id);
        });
        break;
    }
    setTimeout(() => {
      updateGraph();
    }, 300);
  },
  // offsetX and offsetY include the padding of the parent container
  offsetY: 0,
  itemTypes: ["node"],
});

const graphStyle = {
  container: "mountNode",
  width: 1300,
  height: 1000,
  fitViewPadding: [20, 40, 50, 200],
  plugins: [toolbar, contextMenu],
  layout: dagreLayout,
  animate: true,
  defaultNode: defaultNodeStyle,
  defaultEdge: defaultEdgeStyle,
  nodeStateStyles: stateStyle,
  modes: defaultMode,
};

var time = 0;
var curentSimulation: Simulation;

interface G6info {
  info: string;
  time: number;
  position: string;
  pType: string;
  gid: string;
}

interface Simulation {
  name: string;
  actionByTime: Array<Array<G6info>>;
}

function changeGroupColor(position: string, state: boolean) {
  const item = graph.findById(position);
  if (state) {
    graph.setItemState(item, "group", "alive");
  } else {
    graph.setItemState(item, "group", "dead");
  }
  graph.refresh();
}

function changeGroupPosition(position: string) {
  const item = graph.findById(position);
  console.log("moveGroup");
  item.getModel().type = "sectionWithGroup";
  graph.refreshItem(position);
}

function changeTrainPosition(position: string) {
  const item = graph.findById(position).getModel();
  const train = graph.findById("train");

  console.log("change train to " + position);

  train.getModel().x = item.x;
  train.getModel().y = item.y;
  graph.refresh();
}

function run(sim: Simulation, t: number) {
  var actions: Array<G6info> = sim.actionByTime[t]!;
  for (let index = 0; index < actions.length; index++) {
    const element = actions[index];
    console.log(index);
    console.log(element);
    switch (element.info) {
      case "train":
        changeTrainPosition(element.position);
        break;
      case "moveGroup":
        changeGroupPosition(element.position);
        break;
      case "group":
        changeGroupColor(element.position, false);
        break;
      case "alive":
        changeGroupColor(element.position, true);
      default:
        break;
    }
  }
}

async function runOneStep() {
  if (!curentSimulation) {
    initSimulation();
    await sleep1000();
  }
  run(curentSimulation, timeSim);
  timeSim++;
}

async function runOneStepBack() {
  if (!curentSimulation) {
    initSimulation();
    await sleep1000();
  }
  if (timeSim < 2) {
    Notify.create({
      message: "can't go back",
      position: "top",
      color: "negative",
    });
    return;
  }
  timeSim = timeSim - 2;
  run(curentSimulation, timeSim);
  timeSim++;
}

const sleep1000 = () => {
  return sleep(1000).then();
};

async function runAll() {
  initSimulation();
  await sleep1000();
  console.log(curentSimulation.actionByTime);
  curentSimulation.actionByTime.forEach((element) => {});
  for (var i = 0; i < curentSimulation.actionByTime.length; i++) {
    runOneStep();
    graph.refresh();
    await sleep1000();
  }
}

async function initSimulation() {
  graph.changeData(data);
  const name = { name: "./traceTroll.lp" };
  graph.remove("train");
  await axios.post("api/section/trace", name).then((res) => {
    curentSimulation = res.data[1];
    for (let index = 0; index < res.data.length; index++) {
      const element = res.data[index];
      if (element.name === currentSimulationName.value) {
        curentSimulation = element;
        break;
      }
    }
    console.log(curentSimulation);updateSimulations()
    axios.get("api/section/original").then((res) => {
      const id: string = res.data;
      const item = graph.findById(id).getModel();
      trainSetup.x = item.x!;
      trainSetup.y = item.y!;
      const train = graph.addItem("node", trainSetup);
      timeSim = 0;

      graph.setMode("animation");
      fixNodesPosition();
      editStyle.value.color = "lightgrey";
      editMode.value = false;
    });;
  });
  
}

const openContextMenu = () => {};

const initGraph = async () => {
  await axios.post("api/Node/addAll/test").then((res) => {
    axios.get("api/Node").then((res1) => {
      axios.get("api/Edge").then((res2) => {
        var _nodes = res1.data;
        var _edges = res2.data;
        changeStyleByType(_nodes);
        data = { nodes: _nodes, edges: _edges };
        graph = g6(data);
        setTimeout(() => {
          graph.updateLayout({});
        }, 500);
        graph.on("canvas:contextmenu", function (evt) {
          trackEditVisible.value = true;
        });
        graph.on("node:dblclick", function (evt) {
          var id: string = evt.item?.getModel().id!;
          setNodeId(id);
          axios.get("api/section/" + id).then((res) => {
            setSectionData(res.data);
            updateEditMenu();
            openMenu();
          });
        });
      });
    });
  });
  console.log("run:initGraph");
};

async function updateSimulations() {
  await axios.get("api/section/simulation").then((res) => {
    let data = res.data.data;
    let names: Array<string> = [];
    for (let index = 0; index < data.length; index++) {
      const element = data[index];
      names.push(element.name);
    }
    simulationNames.value = names;
    console.log(simulationNames.value);
  });
}

const updateGraph = () => {
  getData().then((data) => {
    console.log(data);

    graph.changeData(data);
  });
  graph.refresh();
  updateClingo();
  console.log("run:updateGraph");
};

initGraph();

onBeforeUpdate(() => {
  updateGraph();
});

defineExpose({
  runOneStep,
  initSimulation,
  runOneStepBack,
  runAll,
  updateGraph,
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

.contextmenu_item:not(:last-child) {
  border-bottom: 1px solid rgba(0, 0, 0, 0.1);
}

.contextmenu_item:hover {
  cursor: pointer;
  background: #66b1ff;
  border-color: #66b1ff;
  color: #fff;
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
