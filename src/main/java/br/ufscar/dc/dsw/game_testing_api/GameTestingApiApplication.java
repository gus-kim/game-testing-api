package br.ufscar.dc.dsw.game_testing_api;

import br.ufscar.dc.dsw.game_testing_api.model.Role;
import br.ufscar.dc.dsw.game_testing_api.model.Usuario;
import br.ufscar.dc.dsw.game_testing_api.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class GameTestingApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(GameTestingApiApplication.class, args);
	}

	@Bean
	public CommandLineRunner init(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			// Verifica se o usuário admin já existe para não criar duplicado
			if (usuarioRepository.findByEmail("admin@email.com").isEmpty()) {
				System.out.println("Criando usuário admin de teste...");

				Usuario admin = new Usuario();
				admin.setNome("Admin Teste");
				admin.setEmail("admin@email.com");
				admin.setSenha(passwordEncoder.encode("senhaforte"));
				admin.setRole(Role.ADMIN);

				usuarioRepository.save(admin);
				System.out.println("Usuário admin de teste criado!");
			}
		};
	}
}