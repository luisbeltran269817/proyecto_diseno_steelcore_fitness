/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package fachada;

import dtos.SucursalDTO;
import java.util.List;
import javax.swing.JComponent;

/**
 * 
 * @author julian izaguirre
 */
public class Mapa implements IMapa {

    private static Mapa instancia;
 
    private java.util.function.Supplier<JComponent>              fnGetComponente;
    private java.util.function.Consumer<List<SucursalDTO>>       fnColocarMarcadores;
    private java.util.function.Consumer<String>                  fnResaltarMarcador;
    private IMapa.CoordConsumer                                  fnCentrarEn;
    private IMapa.CoordConsumer                                  fnMostrarUsuario;
    private java.util.function.Consumer<OnMarcadorClickListener> fnSetListener;
 
    private Mapa() { }
 
    public static Mapa getInstancia() {
        if (instancia == null) instancia = new Mapa();
        return instancia;
    }
 
    @Override
    public void registrar(
            java.util.function.Supplier<JComponent>              getComponente,
            java.util.function.Consumer<List<SucursalDTO>>       colocarMarcadores,
            java.util.function.Consumer<String>                  resaltarMarcador,
            IMapa.CoordConsumer                                  centrarEn,
            IMapa.CoordConsumer                                  mostrarUsuario,
            java.util.function.Consumer<OnMarcadorClickListener> setListener) {
 
        this.fnGetComponente     = getComponente;
        this.fnColocarMarcadores = colocarMarcadores;
        this.fnResaltarMarcador  = resaltarMarcador;
        this.fnCentrarEn         = centrarEn;
        this.fnMostrarUsuario    = mostrarUsuario;
        this.fnSetListener       = setListener;
    }
 
    @Override
    public JComponent getComponente() {
        return fnGetComponente != null ? fnGetComponente.get() : null;
    }
 
    @Override
    public void colocarMarcadores(List<SucursalDTO> sucursales) {
        if (fnColocarMarcadores != null) fnColocarMarcadores.accept(sucursales);
    }
 
    @Override
    public void resaltarMarcador(String idSucursal) {
        if (fnResaltarMarcador != null) fnResaltarMarcador.accept(idSucursal);
    }
 
    @Override
    public void centrarEn(double lat, double lng) {
        if (fnCentrarEn != null) fnCentrarEn.accept(lat, lng);
    }
 
    @Override
    public void mostrarUbicacionUsuario(double lat, double lng) {
        if (fnMostrarUsuario != null) fnMostrarUsuario.accept(lat, lng);
    }
 
    @Override
    public void setOnMarcadorClickListener(OnMarcadorClickListener listener) {
        if (fnSetListener != null) fnSetListener.accept(listener);
    }
}