package dev.vitorvidal.petmanagementapi.adapter;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.config.DefaultDriverOption;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.cassandra.DriverConfigLoaderBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.CqlSessionFactoryBean;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.config.SessionFactoryFactoryBean;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.convert.CassandraConverter;
import org.springframework.data.cassandra.core.convert.MappingCassandraConverter;
import org.springframework.data.cassandra.core.cql.CqlTemplate;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.core.mapping.CassandraMappingContext;
import org.springframework.data.cassandra.core.mapping.SimpleUserTypeResolver;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import java.util.List;

@Configuration
@EnableCassandraRepositories(basePackages = "dev.vitorvidal.petmanagementapi.infrastrucutre.repository")
public class CassandraPetAdapter {
    @Value("${cassandra.schema-action}")
    private String schemaAction;
    @Value("${cassandra.local-datacenter}")
    private String localDatacenter;
    @Value("${cassandra.contact-points}")
    private String contactPoints;
    @Value("${cassandra.pet.keyspace-name}")
    private String keyspaceName;
    @Value("${cassandra.pet.keyspace-port}")
    private Integer keyspacePort;

    @Bean("petSession")
    public CqlSessionFactoryBean session() {
        CqlSessionFactoryBean session = new CqlSessionFactoryBean();
        session.setContactPoints(contactPoints);
        session.setKeyspaceName(keyspaceName);
        session.setKeyspaceCreations(keyspaceActionSpecification());
        session.setLocalDatacenter(localDatacenter);
        session.setPort(keyspacePort);
        return session;
    }

    @Bean("petCassandraTemplate")
    public CassandraTemplate cassandraTemplate(CqlSession session, CassandraConverter converter) {
        return new CassandraTemplate(session, converter);
    }

    public List<CreateKeyspaceSpecification> keyspaceActionSpecification() {
        return List.of(CreateKeyspaceSpecification.createKeyspace(keyspaceName)
                .ifNotExists()
                .withSimpleReplication(1));
    }

    @Bean("petDriverConfigLoaderBuilderCustomizer")
    public DriverConfigLoaderBuilderCustomizer defaultProfile() {
        return builder -> builder.withString(DefaultDriverOption.METADATA_SCHEMA_REQUEST_TIMEOUT, "30 seconds").build();
    }

    @Bean("petSessionFactory")
    public SessionFactoryFactoryBean sessionFactory(CqlSession session, CassandraConverter converter) {
        SessionFactoryFactoryBean sessionFactory = new SessionFactoryFactoryBean();
        sessionFactory.setSession(session);
        sessionFactory.setConverter(converter);
        sessionFactory.setSchemaAction(SchemaAction.valueOf(schemaAction.toUpperCase()));
        return sessionFactory;
    }

    @Bean("petMappingContext")
    public CassandraMappingContext mappingContext(CqlSession cqlSession) {
        CassandraMappingContext mappingContext = new CassandraMappingContext();
        mappingContext.setUserTypeResolver(new SimpleUserTypeResolver(cqlSession));
        return mappingContext;
    }

    @Bean("petConverter")
    public CassandraConverter converter(CassandraMappingContext mappingContext) {
        return new MappingCassandraConverter(mappingContext);
    }

    @Bean("petCqlTemplate")
    public CqlTemplate cqlTemplate(CqlSession session) {
        return new CqlTemplate(session);
    }

    public CqlTemplate getCqlTemplate() {
        return cqlTemplate(session().getObject());
    }
}
