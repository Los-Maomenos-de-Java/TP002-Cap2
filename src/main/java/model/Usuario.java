package model;

import dao.DAOFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Usuario {
    private int id;
    private String nombre;
    private double presupuestoActual;
    private double tiempoDisponible;
    private TipoDeAtraccion tipoDeAtraccionPreferida;
    private List<Ofertable> ofertasCompradas = new ArrayList<>();

    public Usuario(int id, String nombre, double presupuesto, double tiempo_disponible,
                   TipoDeAtraccion tipo_atraccion_preferido) {
        this.id = id;
        this.nombre = nombre;
        this.presupuestoActual = presupuesto;
        this.tiempoDisponible = tiempo_disponible;
        this.tipoDeAtraccionPreferida = tipo_atraccion_preferido;

        if (presupuestoActual < 0) {
            throw new Error("Presupuesto Inválido");
        }
        if (tiempoDisponible < 0) {
            throw new Error("Tiempo Disponible Inválido");
        }
    }

    public boolean comprarOferta(Ofertable ofertable) {
        if (!puedeVisitar(ofertable)) {
            throw new Error("No posee tiempo o dinero para comprar esta oferta");
        }
        if (ofertable.tieneCupo()) {
            presupuestoActual -= ofertable.getCosto();
            tiempoDisponible -= ofertable.getTiempo();
            ofertasCompradas.add(ofertable);
            DAOFactory.getUsuarioDAO().update(this);
            return true;
        }
        return false;
    }

    public boolean puedeVisitar(Ofertable ofertable) {
        return this.tiempoDisponible >= ofertable.getTiempo() && this.presupuestoActual >= ofertable.getCosto();
    }

    public int getId() {
        return this.id;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPresupuestoActual() {
        return presupuestoActual;
    }

    public double getTiempoDisponible() {
        return tiempoDisponible;
    }

    public TipoDeAtraccion getTipoDeAtraccionPreferida() {
        return this.tipoDeAtraccionPreferida;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Usuario that = (Usuario) o;
        return nombre.equals(that.nombre) && Double.compare(presupuestoActual, that.presupuestoActual) == 0
                && Double.compare(tiempoDisponible, that.tiempoDisponible) == 0
                && tipoDeAtraccionPreferida.equals(that.tipoDeAtraccionPreferida);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, presupuestoActual, tiempoDisponible, tipoDeAtraccionPreferida);
    }

    public List<Ofertable> getOfertasCompradas() {
        return this.ofertasCompradas;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", presupuestoActual=" + presupuestoActual +
                ", tiempoDisponible=" + tiempoDisponible +
                ", tipoDeAtraccionPreferida=" + tipoDeAtraccionPreferida +
                '}';
    }
}