package dao;

import jdbc.ConnectionProvider;
import model.*;
import parque.Boleteria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

public class PromocionDAOImpl implements GenericDAO<Promocion> {

    @Override
    public List<Promocion> findAll() {
        try {
            String allPromociones = "SELECT * FROM promociones";
            Connection conn = ConnectionProvider.getConnection();

            PreparedStatement statement = conn.prepareStatement(allPromociones);
            ResultSet resultados = statement.executeQuery();

            List<Promocion> promociones = new LinkedList<>();
            while (resultados.next()) {
                promociones.add(toPromocion(resultados));
            }
            return promociones;
        } catch (Exception e) {
            throw new MissingDataException(e);
        }
    }

    @Override
    public int update(Promocion promocion) {
        return 0;
    }

    private Promocion toPromocion(ResultSet resultados) {
        try {
            Promocion promocionAAgregar = null;

            var id = resultados.getInt(1);
            var nombre = resultados.getString(2);
            var tipo = resultados.getInt(3);
            var atracciones = atraccionesPorPromocion(id);

            if (tipo == 1) {
                var descuento = resultados.getDouble(4);
                promocionAAgregar = new PromocionAbsoluta(id, nombre, descuento);

                for (Atraccion atraccion : atracciones) {
                    promocionAAgregar.agregarAtraccion(atraccion);
                }
            }

            if (tipo == 2) {
                var descuento = resultados.getDouble(4);
                promocionAAgregar = new PromocionPorcentual(id, nombre, descuento);

                for (Atraccion atraccion : atracciones) {
                    promocionAAgregar.agregarAtraccion(atraccion);
                }
            }

            if (tipo == 3) {
                var atraccionesGratisString = resultados.getString(4).split(",");
                var atraccionesGratis = new Atraccion[atraccionesGratisString.length];

                for (int i = 0; i < atraccionesGratisString.length; i++) {
                    atraccionesGratis[i] = (Atraccion) Boleteria.obtenerOfertablePorId(Integer.parseInt(atraccionesGratisString[i]),false);
                }

                promocionAAgregar = new PromocionAxB(id, nombre, atraccionesGratis);

                for (Atraccion atraccion : atracciones) {
                    promocionAAgregar.agregarAtraccion(atraccion);
                }
            }

            return promocionAAgregar;

        } catch (Exception e) {
            throw new MissingDataException(e);
        }
    }

    private List<Atraccion> atraccionesPorPromocion(int idPromocion) {
        try {
            String idAtraccionesPorPromocion = "SELECT id_atraccion FROM atracciones_en_promocion WHERE id_promocion = ?";
            Connection conn = ConnectionProvider.getConnection();

            PreparedStatement statement = conn.prepareStatement(idAtraccionesPorPromocion);
            statement.setInt(1, idPromocion);

            ResultSet resultados = statement.executeQuery();
            var atracciones = new LinkedList<Atraccion>();

            while (resultados.next()) {
                atracciones.add((Atraccion) Boleteria.obtenerOfertablePorId(resultados.getInt(1),false));
            }

            return atracciones;
        } catch (Exception e) {
            throw new MissingDataException(e);
        }
    }
}
