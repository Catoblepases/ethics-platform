import { createApp } from "vue";
import App from "./App.vue";
import { KEY_INTEROP, KEY_LOG } from "./keys";
import interopFallback from "./interop-fallback";
import axios from "axios";

import { Quasar, Notify, Loading } from "quasar";
// Import icon libraries
import "@quasar/extras/material-icons/material-icons.css";
// Import Quasar css
import "quasar/src/css/index.sass";

axios.defaults.headers.get["header-name"] = "value";
const app = createApp(App);

import ElementPlus from "element-plus";
import "element-plus/theme-chalk/index.css";
import * as ElementPlusIconsVue from "@element-plus/icons-vue";
import "./assets/css/icon.css";
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component);
}

const interop = window.interop || interopFallback;
app.use(ElementPlus, { size: "small", zIndex: 3000 });

app.use(Quasar, {
  plugins: {
    Notify,
    Loading,
  },
  config: {
    notify: {
      /* look at QuasarConfOptions from the API card */
    },
  },
});

app.provide(KEY_INTEROP, interop);
app.provide(KEY_LOG, interop.log);

app.mount("#app");
