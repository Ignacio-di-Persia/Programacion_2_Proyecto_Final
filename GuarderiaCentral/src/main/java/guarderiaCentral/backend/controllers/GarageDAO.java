
package guarderiaCentral.backend.controllers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import guarderiaCentral.Conections.BBDDGarage;
import guarderiaCentral.backend.Models.Garage;

public class GarageDAO {

    public int ultimoCodigo() {
        int ultimoCodigo;

        List<Garage> lista = leerGarage();
        if (!lista.isEmpty()) {
            ultimoCodigo = lista.get(lista.size() - 1).getCodigo();
        } else {
            ultimoCodigo = 0;
        }
        return ultimoCodigo + 1;
    }

    public void crearGarage(int piso, boolean estado) {

        Garage g = new Garage(ultimoCodigo(), piso, estado);

        if (!existe(g.getCodigo())) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(BBDDGarage.getARCHIVO(), true))) {
                bw.write(g.toString());
                bw.newLine();
            } catch (IOException ex) {
                System.out.println("ERROR: " + ex.getMessage());
            } finally {

                System.out.println("Garage cargado. . .");
            }
        } else {
            System.out.println("ERROR: Garage ya registrado");
        }
    }

    public boolean existe(int Codigo) {
        boolean encontrado = false;
        List<Garage> lista = leerGarage();
        for (Garage g : lista) {
            if (g.getCodigo() == Codigo) {
                encontrado = true;
            }
        }
        return encontrado;
    }

    public List<Garage> leerGarage() {
        List<Garage> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(BBDDGarage.getARCHIVO()))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                lista.add(Garage.fromString(linea));
            }
        } catch (IOException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
        return lista;
    }

    public void actualizarGarage(int Codigo, Garage nuevo) {
        List<Garage> lista = leerGarage();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(BBDDGarage.getARCHIVO()))) {
            for (Garage g : lista) {
                if (g.getCodigo() == Codigo) {
                    bw.write(nuevo.toString());
                } else {
                    bw.write(g.toString());
                }
                bw.newLine();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String buscarPorCodigo(int Codigo) {
        String est = "Garage no encontrado...";
        List<Garage> lista = leerGarage();
        for (Garage g : lista) {
            if (g.getCodigo() == Codigo) {
                est = g.toString();
            }
        }
        return est;
    }

    public void DeshabilitarHabitacion(int Codigo) {
        List<Garage> lista = leerGarage();
        for (Garage g : lista) {
            if (g.getCodigo()== Codigo) {
                g.setEstado(false);
                break;
            }
        }

        // Se reescribir el archivo para actualizar los datos
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(BBDDGarage.getARCHIVO()))) {
            for (Garage g : lista) {
                bw.write(g.toString());
                bw.newLine();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
