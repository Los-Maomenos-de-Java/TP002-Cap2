package parque;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import model.*;

public class OrdenadorDeOfertasTest {

	@Test
	public void testOrdenadorDeOfertas() {
		// Orden de prioridad: TIPO - PROMO/NO PROMO - COSTO - TIEMPO
		TipoDeAtraccion atraccionPreferida = TipoDeAtraccion.valueOf("DEGUSTACION");

		Promocion oferta1 = new PromocionPorcentual(50, "Promocion De Prueba1", 10.0);
		Atraccion oferta2 = new Atraccion(50, "Atraccion De Prueba1", 20.0, 20.0,
				TipoDeAtraccion.valueOf("DEGUSTACION"), 10);
		oferta1.agregarAtraccion(oferta2);

		Promocion oferta3 = new PromocionPorcentual(51, "Promocion De Prueba2", 10.0);
		Atraccion oferta4 = new Atraccion(51, "Atraccion De Prueba2", 200.0, 20.0, TipoDeAtraccion.valueOf("ACCION"),
				10);
		oferta3.agregarAtraccion(oferta4);

		Atraccion oferta5 = new Atraccion(52, "Atraccion De Prueba2", 20.0, 40.0, TipoDeAtraccion.valueOf("ACCION"),
				10);
		Atraccion oferta6 = new Atraccion(53, "Atraccion De Prueba2", 20.0, 20.0, TipoDeAtraccion.valueOf("ACCION"),
				10);

		List<Ofertable> ofertas = new ArrayList<>();
		ofertas.add(oferta4);
		ofertas.add(oferta6);
		ofertas.add(oferta1);
		ofertas.add(oferta2);
		ofertas.add(oferta5);
		ofertas.add(oferta3);

		ofertas.sort(new OrdenadorDeOfertas(atraccionPreferida));
		assertEquals(oferta1.getNombre(), ofertas.get(0).getNombre());
		assertEquals(oferta2.getNombre(), ofertas.get(1).getNombre());
		assertEquals(oferta3.getNombre(), ofertas.get(2).getNombre());
		assertEquals(oferta4.getNombre(), ofertas.get(3).getNombre());
		assertEquals(oferta5.getNombre(), ofertas.get(4).getNombre());
		assertEquals(oferta6.getNombre(), ofertas.get(5).getNombre());

	}

}
