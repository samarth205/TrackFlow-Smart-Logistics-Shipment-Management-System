package com.trackflow.security;
import com.trackflow.user.UserRepository; import jakarta.servlet.*; import jakarta.servlet.http.*; import org.springframework.web.filter.GenericFilterBean; import org.springframework.security.authentication.UsernamePasswordAuthenticationToken; import org.springframework.security.core.context.SecurityContextHolder; import org.springframework.stereotype.Component; import java.io.IOException; import java.util.List;
@Component public class JwtFilter extends GenericFilterBean {
 private final JwtService jwt; private final UserRepository users;
 public JwtFilter(JwtService jwt,UserRepository users){this.jwt=jwt;this.users=users;}
 @Override public void doFilter(ServletRequest req,ServletResponse res,FilterChain chain)throws IOException,ServletException{
 HttpServletRequest r=(HttpServletRequest)req;String h=r.getHeader("Authorization");
 if(h!=null&&h.startsWith("Bearer "))try{String email=jwt.subject(h.substring(7));if(users.findByEmail(email).isPresent())SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(email,null,List.of()));}catch(Exception ignored){}
 chain.doFilter(req,res);
 }
}
