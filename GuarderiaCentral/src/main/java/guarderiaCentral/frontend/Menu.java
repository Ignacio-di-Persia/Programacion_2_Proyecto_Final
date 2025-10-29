package guarderiaCentral.frontend;

import java.util.Scanner;

public class Menu implements MenuConector{
    private int op;
    Scanner sc = new Scanner(System.in);

    @Override
    public void IniciarMenu() {
        do {
            System.out.println("\n===== MENÚ PRINCIPAL =====");
            System.out.println("1. Menú gestión Empleados.");
            System.out.println("2. Menú gestión Socios.");
            System.out.println("3. Menú gestión Garage.");
            System.out.println("4. Menú gestión Asignaciones.");
            System.out.println("5. Menú de Consultas.");
            System.out.println("0. Salir del sistema");
            System.out.print("Elija una opción: ");
            op = sc.nextInt();
            sc.nextLine(); // limpiar buffer

            switch (op) {
                case 1 ->
                    empleado();
                case 2 -> socio();
                case 3 -> garage();
                case 4 -> asignacion();
                case 5 -> consultas();
                case 0 ->
                    System.out.println("Saliendo del sistema...");
                default ->
                    System.out.println("Opción inválida, intente de nuevo.");
            }
        } while (op != 0);

    }

    public void empleado() {
        MenuEmpleado menu = new MenuEmpleado();
        menu.IniciarMenu();
    }
    
    public void socio() {
        MenuSocio menu = new MenuSocio();
        menu.IniciarMenu();
    }
    
    public void garage() {
        MenuGarage menu = new MenuGarage();
        menu.IniciarMenu();
    }
    
    
    public void asignacion() {
        MenuAsignacion menu = new MenuAsignacion();
        menu.IniciarMenu();
    }

    public void consultas() {
        MenuConsultas menu = new MenuConsultas();
        menu.IniciarMenu();
    }
}

