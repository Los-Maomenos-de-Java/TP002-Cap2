package dao;

public class DAOFactory {
    public static OfertableDAO getAtraccionDAO() {
        return new AtraccionDAOImpl();
    }

    public static PromocionDAOImpl getPromocionDAO() {
        return new PromocionDAOImpl();
    }
    
    public static UsuarioDAOImpl getUsuarioDAO() {
        return new UsuarioDAOImpl();
    }
}
