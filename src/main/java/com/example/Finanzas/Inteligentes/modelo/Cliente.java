package com.example.Finanzas.Inteligentes.modelo;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Table(name="cliente")
public class Cliente implements Serializable {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="nombre")
    private String nombre;
    @Column(name="descripcion", columnDefinition = "TEXT")
    private String descripcion;
    @Column(name="url", columnDefinition = "TEXT")
    private String url;
    @Column(name = "logo")
    private String logo;
    @Column(name = "colorP")
    private String colorP;
    @Column(name = "colorS")
    private String colorS;

    public String getColorP() {
        return colorP;
    }

    public void setColorP(String colorP) {
        this.colorP = colorP;
    }

    public String getColorS() {
        return colorS;
    }

    public void setColorS(String colorS) {
        this.colorS = colorS;
    }

    public Cliente() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
