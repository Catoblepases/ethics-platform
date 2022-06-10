// import G6, { GraphData, TreeGraphData, Graph, Item } from "@antv/g6";

export const stateStyle = {
  "group:alive": {
    group1: {
      fill: "green",
    },
    group2: {
      fill: "green",
    },
    group3: {
      fill: "green",
    },
    group4: {
      fill: "green",
    },
    group5: {
      fill: "green",
    },
    group6: {
      fill: "green",
    },
    group7: {
      fill: "green",
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

    "single-node"
  );

  G6.registerNode(
    "carriageWithbridge",
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
        });

        group.addShape("rect", {
          attrs: {
            x: -30,
            y: -15,
            width: 10,
            height: 30,
            fill: "#6bccdb",
          },
        });
        return rect;
      },
    },
    "rect"
  );

  G6.registerNode(
    "carriageWithbridgeandgroup",
    {
      draw(cfg, group) {
        let y: number = Math.round(7 / 2);
        let nb = cfg?.nb;
        const size = cfg?.size[0];
        const gcolor = cfg?.style.gcolor;
        var nbLine = Math.round(Math.sqrt(nb)) + 1;
        var h = Math.round(size / (nbLine + 1));

        group.addShape("rect", {
          attrs: {
            x: -30,
            y: -15,
            width: 10,
            height: 30,
            fill: "#6bccdb",
          },
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
            });
            nb--;
          }
        }
        return rect;
      },
    },
    "carriageWithbridge"
  );
}
