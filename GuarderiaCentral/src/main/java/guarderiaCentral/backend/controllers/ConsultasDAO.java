package guarderiaCentral.backend.controllers;

public class ConsultasDAO {

    SocioDAO socioDAO = new SocioDAO();
    EmpleadoDAO empleadoDAO = new EmpleadoDAO();
    GarageDAO garageDAO = new GarageDAO();
    AsignacionDAO AsignacionDAO = new AsignacionDAO();

    public void ConsultaSocioGarage(String nombreSocio) {
        if (socioDAO.existeSocio(nombreSocio)) {
            int ID_socio = socioDAO.obtenerID(nombreSocio);
            if (AsignacionDAO.socioYaAsignado(ID_socio)) {
                int asignacion_id = AsignacionDAO.asignacionIDxSocioId(ID_socio);
                int codigoGarage = AsignacionDAO.obtenerCodigoGarage(asignacion_id);

                System.out.print("\nNombre Socio: " + nombreSocio + "\nGarage ya ocupado:" + codigoGarage + "\n\n");

            }
        }
    }

    public void ConsultaSocioEmpleado(String nombreSocio) {
        if (socioDAO.existeSocio(nombreSocio)) {
            System.out.println("Entra al primer if de ConsultaSocioEmpleado");
            int ID_socio = socioDAO.obtenerID(nombreSocio);
            if (AsignacionDAO.socioYaAsignado(ID_socio)) {
                
            System.out.println("Entra al segundo if de ConsultasocioEmpleado");
                int asignacion_id = AsignacionDAO.asignacionIDxSocioId(ID_socio);
                int id_empleado = AsignacionDAO.obtenerIdEmpleado(asignacion_id);
                String nombreEmpleado = empleadoDAO.obtenerNombreEmpleado(id_empleado);

                System.out.print("\nNombre Socio: " + nombreSocio + "\nEmpleado que lo atendio:" + nombreEmpleado + "\n\n");

            }
        }
    }

    public void ConsultaEmpleadoSocio(int id_empleado) {
        if (empleadoDAO.existe(id_empleado)) {
            if (AsignacionDAO.empleadoYaAsignado(id_empleado)) {
                AsignacionDAO.vecesAsignadoEmpleado(id_empleado);
            } else {
                System.out.println("El empleado no antendio a ningun socio");
            }

        }
    }
    
}

