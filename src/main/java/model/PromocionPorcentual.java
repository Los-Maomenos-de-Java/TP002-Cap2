package model;

import java.util.stream.Collectors;

public class PromocionPorcentual extends Promocion {
    private double porcentajeDescuento;

    public PromocionPorcentual(int id, String nombre, double porcentajeDescuento) {
        super(id, nombre);
        if (porcentajeDescuento < 0) {
            throw new Error("Valor de Porcentaje de Descuento Inválido");
        }
        this.porcentajeDescuento = porcentajeDescuento;
    }

    @Override
    public double getCosto() {
        var porcentaje = 1 - porcentajeDescuento / 100;

        return costoTotalAtracciones() * porcentaje;
    }

    @Override
    public String toString() {
        return "Promocion Porcentual { " +
                "Nombre: " + this.getNombre() +
                "Atracciones: " + atracciones.stream().map(Atraccion::getNombre).collect(Collectors.toList()) +
                "Costo sin descuento: $" + atracciones.stream().mapToDouble(Atraccion::getCosto).sum() +
                "Descuento Porcentual: %" + porcentajeDescuento +
                "Costo: $" + this.getCosto() +
                "Tiempo: " + this.getTiempo() +
                " }";
    }
}