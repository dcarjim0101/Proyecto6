package persistencia;

import modelo.Usuario;

import java.io.*;

public class GestorPersistencia {

    private static final String RUTA_ARCHIVO = "usuario.dat";

    public static void guardarUsuario(Usuario usuario) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(RUTA_ARCHIVO))) {
            oos.writeObject(usuario);
        }
    }

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
