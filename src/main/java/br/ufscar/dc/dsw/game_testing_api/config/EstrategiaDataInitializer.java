package br.ufscar.dc.dsw.game_testing_api.config;

import br.ufscar.dc.dsw.game_testing_api.model.Estrategia;
import br.ufscar.dc.dsw.game_testing_api.repository.EstrategiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class EstrategiaDataInitializer implements CommandLineRunner {

    @Autowired
    private EstrategiaRepository estrategiaRepository;

    @Override
    public void run(String... args) throws Exception {
        // Verifica se já existem estratégias no banco
        if (estrategiaRepository.count() == 0) {
            criarEstrategiasIniciais();
        }
    }

    private void criarEstrategiasIniciais() {
        List<Estrategia> estrategias = Arrays.asList(
                new Estrategia(
                        "Defesa de Torres",
                        "Estratégia baseada na proteção de pontos estratégicos do mapa.",
                        "Construa torres em gargalos e rotas obrigatórias. Foque em pontos de passagem estreitos.",
                        Arrays.asList(
                                "https://pt.quizur.com/_image?href=https://img.quizur.com/f/img5ed6ffaabd0883.93229399.jpg?lastEdited=1591148508&w=600&h=600&f=webp"
                        )
                ),
                new Estrategia(
                        "Ataque Relâmpago",
                        "Movimentação rápida para surpreender o inimigo antes que se organize.",
                        "Utilize unidades leves e rápidas no início da partida. Ataque recursos e unidades isoladas.",
                        Arrays.asList(
                                "https://www.ahnegao.com.br/wp-content/uploads/2025/02/imgaleat-6jx-3.jpg"
                        )
                ),
                new Estrategia(
                        "Controle de Área",
                        "Tomar o controle de áreas centrais para dominar o campo de batalha.",
                        "Concentre-se em regiões elevadas e cruzamentos estratégicos. Mantenha presença constante.",
                        Arrays.asList(
                                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSJ2hAI7vKl1LiC4A4RP1utrk9pI09UL89rcg&s"
                        )
                ),
                new Estrategia(
                        "Exploração Livre",
                        "Teste sem roteiro específico, explorando funcionalidades de forma intuitiva.",
                        "Siga sua intuição. Teste diferentes combinações de ações. Documente comportamentos inesperados.",
                        Arrays.asList()
                ),
                new Estrategia(
                        "Teste de Bordas",
                        "Focado em testar limites e valores extremos do sistema.",
                        "Teste valores máximos e mínimos. Explore limites de mapas, recursos e unidades.",
                        Arrays.asList()
                ),
                new Estrategia(
                        "Simulação de Usuário Novato",
                        "Teste como se fosse um usuário completamente novo no jogo.",
                        "Ignore conhecimentos prévios. Teste ações óbvias primeiro. Observe tutoriais e dicas.",
                        Arrays.asList()
                )
        );

        estrategiaRepository.saveAll(estrategias);
        System.out.println("Estratégias iniciais criadas com sucesso!");
    }
}
