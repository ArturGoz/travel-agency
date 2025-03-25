package com.epam.finaltask.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.util.*;

public class CustomHeaderRequestWrapper extends HttpServletRequestWrapper {
    private final Map<String, String> headers = new HashMap<>();

    public CustomHeaderRequestWrapper(HttpServletRequest request, String username) {
        super(request);
        headers.put("X-User-Name", username);
    }

    @Override
    public String getHeader(String name) {
        return headers.getOrDefault(name, super.getHeader(name));
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        return headers.containsKey(name)
                ? Collections.enumeration(Collections.singletonList(headers.get(name)))
                : super.getHeaders(name);
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        Set<String> names = new LinkedHashSet<>(headers.keySet());
        Collections.list(super.getHeaderNames()).forEach(names::add);
        return Collections.enumeration(names);
    }
}

