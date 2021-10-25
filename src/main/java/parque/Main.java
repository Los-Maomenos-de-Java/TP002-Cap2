package parque;

import java.util.ArrayList;
import java.util.List;
import dao.DAOFactory;
import model.Usuario;

public class Main {
	
	private static List<Usuario> usuarios = new ArrayList<>();
	
    public static void main(String[] args) {
    	
    	usuarios.addAll(DAOFactory.getUsuarioDAO().findAll());
        Boleteria b = new Boleteria();
    }
}