package logica;

import modelo.Caja;
import modelo.Skin;
import modelo.Usuario;

public class Simulador {
    private Caja caja;
    private Usuario usuario;

    public Simulador(Caja caja, Usuario usuario) {
        this.caja = caja;
        this.usuario = usuario;
    }

    public Skin abrirCaja() {
        Skin skin = caja.abrirCaja();
        usuario.agregarSkin(skin);
        return skin;
    }

    public Usuario getUsuario() {
        return usuario;
    }
}
