package model;

import static org.junit.Assert.*;

import org.junit.Test;

public class AtraccionesTest {

	@Test
	public void testInicializarAtracciones() {
		String nombre = "Atraccion De Prueba";
		double costoVisita = 20.0;
		double tiempoVisita = 20.0;
		TipoDeAtraccion tipoAtraccion = TipoDeAtraccion.valueOf("ACCION");
		int cupo = 10;

		Atraccion atraccion = new Atraccion(50, nombre, costoVisita, tiempoVisita, tipoAtraccion, cupo);
		assertEquals(nombre, atraccion.getNombre());
		assertEquals(costoVisita, atraccion.getCosto(), 0.01);
		assertEquals(tiempoVisita, atraccion.getTiempo(), 0.01);
		assertEquals(tipoAtraccion, atraccion.getTipo());
		assertEquals(cupo, atraccion.getCupo());
	}

	@Test(expected = Error.class)
	public void testAtraccionConCostoInvalido() {
		new Atraccion(50, "Atraccion De Prueba", -20.0, 20.0, TipoDeAtraccion.valueOf("ACCION"), 10);
	}

	@Test(expected = Error.class)
	public void testAtraccionConTiempoInvalido() {
		new Atraccion(50, "Atraccion De Prueba", 20.0, -20.0, TipoDeAtraccion.valueOf("ACCION"), 10);
	}

	@Test(expected = Error.class)
	public void testAtraccionConCupoInvalido() {
		new Atraccion(50, "Atraccion De Prueba", 20.0, 20.0, TipoDeAtraccion.valueOf("ACCION"), -10);
	}
}
