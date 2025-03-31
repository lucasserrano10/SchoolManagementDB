package Unknown;

public class Professor {
    private String nome;
    private String materia;

    public Professor(String nome, String materia) {
        this.nome = nome;
        this.materia = materia;
    }

    @Override
    public String toString() {
        return "Professor{" +
                "nome='" + nome + '\'' +
                ", materia='" + materia + '\'' +
                '}';
    }

    public String getMateria() {
        return materia;
    }

    public String getNome() {
        return nome;
    }

}
