package dao;

import jdbc.ConnectionProvider;
import model.Atraccion;
import model.Ofertable;
import model.TipoDeAtraccion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

public class AtraccionDAOImpl implements OfertableDAO {
    @Override
    public List<Ofertable> findAll() {
        try {
            String sql = "SELECT * FROM USERS";
            Connection conn = ConnectionProvider.getConnection();

            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet resultados = statement.executeQuery();

            List<Atraccion> atracciones = new LinkedList<>();
            while (resultados.next()) {
                atracciones.add(toUser(resultados));
            }

            return atracciones;
        } catch (Exception e) {
            throw new MissingDataException(e);
        }
    }

    @Override
    public int update(Ofertable ofertable) {
        return 0;
    }

    @Override
    public Ofertable encontrarOfertablePorNombre(String nombre) {
        return null;
    }

    private Atraccion toAtraccion(ResultSet resultados) {
        try {
            var nombre = resultados.getString(1);
            var costo = resultados.getDouble(2);
            var tiempo = resultados.getDouble(3);
            var tipo = getTipoAtraccion(resultados.getInt(4));
            var cupo = resultados.getInt(5);

            return new Atraccion(nombre, costo, tiempo, tipo, cupo);

        } catch (Exception e) {
            throw new MissingDataException(e);
        }
    }

    private TipoDeAtraccion getTipoAtraccion(int id) {
        try {
            String sql = "SELECT \"tipos_de_atraccion\".\"nombre\" " +
                    "FROM \"tipos_de_atraccion\", \"atracciones\" " +
                    "WHERE \"tipos_de_atraccion\".\"id\" = \"atracciones\".\"tipo_atraccion\" " +
                    "AND \"atracciones\".\"id\" = ?";

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
