package net.itinajero.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.itinajero.dao.DbConnection;
import net.itinajero.dao.VacanteDao;
import net.itinajero.model.Vacante;

public class VacanteController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Recibimos el parametro de accion, para ver que solicito el cliente.
        String action = request.getParameter("action");
        if ("ver".equals(action)) {
            this.verDetalle(request, response);
        } else if ("lista".equals(action)) {
            this.verTodas(request, response);
        } 
    }

    /**
     * 1. Metodo que sirve para guardar una vacante.
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Recibimos parametros del formulario
        Vacante vacante = new Vacante(0);        
        String nombreParam = request.getParameter("nombre");
        vacante.setNombre(nombreParam);
        String descripcionParam = request.getParameter("descripcion");
        vacante.setDescripcion(descripcionParam);
        String detalleParam = request.getParameter("detalle");
        
        // Procesamos los datos. Guardar en BD
        DbConnection conn = new DbConnection();
        vacante.setDetalle(detalleParam);
        VacanteDao vacanteDao = new VacanteDao(conn);
        boolean status = vacanteDao.insert(vacante);
        
        // Preparamos un mensaje para el usuario
        String msg = "";
        if (status) {
            msg = "La vacante fue guardada correctamente.";
        } else {
            msg = "Ocurrio un error. La vacante no fue guardada.";
        }
        conn.disconnect();        
        RequestDispatcher rd;
        // Compartimos la variable msg, para poder accederla desde la vista con Expression Language
        request.setAttribute("message", msg);
        // Enviarmos respuesta. Renderizamos la vista mensaje.jsp
        rd = request.getRequestDispatcher("/mensaje.jsp");
        rd.forward(request, response);
    }

    /**
     * 2. Metodo para ver los detalles completos de una Vacante.
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void verDetalle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {        
        
        // Recibimos id de la vacante a consultar
        int idVacante = Integer.parseInt(request.getParameter("id"));                
        DbConnection conn = new DbConnection();
        VacanteDao vacanteDao = new VacanteDao(conn);
        Vacante vacante = vacanteDao.getById(idVacante);
        conn.disconnect();        
        
        // Compartimos la variable msg, para poder accederla desde la vista con Expression Language
        request.setAttribute("vacante", vacante);
        RequestDispatcher rd;
        
        // Enviarmos respuesta. Renderizamos la vista detalle.jsp
        rd = request.getRequestDispatcher("/detalle.jsp");
        rd.forward(request, response);
    }

    /**
     * 3. Metodo para buscar todas las vacantes registradas.
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void verTodas(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {                
        DbConnection conn = new DbConnection();
        VacanteDao vacanteDao = new VacanteDao(conn);
        List<Vacante> lista = vacanteDao.getAll();
        conn.disconnect();
        // Compartimos la variable lista, para poder accederla desde la vista
        request.setAttribute("vacantes", lista);
        RequestDispatcher rd;
        rd = request.getRequestDispatcher("/vacantes.jsp");
        rd.forward(request, response);
    }

}
