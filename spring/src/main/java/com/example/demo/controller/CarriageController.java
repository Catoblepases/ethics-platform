package com.example.demo.controller;

import com.example.demo.mapper.SimulationMapper;
import com.example.demo.model.*;
import com.example.demo.model.clingo.ClingoCausal;
import com.example.demo.model.clingo.ClingoSimulation;
import com.example.demo.model.clingo.ClingoTrace;
import com.example.demo.model.clingo.G6Info;
import com.example.demo.model.menu.EventForm;
import com.example.demo.model.menu.EventItem;
import com.example.demo.model.menu.InfoCarriage;
import org.apache.ibatis.annotations.Delete;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/carriage")
public class CarriageController {

    @Resource
    Generator generator;

    @GetMapping("/{id}")
    InfoCarriage getCarriageById(@PathVariable("id") String id) {
        Carriage carriage = (Carriage) generator.readPosition(id);
        Boolean isOriginal = generator.train.getOriginPosition().equals(carriage);
        InfoCarriage infoCarriage = new InfoCarriage(carriage, isOriginal);
        return infoCarriage;
    }

    @PutMapping
    String updateCarriageById(@RequestBody InfoCarriage infoCarriage) {
        Carriage carriage = (Carriage) generator.readPosition(infoCarriage.getId());
        infoCarriage.updateCarriage(carriage, generator);
        return "success";
    }

    @GetMapping("/test")
    String getCarriage() {
        return "carriage";
    }

    @PostMapping("/add/{trackId}")
    void addCarriage(@PathVariable String trackId) {
        generator.findTrack(trackId).addNewCarriage();
    }

    @PostMapping("/reset")
    void resetGenerator() {
        generator.read("./data/testsw");
    }

    @PostMapping("/switch")
    void addSwitch(@RequestBody String[] carriageIds) {
        Carriage carriage1 = (Carriage) generator.readPosition(carriageIds[0]);
        Carriage carriage2 = (Carriage) generator.readPosition(carriageIds[1]);
        Switch sw = new Switch(carriage1, carriage2);
        generator.switchs.add(sw);
    }

    @PostMapping("/bridge")
    void addBridge(@RequestBody String[] carriageId_BridgeId) {
        Carriage carriage = (Carriage) generator.readPosition(carriageId_BridgeId[0]);
        Bridge bridge = new Bridge(carriageId_BridgeId[1], carriage);
        generator.bridges.add(bridge);
    }

    @PostMapping("/group")
    void addGroup(@RequestBody String[] update) {
        Carriage carriage = (Carriage) generator.readPosition(update[0]);
        int number = Integer.parseInt(update[2]);
        String name = update[1];
        Group group = new Group(number, carriage, name);
        generator.groups.add(group);
    }

    @GetMapping("/{id}/switch")
    List<String> getSwitch(@PathVariable String id) {
        Carriage carriage = (Carriage) generator.readPosition(id);
        List<String> ls = new ArrayList<>();
        ls.add(carriage.getSwitch().getOtherCarriage(carriage).toString());
        ls.add(carriage.toString());
        return ls;
    }

    @GetMapping("/{id}/bridge/name")
    String getBridgeName(@PathVariable String id) {
        Carriage carriage = (Carriage) generator.readPosition(id);
        List<String> ls = new ArrayList<>();
        Bridge b = carriage.getBridge();
        return b.getName();
    }

    @GetMapping("/{id}/group")
    List<String> getGroupInfo(@PathVariable String id) {
        Carriage carriage = (Carriage) generator.readPosition(id);
        List<String> ls = new ArrayList<>();
        Group g = carriage.getGroup();
        ls.add(g.getName());
        ls.add(Integer.toString(g.getNb()));
        return ls;
    }

    @DeleteMapping("/{carriageId}")
    String deleteCarriageById(@PathVariable("carriageId") String carriageId) {
        Carriage carriage = (Carriage) generator.readPosition(carriageId);
        if (carriage == null) {
            return "fail";
        }
        Track track = generator.findTrack(carriage.getTrack());
        track.remove(carriage);
        return "sucess";
    }

    @DeleteMapping("/switch/{carriageId}")
    String deleteSwitchById(@PathVariable("carriageId") String carriageId) {
        Carriage carriage = (Carriage) generator.readPosition(carriageId);
        carriage.deleteSwitch();
        return "sucess";
    }

    @DeleteMapping("/group/{carriageId}")
    String deleteGroupById(@PathVariable("carriageId") String carriageId) {
        Carriage carriage = (Carriage) generator.readPosition(carriageId);
        carriage.deleteGroup();
        return "sucess";
    }

    @DeleteMapping("/bridge/{carriageId}")
    String deleteBridgeById(@PathVariable("carriageId") String carriageId) {
        Carriage carriage = (Carriage) generator.readPosition(carriageId);
        carriage.deleteBridge();
        return "sucess";
    }

    @PostMapping("/{id}")
    String setOriginalPosition(@PathVariable String id) {
        Carriage carriage = (Carriage) generator.readPosition(id);
        generator.train.setOriginPosition(carriage);
        return "success";
    }

    @GetMapping("/allTrack")
    List<String> getAllTrack() {
        List<String> ls = new ArrayList<>();
        for (Track track : generator.tracks) {
            ls.add(track.getName());
        }
        return ls;
    }

    @GetMapping("/allTrackNumber")
    List<Integer> getAllTrackNumber() {
        List<Integer> ls = new ArrayList<>();
        for (Track track : generator.tracks) {
            ls.add(track.size());
        }
        return ls;
    }

    @GetMapping("/number/{trackId}")
    int getTrackNumber(@PathVariable String trackId) {
        return generator.findTrack(trackId).size();
    }

    @GetMapping("/events")
    List<EventItem> getEventsForm() {
        EventForm eventForm = new EventForm(generator);
        return eventForm.getEventItemList();
    }

    @PostMapping("/command")
    String postCommand(@RequestBody String line) {
        generator.readInformation(line, 0);
        return "success";
    }

    @PostMapping("/lp")
    String saveFile(@RequestBody Map<String, String> map) {
        try {
            File file = new File(map.get("name"));
            generator.saveFile(file);
        } catch (Exception e) {
            return e.getMessage();
        }
        return "success";
    }

    @GetMapping("/simulation")
    Map getSimulation() {
        SimulationMapper simulationMapper = new SimulationMapper(generator);
        Map map = new HashMap();
        map.put("columns", simulationMapper.getNameAndEvents());
        map.put("data", simulationMapper.getSimulations());
        return map;
    }

    @PutMapping("/simulation/delete/{id}")
    String deleteSimulation(@PathVariable String id) {
        generator.removeSimulation(id);
        return "sucess";
    }

    @DeleteMapping("/simulation")
    String deleteSimulation(@RequestBody Map<String, String> data) {
        generator.removeSimulation(data.get("name"));
        return "sucess";
    }

    @PostMapping("/simulation")
    String addSimulation(@RequestBody Simulation simulation) {
        Simulation sim = generator.findSimulation(simulation.getName());
        if (sim == null) {
            generator.simulations.add(simulation);
            System.out.println(simulation.getActions());
        } else {
            sim.update(simulation);
        }
        return "sucess";
    }

    @PostMapping("/updateAction")
    String updateAction() {
        generator.save("trolley1act.lp");
        return "sucess";
    }

    @PostMapping("/trace")
    List<ClingoSimulation> updateTrace(@RequestBody Map<String, String> data) {
        String filename = data.get("name");
        ClingoTrace clingoTrace = new ClingoTrace(filename, generator);
        return clingoTrace.getClingoSimulations();
    }

    @GetMapping("/original")
    String getOriginal() {
        return generator.train.getOriginPosition().getName();
    }

    @PostMapping("/addCarriage")
    String addCarriage(@RequestBody Map<String, String> map) {
        String trackName = ClingoCausal.findCompleteCommande(map.get("name"), '(').get(0);
        Track track = generator.findTrack(trackName);
        track.addNewCarriage();
        return "sucess";
    }

    @PostMapping("/addTrack")
    String addTrack(@RequestBody Map<String, Object> map) {
        Track track = new Track((String) map.get("name"), (int) map.get("length"));
        generator.tracks.add(track);
        return "sucess";
    }

    @PostMapping("/deleteTrack")
    String deleteTrack(@RequestBody Map<String, String> map){
        String trackName = ClingoCausal.findCompleteCommande(map.get("name"), '(').get(0);
        Track track = generator.findTrack(trackName);
        if (track!=null){
            track.delete();
            generator.tracks.remove(track);
        }
        return "success";
    }
}
