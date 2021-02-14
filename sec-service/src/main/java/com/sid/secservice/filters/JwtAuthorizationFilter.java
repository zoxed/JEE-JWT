package com.sid.secservice.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.sid.secservice.JWTUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest HSRequest, HttpServletResponse HSResponse, FilterChain FChain) throws ServletException, IOException {
        if (HSRequest.getServletPath().equals("/refreshToken"))
        {
            FChain.doFilter(HSRequest,HSResponse);
        }
        else
        {
            String authorizationToken = HSRequest.getHeader(JWTUtil.AUTH_HEADER);
            if (authorizationToken != null && authorizationToken.startsWith(JWTUtil.PREFIX)) {
                try
                {
                    String jwt = authorizationToken.substring(JWTUtil.PREFIX.length());
                    Algorithm xx = Algorithm.HMAC256(JWTUtil.SECRET);
                    JWTVerifier jwtVerifier = JWT.require(xx).build();
                    DecodedJWT decodedJWT = jwtVerifier.verify(jwt);
                    //recuperer la session d'utilisateur
                    String username = decodedJWT.getSubject();
                    String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
                    //l'inversion liste des roles en une collection de granted authorities
                    Collection<GrantedAuthority> authorities = new ArrayList<>();
                    // implementer les roles dans la liste des roles ( ligne 40 )  avec grantedAuthority
                    for (String r:roles)
                    {
                        authorities.add(new SimpleGrantedAuthority(r));
                    }

                    UsernamePasswordAuthenticationToken authenticationToken = new
                            UsernamePasswordAuthenticationToken(username, null,authorities);

                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    FChain.doFilter(HSRequest, HSResponse);
                }
                catch (Exception e)
                {
                    HSResponse.setHeader("error-message", e.getMessage());
                    HSResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
                }

            }
            else
            {
                FChain.doFilter(HSRequest, HSResponse);
            }
        }


    }
}