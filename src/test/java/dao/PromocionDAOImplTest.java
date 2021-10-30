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
import model.Promocion;
import parque.Boleteria;

public class PromocionDAOImplTest {

	private static Connection conexion;
	private static PromocionDAOImpl promocionDAO;

	@BeforeClass
	public static void setUpBeforeClass() throws SQLException {
		conexion = ConnectionProvider.getConnection();
		conexion.setAutoCommit(false);

		promocionDAO = PromocionDAOImpl.getInstance();
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
		Boleteria boleteria = new Boleteria();
		List<Promocion> resultado = promocionDAO.findAll();

		// sabemos que contamos con 6 promociones
		assertEquals(6, resultado.size());
	}

}
