package de.testcenter.testcenterbackend.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "kundeHealthy")
public class Kunde {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "nachname")
    private String nachname;
    
    @Column(name = "vorname")
    private String vorname;
    
    @Column(name = "strasse")
    private String strasse;
    
    @Column(name = "plz")
    private String plz;
    
    @Column(name = "ort")
    private String ort;
    
    @Column(name = "geburtsdatum")
    private String geburtsdatum;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "telefon")
    private String telefon;

    public String getNachname() {
        return nachname;
    }

    public String getVorname() {
        return vorname;
    }

    public String getStrasse() {
        return strasse;
    }

    public String getPlz() {
        return plz;
    }

    public String getOrt() {
        return ort;
    }

    public String getGeburtsdatum() {
        return geburtsdatum;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefon() {
        return telefon;
    }

    public Long getId() {
        return id;
    }
    
    public void setNachname(String nachname) {
        this.nachname = nachname;
    }
    
    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public void setStrasse(String strasse) {
        this.strasse = strasse;
    }

    public void setPlz(String plz) {
        this.plz = plz;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public void setGeburtsdatum(String geburtsdatum) {
        this.geburtsdatum = geburtsdatum;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public void setId(Long id) {
        this.id = id;
    }
}