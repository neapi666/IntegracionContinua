package net.itinajero.controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.itinajero.dao.DbConnection;
import net.itinajero.dao.UsuarioDao;
import net.itinajero.dao.VacanteDao;
import net.itinajero.model.Usuario;

public class AdminController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Recibimos el parametro action, el cual servira para saber que accion GET se ejecutara
        String action = request.getParameter("action");

        // Recuperamos la session activa que viene junto con el request
        HttpSession session = request.getSession();
        RequestDispatcher rd;
        String msg = "";
        /*
         Comparamos que accion viene en la peticion GET, es decir, que boton/opcion selecciono el usuario. 
         Para cada accion excepto para logout, primero verificamos que existe un usuario en la session, 
         si no existe lo mandamos al formulario de login (de esta forma se maneja la seguridad de la app)
        */
        switch (action) {
            case "login":
                // Aqui no existe todavia una sesion para el usuario, lo mandamos al form de login
                if (session.getAttribute("usuario") == null) {
                    request.setAttribute("message", msg);
                    rd = request.getRequestDispatcher("/login.jsp");
                    rd.forward(request, response);
                } else { // Ya esta logueado, lo mandamos al index.jsp, pero de la administracion
                    rd = request.getRequestDispatcher("/admin.jsp");
                    rd.forward(request, response);
                }
                break;
            case "crear":
                if (session.getAttribute("usuario") == null) {
                    msg = "Acceso Denegado.";
                    request.setAttribute("message", msg);
                    rd = request.getRequestDispatcher("/login.jsp");
                    rd.forward(request, response);
                } else {
                    rd = request.getRequestDispatcher("/frmvacante.jsp");
                    rd.forward(request, response);
                }
                break;
            case "eliminar":
                if (session.getAttribute("usuario") == null) {
                    msg = "Acceso Denegado.";
                    request.setAttribute("message", msg);
                    rd = request.getRequestDispatcher("/login.jsp");
                    rd.forward(request, response);
                } else {
                    this.eliminarVacante(request, response);
                }
                break;
            /* 
            Cuando es logout (GET), cerramos la session. Aqui se puede comprobar en el administrador
            de aplicaciones de tomcat, como la session es destruida.
            */
            case "logout":
                session.invalidate();
                response.sendRedirect(request.getContextPath() + "/homepage");
                break;
        }

    }

    /**
     * Metodo que valida el usuario y password
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Recibimos parametros del formulario de login
        String userParam = request.getParameter("user");
        String passParam = request.getParameter("pass");
        String msg = "";
        // Recuperamos una instancia del objeto HttpSession
        HttpSession session = request.getSession();

        DbConnection conn = new DbConnection();
        UsuarioDao usuarioDao = new UsuarioDao(conn);
        // Verificamos en la BD, si es un usuario correcto.
        Usuario usuario = usuarioDao.login(userParam, passParam);
        conn.disconnect();

        RequestDispatcher rd;
        if (usuario.getId() > 0) {
            // Creamos una variable de session, con el registro de usuario (Bean)
            // Verificar en el administrador de aplicaciones de tomcat.
            session.setAttribute("usuario", usuario);
            rd = request.getRequestDispatcher("/admin.jsp");
            rd.forward(request, response);

        } else {
            msg = "Usuario y/o password incorrectos";
            request.setAttribute("message", msg);
            rd = request.getRequestDispatcher("/login.jsp");
            rd.forward(request, response);
        }
    }
    /**
     * Metodo para eliminar una vacante.
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    private void eliminarVacante(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Recibimos el id de la vacante que vamos a eliminar
        int idVacanteParam = Integer.parseInt(request.getParameter("idVacante"));
        DbConnection conn = new DbConnection();
        VacanteDao vacanteDao = new VacanteDao(conn);
        int respuesta = vacanteDao.delete(idVacanteParam);
        String msg = "";
        if (respuesta == 1) { // Fue afectado un registro, esto significa que si se borro
            msg = "La vacante fue eliminada correctamente.";
        } else {
            msg = "Ocurrio un error. La vacante no fue eliminada.";
        }
        conn.disconnect();
        request.setAttribute("message", msg);
        RequestDispatcher rd;
        rd = request.getRequestDispatcher("/mensaje.jsp");
        rd.forward(request, response);
    }

}
