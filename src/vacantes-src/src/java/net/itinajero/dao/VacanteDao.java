package net.itinajero.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import net.itinajero.model.Vacante;

public class VacanteDao {

    private DbConnection conn;

    public VacanteDao(DbConnection conn) {
        this.conn = conn;
    }

    /**
     * 1. Metodo para insertar un registro en la tabla Vacante
     *
     * @param vacante
     * @return Regresa el id generado por la base de datos
     * @throws Exception
     */
    public boolean insert(Vacante vacante) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            String sql = "insert into Vacante values (?,?,?,?,?)";
            PreparedStatement preparedStatement = conn.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, vacante.getId());
            preparedStatement.setString(2, format.format(vacante.getFechaPublicacion()));
            preparedStatement.setString(3, vacante.getNombre());
            preparedStatement.setString(4, vacante.getDescripcion());
            preparedStatement.setString(5, vacante.getDetalle());
            preparedStatement.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error VacanteDao.insert: " + e.getMessage());
            return false;
        }
    }

    /**
     * 2. Metodo que regresa una lista con las 3 ultimas vacantes que seran
     * mostradas en la pagina principal
     *
     * @return
     * @throws Exception
     */
    public List<Vacante> getUltimas() {

        try {
            String sql = "select * from Vacante order by id desc limit 3";
            PreparedStatement preparedStatement = conn.getConnection().prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            List<Vacante> list = new LinkedList<>();
            Vacante vacante;
            while (rs.next()) {
                vacante = new Vacante(rs.getInt("id"));
                vacante.setFechaPublicacion(rs.getDate("fechaPublicacion"));
                vacante.setNombre(rs.getString("nombre"));
                vacante.setDescripcion(rs.getString("descripcion"));
                vacante.setDetalle(rs.getString("detalle"));
                // Add vacante object to the list
                list.add(vacante);
            }
            return list;

        } catch (SQLException e) {            
            System.out.println("Error VacanteDao.getUltimas: " + e.getMessage());
            return null;
        }
    }

    /**
     * 3. Metodo para buscar en la base de datos un registro de Vacante por
     * medio del id
     *
     * @param idVacante
     * @return Objeto de tipo vacante. Si no lo encuentra, regresa null
     * @throws Exception
     */
    public Vacante getById(int idVacante){
        try {
            String sql = "select * from Vacante where id=? limit 1";
            PreparedStatement preparedStatement = conn.getConnection()
                    .prepareStatement(sql);
            preparedStatement.setInt(1, idVacante); // Set idVacante
            ResultSet rs = preparedStatement.executeQuery();
            Vacante vacante = new Vacante(0);
            while (rs.next()) {
                // Create an object for the movie
                vacante.setId(rs.getInt("id"));
                vacante.setFechaPublicacion(rs.getDate("fechaPublicacion"));
                vacante.setNombre(rs.getString("nombre"));
                vacante.setDescripcion(rs.getString("descripcion"));
                vacante.setDetalle(rs.getString("detalle"));
            }
            return vacante;

        } catch (SQLException e) {            
            System.out.println("Error VacanteDao.getById: " + e.getMessage());
            return null;
        }
    }

    /**
     * 4. Metodo que regresa una lista con todas las vacantes.
     *
     * @return Lista de todos los objetos de vacantes
     * @throws Exception
     */
    public List<Vacante> getAll(){

        try {
            String sql = "select * from Vacante order by id desc";
            PreparedStatement preparedStatement = conn.getConnection().prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            List<Vacante> list = new LinkedList<>();
            Vacante vacante;
            while (rs.next()) {
                vacante = new Vacante(rs.getInt("id"));
                vacante.setFechaPublicacion(rs.getDate("fechaPublicacion"));
                vacante.setNombre(rs.getString("nombre"));
                vacante.setDescripcion(rs.getString("descripcion"));
                vacante.setDetalle(rs.getString("detalle"));       
                // Add vacante object to the list
                list.add(vacante);
            }
            return list;

        } catch (SQLException e) {            
            System.out.println("Error VacanteDao.getAll: " + e.getMessage());
            return null;
        }
    }

    /**
     * 5. Metodo para hacer busqueda de vacantes (la busqueda se hace por
     * descripcion y nombreVacante)
     *
     * @param query
     * @return Lista de todos los objetos de vacantes que fueron encontrados
     * @throws Exception
     */
    public List<Vacante> getByQuery(String query){

        try {
            String sql = "select * from Vacante where (descripcion like ? or nombre like ?) order by id desc";
            PreparedStatement preparedStatement = conn.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, "%" + query + "%");
            preparedStatement.setString(2, "%" + query + "%");
            ResultSet rs = preparedStatement.executeQuery();
            List<Vacante> list = new LinkedList<>();
            Vacante vacante;
            while (rs.next()) {
                vacante = new Vacante(rs.getInt("id"));
                vacante.setFechaPublicacion(rs.getDate("fechaPublicacion"));
                vacante.setNombre(rs.getString("nombre"));
                vacante.setDescripcion(rs.getString("descripcion"));
                vacante.setDetalle(rs.getString("detalle"));                
                // Add vacante object to the list
                list.add(vacante);
            }
            return list;

        } catch (SQLException e) {            
            System.out.println("Error VacanteDao.getByQuery: " + e.getMessage());
            return null;
        }
    }
    /**
     * 6. Metodo para eliminar una vacante.
     * @param idVacante
     * @return No. de filas afectadas
     */
    public int delete(int idVacante) {
        try {
            String sql = "delete from Vacante where id=?";
            PreparedStatement preparedStatement = conn.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, idVacante);
            int rows = preparedStatement.executeUpdate();
            return rows;

        } catch (SQLException e) {            
            System.out.println("Error VacanteDao.eliminar: " + e.getMessage());
            return 0;
        }
    }

}
