package model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class ItinerarioTest {

    @Test
    public void testItinerarioConPromocionesYAtracciones() {
        // Atraccion:
        Atraccion atraccion0 = new Atraccion(50,"Atraccion De Prueba", 50.0, 50.0, TipoDeAtraccion.valueOf("DEGUSTACION"),
                10);

        // Promocion:
        String nombre = "PromoAxB Prueba";
        Atraccion atraccion1 = new Atraccion(50,"Atraccion De Prueba1", 20.0, 10.0, TipoDeAtraccion.valueOf("ACCION"), 10);
        Atraccion atraccion2 = new Atraccion(50,"Atraccion De Prueba2", 20.0, 20.0, TipoDeAtraccion.valueOf("ACCION"), 10);
        Atraccion[] atraccionesGratis = new Atraccion[]{atraccion1, atraccion2};

        Promocion promoAxB = new PromocionAxB(50,nombre, atraccionesGratis);
        promoAxB.agregarAtraccion(atraccion1);
        promoAxB.agregarAtraccion(atraccion2);

		List<Ofertable> ofertas = new ArrayList<>();
		ofertas.add(atraccion0);
        ofertas.add(promoAxB);

        Itinerario resumen = new Itinerario(ofertas);

        // Salida Esperada
        String salidaEsperada = "\n\t\t\t\tATRACCIONES COMPRADAS: "
                + "\n-----------------------------------------------------------------------------\n"
                + String.format("|%-29.29s |%-10.10s |%-10.10s |%-20.20s|%n", "        Atracciones", "   Costo",
                "  Tiempo", " Tipo de Atracción")
                + "-----------------------------------------------------------------------------\n";
        salidaEsperada += String.format("|%-29.29s |%-10.10s |%-9.9s |%-20.20s|%n", "- " + promoAxB.getNombre(),
                "  $" + String.format("%.2f", promoAxB.getCosto()),
                "  ⏱ " + String.format("%.2f", promoAxB.getTiempo()), "     " + promoAxB.getTipo().toString().charAt(0)
                        + promoAxB.getTipo().toString().substring(1).toLowerCase());
        for (Atraccion atraccion : promoAxB.getAtracciones()) {
            salidaEsperada += String.format("|%-27.27s |%-10.10s |%-10.10s |%-20.20s|%n", "\t-" + atraccion.getNombre(),
                    "  $" + String.format("%.2f", atraccion.getCosto()), "", "");
        }
        salidaEsperada += String
                .format("|%-27.27s |%-10.10s |%-10.10s |%-20.20s|%n", "\t-Descuento",
                        "  $" + String.format("%.2f",
                                (promoAxB.getCosto()
                                        - promoAxB.getAtracciones().stream().mapToDouble(Atraccion::getCosto).sum())),
                        "", "");
        salidaEsperada += "- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - \n";
        salidaEsperada += String.format("|%-29.29s |%-10.10s |%-9.9s |%-20.20s|%n", "- " + atraccion0.getNombre(),
                "  $" + String.format("%.2f", atraccion0.getCosto()),
                "  ⏱ " + String.format("%.2f", atraccion0.getTiempo()),
                "     " + atraccion0.getTipo().toString().charAt(0)
                        + atraccion0.getTipo().toString().substring(1).toLowerCase());
        salidaEsperada += "- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - \n";

        var costoTotal = ofertas.stream().mapToDouble(Ofertable::getCosto).sum();
        var tiempoTotal = ofertas.stream().mapToDouble(Ofertable::getTiempo).sum();

        salidaEsperada += String.format("%-30.30s |%-10.10s |%-10.10s|%n", "- TOTAL", "  $" + String.format("%.2f", costoTotal),
                "  ⏱ " + String.format("%.2f", tiempoTotal));

        salidaEsperada += "-----------------------------------------------------------------------------\n";

        assertEquals(salidaEsperada, resumen.toString());
    }
}
