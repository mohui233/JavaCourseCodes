package com.wzj.nio02.filter;

import io.netty.handler.codec.http.FullHttpResponse;

/**
 * @author wangzhijie
 */
public interface HttpResponseFilter {

    void filter(FullHttpResponse response);

}
