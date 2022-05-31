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
            <el-input v-model="switchTrackValue" autocomplete="off" disabled />
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
          <el-button type="danger">Delete This Carriage</el-button>
        </el-row>
      </el-tab-pane>
      <el-tab-pane label="Bridge">
        <el-form-item label="name of bridge">
          <el-input
            v-model="nameBridge"
            autocomplete="off"
            placeholder="Please type the name of bridge"
          />
        </el-form-item>
      </el-tab-pane>
      <el-tab-pane label="Group">
        <el-row
          ><el-descriptions
            class="margin-top"
            title="Group on the carriage"
            :column="3"
            :size="size"
            :style="blockMargin"
          >
            <el-descriptions-item label="name">{{
              node.group.name
            }}</el-descriptions-item>
            <el-descriptions-item label="size">{{
              node.group.size
            }}</el-descriptions-item>
          </el-descriptions>
          <el-divider direction="vertical"></el-divider>
          <el-descriptions
            class="margin-top"
            title="Group on the carriage"
            :column="3"
            :size="size"
            :style="blockMargin"
          >
            <el-descriptions-item label="name">{{
              node.group.name
            }}</el-descriptions-item>
            <el-descriptions-item label="size">{{
              node.group.size
            }}</el-descriptions-item>
          </el-descriptions></el-row
        >

        <el-collapse v-model="activeName" accordion>
          <el-collapse-item title="Delete Group" name="1">
            <el-button type="danger" plain>delete Group on bridge</el-button>
            <el-button type="danger" plain>delete Group on carriage</el-button>
          </el-collapse-item>
          <el-collapse-item title="Create Group" name="2">
            <el-form :inline="true" class="demo-form-inline">
              <el-form-item label="Position">
                <el-select v-model="pos" placeholder="position to place group">
                  <el-option label="carriage" value="shanghai" />
                  <el-option label="bridge" value="beijing" />
                </el-select>
              </el-form-item>
              <el-form-item label="name">
                <el-input v-model="node.group.name" placeholder="Approved by" />
              </el-form-item>
              <el-form-item label="size">
                <el-input v-model="node.group.size" placeholder="Approved by" />
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="onSubmit"
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
  inject,
  onBeforeMount,
} from "vue";
import axios from "axios";
import { castArray } from "element-plus/lib/utils";
let dialogFormVisible = inject("dialogFormVisible");
const nodeId = inject("nodeId");
const carriageData = inject("carriageData");

const formLabelWidth = "140px";
const orignalPosition = ref(false);
const switchTrackValue = ref("");
const nameBridge = ref("");
const pos = ref("");
const switchTrackNumber = ref(0);
const group = ref({ name: null, size: null });

const data = {
  id: nodeId._rawValue,
  track: "",
  index: -1,
  switchs: {
    trackBegin: "",
    trackEnd: "",
  },
  group: {
    name: null,
    size: null,
  },
  bridge: {
    name: null,
    group: {
      name: null,
      size: null,
    },
  },
};

group.value = data.group;
var node = {
  switch: "nnn",
  switchTrack: switchTrackValue,
  switchNumber: switchTrackNumber,
  track: "main",
  trackNumber: 10,
  allTrack: ["side", "main"],
  bridge: nameBridge,
  group: group,
};

switchTrackNumber.value = 5;
switchTrackValue.value = "side";
nameBridge.value = "b";

var TrackItems = [];
var TrackNumberItems = [];
var TrackLength = [];

const update = () => {
  data.id = nodeId._rawValue;
  data.track = carriageData._rawValue.track;
  data.index = carriageData._rawValue.index;
  data.switchs = carriageData._rawValue.switchs;
  data.group = carriageData._rawValue.group;
  data.bridge = carriageData._rawValue.bridge;
  console.log("EditMenu: up to date");
  console.log(data);
};

const apply = () => {
  data.switchs = {
    trackBegin: data.id,
    trackEnd: switchTrackValue.value + "(" + switchTrackNumber.value + ")",
  };
  data.group = { name: group.value.name, size: group.value.size };

  console.log(carriageData._rawValue);
  data.id = nodeId._rawValue;
  axios.put("api/carriage", data).then(() => {
    console.log("submit succes");
  });
};

onBeforeMount(() => {
  axios.get("api/carriage/allTrack").then((res) => {
    TrackItems = res.data;
    console.log(TrackItems);
  });
});

onMounted(() => {
  for (let index = 0; index < node.trackNumber; index++) {
    TrackNumberItems.push({ value: index, key: index });
  }
  for (let index = 0; index < node.allTrack.length; index++) {
    TrackItems.push({ value: node.allTrack[index], key: node.allTrack[index] });
  }
});

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
