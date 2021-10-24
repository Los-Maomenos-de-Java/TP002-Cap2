package model;

public class PromocionAbsoluta extends Promocion {
    private double descuentoAbsoluto;

    public PromocionAbsoluta(String nombre, double descuentoAbsoluto) {
        super(nombre);
        if (descuentoAbsoluto < 0) {
            throw new Error("Valor de Descuento Inválido");
        }
        this.descuentoAbsoluto = descuentoAbsoluto;
    }

    @Override
    public double getCosto() {
        return costoTotalAtracciones() - descuentoAbsoluto;
    }
}