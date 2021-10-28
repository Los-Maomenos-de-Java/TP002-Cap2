package model;

import java.util.ArrayList;
import java.util.List;

public class Itinerario {
    private List<Ofertable> ofertas = new ArrayList<>();

    public Itinerario(List<Ofertable> ofertasVendidas) {
        ofertas.addAll(ofertasVendidas);
    }

    public void mostrar() {
        System.out.println(this);
    }

    public List<Ofertable> getOfertas() {
        return this.ofertas;
    }

    @Override
    public String toString() {
        String salida = "\n\t\t\t\tATRACCIONES COMPRADAS: "
                + "\n-----------------------------------------------------------------------------\n"
                + String.format("|%-29.29s |%-10.10s |%-10.10s |%-20.20s|%n", "        Atracciones", "   Costo",
                "  Tiempo", " Tipo de Atracción")
                + "-----------------------------------------------------------------------------\n";
        for (Ofertable oferta : ofertas) {
            if (oferta.esPromocion()) {
                salida += String.format("|%-29.29s |%-10.10s |%-9.9s |%-20.20s|%n", "- " + oferta.getNombre(),
                        "  $" + String.format("%.2f", oferta.getCosto()),
                        "  ⏱ " + String.format("%.2f", oferta.getTiempo()),
                        "     " + oferta.getTipo().toString().charAt(0)
                                + oferta.getTipo().toString().substring(1).toLowerCase());
                for (Atraccion atraccion : oferta.getAtracciones()) {
                    salida += String.format("|%-27.27s |%-10.10s |%-10.10s |%-20.20s|%n",
                            "\t-" + atraccion.getNombre(), "  $" + String.format("%.2f", atraccion.getCosto()), "",
                            "");
                }
                salida += String.format("|%-27.27s |%-10.10s |%-10.10s |%-20.20s|%n", "\t-Descuento",
                        "  $" + String.format("%.2f", (oferta.getCosto()
                                - oferta.getAtracciones().stream().mapToDouble(Atraccion::getCosto).sum())),
                        "", "");
                salida += "- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - \n";
            }
        }

        for (Ofertable oferta : ofertas) {
            if (!oferta.esPromocion()) {
                salida += String.format("|%-29.29s |%-10.10s |%-9.9s |%-20.20s|%n", "- " + oferta.getNombre(),
                        "  $" + String.format("%.2f", oferta.getCosto()),
                        "  ⏱ " + String.format("%.2f", oferta.getTiempo()),
                        "     " + oferta.getTipo().toString().charAt(0)
                                + oferta.getTipo().toString().substring(1).toLowerCase());
                salida += "- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - \n";
            }
        }

        var costoTotal = ofertas.stream().mapToDouble(Ofertable::getCosto).sum();
        var tiempoTotal = ofertas.stream().mapToDouble(Ofertable::getTiempo).sum();

        salida += String.format("%-30.30s |%-10.10s |%-10.10s|%n", "- TOTAL", "  $" + String.format("%.2f", costoTotal),
                "  ⏱ " + String.format("%.2f", tiempoTotal));

        salida += "-----------------------------------------------------------------------------\n";

        return salida;
    }
}