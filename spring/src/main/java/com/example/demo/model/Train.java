package com.example.demo.model;



/*
*La classe represente le train
* */
public class Train implements ComponentTrolley {
    // Quand le train heurte une personne, il s'arrête.
    public static final boolean STOPAFTERCOLLISION = true;
    // la position originale
    public Section             originPosition;
    //position actuelle
    private Section            present;
    public Train(Section originPosition) {
        this.originPosition = originPosition;
        present             = originPosition;
    }
    public Train() {}

    @Override
    /*Affiche la position du train*/
    public String toString() {
        if (present == null) {
            return "Train: null";
        }
        return "Train: " + present.getName();
    }

    /*Avancer d'un etap*/
    public int run() {
        if (present == null) {
            return -1;
        }
        if (present.getSwitch() != null) {
            System.out.println("Le train s'est deposition de la track " + present.getTrack() +
                               " a la track " + present.getSwitch().next().getTrack());
            present = present.getSwitch().next(present);
        } else {
            present = present.next();
        }
        if (present != null) {
            System.out.println("Le train roule sur " + present.getName());
            if (present.getGroup() != null || present.getBridge() != null) {
                if (present.getGroup() != null) {
                    present.getGroup().roule();
                    return -1;
                } else if (present.getBridge() != null && present.getBridge().getGroup() != null &&
                           present.getBridge().getPush() == true) {
                    System.out.println(present.getBridge().getGroup());
                    present.getBridge().getGroup().roule();
                    return -1;
                }
            }
        } else {
            System.out.println("Pas de sortie du train\n");
        }
        return 0;
    }

    /*retourn la position actuelle*/
    public Section getPresent() { return this.present; }
    /*retourn la position originale*/
    public Section getOriginPosition() { return this.originPosition; }
    /*Définir la position*/
    public void     setPresent(Section present) { this.present = present; }
    /*Définir la position originale*/
    public void     setOriginPosition(Section originPosition) {
        this.originPosition = originPosition;
        this.present        = originPosition;
    }
    /*retourn le train*/
    public String getName() { return "train"; }

    /*Affiche les infomation de la position actuelle de train*/
    public String getInformation() { return "Train," + present.getInformation(); }
    public void   simulation() {
        while (present != null)
            run();
    }
}
