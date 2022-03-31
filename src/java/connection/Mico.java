package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Mico {
    public Mico()
    {
    }
    public Connection  connect() throws Exception
    {   
        Connection con = null;

        try
        {   
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection("jdbc:postgresql://ec2-52-21-136-176.compute-1.amazonaws.com:5432/de3fq245pphhr0", "ffxfmytgnmyajs", "405c7cc49e77c4d70702696ecbfba824e0d6a04a92b8d3789b4cd1626c0d4866");

        }
        catch(ClassNotFoundException | SQLException e1)
        {
            e1.printStackTrace();
        }
        return con;
    }
}
