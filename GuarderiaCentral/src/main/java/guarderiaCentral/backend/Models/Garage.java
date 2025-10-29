package guarderiaCentral.backend.Models;

public class Garage {

    private int codigo;
    private int piso;
    private boolean estado; //(ocupada/libre)

    public Garage(int codigo, int piso, boolean estado) {
        this.codigo = codigo;
        this.piso = piso;
        this.estado = estado;
    }

    public int getCodigo() {
        return codigo;
    }

    public int getPiso() {
        return piso;
    }

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
    
    @Override
    public String toString() {
        return  codigo + "," + piso + "," + estado;
    }
    
    
    public static Garage fromString(String linea) {
        String[] partes = linea.split(",");
        return new Garage(Integer.parseInt(partes[0]),Integer.parseInt(partes[1]),Boolean.parseBoolean(partes[2]));
    }
    
}
    
    