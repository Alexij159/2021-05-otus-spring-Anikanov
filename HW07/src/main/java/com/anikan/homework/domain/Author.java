package com.anikan.homework.domain;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fullname")
    private String fullName;
    @Column(name = "shortname")
    private String shortName;
    @Column(name = "birthdate")
    private LocalDate birthDate;

    public Author() {
    }

    public Author(String fullName, String shortName, LocalDate birthDate) {
        this.fullName = fullName;
        this.shortName = shortName;
        this.birthDate = birthDate;
    }

    public Author(Long id, String fullName, String shortName, LocalDate birthDate) {
        this.id = id;
        this.fullName = fullName;
        this.shortName = shortName;
        this.birthDate = birthDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return "Id: " + id.toString()
                + ", fullName: " + fullName
                + ", shortName: " + shortName
                + ", birthDate: " + birthDate;
    }
}
