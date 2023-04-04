package dev.vitorvidal.petmanagementapi.infrastrucutre.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.CqlSessionFactoryBean;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.convert.CassandraConverter;
import org.springframework.data.cassandra.core.convert.MappingCassandraConverter;
import org.springframework.data.cassandra.core.mapping.CassandraMappingContext;
import org.springframework.data.cassandra.repository.support.CassandraRepositoryFactory;

import java.util.Objects;

@Configuration
public class CassandraConfig {
    // https://docs.spring.io/spring-data/cassandra/docs/current/reference/html/#reference
    @Value("${cassandra.schema-action}")
    private String schemaAction;
    @Value("${cassandra.local-datacenter}")
    private String localDatacenter;
    @Value("${cassandra.contact-points}")
    private String contactPoints;
    @Value("${cassandra.keyspace-name}")
    private String keyspaceName;

    @Bean
    public CassandraMappingContext cassandraMapping() {
        return new CassandraMappingContext();
    }

    @Bean
    public CqlSessionFactoryBean session() {
        CqlSessionFactoryBean session = new CqlSessionFactoryBean();
        session.setContactPoints(contactPoints);
        session.setPort(9042);
        session.setKeyspaceName(keyspaceName);
        session.setSchemaAction(SchemaAction.valueOf(schemaAction.toUpperCase()));
        session.setLocalDatacenter(localDatacenter);

        return session;
    }

    @Bean
    public CassandraConverter converter() {
        return new MappingCassandraConverter(cassandraMapping());
    }


    @Bean
    public CassandraTemplate cassandraTemplate() {
        return new CassandraTemplate(Objects.requireNonNull(session().getObject()));
    }

    @Bean
    public CassandraOperations cassandraOperations() throws Exception {
        return cassandraTemplate();
    }

    @Bean
    public CassandraRepositoryFactory cassandraRepositoryFactory() throws Exception {
        return new CassandraRepositoryFactory(cassandraTemplate());
    }
}
