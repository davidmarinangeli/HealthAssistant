
package com.example.appengine.java8.repository;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PRONTOSOCCORSO {

    @SerializedName("AMBULATORIO")
    @Expose
    private AMBULATORIO aMBULATORIO;
    @SerializedName("ATTESA")
    @Expose
    private ATTESA aTTESA;
    @SerializedName("ATTESA-MEDIA")
    @Expose
    private ATTESAMEDIA aTTESAMEDIA;
    @SerializedName("COD_OSP_OD")
    @Expose
    private String cODOSPOD;
    @SerializedName("COD_PS_OD")
    @Expose
    private String cODPSOD;
    @SerializedName("LOCALITA")
    @Expose
    private String lOCALITA;
    @SerializedName("OSSERVAZIONE")
    @Expose
    private OSSERVAZIONE oSSERVAZIONE;
    @SerializedName("PS")
    @Expose
    private String pS;
    @SerializedName("UNITA_OPERATIVA")
    @Expose
    private String uNITAOPERATIVA;
    @SerializedName("MESSAGGIO")
    @Expose
    private String mESSAGGIO;

    public AMBULATORIO getAMBULATORIO() {
        return aMBULATORIO;
    }

    public void setAMBULATORIO(AMBULATORIO aMBULATORIO) {
        this.aMBULATORIO = aMBULATORIO;
    }

    public PRONTOSOCCORSO withAMBULATORIO(AMBULATORIO aMBULATORIO) {
        this.aMBULATORIO = aMBULATORIO;
        return this;
    }

    public ATTESA getATTESA() {
        return aTTESA;
    }

    public void setATTESA(ATTESA aTTESA) {
        this.aTTESA = aTTESA;
    }

    public PRONTOSOCCORSO withATTESA(ATTESA aTTESA) {
        this.aTTESA = aTTESA;
        return this;
    }

    public ATTESAMEDIA getATTESAMEDIA() {
        return aTTESAMEDIA;
    }

    public void setATTESAMEDIA(ATTESAMEDIA aTTESAMEDIA) {
        this.aTTESAMEDIA = aTTESAMEDIA;
    }

    public PRONTOSOCCORSO withATTESAMEDIA(ATTESAMEDIA aTTESAMEDIA) {
        this.aTTESAMEDIA = aTTESAMEDIA;
        return this;
    }

    public String getCODOSPOD() {
        return cODOSPOD;
    }

    public void setCODOSPOD(String cODOSPOD) {
        this.cODOSPOD = cODOSPOD;
    }

    public PRONTOSOCCORSO withCODOSPOD(String cODOSPOD) {
        this.cODOSPOD = cODOSPOD;
        return this;
    }

    public String getCODPSOD() {
        return cODPSOD;
    }

    public void setCODPSOD(String cODPSOD) {
        this.cODPSOD = cODPSOD;
    }

    public PRONTOSOCCORSO withCODPSOD(String cODPSOD) {
        this.cODPSOD = cODPSOD;
        return this;
    }

    public String getLOCALITA() {
        return lOCALITA;
    }

    public void setLOCALITA(String lOCALITA) {
        this.lOCALITA = lOCALITA;
    }

    public PRONTOSOCCORSO withLOCALITA(String lOCALITA) {
        this.lOCALITA = lOCALITA;
        return this;
    }

    public OSSERVAZIONE getOSSERVAZIONE() {
        return oSSERVAZIONE;
    }

    public void setOSSERVAZIONE(OSSERVAZIONE oSSERVAZIONE) {
        this.oSSERVAZIONE = oSSERVAZIONE;
    }

    public PRONTOSOCCORSO withOSSERVAZIONE(OSSERVAZIONE oSSERVAZIONE) {
        this.oSSERVAZIONE = oSSERVAZIONE;
        return this;
    }

    public String getPS() {
        return pS;
    }

    public void setPS(String pS) {
        this.pS = pS;
    }

    public PRONTOSOCCORSO withPS(String pS) {
        this.pS = pS;
        return this;
    }

    public String getUNITAOPERATIVA() {
        return uNITAOPERATIVA;
    }

    public void setUNITAOPERATIVA(String uNITAOPERATIVA) {
        this.uNITAOPERATIVA = uNITAOPERATIVA;
    }

    public PRONTOSOCCORSO withUNITAOPERATIVA(String uNITAOPERATIVA) {
        this.uNITAOPERATIVA = uNITAOPERATIVA;
        return this;
    }

    public String getMESSAGGIO() {
        return mESSAGGIO;
    }

    public void setMESSAGGIO(String mESSAGGIO) {
        this.mESSAGGIO = mESSAGGIO;
    }

    public PRONTOSOCCORSO withMESSAGGIO(String mESSAGGIO) {
        this.mESSAGGIO = mESSAGGIO;
        return this;
    }

}
