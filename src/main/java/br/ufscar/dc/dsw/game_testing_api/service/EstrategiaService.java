package br.ufscar.dc.dsw.game_testing_api.service;

import br.ufscar.dc.dsw.game_testing_api.dto.EstrategiaRequestDTO;
import br.ufscar.dc.dsw.game_testing_api.dto.EstrategiaResponseDTO;
import br.ufscar.dc.dsw.game_testing_api.model.Estrategia;
import br.ufscar.dc.dsw.game_testing_api.repository.EstrategiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EstrategiaService {

    @Autowired
    private EstrategiaRepository estrategiaRepository;

    /**
     * Lista todas as estratégias
     */
    public List<EstrategiaResponseDTO> listarTodas() {
        return estrategiaRepository.findAll()
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Lista estratégias com paginação
     */
    public Page<EstrategiaResponseDTO> listarComPaginacao(Pageable pageable) {
        return estrategiaRepository.findAll(pageable)
                .map(this::convertToResponseDTO);
    }

    /**
     * Busca estratégia por ID
     */
    public EstrategiaResponseDTO buscarPorId(Long id) {
        Estrategia estrategia = estrategiaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estratégia não encontrada com ID: " + id));
        return convertToResponseDTO(estrategia);
    }

    /**
     * Busca estratégias por nome
     */
    public List<EstrategiaResponseDTO> buscarPorNome(String nome) {
        return estrategiaRepository.findByNomeContainingIgnoreCase(nome)
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Cria uma nova estratégia
     */
    public EstrategiaResponseDTO criar(EstrategiaRequestDTO requestDTO) {
        // Validar se já existe estratégia com o mesmo nome
        if (estrategiaRepository.existsByNomeIgnoreCase(requestDTO.getNome())) {
            throw new RuntimeException("Já existe uma estratégia com o nome: " + requestDTO.getNome());
        }

        Estrategia estrategia = convertToEntity(requestDTO);
        Estrategia estrategiaSalva = estrategiaRepository.save(estrategia);
        return convertToResponseDTO(estrategiaSalva);
    }

    /**
     * Atualiza uma estratégia existente
     */
    public EstrategiaResponseDTO atualizar(Long id, EstrategiaRequestDTO requestDTO) {
        Estrategia estrategiaExistente = estrategiaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estratégia não encontrada com ID: " + id));

        // Validar se já existe estratégia com o mesmo nome (excluindo a atual)
        if (estrategiaRepository.existsByNomeIgnoreCaseAndIdNot(requestDTO.getNome(), id)) {
            throw new RuntimeException("Já existe uma estratégia com o nome: " + requestDTO.getNome());
        }

        // Atualizar campos
        estrategiaExistente.setNome(requestDTO.getNome());
        estrategiaExistente.setDescricao(requestDTO.getDescricao());
        estrategiaExistente.setDicas(requestDTO.getDicas());
        estrategiaExistente.setImagens(requestDTO.getImagens());

        Estrategia estrategiaAtualizada = estrategiaRepository.save(estrategiaExistente);
        return convertToResponseDTO(estrategiaAtualizada);
    }

    /**
     * Remove uma estratégia
     */
    public void remover(Long id) {
        if (!estrategiaRepository.existsById(id)) {
            throw new RuntimeException("Estratégia não encontrada com ID: " + id);
        }
        estrategiaRepository.deleteById(id);
    }

    /**
     * Converte entidade para DTO de resposta
     */
    private EstrategiaResponseDTO convertToResponseDTO(Estrategia estrategia) {
        return new EstrategiaResponseDTO(
                estrategia.getId(),
                estrategia.getNome(),
                estrategia.getDescricao(),
                estrategia.getDicas(),
                estrategia.getImagens()
        );
    }

    /**
     * Converte DTO de request para entidade
     */
    private Estrategia convertToEntity(EstrategiaRequestDTO requestDTO) {
        return new Estrategia(
                requestDTO.getNome(),
                requestDTO.getDescricao(),
                requestDTO.getDicas(),
                requestDTO.getImagens()
        );
    }
}
