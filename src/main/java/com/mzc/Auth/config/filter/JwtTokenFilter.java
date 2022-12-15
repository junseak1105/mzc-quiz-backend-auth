package com.mzc.Auth.config.filter;

import com.mzc.Auth.model.Host;
import com.mzc.Auth.service.HostAuthService;
import com.mzc.Auth.util.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final HostAuthService hostAuthService;

    private final String key;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // get header
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(header == null || !header.startsWith("Bearer ")){
            log.error("Error occurs while getting header. header is null or invalid");
            filterChain.doFilter(request,response);
            return;
        }

        try {
            final String token = header.split(" ")[1].trim();

            // 토큰 값 유효 체크
            if(JwtTokenUtils.isExpired(token,key)){
                log.error("key is expired");
                filterChain.doFilter(request,response);
                return;
            }

            String hostEmail = JwtTokenUtils.getHostEmail(token,key);

            // hostEmail 유효 체크
            Host host = hostAuthService.loadFindByHostEmail(hostEmail);

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    host, null, host.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);

        }catch (RuntimeException e){
            log.error("Error occurs while validating. {}", e.toString());
            filterChain.doFilter(request,response);
            return;
        }

        filterChain.doFilter(request,response);


    }
}
