<template>
  <div id="contextmenu" class="menu">
    <div id="sectionMenu" class="menu">
      <div class="contextmenu_item" @click="cha(currentData)">查哨</div>
      <div class="contextmenu_item" @click="cha(currentData)">查哨</div>
    </div>
    <div id="sectionMenu" class="menu">
      <div class="contextmenu_item" @click="cha(currentData)">查哨</div>
      <div class="contextmenu_item" @click="cha(currentData)">查哨</div>
    </div>
  </div>
</template>

<style scoped lang="less" rel="stylesheet/less">
.context-menu {
  position: absolute;
  min-width: 120px;
  width: auto !important;
  z-index: 9999;
  background: #fff;
  box-shadow: 0 0 5px 2px rgba(0, 0, 0, 0.1);
  padding: 5px 0;
  .tool-box {
    padding: 0;
    .tool-item {
      padding: 8px 10px;
      display: flex;
      align-items: center;
      &:hover {
        color: #000000;
        background: rgba(0, 0, 0, 0.1);
        .tool-box {
          visibility: visible;
        }
      }
      .tool-box {
        visibility: hidden;
        position: absolute;
        top: 0;
        padding: 5px 0;
        background: #fff;
        box-shadow: 0 0 5px 2px rgba(0, 0, 0, 0.1);
      }
      .item-link {
        display: inline-block;
        vertical-align: middle;
        width: 100%;
      }
      .item-icon {
        display: inline-block;
        width: 16px;
        height: 16px;
        line-height: 1;
      }
      .item-label {
        flex: 1 1 auto;
        margin: 0 10px;
        line-height: 1;
        min-width: 60px;
        white-space: nowrap;
      }
      .item-shortcut {
        line-height: 1;
      }
      .item-more {
        display: inline-block;
        float: right;
        width: 16px;
        line-height: 1;
      }
    }
    .divider {
      width: calc(~"100% - 10px");
    }
  }
}
</style>
