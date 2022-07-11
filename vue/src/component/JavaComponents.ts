import axios from "axios";
import { ElMessage } from "element-plus";
import { Ref, ref } from "vue";

export class Group {
  name: string = "";
  size: number = -1;
}

export class Switch {
  trackBegin: string = "";
  trackEnd: string = "";
  trackBeginName: string = "";
  trackBeginNumber: number = -1;
  trackEndName: string = "";
  trackEndNumber: number = -1;
}

export class Bridge {
  name: string = "";
  group: Group | null | undefined = new Group();
}

export class Data {
  id: string = "";
  track: string = "";
  index: number = -1;
  switchs: Switch | null | undefined = new Switch();
  group: Group | null | undefined = new Group();
  bridge: Bridge | null | undefined = new Bridge();
  positionOriginal: Boolean = false;
}

export interface DataRef {
  // [x: string]: any;
  orignalPosition: Ref<boolean>;
  switchTrackValue: Ref<string>;
  switchTrackNumber: Ref<number>;
  nameBridge: Ref<string>;
  group: Ref<Group>;
  groupBridge: Ref<Group>;
}

export const notEqualNull = (val: any) => {
  if (val === "" || val === "-1" || val === null) {
    return false;
  }
  return true;
};

export const setGroup = (g: Group | null | undefined, dest: Ref<Group>) => {
  if (!g) {
    dest.value.name = "";
    dest.value.size = -1;
  } else {
    dest.value.name = g.name;
    dest.value.size = g.size;
  }
};

export const setSwitch = (
  s: Switch | null | undefined,
  destValue: Ref<string>,
  destNumber: Ref<number>
) => {
  if (!s) {
    destValue.value = "";
    destNumber.value = -1;
  } else {
    destValue.value = s.trackEndName;
    destNumber.value = s.trackEndNumber;
  }
};

export const setBridge = (
  b: Bridge | null | undefined,
  dest_name: Ref<string>,
  dest_group: Ref<Group>
) => {
  if (b?.name != "" && b != null) {
    dest_name.value = b!.name;
  } else {
    dest_name.value = "";
  }
  setGroup(b?.group, dest_group);
};

export const setOriginal = (o: Boolean, dest: Ref<Boolean>) => {
  console.log(o);
  dest.value = o.valueOf();
};

export const setDataRef = (data: Data, dataR: DataRef) => {
  if (notEqualNull(data.bridge)) {
    dataR.nameBridge.value = data.bridge!.name;
  }
  setGroup(data.group, dataR.group);
  setGroup(data.bridge?.group, dataR.groupBridge);
  setSwitch(data.switchs, dataR.switchTrackValue, dataR.switchTrackNumber);
  setBridge(data.bridge, dataR.nameBridge, dataR.groupBridge);
  setOriginal(data.positionOriginal, dataR.orignalPosition);
};

export const updateGroup = (
  data: Data | null | undefined,
  dest: Ref<Group>
) => {
  var g = data!.group;
  if (!g) {
    return;
  }
  if (notEqualNull(dest.value?.name) && notEqualNull(dest.value?.size)) {
    g!.name = dest.value?.name;
    g!.size = dest.value?.size;
  } else if (!notEqualNull(g!.name) || !notEqualNull(g!.size)) {
    data!.group = null;
  }
};

export const updateSwitch = (
  data: Data | null | undefined,
  destValue: Ref<string>,
  destNumber: Ref<number>
) => {
  var s = data?.switchs;
  if (s === null) {
    data!.switchs = new Switch();
    s = data?.switchs;
    s!.trackBegin = data!.id;
    s!.trackBeginName = data!.track;
    s!.trackBeginNumber = data!.index;
  }
  if (notEqualNull(destValue.value) && notEqualNull(destNumber.value)) {
    data!.switchs!.trackEndName = destValue.value;
    data!.switchs!.trackEndNumber = destNumber.value;
    data!.switchs!.trackEnd = destValue.value + "(" + destNumber.value + ")";
  } else if (
    data!.switchs != null &&
    (!notEqualNull(data!.switchs?.trackEndName) ||
      !notEqualNull(data!.switchs?.trackEndNumber))
  ) {
    data!.switchs = null;
  }
};

export const updateBridge = (
  data: Data | null | undefined,
  dest_name: Ref<string>
) => {
  if (notEqualNull(dest_name.value)) {
    data!.bridge!.name = dest_name.value;
  } else if (!notEqualNull(data!.bridge!.name)) {
    data!.bridge = null;
  }
};

export const updateOriginal = (data: Data, dest: Ref<Boolean>) => {
  data.positionOriginal = dest.value;
};

export const updateData = (data: Data, dataR: DataRef) => {
  // updateGroup(data, dataR.group);
  // updateGroup(data, dataR.groupBridge);
  updateSwitch(data, dataR.switchTrackValue, dataR.switchTrackNumber);
  updateOriginal(data, dataR.orignalPosition);
  // updateBridge(data,dataR.nameBridge)
};

export const deleteBridge = (data: Data) => {
  data.bridge = null;
};

export const deleteSwitch = (data: Data) => {
  data.switchs = null;
};

export const deleteSection = (data: Data) => {
  ElMessage("try to delete this section");
  axios.delete("api/section/" + data.id).then(() => {
    ElMessage("delete section successfully");
  });
};

export const changeString = (s: string, rs: Ref<String>) => {
  if (rs.value === "" || rs.value != s) {
    rs.value = s;
  } else {
    rs.value = "sucess";
  }
};
