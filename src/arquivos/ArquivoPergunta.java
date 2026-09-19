package arquivos;

import aed3.ArvoreBMais;

import entidades.Pergunta;
import indices.ParUsuarioPergunta;

public class ArquivoPergunta extends aed3.Arquivo<Pergunta> {
    private ArvoreBMais<ParUsuarioPergunta> indiceUsuarioPergunta;

    public ArquivoPergunta() throws Exception {
        super("perguntas", Pergunta.class.getConstructor());

        indiceUsuarioPergunta = new ArvoreBMais<>(
            ParUsuarioPergunta.class.getConstructor(),
            5,
            ".\\dados\\perguntas\\indiceUsuarioPergunta.db"
        );
    }

    public ArvoreBMais<ParUsuarioPergunta> getIndiceUsuarioPergunta() {
        return indiceUsuarioPergunta;
    }

    @Override
    public int create(Pergunta p) throws Exception {
        int id = super.create(p);
        indiceUsuarioPergunta.create(new ParUsuarioPergunta(p.getIdUsuario(), id));
        return id;
    }
}