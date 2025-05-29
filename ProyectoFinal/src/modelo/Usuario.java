package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Usuario implements Serializable {
    private List<Skin> inventario;

    public Usuario() {
        inventario = new ArrayList<>();
    }

    public void agregarSkin(Skin skin) {
        inventario.add(skin);
    }

    public List<Skin> getInventario() {
        return inventario;
    }
}
