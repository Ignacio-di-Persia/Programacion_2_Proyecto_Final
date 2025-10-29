package guarderiaCentral.backend.Models;

public class Socio extends Persona {
    
    private boolean activo;
    
    public Socio(int ID, int DNI, String nombre,boolean activo) {
        super(ID, DNI, nombre);
        this.activo = activo;
    }
   
    
    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
    
    @Override
    public String toString() {
        return getID() + "," + getDNI() + "," + getNombre() + "," + activo;
    }
    
    public static Socio fromString(String linea) {
        String[] partes = linea.split(",");
        return new Socio(Integer.parseInt(partes[0]),Integer.parseInt(partes[1]),partes[2],Boolean.parseBoolean(partes[3]));
    }

}

