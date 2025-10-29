package guarderiaCentral.frontend;

import guarderiaCentral.backend.Models.Garage;
import guarderiaCentral.backend.controllers.GarageDAO;
import java.util.List;
import java.util.Scanner;

public class MenuGarage {

    private final GarageDAO dao;
    private int op;
    private final Scanner sc = new Scanner(System.in);

    public MenuGarage() {
        dao = new GarageDAO();
    }

    public void IniciarMenu() {
        do {
            System.out.println("******* MENÚ *******");
            System.out.println(" 1. Lista Garage.");
            System.out.println(" 2. Ingresar Garage.");
            System.out.println(" 3. Actualizar Garage.");
            System.out.println(" 4. Buscar Garage.");
            System.out.println(" 0. SALIR.");
            System.out.print(" **** OPCIÓN: ");
            op = sc.nextInt();
            sc.nextLine();

            switch (op) {
                case 1 ->
                    listar();
                case 2 ->
                    ingresar();
                case 3 ->
                    actualizar();
                case 4 ->
                    buscar();
            }
        } while (op != 0);
    }

    public void listar() {
        List<Garage> lista = dao.leerGarage();

        System.out.println("LISTA DE GARAGE...");
        if (!lista.isEmpty()) {
            System.out.println("Codigo:\t\tPiso:\t\tEstado:");
            for (Garage g : dao.leerGarage()) {
                System.out.println(g.getCodigo() + "\t\t" + g.getPiso() + "\t\t" + g.getEstado());
            }
        } else {
            System.out.println("\n\nNo se encuentra ningun Garage cargado, porfavor ingrese al menos un registro.\n");
        }
    }

    public void ingresar() {
        System.out.print("Ingrese piso: ");
        int piso = sc.nextInt();
        sc.nextLine();
        System.out.print("¿Se encuentra ocupado?: \n ingrese <1> = Libre \n <0> = Ocupado ");
        int opcion = sc.nextInt();

        boolean estado;
        if (opcion == 1) {
            estado = true;
        } else {
            estado = false;
        }
        dao.crearGarage(piso, estado);
    }

    public void actualizar() {
        System.out.print("Ingrese Codigo de garage: ");
        int Codigo = sc.nextInt();
        sc.nextLine();
        System.out.print("Ingrese nuevo piso: ");
        int piso = sc.nextInt();
        sc.nextLine();
        System.out.print("¿Se encuentra ocupado?: \n ingrese <1> = Libre \n <0> = Ocupado ");
        int opcion = sc.nextInt();

        boolean estado;
        if (opcion == 1) {
            estado = true;
        } else {
            estado = false;
        }

        sc.nextLine();
        dao.actualizarGarage(Codigo, new Garage(Codigo, piso, estado));
    }

    public void buscar() {
        System.out.print("Ingrese Codigo Garage: ");
        int Codigo = sc.nextInt();
        sc.nextLine();
        System.out.println(dao.buscarPorCodigo(Codigo));
    }

}
