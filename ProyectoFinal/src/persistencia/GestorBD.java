package persistencia;

import jakarta.persistence.*;
import modelo.Skin;

public class GestorBD {

private static final EntityManagerFactory emf =
Persistence.createEntityManagerFactory("ProyectoFinalPU");

public static void guardarSkin(Skin skin) {
EntityManager em = emf.createEntityManager();
em.getTransaction().begin();
em.persist(skin);
em.getTransaction().commit();
em.close();
}
}