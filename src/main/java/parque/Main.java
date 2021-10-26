package parque;

import java.io.IOException;
import java.util.List;
import dao.DAOFactory;
import model.Usuario;

public class Main {
    public static void main(String[] args) throws IOException {
        List<Usuario> usuarios = DAOFactory.getUsuarioDAO().findAll();

        System.out.println(DibujadorDeHomero.saludo());
        Boleteria boleteria = new Boleteria();

        for(Usuario usuario:usuarios) {
            boleteria.ofrecerA(usuario);
        }

        System.out.println(DibujadorDeHomero.dibujarHomero());
    }
}