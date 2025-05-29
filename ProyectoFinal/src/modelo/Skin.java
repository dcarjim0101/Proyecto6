package modelo;

import java.io.Serializable;

public class Skin implements Serializable {
    private String nombre;
    private String rareza;  // Por ejemplo: Común, Rara, Legendaria
    private String rutaImagen; // Ruta a la imagen para mostrar

    public Skin(String nombre, String rareza, String rutaImagen) {
        this.nombre = nombre;
        this.rareza = rareza;
        this.rutaImagen = rutaImagen;
    }

    public String getNombre() {
        return nombre;
    }

    public String getRareza() {
        return rareza;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    @Override
    public String toString() {
        return rareza + " - " + nombre;
    }
}
