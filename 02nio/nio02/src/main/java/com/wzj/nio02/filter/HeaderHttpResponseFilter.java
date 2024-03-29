package com.wzj.nio02.filter;

import io.netty.handler.codec.http.FullHttpResponse;

/**
 * @author wangzhijie
 */
public class HeaderHttpResponseFilter implements HttpResponseFilter {

    @Override
    public void filter(FullHttpResponse response) {
        response.headers().set("kk", "java-1-nio");
    }
}
