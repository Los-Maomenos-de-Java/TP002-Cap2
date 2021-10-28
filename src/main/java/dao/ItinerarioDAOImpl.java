package dao;

import jdbc.ConnectionProvider;
import model.*;
import parque.Boleteria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

public class ItinerarioDAOImpl implements ItinerarioDAO {
    private static ItinerarioDAOImpl instance;

    public static ItinerarioDAOImpl getInstance(){
        if (instance == null) {
            instance = new ItinerarioDAOImpl();
        }
        return instance;
    }

    @Override
    public List<Itinerario> findAll() {
        return null;
    }

    @Override
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
            /*String idPromociones = "SELECT fk_promocion FROM itinerarios WHERE fk_usuario = ? AND fk_promocion IS NOT NULL";
            Connection conn = ConnectionProvider.getConnection();

            PreparedStatement statementPromociones = conn.prepareStatement(idPromociones);
            statementPromociones.setInt(1, usuario.getId());

            ResultSet resultadosPromociones = statementPromociones.executeQuery();
            var ofertasCompradas = new LinkedList<Ofertable>();

            while (resultadosPromociones.next()) {
                ofertasCompradas.add(Boleteria.obtenerOfertablePorId(resultadosPromociones.getInt(1)));
            }

            String idAtracciones = "SELECT fk_atraccion FROM itinerarios WHERE fk_usuario = ? AND fk_atraccion IS NOT NULL";
            PreparedStatement statementAtracciones = conn.prepareStatement(idAtracciones);
            statementAtracciones.setInt(1, usuario.getId());

            ResultSet resultadosAtracciones = statementAtracciones.executeQuery();

            while (resultadosAtracciones.next()) {
                ofertasCompradas.add(Boleteria.obtenerOfertablePorId(resultadosAtracciones.getInt(1)));
            }
			*/
            return new Itinerario(ofertasCompradas);
        } catch (Exception e) {
            throw new MissingDataException(e);
        }
    }

    @Override
    public int insertar(Usuario usuario, Ofertable ofertable) {
        try {
            Connection conn = ConnectionProvider.getConnection();
            String insertarPromocion = "INSERT INTO itinerarios (fk_usuario, fk_promocion) VALUES (?, ?)";
            String insertarAtraccion = "INSERT INTO itinerarios (fk_usuario, fk_atraccion) VALUES (?, ?)";

            if(ofertable.esPromocion()) {
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

    @Override
    public int update(Itinerario itinerario) {
        return 0;
    }
}
