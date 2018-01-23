package com.example.timotej.whatdo;

/**
 * Created by Timotej on 13.1.2018.
 */

public class ActionForWeka {
    public int oddaljenost;
    public int trajanje;
    public int danVTednu;
    public int cas;
    public int tag;
    public int cenaVstopnice;

    public ActionForWeka(){};
    public ActionForWeka(int o,int t,int d,int cas, int tag,int cena){
        oddaljenost = o;
        trajanje = t;
        danVTednu = d;
        this.cas = cas;
        this.tag = tag;
        cenaVstopnice = cena;
    };

}