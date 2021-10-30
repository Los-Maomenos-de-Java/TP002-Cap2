package model;

import static org.junit.Assert.*;

import org.junit.Test;

public class UsuarioTest {

	@Test
	public void testInicializarUsuario() {
		String nombre = "Usuario De Prueba";
		double presupuesto = 2000.0;
		double tiempo = 80.0;
		TipoDeAtraccion tipoPreferido = TipoDeAtraccion.valueOf("DEGUSTACION");
		Usuario usuario = new Usuario(50, nombre, presupuesto, tiempo, tipoPreferido);

		assertEquals(nombre, usuario.getNombre());
		assertEquals(presupuesto, usuario.getPresupuestoActual(), 0.01);
		assertEquals(tiempo, usuario.getTiempoDisponible(), 0.01);
		assertEquals(tipoPreferido, usuario.getTipoDeAtraccionPreferida());
	}

	@Test(expected = Error.class)
	public void testUsuarioConPresupInvalido() {
		new Usuario(50, "Usuario De Prueba", -20.0, 80.0, TipoDeAtraccion.valueOf("DEGUSTACION"));
	}

	@Test(expected = Error.class)
	public void testUsuarioConTiempoInvalido() {
		new Usuario(50, "Usuario De Prueba", 20.0, -80.0, TipoDeAtraccion.valueOf("DEGUSTACION"));
	}

	@Test(expected = Error.class)
	public void testUsuarioNoPuedeComprarPorDineroInsuficiente() {
		Ofertable oferta = new Atraccion(50, "Atraccion De Prueba", 150.0, 2.0, TipoDeAtraccion.valueOf("DEGUSTACION"),
				10);
		Usuario usuario = new Usuario(50, "Usuario De Prueba", 20.0, 80.0, TipoDeAtraccion.valueOf("DEGUSTACION"));

		assertFalse(usuario.puedeVisitar(oferta));
		usuario.comprarOferta(oferta);
	}

	@Test(expected = Error.class)
	public void testUsuarioNoPuedeComprarPorTiempoInsuficiente() {
		Ofertable oferta = new Atraccion(50, "Atraccion De Prueba", 150.0, 2.0, TipoDeAtraccion.valueOf("DEGUSTACION"),
				10);
		Usuario usuario = new Usuario(50, "Usuario De Prueba", 200.0, 0.0, TipoDeAtraccion.valueOf("DEGUSTACION"));

		assertFalse(usuario.puedeVisitar(oferta));
		usuario.comprarOferta(oferta);
	}

	@Test
	public void testUsuarioPuedeVisitar() {
		Usuario usuario = new Usuario(50, "Usuario De Prueba", 200.0, 80.0, TipoDeAtraccion.valueOf("DEGUSTACION"));
		Ofertable oferta = new Atraccion(50, "Atraccion De Prueba", 150.0, 0.0, TipoDeAtraccion.valueOf("DEGUSTACION"),
				10);

		assertTrue(usuario.puedeVisitar(oferta));
	}
}
