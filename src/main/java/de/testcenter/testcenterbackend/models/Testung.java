package de.testcenter.testcenterbackend.models;

public class Testung {
    private String ergebnis;
    private String testername;
    private String datum;
    private String uhrzeit;

    public Testung(String ergebnis, String testername, String datum, String uhrzeit) {
        this.ergebnis = ergebnis;
        this.testername = testername;
        this.datum = datum;
        this.uhrzeit = uhrzeit;
    }

    public String getErgebnis() {
        return ergebnis;
    }

    public String getTestername() {
        return testername;
    }

    public String getDatum() {
        return datum;
    }

    public String getUhrzeit() {
        return uhrzeit;
    }

    public void setErgebnis(String ergebnis) {
        this.ergebnis = ergebnis;
    }

    public void setTestername(String testername) {
        this.testername = testername;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public void setUhrzeit(String uhrzeit) {
        this.uhrzeit = uhrzeit;
    }
}
