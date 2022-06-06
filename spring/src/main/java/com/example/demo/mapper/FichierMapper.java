package com.example.demo.mapper;

import com.example.demo.model.menu.Fichierclingo;
import com.example.demo.model.menu.Node;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FichierMapper {
    private String fconf;
    private List<Fichierclingo> Fichiers;

    public FichierMapper(){
        fconf="newtrolley.conf";
        Fichiers= new ArrayList<>();
    }

    public String getFconf(){return fconf;}

    public List<Fichierclingo> findAllfichier() {
        return Fichiers;
    }

    public void addFichier(Fichierclingo f) {Fichiers.add(f);
    }

    public void creatfichierconf(){
        FileWriter writer;
        try {
            File file = new File("D:/IN013/ethics-platform/spring/data/"+fconf);		//文件路径（路径+文件名）
            if (!file.exists()) {	//文件不存在则创建文件，先创建目录
                File dir = new File(file.getParent());
                dir.mkdirs();
                file.createNewFile();
            }
            writer = new FileWriter("E:/token.txt");
            writer.write("[clingo]\nclingo\n[action theory]\n");
            writer.write(Fichiers.get(0).getNom()+"\n[context]\n"+Fichiers.get(1).getNom()+"\n[causal theory]\n"+Fichiers.get(2).getNom()
            +"\n[ethical spec]\n"+Fichiers.get(3).getNom()+"\n[ethical theories]\n"+Fichiers.get(4).getNom()+"\n[trace]\n"+Fichiers.get(5).getNom()
            +"\n[causal trace]\n"+Fichiers.get(6).getNom()+"\n[results]\nimp/2\nper/2\n[output]\n./data/newresultTroll.lp\n");
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
