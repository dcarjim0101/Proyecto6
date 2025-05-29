package modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Caja {
    private List<Skin> skins;

    public Caja() {
        skins = new ArrayList<>();
        // Aqui­ se agregaran skins al constructor o mediante metodo
    }

    public void agregarSkin(Skin skin) {
        skins.add(skin);
    }

    public Skin abrirCaja() {
        // Metodo simple que simula abrir la caja y devuelve una skin aleatoria
        Random rand = new Random();
        int index = rand.nextInt(skins.size());
        return skins.get(index);
    }

    public List<Skin> getSkins() {
        return skins;
    }
}
