
package com.example.appengine.java8.repository;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ATTESAMEDIA {

    @SerializedName("BIANCO")
    @Expose
    private int bIANCO;
    @SerializedName("GIALLO")
    @Expose
    private int gIALLO;
    @SerializedName("ROSSO")
    @Expose
    private int rOSSO;
    @SerializedName("VERDE")
    @Expose
    private int vERDE;

    public int getBIANCO() {
        return bIANCO;
    }

    public void setBIANCO(int bIANCO) {
        this.bIANCO = bIANCO;
    }

    public ATTESAMEDIA withBIANCO(int bIANCO) {
        this.bIANCO = bIANCO;
        return this;
    }

    public int getGIALLO() {
        return gIALLO;
    }

    public void setGIALLO(int gIALLO) {
        this.gIALLO = gIALLO;
    }

    public ATTESAMEDIA withGIALLO(int gIALLO) {
        this.gIALLO = gIALLO;
        return this;
    }

    public int getROSSO() {
        return rOSSO;
    }

    public void setROSSO(int rOSSO) {
        this.rOSSO = rOSSO;
    }

    public ATTESAMEDIA withROSSO(int rOSSO) {
        this.rOSSO = rOSSO;
        return this;
    }

    public int getVERDE() {
        return vERDE;
    }

    public void setVERDE(int vERDE) {
        this.vERDE = vERDE;
    }

    public ATTESAMEDIA withVERDE(int vERDE) {
        this.vERDE = vERDE;
        return this;
    }

}
