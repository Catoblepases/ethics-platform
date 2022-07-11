package com.example.demo.controller;

import com.example.demo.mapper.EdgeMapper;
import com.example.demo.model.menu.Edge;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/*Le classe EdgeController transmet les données du connexion avec le front-end. */
@RestController
@RequestMapping("/Edge")
public class EdgeController {
    @Resource
    EdgeMapper edgeMapper;

    @GetMapping
    List<Edge> getAllEdges() {
        return edgeMapper.findAllEdge();
    }

    @GetMapping("/{id}")
    Edge getEdgeById(@PathVariable("id") String id) {
        return edgeMapper.findEdgeById(id);
    }

    @GetMapping("/test")
    String getEdge() {
        return "edge";
    }

    @PostMapping
    void addEdge(@RequestBody Edge edge) {
        edgeMapper.addEdge(edge);
    }

    @PutMapping
    Edge updateEdgeById(@RequestBody Edge edge) {
        edgeMapper.updateEdge(edge);
        return edge;
    }

    @DeleteMapping("/{edgeId}")
    String deleteEdgeById(@PathVariable("edgeId") String edgeId) {
        edgeMapper.deleteEdgeById(edgeId);
        return "sucess";
    }

    @DeleteMapping("/deleteAll")
    String deleteAllEdges() {
        edgeMapper.deleteAllEdges();
        return "sucess";
    }

}
