package com.final_test_sof3012.sof3022_ass_restful_api.filter;

import com.final_test_sof3012.sof3022_ass_restful_api.services.CustomUserDetailsService;
import com.final_test_sof3012.sof3022_ass_restful_api.services.UserService;
import com.final_test_sof3012.sof3022_ass_restful_api.utils.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getTokenFromRequest(request);
        if(token != null){
            try{
                String username = jwtUtil.extractUsername(token);
                List<String> roles = jwtUtil.extractRoles(token);
                if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
                    UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

                    List<GrantedAuthority>  grantedAuthorities = roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());

                    if(jwtUtil.validateToken(token,userDetails.getUsername())){
                        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,grantedAuthorities);
                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                }
            }catch (ExpiredJwtException e){
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token expired");
                return;
            }catch(JwtException e){
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Invalid token");
            }
        }
        filterChain.doFilter(request,response);
    }

    private String getTokenFromRequest(HttpServletRequest request){
        String headerAuth = request.getHeader("Authorization");
        if(headerAuth != null && headerAuth.startsWith("Bearer ")){
            return headerAuth.substring(7);
        }
        return null;
    }
}
