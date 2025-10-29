package guarderiaCentral.backend.Models;

public class Empleado extends Persona {

    private String Especialidad;
    private boolean activo;

    public Empleado(int ID,int DNI, String nombre, String Especialidad, boolean activo) {
        super(ID, DNI, nombre);
        this.Especialidad = Especialidad;
        this.activo = activo;
    }
    

    public String getEspecialidad() {
        return this.Especialidad;
    }
    
    public void setEspecialidad(String Especialidad) {
        this.Especialidad = Especialidad;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
    
    
    @Override
    public String toString() {
        return getID() + "," + getDNI() + "," + getNombre() + "," + Especialidad + "," + activo;
    }

    
    public static Empleado fromString(String linea) {
        String[] partes = linea.split(",");
        return new Empleado(Integer.parseInt(partes[0]),Integer.parseInt(partes[1]),partes[2],partes[3],Boolean.parseBoolean(partes[4]));
    }
    
}

