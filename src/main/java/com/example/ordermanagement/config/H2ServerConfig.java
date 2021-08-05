package com.example.ordermanagement.config;

import org.h2.tools.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;

@Configuration
public class H2ServerConfig {

    // initMethod = Bean 초기화 , destroyMethod = 어플리케이션 종료시
    @Bean(initMethod = "start",destroyMethod = "stop")
    public Server H2DatabaseServer() throws SQLException{
        return Server.createTcpServer("-tcp","-tcpAllowOthers", "-tcpPort", "9092");
        // 지원되는 옵션은 -tcpPort, -tcpSSL, -tcpPassword, -tcpAllowOthers, -tcpDaemon, -trace, -ifExists, -ifNotExists, -baseDir, -key
    }
}
