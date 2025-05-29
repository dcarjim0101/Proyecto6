import modelo.Caja;
import modelo.Skin;
import modelo.Usuario;
import logica.Simulador;
import persistencia.GestorPersistencia;
import vista.InterfazGrafica;

import javax.swing.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        // Crear la caja y añadir skins
        Caja caja = new Caja();
        caja.agregarSkin(new Skin("Desert Eagle | Blaze", "Legendaria", "blaze.png"));      // --> CAMBIAR NOMBRE IMAGEN SKIN AQUÍ
        caja.agregarSkin(new Skin("AK-47 | Redline", "Rara", "redline.png"));              // --> CAMBIAR NOMBRE IMAGEN SKIN AQUÍ
        caja.agregarSkin(new Skin("M4A1-S | Guardian", "Rara", "guardian.png"));            // --> CAMBIAR NOMBRE IMAGEN SKIN AQUÍ
        caja.agregarSkin(new Skin("P250 | Mehndi", "Común", "mehndi.png"));                 // --> CAMBIAR NOMBRE IMAGEN SKIN AQUÍ

        // Cargar usuario guardado o nuevo
        Usuario usuario;
        try {
            usuario = GestorPersistencia.cargarUsuario();
        } catch (IOException | ClassNotFoundException e) {
            usuario = new Usuario();
        }

        Simulador simulador = new Simulador(caja, usuario);

        SwingUtilities.invokeLater(() -> {
            InterfazGrafica gui = new InterfazGrafica(simulador);
            gui.cargarImagenCaja("caja.png"); // --> CAMBIAR NOMBRE IMAGEN CAJA AQUÍ
        });
    }
}
