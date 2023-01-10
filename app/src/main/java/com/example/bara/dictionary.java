package com.example.bara;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;

public class dictionary {
    private String kalimat;
    private String bahasaFromID = "empty";
    private String bahasaToID = "empty";

    public void setKalimat(String kalimat) {
        this.kalimat = kalimat;
    }

    public void setBahasaFromID(String bahasaFromID) {
        this.bahasaFromID = bahasaFromID;
    }

    public void setBahasaToID(String bahasaToID) {
        this.bahasaToID = bahasaToID;
    }

    public String getBahasaFromID() {
        return bahasaFromID;
    }

    public String getBahasaToID() {
        return bahasaToID;
    }

    public String getBahasaID(String bahasa) {
        String bahasaID = "empty";
        switch (bahasa) {
            case "Indonesia":
                bahasaID = "id";
                break;
            case "Sunda":
                bahasaID = "su";
                break;
            case "Jawa":
                bahasaID = "jw";
                break;
            default:
                bahasaID = "empty";
        }
        return bahasaID;
    }

    public String translate() {
        Python py = Python.getInstance();
        final PyObject pyobj = py.getModule("script");
        PyObject obj = pyobj.callAttr("translate", kalimat, bahasaFromID, bahasaToID);

        return obj.toString();
    }
}
