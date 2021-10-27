package parque;

import dao.DAOFactory;
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
        ofertables.addAll(DAOFactory.getAtraccionDAO().findAll());
        ofertables.addAll(DAOFactory.getPromocionDAO().findAll());
    }

    public static Ofertable obtenerOfertablePorId(int id, boolean esPromocion ) {
    	for (Ofertable ofertable : ofertables) {
    		if((ofertable.esPromocion()) && (esPromocion) && (ofertable.getId() == id)) {
    			return ofertable;
    		} else if ((!ofertable.esPromocion()) && (!esPromocion) && (ofertable.getId() == id)) {
    			return ofertable.getAtracciones().get(0);
    		}
    	}
    	
    	/*
        for (Ofertable ofertable : ofertables) {
            if (!ofertable.esPromocion()) {
                if (ofertable.getId() == id) {
                    return ofertable.getAtracciones().get(0);
                }
            } else {
                if (ofertable.getId() == id) {
                    return ofertable;
                }
            }
        }*/
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
        this.ofertasOrdenadasPara(usuario);
        this.vendedor.iniciarVenta(usuario);

        while (!this.ofertasParaUsuario.isEmpty() && usuario.getPresupuestoActual() > 0 && usuario.getTiempoDisponible() > 0) {
            Ofertable ofertableSugerida = this.ofertasParaUsuario.remove(0);

            if (this.vendedor.ofrecer(ofertableSugerida)) {
                usuario.comprarOferta(ofertableSugerida);
                DAOFactory.getItinerarioDAO().insertar(usuario, ofertableSugerida);
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