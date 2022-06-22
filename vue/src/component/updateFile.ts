import axios from "axios";

export async function updateClingo() {
  await axios.post("api/carriage/updateAction").then(() => {
    axios.post("api/clingo/update").then(() => {
      console.log("update:clingo");
    });
  });
}
