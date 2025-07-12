package br.ufscar.dc.dsw.game_testing_api.controller;

import br.ufscar.dc.dsw.game_testing_api.dto.LoginRequestDTO;
import br.ufscar.dc.dsw.game_testing_api.dto.LoginResponseDTO;
import br.ufscar.dc.dsw.game_testing_api.model.Usuario;
import br.ufscar.dc.dsw.game_testing_api.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    // Principal orquestrador da autenticação do Spring Security
    @Autowired
    private AuthenticationManager authenticationManager;

    // Nosso serviço de token que criamos
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO data) {
        // 1. Cria um objeto de autenticação com email e senha
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.senha());

        // 2. Efetivamente autentica o usuário. O Spring vai usar nosso UserDetailsService e PasswordEncoder aqui.
        // Se as credenciais estiverem erradas, ele lançará uma exceção que será tratada globalmente.
        var auth = this.authenticationManager.authenticate(usernamePassword);

        // 3. Se a autenticação for bem-sucedida, gera o token
        var token = tokenService.generateToken((Usuario) auth.getPrincipal());

        // 4. Retorna o token em um DTO de resposta com status 200 OK
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
}