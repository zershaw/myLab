package com.cbsys.saleexplore.compos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;


@Component
public class InMemoryTraceRepository implements HttpTraceRepository {

    private static final Logger HTTP_LOGGER = LoggerFactory.getLogger("http-logger");

    //     For security matters it's better to not expose Traces on HTTP
    @Override
    public List<HttpTrace> findAll() {
        return null;
    }

    @Override
    public void add(HttpTrace trace) {
        HttpTrace.Request request = trace.getRequest();

        StringBuilder traceInfo = new StringBuilder();
        if(request.getRemoteAddress() != null){
            traceInfo.append(request.getRemoteAddress());
            traceInfo.append(" ");
        }
        traceInfo.append(request.getUri().getPath());


        HTTP_LOGGER.info(traceInfo.toString());
    }
}
