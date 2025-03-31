package Unknown.service;
import java.time.LocalDate;
import Unknown.Aluno;
import Unknown.ConnectionDB;
import Unknown.Professor;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class GerenciamentoEscola {
    private ConnectionDB connection;

    public GerenciamentoEscola(){
        this.connection = new ConnectionDB();
    }

    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public void abrir(Aluno aluno) {
        Connection conn = connection.getConnection();
        new EscolaDAO(conn).salvarAluno(aluno);
    }

    public void abrirProfessor(Professor professor){
        Connection conn = connection.getConnection();
        new EscolaDAO(conn).salvarProfessor(professor);
    }

    public void listarAluno(){
        System.out.println("Contas Cadastradas :");
        Connection conn = connection.getConnection();
        Set<Aluno> alunos = new EscolaDAO(conn).listarAluno();
        alunos.forEach(System.out::println);
    }

    public void listarProfessores(){
        System.out.println("Professores Cadastrados :");
        Connection conn = connection.getConnection();
        Set<Professor> professores = new EscolaDAO(conn).listarProfessor();
        professores.forEach(System.out::println);
    }

    public void procurarAlunoPorNome(String nome) {
        System.out.println("Procurando aluno " + nome + "...");
        Connection conn = connection.getConnection();
        Aluno aluno = new EscolaDAO(conn).procurarAlunoPorNome(nome);
        if(aluno == null){
            System.out.println("Não foi possível encontrar esse aluno !");
        }else{
            System.out.println(aluno);
        }
    }

    public void procurarProfessorPorNome(String nome) {
        System.out.println("Procurando Professor " + nome + "...");
        Connection conn = connection.getConnection();
        Professor professor = new EscolaDAO(conn).procurarProfessorPorNome(nome);
        if(professor == null){
            System.out.println("Professor não encontrado !");
        }else{
            System.out.println(professor);
        }
    }

    public void removerAlunoPorNome(String nome) {
        System.out.println("Removendo Aluno " + nome + "...");
        Connection conn = connection.getConnection();
        new EscolaDAO(conn).removerAlunoPorNome(nome);
    }

    public void removerProfessorPorNome(String nome){
        System.out.println("Removendo Professor " + nome + "...");
        Connection conn = connection.getConnection();
        new EscolaDAO(conn).removerProfessorPorNome(nome);
    }

    public void alterarAluno(String nomeAntigo, String novoNome, String novoCurso){
        System.out.println("Alterando os dados do Aluno " + nomeAntigo + "...");
        Connection conn = connection.getConnection();
        new EscolaDAO(conn).alterarAluno(nomeAntigo,novoNome,novoCurso);
    }

    public void chamadaAlunos(){
        System.out.println("Verificando presença dos alunos...");
        Connection conn = connection.getConnection();
        List<Aluno> alunos = new EscolaDAO(conn).chamadaAlunos();
        System.out.println("Alunos presentes no dia de hoje ("+ now.format(formatter) +") :");
        for (Aluno aluno : alunos) {
            System.out.println(aluno);
        }
    }
}
