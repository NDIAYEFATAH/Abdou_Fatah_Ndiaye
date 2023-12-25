package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DB {
    private Connection cnx;
    private PreparedStatement pstm;
    private ResultSet rs;
    private int ok;

    public DB() {
    }

    private void getConnection()
    {
        String host = "localhost";
        String database = "utilisateur";
        int port = 3306;
        String url = "jdbc:mysql://" + host + ":" + port + "/" + database;
        String user = "root";
        String password = "";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.cnx = DriverManager.getConnection(url, user, password);
            System.out.println("Connexion Reussie ");
        } catch (Exception var8) {
            var8.printStackTrace();
        }
    }
    public void initPrepar(String sql) {
        try {
            this.getConnection();
            this.pstm = this.cnx.prepareStatement(sql);
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }
    public ResultSet executeSelect() {
        this.rs = null;

        try {
            this.rs = this.pstm.executeQuery();
        } catch (Exception var2) {
            var2.printStackTrace();
        }

        return this.rs;
    }
    public int executeMaj() {
        try {
            this.ok = this.pstm.executeUpdate();
        } catch (Exception var2) {
            var2.printStackTrace();
        }

        return this.ok;
    }
    public void clossConnection() {
        try {
            if (this.cnx != null) {
                this.cnx.close();
            }
        } catch (Exception var2) {
            var2.printStackTrace();
        }

    }

    public PreparedStatement getPstm() {
        return this.pstm;
    }
}
