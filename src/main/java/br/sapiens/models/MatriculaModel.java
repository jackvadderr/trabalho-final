package br.sapiens.models;

public class MatriculaModel {
    private Integer id;
    private DisciplinaModel disciplina;
    private Integer idDisciplina;

    private AlunoModel aluno;
    private Integer idAluno;

    private PeriodoEnum periodo;
    private Integer idPeriodo;

    public MatriculaModel(DisciplinaModel disciplina, int idDisciplina, AlunoModel aluno, int idAluno, PeriodoEnum periodo, int idPeriodo) {
        this.disciplina = disciplina;
        this.idDisciplina = idDisciplina;
        this.aluno = aluno;
        this.idAluno = idAluno;
        this.periodo = periodo;
        this.idPeriodo = idPeriodo;
    }

    public Integer getId(){
        return id;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public DisciplinaModel getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(DisciplinaModel disciplina) {
        this.disciplina = disciplina;
    }

    public Integer getIdDisciplina() {
        return idDisciplina;
    }

    public void setIdDisciplina(int idDisciplina) {
        this.idDisciplina = idDisciplina;
    }

    public AlunoModel getAluno() {
        return aluno;
    }

    public void setAluno(AlunoModel aluno) {
        this.aluno = aluno;
    }

    public Integer getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(int idAluno) {
        this.idAluno = idAluno;
    }

    public PeriodoEnum getPeriodo() {
        return periodo;
    }

    public void setPeriodo(PeriodoEnum periodo) {
        this.periodo = periodo;
    }

    public Integer getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(int idPeriodo) {
        this.idPeriodo = idPeriodo;
    }
}
