package vista;

import modelo.Skin;
import logica.Simulador;
import persistencia.GestorPersistencia;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class InterfazGrafica extends JFrame {

    private Simulador simulador;
    private JLabel cajaLabel;
    private JLabel skinImagenLabel;
    private JLabel skinTextoLabel;
    private JButton abrirCajaButton;

    private Timer timer;
    private int contadorAnimacion = 0;
    private final int duracionAnimacion = 10;

    public InterfazGrafica(Simulador simulador) {
        this.simulador = simulador;
        initGUI();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    GestorPersistencia.guardarUsuario(simulador.getUsuario());
                    System.out.println("Inventario guardado.");
                } catch (IOException ex) {
                    System.err.println("Error guardando inventario: " + ex.getMessage());
                }
            }
        });
    }

    private void initGUI() {
        setTitle("Simulador Apertura de Cajas");
        setSize(450, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        cajaLabel = new JLabel();
        cajaLabel.setHorizontalAlignment(JLabel.CENTER);

        JPanel skinPanel = new JPanel(new BorderLayout());

        skinImagenLabel = new JLabel();
        skinImagenLabel.setHorizontalAlignment(JLabel.CENTER);
        skinImagenLabel.setPreferredSize(new Dimension(200, 200));

        skinTextoLabel = new JLabel("Abre una caja para obtener una skin");
        skinTextoLabel.setHorizontalAlignment(JLabel.CENTER);
        skinTextoLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        skinPanel.add(skinImagenLabel, BorderLayout.CENTER);
        skinPanel.add(skinTextoLabel, BorderLayout.SOUTH);

        abrirCajaButton = new JButton("Abrir Caja");
        abrirCajaButton.addActionListener(e -> abrirCaja());

        add(cajaLabel, BorderLayout.NORTH);
        add(skinPanel, BorderLayout.CENTER);
        add(abrirCajaButton, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);

        cargarImagenCaja("caja.png");
    }

    private void abrirCaja() {
        abrirCajaButton.setEnabled(false);
        contadorAnimacion = 0;
        skinImagenLabel.setIcon(null);
        cajaLabel.setIcon(null); // Ocultar caja al abrir
        skinTextoLabel.setText("Abriendo caja");

        timer = new Timer(200, e -> {
            if (contadorAnimacion < duracionAnimacion) {
                String puntos = ".".repeat(contadorAnimacion % 4);
                skinTextoLabel.setText("Abriendo caja" + puntos);
                contadorAnimacion++;
            } else {
                timer.stop();
                Skin skin = simulador.abrirCaja();
                skinTextoLabel.setText("¡Obtuviste: " + skin + "!");
                ImageIcon iconSkin = cargarImagenDesdeRecurso(skin.getRutaImagen(), 200, 200);
                if (iconSkin != null) {
                    skinImagenLabel.setIcon(iconSkin);
                }

                // Volver a mostrar la caja tras un tiempo
                Timer delay = new Timer(2000, ev -> {
                    skinTextoLabel.setText("Abre una caja para obtener una skin");
                    skinImagenLabel.setIcon(null);
                    cargarImagenCaja("caja.png");
                    abrirCajaButton.setEnabled(true);
                });
                delay.setRepeats(false);
                delay.start();
            }
        });
        timer.start();
    }

    public void cargarImagenCaja(String nombreArchivo) {
        ImageIcon icon = cargarImagenDesdeRecurso("imagenes/" + nombreArchivo, 300, 200);
        if (icon != null) {
            cajaLabel.setIcon(icon);
        } else {
            System.err.println("No se encuentra la imagen: " + nombreArchivo);
        }
    }

    private ImageIcon cargarImagenDesdeRecurso(String ruta, int maxWidth, int maxHeight) {
        java.net.URL imgURL = getClass().getResource("/" + ruta);
        if (imgURL != null) {
            ImageIcon icon = new ImageIcon(imgURL);
            Image img = icon.getImage();
            int anchoOriginal = icon.getIconWidth();
            int altoOriginal = icon.getIconHeight();

            double escalaWidth = (double) maxWidth / anchoOriginal;
            double escalaHeight = (double) maxHeight / altoOriginal;
            double escala = Math.min(escalaWidth, escalaHeight);

            int nuevoAncho = (int) (anchoOriginal * escala);
            int nuevoAlto = (int) (altoOriginal * escala);

            Image imgEscalada = img.getScaledInstance(nuevoAncho, nuevoAlto, Image.SCALE_SMOOTH);
            return new ImageIcon(imgEscalada);
        } else {
            System.err.println("No se encuentra la imagen de la skin: " + ruta);
            return null;
        }
    }
}
