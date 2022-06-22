package com.example.demo.controller;

import com.example.demo.mapper.EdgeMapper;
import com.example.demo.mapper.NodeMapper;
import com.example.demo.model.*;
import com.example.demo.model.menu.Edge;
import com.example.demo.model.menu.NodeStandard;
import com.example.demo.model.menu.Node;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/Node")
public class NodeController {

    @Resource
    NodeMapper nodeMapper;

    @Resource
    EdgeMapper edgeMapper;

    @GetMapping
    List<Node> getAllNodes() {
        return nodeMapper.findAllNode();
    }

    @GetMapping("/{id}")
    NodeStandard getNodeById(@PathVariable("id") String id) {
        return nodeMapper.findNodeById(id);
    }

    @GetMapping("/test")
    String getNode() {
        return "node";
    }

    @PostMapping
    void addNode(@RequestBody Node node) {
        nodeMapper.addNode(node);
    }

    @PutMapping
    NodeStandard updateNodeById(@RequestBody Node node) {
        nodeMapper.updateNode(node);
        return node;
    }

    @DeleteMapping("/{nodeId}")
    String deleteNodeById(@PathVariable("nodeId") String nodeId) {
        nodeMapper.deleteNodeById(nodeId);
        return "sucess";
    }

    @DeleteMapping("/deleteAll")
    String deleteAllNodes() {
        nodeMapper.deleteAllNodes();
        return "sucess";
    }

    @Resource
    Generator generator;

    @PostMapping("/addAll/{filePath}")
    public void generateAllNodes(@PathVariable String filePath) {
        nodeMapper.deleteAllNodes();
        edgeMapper.deleteAllEdges();
        List<Track> tracks = generator.tracks;
        String[] fills = {"#90CAF9", "#5C6BC0", "#FFF176", "green", "white", "pink"};
        int i = 0;
        for (Track track : tracks) {
            for (Carriage carriage : track) {
                boolean original = carriage.equals(generator.train.getOriginPosition());
                Node node = new Node(carriage.getName(), 0, 0, "track", carriage.getInformation(), fills[i], carriage, original);
                nodeMapper.addNode(node);
            }
            i++;
        }
        List<Switch> ls = new ArrayList<>();
        for (Track track : tracks) {
            for (Carriage carriage : track) {
                NodeStandard node = getNodeById(carriage.getName());
                if (carriage.suivant != null) {
                    NodeStandard target = getNodeById(carriage.suivant.getName());
                    if (target != null) {
                        edgeMapper.addEdge(new Edge(node.getId(), target.getId()));
                    }
                }
                Switch sw = carriage.getSwitch();
                if ((sw != null) && (!ls.contains(sw))) {
                    NodeStandard target = getNodeById(carriage.getSwitch().next(carriage).getName());
                    if (target != null) {
                        edgeMapper.addEdge(new Edge(sw.getTrackBegin().getName(), sw.getTrackEnd().getName()));
                        ls.add(sw);
                    }
                }
            }
        }
    }
}
