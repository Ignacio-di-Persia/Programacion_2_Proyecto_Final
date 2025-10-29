package guarderiaCentral.frontend;

import guarderiaCentral.backend.Models.Asignacion;
import guarderiaCentral.backend.controllers.AsignacionDAO;
import java.util.List;
import java.util.Scanner;

public class MenuAsignacion {
    private final AsignacionDAO dao;
    private int op;
    private final Scanner sc = new Scanner(System.in);

    public MenuAsignacion() {
        dao = new AsignacionDAO();
    }
    
    public void IniciarMenu() {
        do {
            System.out.println("******* MENÚ *******");
            System.out.println(" 1. Lista de asignaciones.");
            System.out.println(" 2. Agregar asignacion.");
            System.out.println(" 3. Actualizar asignacion.");
            System.out.println(" 4. Buscar asignacion.");
            System.out.println(" 5. Eliminar asignacion.");
            System.out.println(" 0. SALIR.");
            System.out.print(" **** OPCIÓN: ");
            op = sc.nextInt();
            sc.nextLine();

            switch (op) {
                case 1 -> listar();
                case 2 -> agregar();
                case 3 -> actualizar();
                case 4 -> buscar();
                case 5 -> eliminar();
            }
        } while (op != 0);
    }

    public void listar(){
        
        List<Asignacion> lista = dao.leerAsignacion();
        
        System.out.println("LISTA DE ASIGNACIONES...");
        if(!lista.isEmpty()){
        System.out.println("ID:\t\tCodigo Garage:\t\tID_Empleado:\t\t ID_Socio:");
        for(Asignacion a: dao.leerAsignacion()){
            System.out.println(a.getID()+"\t\t"+ a.getCodigoGarage() +"\t\t"+a.getID_Empleado()+"\t\t"+ a.getID_Socio());
        }
        }else{
            System.out.println("\n\nNo se encuentra ninguna asignación cargada, porfavor ingrese al menos un registro.\n");
        }
    }
    public void agregar(){
        System.out.print("Ingrese CodigoGarage: ");
        int CodigoGarage = sc.nextInt();
        sc.nextLine();
        System.out.print("Ingrese ID_empleado: ");
        int ID_empleado = sc.nextInt();
        sc.nextLine();
        System.out.print("Ingrese ID_socio: ");
        int ID_socio = sc.nextInt();
        sc.nextLine();
        dao.crearAsignacion(CodigoGarage,ID_empleado,ID_socio);
    }
    public void actualizar(){
        System.out.print("Ingrese id: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Ingrese CodigoGarage: ");
        int CodigoGarage = sc.nextInt();
        sc.nextLine();
        System.out.print("Ingrese ID_empleado: ");
        int ID_empleado = sc.nextInt();
        sc.nextLine();
        System.out.print("Ingrese ID_socio: ");
        int ID_socio = sc.nextInt();
        sc.nextLine();
        dao.actualizarAsignacion(id, new Asignacion(id,CodigoGarage,ID_empleado,ID_socio));
    }
    public void buscar(){
        System.out.print("Ingrese id: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.println(dao.buscarPorID(id));
    }
    public void eliminar(){
        System.out.print("Ingrese id: ");
        int id = sc.nextInt();
        sc.nextLine();
        dao.eliminarAsignacion(id);
    }
}
