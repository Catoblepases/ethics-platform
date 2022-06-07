<template>
  <el-dialog v-model="editSimulationVisible">
    <el-row>
      <el-space>
        <el-col span="2">title:</el-col>
        <el-col span="2">
          <el-input v-model="sname" :disabled="editMode"></el-input>
        </el-col>
      </el-space>
    </el-row>
    <el-table :data="tableData" style="width: 100%">
      <el-table-column label="valid" align="right" width="80">
        <template #default="scope">
          <el-switch
            v-model="tableData[scope.$index].valid"
            class="mt-2"
            style="margin-left: 24px"
            inline-prompt
            :active-icon="Check"
            :inactive-icon="Close"
            @click="handleValid(scope.$index, scope.row)"
          />
        </template>
      </el-table-column>
      <el-table-column
        prop="eventDescription"
        label="eventDescription"
        width="120"
      />
      <el-table-column prop="time" label="time" width="150">
        <template #default="scope">
          <el-input-number
            size="small"
            v-model="tableData[scope.$index].time"
            controls-position="right"
          ></el-input-number>
        </template>
      </el-table-column>
    </el-table>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="editSimulationVisible = false">Cancel</el-button>
        <el-button type="primary" @click="apply">Apply</el-button>
        <el-button type="primary" @click="update">Update</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
import axios from "axios";
import Vue, { inject, onMounted, Ref, ref } from "vue";
const editSimulationVisible = inject("editSimulationVisible");
const activeSim = inject("activeSimulation");
const editMode = inject("editMode");
const sname = ref("");
interface Event {
  valid: boolean;
  eventType: string;
  eventDescription: string;
  time: number;
}

let tableData: Ref<Array<Event>> = ref([]);

const updateEditSimulation = () => {
  if (editMode._rawValue === false) {
    sname.value = "";
    getEvent();
    return;
  }
  sname.value = "unkown";
  sname.value = activeSim._rawValue.name;
  console.log("name: " + activeSim._rawValue["name"]);
  console.log("rawvalue:");
  console.log(activeSim);
  console.log("update:editSimulationVisible");
  var actions: Array<string> = Object.keys(activeSim._rawValue);
  console.log(actions);

  var data = tableData.value;

  for (let index = 0; index < data.length; index++) {
    const el = data[index];
    const content = activeSim._rawValue[el.eventDescription];
    if (actions.includes(el.eventDescription) && content != "X") {
      el.valid = true;
      var time = content.match(/\d+/g);
      el.time = time;
    } else {
      el.valid = false;
    }
  }
  setTableData(data);
  console.log();
};

const setTableData = (data: any) => {
  tableData.value = data;
  console.log(tableData.value);
};

const handleValid = (index: number, row: Event) => {
  console.log(index, row.valid);
};

const getEvent = () => {
  axios.get("api/carriage/events").then((res) => {
    setTableData(res.data);
    for (let index = 0; index < tableData.value.length; index++) {
      tableData.value[index].valid = false;
    }
    console.log("SimulationEdit:dataupdate");
  });
};

onMounted(() => {
  getEvent();
  console.log(tableData);
});

class Event {
  valid: boolean = false;
  eventType: string = "";
  eventDescription: string = "";
  time: number = 0;
}

class simulations {
  actions: Array<Event> = [];
  name: string = "";
}

const createData = () => {
  var out: simulations = new simulations();
  out.name = sname.value;

  var data = tableData.value;
  for (let index = 0; index < data.length; index++) {
    const el = data[index];
    const event = new Event();
    event.valid = el.valid;
    event.eventDescription = el.eventDescription;
    event.time = el.time;
    console.log(event);
    
    out.actions.push(event);
  }
  return out;
};

const emit = defineEmits(["onChange"]);
const update = () => {
  emit("onChange");
};

const apply = () => {
  var out: simulations = createData();
  console.log(out);
  axios.post("api/carriage/simulation", out).then((res) => {
    console.log(res);
    update();
  });
};

defineExpose({ updateEditSimulation });
</script>
