/** * Created by OXOYO on 2018/3/26. * * */

<style scoped lang="less" rel="stylesheet/less">
.handler {
  display: inline-block;
  position: absolute;
  background: #ffffff;
  border: 1px solid #dddddd;
  overflow: hidden;
  transition: all 0.2s ease-in 0.1s;
  cursor: pointer;
  &:hover {
    background: #dddddd;
  }
  &.horizontal {
    width: 20px;
    height: 20px;
    line-height: 20px;
    left: 50%;
    margin-left: -10px;
  }
  &.vertical {
    width: 20px;
    height: 20px;
    line-height: 20px;
    top: 50%;
    margin-top: -10px;
  }
  &.top {
    top: -11px;
    border-bottom: none;
    border-radius: 20px 20px 0 0;
    box-shadow: 0 -2px 2px 0 rgba(0, 0, 0, 0.1);
  }
  &.right {
    right: -11px;
    border-left: none;
    border-radius: 0 20px 20px 0;
    box-shadow: 2px 0 2px 0 rgba(0, 0, 0, 0.1);
  }
  &.bottom {
    bottom: -11px;
    border-top: none;
    border-radius: 0 0 20px 20px;
    box-shadow: 0 2px 2px 0 rgba(0, 0, 0, 0.1);
  }
  &.left {
    left: -11px;
    border-right: none;
    border-radius: 20px 0 0 20px;
    box-shadow: -2px 0 2px 0 rgba(0, 0, 0, 0.1);
  }
  &.expand {
    &.top {
      margin-top: 0;
    }
    &.right {
      margin-right: 0;
    }
    &.bottom {
      margin-bottom: 0;
    }
    &.left {
      margin-left: 0;
    }
  }
}
</style>

<template>
  <div :class="className" @click="toggleHandler"></div>
</template>

<script setup lang="ts">
import { computed } from "vue";

type PositionHV = "horizontal" | "vertical";
type PositionRL = "top" | "right" | "bottom" | "left";

interface Props {
  mode: { type: PositionHV; default: "horizontal" };
  position: PositionRL;
  expand: {
    type: Boolean;
    default: true;
  };
  callback: Function;
}

const props = defineProps<Props>();

const className = computed(() => {
  return ["handler", props.mode, props.position, props.expand];
});

function toggleHandler() {
  props.callback();
}
</script>
