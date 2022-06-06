package com.example.demo.mapper;

import com.example.demo.model.menu.Node;
import com.example.demo.model.menu.Node;

import java.util.ArrayList;
import java.util.List;

public class NodeMapper {
    public List<Node> nodes;

    public NodeMapper() {
        nodes = new ArrayList<>();
    }

    public List<Node> findAllNode() {
        return nodes;
    }

    public void addNode(Node node) {
        nodes.add(node);
    }

    public void updateNode(Node node) {
        Node e = findNodeById(node.getId());
        nodes.remove(node);
        nodes.add(e);
    }

    public void deleteAllNodes() {
        nodes.clear();
    }

    public Node findNodeById(String id) {
        for (int i = 0; i < nodes.size(); i++) {
            if (id.equals(nodes.get(i).getId())) {
                return nodes.get(i);
            }
        }
        return null;
    }

    public void deleteNodeById(String id) {
        Node node = findNodeById(id);
        nodes.remove(node);
    }
}
