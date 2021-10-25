package dao;

import jdbc.ConnectionProvider;
import model.TipoDeAtraccion;
import model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

public class UsuarioDAOImpl implements GenericDAO<Usuario> {

	@Override
	public List<Usuario> findAll() {
		try {
            String sql = "SELECT * FROM usuarios";
            Connection conn = ConnectionProvider.getConnection();

            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet resultados = statement.executeQuery();

            List<Usuario> usuarios = new LinkedList<>();
            while (resultados.next()) {
                usuarios.add(toUsuario(resultados));
            }

            return usuarios;
        } catch (Exception e) {
            throw new MissingDataException(e);
        }
    }

	private Usuario toUsuario(ResultSet resultados) {
		try {
            var id = resultados.getInt(1);
            var nombre = resultados.getString(2);
            var presupuesto = resultados.getDouble(3);
            var tiempo_disponible = resultados.getDouble(4);
            TipoDeAtraccion tipo_atraccion_preferido = getTipoAtraccion(resultados.getInt(5));
            

            return new Usuario(id, nombre, presupuesto, tiempo_disponible, tipo_atraccion_preferido);

        } catch (Exception e) {
            throw new MissingDataException(e);
        }

	}

	private TipoDeAtraccion getTipoAtraccion(int id) {
		   try {
	            String sql = "SELECT nombre FROM tipos_de_atraccion WHERE id = ?";

	            Connection conn = ConnectionProvider.getConnection();

	            PreparedStatement statement = conn.prepareStatement(sql);
	            statement.setInt(1, id);

	            ResultSet resultados = statement.executeQuery();
	            return TipoDeAtraccion.valueOf(resultados.getString(1));

	        } catch (Exception e) {
	            throw new MissingDataException(e);
	        }
	}

	@Override
	public int update(Usuario t) {
		return 0;
	}

}
