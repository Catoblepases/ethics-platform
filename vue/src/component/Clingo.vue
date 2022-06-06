<template>
  <el-scrollbar>
    <el-form :model="form" label-width="120px" width="600px">
      <ClingoItem
        clabel="action theory"
        :cmodel="form.actionTheory"
        disabled="true"
      ></ClingoItem>
      <ClingoItem
        clabel="Context"
        :cmodel="form.context"
        disabled="true"
      ></ClingoItem>
      <ClingoItem
        clabel="causal theory"
        :cmodel="form.causalTheory"
        disabled="true"
      ></ClingoItem>
      <ClingoItem
        clabel="ethical spec"
        :cmodel="form.ethicalSpec"
        disabled="true"
      ></ClingoItem>
      <ClingoItem
        clabel="ethical theories"
        :cmodel="form.ethicalTheories"
        disabled="true"
      ></ClingoItem>
      <ClingoItem
        clabel="trace"
        :cmodel="form.trace"
        disabled="true"
      ></ClingoItem>
      <ClingoItem
        clabel="causal trace"
        :cmodel="form.causalTrace"
        disabled="true"
      ></ClingoItem>
      <ClingoItem
        clabel="output"
        :cmodel="form.output"
        disabled="false"
      ></ClingoItem>

      <el-form-item>
        <el-row>
          <el-button type="primary" @click="onSubmit">Download</el-button>
          <el-button>Cancel</el-button>
        </el-row>
      </el-form-item>
    </el-form>
  </el-scrollbar>
</template>

<script lang="ts" setup>
import { reactive } from "vue";
import ClingoItem from "./ClingoFileItem.vue";

// do not use same name with ref
const form = reactive({
  actionTheory: "./eventMotorOnly.lp",
  context: "./trolley1act.lp",
  causalTheory: "./causalMotorPrevents.lp",
  ethicalSpec: "./trollEthSpec.lp",
  ethicalTheories: "./restrEthicalMotor.lp",
  trace: "./traceTroll.lp",
  causalTrace: "./traceTrollC.lp",
  results: "imp/2\nper/2",
  output: "./resultTroll.lp",
});

const onSubmit = () => {
  console.log("submit!");
};

</script>
