package parque;

import java.io.IOException;
import java.util.List;

import dao.UsuarioDAOImpl;
import model.Usuario;

public class Main {
    public static void main(String[] args) throws IOException {
        Boleteria boleteria = new Boleteria();

        List<Usuario> usuarios = UsuarioDAOImpl.getInstance().findAll();

        System.out.println(DibujadorDeHomero.saludo());

        for (Usuario usuario : usuarios) {
            boleteria.ofrecerA(usuario);
        }

        System.out.println(DibujadorDeHomero.dibujarHomero());
    }
}