package br.com.cwi.reset.josealencar.service;

import static org.junit.Assert.*;

import br.com.cwi.reset.josealencar.FakeDatabase;
import br.com.cwi.reset.josealencar.model.Ator;
import br.com.cwi.reset.josealencar.model.StatusCarreira;
import br.com.cwi.reset.josealencar.request.AtorRequest;
import br.com.cwi.reset.josealencar.response.AtorEmAtividade;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

public class AtorServiceTest {

    private AtorService atorService;

    @Before
    public void setUp() {
        FakeDatabase fakeDatabase = new FakeDatabase();
        atorService = new AtorService(fakeDatabase);
    }

    @Test(expected = Exception.class)
    public void deveRetornarErroQuandoNomeNulo() throws Exception {
        String nome = null;
        LocalDate dataNascimento = LocalDate.of(1968, Month.SEPTEMBER, 25);
        StatusCarreira statusCarreira = StatusCarreira.EM_ATIVIDADE;
        Integer anoInicioAtividade = 1986;
        AtorRequest atorRequest = new AtorRequest(nome, dataNascimento, statusCarreira, anoInicioAtividade);

        try {
            atorService.criarAtor(atorRequest);
            assertTrue(false);
        } catch (Exception re) {
            String expected = "Campo obrigatório não informado. Favor informar o campo nome.";
            assertEquals(expected, re.getMessage());
            throw re;
        }
    }

    @Test(expected = Exception.class)
    public void deveRetornarErroQuandoDataNascimentoNulo() throws Exception {
        String nome = "Will Smith";
        LocalDate dataNascimento = null;
        StatusCarreira statusCarreira = StatusCarreira.EM_ATIVIDADE;
        Integer anoInicioAtividade = 1986;
        AtorRequest atorRequest = new AtorRequest(nome, dataNascimento, statusCarreira, anoInicioAtividade);

        try {
            atorService.criarAtor(atorRequest);
            assertTrue(false);
        } catch (Exception re) {
            String expected = "Campo obrigatório não informado. Favor informar o campo dataNascimento.";
            assertEquals(expected, re.getMessage());
            throw re;
        }
    }

    @Test(expected = Exception.class)
    public void deveRetornarErroQuandoStatusCarreiraNulo() throws Exception {
        String nome = "Will Smith";
        LocalDate dataNascimento = LocalDate.of(1968, Month.SEPTEMBER, 25);
        StatusCarreira statusCarreira = null;
        Integer anoInicioAtividade = 1986;
        AtorRequest atorRequest = new AtorRequest(nome, dataNascimento, statusCarreira, anoInicioAtividade);

        try {
            atorService.criarAtor(atorRequest);
            assertTrue(false);
        } catch (Exception re) {
            String expected = "Campo obrigatório não informado. Favor informar o campo statusCarreira.";
            assertEquals(expected, re.getMessage());
            throw re;
        }
    }

    @Test(expected = Exception.class)
    public void deveRetornarErroQuandoAnoInicioAtividadeNulo() throws Exception {
        String nome = "Will Smith";
        LocalDate dataNascimento = LocalDate.of(1968, Month.SEPTEMBER, 25);
        StatusCarreira statusCarreira = StatusCarreira.EM_ATIVIDADE;
        Integer anoInicioAtividade = null;
        AtorRequest atorRequest = new AtorRequest(nome, dataNascimento, statusCarreira, anoInicioAtividade);

        try {
            atorService.criarAtor(atorRequest);
            assertTrue(false);
        } catch (Exception re) {
            String expected = "Campo obrigatório não informado. Favor informar o campo anoInicioAtividade.";
            assertEquals(expected, re.getMessage());
            throw re;
        }
    }

    @Test(expected = Exception.class)
    public void deveRetornarErroQuandoAtorPossuirSomenteNome() throws Exception {
        String nome = "Will";
        LocalDate dataNascimento = LocalDate.of(1968, Month.SEPTEMBER, 25);
        StatusCarreira statusCarreira = StatusCarreira.EM_ATIVIDADE;
        Integer anoInicioAtividade = 1986;
        AtorRequest atorRequest = new AtorRequest(nome, dataNascimento, statusCarreira, anoInicioAtividade);

        try {
            atorService.criarAtor(atorRequest);
            assertTrue(false);
        } catch (Exception re) {
            String expected = "Deve ser informado no mínimo nome e sobrenome para o ator.";
            assertEquals(expected, re.getMessage());
            throw re;
        }
    }

    @Test(expected = Exception.class)
    public void deveRetornarErroQuandoAtorNaoNascido() throws Exception {
        String nome = "Will Smith";
        LocalDate dataNascimento = LocalDate.of(LocalDate.now().getYear() + 10, Month.SEPTEMBER, 25);
        StatusCarreira statusCarreira = StatusCarreira.EM_ATIVIDADE;
        Integer anoInicioAtividade = 1986;
        AtorRequest atorRequest = new AtorRequest(nome, dataNascimento, statusCarreira, anoInicioAtividade);

        try {
            atorService.criarAtor(atorRequest);
            assertTrue(false);
        } catch (Exception re) {
            String expected = "Não é possível cadastrar atores não nascidos.";
            assertEquals(expected, re.getMessage());
            throw re;
        }
    }

    @Test(expected = Exception.class)
    public void deveRetornarErroQuandoAtorComInicioAtividadeAnteriorNascimento() throws Exception {
        String nome = "Will Smith";
        LocalDate dataNascimento = LocalDate.of(1968, Month.SEPTEMBER, 25);
        StatusCarreira statusCarreira = StatusCarreira.EM_ATIVIDADE;
        Integer anoInicioAtividade = 1956;
        AtorRequest atorRequest = new AtorRequest(nome, dataNascimento, statusCarreira, anoInicioAtividade);

        try {
            atorService.criarAtor(atorRequest);
            assertTrue(false);
        } catch (Exception re) {
            String expected = "Ano de início de atividade inválido para o ator cadastrado.";
            assertEquals(expected, re.getMessage());
            throw re;
        }
    }

    @Test(expected = Exception.class)
    public void deveRetornarErroQuandoAtorJaExistenteComMesmoNome() throws Exception {
        String nome = "Will Smith";
        String nome2 = "will smith";
        LocalDate dataNascimento = LocalDate.of(1968, Month.SEPTEMBER, 25);
        StatusCarreira statusCarreira = StatusCarreira.EM_ATIVIDADE;
        Integer anoInicioAtividade = 1986;
        AtorRequest atorRequest = new AtorRequest(nome, dataNascimento, statusCarreira, anoInicioAtividade);
        AtorRequest atorRequest2 = new AtorRequest(nome2, dataNascimento, statusCarreira, anoInicioAtividade);

        try {
            atorService.criarAtor(atorRequest);
            atorService.criarAtor(atorRequest2);
        } catch (Exception re) {
            String expected = "Já existe um ator cadastrado para o nome will smith.";
            assertEquals(expected, re.getMessage());
            throw re;
        }

        assertEquals(1, atorService.consultarAtores().size());
    }

    @Test
    public void deveSalvarAtorEConsultarListaComUmNovoAtor() throws Exception {
        String nome = "Will Smith";
        LocalDate dataNascimento = LocalDate.of(1968, Month.SEPTEMBER, 25);
        StatusCarreira statusCarreira = StatusCarreira.EM_ATIVIDADE;
        Integer anoInicioAtividade = 1986;
        AtorRequest atorRequest = new AtorRequest(nome, dataNascimento, statusCarreira, anoInicioAtividade);

        atorService.criarAtor(atorRequest);

        assertEquals(1, atorService.consultarAtores().size());
    }

    @Test(expected = Exception.class)
    public void deveRetornarErroQuandoNenhumAtorCadastradoNaConsulta() throws Exception {
        try {
            atorService.listarAtoresEmAtividade(null);
        } catch (Exception e) {
            String expected = "Nenhum ator cadastrado, favor cadastar atores.";
            assertEquals(expected, e.getMessage());
            throw e;
        }
    }

    @Test(expected = Exception.class)
    public void deveRetornarErroQuandoNenhumAtorCadastradoComFiltroInformado() throws Exception {
        String nome = "Will Smith";
        LocalDate dataNascimento = LocalDate.of(1968, Month.SEPTEMBER, 25);
        StatusCarreira statusCarreira = StatusCarreira.EM_ATIVIDADE;
        Integer anoInicioAtividade = 1986;
        AtorRequest atorRequest = new AtorRequest(nome, dataNascimento, statusCarreira, anoInicioAtividade);

        atorService.criarAtor(atorRequest);
        try {
            atorService.listarAtoresEmAtividade("teste");
        } catch (Exception e) {
            String expected = "Ator não encontrato com o filtro teste, favor informar outro filtro.";
            assertEquals(expected, e.getMessage());
            throw e;
        }
    }

    @Test
    public void deveRetornarApenasAtoresEmAtividade() throws Exception {
        String nome = "Will Smith";
        LocalDate dataNascimento = LocalDate.of(1968, Month.SEPTEMBER, 25);
        StatusCarreira statusCarreira = StatusCarreira.EM_ATIVIDADE;
        Integer anoInicioAtividade = 1986;
        AtorRequest atorRequest = new AtorRequest(nome, dataNascimento, statusCarreira, anoInicioAtividade);

        String nomeAtriz = "Cameron Diaz";
        LocalDate dataNascimentoAtriz = LocalDate.of(1972, Month.AUGUST, 30);
        StatusCarreira statusCarreiraAtriz = StatusCarreira.APOSENTADO;
        Integer anoInicioAtividadeAtriz = 1990;
        AtorRequest atrizRequest = new AtorRequest(nomeAtriz, dataNascimentoAtriz, statusCarreiraAtriz, anoInicioAtividadeAtriz);

        atorService.criarAtor(atorRequest);
        atorService.criarAtor(atrizRequest);

        List<AtorEmAtividade> atorEmAtividades = atorService.listarAtoresEmAtividade(null);

        assertEquals(1, atorEmAtividades.size());
    }

    @Test
    public void deveRetornarApenasFiltradosPorNome() throws Exception {
        String nome = "Will Smith";
        LocalDate dataNascimento = LocalDate.of(1968, Month.SEPTEMBER, 25);
        StatusCarreira statusCarreira = StatusCarreira.EM_ATIVIDADE;
        Integer anoInicioAtividade = 1986;
        AtorRequest atorRequest = new AtorRequest(nome, dataNascimento, statusCarreira, anoInicioAtividade);

        String nome2 = "Mel Gibson";
        LocalDate dataNascimento2 = LocalDate.of(1956, Month.JANUARY, 3);
        StatusCarreira statusCarreira2 = StatusCarreira.EM_ATIVIDADE;
        Integer anoInicioAtividade2 = 1976;
        AtorRequest atorRequest2 = new AtorRequest(nome2, dataNascimento2, statusCarreira2, anoInicioAtividade2);

        atorService.criarAtor(atorRequest);
        atorService.criarAtor(atorRequest2);

        List<AtorEmAtividade> atorEmAtividades = atorService.listarAtoresEmAtividade("mit");

        assertEquals(1, atorEmAtividades.size());
    }

    @Test
    public void deveRetornarAtoresEmAtividadeQuandoNaoInformadoFiltro() throws Exception {
        String nome = "Will Smith";
        LocalDate dataNascimento = LocalDate.of(1968, Month.SEPTEMBER, 25);
        StatusCarreira statusCarreira = StatusCarreira.EM_ATIVIDADE;
        Integer anoInicioAtividade = 1986;
        AtorRequest atorRequest = new AtorRequest(nome, dataNascimento, statusCarreira, anoInicioAtividade);

        String nome2 = "Mel Gibson";
        LocalDate dataNascimento2 = LocalDate.of(1956, Month.JANUARY, 3);
        StatusCarreira statusCarreira2 = StatusCarreira.EM_ATIVIDADE;
        Integer anoInicioAtividade2 = 1976;
        AtorRequest atorRequest2 = new AtorRequest(nome2, dataNascimento2, statusCarreira2, anoInicioAtividade2);

        String nomeAtriz = "Cameron Diaz";
        LocalDate dataNascimentoAtriz = LocalDate.of(1972, Month.AUGUST, 30);
        StatusCarreira statusCarreiraAtriz = StatusCarreira.APOSENTADO;
        Integer anoInicioAtividadeAtriz = 1990;
        AtorRequest atrizRequest = new AtorRequest(nomeAtriz, dataNascimentoAtriz, statusCarreiraAtriz, anoInicioAtividadeAtriz);

        atorService.criarAtor(atorRequest);
        atorService.criarAtor(atorRequest2);
        atorService.criarAtor(atrizRequest);

        List<AtorEmAtividade> atorEmAtividades = atorService.listarAtoresEmAtividade(null);

        assertEquals(2, atorEmAtividades.size());
    }

    @Test(expected = Exception.class)
    public void deveRetornarErroQuandoNenhumAtorCadastradoNaConsultaDeTodosAtores() throws Exception {
        try {
            atorService.consultarAtores();
        } catch (Exception e) {
            String expected = "Nenhum ator cadastrado, favor cadastar atores.";
            assertEquals(expected, e.getMessage());
            throw e;
        }
    }

    @Test
    public void deveRetornarTodosOsAtores() throws Exception {
        String nome = "Will Smith";
        LocalDate dataNascimento = LocalDate.of(1968, Month.SEPTEMBER, 25);
        StatusCarreira statusCarreira = StatusCarreira.EM_ATIVIDADE;
        Integer anoInicioAtividade = 1986;
        AtorRequest atorRequest = new AtorRequest(nome, dataNascimento, statusCarreira, anoInicioAtividade);

        String nome2 = "Mel Gibson";
        LocalDate dataNascimento2 = LocalDate.of(1956, Month.JANUARY, 3);
        StatusCarreira statusCarreira2 = StatusCarreira.EM_ATIVIDADE;
        Integer anoInicioAtividade2 = 1976;
        AtorRequest atorRequest2 = new AtorRequest(nome2, dataNascimento2, statusCarreira2, anoInicioAtividade2);

        String nomeAtriz = "Cameron Diaz";
        LocalDate dataNascimentoAtriz = LocalDate.of(1972, Month.AUGUST, 30);
        StatusCarreira statusCarreiraAtriz = StatusCarreira.APOSENTADO;
        Integer anoInicioAtividadeAtriz = 1990;
        AtorRequest atrizRequest = new AtorRequest(nomeAtriz, dataNascimentoAtriz, statusCarreiraAtriz, anoInicioAtividadeAtriz);

        atorService.criarAtor(atorRequest);
        atorService.criarAtor(atorRequest2);
        atorService.criarAtor(atrizRequest);

        List<Ator> atores = atorService.consultarAtores();

        assertEquals(3, atores.size());
    }

    @Test(expected = Exception.class)
    public void deveRetornarErroQuandoConsultaAtorNaoInformarId() throws Exception {
        try {
            atorService.consultarAtor(null);
        } catch (Exception e) {
            String expected = "Campo obrigatório não informado. Favor informar o campo id.";
            assertEquals(expected, e.getMessage());
            throw e;
        }
    }

    @Test(expected = Exception.class)
    public void deveRetornarErroQuandoConsultaAtorNaoEncontrarAtorPorId() throws Exception {
        String nome = "Will Smith";
        LocalDate dataNascimento = LocalDate.of(1968, Month.SEPTEMBER, 25);
        StatusCarreira statusCarreira = StatusCarreira.EM_ATIVIDADE;
        Integer anoInicioAtividade = 1986;
        AtorRequest atorRequest = new AtorRequest(nome, dataNascimento, statusCarreira, anoInicioAtividade);

        atorService.criarAtor(atorRequest);
        try {
            atorService.consultarAtor(1000);
        } catch (Exception e) {
            String expected = "Nenhum ator encontrado com o parâmetro id=1000, favor verifique os parâmetros informados.";
            assertEquals(expected, e.getMessage());
            throw e;
        }
    }

    @Test
    public void deveRetornarAtorPorId() throws Exception {
        String nome = "Will Smith";
        LocalDate dataNascimento = LocalDate.of(1968, Month.SEPTEMBER, 25);
        StatusCarreira statusCarreira = StatusCarreira.EM_ATIVIDADE;
        Integer anoInicioAtividade = 1986;
        AtorRequest atorRequest = new AtorRequest(nome, dataNascimento, statusCarreira, anoInicioAtividade);

        atorService.criarAtor(atorRequest);

        Ator ator = atorService.consultarAtor(1);

        assertEquals(nome, ator.getNome());
    }
}