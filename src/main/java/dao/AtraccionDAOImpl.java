package dao;

import jdbc.ConnectionProvider;
import model.Atraccion;
import model.TipoDeAtraccion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

public class AtraccionDAOImpl implements GenericDAO<Atraccion> {
    @Override
    public List<Atraccion> findAll() {
        try {
            String sql = "SELECT * FROM atracciones";
            Connection conn = ConnectionProvider.getConnection();

            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet resultados = statement.executeQuery();

            List<Atraccion> atracciones = new LinkedList<>();
            while (resultados.next()) {
                atracciones.add(toAtraccion(resultados));
            }

            return atracciones;
        } catch (Exception e) {
            throw new MissingDataException(e);
        }
    }

    @Override
    public int update(Atraccion atraccion) {
        return 0;
    }

    private Atraccion toAtraccion(ResultSet resultados) {
        try {
            var id = resultados.getInt(1);
            var nombre = resultados.getString(2);
            var costo = resultados.getDouble(3);
            var tiempo = resultados.getDouble(4);
            var tipo = getTipoAtraccion(resultados.getInt(5));
            var cupo = resultados.getInt(6);

            return new Atraccion(id, nombre, costo, tiempo, tipo, cupo);

        } catch (Exception e) {
            throw new MissingDataException(e);
        }
    }

    public static TipoDeAtraccion getTipoAtraccion(int id) {
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

}
