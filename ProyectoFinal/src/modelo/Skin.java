package modelo;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class Skin implements Serializable {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

private String nombre;
private String rareza;
private String rutaImagen;

// Constructor vacío requerido por JPA
public Skin() {}

public Skin(String nombre, String rareza, String rutaImagen) {
this.nombre = nombre;
this.rareza = rareza;
this.rutaImagen = rutaImagen;
}

// Getters y toString igual
}