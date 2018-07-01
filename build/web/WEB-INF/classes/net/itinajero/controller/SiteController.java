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

public class SiteController extends HttpServlet {
    /**
     * Controller que sirve para mostrar la página principal de la aplicacion. Se encarga de mandar al index.jsp
     * un objeto de tipo List con las 3 ultimas vacantes
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher rd;        
        DbConnection conn = new DbConnection();
        if(conn==null){
            System.out.println("No se pudo conectar a la base de datos");
        }
        VacanteDao vacanteDao = new VacanteDao(conn);
        if(vacanteDao==null){
            System.out.println(vacanteDao);
            System.out.println("vacanteDao no funciona");
        }
        System.out.print(vacanteDao);
        try{
        List<Vacante> lista = vacanteDao.getUltimas();
               conn.disconnect();
        request.setAttribute("ultimas", lista);
        rd = request.getRequestDispatcher("/index.jsp");
        rd.forward(request, response);
        
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            System.out.println("Error al traer datos");
        }
 
    }

}
