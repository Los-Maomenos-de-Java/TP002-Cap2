package model;

import java.util.stream.Collectors;

public class PromocionAbsoluta extends Promocion {
    private double descuentoAbsoluto;

    public PromocionAbsoluta(int id, String nombre, double descuentoAbsoluto) {
        super(id, nombre);
        if (descuentoAbsoluto < 0) {
            throw new Error("Valor de Descuento Inválido");
        }
        this.descuentoAbsoluto = descuentoAbsoluto;
    }

    @Override
    public double getCosto() {
        return costoTotalAtracciones() - descuentoAbsoluto;
    }

    @Override
    public String toString() {
        return "Promocion Absoluta { " +
                " Nombre: " + this.getNombre() +
                "Atracciones: " + atracciones.stream().map(Atraccion::getNombre).collect(Collectors.toList()) +
                "Costo sin descuento: $" + atracciones.stream().mapToDouble(Atraccion::getCosto).sum() +
                " Descuento Absoluto: $" + descuentoAbsoluto +
                " Costo: $" + this.getCosto() +
                " Tiempo: " + this.getTiempo() +
                " }";
    }
}