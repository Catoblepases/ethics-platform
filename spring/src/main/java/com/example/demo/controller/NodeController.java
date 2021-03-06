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

/*Le classe NodeController transmet les données du nœud avec le front-end. */
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

    /*Obtenir un nœud par son identifiant*/
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

    /*ajouter les nodes*/
    @PostMapping("/addAll/{filePath}")
    public void generateAllNodes(@PathVariable String filePath) {
        nodeMapper.deleteAllNodes();
        edgeMapper.deleteAllEdges();
        List<Track> tracks = generator.tracks;
        String[] fills = {"#90CAF9", "#5C6BC0", "#FFF176", "green", "darkgrey", "pink"};
        int i = 0;
        for (Track track : tracks) {
            for (Section section : track) {
                boolean original = section.equals(generator.train.getOriginPosition());
                Node node = new Node(section.getName(), 0, 0, "track", section.getInformation(), fills[i], section, original);
                nodeMapper.addNode(node);
            }
            i++;
            if (i >= fills.length) {
                i = 0;
            }
        }
        List<Switch> ls = new ArrayList<>();
        for (Track track : tracks) {
            for (Section section : track) {
                NodeStandard node = getNodeById(section.getName());
                if (section.suivant != null) {
                    NodeStandard target = getNodeById(section.suivant.getName());
                    if (target != null) {
                        edgeMapper.addEdge(new Edge(node.getId(), target.getId()));
                    }
                }
                Switch sw = section.getSwitch();
                if ((sw != null) && (!ls.contains(sw))) {
                    NodeStandard target = getNodeById(section.getSwitch().next(section).getName());
                    if (target != null) {
                        edgeMapper.addEdge(new Edge(sw.getTrackBegin().getName(), sw.getTrackEnd().getName()));
                        ls.add(sw);
                    }
                }
            }
        }
    }
}
