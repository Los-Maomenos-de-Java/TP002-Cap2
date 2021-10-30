package model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class OfertableTest {

	Atraccion atraccion = new Atraccion(50, "Atraccion De Prueba", 20.0, 20.0, TipoDeAtraccion.valueOf("ACCION"), 10);
	Promocion promo = new PromocionAbsoluta(50, "Promocion De Prueba", 10.0);
	List<Ofertable> ofertas;

	@Before
	public void setUp() {
		ofertas = new ArrayList<>();
		promo.agregarAtraccion(atraccion);
		ofertas.add(atraccion);
		ofertas.add(promo);
	}

	@Test
	public void testSerOfertable() {
		assertTrue(atraccion instanceof Ofertable);
		assertTrue(promo instanceof Ofertable);

		Usuario usuario = new Usuario(50, "Usuario De Prueba", 200.0, 80.0, TipoDeAtraccion.valueOf("DEGUSTACION"));
		assertFalse(usuario instanceof Ofertable);
	}

	@Test
	public void testOfertableGetters() {
		assertEquals(atraccion.getNombre(), ofertas.get(0).getNombre());
		assertEquals(atraccion.getCosto(), ofertas.get(0).getCosto(), 0.01);
		assertEquals(atraccion.getTiempo(), ofertas.get(0).getTiempo(), 0.01);
		assertEquals(atraccion.getTipo(), ofertas.get(0).getTipo());
		assertEquals(atraccion.getCupo(), ofertas.get(0).getCupo());

		assertEquals(promo.getNombre(), ofertas.get(1).getNombre());
		assertEquals(promo.getCosto(), ofertas.get(1).getCosto(), 0.01);
		assertEquals(promo.getTiempo(), ofertas.get(1).getTiempo(), 0.01);
		assertEquals(promo.getTipo(), ofertas.get(1).getTipo());

	}

	@Test
	public void testGetCupoPromociones() {
		assertEquals(promo.getCupo(), ofertas.get(1).getCupo());

		// se comprueba que las promociones devuelven el cupo de la Atraccion con menos
		// cupo
		Promocion otraPromo = new PromocionAbsoluta(50, "Promocion De Prueba", 10.0);
		Atraccion otraAtraccion = new Atraccion(50, "Atraccion", 10.0, 10.0, TipoDeAtraccion.valueOf("ACCION"), 1);

		otraPromo.agregarAtraccion(atraccion);
		otraPromo.agregarAtraccion(otraAtraccion);

		assertEquals(otraAtraccion.getCupo(), otraPromo.getCupo());
	}

	@Test
	public void testOfertableEsPromocion() {
		assertFalse(ofertas.get(0).esPromocion());
		assertTrue(ofertas.get(1).esPromocion());
	}

	@Test
	public void testOfertableTieneCupo() {
		for (Ofertable oferta : ofertas) {
			assertTrue(oferta.tieneCupo());
		}

		Ofertable ofertaSinCupo = new Atraccion(50, "Atraccion", 10.0, 10.0, TipoDeAtraccion.valueOf("ACCION"), 0);
		assertFalse(ofertaSinCupo.tieneCupo());
	}

	@Test
	public void testOfertableGetAtracciones() {
		List<Atraccion> listaAtracciones = new ArrayList<>();
		listaAtracciones.add(atraccion);
		for (Ofertable oferta : ofertas) {
			assertEquals(listaAtracciones, oferta.getAtracciones());
		}
	}

	/*
	 * PONER ESTO EN UN TEST DE DAO
	 * 
	 * @Test public void testOfertableSerComprado() { for (Ofertable oferta :
	 * ofertas) { int cupoInicial = oferta.getCupo(); oferta.serComprada();
	 * assertEquals(cupoInicial - 1, oferta.getCupo()); }
	 * 
	 * //Se agrega una oferta a la promocion para corroborar que disminuye el cupo
	 * de cada una Promocion otraPromo = new
	 * PromocionAbsoluta("Promocion De Prueba", 10.0); Atraccion otraAtraccion = new
	 * Atraccion("Atraccion", 10.0, 10.0, TipoDeAtraccion.valueOf("ACCION"), 1);
	 * 
	 * otraPromo.agregarAtraccion(atraccion); int cupoInicial = atraccion.getCupo();
	 * otraPromo.agregarAtraccion(otraAtraccion);
	 * 
	 * otraPromo.serComprada(); assertEquals(cupoInicial - 1,
	 * otraPromo.getAtracciones().get(0).getCupo()); assertEquals(0,
	 * otraPromo.getAtracciones().get(1).getCupo()); }
	 */
}
