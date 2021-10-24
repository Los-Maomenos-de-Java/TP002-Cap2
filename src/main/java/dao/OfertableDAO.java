package dao;

import model.Ofertable;

public interface OfertableDAO extends GenericDAO<Ofertable> {
    Ofertable encontrarOfertablePorNombre(String nombre);
}
