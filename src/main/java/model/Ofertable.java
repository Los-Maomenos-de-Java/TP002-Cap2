package model;

import java.util.List;

public interface Ofertable {
    String getNombre();

    double getCosto();

    double getTiempo();

    int getCupo();

    TipoDeAtraccion getTipo();

    List<Atraccion> getAtracciones();

    boolean esPromocion();

    void serComprada();

    boolean tieneCupo();
}