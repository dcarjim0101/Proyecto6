package persistencia;

import modelo.Usuario;

import java.io.*;

public class GestorPersistencia {
    //se establece ruta
    private static final String RUTA_ARCHIVO = "usuario.dat";
    //metodo de guardar datos del usuario
    public static void guardarUsuario(Usuario usuario) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(RUTA_ARCHIVO))) {
            oos.writeObject(usuario);
        }
    }

    //comprueba que existe para leer o crear una nueva ademas de las excepciones
    public static Usuario cargarUsuario() throws IOException, ClassNotFoundException {
        File file = new File(RUTA_ARCHIVO);
        if (!file.exists()) {
            return new Usuario(); // Si no existe archivo, devolvemos un usuario nuevo
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(RUTA_ARCHIVO))) {
            return (Usuario) ois.readObject();
        }
    }
}
