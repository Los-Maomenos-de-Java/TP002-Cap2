package dao;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import jdbc.ConnectionProvider;
import model.Atraccion;
import model.Itinerario;
import model.Promocion;
import model.Usuario;
import parque.Boleteria;

public class ItinerarioDAOImplTest {

	private static Connection conexion;
	private static ItinerarioDAOImpl itinerarioDAO;

	@BeforeClass
	public static void setUpBeforeClass() throws SQLException {
		conexion = ConnectionProvider.getConnection();
		conexion.setAutoCommit(false);

		itinerarioDAO = ItinerarioDAOImpl.getInstance();
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
	public void testInsertar() {
		Boleteria boleteria = new Boleteria();
		// obtenemos el usuario y las ofertas a agregar
		Usuario usuario = UsuarioDAOImpl.getInstance().findAll().get(0);
		Atraccion atraccion = AtraccionDAOImpl.getInstance().findAll().get(2);
		Promocion promocion = PromocionDAOImpl.getInstance().findAll().get(0);

		// las consultas de inserción devuelven 1 si se ejecutaron correctamente
		assertEquals(1, itinerarioDAO.insertar(usuario, promocion));
		assertEquals(1, itinerarioDAO.insertar(usuario, atraccion));
	}

	@Test
	public void testItinerarioDe() {
		Boleteria boleteria = new Boleteria();
		// obtenemos el usuario y las ofertas a agregar
		Usuario usuario = UsuarioDAOImpl.getInstance().findAll().get(0);
		// atraccion id = 3:
		Atraccion atraccion = AtraccionDAOImpl.getInstance().findAll().get(2);
		// promocion id = 1, que contiene las atracciones con id = 1,2 y 4:
		Promocion promocion = PromocionDAOImpl.getInstance().findAll().get(0);

		itinerarioDAO.insertar(usuario, promocion);
		itinerarioDAO.insertar(usuario, atraccion);

		// las consultas de inserción devuelven 1 si se ejecutaron correctamente
		Itinerario itinerario = ItinerarioDAOImpl.getInstance().itinerarioDe(usuario);

		// sabemos que cargó 2 ofertas:
		assertEquals(2, itinerario.getOfertas().size());

		// comprobamos que obtenemos los datos correctos con su respectivo tipo:
		assertEquals(promocion.getId(), itinerario.getOfertas().get(0).getId());
		assertEquals(atraccion.getId(), itinerario.getOfertas().get(1).getId());

		assertTrue(itinerario.getOfertas().get(0) instanceof Promocion);
		assertTrue(itinerario.getOfertas().get(1) instanceof Atraccion);
	}

}
