package com.example.demo.controller;

import com.example.demo.mapper.SimulationMapper;
import com.example.demo.model.*;
import com.example.demo.model.clingo.ClingoCausal;
import com.example.demo.model.clingo.ClingoSimulation;
import com.example.demo.model.clingo.ClingoTrace;
import com.example.demo.model.menu.EventForm;
import com.example.demo.model.menu.EventItem;
import com.example.demo.model.menu.InfoSection;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*Le classe CarriageController echange d'informations les données du trolley avec le front-end. */
@RestController
@RequestMapping("/section")
public class SectionController {

    @Resource
    Generator generator;

    @GetMapping("/{id}")
    InfoSection getSectionById(@PathVariable("id") String id) {
        Section section = (Section) generator.readPosition(id);
        Boolean isOriginal = generator.train.getOriginPosition().equals(section);
        InfoSection infoSection = new InfoSection(section, isOriginal);
        return infoSection;
    }

    @PutMapping
    String updateSectionById(@RequestBody InfoSection infoSection) {
        Section section = (Section) generator.readPosition(infoSection.getId());
        infoSection.updateSection(section, generator);
        return "success";
    }

    @GetMapping("/test")
    String getSection() {
        return "section";
    }

    @PostMapping("/add/{trackId}")
    void addSection(@PathVariable String trackId) {
        generator.findTrack(trackId).addNewSection();
    }

    @PostMapping("/reset")
    void resetGenerator() {
        generator.read("./data/testsw");
    }


    @PostMapping("/upload")
    public void handleFileUpload(@RequestPart(value = "file") final MultipartFile uploadfile) throws IOException {
        saveUploadedFiles(uploadfile);
    }

    private String saveUploadedFiles(final MultipartFile file) throws IOException {
        final byte[] bytes = file.getBytes();
        final Path path = Paths.get("./" + file.getOriginalFilename());
        Files.write(path, bytes);
        generator.resetGenerator();
        generator.read("./" + file.getOriginalFilename());
        return "success";
    }

    @PostMapping("/switch")
    void addSwitch(@RequestBody String[] sectionIds) {
        Section section1 = (Section) generator.readPosition(sectionIds[0]);
        Section section2 = (Section) generator.readPosition(sectionIds[1]);
        Switch sw = new Switch(section1, section2);
        generator.switchs.add(sw);
    }

    @PostMapping("/bridge")
    void addBridge(@RequestBody String[] sectionId_BridgeId) {
        Section section = (Section) generator.readPosition(sectionId_BridgeId[0]);
        Bridge bridge = new Bridge(sectionId_BridgeId[1], section);
        generator.bridges.add(bridge);
    }

    @PostMapping("/group")
    void addGroup(@RequestBody String[] update) {
        Section section = (Section) generator.readPosition(update[0]);
        int number = Integer.parseInt(update[2]);
        String name = update[1];
        Group group = new Group(number, section, name);
        generator.groups.add(group);
    }

    @GetMapping("/{id}/switch")
    List<String> getSwitch(@PathVariable String id) {
        Section section = (Section) generator.readPosition(id);
        List<String> ls = new ArrayList<>();
        ls.add(section.getSwitch().getOtherSection(section).toString());
        ls.add(section.toString());
        return ls;
    }

    @GetMapping("/{id}/bridge/name")
    String getBridgeName(@PathVariable String id) {
        Section section = (Section) generator.readPosition(id);
        List<String> ls = new ArrayList<>();
        Bridge b = section.getBridge();
        return b.getName();
    }

    @GetMapping("/{id}/group")
    List<String> getGroupInfo(@PathVariable String id) {
        Section section = (Section) generator.readPosition(id);
        List<String> ls = new ArrayList<>();
        Group g = section.getGroup();
        ls.add(g.getName());
        ls.add(Integer.toString(g.getNb()));
        return ls;
    }

    @DeleteMapping("/{sectionId}")
    String deleteSectionById(@PathVariable("sectionId") String sectionId) {
        Section section = (Section) generator.readPosition(sectionId);
        if (section == null) {
            return "fail";
        }
        Track track = generator.findTrack(section.getTrack());
        track.remove(section);
        return "sucess";
    }

    @DeleteMapping("/switch/{sectionId}")
    String deleteSwitchById(@PathVariable("sectionId") String sectionId) {
        Section section = (Section) generator.readPosition(sectionId);
        section.deleteSwitch();
        return "sucess";
    }

    @DeleteMapping("/group/{sectionId}")
    String deleteGroupById(@PathVariable("sectionId") String sectionId) {
        Section section = (Section) generator.readPosition(sectionId);
        section.deleteGroup();
        return "sucess";
    }

    @DeleteMapping("/bridge/{sectionId}")
    String deleteBridgeById(@PathVariable("sectionId") String sectionId) {
        Section section = (Section) generator.readPosition(sectionId);
        section.deleteBridge();
        return "sucess";
    }

    @PostMapping("/{id}")
    String setOriginalPosition(@PathVariable String id) {
        Section section = (Section) generator.readPosition(id);
        generator.train.setOriginPosition(section);
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
    String postCommand(@RequestBody Map<String, String> map) {
        generator.readInformation(map.get("content"), 0);
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
            simulation.normalize();
            generator.simulations.add(simulation);
            System.out.println(simulation.getActions());
        } else {
            sim.update(simulation);
        }
        return "sucess";
    }

    @PostMapping("/updateAction")
    String updateAction() {
        generator.save("./trolley1act.lp");
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

    @PostMapping("/addSection")
    String addSection(@RequestBody Map<String, String> map) {
        String trackName = ClingoCausal.findCompleteCommande(map.get("name"), '(').get(0);
        Track track = generator.findTrack(trackName);
        track.addNewSection();
        return "sucess";
    }

    @PostMapping("/addTrack")
    String addTrack(@RequestBody Map<String, Object> map) {
        Track track = new Track((String) map.get("name"), (int) map.get("length"));
        generator.tracks.add(track);
        return "sucess";
    }

    @PostMapping("/deleteTrack")
    String deleteTrack(@RequestBody Map<String, String> map) {
        String trackName = ClingoCausal.findCompleteCommande(map.get("name"), '(').get(0);
        Track track = generator.findTrack(trackName);
        if (track != null) {
            track.delete();
            generator.tracks.remove(track);
        }
        return "success";
    }
}
