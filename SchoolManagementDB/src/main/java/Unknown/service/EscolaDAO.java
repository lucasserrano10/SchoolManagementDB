package Unknown.service;

import Unknown.Aluno;
import Unknown.Professor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class EscolaDAO {
    private Connection conn;

    public EscolaDAO(Connection connection){
        this.conn = connection;
    }

    public void salvarAluno(Aluno aluno){
        String sql = "INSERT INTO aluno (nome,curso) values (?, ?)";

        PreparedStatement preparedStatement;

        try {
            conn.setAutoCommit(false);
            preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setString(1,aluno.getNome());
            preparedStatement.setString(2,aluno.getCurso());

            preparedStatement.execute();
            conn.commit();
            preparedStatement.close();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
    }

    public void salvarProfessor(Professor professor){
        String sql = "INSERT INTO professor (nome, materia) VALUES (?,?)";

        PreparedStatement preparedStatement;

        try {
            conn.setAutoCommit(false);
            preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setString(1, professor.getNome());
            preparedStatement.setString(2, professor.getMateria());

            preparedStatement.execute();
            conn.commit();
            preparedStatement.close();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
    }

    public Set<Aluno> listarAluno(){
        Set<Aluno> alunos = new HashSet<>();
        String sql = "SELECT * FROM aluno";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();

            while(resultSet.next()){
                String nome = resultSet.getString(2);
                String curso = resultSet.getString(3);

                Aluno aluno = new Aluno(nome,curso);
                alunos.add(aluno);
            }
            resultSet.close();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return alunos;
    }

    public Set<Professor> listarProfessor(){
        Set<Professor> professores = new HashSet<>();
        String sql = "SELECT * FROM professor";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                String nome = rs.getString(2);
                String materiaLecionada = rs.getString(3);

                Professor professor = new Professor(nome,materiaLecionada);
                professores.add(professor);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return professores;
    }

    public Aluno procurarAlunoPorNome(String nome){
        String sql = "SELECT * FROM aluno WHERE nome = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,nome);
            ResultSet rs = ps.executeQuery();
            Aluno alunoEncontrado;
            String name;
            String curso;
            if(rs.next()){
                name = rs.getString(2);
                curso = rs.getString(3);
            }else{
                return null;
            }
            alunoEncontrado = new Aluno(name,curso);
            return alunoEncontrado;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public Professor procurarProfessorPorNome(String nome){
        String sql = "SELECT * FROM professor WHERE nome = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,nome);
            ResultSet rs = ps.executeQuery();
            Professor professorEncontrado;
            String name;
            String materiaLecionada;
            if(rs.next()){
                name = rs.getString(2);
                materiaLecionada = rs.getString(3);
            }else{
                return null;
            }
            professorEncontrado = new Professor(name,materiaLecionada);
            return professorEncontrado;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void removerAlunoPorNome(String nome){
        String sql = "DELETE FROM aluno WHERE nome = ?";
        try {
            conn.setAutoCommit(false);
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,nome);
            ps.execute();
            conn.commit();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
    }

    public void removerProfessorPorNome(String nome){
        String sql = "DELETE FROM professor WHERE nome = ?";

        try {
            conn.setAutoCommit(false);
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nome);
            ps.execute();
            conn.commit();
            conn.close();
            ps.close();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
    }

    public void alterarAluno(String nomeAntigo, String novoNome,String novoCurso){
        String sql = "UPDATE aluno SET nome = ?, curso = ? WHERE nome = ?";
        try {
            conn.setAutoCommit(false);
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,novoNome);
            ps.setString(2,novoCurso);
            ps.setString(3,nomeAntigo);
            ps.execute();
            conn.commit();
            conn.close();
            ps.close();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
    }

    public List<Aluno> chamadaAlunos(){
        Scanner sc = new Scanner(System.in);

        String sql = "SELECT * FROM aluno";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<Aluno> alunosPresentes = new ArrayList<>();
            while (rs.next()){
                String nomeAluno = rs.getString("nome");
                String cursoAluno = rs.getString("curso");
                System.out.println("Aluno -> " + nomeAluno);
                System.out.println("Está Presente? (sim/não) -> ");
                String resposta = sc.nextLine();

                if (resposta.equalsIgnoreCase("sim")) {
                    String sqlPresenca = "UPDATE aluno SET presente = TRUE WHERE nome = ?";

                    try (PreparedStatement updatePs = conn.prepareStatement(sqlPresenca)) {
                        updatePs.setString(1, nomeAluno);
                        updatePs.executeUpdate();
                        Aluno aluno = new Aluno(nomeAluno, cursoAluno);
                        alunosPresentes.add(aluno);
                    }
                }
            }
            return alunosPresentes;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
