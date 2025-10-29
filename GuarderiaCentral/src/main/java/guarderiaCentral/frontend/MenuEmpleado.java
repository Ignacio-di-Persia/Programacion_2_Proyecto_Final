package guarderiaCentral.frontend;

import guarderiaCentral.backend.Models.Empleado;
import guarderiaCentral.backend.controllers.EmpleadoDAO;
import java.util.List;
import java.util.Scanner;

public class MenuEmpleado {
    private final EmpleadoDAO dao;
    private int op;
    private final Scanner sc = new Scanner(System.in);

    public MenuEmpleado() {
        dao = new EmpleadoDAO();
    }
    
    public void IniciarMenu() {
        do {
            System.out.println("******* MENÚ *******");
            System.out.println(" 1. Lista Empleados.");
            System.out.println(" 2. Agregar Empleado.");
            System.out.println(" 3. Actualizar Empleado.");
            System.out.println(" 4. Buscar Empleado.");
            System.out.println(" 5. Eliminar Empleado.");
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
         List<Empleado> lista = dao.leerEmpleado();
        System.out.println("LISTA DE EMPLEADOS...");
if (!lista.isEmpty()) {
        System.out.println("ID:\t\tDNI:\t\tNombre:\t\t Especialidad: \t\t Activo:");
        for(Empleado e: dao.leerEmpleado()){
            System.out.println(e.getID()+"\t\t"+ e.getDNI()+"\t\t"+e.getNombre()+"\t\t"+ e.getEspecialidad()+"\t\t"+ e.isActivo());
        }
                } else {
            System.out.println("\n\nNo se encuentra ningun Empleado cargado, porfavor ingrese al menos un registro.\n");
        }
    }
    
    public void agregar(){
        System.out.print("Ingrese dni: ");
        int dni = sc.nextInt();
        sc.nextLine();
        System.out.print("Ingrese nombre: ");
        String nombre = sc.nextLine();
        System.out.print("Ingrese especialidad: ");
        String especialidad = sc.nextLine();
        dao.crearEmpleado(dni, nombre, especialidad);
    }
    
    public void actualizar(){
        System.out.print("Ingrese id: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Ingrese dni: ");
        int dni = sc.nextInt();
        sc.nextLine();
        System.out.print("Ingrese nuevo nombre: ");
        String nombre = sc.nextLine();
        System.out.print("Ingrese Especialidad: ");
        String Especialidad = sc.nextLine();
        System.out.print("¿Se encuentra activo?: \n ingres <1> = ACTIVO \n <0> = NO SE ENCUENTRA ACTIVO ");
        int opcion = sc.nextInt();
        sc.nextLine();
        
        boolean activo;
        if(opcion==1){
         activo = true;
        }else{
            activo=false;
        }
        dao.actualizarEmpleado(id, new Empleado(id,dni,nombre,Especialidad,activo));
    }
    
    
    
    public void buscar(){
        System.out.print("Ingrese id: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.println(dao.buscarPorId(id));
    }
    public void eliminar(){
        System.out.print("Ingrese id: ");
        int id = sc.nextInt();
        sc.nextLine();
        dao.eliminarEmpleado(id);
    }
}

