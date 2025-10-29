package guarderiaCentral.backend.controllers;


import guarderiaCentral.Conections.BBDDSocio;
import guarderiaCentral.backend.Models.Socio;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SocioDAO {
    
    public int ultimoID() {
        int ultimoID;

        List<Socio> lista = leerSocio();
        if(!lista.isEmpty()){
        ultimoID = lista.get(lista.size()-1).getID();
        }else {
            ultimoID=0;
        }
        return ultimoID+1;
    }
    
     public void crearSocio(int dni, String Nombre) {
         
         boolean activo = true;//Al crearlo ya lo dejamos activado
         Socio s = new Socio(ultimoID(), dni, Nombre,activo);

        if (!existe(s.getID())) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(BBDDSocio.getARCHIVO(), true))) {
                bw.write(s.toString());
                bw.newLine();
            } catch (IOException ex) {
                System.out.println("ERROR: " + ex.getMessage());
            } finally {
                System.out.println("Socio agregado...");
            }
        } else {
                System.out.println("ERROR: Socio ya registrado");
        }
    }

    public boolean existe(int id) {
        boolean encontrado = false;
        List<Socio> lista = leerSocio();
        for (Socio s : lista) {
            if (s.getID() == id) {
                encontrado = true;
            }
        }
        return encontrado;
    }
    
    public int obtenerID (String nombreSocio){
        List<Socio> lista = leerSocio();
        try (BufferedReader br = new BufferedReader(new FileReader(BBDDSocio.getARCHIVO()))) {
        String linea;
        while ((linea = br.readLine()) != null) {
            String[] partes = linea.split(","); // Funcion para buscar por posición
            int id = Integer.parseInt(partes[0]); //En posición 0 de la lista estan los id guardados
            String nombre = partes[2].trim(); // en la posicion 1 del arreglo se encuentra el nombre
            if (nombreSocio.equalsIgnoreCase(nombre.trim())) {
                return id;
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    return -1; // No encontrado
    }
    
    public boolean existeSocio(String nombreSocio) {
        boolean encontrado = false;
        List<Socio> lista = leerSocio();
        for (Socio s : lista) {
            if (s.getNombre().equals(nombreSocio)) {
                encontrado = true;
            }
        }
        return encontrado;
    }

    public List<Socio> leerSocio() {
        List<Socio> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(BBDDSocio.getARCHIVO()))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                lista.add(Socio.fromString(linea));
            }
        } catch (IOException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
        return lista;
    }

    public void actualizarSocio(int id, Socio nuevo) {
        List<Socio> lista = leerSocio();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(BBDDSocio.getARCHIVO()))) {
            for (Socio s : lista) {
                if (s.getID() == id) {
                    bw.write(nuevo.toString());
                } else {
                    bw.write(s.toString());
                }
                bw.newLine();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String buscarPorId(int id) {
        String est = "Socio no encontrado...";
        List<Socio> lista = leerSocio();
        for (Socio s : lista) {
            if (s.getID() == id) {
                est = s.toString();
            }
        }
        return est;
    }

    public void eliminarSocio(int id) {
        List<Socio> lista = leerSocio();
        for (Socio s : lista) {
            if (s.getID() == id) {
                s.setActivo(false);
                break;
            }
        }

        // Se reescribir el archivo para actualizar los datos
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(BBDDSocio.getARCHIVO()))) {
            for (Socio s : lista) {
                bw.write(s.toString()); 
                bw.newLine();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
