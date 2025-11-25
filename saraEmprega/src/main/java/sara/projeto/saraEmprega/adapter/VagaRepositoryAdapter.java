package sara.projeto.saraEmprega.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sara.projeto.saraEmprega.model.Vaga;
import sara.projeto.saraEmprega.ports.VagaRepositoryPort;
import sara.projeto.saraEmprega.repository.VagaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class VagaRepositoryAdapter implements VagaRepositoryPort {

    private final VagaRepository vagaRepository;

    @Override
    public Vaga save(Vaga vaga) {
        return vagaRepository.save(vaga);
    }

    @Override
    public Optional<Vaga> findById(UUID id) {
        return vagaRepository.findById(id);
    }

    @Override
    public List<Vaga> findAll() {
        return vagaRepository.findAll();
    }

    @Override
    public List<Vaga> findByEmpresaId(UUID empresaId) {
        return vagaRepository.findByEmpresaId(empresaId);
    }

    @Override
    public boolean existsById(UUID id) {
        return vagaRepository.existsById(id);
    }

    @Override
    public void delete(UUID id) {
        vagaRepository.deleteById(id);
    }

    @Override
    public List<Vaga> findByTagsIn(List<String> tags) {
        return vagaRepository.findAllByTagsIn(tags);
    }

    @Override
    public List<Vaga> findByTagsContaining(String tag) {
        return vagaRepository.findAllByTagsContaining(tag);
    }
}
