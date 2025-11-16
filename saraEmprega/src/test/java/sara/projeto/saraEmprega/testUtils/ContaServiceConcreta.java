package sara.projeto.saraEmprega.testUtils;

import sara.projeto.saraEmprega.model.Conta;
import sara.projeto.saraEmprega.ports.ContaRepositoryPort;
import sara.projeto.saraEmprega.service.ContaService;

public class ContaServiceConcreta extends ContaService<Conta> {

    private final ContaRepositoryPort<Conta> repositorio;

    public ContaServiceConcreta(ContaRepositoryPort<Conta> repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    protected ContaRepositoryPort<Conta> repositorio() {
        return repositorio;
    }

}
