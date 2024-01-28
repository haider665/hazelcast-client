package com.example.hazelcastclient;

import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
class HazelcastClientApplicationTests {

    private String cacheMap = "test-map";

    @Autowired
    private HazelcastDemo hazelcastDemo;

    @Autowired
    private HazelcastInstance hazelcastInstance;

    static GenericContainer<?> container =
            new GenericContainer<>(DockerImageName.parse("hazelcast/hazelcast:latest"))
            .withExposedPorts(5701);

    @BeforeAll
    public static void singleHazelcastContainer() {
        container.start();

        ClientConfig clientConfig = new ClientConfig();
        clientConfig
                .getNetworkConfig()
                .addAddress(container.getHost() + ":" + container.getFirstMappedPort());
    }

    @BeforeEach
    void cleanUp() {
        hazelcastInstance.getMap(cacheMap).clear();
    }

    @Test
    void cacheInitTest() {
        hazelcastDemo.createDummyMap();

        assertFalse(hazelcastInstance.getMap(cacheMap).isEmpty());
    }

}
