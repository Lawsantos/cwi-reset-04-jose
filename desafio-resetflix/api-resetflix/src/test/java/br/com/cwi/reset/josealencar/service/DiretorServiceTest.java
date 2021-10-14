package br.com.cwi.reset.josealencar.service;

import br.com.cwi.reset.josealencar.FakeDatabase;
import br.com.cwi.reset.josealencar.model.Diretor;
import br.com.cwi.reset.josealencar.request.DiretorRequest;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.junit.Assert.*;

public class DiretorServiceTest {

    private DiretorService diretorService;

    @Before
    public void setUp() {
        FakeDatabase fakeDatabase = new FakeDatabase();
        diretorService = new DiretorService(fakeDatabase);
    }

    @Test(expected = Exception.class)
    public void deveRetornarErroQuandoNomeNulo() throws Exception {
        String nome = null;
        LocalDate dataNascimento = LocalDate.of(1956, Month.JANUARY, 3);
        Integer anoInicioAtividade = 1976;
        DiretorRequest diretorRequest = new DiretorRequest(nome, dataNascimento, anoInicioAtividade);

        try {
            diretorService.cadastrarDiretor(diretorRequest);
            assertTrue(false);
        } catch (Exception re) {
            String expected = "Campo obrigatório não informado. Favor informar o campo nome.";
            assertEquals(expected, re.getMessage());
            throw re;
        }
    }

    @Test(expected = Exception.class)
    public void deveRetornarErroQuandoDataNascimentoNulo() throws Exception {
        String nome = "Mel Gibson";
        LocalDate dataNascimento = null;
        Integer anoInicioAtividade = 1976;
        DiretorRequest diretorRequest = new DiretorRequest(nome, dataNascimento, anoInicioAtividade);

        try {
            diretorService.cadastrarDiretor(diretorRequest);
            assertTrue(false);
        } catch (Exception re) {
            String expected = "Campo obrigatório não informado. Favor informar o campo dataNascimento.";
            assertEquals(expected, re.getMessage());
            throw re;
        }
    }

    @Test(expected = Exception.class)
    public void deveRetornarErroQuandoAnoInicioAtividadeNulo() throws Exception {
        String nome = "Mel Gibson";
        LocalDate dataNascimento = LocalDate.of(1956, Month.JANUARY, 3);
        Integer anoInicioAtividade = null;
        DiretorRequest diretorRequest = new DiretorRequest(nome, dataNascimento, anoInicioAtividade);

        try {
            diretorService.cadastrarDiretor(diretorRequest);
            assertTrue(false);
        } catch (Exception re) {
            String expected = "Campo obrigatório não informado. Favor informar o campo anoInicioAtividade.";
            assertEquals(expected, re.getMessage());
            throw re;
        }
    }

    @Test(expected = Exception.class)
    public void deveRetornarErroQuandoDiretorPossuirSomenteNome() throws Exception {
        String nome = "Mel";
        LocalDate dataNascimento = LocalDate.of(1956, Month.JANUARY, 3);
        Integer anoInicioAtividade = 1976;
        DiretorRequest diretorRequest = new DiretorRequest(nome, dataNascimento, anoInicioAtividade);

        try {
            diretorService.cadastrarDiretor(diretorRequest);
            assertTrue(false);
        } catch (Exception re) {
            String expected = "Deve ser informado no mínimo nome e sobrenome para o diretor.";
            assertEquals(expected, re.getMessage());
            throw re;
        }
    }

    @Test(expected = Exception.class)
    public void deveRetornarErroQuandoDiretorNaoNascido() throws Exception {
        String nome = "Mel Gibson";
        LocalDate dataNascimento = LocalDate.of(LocalDate.now().getYear() + 10, Month.JANUARY, 3);
        Integer anoInicioAtividade = 1976;
        DiretorRequest diretorRequest = new DiretorRequest(nome, dataNascimento, anoInicioAtividade);

        try {
            diretorService.cadastrarDiretor(diretorRequest);
            assertTrue(false);
        } catch (Exception re) {
            String expected = "Não é possível cadastrar diretores não nascidos.";
            assertEquals(expected, re.getMessage());
            throw re;
        }
    }

    @Test(expected = Exception.class)
    public void deveRetornarErroQuandoDiretorComInicioAtividadeAnteriorNascimento() throws Exception {
        String nome = "Mel Gibson";
        LocalDate dataNascimento = LocalDate.of(1956, Month.JANUARY, 3);
        Integer anoInicioAtividade = 1926;
        DiretorRequest diretorRequest = new DiretorRequest(nome, dataNascimento, anoInicioAtividade);

        try {
            diretorService.cadastrarDiretor(diretorRequest);
            assertTrue(false);
        } catch (Exception re) {
            String expected = "Ano de início de atividade inválido para o diretor cadastrado.";
            assertEquals(expected, re.getMessage());
            throw re;
        }
    }

    @Test(expected = Exception.class)
    public void deveRetornarErroQuandoDiretorJaExistenteComMesmoNome() throws Exception {
        String nome = "Mel Gibson";
        String nome2 = "mel gibson";
        LocalDate dataNascimento = LocalDate.of(1956, Month.JANUARY, 3);
        Integer anoInicioAtividade = 1976;
        DiretorRequest diretorRequest = new DiretorRequest(nome, dataNascimento, anoInicioAtividade);
        DiretorRequest diretorRequest2 = new DiretorRequest(nome2, dataNascimento, anoInicioAtividade);

        try {
            diretorService.cadastrarDiretor(diretorRequest);
            diretorService.cadastrarDiretor(diretorRequest2);
        } catch (Exception re) {
            String expected = "Já existe um diretor cadastrado para o nome mel gibson.";
            assertEquals(expected, re.getMessage());
            throw re;
        }
    }

    @Test
    public void deveSalvarDiretorEConsultarListaComUmNovoDiretor() throws Exception {
        String nome = "Mel Gibson";
        LocalDate dataNascimento = LocalDate.of(1956, Month.JANUARY, 3);
        Integer anoInicioAtividade = 1976;
        DiretorRequest diretorRequest = new DiretorRequest(nome, dataNascimento, anoInicioAtividade);

        diretorService.cadastrarDiretor(diretorRequest);

        assertEquals(1, diretorService.listarDiretores(null).size());
    }

    @Test(expected = Exception.class)
    public void deveRetornarErroQuandoNenhumDiretorCadastradoNaConsulta() throws Exception {
        try {
            diretorService.listarDiretores(null);
        } catch (Exception e) {
            String expected = "Nenhum diretor cadastrado, favor cadastar diretores.";
            assertEquals(expected, e.getMessage());
            throw e;
        }
    }

    @Test(expected = Exception.class)
    public void deveRetornarErroQuandoNenhumDiretorCadastradoComFiltroInformado() throws Exception {
        String nome = "Mel Gibson";
        LocalDate dataNascimento = LocalDate.of(1956, Month.JANUARY, 3);
        Integer anoInicioAtividade = 1976;
        DiretorRequest diretorRequest = new DiretorRequest(nome, dataNascimento, anoInicioAtividade);

        diretorService.cadastrarDiretor(diretorRequest);
        try {
            diretorService.listarDiretores("teste");
        } catch (Exception e) {
            String expected = "Diretor não encontrato com o filtro teste, favor informar outro filtro.";
            assertEquals(expected, e.getMessage());
            throw e;
        }
    }

    @Test
    public void deveRetornarApenasFiltradosPorNome() throws Exception {
        String nome = "Mel Gibson";
        LocalDate dataNascimento = LocalDate.of(1956, Month.JANUARY, 3);
        Integer anoInicioAtividade = 1976;
        DiretorRequest diretorRequest = new DiretorRequest(nome, dataNascimento, anoInicioAtividade);

        String nome2 = "Michael Hoffman";
        LocalDate dataNascimento2 = LocalDate.of(1956, Month.NOVEMBER, 30);
        Integer anoInicioAtividade2 = 1982;
        DiretorRequest diretorRequest2 = new DiretorRequest(nome2, dataNascimento2, anoInicioAtividade2);

        diretorService.cadastrarDiretor(diretorRequest);
        diretorService.cadastrarDiretor(diretorRequest2);

        List<Diretor> diretores = diretorService.listarDiretores("gib");

        assertEquals(1, diretores.size());
    }

    @Test
    public void deveRetornarTodosOsDiretores() throws Exception {
        String nome = "Mel Gibson";
        LocalDate dataNascimento = LocalDate.of(1956, Month.JANUARY, 3);
        Integer anoInicioAtividade = 1976;
        DiretorRequest diretorRequest = new DiretorRequest(nome, dataNascimento, anoInicioAtividade);

        String nome2 = "Michael Hoffman";
        LocalDate dataNascimento2 = LocalDate.of(1956, Month.NOVEMBER, 30);
        Integer anoInicioAtividade2 = 1982;
        DiretorRequest diretorRequest2 = new DiretorRequest(nome2, dataNascimento2, anoInicioAtividade2);

        diretorService.cadastrarDiretor(diretorRequest);
        diretorService.cadastrarDiretor(diretorRequest2);

        List<Diretor> diretores = diretorService.listarDiretores(null);

        assertEquals(2, diretores.size());
    }

    @Test(expected = Exception.class)
    public void deveRetornarErroQuandoConsultaDiretorNaoInformarId() throws Exception {
        try {
            diretorService.consultarDiretor(null);
        } catch (Exception e) {
            String expected = "Campo obrigatório não informado. Favor informar o campo id.";
            assertEquals(expected, e.getMessage());
            throw e;
        }
    }

    @Test(expected = Exception.class)
    public void deveRetornarErroQuandoConsultaDiretorNaoEncontrarDiretorPorId() throws Exception {
        String nome = "Mel Gibson";
        LocalDate dataNascimento = LocalDate.of(1956, Month.JANUARY, 3);
        Integer anoInicioAtividade = 1976;
        DiretorRequest diretorRequest = new DiretorRequest(nome, dataNascimento, anoInicioAtividade);

        diretorService.cadastrarDiretor(diretorRequest);
        try {
            diretorService.consultarDiretor(1000);
        } catch (Exception e) {
            String expected = "Nenhum diretor encontrado com o parâmetro id=1000, favor verifique os parâmetros informados.";
            assertEquals(expected, e.getMessage());
            throw e;
        }
    }

    @Test
    public void deveRetornarDiretorPorId() throws Exception {
        String nome = "Mel Gibson";
        LocalDate dataNascimento = LocalDate.of(1956, Month.JANUARY, 3);
        Integer anoInicioAtividade = 1976;
        DiretorRequest diretorRequest = new DiretorRequest(nome, dataNascimento, anoInicioAtividade);

        diretorService.cadastrarDiretor(diretorRequest);

        Diretor diretor = diretorService.consultarDiretor(1);

        assertEquals(nome, diretor.getNome());
    }
}