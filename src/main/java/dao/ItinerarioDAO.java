package dao;

import model.*;

public interface ItinerarioDAO extends GenericDAO<Itinerario> {
    int insertar(Usuario usuario, Ofertable ofertable);

    Itinerario itinerarioDe(Usuario usuario);
}
