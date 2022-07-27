package com.example.demo.Entidades;

import com.example.demo.Enumerations.EnumRol;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;


@Data
@Entity
public class Usuario {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String nombre;
    private String apellido;
    
    @Column(unique=true)
    private String email;
    private String contrasenia;
    private Boolean alta;
    
//    EL USUARIO PUEDE TENER EL ROL DE ADMINISTRADOR O SER UN USUARIO SIMPLE.
    @Enumerated(EnumType.STRING)
    private EnumRol rol;
    
    @OneToOne
    private Foto foto;
}