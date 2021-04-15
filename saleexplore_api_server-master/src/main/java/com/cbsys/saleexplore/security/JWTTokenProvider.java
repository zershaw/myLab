package com.cbsys.saleexplore.security;


import com.cbsys.saleexplore.config.AppProperties;
import com.cbsys.saleexplore.payload.ApiResponsePd;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class JWTTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(JWTTokenProvider.class);

    @Autowired
    private AppProperties appProperties;


    /**
     * create a JWT token based on userID
     */
    public ApiResponsePd getAuthenticationResponse(long userId) {

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + appProperties.getAuth().getTokenExpirationMsec());

        // the subject is the userId
        String jwtToken = "Bearer " + Jwts.builder()
                .setSubject(userId + "")
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, appProperties.getAuth().getTokenSecret())
                .compact();

        JWTBearerToken bearerToken = new JWTBearerToken(jwtToken);

        ApiResponsePd apiResponse = new ApiResponsePd(true, bearerToken);

        return apiResponse;
    }

    /**
     * extract the JWT token from the Httprequest
     *
     * @param request httprequest from the user which need to have the token in the Authentication key in the header
     * @return the JWT token in string
     */
    public String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }


    /**
     * @param token submitted token by the user's request
     * @return the user's ID
     */
    public Long getUserIdFromToken(String token) {

        Claims claims = Jwts.parser()
                .setSigningKey(appProperties.getAuth().getTokenSecret())
                .parseClaimsJws(token)
                .getBody();

        // the subject is the userId
        return Long.parseLong(claims.getSubject());
    }


    /**
     * Validate the submitted token from user
     *
     * @param jwtToken a json-based token in string
     * @return errorMsg will be set with the error message
     */
    public boolean validateToken(String jwtToken) {
        try {

            Jwts.parser().setSigningKey(appProperties.getAuth().getTokenSecret()).parseClaimsJws(jwtToken);

            return true;
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");

        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
        }
        return false;
    }

}
