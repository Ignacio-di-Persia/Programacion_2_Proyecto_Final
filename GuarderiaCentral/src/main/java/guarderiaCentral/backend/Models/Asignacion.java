package guarderiaCentral.backend.Models;

public class Asignacion {
    
    private int ID;
    private int CodigoGarage;
    private int ID_Empleado;
    private int ID_Socio;

    public Asignacion(int ID, int CodigoGarage, int ID_Empleado, int ID_Socio) {
        this.ID = ID;
        this.CodigoGarage = CodigoGarage;
        this.ID_Empleado = ID_Empleado;
        this.ID_Socio = ID_Socio;
    }

    public int getID() {
        return ID;
    }
    
    public int getCodigoGarage() {
        return CodigoGarage;
    }

    public int getID_Empleado() {
        return ID_Empleado;
    }

    public int getID_Socio() {
        return ID_Socio;
    }

    @Override
    public String toString() {
        return  ID + "," + CodigoGarage + "," + ID_Empleado + "," + ID_Socio;
    }
    
    public static Asignacion fromString(String linea) {
        String[] partes = linea.split(",");
        return new Asignacion(Integer.parseInt(partes[0]),Integer.parseInt(partes[1]),Integer.parseInt(partes[2]),Integer.parseInt(partes[3]));
    }
    
}

