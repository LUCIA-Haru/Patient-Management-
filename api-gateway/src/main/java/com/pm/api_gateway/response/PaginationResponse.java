package com.pm.api_gateway.response;

import java.time.LocalDateTime;
import java.util.List;

public class PaginationResponse<T>  extends ApiResponse<List<T>> {

    private Object metadata;

    public PaginationResponse(String status, int statusCode, String message, LocalDateTime timestamp,
                               List<T> data, Object metadata) {
        super(status, statusCode, message, timestamp,  data);
        this.metadata = metadata;
    }


    public Object getMetadata() {
        return metadata;
    }

    public void setMetadata(Object metadata) {
        this.metadata = metadata;
    }

}
