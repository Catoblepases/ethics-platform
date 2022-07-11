<template>
  <q-layout view="hHh lpR fFf">
    <q-header elevated class="bg-dark text-white">
      <q-toolbar>
        <q-btn dense flat round icon="menu" @click="toggleLeftDrawer" />
        <q-toolbar-title>
          <q-avatar>
            <img src="https://cdn.quasar.dev/logo-v2/svg/logo-mono-white.svg" />
          </q-avatar>
          Ethics Platform
        </q-toolbar-title>
        <q-btn-dropdown stretch flat label="File">
          <q-list>
            <q-item-label header>Files</q-item-label>
            <q-item v-close-popup tabindex="0">
              <q-item-section avatar>
                <q-avatar icon="folder" color="dark" text-color="white" />
              </q-item-section>
              <q-item-section>
                <q-item-label>open File</q-item-label>
              </q-item-section>
            </q-item>
            <q-item>
              <q-uploader
                field-name="file"
                url="api/section/upload"
                with-credentials
                color="dark"
                auto-upload
                @uploaded="onUploaded"
              />
            </q-item>
            <q-item clickable v-close-popup tabindex="1">
              <q-item-section avatar>
                <q-avatar icon="folder" color="dark" text-color="white" />
              </q-item-section>
              <q-item-section>
                <a
                  href="api/Node"
                  target="_blank"
                  rel="external nofollow"
                  style="text-decoration: none; color: #606266"
                  download="allNode.json"
                  >download</a
                >
                <q-item-label>Save File as json</q-item-label>
              </q-item-section>
            </q-item>
            <q-item clickable tabindex="4" @click="saveFile">
              <q-item-section avatar>
                <q-avatar icon="folder" color="dark" text-color="white" />
              </q-item-section>
              <q-item-section>
                <q-item-label>Save File as lp</q-item-label>
              </q-item-section>
            </q-item>
          </q-list>
        </q-btn-dropdown>
        <q-btn dense flat round icon="menu" @click="toggleRightDrawer" />
      </q-toolbar>
    </q-header>

    <q-drawer
      show-if-above
      v-model="leftDrawerOpen"
      side="left"
      bordered
      :width="100"
    >
      <q-tabs v-model="tab" vertical class="text-dark">
        <q-tab name="simulations" icon="tv" label="Sims" />
        <q-tab name="clingoConfigs" icon="settings" label="Clingo" />
        <q-tab name="commands" icon="terminal" label="Terminal" />
      </q-tabs>
    </q-drawer>

    <q-drawer show-if-above v-model="rightDrawerOpen" side="right" bordered>
      <q-list>
        <q-item-label header>Essential Functions</q-item-label>
        <q-item clickable target="_blank" @click="showAnalyse">
          <q-item-section avatar>
            <q-icon name="account_tree" />
          </q-item-section>
          <q-item-section>
            <q-item-label>causal tree</q-item-label>
            <q-item-label caption
              >Show the causal tree of all simulations</q-item-label
            >
          </q-item-section>
        </q-item>

        <q-item clickable target="_blank" @click="showResult">
          <q-item-section avatar>
            <q-icon name="summarize" />
          </q-item-section>
          <q-item-section>
            <q-item-label>clingo result</q-item-label>
            <q-item-label caption
              >Demonstrate the results of the ethical judgment of the current
              action</q-item-label
            >
          </q-item-section>
        </q-item>

        <q-item clickable target="_blank" @click="updateClingo">
          <q-item-section avatar>
            <q-icon name="refresh" />
          </q-item-section>
          <q-item-section>
            <q-item-label>update files of clingo</q-item-label>
            <q-item-label caption>Manual refresh clingo</q-item-label>
          </q-item-section>
        </q-item>

        <q-item-label header>Essential Links</q-item-label>
        <q-item
          clickable
          target="_blank"
          rel="noopener"
          href="https://github.com/Catoblepases/ethics-platform/blob/main/README.md"
        >
          <q-item-section avatar>
            <q-icon name="school" />
          </q-item-section>
          <q-item-section>
            <q-item-label>Docs</q-item-label>
            <q-item-label caption
              >https://github.com/Catoblepases/ethics-platform/README.md</q-item-label
            >
          </q-item-section>
        </q-item>
        <q-item
          clickable
          target="_blank"
          rel="noopener"
          href="https://github.com/Catoblepases/ethics-platform"
        >
          <q-item-section avatar>
            <q-icon name="code" />
          </q-item-section>
          <q-item-section>
            <q-item-label>GitHub</q-item-label>
            <q-item-label caption
              >https://github.com/Catoblepases/ethics-platform</q-item-label
            >
          </q-item-section>
        </q-item>
      </q-list>
    </q-drawer>

    <q-page-container>
      <q-splitter v-model="splitterModel">
        <template v-slot:before>
          <q-tab-panels
            v-model="tab"
            animated
            swipeable
            vertical
            transition-prev="jump-up"
            transition-next="jump-up"
          >
            <q-tab-panel name="simulations">
              <div class="text-h5 q-mb-md">Simulation menu</div>
              <q-separator />
              <simulation-menu></simulation-menu>
            </q-tab-panel>
            <q-tab-panel name="clingoConfigs">
              <div class="text-h5 q-mb-md">Clingo Config</div>
              <q-separator />
              <clingo-menu></clingo-menu>
            </q-tab-panel>
            <q-tab-panel name="commands">
              <div class="text-h5 q-mb-md">Commands</div>
              <q-separator />
              <command-menu @updateGraph="updateGraph"></command-menu>
            </q-tab-panel>
          </q-tab-panels>
        </template>

        <template v-slot:after>
          <main-canvas ref="g6Ref" @getSimulation="getSimulation"></main-canvas>
        </template>
      </q-splitter>
    </q-page-container>
  </q-layout>

  <clingo-analyse ref="ARef"></clingo-analyse>
  <clingo-result ref="RRef"></clingo-result>
</template>

<script setup lang="ts">
import { onBeforeUnmount, onMounted, provide, ref } from "vue";
import { updateClingo } from "./component/updateFile";
import MainCanvas from "./component/g6Canvas.vue";
import ClingoMenu from "./component/ClingoConfig.vue";
import SimulationMenu from "./component/SimulationMenu.vue";
import clingoAnalyse from "./component/ClingoAnalyse.vue";
import clingoResult from "./component/ClingoResult.vue";
import commandMenu from "./component/Command.vue";
import { Loading } from "quasar";
import axios from "axios";
import { Notify } from "quasar";

let timer: any;

const file = ref<File>();
const leftDrawerOpen = ref(false);
const rightDrawerOpen = ref(false);
const tab = ref("simulations");
const splitterModel = ref(33);
const ARef = ref<any>();
const RRef = ref<any>();
const g6Ref = ref<any>();

let analyse = ref(false);
let result = ref(false);
provide("analyse", analyse);
provide("result", result);

function onUploaded() {
  Loading.show();
  setTimeout(() => {
    updateGraph();
    Loading.hide();
  }, 1000);
}

function saveFile() {
  axios.post("api/section/lp", { name: "trolley1act.lp" }).then(() => {
    Notify.create(
      "finish saving, check the relative path: ./spring/trolley1act.lp "
    );
  });
}

function toggleLeftDrawer() {
  if (leftDrawerOpen.value) {
    splitterModel.value = 0;
  } else {
    splitterModel.value = 33;
  }
  leftDrawerOpen.value = !leftDrawerOpen.value;
}

function toggleRightDrawer() {
  rightDrawerOpen.value = !rightDrawerOpen.value;
}

const showAnalyse = () => {
  analyse.value = true;
  ARef.value.initGraphAnalyse();
};

function updateGraph() {
  console.log("update command:graph");
  g6Ref.value.updateGraph();
}

const showResult = () => {
  result.value = true;
  RRef.value.initGraphAnalyse();
};
</script>
