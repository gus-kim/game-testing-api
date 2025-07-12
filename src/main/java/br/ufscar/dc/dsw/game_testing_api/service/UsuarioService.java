package br.ufscar.dc.dsw.game_testing_api.service;

import br.ufscar.dc.dsw.game_testing_api.model.Usuario;
import br.ufscar.dc.dsw.game_testing_api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario save(Usuario usuario) {
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return usuarioRepository.save(usuario);
    }

    public Usuario findById(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public void delete(Long id) {
        usuarioRepository.deleteById(id);
    }
}