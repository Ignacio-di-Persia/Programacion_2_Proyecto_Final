package guarderiaCentral.frontend;

import guarderiaCentral.backend.controllers.ConsultasDAO;
import java.util.Scanner;

public class MenuConsultas {
    
    private final ConsultasDAO dao;
    private int op;
    private final Scanner sc = new Scanner(System.in);
    
    public MenuConsultas() {
        dao = new ConsultasDAO();
    }
    
    public void IniciarMenu() {
        do {
            System.out.println("******* MENÚ *******");
            System.out.println(" 1. Lista de Socio - Garage.");
            System.out.println(" 2. Lista Socio - Empleado.");
            System.out.println(" 3. Lista Empleado - Socio.");
            System.out.println(" 0. SALIR.");
            System.out.print(" **** OPCIÓN: ");
            op = sc.nextInt();
            sc.nextLine();

            switch (op) {
                case 1 -> SocioGarage();
                case 2 -> SocioEmpleado();
                case 3 -> EmpleadoSocio();
            }
        } while (op != 0);
    }
    
    
    public void SocioGarage(){
        System.out.println("\n--------------\n");
        System.out.println("Para conocer el garage del socio, porfavor ");
        System.out.print("ingrese Nombre del Socio: ");
        String nombreSocio = sc.nextLine();
        dao.ConsultaSocioGarage(nombreSocio);
    }
    
   
    public void SocioEmpleado(){
        System.out.println("\n--------------\n");
        System.out.println("Para conocer el empleado asignado al socio, porfavor ");
        System.out.print("ingrese Nombre del Socio: ");
        String nombreSocio = sc.nextLine();
        dao.ConsultaSocioEmpleado(nombreSocio);
    }
    
    
    public void EmpleadoSocio(){
        System.out.println("\n--------------\n");
        System.out.println("Para conocer los socios asignados al empleado, porfavor ");
        System.out.print("ingrese el id del Empleado: ");
        int id = sc.nextInt();
        sc.nextLine();
        dao.ConsultaEmpleadoSocio(id);
    }
}

