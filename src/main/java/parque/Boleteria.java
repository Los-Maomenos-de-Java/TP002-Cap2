package parque;

import dao.AtraccionDAOImpl;
import dao.ItinerarioDAOImpl;
import dao.PromocionDAOImpl;
import model.Atraccion;
import model.Ofertable;
import model.Usuario;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Boleteria {
    private static List<Ofertable> ofertables = new ArrayList<>();
    private List<Ofertable> ofertasParaUsuario = new ArrayList<>();
    private Vendedor vendedor = new Vendedor();

    public Boleteria() {
        ofertables.addAll(AtraccionDAOImpl.getInstance().findAll());
        ofertables.addAll(PromocionDAOImpl.getInstance().findAll());
    }

    public static Ofertable obtenerOfertablePorId(int id, boolean esPromocion) {
        for (Ofertable ofertable : ofertables) {
            if ((ofertable.esPromocion()) && (esPromocion) && (ofertable.getId() == id)) {
                return ofertable;
            } else if ((!ofertable.esPromocion()) && (!esPromocion) && (ofertable.getId() == id)) {
                return ofertable.getAtracciones().get(0);
            }
        }
        return null;
    }

    private boolean tieneAtraccionVendida(Ofertable ofertable) {
        for (Atraccion atraccion : ofertable.getAtracciones()) {
            if (vendedor.getAtraccionesVendidas().contains(atraccion)) {
                return true;
            }
        }
        return false;
    }

    private List<Ofertable> ofertasOrdenadasPara(Usuario usuario) {
        this.ofertasParaUsuario.addAll(ofertables);
        this.ofertasParaUsuario.sort(new OrdenadorDeOfertas(usuario.getTipoDeAtraccionPreferida()));
        this.ofertasFiltradasPara(usuario);
        return this.ofertasParaUsuario;
    }

    private List<Ofertable> ofertasFiltradasPara(Usuario usuario) {
        //remover oferta si alguna de las: oferta.Atracciones est� en atraccionesVendidas
        this.ofertasParaUsuario.removeIf(this::tieneAtraccionVendida);
        this.ofertasParaUsuario.removeIf(ofertable -> !ofertable.tieneCupo());
        this.ofertasParaUsuario.removeIf(ofertable -> !usuario.puedeVisitar(ofertable));

        return this.ofertasParaUsuario;
    }

    public void ofrecerA(Usuario usuario) throws IOException {
        this.vendedor.iniciarVenta(usuario);
        this.ofertasOrdenadasPara(usuario);
        
        while (!this.ofertasParaUsuario.isEmpty() && usuario.getPresupuestoActual() > 0 && usuario.getTiempoDisponible() > 0) {
            Ofertable ofertableSugerida = this.ofertasParaUsuario.remove(0);

            if (this.vendedor.ofrecer(ofertableSugerida)) {
                ofertableSugerida.serComprada();
                usuario.comprarOferta(ofertableSugerida);

                ItinerarioDAOImpl.getInstance().insertar(usuario, ofertableSugerida);
                this.ofertasFiltradasPara(usuario);
                vendedor.continuarVenta(usuario);
            }
        }
        vendedor.mostrarItinerario();
        vendedor.generarTicket(usuario);
    }

    public List<Ofertable> filtradorParaTest(Usuario usuario) {
        ofertasOrdenadasPara(usuario);
        return ofertasFiltradasPara(usuario);
    }
}