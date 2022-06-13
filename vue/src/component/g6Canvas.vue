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
import {
  stateStyle,
  registerNodes,
  graphStyle,
  trainSetup,
  dagreLayout,
  changeStyleByType,
} from "./nodes";
import { ElMessage } from "element-plus";

var graph: Graph;
let data: GraphData;

let editMode = ref(true);
let color = ref("#409EFC");
var timeSim = 0;

registerNodes(G6);

const switchMode = () => {
  if (editMode.value === true) {
    initSimulation().then(() => {});
  } else {
    color.value = "#409EFC";
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
  item.getModel().type = "carriageWithGroup";
}

function changeTrainPosition(position: string) {
  const item = graph.findById(position).getModel();
  const train = graph.findById("train");
  console.log(item);
  console.log(train);

  train.getModel().x = item.x;
  train.getModel().y = item.y;
  graph.refresh();
}

function run(sim: Simulation, t: number) {
  var actions: Array<G6info> = sim.actionByTime[t]!;
  for (let index = 0; index < actions.length; index++) {
    const element = actions[index];
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
      default:
        break;
    }
  }
}

async function runOneStep() {
  if (!curentSimulation) {
    initSimulation();
    const no = await sleep1000();
  }
  run(curentSimulation, timeSim);
  timeSim++;
}

async function runOneStepBack() {
  if (!curentSimulation) {
    initSimulation();
    const no = await sleep1000();
  }
  if (timeSim < 2) {
    ElMessage("can't go back");
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
  const no = await sleep1000();
  console.log(curentSimulation.actionByTime.length);
  curentSimulation.actionByTime.forEach((element) => {});
  for (var i = 0; i < curentSimulation.actionByTime.length; i++) {
    runOneStep();
    graph.refresh();
    const no = await sleep1000();
  }
}

async function initSimulation() {
  const name = { name: "./traceTroll.lp" };
  graph.remove("train");
  await axios.post("api/carriage/trace", name).then((res) => {
    curentSimulation = res.data[1];
    console.log(curentSimulation);

    axios.get("api/carriage/original").then((res) => {
      const id: string = res.data;
      const item = graph.findById(id).getModel();
      trainSetup.x = item.x!;
      trainSetup.y = item.y!;
      const train = graph.addItem("node", trainSetup);
      timeSim = 0;

      graph.setMode("animation");
      fixNodesPosition();
      color.value = "";
      editMode.value = false;
    });
  });
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
        setTimeout(() => {
          graph.updateLayout({});
        }, 500);
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

defineExpose({ runOneStep, initSimulation, runOneStepBack, runAll });
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
