package dao;

import jdbc.ConnectionProvider;
import model.TipoDeAtraccion;
import model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

public class UsuarioDAOImpl {
    private static UsuarioDAOImpl instance;

    public static UsuarioDAOImpl getInstance() {
        if (instance == null) {
            instance = new UsuarioDAOImpl();
        }
        return instance;
    }

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
            TipoDeAtraccion tipo_atraccion_preferida = AtraccionDAOImpl.getTipoAtraccion(resultados.getInt(5));

            Usuario usuario = new Usuario(id, nombre, presupuesto, tiempo_disponible, tipo_atraccion_preferida);

            usuario.setOfertasCompradas(ItinerarioDAOImpl.getInstance().itinerarioDe(usuario).getOfertas());

            return usuario;

        } catch (Exception e) {
            throw new MissingDataException(e);
        }

    }

    public int update(Usuario usuario) {
        try {
            String updateUsuario = "UPDATE usuarios SET presupuesto = ?, tiempo_disponible = ?  WHERE id = ?";
            Connection conn = ConnectionProvider.getConnection();

            PreparedStatement statement = conn.prepareStatement(updateUsuario);
            statement.setDouble(1, usuario.getPresupuestoActual());
            statement.setDouble(2, usuario.getTiempoDisponible());
            statement.setInt(3, usuario.getId());

            return statement.executeUpdate();
        } catch (Exception e) {
            throw new MissingDataException(e);
        }
    }
}
