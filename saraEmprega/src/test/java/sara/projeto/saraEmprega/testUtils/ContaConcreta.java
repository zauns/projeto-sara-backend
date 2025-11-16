package sara.projeto.saraEmprega.testUtils;

import java.util.UUID;

import sara.projeto.saraEmprega.model.Conta;

public class ContaConcreta extends Conta {
    public ContaConcreta() {
        super();
    }

    public ContaConcreta(UUID id, String nome, String email, String senhaHash, String endereco, String telefone) {
        super();
        this.setId(id);
        this.setNome(nome);
        this.setEmail(email);
        this.setSenhaHash(senhaHash);
        this.setEndereco(endereco);
        this.setTelefone(telefone);
    }

}
