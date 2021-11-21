package com.wzj.nio02.router;

import java.util.List;
import java.util.Random;

/**
 * @author wangzhijie
 */
public class RandomHttpEndpointRouter implements HttpEndpointRouter {
    @Override
    public String route(List<String> urls) {
        int size = urls.size();
        Random random = new Random(System.currentTimeMillis());
        return urls.get(random.nextInt(size));
    }
}
