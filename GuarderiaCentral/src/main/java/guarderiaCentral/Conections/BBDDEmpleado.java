package guarderiaCentral.Conections;

import java.io.File;
import java.io.IOException;

public class BBDDEmpleado implements Conector {
    private static final String ARCHIVO = "empleados.txt";

    public static String getARCHIVO() { return ARCHIVO; }
    
    @Override
    public void inicializarBD() {
        try {
            File archivo = new File(ARCHIVO);
            if (!archivo.exists()) {
                if (archivo.createNewFile()) {
                    System.out.println("Archivo creado...");
                }
            } else {
                System.out.println("Archivo existente...");
            }
        } catch (IOException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
    }
}