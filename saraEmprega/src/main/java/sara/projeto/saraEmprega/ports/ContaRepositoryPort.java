package sara.projeto.saraEmprega.ports;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import sara.projeto.saraEmprega.model.Conta;

/**
* Interface base para repositórios de Conta
* Define operações CRUD básicas para qualquer entidade que estenda Conta
*
* Como usar:
* No adapter implemente ContaRepositoryAdapter
* Defina seu tipo genérico que será gerenciado.
* Para operações específicas, adicione as operações no adapter do tipo específico
* Aqui ficam apenas as operações que serão usadas em todas contas
*
* O método 'salvar' funciona tanto para criar quanto atualizar
*/
public interface ContaRepositoryPort<T extends Conta> {
    T salvar(T conta);
    Optional<T> encontrarPorId(UUID id);
    Optional<T> encontrarPorEmail(String email);
    boolean existePorEmail(String email);
    boolean existePorId(UUID id);
    List<T> encontrarTodas();
    void deletar(UUID id);
}
