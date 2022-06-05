import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue";

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  // css: {
  //   preprocessorOptions: {
  //     sass: {
  //       additionalData: `@import "@/assets/css/element.scss";`,
  //     },
  //   },
  // },
  server: {
    port: 9000,
    proxy: {
      "^/api": {
        target: "http://localhost:8080",
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api/, ""),
      },
      "/api": {
        target: "http://localhost:8080", //
        ws: true, // websockets
        changeOrigin: true, // needed for virtual hosted sites
        rewrite: (path) => path.replace("^/api", ""),
      },
      "^/User": {
        target: "http://localhost/User",
        changeOrigin: true,
      },
      "^/actuator/health": {
        target: "http://localhost:8080",
      },
    },
  },
});
