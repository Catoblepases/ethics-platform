//package com.example.demo.controller;
//
//import com.example.demo.model.Generator;
//import com.example.demo.model.Track;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.annotation.Resource;
//import java.util.ArrayList;
//import java.util.List;
//
//
//@RestController
//@RequestMapping("/generator")
//public class GeneratorController {
//    @Resource
//    Generator generator;
//
//    @GetMapping("/allTrack")
//    List<String> getAllTrack(){
//        List<String> ls=new ArrayList<>();
//        for (Track track:generator.tracks){
//            ls.add(track.getName());
//        }
//        return ls;
//    }
//
//    @GetMapping("/allTrackNumber")
//    List<Integer> getAllTrackNumber(){
//        List<Integer> ls=new ArrayList<>();
//        for (Track track:generator.tracks){
//            ls.add(track.size());
//        }
//        return ls;
//    }
//
//    @GetMapping("/number/{trackId}")
//    int getTrackNumber(@PathVariable String trackId){
//        return generator.findTrack(trackId).size();
//    }
//}
