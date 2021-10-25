package model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PromocionAxB extends Promocion {
    private List<Atraccion> atraccionesGratis;

    public PromocionAxB(int id, String nombre, Atraccion[] atraccionesGratis) {
        super(id, nombre);
        if (atraccionesGratis.length == 0) {
            throw new Error("No se han cargado atracciones para descontar");
        }
        this.atraccionesGratis = Arrays.stream(atraccionesGratis).collect(Collectors.toList());
    }

    @Override
    public double getCosto() {
        return atracciones
                .stream()
                .filter(atraccion -> !atraccionesGratis.contains(atraccion))
                .mapToDouble(Atraccion::getCosto).sum();
    }

    @Override
    public String toString() {
        return "Promocion AxB { " +
                "Nombre: " + this.getNombre() +
                "Atracciones: " + atracciones.stream().map(Atraccion::getNombre).collect(Collectors.toList()) +
                "Costo sin descuento: $" + atracciones.stream().mapToDouble(Atraccion::getCosto).sum() +
                "Atracciones gratis: " + this.atraccionesGratis +
                "Costo: $" + this.getCosto() +
                "Tiempo: " + this.getTiempo() +
                " }";
    }
}