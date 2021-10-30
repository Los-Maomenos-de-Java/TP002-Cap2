package model;

import static org.junit.Assert.*;

import org.junit.Test;

public class PromocionesTest {
	
	@Test
    public void testAgregarAtracciones() {
        String nombre = "Promo Prueba";
        Atraccion atraccion = new Atraccion(50,"Atraccion De Prueba", 20.0, 20.0, TipoDeAtraccion.valueOf("ACCION"), 10);

        Promocion promoAbs = new PromocionAbsoluta(50,nombre, 10.0);
        promoAbs.agregarAtraccion(atraccion);
        assertEquals(atraccion, promoAbs.getAtracciones().get(0));

        Promocion promoPor = new PromocionPorcentual(51,nombre, 10.0);
        promoPor.agregarAtraccion(atraccion);
        assertEquals(atraccion, promoPor.getAtracciones().get(0));

        Atraccion[] atraccionesGratis = new Atraccion[]{atraccion};
        Promocion promoAxB = new PromocionAxB(53,nombre, atraccionesGratis);
        promoAxB.agregarAtraccion(atraccion);
        assertEquals(atraccion, promoAxB.getAtracciones().get(0));
    }

    @Test(expected = Error.class)
    public void testAgregarAtraccionesRepetidasEnAbs() {
        String nombre = "Promo Prueba";
        Atraccion atraccion = new Atraccion(50,"Atraccion De Prueba", 20.0, 20.0, TipoDeAtraccion.valueOf("ACCION"), 10);

        Promocion promoAbs = new PromocionAbsoluta(50,nombre, 10.0);
        promoAbs.agregarAtraccion(atraccion);
        promoAbs.agregarAtraccion(atraccion);
    }

    @Test(expected = Error.class)
    public void testAgregarAtraccionesRepetidasEnPor() {
        String nombre = "Promo Prueba";
        Atraccion atraccion = new Atraccion(50,"Atraccion De Prueba", 20.0, 20.0, TipoDeAtraccion.valueOf("ACCION"), 10);

        Promocion promoPor = new PromocionPorcentual(50,nombre, 10.0);
        promoPor.agregarAtraccion(atraccion);
        promoPor.agregarAtraccion(atraccion);
    }

    @Test(expected = Error.class)
    public void testAgregarAtraccionesRepetidasEnAxB() {
        String nombre = "Promo Prueba";
        Atraccion atraccion = new Atraccion(50,"Atraccion De Prueba", 20.0, 20.0, TipoDeAtraccion.valueOf("ACCION"), 10);
        Atraccion[] atraccionesGratis = new Atraccion[]{atraccion};

        Promocion promoAxB = new PromocionAxB(50,nombre, atraccionesGratis);
        promoAxB.agregarAtraccion(atraccion);
        promoAxB.agregarAtraccion(atraccion);
    }

    @Test
    public void testInicializarPromoAbsoluta() {
        String nombre = "Promo Prueba";
        Atraccion atraccion = new Atraccion(50,"Atraccion De Prueba", 20.0, 20.0, TipoDeAtraccion.valueOf("ACCION"), 10);
        double descAbs = 10.0;

        Promocion promoAbs = new PromocionAbsoluta(50,nombre, descAbs);
        promoAbs.agregarAtraccion(atraccion);

        assertEquals(nombre, promoAbs.getNombre());
        assertEquals(atraccion.getCosto() - descAbs, promoAbs.getCosto(), 0.01);
    }

    @Test(expected = Error.class)
    public void testInicializarPromoAbsolutaInvalida() {
        new PromocionAbsoluta(50,"Promo Prueba", -10);
    }

    @Test
    public void testInicializarPromoPorcentual() {
        String nombre = "Promo Prueba";
        Atraccion atraccion = new Atraccion(50,"Atraccion De Prueba", 20.0, 20.0, TipoDeAtraccion.valueOf("ACCION"), 10);
        double descPor = 10.0;

        Promocion promoPor = new PromocionPorcentual(50,nombre, descPor);
        promoPor.agregarAtraccion(atraccion);
        double costoEsperado = atraccion.getCosto() * (1 - descPor / 100.0);

        assertEquals(nombre, promoPor.getNombre());
        assertEquals(costoEsperado, promoPor.getCosto(), 0.01);
    }

    @Test(expected = Error.class)
    public void testInicializarPromoPorcentualInvalida() {
        new PromocionPorcentual(50,"Promo Prueba", -10);
    }

    @Test
    public void testInicializarPromoAxB() {
        String nombre = "Promo Prueba";
        Atraccion atraccion1 = new Atraccion(50,"Atraccion De Prueba1", 20.0, 10.0, TipoDeAtraccion.valueOf("ACCION"), 10);
        Atraccion atraccion2 = new Atraccion(51,"Atraccion De Prueba2", 20.0, 20.0, TipoDeAtraccion.valueOf("ACCION"), 10);
        Atraccion[] atraccionesGratis = new Atraccion[]{atraccion1};

        Promocion promoAxB = new PromocionAxB(50,nombre, atraccionesGratis);
        promoAxB.agregarAtraccion(atraccion1);
        promoAxB.agregarAtraccion(atraccion2);

        assertEquals(nombre, promoAxB.getNombre());
        assertEquals(atraccion2.getCosto(), promoAxB.getCosto(), 0.01);
    }

    @Test(expected = Error.class)
    public void testInicializarPromoAxBInvalida() {
    	Atraccion[] atraccionesGratis = new Atraccion[]{};
        new PromocionAxB(50,"Promo Prueba", atraccionesGratis);
    }
}
