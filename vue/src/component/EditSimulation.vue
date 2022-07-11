<template>
  <q-dialog v-model="editSimulationVisible">
    <q-card>
      <q-card-section>
        <q-toolbar>
          <q-toolbar-title>
            <q-input
              label="simulation name"
              v-model="sname"
              :disabled="editMode"
            ></q-input>
          </q-toolbar-title>
          <q-btn dense flat round icon="close" color="red" v-close-popup />
        </q-toolbar>
      </q-card-section>
      <q-card-section>
        <q-table flat :rows="tableData" row-key="eventDescription">
          <template v-slot:body="props">
            <q-tr>
              <q-td key="valid" :props="props">
                {{ props.row.valid }}
                <q-popup-edit v-model="props.row.valid" buttons v-slot="scope">
                  <q-toggle
                    v-model="scope.value"
                    checked-icon="check"
                    color="red"
                    unchecked-icon="clear"
                    autofocus
                  />
                </q-popup-edit>
              </q-td>
              <q-td key="eventType" :props="props">
                {{ props.row.eventType }}
              </q-td>
              <q-td key="eventDescription" :props="props">
                {{ props.row.eventDescription }}
              </q-td>
              <q-td key="time" :props="props">
                {{ props.row.time }}
                <q-popup-edit
                  v-model="props.row.time"
                  title="Update time"
                  buttons
                  v-slot="scope"
                >
                  <q-input
                    type="number"
                    v-model="scope.value"
                    dense
                    autofocus
                  />
                </q-popup-edit>
              </q-td>
            </q-tr>
          </template>
        </q-table>
      </q-card-section>
      <q-card-actions align="right">
        <q-btn flat label="apply" color="primary" @click="apply" />
        <q-btn flat label="update" @click="update" />
      </q-card-actions>
    </q-card>
  </q-dialog>
</template>

<script setup lang="ts">
import axios from "axios";
import { updateClingo } from "./updateFile";
import { Notify } from "quasar";
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
      el.time = time[0];
    } else {
      el.valid = false;
    }
  }
  setTableData(data);
  console.log();
};

async function asyncEvent() {
  await getEvent();
}

const setTableData = (data: any) => {
  tableData.value = data;
  console.log(tableData.value);
};

const getEvent = async () => {
  await axios.get("api/section/events").then((res) => {
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
  if (out.name === "" || out.name === " ") {
    Notify.create({
      message: "bad format ! (simulations without name)",
      position: "top",
      color: "negative",
    });
    return;
  }
  console.log(out);
  axios.post("api/section/simulation", out).then((res) => {
    console.log(res);
    update();
    updateClingo();
  });
};

defineExpose({ updateEditSimulation,asyncEvent });
</script>
