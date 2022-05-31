package com.example.demo.mapper;

import com.example.demo.model.menu.Edge;

import java.util.ArrayList;
import java.util.List;

public class EdgeMapper {
    public List<Edge> edges;

    public EdgeMapper() {
        edges = new ArrayList<>();
    }

    public List<Edge> findAllEdge() {
        return edges;
    }

    public void addEdge(Edge edge) {
        edges.add(edge);
    }

    public void updateEdge(Edge edge) {
        Edge e = findEdgeById(edge.getId());
        edges.remove(edge);
        edges.add(e);
    }

    public void deleteAllEdges() {
        edges.clear();
    }

    public Edge findEdgeById(String id) {
        for (int i = 0; i < edges.size(); i++) {
            if (id.equals(edges.get(i).getId())) {
                return edges.get(i);
            }
        }
        return null;
    }

    public void deleteEdgeById(String id) {
        Edge edge = findEdgeById(id);
        edges.remove(edge);
    }
}
