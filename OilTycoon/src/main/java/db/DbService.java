package db;

import domains.Equipment;
import domains.Well;
import org.sqlite.JDBC;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbService {
    private final String SQL_INSERT_WELL = "INSERT INTO main.wells(name) VALUES (";
    private final String SQL_QUERY_GET_ALL_WELLS = "SELECT * FROM main.wells";
    private final String SQL_QUERY_FIND_WELL_BY_NAME = SQL_QUERY_GET_ALL_WELLS +  " WHERE name = ";
    private final String SQL_INSERT_EQUIP = "INSERT INTO main.equipment(name, well_id) VALUES (";
    private final String SQL_GET_ALL_EQUIP = "SELECT * FROM main.equipment";
    private final String SQL_QUERY_FIND_EQUIP_BY_ID = SQL_GET_ALL_EQUIP + " WHERE id = ";
    private final String SQL_QUERY_CNT_ALL_EQUIP_FOR_WELL = "SELECT COUNT(*) FROM main.equipment WHERE well_id = ";
    private final String SQL_QUERY_GET_ALL_EQUIP_FOR_WELL = "SELECT * FROM main.equipment WHERE well_id = ";

    private static final String CONN_URL = "jdbc:sqlite:/home/tinker/IdeaProjects/OilTycoon/tycoon.db";
//    private static final String CONN_USER = "test";
//    private static final String CONN_PASS = "test";

    private static DbService instance = null;
    private Connection connection = null;

    public static DbService getInstance(){
        if (instance == null) instance = new DbService();
        return instance;
    }

    private DbService(){
        try {
            DriverManager.registerDriver(new JDBC());
//            this.connection = DriverManager.getConnection(CONN_URL, CONN_USER, CONN_PASS);
            this.connection = DriverManager.getConnection(CONN_URL);
            initSchema();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initSchema() {
        try(Statement stmt = connection.createStatement()){
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS wells (" +
                    "id      INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name    VARCHAR (32) NOT NULL UNIQUE" +
            ");" +
            "CREATE TABLE IF NOT EXISTS equipment (" +
                    "id      INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name    VARCHAR (32) NOT NULL UNIQUE," +
                    "well_id INTEGER NOT NULL REFERENCES wells(id)" +
            ");");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public Well saveWell(Well domain) {
        Well result = findWellByName(domain.getName());
        if(result == null){
            try (Statement stmt = connection.createStatement()) {
                stmt.executeUpdate(SQL_INSERT_WELL +/* + domain.getId() + ",*/" '" + domain.getName() + "');");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        result = findWellByName(domain.getName());
        return result;
    }

    public Well findWellByName(String name) {
        Well result = null;
        try (Statement stmt = connection.createStatement()){
            ResultSet rs = stmt.executeQuery(SQL_QUERY_FIND_WELL_BY_NAME +"'"+ name + "'");
            if (rs.next()) {
                result = new Well();
                result.setId(rs.getInt("id"));
                result.setName(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<Well> getAllWells(){
        List<Well> result = new ArrayList<>();
        try (Statement stmt = connection.createStatement()){
            ResultSet rs = stmt.executeQuery(SQL_QUERY_GET_ALL_WELLS);
            while (rs.next()){
                Well well = new Well();
                well.setId(rs.getInt("id"));
                well.setName(rs.getString("name"));
                well.setEquipmentList(getEquipForWell(well.getId()));
                result.add(well);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Equipment saveEquip(Equipment equipment) {
        Equipment result = findEquipById(equipment.getId());
        if(result == null){
            try (Statement stmt = connection.createStatement()) {
                stmt.executeUpdate(SQL_INSERT_EQUIP  + " '"
                                                        + equipment.getName()+ "', "
                                                        +equipment.getWellId() + ");");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        result = findEquipById(equipment.getId());
        return result;
    }

    public Equipment findEquipById(int id) {
        Equipment result = null;
        try (Statement stmt = connection.createStatement()){
            ResultSet rs = stmt.executeQuery(SQL_QUERY_FIND_EQUIP_BY_ID + id);
            if (rs.next()) {
                result = new Equipment();
                result.setId(rs.getInt("id"));
                result.setName(rs.getString("name"));
                result.setWellId(rs.getInt("well_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public int findEquipAmountForWell(int id) {
        int result = 0;
        try (Statement stmt = connection.createStatement()){
            ResultSet rs = stmt.executeQuery(SQL_QUERY_CNT_ALL_EQUIP_FOR_WELL + id);
            if (rs.next()) {
                result = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<Equipment> getEquipForWell(int wellId){
        List<Equipment> result = new ArrayList<>();
        try (Statement stmt = connection.createStatement()){
            ResultSet rs = stmt.executeQuery(SQL_QUERY_GET_ALL_EQUIP_FOR_WELL + wellId);
            while (rs.next()){
                Equipment equip = new Equipment();
                equip.setId(rs.getInt("id"));
                equip.setName(rs.getString("name"));
                equip.setWellId(rs.getInt("well_id"));
                result.add(equip);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
