import modelo.Caja;
import modelo.Skin;
import modelo.Usuario;
import logica.Simulador;
import persistencia.GestorBD;
import vista.InterfazGrafica;

public class Main {
public static void main(String[] args) {
// Creamos una caja
Caja caja = new Caja();

// Creamos algunas skins
Skin s1 = new Skin("Espada de Fuego", "Legendaria", "imagenes/espada_fuego.png");
Skin s2 = new Skin("Arco del Viento", "Rara", "imagenes/arco_viento.png");
Skin s3 = new Skin("Hacha de Hielo", "Común", "imagenes/hacha_hielo.png");

// Las agregamos a la caja
caja.agregarSkin(s1);
caja.agregarSkin(s2);
caja.agregarSkin(s3);

// También las guardamos en la base de datos
GestorBD.guardarSkin(s1);
GestorBD.guardarSkin(s2);
GestorBD.guardarSkin(s3);

// Creamos un usuario y el simulador
Usuario usuario = new Usuario();
Simulador simulador = new Simulador(caja, usuario);

// Lanzamos la interfaz gráfica
new InterfazGrafica(simulador);
}
}