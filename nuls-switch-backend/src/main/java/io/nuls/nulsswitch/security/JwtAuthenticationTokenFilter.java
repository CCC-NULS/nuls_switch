package io.nuls.nulsswitch.security;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.nuls.nulsswitch.entity.UserAuth;
import io.nuls.nulsswitch.service.UserAuthService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * token校验
 *
 * @author: EdwardChan
 * createAt: Jul. 20th 2019
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Value("${jwt.header}")
    private String token_header;

    @Value("${jwt.expiration}")
    private Long access_token_expiration;


    @Autowired
    UserAuthService userAuthService;

    private static final String FILTER_APPLIED = "_spring_security_demoFilter_filterApplied";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String auth_token = request.getHeader(this.token_header);
        final String auth_token_start = "Bearer ";
        if (StringUtils.isNotEmpty(auth_token) && auth_token.startsWith(auth_token_start)) {
            auth_token = auth_token.substring(auth_token_start.length());
        } else {
            auth_token = null;
        }
        if (auth_token != null) {
            UserAuth userAuthQuery = new UserAuth();
            userAuthQuery.setToken(auth_token);
            EntityWrapper entityWrapper = new EntityWrapper(userAuthQuery);
            UserAuth userAuth = userAuthService.selectOne(entityWrapper);
            if (userAuth == null) {
                //TODO userAuth不存在
            }
            //验证地址和有效期
            if (userAuth.getCreateTime().after(new Date(System.currentTimeMillis() - access_token_expiration * 1000))) {
                //ToDO 失效
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userAuth.getAddress(), null, null);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }
}
