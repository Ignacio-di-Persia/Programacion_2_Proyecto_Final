package guarderiaCentral.frontend;

import guarderiaCentral.backend.Models.Socio;
import guarderiaCentral.backend.controllers.SocioDAO;
import java.util.List;
import java.util.Scanner;

public class MenuSocio {
     private final SocioDAO dao;
    private int op;
    private final Scanner sc = new Scanner(System.in);

    public MenuSocio() {
        dao = new SocioDAO();
    }
    
    public void IniciarMenu() {
        do {
            System.out.println("******* MENÚ *******");
            System.out.println(" 1. Lista Socios.");
            System.out.println(" 2. Agregar Socio.");
            System.out.println(" 3. Actualizar Socio.");
            System.out.println(" 4. Buscar Socio.");
            System.out.println(" 5. Eliminar Socio.");
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
        List<Socio> lista = dao.leerSocio();
    
        System.out.println("LISTA DE SOCIO...");
        if(!lista.isEmpty()){
        System.out.println("ID:\t\tNombre:\t\tDNI: \t\t Activo:");
        for(Socio s: dao.leerSocio()){
            System.out.println(s.getID()+"\t\t"+ s.getNombre()+"\t\t"+s.getDNI()+"\t\t"+ s.isActivo());
        }
        }else{
            System.out.println("\n\nNo se encuentra ningun Socio cargado, porfavor ingrese al menos un registro.\n");
        }
    }
    
    
    public void agregar(){
        System.out.print("Ingrese dni: ");
        int dni = sc.nextInt();
        sc.nextLine();
        System.out.print("Ingrese nombre: ");
        String nombre = sc.nextLine();
        dao.crearSocio(dni, nombre);
    }
    public void actualizar(){
        System.out.print("Ingrese ID: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Ingrese nuevo nombre: ");
        String nombre = sc.nextLine();
        System.out.print("Ingrese dni: ");
        int dni = sc.nextInt();
        sc.nextLine();
        System.out.print("¿Se encuentra activo?: \n ingres <1> = ACTIVO \n <0> = NO SE ENCUENTRA ACTIVO ");
         int opcion = sc.nextInt();
        sc.nextLine();
        
        boolean activo;
        if(opcion==1){
         activo = true;
        }else{
            activo=false;
        }
        dao.actualizarSocio(id, new Socio(id, dni, nombre,activo));
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
        dao.eliminarSocio(id);
    }
}

