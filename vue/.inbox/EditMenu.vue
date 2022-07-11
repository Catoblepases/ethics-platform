<template>
  <el-dialog v-model="dialogFormVisible">
    <el-button>{{ nodeId }}</el-button>
    <el-tabs type="border-card">
      <el-tab-pane label="Switch">
        <el-form :model="node">
          <el-form-item
            label="Section Connected"
            :label-width="formLabelWidth"
          >
            <el-input v-model="switchTrackValue" autocomplete="off" disabled />
          </el-form-item>
          <el-form-item label="Name of Track" :label-width="formLabelWidth">
            <el-select
              v-model="switchTrackValue"
              placeholder="Please select a section to connect"
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
              placeholder="Please select a section to connect"
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
      <el-tab-pane label="Section">
        <el-row class="section">
          <el-switch
            v-model="orignalPosition"
            class="mb-2"
            active-text="Set as Position Original"
            inactive-text=""
          />
          <el-divider direction="vertical" />
          <el-button type="danger" @click="deleteSection" plain
            >Delete This Section</el-button
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
            title="Group on the section"
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
            title="Group on the section"
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
            <el-button type="danger" plain>delete Group on section</el-button>
          </el-collapse-item>
          <el-collapse-item title="Create Group" name="2">
            <el-form :inline="true" class="demo-form-inline">
              <el-form-item label="Position">
                <el-select v-model="pos" placeholder="position to place group">
                  <el-option label="section" value="c" />
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
import { Data, Bridge, Group, Switch } from "./JavaComponents";
const formLabelWidth = "140px";
// components from mother
let dialogFormVisible = inject("dialogFormVisible");
const nodeId = inject("nodeId");
let sectionData = inject("sectionData");
// ref
const isOrignalPosition = ref(false);
const switches = ref<Switch>();
const bridge = ref<Bridge>();
const pos = ref("");
const group = ref<Group>();
const createGroup = ref<Group>();
const data = new Data();
var TrackItems = [];
var TrackNumberItems = [];
var TrackLength = [];
const setSectionData = (data: any) => {
  sectionData = data;
};
// useful funtion
const notEqualNull = (val: any) => {
  if (val === "" || val === "-1" || val === null) {
    return false;
  }
  return true;
};

const setBridge = (b: Bridge) => {
  if (b === null) {
    bridge.value = null;
  } else if (b.group === null) {
    bridge.value.name = b.name;
    bridge.value.group = null;
  } else {
    bridge.value.name = b.name;
    setGroup(b.group, "b");
    bridge.value.group = b.group;
  }
};

const setGroup = (g: Group, bOrc: string) => {
  if (g === null) {
    if (bOrc === "b") {
      bridge.value?.group?.name = "";
      bridge.value?.group?.size = -1;
    } else {
      group.value?.name = "";
      group.value?.size = -1;
    }
  } else {
    if (bOrc === "b") {
      bridge.value?.group?.name = g.name;
      bridge.value?.group?.size = g.size;
    } else {
      group.value?.name = g.name;
      group.value?.size = g.size;
    }
  }
};

const setSwitch = (s: Switch) => {
  if (s === null) {
    switches.value?.trackEndName = "";
    switches.value?.trackEndNumber = -1;
  } else {
    switches.value?.trackEndName = s.trackEndName;
    switches.value?.trackEndNumber = s.trackEndNumber;
  }
};

// update
const update = () => {
  data.id = nodeId._rawValue;
  data.track = sectionData._rawValue.track;
  data.index = sectionData._rawValue.index;
  data.switchs = sectionData._rawValue.switchs;
  data.group = sectionData._rawValue.group;
  data.bridge = sectionData._rawValue.bridge;
  setSwitch(data.switchs);
  setBridge(data.bridge);
  setGroup(data.group);

  console.log("EditMenu: up to date");
  console.log(data);
};

const apply = () => {

  axios.put("api/section", data).then(() => {
    console.log("submit succes");
  });
  updateGraph();
};

onBeforeMount(() => {
  axios.get("api/section/allTrack").then((res) => {
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

// delete
const deleteBridge = () => {
  data.bridge = null;
};

const deleteSwitch = () => {
  data.switchs = null;
};

const deleteSection = () => {
  ElMessage("try to delete this section");
  axios.delete("api/section/" + data.id).then(() => {
    ElMessage("delete section successfully");
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
