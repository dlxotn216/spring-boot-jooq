package me.taesu.springjooq.base.config;

import org.h2.tools.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;

/**
 * Created by itaesu on 2020/12/30.
 *
 * @author Lee Tae Su
 * @version 1.2.0
 * @since 1.2.0
 */
@Configuration
public class AppConfig {
    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server inMemoryH2DatabaseaServer() throws SQLException {
        return Server.createTcpServer(
                "-tcp", "-tcpAllowOthers", "-tcpPort", "9090");
    }
}
