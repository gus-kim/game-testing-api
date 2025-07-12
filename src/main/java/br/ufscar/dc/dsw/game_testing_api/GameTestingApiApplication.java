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
			// Cria usuário ADMIN se não existir
			if (usuarioRepository.findByEmail("admin@email.com").isEmpty()) {
				System.out.println("Criando usuário ADMIN de teste...");
				Usuario admin = new Usuario();
				admin.setNome("Admin Mestre");
				admin.setEmail("admin@email.com");
				admin.setSenha(passwordEncoder.encode("admin123")); // Use uma senha diferente para o admin
				admin.setRole(Role.ADMIN);
				usuarioRepository.save(admin);
				System.out.println("Usuário ADMIN de teste criado!");
			}

			// Cria usuário TESTER se não existir
			if (usuarioRepository.findByEmail("tester@email.com").isEmpty()) {
				System.out.println("Criando usuário TESTER de teste...");
				Usuario tester = new Usuario();
				tester.setNome("Testador Padrão");
				tester.setEmail("tester@email.com");
				tester.setSenha(passwordEncoder.encode("tester123")); // E outra para o tester
				tester.setRole(Role.TESTER);
				usuarioRepository.save(tester);
				System.out.println("Usuário TESTER de teste criado!");
			}
		};
	}
}