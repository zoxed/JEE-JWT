package com.sid.secservice.web;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sid.secservice.JWTUtil;
import com.sid.secservice.entities.AppRole;
import com.sid.secservice.entities.AppUser;
import com.sid.secservice.entities.RoleUserForm;
import com.sid.secservice.service.AccountService;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class AccountRestController  {
    private AccountService accountService;

    public AccountRestController(AccountService accountService)
    {
        this.accountService = accountService;
    }

    @GetMapping("/users")
        @PostAuthorize("hasAuthority('USER')")
    public List<AppUser> appUsers()
    {
        return accountService.listUsers();
    }
    @PostMapping("/users")
        @PostAuthorize("hasAuthority('ADMIN')")
    public AppUser saveUser(@RequestBody AppUser appUser){
        return accountService.addNewUser(appUser);
    }
    @PostMapping("/roles")
        @PostAuthorize(value = "hasAuthority('USER)')")
    public AppRole saveRole(@RequestBody AppRole appRole){
        return accountService.addNewRole(appRole);
    }
    @PostMapping("/role2user")
    public void addRole2User(@RequestBody RoleUserForm roleUserForm){
       accountService.addRoleToUser(roleUserForm.getUsername(),roleUserForm.getRolename());
    }
    @GetMapping(path = "/refreshToken")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws Exception{
     String authToken=request.getHeader(JWTUtil.AUTH_HEADER);
     if (authToken!=null && authToken.startsWith(JWTUtil.PREFIX)){
         try
         {
             String jwt = authToken.substring(JWTUtil.PREFIX.length());

             Algorithm xx = Algorithm.HMAC256(JWTUtil.SECRET);
             JWTVerifier jwtVerifier = JWT.require(xx).build();
             DecodedJWT decodedJWT = jwtVerifier.verify(jwt);
             //recuperer la session d'utilisateur
             String username = decodedJWT.getSubject();

             AppUser appUser = accountService.loadUserByUsername(username);

             String jwtAccessToken = JWT.create()
                     .withSubject(appUser.getUsername())
                     .withExpiresAt(new Date(System.currentTimeMillis()+JWTUtil.EXPIRE_REFRESH_TOKEN))
                     .withIssuer(request.getRequestURL().toString())
                     .withClaim("roles",appUser.getRoles().stream().map(r -> r.getRolename()).collect(Collectors.toList()))
                     .sign(xx);
             Map<String,String> idToken =new HashMap<>();
             idToken.put("accessToken :",jwtAccessToken);
             idToken.put("refreshToken :",jwt);
             response.setContentType("application/json");
             new ObjectMapper().writeValue(response.getOutputStream(),idToken);

         }
         catch (Exception e)
         {
             throw e;
         }

     }  else {
         throw new RuntimeException("refresh token required !!");
     }
    }
    @GetMapping(path = "/profile")
    public AppUser profile(Principal principal){
        return accountService.loadUserByUsername(principal.getName());
    }
}
