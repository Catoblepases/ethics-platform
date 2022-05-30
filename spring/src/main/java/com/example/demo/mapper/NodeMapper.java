package com.example.demo.mapper;

import com.example.demo.model.Node;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public interface NodeMapper {

    @Select("Select * from Node")
    List<Node> findAllNode();

    @Update("insert into Node values (#{id},#{x},#{y},#{label},#{typeName},#{fill})")
    @Transactional
    void addNode(Node node);

    @Update("Update Node set x=#{x},y=#{y},label=#{label},typeName=#{typeName},fill=#{fill} where id=#{id}")
    @Transactional
    void updateNode(Node node);

    @Delete("Delete from Node")
    void deleteAllNodes();

    @Select("Select * from node where id=#{id};")
    Node findNodeById(String id);
    @Delete("Delete from User where id=#{id}")
    void deleteNodeById(String idx);
}
