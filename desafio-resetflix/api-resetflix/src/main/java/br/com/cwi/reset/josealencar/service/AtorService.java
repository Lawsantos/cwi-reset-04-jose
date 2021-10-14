package br.com.cwi.reset.josealencar.service;

import br.com.cwi.reset.josealencar.FakeDatabase;
import br.com.cwi.reset.josealencar.exception.*;
import br.com.cwi.reset.josealencar.model.Ator;
import br.com.cwi.reset.josealencar.model.StatusCarreira;
import br.com.cwi.reset.josealencar.request.AtorRequest;
import br.com.cwi.reset.josealencar.response.AtorEmAtividade;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AtorService {

    private FakeDatabase fakeDatabase;

    public AtorService(FakeDatabase fakeDatabase) {
        this.fakeDatabase = fakeDatabase;
    }

    public void criarAtor(AtorRequest atorRequest) throws Exception {
        if (atorRequest.getNome() == null) {
            throw new NomeNaoInformadoException();
        }

        if (atorRequest.getDataNascimento() == null) {
            throw new DataNascimentoNaoInformadoException();
        }

        if (atorRequest.getStatusCarreira() == null) {
            throw new StatusCarreiraNaoInformadoException();
        }

        if (atorRequest.getAnoInicioAtividade() == null) {
            throw new AnoInicioAtividadeNaoInformadoException();
        }

        if (atorRequest.getNome().split(" ").length < 2) {
            throw new NomeSobrenomeAtorObrigatorioException();
        }

        if (LocalDate.now().isBefore(atorRequest.getDataNascimento())) {
            throw new Exception("Não é possível cadastrar atores não nascidos.");
        }

        if (atorRequest.getAnoInicioAtividade() <= atorRequest.getDataNascimento().getYear()) {
            throw new Exception("Ano de início de atividade inválido para o ator cadastrado.");
        }

        final List<Ator> atoresCadastrados = fakeDatabase.recuperaAtores();

        for (Ator atorCadastrado : atoresCadastrados) {
            if (atorCadastrado.getNome().equalsIgnoreCase(atorRequest.getNome())) {
                throw new Exception(String.format("Já existe um ator cadastrado para o nome %s.", atorRequest.getNome()));
            }
        }

        final Integer idGerado = atoresCadastrados.size() + 1;

        final Ator ator = new Ator(idGerado, atorRequest.getNome(), atorRequest.getDataNascimento(), atorRequest.getStatusCarreira(), atorRequest.getAnoInicioAtividade());

        fakeDatabase.persisteAtor(ator);
    }

    public List<AtorEmAtividade> listarAtoresEmAtividade(String filtroNome) throws Exception {
        final List<Ator> atoresCadastrados = fakeDatabase.recuperaAtores();

        if (atoresCadastrados.isEmpty()) {
            throw new Exception("Nenhum ator cadastrado, favor cadastar atores.");
        }

        final List<AtorEmAtividade> retorno = new ArrayList<>();

        if (filtroNome != null) {
            for (Ator ator : atoresCadastrados) {
                final boolean containsFilter = ator.getNome().toLowerCase(Locale.ROOT).contains(filtroNome.toLowerCase(Locale.ROOT));
                final boolean emAtividade = StatusCarreira.EM_ATIVIDADE.equals(ator.getStatusCarreira());
                if (containsFilter && emAtividade) {
                    retorno.add(new AtorEmAtividade(ator.getId(), ator.getNome(), ator.getDataNascimento()));
                }
            }
        } else {
            for (Ator ator : atoresCadastrados) {
                final boolean emAtividade = StatusCarreira.EM_ATIVIDADE.equals(ator.getStatusCarreira());
                if (emAtividade) {
                    retorno.add(new AtorEmAtividade(ator.getId(), ator.getNome(), ator.getDataNascimento()));
                }
            }
        }

        if (retorno.isEmpty()) {
            throw new Exception(String.format("Ator não encontrato com o filtro %s, favor informar outro filtro.", filtroNome));
        }

        return retorno;
    }

    public Ator consultarAtor(Integer id) throws Exception {
        if (id == null) {
            throw new Exception("Campo obrigatório não informado. Favor informar o campo id.");
        }

        final List<Ator> atores = fakeDatabase.recuperaAtores();

        for (Ator ator : atores) {
            if (ator.getId().equals(id)) {
                return ator;
            }
        }

        throw new Exception(String.format("Nenhum ator encontrado com o parâmetro id=%d, favor verifique os parâmetros informados.", id));
    }

    public List<Ator> consultarAtores() throws Exception {
        final List<Ator> atores = fakeDatabase.recuperaAtores();

        if (atores.isEmpty()) {
            throw new Exception("Nenhum ator cadastrado, favor cadastar atores.");
        }

        return atores;
    }
}
