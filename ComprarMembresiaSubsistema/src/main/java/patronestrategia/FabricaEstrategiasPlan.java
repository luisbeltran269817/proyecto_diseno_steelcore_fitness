/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package patronestrategia;

/**
 *
 * @author julian izaguirre
 */
public class FabricaEstrategiasPlan {
    
    /**
     * @param idPlan ID del plan: "P001" = Mensual, "P002" = Semestral, "P003" = Anual.
     * @return Devuelve la estrategia correspondiente al plan indicado
     */
    /**
    public static IEstrategiaPlan obtener(String idPlan) {
        if (idPlan == null) {
            throw new IllegalArgumentException("El ID del plan no puede ser nulo");
        }
 
        switch (idPlan.toUpperCase().trim()) {
            case "P001":
            case "MENSUAL":
                return new EstrategiaPlanMensual();
 
            case "P002":
            case "SEMESTRAL":
                return new EstrategiaPlanSemestral();
 
            case "P003":
            case "ANUAL":
                return new EstrategiaPlanAnual();
 
            default:
                throw new IllegalArgumentException(
                    "Plan no reconocido: " + idPlan);
        }
    }
    */
    
}
