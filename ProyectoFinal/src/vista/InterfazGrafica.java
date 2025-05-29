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

        cargarImagenCaja("caja.png"); // <-- CAMBIAR NOMBRE DE LA IMAGEN AQU�? SI ES NECESARIO
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
                    cargarImagenCaja("caja.png"); // <-- CAMBIAR NOMBRE DE LA IMAGEN AQU�? SI ES NECESARIO
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
            System.err.println("No se encontró la imagen: " + nombreArchivo);
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
            System.err.println("No se encontró la imagen de la skin: " + ruta);
            return null;
        }
    }
}

/*
Esta clase es la interfaz gr�fica del simulador,
 basada en JFrame de Swing, que es la biblioteca
 est�ndar para GUIs en Java.

Recibe un objeto Simulador para conectar
 la l�gica con la presentaci�n.

En el constructor, inicializa la interfaz
 con initGUI() y a�ade un listener para
 detectar cuando la ventana se cierra;
 ah� guarda el estado del usuario llamando
 a GestorPersistencia.guardarUsuario(), 
para que los datos no se pierdan.

En initGUI() configuramos el dise�o:
 tenemos una etiqueta para mostrar la
 imagen de la caja (cajaLabel), otra 
para la imagen de la skin (skinImagenLabel)
 y un texto para mostrar mensajes (skinTextoLabel).

Tambi�n tenemos un bot�n que al 
pulsarlo ejecuta el m�todo abrirCaja(),
 que es el n�cleo de la acci�n del usuario.

El m�todo abrirCaja() desactiva el
 bot�n para evitar acciones repetidas
 mientras dura la animaci�n de �abrir caja�,
 que se simula con un Timer que actualiza
 un texto en la pantalla cada 200 ms con
 puntos suspensivos para dar sensaci�n de carga.

Cuando termina la animaci�n, obtiene
 una skin aleatoria del Simulador, la
 muestra y luego tras 2 segundos reinicia
 la interfaz para que se pueda abrir otra 
caja, reactivando el bot�n y mostrando
 la imagen inicial de la caja.

La carga y escalado de im�genes se hace
 con el m�todo cargarImagenDesdeRecurso(),
 que busca las im�genes dentro de los
 recursos del proyecto y las adapta al
 tama�o requerido, manteniendo la
 proporci�n para que no se deformen.

En resumen, esta clase controla toda
 la interacci�n visual y la experiencia
 del usuario para abrir cajas y ver las skins que obtiene.
*/