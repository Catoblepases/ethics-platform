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
            title="Group on the carriage"
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
            <el-button type="danger" plain>delete Group on bridge</el-button>
            <el-button type="danger" plain>delete Group on carriage</el-button>
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
import { ElMessage } from "element-plus";
// import { castArray } from "element-plus/lib/utils";
let dialogFormVisible = inject("dialogFormVisible");
const nodeId = inject("nodeId");
let carriageData = inject("carriageData");

const formLabelWidth = "140px";
const orignalPosition = ref(false);
const switchTrackValue = ref("");
const nameBridge = ref("");
const pos = ref("");
const switchTrackNumber = ref(0);
const group = ref({ name: null, size: null });
const groupBridge = ref({ name: null, size: null });
const createGroup = ref({ name: null, size: null });

const notEqualNull = (val: any) => {
  if (val === "" || val === "-1" || val === null) {
    return false;
  }
  return true;
};

const setCarriageData = (data: any) => {
  carriageData = data;
};

const data = {
  id: nodeId._rawValue,
  track: "",
  index: -1,
  switchs: {
    trackBegin: "",
    trackEnd: "",
    trackBeginName: "",
    trackBeginNumber: 0,
    trackEndName: "",
    trackEndNumber: 0,
  },

  group: {
    name: "",
    size: 0,
  },

  bridge: null,
};

var node = {
  switchTrack: switchTrackValue,
  switchNumber: switchTrackNumber,
  track: "main",
  trackNumber: 10,
  allTrack: ["side", "main"],
  bridge: nameBridge,
  group: group,
  groupBridge: groupBridge,
};

switchTrackNumber.value = -1;
switchTrackValue.value = "";
nameBridge.value = "";

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
  if (data.switchs != null) {
    switchTrackValue.value = data.switchs.trackEndName;
    switchTrackNumber.value = data.switchs.trackEndNumber;
  } else {
    switchTrackValue.value = "";
    switchTrackNumber.value = -1;
  }

  if (data.group != null) {
    group.value.name = data.group.name;
    group.value.size = data.group.size;
  } else {
    group.value.name = "";
    group.value.size = 0;
  }

  if (data.bridge != null) {
    nameBridge.value = data.bridge;
    if (data.bridge.group != null) {
      groupBridge.value.name = data.bridge.group.name;
      groupBridge.value.size = data.bridge.group.size;
    } else {
      groupBridge.value.name = "";
      groupBridge.value.size = 0;
    }
  } else {
    nameBridge.value = "";
    groupBridge.value.name = "";
    groupBridge.value.size = 0;
  }
  console.log("EditMenu: up to date");
  console.log(data);
};

const apply = () => {
  if (switchTrackValue.value != "" && switchTrackNumber.value != -1) {
    data.switchs = {
      trackBegin: data.id,
      trackEnd: switchTrackValue.value + "(" + switchTrackNumber.value + ")",
    };
  }
  if (
    data.group != null &&
    (data.group.name === "" || data.group.size === -1)
  ) {
    data.group = null;
  }
  axios.put("api/carriage", data).then(() => {
    console.log("submit succes");
  });
  updateGraph();
};

onBeforeMount(() => {
  axios.get("api/carriage/allTrack").then((res) => {
    TrackItems = res.data;
    console.log(TrackItems);
  });
});

const onSubmit = () => {
  if (group.value.name != null && group.value.size != null) {
    if (pos.value == "c") {
      if (data.group === null) {
        data.group = {
          name: createGroup.value.name,
          size: createGroup.value.size,
        };
      } else {
        data.group.name = createGroup.value.name;
        data.group.size = createGroup.value.size;
      }
    } else {
      if (data.bridge === null) {
        ElMessage("this isnt a bridge");
      } else if (data.bridge.group === null) {
        data.bridge.group = {
          name: createGroup.value.name,
          size: createGroup.value.size,
        };
      } else {
        data.bridge.group.name = createGroup.value.name;
        data.bridge.group.size = createGroup.value.size;
      }
    }
  }
};

const onSubmitBridge = () => {
  if (nameBridge.value != "") {
    if (data.bridge == null) {
      data.bridge = { name: nameBridge.value, group: null };
    } else {
      data.bridge.name = nameBridge.value;
    }
  }
};

const deleteBridge = () => {
  data.bridge = null;
};

const deleteSwitch = () => {
  data.switchs = null;
};

const deleteCarriage = () => {
  ElMessage("try to delete this carriage");
  axios.delete("api/carriage/" + data.id).then(() => {
    ElMessage("delete carriage successfully");
  });
};

onMounted(() => {
  for (let index = 0; index < node.trackNumber; index++) {
    TrackNumberItems.push({ value: index, key: index });
  }
  for (let index = 0; index < node.allTrack.length; index++) {
    TrackItems.push({ value: node.allTrack[index], key: node.allTrack[index] });
  }
});

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
