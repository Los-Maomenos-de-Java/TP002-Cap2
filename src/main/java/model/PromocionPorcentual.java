package model;

public class PromocionPorcentual extends Promocion {
    private double porcentajeDescuento;

    public PromocionPorcentual(String nombre, double porcentajeDescuento) {
        super(nombre);
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
}