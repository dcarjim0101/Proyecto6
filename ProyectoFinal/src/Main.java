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
        caja.agregarSkin(new Skin("Desert Eagle | Blaze", "Legendaria", "blaze.png"));
        caja.agregarSkin(new Skin("AK-47 | Redline", "Rara", "redline.png"));
        caja.agregarSkin(new Skin("M4A1-S | Guardian", "Rara", "guardian.png"));
        caja.agregarSkin(new Skin("P250 | Mehndi", "Común", "mehndi.png"));

        // Cargar usuario guardado o nuevo
        Usuario usuario;
        try {
            usuario = GestorPersistencia.cargarUsuario();
        } catch (IOException | ClassNotFoundException e) {
            usuario = new Usuario();
        }

        //Se crea simulador para gestionar la logica, conectar con la interfaz grafica controlando las acciones del usuario
        Simulador simulador = new Simulador(caja, usuario);

        //Correcta creacion de la gui ademas de crear instancia
        SwingUtilities.invokeLater(() -> {
            InterfazGrafica gui = new InterfazGrafica(simulador);
            gui.cargarImagenCaja("caja.png");
        });
    }
}
