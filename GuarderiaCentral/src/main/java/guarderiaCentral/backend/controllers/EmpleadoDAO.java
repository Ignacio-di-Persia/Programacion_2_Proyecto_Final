package guarderiaCentral.backend.controllers;

import guarderiaCentral.Conections.BBDDEmpleado;
import guarderiaCentral.backend.Models.Empleado;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDAO {

    public int IDultimo() {
        int IDultimo;
        List<Empleado> lista = leerEmpleado();

        if (!lista.isEmpty()) {
            IDultimo = lista.get(lista.size() - 1).getID();
        } else {
            IDultimo = 0;
        }
        return IDultimo + 1;
    }

    public void crearEmpleado(int dni, String nombre, String turno) {

        boolean activo = true;//Al crearlo ya lo dejamos activado
        Empleado e = new Empleado(IDultimo(), dni, nombre, turno, activo);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(BBDDEmpleado.getARCHIVO(), true))) {
            bw.write(e.toString());
            bw.newLine();
        } catch (IOException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        } finally {
            System.out.println("Enfermero agregado");
        }
    }

    public boolean existe(int id) {
        boolean encontrado = false;
        List<Empleado> lista = leerEmpleado();
        for (Empleado e : lista) {
            if (e.getID() == id) {
                encontrado = true;
            }
        }
        return encontrado;
    }

    public String obtenerNombreEmpleado(int idEmpleado) {
        try (BufferedReader br = new BufferedReader(new FileReader(BBDDEmpleado.getARCHIVO()))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                int id = Integer.parseInt(partes[0]);
                if (id == idEmpleado) {
                    return partes[2].trim();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "No encontrado";
    }

    public List<Empleado> leerEmpleado() {
        List<Empleado> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(BBDDEmpleado.getARCHIVO()))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                lista.add(Empleado.fromString(linea));
            }
        } catch (IOException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
        return lista;
    }

    public void actualizarEmpleado(int id, Empleado nuevo) {
        List<Empleado> lista = leerEmpleado();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(BBDDEmpleado.getARCHIVO()))) {
            for (Empleado e : lista) {
                if (e.getID() == id) {
                    bw.write(nuevo.toString());
                } else {
                    bw.write(e.toString());
                }
                bw.newLine();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String buscarPorId(int id) {
        String est = "Empleado no encontrado...";
        List<Empleado> lista = leerEmpleado();
        for (Empleado e : lista) {
            if (e.getID() == id) {
                est = e.toString();
            }
        }
        return est;
    }

    public void eliminarEmpleado(int id) {
        List<Empleado> lista = leerEmpleado();
        for (Empleado e : lista) {
            if (e.getID() == id) {
                e.setActivo(false);
                break;
            }
        }

        // Se reescribir el archivo para actualizar los datos
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(BBDDEmpleado.getARCHIVO()))) {
            for (Empleado e : lista) {
                bw.write(e.toString()); 
                bw.newLine();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
