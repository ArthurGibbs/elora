package Elora.DAO;

import Elora.DBClient;
import Elora.Elora;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class Data {
    DBClient dbClient;

    public Data(DBClient dbClient) {
        this.dbClient = dbClient;
    }

    public void saveOPV(String object, String prop, String value) {
        Optional<String> exists = lookupOPV(object, prop);
        if (exists.isPresent()) {
            update(object, prop, value);
        }else {
            String sqlSelectAllPersons = String.format("INSERT INTO bot.data (object, prop, `value`)\n" +
                    "VALUES (\"%s\", \"%s\", \"%s\");", object, prop, value);
            this.dbClient.update(sqlSelectAllPersons);
        }
    }

    public Optional<String> lookupOPV(String object, String prop) {
        String sqlSelectAllPersons = String.format("SELECT value FROM bot.data where object=\"%s\" and prop=\"%s\" ",object, prop);
        ResultSet rs = this.dbClient.query(sqlSelectAllPersons);
        try {
            while (rs.next()) {
                return Optional.of(rs.getString("value"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Optional.empty();
    }

    public void update(String object, String prop, String value) {
        String sqlSelectAllPersons = String.format("UPDATE bot.data SET `value`='%s' WHERE object ='%s' AND prop='%s'", value, object, prop);
        this.dbClient.update(sqlSelectAllPersons);
    }
}
