package br.com.cwi.reset.josealencar.service;

import br.com.cwi.reset.josealencar.model.Diretor;
import br.com.cwi.reset.josealencar.request.DiretorRequest;
import br.com.cwi.reset.josealencar.FakeDatabase;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DiretorService {

    private FakeDatabase fakeDatabase;

    public DiretorService(FakeDatabase fakeDatabase) {
        this.fakeDatabase = fakeDatabase;
    }

    public void cadastrarDiretor(final DiretorRequest diretorRequest) throws Exception {
        if (diretorRequest.getNome() == null) {
            throw new Exception("Campo obrigatório não informado. Favor informar o campo nome.");
        }

        if (diretorRequest.getDataNascimento() == null) {
            throw new Exception("Campo obrigatório não informado. Favor informar o campo dataNascimento.");
        }

        if (diretorRequest.getAnoInicioAtividade() == null) {
            throw new Exception("Campo obrigatório não informado. Favor informar o campo anoInicioAtividade.");
        }

        if (diretorRequest.getNome().split(" ").length < 2) {
            throw new Exception("Deve ser informado no mínimo nome e sobrenome para o diretor.");
        }

        if (LocalDate.now().isBefore(diretorRequest.getDataNascimento())) {
            throw new Exception("Não é possível cadastrar diretores não nascidos.");
        }

        if (diretorRequest.getAnoInicioAtividade() <= diretorRequest.getDataNascimento().getYear()) {
            throw new Exception("Ano de início de atividade inválido para o diretor cadastrado.");
        }

        final List<Diretor> diretoresCadastrados = fakeDatabase.recuperaDiretores();

        for (Diretor diretorCadastrado : diretoresCadastrados) {
            if (diretorCadastrado.getNome().equalsIgnoreCase(diretorRequest.getNome())) {
                throw new Exception(String.format("Já existe um diretor cadastrado para o nome %s.", diretorRequest.getNome()));
            }
        }

        final Integer idGerado = diretoresCadastrados.size() + 1;

        final Diretor diretor = new Diretor(idGerado, diretorRequest.getNome(), diretorRequest.getDataNascimento(), diretorRequest.getAnoInicioAtividade());

        fakeDatabase.persisteDiretor(diretor);
    }

    public List<Diretor> listarDiretores(final String filtroNome) throws Exception {
        final List<Diretor> diretoresCadastrados = fakeDatabase.recuperaDiretores();

        if (diretoresCadastrados.isEmpty()) {
            throw new Exception("Nenhum diretor cadastrado, favor cadastar diretores.");
        }

        final List<Diretor> retorno = new ArrayList<>();

        if (filtroNome != null) {
            for (Diretor diretor : diretoresCadastrados) {
                final boolean containsFilter = diretor.getNome().toLowerCase(Locale.ROOT).contains(filtroNome.toLowerCase(Locale.ROOT));
                if (containsFilter) {
                    retorno.add(diretor);
                }
            }
        } else {
            retorno.addAll(diretoresCadastrados);
        }

        if (retorno.isEmpty()) {
            throw new Exception(String.format("Diretor não encontrato com o filtro %s, favor informar outro filtro.", filtroNome));
        }

        return retorno;
    }

    public Diretor consultarDiretor(final Integer id) throws Exception {
        if (id == null) {
            throw new Exception("Campo obrigatório não informado. Favor informar o campo id.");
        }

        final List<Diretor> diretores = fakeDatabase.recuperaDiretores();

        for (Diretor diretor : diretores) {
            if (diretor.getId().equals(id)) {
                return diretor;
            }
        }

        throw new Exception(String.format("Nenhum diretor encontrado com o parâmetro id=%d, favor verifique os parâmetros informados.", id));
    }
}
