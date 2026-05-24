package duoc.hotelpro.auth;

import duoc.hotelpro.usuarios.Usuario;
import duoc.hotelpro.usuarios.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final AuthRepository authRepository;

    public String login(AuthDTO dto) {
        log.info("Intento de login para usuario {}", dto.getUsername());

        Usuario usuario = usuarioRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new AuthException("Usuario o contraseña incorrectos"));

        if (!usuario.getPassword().equals(dto.getPassword())) {
            log.warn("Password incorrecta para usuario {}", dto.getUsername());
            throw new AuthException("Usuario o contraseña incorrectos");
        }

        if (!Boolean.TRUE.equals(usuario.getActivo())) {
            throw new AuthException("Usuario inactivo");
        }

        String token = UUID.randomUUID().toString();

        Auth auth = new Auth();
        auth.setUsuarioId(usuario.getId());
        auth.setToken(token);
        auth.setFechaCreacion(LocalDateTime.now());

        authRepository.save(auth);

        log.info("Token generado correctamente para usuario {}", dto.getUsername());

        return token;
    }

    public boolean validarToken(String token) {
        return authRepository.findByToken(token).isPresent();
    }
}