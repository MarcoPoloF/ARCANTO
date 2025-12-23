package com.example.Finanzas.Inteligentes.modelo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name= "usuario")
public class Usuario implements Serializable {
    @Id
    @Column(name="id_usuario")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idusuario;
    @Column(name="nombre_c")
    private String nombre_c;
    @Column(name = "correo", unique = true)
    private String correo;
    @Column(name="contrasena")
    private String contrasena;
    @Column(name="nocifrado")
    private String nocifrado;
    @Column(name = "estado")
    private boolean estado;
    @Column(name="fecha_registro")
    private Date fecha_registro;
    @Column (name = "fecha_edicion")
    private Date fecha_edicion;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(name = "usuario_rol", joinColumns = @JoinColumn(name = "id_usuario"), inverseJoinColumns = @JoinColumn(name = "id_rol"))
    private List<Rol> rolList;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Cliente cliente;

    public Usuario() {
    }

    public Date getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(Date fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    public Date getFecha_edicion() {
        return fecha_edicion;
    }

    public void setFecha_edicion(Date fecha_edicion) {
        this.fecha_edicion = fecha_edicion;
    }

    public String getNocifrado() {
        return nocifrado;
    }

    public void setNocifrado(String nocifrado) {
        this.nocifrado = nocifrado;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public List<Rol> getRolList() {
        return rolList;
    }

    public void setRolList(List<Rol> rolList) {
        this.rolList = rolList;
    }

    public Long getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(Long idusuario) {
        this.idusuario = idusuario;
    }

    public String getNombre_c() {
        return nombre_c;
    }

    public void setNombre_c(String nombre_c) {
        this.nombre_c = nombre_c;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
