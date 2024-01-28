package com.example.hazelcastclient;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author mojib.haider
 * @since 1/4/24
 */
@Component
public class HazelcastDemo {

    private String cacheMap = "test-map";

    @Autowired
    private HazelcastInstance hazelcastInstance;

    public void createDummyMap() {
        IMap<String, Integer> cachedMap = hazelcastInstance.getMap(cacheMap);
        cachedMap.put("1", 1);
    }
}
