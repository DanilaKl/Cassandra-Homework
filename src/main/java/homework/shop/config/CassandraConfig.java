package homework.shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.CqlSessionFactoryBean;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.KeyspaceOption;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class CassandraConfig extends AbstractCassandraConfiguration {

    private static final String CONFIG_PATH = "classpath:cassandra.config";

    private String username;
    private String password;
    private final int port;
    private final String contactPoints;
    private final String keySpace;

    public CassandraConfig(ResourceLoader resourceLoader) {
        Map<String, String> configs = loadConfigs(resourceLoader);

        this.username = configs.get("user");
        this.password = configs.get("password");
        this.keySpace = configs.get("keyspace");
        this.contactPoints = configs.get("contactpoint");
        this.port = 9042;
    }

    @Override
    public String[] getEntityBasePackages() {
        return new String[]{ "homework.shop.model" };
    }

    @Override
    public SchemaAction getSchemaAction() {
        return SchemaAction.CREATE_IF_NOT_EXISTS;
    }

    @Override
    protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
        return Collections.singletonList(CreateKeyspaceSpecification
                .createKeyspace(getKeyspaceName())
                .ifNotExists()
                .with(KeyspaceOption.DURABLE_WRITES, true)
                .withSimpleReplication());
    }

    @Bean
    @Override
    public CqlSessionFactoryBean cassandraSession() {
        CqlSessionFactoryBean cassandraSession = super.cassandraSession();
        cassandraSession.setUsername(username);
        cassandraSession.setPassword(password);

        username = null;
        password = null;

        return cassandraSession;
    }

    @Override
    public String getContactPoints() {
        return contactPoints;
    }

    @Override
    protected String getKeyspaceName() {
        return keySpace;
    }

    @Override
    public int getPort() {
        return port;
    }

    private Map<String, String> loadConfigs(ResourceLoader resourceLoader) {
        Map<String, String> configs = new HashMap<>();

        try {
            Resource resource = resourceLoader.getResource(CONFIG_PATH);
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(resource.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    int keySeparatorIndex = line.indexOf(':');

                    if (keySeparatorIndex == -1) {
                        throw new RuntimeException("Failed to parse key in line: " + line);
                    }

                    String key = line.substring(0, keySeparatorIndex).strip().toLowerCase();
                    String value = line.substring(keySeparatorIndex + 1).strip();

                    configs.put(key, value);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load resource", e);
        }

        return configs;
    }
}
