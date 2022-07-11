import axios from "axios";

export async function updateClingo() {
  await axios.post("api/section/updateAction").then(() => {
    axios.post("api/clingo/update").then(() => {
      console.log("update:clingo");
    });
  });
}
