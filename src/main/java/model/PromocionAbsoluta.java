package model;

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
}