package guarderiaCentral.Conections;

public class BBDDConector {

    public static void Conectar() {
        BBDDEmpleado bde = new BBDDEmpleado();
        bde.inicializarBD();
        BBDDSocio bds = new BBDDSocio();
        bds.inicializarBD();
        BBDDGarage bdg = new BBDDGarage();
        bdg.inicializarBD();
        BBDDAsignaciones bda = new BBDDAsignaciones();
        bda.inicializarBD();
    }
    
}
