package com.example.hazelcastclient;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.concurrent.BlockingQueue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author mojib.haider
 * @since 1/4/24
 */
public class TestContainerBase {

    private static final String HZ_IMAGE_NAME = "hazelcast/hazelcast:5.2.0-slim";

    private static final String HZ_CLUSTERNAME_ENV_NAME = "HZ_CLUSTERNAME";

    private static final String HZ_NETWORK_JOIN_AZURE_ENABLED_ENV_NAME = "HZ_NETWORK_JOIN_AZURE_ENABLED";

    private static final String HZ_NETWORK_JOIN_MULTICAST_ENABLED_ENV_NAME = "HZ_NETWORK_JOIN_MULTICAST_ENABLED";

    private static final int DEFAULT_EXPOSED_PORT = 5701;

    // Test values
    private static final String CLUSTER_STARTUP_LOG_MESSAGE_REGEX = ".*Members \\{size:2.*";

    private static final String HOST_PORT_SEPARATOR = ":";

    private static final String TEST_QUEUE_NAME = "test-queue";

    private static final String TEST_CLUSTER_NAME = "test-cluster";

    private static final String TEST_VALUE = "Hello!";

    private static final String FALSE_VALUE = "false";

    private static final String TRUE_VALUE = "true";

    @AfterEach
    void cleanUp() {
        HazelcastClient.shutdownAll();
    }

    static GenericContainer<?> container = new GenericContainer<>(DockerImageName.parse(HZ_IMAGE_NAME))
            .withExposedPorts(DEFAULT_EXPOSED_PORT);

    @BeforeAll
    public static void singleHazelcastContainer() {
            container.start();

            ClientConfig clientConfig = new ClientConfig();
            clientConfig
                    .getNetworkConfig()
                    .addAddress(container.getHost() + HOST_PORT_SEPARATOR + container.getFirstMappedPort());
    }
}
