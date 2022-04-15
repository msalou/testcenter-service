package de.testcenter.testcenterbackend.models;

public class Zertifikat {
    private Kunde kunde;
    private Testung testung;

    public Zertifikat(Kunde kunde, Testung testung) {
        this.kunde = kunde;
        this.testung = testung;
    }

    public Kunde getKunde() {
        return kunde;
    }

    public Testung getTestung() {
        return testung;
    }

    public void setKunde(Kunde kunde) {
        this.kunde = kunde;
    }

    public void setTestung(Testung testung) {
        this.testung = testung;
    }
}
