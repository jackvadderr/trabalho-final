package br.sapiens.models;

public class MatriculaModel {

    private DisciplinaModel disciplina;
    private AlunoModel aluno;
    private PeriodoEnum periodo;

    public MatriculaModel(DisciplinaModel disciplina, AlunoModel aluno, PeriodoEnum periodo) {
        this.disciplina = disciplina;
        this.aluno = aluno;
        this.periodo = periodo;
    }

    public String getId(){
        return aluno.getId()+"-"+disciplina.getId()+"-"+periodo;
    }

    public void setId(Integer id){

    }

    public DisciplinaModel getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(DisciplinaModel disciplina) {
        this.disciplina = disciplina;
    }

    public AlunoModel getAluno() {
        return aluno;
    }

    public void setAluno(AlunoModel aluno) {
        this.aluno = aluno;
    }

    public PeriodoEnum getPeriodo() {
        return periodo;
    }

    public void setPeriodo() {
        this.periodo = periodo;
    }
}
