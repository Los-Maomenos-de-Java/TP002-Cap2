package dao;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.junit.*;

import jdbc.ConnectionProvider;
import model.Atraccion;
import model.TipoDeAtraccion;

public class AtraccionDAOImplTest {

	private static Connection conexion;
	private static AtraccionDAOImpl atraccionDAO;

	@BeforeClass
	public static void setUpBeforeClass() throws SQLException {
		conexion = ConnectionProvider.getConnection();
		conexion.setAutoCommit(false);

		atraccionDAO = AtraccionDAOImpl.getInstance();
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
		List<Atraccion> resultado = atraccionDAO.findAll();

		// sabemos que contamos con 23 atracciones
		assertEquals(23, resultado.size());
	}

	@Test
	public void testUpdate() {
		List<Atraccion> resultado = atraccionDAO.findAll();

		// obtenemos la primer atraccion:
		// Atraccion {Nombre: Krusty Burguer, Costo Visita: 90.0, Tiempo Promedio: 0.5,
		// Cupo: 25, Tipo de Atraccion=DEGUSTACION }
		Atraccion atraccion = resultado.get(0);

		// En el método serComprada() se realiza el update de cupo
		atraccion.serComprada();

		resultado = atraccionDAO.findAll();
		Atraccion atraccionActualizada = resultado.get(0);

		assertEquals(atraccion.getCupo(), atraccionActualizada.getCupo());
	}

	@Test(expected = MissingDataException.class)
	public void testFallaUpdatePorFaltaDeCupo() {
		List<Atraccion> resultado = atraccionDAO.findAll();

		// obtenemos la atraccion 11:
		// Atraccion {Nombre: La cabaña de Willy, Costo Visita: 5.0, Tiempo Promedio:
		// 0.5, Cupo: 2, Tipo de Atraccion=RECORRIDO }
		Atraccion atraccion = resultado.get(10);

		// Intentamos comprar una vez más que el cupo de la atraccion:
		int cupo = atraccion.getCupo();
		for (int i = 0; i < cupo + 1; i++) {
			atraccion.serComprada();
		}
	}

	@SuppressWarnings("static-access")
	@Test
	public void testGetTipoAtraccion() {
		List<Atraccion> resultado = atraccionDAO.findAll();

		// obtenemos la primer atraccion:
		// Atraccion {Nombre: Krusty Burguer, Costo Visita: 90.0, Tiempo Promedio: 0.5,
		// Cupo: 25, Tipo de Atraccion=DEGUSTACION }
		Atraccion atraccion = resultado.get(0);

		// El TipoDeAtraccion con id = 1 es DEGUSTACION:
		assertEquals(atraccionDAO.getTipoAtraccion(1), TipoDeAtraccion.DEGUSTACION);
		assertEquals(atraccionDAO.getTipoAtraccion(1), atraccion.getTipo());
	}

}
