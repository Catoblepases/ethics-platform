import G6 from "@antv/g6";

export const trainSetup = {
  id: "train",
  x: 200,
  y: 100,
  type: "rect",
  size: [35, 20],
  style: { fill: "#5AD8A6", lineWidth: 1, opacity: 0.8, fillOpacity: 0.8 },
};

export const dagreLayout = {
  type: "dagre",
  rankdir: "LR",
  align: "DL",
  layer: 0,
  nodesep: 10,
  ranksep: 20,
  controlPoints: true,
};

export function changeStyleByType(nodes: Array<Node>) {
  nodes.forEach((node: Node) => {
    if (!node.style) {
      node.style = { fill: node.fill, cfill: node.fill, gcolor: "lightgreen" };
    }
    node.x = undefined;
    node.y = undefined;
    switch (node.typeName) {
      case "track": {
        node.type = "rect";
        node.size = [35, 20];
        if (node.infoCarriage.group != null) {
          node.type = "carriageWithGroup";
          node.nb = node.infoCarriage.group.size;
        }
        if (node.infoCarriage.bridge != null) {
          console.log(node.infoCarriage.bridge);

          if (node.infoCarriage.bridge.group != null) {
            console.log("carriage with group and bridge");
            node.type = "carriageWithBridgeAndGroup";
            node.nb = node.infoCarriage.bridge.group.size;
          } else {
            node.type = "carriageWithBridge";
          }
        }
        break;
      }
      case "bridge": {
        node.type = "rect";
        node.size = [35, 20];
        break;
      }
      case "train": {
        node.type = "ellipse";
        node.size = [35, 20];
        break;
      }
    }
  });
}

export const stateStyle = {
  "group:alive": {
    group1: {
      fill: "lightgreen",
    },
    group2: {
      fill: "lightgreen",
    },
    group3: {
      fill: "lightgreen",
    },
    group4: {
      fill: "lightgreen",
    },
    group5: {
      fill: "lightgreen",
    },
    group6: {
      fill: "lightgreen",
    },
    group7: {
      fill: "lightgreen",
    },
  },

  "group:dead": {
    group1: {
      fill: "red",
    },
    group2: {
      fill: "red",
    },
    group3: {
      fill: "red",
    },
    group4: {
      fill: "red",
    },
    group5: {
      fill: "red",
    },
    group6: {
      fill: "red",
    },
    group7: {
      fill: "red",
    },
  },
  selected: {
    stroke: "black",
    fill: "black",
  },
  hover: {
    fill: "red",
    "keyShape-name": {
      fill: "red",
    },
  },
};

export const defaultNodeStyle = {
  shape: "circle",
  size: [100],
  color: "#5B8FF9",
  style: {
    fill: "steelblue",
    stroke: "#666",
    lineWidth: 1,
  },
  // Label text configuration on nodes
  labelCfg: {
    style: {
      fill: "#fff",
      fontSize: 9,
    },
  },
};

export const defaultEdgeStyle = {
  style: {
    stroke: "#e2e2e2",
    endArrow: true,
    startArrow: false,
  },
};

export const defaultMode = {
  default: [
    "drag-canvas",
    "zoom-canvas",
    "drag-node",
    "click-select",
    {
      type: "tooltip",
      formatText(model) {
        const text =
          "label: " + model.label + "<br/> typeName: " + model.typeName;
        return text;
      },
    },
  ],
  animation: ["click-select"],
};

export function registerNodes(G6: any) {
  G6.registerNode(
    "carriageWithGroup",
    {
      options: {
        stateStyles: stateStyle,
      },
      draw(cfg, group) {
        let y: number = Math.round(7 / 2);
        let nb = cfg?.nb;
        const size = cfg?.size[0];
        const gcolor = cfg?.style.gcolor;
        var nbLine = Math.round(Math.sqrt(nb)) + 1;
        var h = Math.round(size / (nbLine + 1));

        const rect = group.addShape("rect", {
          zIndex: 10,
          attrs: {
            x: -12,
            y: -12,
            width: cfg?.size[0],
            height: cfg?.size[1],
            fill: cfg?.style?.cfill,
          },
          name: "carriage",
        });

        let GROUP: Array<any> = [];
        let initial: number = 1;

        for (let i = 0; i < nbLine; i++) {
          if (initial > nb) {
            break;
          }
          for (let j = 0; j < nbLine; j++) {
            if (initial > nb) {
              break;
            }
            GROUP.push(undefined);
            GROUP[initial - nb] = group.addShape("circle", {
              zIndex: 10,
              attrs: {
                x: h * j,
                y: h * i,
                r: 5,
                fill: cfg?.style?.gcolor,
              },
              name: "group" + initial,
            });
            initial++;
          }
          group.addShape("text", {
            attrs: {
              text: cfg?.label,
              x: -10,
              y: 0,
              fontSize: 8,
              textAlign: "left",
              textBaseline: "middle",
              fill: "white",
            },
            // must be assigned in G6 3.3 and later versions. it can be any value you want
            name: "text-shape",
          });
        }
        return rect;
      },
    },

    "rect"
  );

  G6.registerNode(
    "carriageWithBridge",
    {
      draw(cfg, group) {
        let y: number = Math.round(7 / 2);
        let nb = cfg?.nb;
        const size = cfg?.size[0];
        var nbLine = Math.round(Math.sqrt(nb)) + 1;
        var h = Math.round(size / (nbLine + 1));

        const rect = group.addShape("rect", {
          zIndex: 10,
          attrs: {
            x: -12,
            y: -12,
            width: cfg?.size[0],
            height: cfg?.size[1],
            fill: cfg?.fill,
          },
          name: "carriage",
        });

        group.addShape("rect", {
          attrs: {
            x: -30,
            y: -15,
            width: 10,
            height: 30,
            fill: "#6bccdb",
          },
          name: "bridge",
        });

        group.addShape("text", {
          attrs: {
            text: cfg?.label,
            x: -10,
            y: 0,
            fontSize: 8,
            textAlign: "left",
            textBaseline: "middle",
            fill: "white",
          },
          // must be assigned in G6 3.3 and later versions. it can be any value you want
          name: "text-shape",
        });

        return rect;
      },
    },
    "rect"
  );

  G6.registerNode(
    "carriageWithBridgeAndGroup",
    {
      draw(cfg, group) {
        let y: number = Math.round(7 / 2);
        let nb = cfg?.nb;
        const size = cfg?.size[0];
        const gcolor = cfg?.style.gcolor;
        var nbLine = Math.round(Math.sqrt(nb)) + 1;
        var h = Math.round(size / (nbLine + 1));

        const rect = group.addShape("rect", {
          zIndex: 10,
          attrs: {
            x: -12,
            y: -12,
            width: cfg?.size[0],
            height: cfg?.size[1],
            fill: cfg?.style?.cfill,
          },
          name: "carriage",
        });

        group.addShape("rect", {
          attrs: {
            x: -30,
            y: -15,
            width: 10,
            height: 30,
            fill: "#6bccdb",
          },
          name: "bridge",
        });

        for (let i = 0; i < nbLine; i++) {
          if (nb <= 0) {
            break;
          }
          for (let j = 0; j < nbLine; j++) {
            if (nb <= 0) {
              break;
            }
            group.addShape("circle", {
              zIndex: 10,
              attrs: {
                x: h * i - 25,
                y: h * j - 10,
                r: 5,
                fill: gcolor,
              },
              name: "group" + nb,
            });
            nb--;
          }
        }
        group.addShape("text", {
          attrs: {
            text: cfg?.label,
            x: -10,
            y: 0,
            fontSize: 8,
            textAlign: "left",
            textBaseline: "middle",
            fill: "white",
          },
          // must be assigned in G6 3.3 and later versions. it can be any value you want
          name: "text-shape",
        });
        return rect;
      },
    },
    "carriageWithGroup"
  );
}
