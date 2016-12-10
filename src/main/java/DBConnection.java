import java.sql.*;

public class DBConnection {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://localhost:5432/Restaurant";

    //  Database credentials
    static final String USER = "postgres";
    static final String PASS = "31388";

    public static void main(String[] args) {
//        getSotrudnik();
//        createSkladTable();

    }

    public static Connection getDBConnection() {
        Connection dbConnection = null;
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            dbConnection = DriverManager.getConnection(DB_URL, USER, PASS);
            return dbConnection;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return dbConnection;
    }

    public static void createSkladTable(){
        Connection conn = null;
        Statement stmt = null;

        String createTableSQL = "CREATE TABLE public.sklad\n" +
                "(\n" +
                "    ingradient integer NOT NULL,\n" +
                "    kolichestvo integer NOT NULL,\n" +
                "    CONSTRAINT \"Sklad_pkey\" PRIMARY KEY (ingradient),\n" +
                "    CONSTRAINT \"Sklad_ingradient\" FOREIGN KEY (ingradient)\n" +
                "        REFERENCES public.ingradient (\"idIngradient\") MATCH SIMPLE\n" +
                "        ON UPDATE NO ACTION\n" +
                "        ON DELETE NO ACTION\n" +
                ")\n" +
                "WITH (\n" +
                "    OIDS = FALSE\n" +
                ")\n" +
                "TABLESPACE pg_default;\n" +
                "\n" +
                "ALTER TABLE public.sklad\n" +
                "    OWNER to postgres;";

        try {
            conn = getDBConnection();
            stmt = conn.createStatement();

            stmt.execute(createTableSQL);
            System.out.println("Table Sklad is created!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void getSotrudnik() {
        Connection conn = null;
        Statement stmt = null;
        try {
            //STEP 2: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT * FROM sotrudnik";
            ResultSet rs = stmt.executeQuery(sql);

            //STEP 5: Extract data from result set
            while (rs.next()) {
                //Retrieve by column name
                int id = rs.getInt("idsotrudnik");
                String familiya = rs.getString("familiya");
                String imya = rs.getString("imya");

                //Display values
                System.out.print("ID Sotrudnik: " + id);
                System.out.print(", familiya: " + familiya);
                System.out.println(", imya: " + imya);
            }
            //STEP 6: Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
    }

}