import { createApp } from "vue";
// import App from "./component/Clingo.vue";
import App from "./App2.vue";
import { KEY_INTEROP, KEY_LOG } from "./keys";
import interopFallback from "./interop-fallback";
import ElementPlus from "element-plus";
import "element-plus/theme-chalk/index.css";
import axios from "axios";

// import VueAxios from "vue-axios";

axios.defaults.headers.get["header-name"] = "value";

import * as ElementPlusIconsVue from "@element-plus/icons-vue";
import "./assets/css/icon.css";

const app = createApp(App);
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component);
}

// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-ignore the unknown interop on window object
const interop = window.interop || interopFallback;
app.use(ElementPlus, { size: "small", zIndex: 3000 });

// app.use(VueAxios);
app.provide(KEY_INTEROP, interop);
app.provide(KEY_LOG, interop.log);

app.mount("#app");
