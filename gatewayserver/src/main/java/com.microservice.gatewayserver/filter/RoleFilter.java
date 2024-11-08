package com.microservice.gatewayserver.filter;

import com.microservice.gatewayserver.exception.RoleMismatchExceptionHandler;
import com.microservice.gatewayserver.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class RoleFilter extends AbstractGatewayFilterFactory<RoleFilter.Config> {

    @Autowired
    private RouteValidator validator;

    @Autowired
    private JwtUtil jwtUtil;

    public RoleFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if (validator.isSecured.test(exchange.getRequest())) {
                // Check if the header contains the Authorization token
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new RuntimeException("Missing Authorization Header");
                }

                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);

                // Extract the token by removing the "Bearer " prefix
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }

                try {
                    // Validate the token
                    jwtUtil.validateToken(authHeader);

                    // Check if the user has the ADMIN role
                    if (!jwtUtil.hasRole(authHeader, "ADMIN")) {
                        throw new RoleMismatchExceptionHandler("Access Denied: Insufficient permissions");
                    }
                }catch (Exception e) {
                    throw new RuntimeException("Unauthorized access to application");
                }
            }
            return chain.filter(exchange);
        });
    }

    public static class Config {
        private String role;

        public String getRequiredRole() {
            return role;
        }

        public void setRequiredRole(String requiredRole) {
            this.role = role;
        }
    }
}
