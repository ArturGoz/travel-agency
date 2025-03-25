package com.epam.finaltask.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;

@Data
@Slf4j
public class RemoteResponse {
    private boolean succeeded;
    private String statusCode;
    private String statusMessage;
    private List<?> results;

    public static RemoteResponse create(boolean succeeded, String statusCode, String statusMessage, List<?> additionalElements) {
        RemoteResponse response = new RemoteResponse();
        response.setSucceeded(succeeded);
        response.setStatusCode(statusCode);
        response.setStatusMessage(statusMessage);
        response.setResults(additionalElements);
        log.info("Response details: succeeded={}, statusCode={}, statusMessage={}, results={}",
                succeeded, statusCode, statusMessage, additionalElements);
        return response;
    }
}
