package guarderiaCentral.backend.controllers;

import guarderiaCentral.Conections.BBDDAsignaciones;
import guarderiaCentral.backend.Models.Asignacion;
import guarderiaCentral.backend.Models.Empleado;
import guarderiaCentral.backend.Models.Garage;
import guarderiaCentral.backend.Models.Socio;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AsignacionDAO {

    SocioDAO socioDAO = new SocioDAO();
    EmpleadoDAO empleadoDAO = new EmpleadoDAO();
    GarageDAO garageDAO = new GarageDAO();

    public int ultimoID() {
        int ultimoID;

        List<Asignacion> lista = leerAsignacion();
        if (!lista.isEmpty()) {
            ultimoID = lista.get(lista.size() - 1).getID();
        } else {
            ultimoID = 0;
        }
        return ultimoID + 1;
    }

    public void crearAsignacion(int CodigoGarage, int ID_Empleado, int ID_Socio) {

        if (socioYaAsignado(ID_Socio)) {
            System.out.println("Este socio ya tiene una garage y empleado asignado.");
            return;
        }

        if (ValidarDatos(ID_Empleado, ID_Socio, CodigoGarage)) {

            Asignacion a = new Asignacion(ultimoID(), CodigoGarage, ID_Empleado, ID_Socio);

            if (!existe(a.getID())) {
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(BBDDAsignaciones.getARCHIVO(), true))) {
                    bw.write(a.toString());
                    bw.newLine();
                } catch (IOException ex) {
                    System.out.println("ERROR: " + ex.getMessage());
                } finally {

                    //marca ocupada la habitacion
                    System.out.println("Asignacion cargada...");
                }
            } else {
                System.out.println("ERROR: Asignacion ya registrada");
            }
        } else {
            System.out.println("ERROR: Datos inválidos o Garage no disponible.");
            return;
        }
    }

    public boolean existe(int ID) {
        boolean encontrado = false;
        List<Asignacion> lista = leerAsignacion();
        for (Asignacion a : lista) {
            if (a.getID() == ID) {
                encontrado = true;
            }
        }
        return encontrado;
    }

    public boolean ValidarDatos(int CodigoGarage, int ID_Empleado, int ID_Socio) {
        boolean existeS = false;
        boolean existeE = false;
        boolean existeG = false;

        List<Socio> listaS = socioDAO.leerSocio();
        List<Empleado> listaE = empleadoDAO.leerEmpleado();
        List<Garage> listaG = garageDAO.leerGarage();

        for (Socio s : listaS) {
            if (s.getID() == ID_Socio) {
                existeS = s.isActivo();
            }
        }

        for (Empleado e : listaE) {
            if (e.getID() == ID_Empleado) {
                existeE = e.isActivo();
            }
        }

        for (Garage g : listaG) {
            if (g.getCodigo() == CodigoGarage) {
                existeG = g.getEstado();
            }
        }

        System.out.println("Empleado existe: " + existeE);
        System.out.println("Socio existe: " + existeS);
        System.out.println("Garage existe y disponible: " + existeG);

        return existeS && existeE && existeG;
    }

    public boolean socioYaAsignado(int ID_Socio) {
        List<Asignacion> lista = leerAsignacion(); // lee desde asignaciones.txt

        for (Asignacion a : lista) {
            if (a.getID_Socio() == ID_Socio) {
                return true; // ya tiene una asignación
            }
        }
        return false; // libre
    }

    public int asignacionIDxSocioId(int ID_Socio) {

        if (socioYaAsignado(ID_Socio)) {
            try (BufferedReader br = new BufferedReader(new FileReader(BBDDAsignaciones.getARCHIVO()))) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    String[] partes = linea.split(",");
                    int id = Integer.parseInt(partes[3]);
                    int idAsignacion = Integer.parseInt(partes[0]);
                    if (id == ID_Socio) {
                        return idAsignacion;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return 0; // libre
    }

    public boolean empleadoYaAsignado(int ID_empleado) {
        List<Asignacion> lista = leerAsignacion(); // lee desde asignaciones.txt

        for (Asignacion a : lista) {
            if (a.getID_Empleado() == ID_empleado) {
                return true; // ya tiene una asignación
            }
        }
        return false; // libre
    }

    public void vecesAsignadoEmpleado(int id_empleado) {
        List<Asignacion> lista = leerAsignacion(); // lee desde asignaciones.txt
        boolean tieneAsignaciones = false;
        String nombreEmpleado = empleadoDAO.obtenerNombreEmpleado(id_empleado);
        System.out.println("El empleado: " + nombreEmpleado);
        System.out.println("\nSocios Atendidos:");
        
        for (Asignacion a : lista) {
            if (a.getID_Empleado() == id_empleado) {
                if (empleadoYaAsignado(id_empleado)) {
                    int id_socio = a.getID_Socio();
                    String socio = socioDAO.buscarPorId(id_socio);
                    System.out.println("- " + socio);
                    tieneAsignaciones = true;
                }
            }
        } // libre
      

        if (!tieneAsignaciones) {
            System.out.println("No atendio ningun socio.");
        }

        System.out.println();
    }

    public int asignacionIDxEmpleadoId(int ID_empleado) {

        if (empleadoYaAsignado(ID_empleado)) {
            try (BufferedReader br = new BufferedReader(new FileReader(BBDDAsignaciones.getARCHIVO()))) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    String[] partes = linea.split(",");
                    int id = Integer.parseInt(partes[2]);
                    int idAsignacion = Integer.parseInt(partes[0]);
                    if (id == ID_empleado) {
                        return idAsignacion;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return 0; // libre
    }

    public List<Asignacion> leerAsignacion() {
        List<Asignacion> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(BBDDAsignaciones.getARCHIVO()))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                lista.add(Asignacion.fromString(linea));
            }
        } catch (IOException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
        return lista;
    }

    public void actualizarAsignacion(int ID, Asignacion nuevo) {
        List<Asignacion> lista = leerAsignacion();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(BBDDAsignaciones.getARCHIVO()))) {
            for (Asignacion a : lista) {
                if (a.getID() == ID) {
                    bw.write(nuevo.toString());
                } else {
                    bw.write(a.toString());
                }
                bw.newLine();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String buscarPorID(int ID) {
        String asignacion = "Asignacion no encontrada...";
        List<Asignacion> lista = leerAsignacion();
        for (Asignacion a : lista) {
            if (a.getID() == ID) {
                asignacion = a.toString();
            }
        }
        return asignacion;
    }

    public int obtenerIdEmpleado(int Asignacion_id) {
        if (existe(Asignacion_id)) {
            try (BufferedReader br = new BufferedReader(new FileReader(BBDDAsignaciones.getARCHIVO()))) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    String[] partes = linea.split(",");
                    int id = Integer.parseInt(partes[0]);
                    int idEmpleado = Integer.parseInt(partes[2]);
                    if (Asignacion_id == id) {
                        return idEmpleado;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No se encuentra un id de empleado asignado");

        }
        return 0;
    }

    public int obtenerIdSocio(int Asignacion_id) {
        if (existe(Asignacion_id)) {
            try (BufferedReader br = new BufferedReader(new FileReader(BBDDAsignaciones.getARCHIVO()))) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    String[] partes = linea.split(",");
                    int id = Integer.parseInt(partes[0]);
                    int idsocio = Integer.parseInt(partes[3]);
                    if (Asignacion_id == id) {
                        return idsocio;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No se encuentra un id de socio asignado");

        }
        return 0;
    }

    public int obtenerCodigoGarage(int Asignacion_id) {
        if (existe(Asignacion_id)) {
            try (BufferedReader br = new BufferedReader(new FileReader(BBDDAsignaciones.getARCHIVO()))) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    String[] partes = linea.split(",");
                    int id = Integer.parseInt(partes[0]);
                    int codigoGarage = Integer.parseInt(partes[1]);
                    if (Asignacion_id == id) {
                        return codigoGarage;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No se encuentra un codigo de garage asignado");

        }
        return 0;
    }

    public void eliminarAsignacion(int ID) {
        List<Asignacion> lista = leerAsignacion();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(BBDDAsignaciones.getARCHIVO()))) {
            for (Asignacion a : lista) {
                if (a.getID() != ID) {
                    bw.write(a.toString());
                    bw.newLine();
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
