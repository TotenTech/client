package repository.remote;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class RemoteDatabaseConnection {
    private JdbcTemplate conexaoDoBanco;

    public RemoteDatabaseConnection() {
        BasicDataSource dataSource = new BasicDataSource();

        dataSource.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        dataSource.setUrl("jdbc:sqlserver://52.203.195.192:1433;database=totemTech;encrypt=false;trustServerCertificate=true;");
        dataSource.setUsername("totemMaster");
        dataSource.setPassword("ComplexPassword@2024");

        this.conexaoDoBanco = new JdbcTemplate(dataSource);
    }

    public JdbcTemplate getConexaoDoBanco() {
        return conexaoDoBanco;
    }
}
