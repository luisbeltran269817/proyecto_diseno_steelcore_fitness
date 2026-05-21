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
public interface IMapa {

    // ── Tipos funcionales propios de la interfaz ──────────────────────────
    @FunctionalInterface
    interface CoordConsumer {
        void accept(double lat, double lng);
    }

    @FunctionalInterface
    interface OnMarcadorClickListener {
        void onMarcadorClick(String idSucursal);
    }

    // ── Setup: conecta la capa visual con el singleton ────────────────────
    void registrar(
            java.util.function.Supplier<javax.swing.JComponent>          getComponente,
            java.util.function.Consumer<List<SucursalDTO>>               colocarMarcadores,
            java.util.function.Consumer<String>                          resaltarMarcador,
            CoordConsumer                                                 centrarEn,
            CoordConsumer                                                 mostrarUsuario,
            java.util.function.Consumer<OnMarcadorClickListener>         setListener);

    // ── Operaciones del mapa ──────────────────────────────────────────────
    JComponent getComponente();

    void colocarMarcadores(List<SucursalDTO> sucursales);

    void resaltarMarcador(String idSucursal);

    void centrarEn(double lat, double lng);

    void mostrarUbicacionUsuario(double lat, double lng);

    void setOnMarcadorClickListener(OnMarcadorClickListener listener);
}
