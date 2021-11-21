package com.wzj.nio02.router;

import java.util.List;

/**
 * @author wangzhijie
 */
public interface HttpEndpointRouter {

    String route(List<String> endpoints);

}
