<template>
  <el-dialog v-model="dialogFormVisible">
    <el-button>{{ nodeId }}</el-button>
    <el-tabs type="border-card">
      <el-tab-pane label="Switch">
        <el-form :model="node">
          <el-form-item
            label="Carriage Connected"
            :label-width="formLabelWidth"
          >
            : {{ switchTrackValue }}({{ switchTrackNumber }})
          </el-form-item>
          <el-form-item label="Name of Track" :label-width="formLabelWidth">
            <el-select
              v-model="switchTrackValue"
              placeholder="Please select a carriage to connect"
            >
              <el-option
                v-bind:key="item.key"
                v-for="item in TrackItems"
                :label="item.value"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="Number of Track" :label-width="formLabelWidth">
            <el-select
              v-model="switchTrackNumber"
              placeholder="Please select a carriage to connect"
            >
              <el-option
                v-bind:key="item.key"
                v-for="item in TrackNumberItems"
                :label="item.value"
                :value="item.value"
              />
            </el-select>
            <el-button type="danger" @click="deleteSwitch" plain
              >Delete</el-button
            >
          </el-form-item>
        </el-form>
      </el-tab-pane>
      <el-tab-pane label="Carriage">
        <el-row class="carriage">
          <el-switch
            v-model="orignalPosition"
            class="mb-2"
            active-text="Set as Position Original"
            inactive-text=""
          />
          <el-divider direction="vertical" />
          <el-button type="danger" @click="deleteCarriage" plain
            >Delete This Carriage</el-button
          >
        </el-row>
      </el-tab-pane>
      <el-tab-pane label="Bridge">
        <el-form-item label="name of bridge">
          <el-row>
            <el-space wrap size="5">
              <el-input
                v-model="nameBridge"
                autocomplete="off"
                placeholder="Please type the name of bridge"
              />
              <el-button type="primary" @click="onSubmitBridge"
                >Create/Update</el-button
              >
              <el-button type="danger" @click="deleteBridge" plain
                >Delete</el-button
              >
            </el-space>
          </el-row>
        </el-form-item>
      </el-tab-pane>
      <el-tab-pane label="Group">
        <el-row
          ><el-descriptions
            class="margin-top"
            title="Group on the carriage"
            :column="3"
          >
            <el-descriptions-item label="name">{{
              group.name
            }}</el-descriptions-item>
            <el-descriptions-item label="size">{{
              group.size
            }}</el-descriptions-item>
          </el-descriptions>
          <el-divider direction="vertical"></el-divider>
          <el-descriptions
            class="margin-top"
            title="Group on the bridge"
            :column="3"
            :size="size"
            :style="blockMargin"
          >
            <el-descriptions-item label="name">{{
              groupBridge.name
            }}</el-descriptions-item>
            <el-descriptions-item label="size">{{
              groupBridge.size
            }}</el-descriptions-item>
          </el-descriptions></el-row
        >
        <el-collapse v-model="activeName" accordion>
          <el-collapse-item title="Delete Group" name="1">
            <el-button type="danger" @click="deleteGroupBridge" plain
              >delete Group on bridge</el-button
            >
            <el-button type="danger" @click="deleteGroup" plain
              >delete Group on carriage</el-button
            >
          </el-collapse-item>
          <el-collapse-item title="Create Group" name="2">
            <el-form :inline="true" class="demo-form-inline">
              <el-form-item label="Position">
                <el-select v-model="pos" placeholder="position to place group">
                  <el-option label="carriage" value="c" />
                  <el-option label="bridge" value="b" />
                </el-select>
              </el-form-item>
              <el-form-item label="name">
                <el-input v-model="createGroup.name" placeholder="name" />
              </el-form-item>
              <el-form-item label="size">
                <el-input v-model="createGroup.size" placeholder="size" />
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="onSubmitGroup"
                  >Create/Update</el-button
                >
              </el-form-item>
            </el-form>
          </el-collapse-item>
        </el-collapse>
      </el-tab-pane>
    </el-tabs>

    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogFormVisible = false">Cancel</el-button>
        <el-button type="primary" @click="apply">Apply</el-button>
        <el-button type="primary" @click="update">Update</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
import {
  onBeforeUpdate,
  onMounted,
  reactive,
  ref,
  Ref,
  inject,
  onBeforeMount,
} from "vue";
import axios from "axios";

import { ElMessage } from "element-plus";
import {
  Data,
  Bridge,
  Group,
  Switch,
  setDataRef,
  updateBridge,
  updateGroup,
  updateSwitch,
  updateData,
  setOriginal,
  changeString,
  setGroup,
} from "./JavaComponents";

const formLabelWidth = "140px";

// components from mother
let dialogFormVisible = inject("dialogFormVisible");
const nodeId = inject("nodeId");
let _carriageData = inject("carriageData");
let carriageData: Data = _carriageData._rawValue;

// ref
const data = new Data();
let createGroup = ref(new Group());
let p = ref("");

const orignalPosition = ref(false);
const switchTrackValue = ref("");
const nameBridge = ref("");
const pos = ref("");
const switchTrackNumber = ref(0);
const group = ref({ name: null, size: null });
const groupBridge = ref({ name: null, size: null });

const dataRef = {
  orignalPosition: orignalPosition,
  switchTrackValue: switchTrackValue,
  switchTrackNumber: switchTrackNumber,
  nameBridge: nameBridge,
  group: group,
  groupBridge: groupBridge,
};

var TrackItems = [];
var TrackLength = [];
var TrackNumberItems = [];

const setCarriageData = (data: any) => {
  carriageData = data;
};
// useful funtion

// update
const update = () => {
  carriageData = _carriageData._rawValue;
  console.log(carriageData);
  data.id = nodeId._rawValue;
  data.track = carriageData.track;
  data.index = carriageData.index;
  data.switchs = carriageData.switchs;
  data.group = carriageData.group;
  data.bridge = carriageData.bridge;
  data.positionOriginal = carriageData.positionOriginal;
  console.log(data);
  setDataRef(data, dataRef);
  console.log(dataRef);
  console.log("EditMenu: up to date");
  let s = "ok";
  changeString(s, p);
};

const apply = () => {
  // updateData(data, dataRef);
  console.log("update data:");
  console.log(data);
  axios.put("api/carriage", data).then(() => {
    console.log("submit succes");
  });
  updateGraph();
};

const onSubmitGroup = () => {
  console.log(createGroup);

  if (createGroup.value.name != null && createGroup.value.size != null) {
    if (pos.value == "c") {
      if (data.group === null || data.group.size === -1) {
        data.group = {
          name: createGroup.value.name,
          size: createGroup.value.size,
        };
      } else {
        data.group.name = createGroup.value.name;
        data.group.size = createGroup.value.size;
      }
      setGroup(data.group, group);
    } else {
      if (data.bridge === null) {
        ElMessage("there is not a bridge");
      } else if (data.bridge.group === null) {
        ElMessage("create a new group on the bridge");
        data.bridge.group = {
          name: createGroup.value.name,
          size: createGroup.value.size,
        };
      } else {
        ElMessage("change the group on the bridge");
        data.bridge.group.name = createGroup.value.name;
        data.bridge.group.size = createGroup.value.size;
      }
    }
    setGroup(data.bridge?.group, groupBridge);
  }
};

const onSubmitBridge = () => {
  if (dataRef.nameBridge.value != "") {
    if (data.bridge?.name === "" || data.bridge === null) {
      data.bridge = { name: dataRef.nameBridge.value.valueOf(), group: null };
    } else {
      data.bridge!.name = dataRef.nameBridge.value.valueOf();
    }
    console.log(data);
  }
};

const deleteBridge = () => {
  dataRef.nameBridge.value = "";
  data.bridge = null;
};

function deleteCarriage() {
  axios.delete("api/carriage/" + data.id).then(() => {
    ElMessage("delete " + data.id);
  });
}

function deleteGroupBridge() {
  if (data.bridge) {
    data.bridge.group = null;
  }
}

function deleteGroup() {
  data.group = null;
}

onBeforeMount(() => {
  axios.get("api/carriage/allTrack").then((res) => {
    for (let index = 0; index < res.data.length; index++) {
      TrackItems.push({ value: res.data[index], label: res.data[index] });
    }
    console.log(TrackItems);
  });

  axios.get("api/carriage/allTrackNumber").then((res) => {
    TrackLength = res.data;
    console.log(TrackLength);
    for (let index = 0; index < TrackLength[0]; index++) {
      TrackNumberItems.push({ value: index, key: index });
    }
  });
});

onMounted(() => {});

const emit = defineEmits(["onUpdate"]);
const updateGraph = () => {
  emit("onUpdate");
};

defineExpose({ update });
</script>

<style scoped>
.el-button--text {
  margin-right: 15px;
}
.el-select {
  width: 300px;
}
.el-input {
  width: 300px;
}
.dialog-footer button:first-child {
  margin-right: 10px;
}

.my-label {
  background: var(--el-color-success-light-9);
}
.my-content {
  background: var(--el-color-danger-light-9);
}
</style>
