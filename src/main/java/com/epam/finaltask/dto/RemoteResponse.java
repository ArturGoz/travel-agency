package com.epam.finaltask.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RemoteResponse<T> {
    private boolean succeeded;
    private String statusCode;
    private String statusMessage;
    private List<T> results;

    public static <T> RemoteResponse<T> success(T data) {
        RemoteResponse<T> response = new RemoteResponse<>();
        response.setStatusCode("OK");
        response.setStatusMessage("User is successfully authenticated");
        response.setResults(Collections.singletonList(data));
        return response;
    }
    //TODO: write create method here

}
