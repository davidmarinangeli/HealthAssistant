
package com.example.appengine.java8.repository;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class STATOPS {

    @SerializedName("DATA_AGGIORNAMENTO")
    @Expose
    private String dATAAGGIORNAMENTO;
    @SerializedName("PRONTO_SOCCORSO")
    @Expose
    private List<PRONTOSOCCORSO> pRONTOSOCCORSO = null;

    public String getDATAAGGIORNAMENTO() {
        return dATAAGGIORNAMENTO;
    }

    public void setDATAAGGIORNAMENTO(String dATAAGGIORNAMENTO) {
        this.dATAAGGIORNAMENTO = dATAAGGIORNAMENTO;
    }

    public STATOPS withDATAAGGIORNAMENTO(String dATAAGGIORNAMENTO) {
        this.dATAAGGIORNAMENTO = dATAAGGIORNAMENTO;
        return this;
    }

    public List<PRONTOSOCCORSO> getPRONTOSOCCORSO() {
        return pRONTOSOCCORSO;
    }

    public void setPRONTOSOCCORSO(List<PRONTOSOCCORSO> pRONTOSOCCORSO) {
        this.pRONTOSOCCORSO = pRONTOSOCCORSO;
    }

    public STATOPS withPRONTOSOCCORSO(List<PRONTOSOCCORSO> pRONTOSOCCORSO) {
        this.pRONTOSOCCORSO = pRONTOSOCCORSO;
        return this;
    }

}
