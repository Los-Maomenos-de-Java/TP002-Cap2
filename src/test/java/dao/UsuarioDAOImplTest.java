package dao;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import jdbc.ConnectionProvider;
import model.Atraccion;
import model.TipoDeAtraccion;
import model.Usuario;

public class UsuarioDAOImplTest {

	private static Connection conexion;
	private static UsuarioDAOImpl usuarioDAO;

	@BeforeClass
	public static void setUpBeforeClass() throws SQLException {
		conexion = ConnectionProvider.getConnection();
		conexion.setAutoCommit(false);

		usuarioDAO = UsuarioDAOImpl.getInstance();
	}

	@After
	public void tearDown() throws SQLException {
		conexion.rollback();
	}

	@AfterClass
	public static void tearDownAfterClass() throws SQLException {
		conexion = ConnectionProvider.getConnection();
		conexion.setAutoCommit(true);
	}

	@Test
	public void testFindAll() {
		List<Usuario> resultado = usuarioDAO.findAll();

		// sabemos que contamos con 8 usuarios
		assertEquals(8, resultado.size());
	}

	@Test
	public void testUpdate() {
		List<Usuario> resultado = usuarioDAO.findAll();

		// obtenemos un usuario a actualizar:
		// Usuario{id=1, nombre='Homero Simpson', presupuestoActual=500.0,
		// tiempoDisponible=80.0, tipoDeAtraccionPreferida=DEGUSTACION}
		Usuario usuario = resultado.get(0);

		double tiempoInicial = usuario.getTiempoDisponible();
		double presupuestoInicial = usuario.getPresupuestoActual();

		// creamos la oferta que va a comprar:
		Atraccion atraccion = new Atraccion(0, "Atraccion de Prueba", 10, 10, TipoDeAtraccion.ACCION, 10);
		usuario.comprarOferta(atraccion);

		assertEquals(tiempoInicial - atraccion.getTiempo(), usuario.getTiempoDisponible(), 0.1);
		assertEquals(presupuestoInicial - atraccion.getCosto(), usuario.getPresupuestoActual(), 0.1);
	}

}
