package Unknown;

import java.util.UUID;

public class Aluno {
    private String nome;
    private String curso;

    public Aluno(String nome, String curso) {
        this.nome = nome;
        this.curso = curso;
    }

    @Override
    public String toString() {
        return "Aluno{" +
                "nome='" + nome + '\'' +
                ", curso='" + curso + '\'' +
                '}';
    }

    public String getNome() {
        return nome;
    }

    public String getCurso() {
        return curso;
    }

}
