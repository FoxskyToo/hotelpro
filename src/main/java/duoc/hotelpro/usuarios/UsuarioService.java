package duoc.hotelpro.usuarios;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public List<Usuario> listar() {
        log.info("Listando usuarios");
        return usuarioRepository.findAll();
    }

    public Usuario buscarPorId(Long id) {
        log.info("Buscando usuario con ID {}", id);
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioException("Usuario no encontrado con ID: " + id));
    }

    public Usuario guardar(UsuarioDTO dto) {
        log.info("Guardando usuario {}", dto.getUsername());

        if (usuarioRepository.existsByUsername(dto.getUsername())) {
            throw new UsuarioException("Ya existe un usuario con ese username");
        }

        Usuario usuario = new Usuario();
        usuario.setUsername(dto.getUsername());
        usuario.setPassword(dto.getPassword());
        usuario.setRol(dto.getRol());
        usuario.setActivo(dto.getActivo() != null ? dto.getActivo() : true);

        return usuarioRepository.save(usuario);
    }

    public Usuario actualizar(Long id, UsuarioDTO dto) {
        log.info("Actualizando usuario con ID {}", id);

        Usuario usuario = buscarPorId(id);
        usuario.setUsername(dto.getUsername());
        usuario.setPassword(dto.getPassword());
        usuario.setRol(dto.getRol());
        usuario.setActivo(dto.getActivo() != null ? dto.getActivo() : true);

        return usuarioRepository.save(usuario);
    }

    public void eliminar(Long id) {
        log.info("Eliminando usuario con ID {}", id);

        Usuario usuario = buscarPorId(id);
        usuarioRepository.delete(usuario);
    }
}