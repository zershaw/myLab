package com.cbsys.saleexplore.security;
/**
 * Authentication Request Filter will be called before accessing the APIs
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * a custom url filter, don't add @component here otherwise it will be picked by spring and added for all requests
 */
public class JWTAccessFilter implements Filter {

    private final JWTTokenProvider tokenProvider;

    private static final Logger logger = LoggerFactory.getLogger(JWTAccessFilter.class);


    public JWTAccessFilter(JWTTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
        logger.info("########## Initiating APIAccessFilter ########## \n\n");
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        try {

            String jwt = tokenProvider.getJwtFromRequest(request);

            // verify the jwt token
            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
                /*
                 * the jwt token is valid. we can set the authentication details to the spring security context
                 */
                Long userId = tokenProvider.getUserIdFromToken(jwt);

                CurrentUser currentUser = new CurrentUser();
                currentUser.setId(userId);

                // initialize the spring token based authentication
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(currentUser,
                        null, null);

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // put the authentication detail into the spring context,
                // THIS REQUEST IS AUTHENTICATED
                SecurityContextHolder.getContext().setAuthentication(authentication);

            }

        } catch (Exception ex) {
            logger.info(ex.toString());
        }

        // pass the request to spring-chain
        filterChain.doFilter(request, response);
    }

}
