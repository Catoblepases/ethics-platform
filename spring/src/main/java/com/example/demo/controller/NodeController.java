package com.example.demo.controller;

import com.example.demo.mapper.EdgeMapper;
import com.example.demo.mapper.NodeMapper;
import com.example.demo.model.*;
import com.example.demo.model.menu.Edge;
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
    Node getNodeById(@PathVariable("id") String id) {
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
    Node updateNodeById(@RequestBody Node node) {
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
        String[] fills = {"red", "blue", "yellow", "green", "white", "pink"};
        int i = 0;
        for (Track track : tracks) {
            for (Carriage carriage : track) {
                boolean original = carriage.equals(generator.train.getOriginPosition());
                Node node = new Node(carriage.getName(), 0, 0, "track", carriage.getInformation(), fills[i], carriage, original);
                nodeMapper.addNode(node);
            }
            i++;
        }
        for (Track track : tracks) {
            for (Carriage carriage : track) {
                Node node = getNodeById(carriage.getName());
                List<Switch> ls = new ArrayList<>();
                if (carriage.suivant != null) {
                    Node target = getNodeById(carriage.suivant.getName());
                    edgeMapper.addEdge(new Edge(node.getId(), target.getId()));
                }
                Switch sw = carriage.getSwitch();
                if ((sw != null) && (!ls.contains(sw))) {
                    Node target = getNodeById(carriage.getSwitch().next(carriage).getName());
                    edgeMapper.addEdge(new Edge(node.getId(), target.getId()));
                    ls.add(sw);
                }
            }
        }
    }
}
