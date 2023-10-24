package com.nnk.springboot.modeles;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jdk.jfr.DataAmount;

@Entity
@Table(name = "Patients")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", updatable = false)
    private Integer id;

    @NotBlank(message="nom est obligatoire")
    @Column(name = "nom", nullable = false)
    private String nom;

    @NotBlank(message="le prenom est obligatoire")
    @Column(name = "prenom", nullable = false)
    private String prenom;

    @NotBlank(message = "la date de naissance est obligatoire")
    @Column(name = "date_de_naissance", nullable = false)
    private String dateDeNaissance;

    @NotBlank(message = "le genre est obligatoire")
    @Column(name = "genre", nullable = false)
    private String genre;

    @NotBlank(message = "adresse postale est obligatoire")
    @Column(name = "addresse_postale", nullable = false)
    private String adressePostale;

    @NotBlank(message = "numero de telephone est obligatoire")
    @Column(name = "numero_de_telephone", nullable = false)
    private String numeroDeTelephone;

    public Patient() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getDateDeNaissance() {
        return dateDeNaissance;
    }

    public void setDateDeNaissance(String dateDeNaissance) {
        this.dateDeNaissance = dateDeNaissance;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getAdressePostale() {
        return adressePostale;
    }

    public void setAdressePostale(String adressePostale) {
        this.adressePostale = adressePostale;
    }

    public String getNumeroDeTelephone() {
        return numeroDeTelephone;
    }

    public void setNumeroDeTelephone(String numeroDeTelephone) {
        this.numeroDeTelephone = numeroDeTelephone;
    }
}
