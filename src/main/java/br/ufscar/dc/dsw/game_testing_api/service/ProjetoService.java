package br.ufscar.dc.dsw.game_testing_api.service;

import br.ufscar.dc.dsw.game_testing_api.dto.ProjetoDTO;
import br.ufscar.dc.dsw.game_testing_api.exceptions.ResourceNotFoundException;
import br.ufscar.dc.dsw.game_testing_api.model.Projeto;
import br.ufscar.dc.dsw.game_testing_api.model.Role;
import br.ufscar.dc.dsw.game_testing_api.model.Usuario;
import br.ufscar.dc.dsw.game_testing_api.repository.ProjetoRepository;
import br.ufscar.dc.dsw.game_testing_api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjetoService {

    @Autowired
    private ProjetoRepository projetoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public ProjetoDTO criarProjeto(ProjetoDTO projetoDTO) {
        if (projetoRepository.existsByNomeIgnoreCase(projetoDTO.getNome())) {
            throw new DataIntegrityViolationException("Já existe um projeto com este nome.");
        }
        Projeto projeto = new Projeto();
        projeto.setNome(projetoDTO.getNome());
        projeto.setDescricao(projetoDTO.getDescricao());
        projeto = projetoRepository.save(projeto);
        return toDTO(projeto);
    }

    public List<ProjetoDTO> listarProjetos(Usuario usuario, String sortBy, String order) {
        Sort sort = Sort.by(Sort.Direction.fromString(order), sortBy);
        List<Projeto> projetos;
        if (usuario.getRole() == Role.ADMIN) {
            projetos = projetoRepository.findAll(sort);
        } else {
            projetos = projetoRepository.findByMembrosPermitidos_Id(usuario.getId());
        }
        return projetos.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public ProjetoDTO atualizarProjeto(Long id, ProjetoDTO projetoDTO) {
        Projeto projeto = projetoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Projeto não encontrado"));
        if (projetoRepository.existsByNomeIgnoreCaseAndIdNot(projetoDTO.getNome(), id)) {
            throw new DataIntegrityViolationException("Já existe um projeto com este nome.");
        }
        projeto.setNome(projetoDTO.getNome());
        projeto.setDescricao(projetoDTO.getDescricao());
        projeto = projetoRepository.save(projeto);
        return toDTO(projeto);
    }

    public void deletarProjeto(Long id) {
        Projeto projeto = projetoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Projeto não encontrado"));
        // TODO: Adicionar verificação se o projeto está em uso
        projetoRepository.delete(projeto);
    }

    public void adicionarMembro(Long projetoId, Long usuarioId) {
        Projeto projeto = projetoRepository.findById(projetoId).orElseThrow(() -> new ResourceNotFoundException("Projeto não encontrado"));
        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
        projeto.getMembrosPermitidos().add(usuario);
        projetoRepository.save(projeto);
    }

    public void removerMembro(Long projetoId, Long usuarioId) {
        Projeto projeto = projetoRepository.findById(projetoId).orElseThrow(() -> new ResourceNotFoundException("Projeto não encontrado"));
        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
        projeto.getMembrosPermitidos().remove(usuario);
        projetoRepository.save(projeto);
    }

    private ProjetoDTO toDTO(Projeto projeto) {
        ProjetoDTO dto = new ProjetoDTO();
        dto.setId(projeto.getId());
        dto.setNome(projeto.getNome());
        dto.setDescricao(projeto.getDescricao());
        dto.setDataCriacao(projeto.getDataCriacao());
        dto.setMemberIds(projeto.getMembrosPermitidos().stream().map(Usuario::getId).collect(Collectors.toList()));
        return dto;
    }

    public ProjetoDTO buscarProjetoPorId(Long id) {
        Projeto projeto = projetoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Projeto não encontrado"));
        return toDTO(projeto);
    }

}
