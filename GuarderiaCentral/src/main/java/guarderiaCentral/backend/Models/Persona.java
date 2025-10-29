package guarderiaCentral.backend.Models;

public class Persona {
    //Atributos:
    private int ID;
    private int DNI;
    private String nombre;
    //Costructor:

    public Persona(int ID, int DNI, String nombre) {
        this.ID = ID;
        this.DNI = DNI;
        this.nombre = nombre;
    }

   
//Getters:
    public int getID() {
        return ID;
    }

    public int getDNI() {
        return DNI;
    }

    public String getNombre() {
        return nombre;
    }


    @Override
    public String toString() {
        return ID + "," + DNI + "," + nombre;
    }
}

