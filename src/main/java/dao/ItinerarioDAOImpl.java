package dao;

import jdbc.ConnectionProvider;
import model.*;
import parque.Boleteria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;

public class ItinerarioDAOImpl {
    private static ItinerarioDAOImpl instance;

    public static ItinerarioDAOImpl getInstance() {
        if (instance == null) {
            instance = new ItinerarioDAOImpl();
        }
        return instance;
    }

    public Itinerario itinerarioDe(Usuario usuario) {
        try {
            String idOfertables = "SELECT coalesce(fk_atraccion, fk_promocion), fk_promocion IS NOT NULL FROM itinerarios WHERE fk_usuario = ?";
            Connection conn = ConnectionProvider.getConnection();

            PreparedStatement statementOfertables = conn.prepareStatement(idOfertables);
            statementOfertables.setInt(1, usuario.getId());

            ResultSet resultadosOfertables = statementOfertables.executeQuery();
            var ofertasCompradas = new LinkedList<Ofertable>();

            while (resultadosOfertables.next()) {
                ofertasCompradas.add(Boleteria.obtenerOfertablePorId(resultadosOfertables.getInt(1), resultadosOfertables.getBoolean(2)));
            }

            return new Itinerario(ofertasCompradas);
        } catch (Exception e) {
            throw new MissingDataException(e);
        }
    }

    public int insertar(Usuario usuario, Ofertable ofertable) {
        try {
            Connection conn = ConnectionProvider.getConnection();
            String insertarPromocion = "INSERT INTO itinerarios (fk_usuario, fk_promocion) VALUES (?, ?)";
            String insertarAtraccion = "INSERT INTO itinerarios (fk_usuario, fk_atraccion) VALUES (?, ?)";

            if (ofertable.esPromocion()) {
                PreparedStatement statement = conn.prepareStatement(insertarPromocion);
                statement.setInt(1, usuario.getId());
                statement.setInt(2, ofertable.getId());

                return statement.executeUpdate();
            } else {
                PreparedStatement statement = conn.prepareStatement(insertarAtraccion);
                statement.setInt(1, usuario.getId());
                statement.setInt(2, ofertable.getId());

                return statement.executeUpdate();
            }
        } catch (Exception e) {
            throw new MissingDataException(e);
        }
    }
}
