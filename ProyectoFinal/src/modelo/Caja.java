package modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Caja {
    private List<Skin> skins;

    public Caja() {
        skins = new ArrayList<>();
        // Aquí se agregarán skins al constructor o mediante método
    }

    public void agregarSkin(Skin skin) {
        skins.add(skin);
    }

    public Skin abrirCaja() {
        // Método simple que simula abrir la caja y devuelve una skin aleatoria
        Random rand = new Random();
        int index = rand.nextInt(skins.size());
        return skins.get(index);
    }

    public List<Skin> getSkins() {
        return skins;
    }
}
