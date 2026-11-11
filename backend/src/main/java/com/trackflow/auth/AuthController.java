package com.trackflow.auth;
import com.trackflow.user.*; import com.trackflow.security.JwtService; import jakarta.validation.Valid; import jakarta.validation.constraints.*; import org.springframework.http.*; import org.springframework.security.crypto.password.PasswordEncoder; import org.springframework.web.bind.annotation.*; import java.util.Map;
@RestController @RequestMapping("/api/auth") public class AuthController {
 private final UserRepository users; private final PasswordEncoder encoder; private final JwtService jwt;
 public AuthController(UserRepository users,PasswordEncoder encoder,JwtService jwt){this.users=users;this.encoder=encoder;this.jwt=jwt;}
 record Register(@NotBlank String name,@Email @NotBlank String email,@Size(min=8) String password){} record Login(@Email @NotBlank String email,@NotBlank String password){}
 @PostMapping("/register") public ResponseEntity<?> register(@Valid @RequestBody Register x){if(users.existsByEmail(x.email().toLowerCase()))return ResponseEntity.status(409).body(Map.of("message","Email already registered"));AppUser u=users.save(new AppUser(x.name(),x.email().toLowerCase(),encoder.encode(x.password())));return ResponseEntity.ok(response(u));}
 @PostMapping("/login") public ResponseEntity<?> login(@Valid @RequestBody Login x){return users.findByEmail(x.email().toLowerCase()).filter(u->encoder.matches(x.password(),u.getPassword())).<ResponseEntity<?>>map(u->ResponseEntity.ok(response(u))).orElseGet(()->ResponseEntity.status(401).body(Map.of("message","Invalid email or password")));}
 private Map<String,Object> response(AppUser u){return Map.of("token",jwt.issue(u.getEmail()),"user",Map.of("id",u.getId(),"name",u.getName(),"email",u.getEmail()));}
}
