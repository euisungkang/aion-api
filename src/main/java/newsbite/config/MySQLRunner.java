package newsbite.config;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
@RequiredArgsConstructor
public class MySQLRunner implements ApplicationRunner {

    private final Logger logger = LoggerFactory.getLogger(MySQLRunner.class);

    private final DataSource dataSource;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        try (Connection connection = dataSource.getConnection()) {
            logger.info(connection.toString());
            String URL = connection.getMetaData().getURL();
            String User = connection.getMetaData().getUserName();
        } catch (SQLException ex) {
            throw new RuntimeException("Error connecting to MySQL database.", ex);
        }
    }
}
