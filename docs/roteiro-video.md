# Roteiro do vídeo e lista de prints

Documento de apoio para a gravação. Não faz parte do relatório — pode ser
apagado antes da entrega, se preferirem.

O vídeo tem **limite de 5 minutos** e precisa cobrir as oito operações que o
enunciado lista. O roteiro abaixo cobre todas, em uma única sessão contínua, sem
precisar reiniciar o programa.

---

## 1. Antes de apertar o REC

1. **Apague os dados antigos**, para a demonstração começar do zero:
   ```bash
   rm -rf dados
   ```
2. **Compile antes de gravar** (não gaste tempo de vídeo com isso):
   ```bash
   javac -encoding UTF-8 -d bin $(find src -name "*.java")
   ```
3. **Aumente a fonte do terminal** e maximize a janela. Texto pequeno é o erro
   mais comum nesse tipo de vídeo.
4. **Deixe o VS Code aberto** em uma segunda janela, já com os dois arquivos que
   serão explicados abertos em abas:
   - `src/arquivos/ArquivoUsuario.java`
   - `src/menus/MenuPerguntas.java`
5. **Teste o microfone** gravando 10 segundos e ouvindo.

> ⚠️ **Armadilha do menu de perguntas:** as opções **C (Alterar)** e
> **D (Arquivar)** só funcionam depois de usar a opção **A (Listar)**, porque é a
> listagem que monta a associação entre o número da tela e o ID real da
> pergunta. Se pular o Listar, aparece "Nenhuma pergunta carregada". O roteiro
> já está na ordem certa.

> ⚠️ **Navegação do Minha área:** o menu *Minha área* executa uma opção e volta
> sozinho para o menu principal. Para entrar em *Minhas perguntas* depois de
> mexer em *Meus dados*, é preciso escolher **A** no menu principal de novo.

---

## 2. Roteiro

Tempo total estimado: **4min 55s**. Os valores a digitar estão em `código`.

### Abertura — 10s

> "Olá, professor. Este é o Trabalho Prático 1 de AEDs III, o sistema AJUDA AÍ,
> feito por Lucas José Souza Rodrigues, Luca Maciel, Matheus Mendes Senna e
> Saulo. Vou demonstrar as operações do sistema."

Execute:
```bash
java -cp bin Principal
```

### ① Cadastro de um novo usuário — 25s

Digite: `B`

| Campo | Valor |
|---|---|
| E-mail | `ana@exemplo.com` |
| Nome completo | `Ana Ribeiro Costa` |
| Senha | `senha123` |
| Pergunta secreta | `Qual o nome do meu primeiro animal de estimação?` |
| Resposta secreta | `Rex` |

> "O cadastro é a única forma de entrar no sistema. A senha não é gravada: o que
> vai para o arquivo é o hash SHA-256 dela. A resposta secreta também vira hash,
> mas antes passa por uma normalização que remove acentos e converte para
> minúsculas — já já isso vai fazer diferença."

📸 **Print 01** aqui (tela com "Usuário cadastrado com sucesso! ID do usuário: 1").

### ② Login falhando e recuperação de senha — 45s

Digite: `A` → `ana@exemplo.com` → `senhaErrada`

> "O e-mail e a senha são conferidos de uma só vez, e a mensagem de erro é a
> mesma nos dois casos, para não revelar qual dos dois está errado."

📸 **Print 02** aqui (erro + as três opções).

Digite: `B` (recuperar senha) → `ana@exemplo.com`

> "O sistema busca o usuário pelo e-mail, usando a tabela hash extensível, e
> mostra a pergunta secreta que ele cadastrou."

Digite a resposta em **letras maiúsculas**: `REX`

> "Repare que cadastrei 'Rex' com R maiúsculo apenas, e estou digitando 'REX'.
> Funciona porque a comparação é feita sobre o hash da resposta normalizada —
> acento e caixa de letra não impedem a recuperação."

Nova senha: `novaSenha456` → confirmação: `novaSenha456`

📸 **Print 03** aqui ("Senha alterada com sucesso!").

### ③ Login correto — 10s

O sistema volta sozinho para a tela de login.

Digite: `ana@exemplo.com` → `novaSenha456`

> "E agora entro com a senha que acabei de definir."

📸 **Print 04** aqui (menu principal com "Usuário: Ana Ribeiro Costa").

### ④ Atualização do e-mail + explicação do código — 60s

Digite: `A` (Minha área) → `A` (Meus dados) → `B` (Alterar email) →
`ana.costa@exemplo.com`

> "O e-mail mudou, e o menu já reexibe o dado atualizado."

📸 **Print 05** aqui (Meus dados mostrando o e-mail novo).

**Agora vá para o VS Code**, em `src/arquivos/ArquivoUsuario.java`, método
`update()`. Fale enquanto aponta o código (o trecho está na seção 3 deste
documento):

> "Esta é a parte mais delicada do CRUD de usuários. O usuário é identificado
> pelo e-mail, mas o e-mail pode mudar — o ID é que nunca muda. Por isso, antes
> de sobrescrever o registro, eu leio o usuário antigo para saber qual era o
> e-mail dele. O `super.update()` grava o registro e cuida do índice direto.
> Depois eu comparo os dois e-mails: se mudou, removo a chave antiga da tabela
> hash extensível e insiro a nova, apontando para o mesmo ID. Sem isso, o
> login com o e-mail novo não encontraria ninguém, e o e-mail antigo continuaria
> funcionando."

**Volte ao terminal.** Digite: `R` (retornar) → `S` (sair) → `A` (login) →
`ana.costa@exemplo.com` → `novaSenha456`

> "E aqui está a prova de que o índice foi atualizado: o login funciona com o
> e-mail novo."

### ⑤ Cadastro de perguntas — 30s

Digite: `A` (Minha área) → `B` (Minhas perguntas) → `B` (Incluir)

| Campo | Valor |
|---|---|
| Pergunta | `É seguro comer pão mofado, se você cortar a parte mofada fora?` |
| Palavras-chave | `pão;mofado;saúde` |

Digite `B` de novo:

| Campo | Valor |
|---|---|
| Pergunta | `Para quem está começando a programar agora, qual a linguagem recomendada?` |
| Palavras-chave | `programação;linguagem` |

E `B` uma terceira vez:

| Campo | Valor |
|---|---|
| Pergunta | `Por que a luz azul das telas atrapalha o nosso sono?` |
| Palavras-chave | `luz azul;sono` |

> "Só peço o texto e as palavras-chave. O ID do usuário vem de quem está logado,
> as datas vêm do relógio do computador e a nota começa em zero. A pergunta é
> vinculada ao usuário na mesma operação em que é criada."

📸 **Print 06** aqui ("Pergunta cadastrada com sucesso!").

### ⑥ Listagem de perguntas — 15s

Digite: `A` (Listar)

> "A listagem não varre o arquivo de perguntas. Ela percorre a árvore B+ com o
> par idUsuário–idPergunta, que é o nosso relacionamento 1:N, e filtra pelo
> usuário logado. As perguntas são numeradas na tela, com a data de criação. Os
> IDs não aparecem, porque são de uso interno."

📸 **Print 07** aqui (as três perguntas numeradas com data).

### ⑦ Atualização de uma pergunta — 25s

Digite: `C` (Alterar) → `2`

- Nova pergunta: `Para quem está começando a programar agora, qual linguagem é a mais recomendada?`
- Novas palavras-chave: *(deixe vazio, aperte Enter)*

> "Informo o número que apareceu na tela, e o sistema converte esse número para o
> ID real da pergunta. Campo deixado em branco não é alterado, e a data de
> alteração é ajustada automaticamente."

📸 **Print 08** aqui ("Pergunta alterada com sucesso!").

### ⑧ Arquivamento + explicação do código — 60s

Digite: `A` (Listar, para atualizar os números) → `D` (Arquivar) → `3` → `S`

> "Confirmado o arquivamento..."

Digite: `A` (Listar)

> "...a pergunta continua aparecendo para o autor, agora marcada como ARQUIVADA,
> e sai das buscas dos outros usuários."

📸 **Print 09** aqui (listagem com a pergunta 3 marcada ARQUIVADA).

**Vá para o VS Code**, em `src/menus/MenuPerguntas.java`, método `arquivar()`:

> "O enunciado pede que perguntas não sejam excluídas, e sim arquivadas — porque
> apagar uma pergunta levaria junto as respostas e os votos que outras pessoas
> deixaram nela. Então não existe delete aqui: é exclusão lógica. Pego o ID real
> a partir do número da tela, verifico se a pergunta já não está arquivada,
> peço confirmação, e então mudo o campo `ativa` para `false` e atualizo a data
> de alteração. O `update` reescreve o registro no arquivo. O registro continua
> lá, e o par continua na árvore B+ — nada é perdido. Quem filtra o que aparece
> é a tela de busca, que só lista perguntas com `ativa` igual a `true`, e o menu
> de respostas, que recusa responder uma pergunta inativa. O arquivamento é
> definitivo: não existe opção de desarquivar em nenhum menu."

### Fechamento — 15s

> "É isso, professor. O CRUD de usuários e o de perguntas estendem a classe
> Arquivo, com tabela hash extensível como índice direto e também como índice
> indireto de e-mail, e a árvore B+ registrando o relacionamento 1:N entre
> usuários e perguntas. Todos os detalhes estão no readme do repositório.
> Obrigado!"

---

## 3. Os dois trechos de código a explicar

Deixe estas duas telas prontas no VS Code antes de gravar.

### 3.1. Atualização de e-mail — `src/arquivos/ArquivoUsuario.java`

```java
@Override
public boolean update(Usuario novoUsuario) throws Exception {
    Usuario usuarioVelho = read(novoUsuario.getId());

    if(super.update(novoUsuario)) {
        if(novoUsuario.getEmail().compareTo(usuarioVelho.getEmail()) != 0) {

            indiceIndiretoEmail.delete(ParEmailID.hash(usuarioVelho.getEmail()));

            indiceIndiretoEmail.create(
                new ParEmailID(novoUsuario.getEmail(), novoUsuario.getId())
            );
        }
        return true;
    }
    return false;
}
```

Os quatro pontos a citar, nesta ordem:

1. `read()` antes do update — é a única chance de saber qual era o e-mail antigo.
2. `super.update()` grava o registro e mantém o índice direto (ID → endereço).
3. A comparação: só mexe no índice se o e-mail realmente mudou.
4. `delete` da chave antiga + `create` da nova — as duas, senão o índice fica
   inconsistente.

Se sobrar tempo, cite também `MenuUsuario.alterarEmail()`, que antes de tudo
verifica se o novo e-mail já pertence a outro usuário.

### 3.2. Arquivamento — `src/menus/MenuPerguntas.java`

```java
int idReal = mapPerguntas.get(numero - 1);
Pergunta p = arqPerguntas.read(idReal);

if (p != null) {
    if (!p.ativa) {
        System.out.println("\nEsta pergunta já está arquivada.");
        return;
    }

    System.out.print("Confirma o arquivamento desta pergunta? (S/N): ");
    // ...
    if (confirma == 'S') {
        p.ativa = false;
        p.alteracao = System.currentTimeMillis();

        if (arqPerguntas.update(p)) {
            System.out.println("\nPergunta arquivada com sucesso!");
        }
    }
}
```

Os pontos a citar:

1. `mapPerguntas.get(numero - 1)` — converte o número da tela no ID real.
2. A guarda `!p.ativa` — não arquiva duas vezes.
3. `p.ativa = false` — exclusão **lógica**, nenhum registro é apagado.
4. `p.alteracao = System.currentTimeMillis()` — a data é sempre ajustada.
5. O efeito: `MenuUsuario.buscarPerguntas()` filtra por `p.ativa`, e
   `MenuResposta.menu()` recusa pergunta inativa.

---

## 4. Prints para o relatório

Os arquivos vão em `docs/img/` e os marcadores já estão no `README.md`, cada um
no lugar certo. Use exatamente estes nomes.

Os prints **01 a 09** saem da mesma sessão do vídeo, nos pontos marcados 📸.
Os prints **10 a 12** exigem um segundo usuário e ficam para depois.

| Arquivo | Quando tirar | O que precisa estar visível |
|---|---|---|
| `01-acesso.png` | Passo ① | Menu AJUDA AÍ + cadastro preenchido + "Usuário cadastrado com sucesso! ID do usuário: 1" |
| `02-login.png` | Passo ② | "Nome/e-mail ou senha incorretos." seguido das opções Tentar novamente / Recuperar senha / Retornar |
| `03-recuperar-senha.png` | Passo ② | A pergunta secreta na tela, a resposta `REX` digitada e "Senha alterada com sucesso!" |
| `04-menu-principal.png` | Passo ③ | Menu principal com "Usuário: Ana Ribeiro Costa" |
| `05-meus-dados.png` | Passo ④ | Tela Meus dados já com `ana.costa@exemplo.com` |
| `06-incluir.png` | Passo ⑤ | Tela Incluir pergunta preenchida + "Pergunta cadastrada com sucesso!" |
| `07-listar.png` | Passo ⑥ | As três perguntas numeradas `(1) (2) (3)` com data e palavras-chave |
| `08-alterar.png` | Passo ⑦ | Pergunta atual, pergunta nova digitada e "Pergunta alterada com sucesso!" |
| `09-arquivar.png` | Passo ⑧ | A listagem final, com a `(3) ARQUIVADA` |

### Prints 10 a 12 — sessão extra, depois do vídeo

Não precisam entrar no vídeo (o enunciado não pede), mas o relatório tem as
telas. Para tirá-los, cadastre um segundo usuário — é preciso, porque **ninguém
pode votar no próprio conteúdo**.

1. Saia e cadastre: `bruno@exemplo.com` / `Bruno Alves Pinto` / `outrasenha` /
   pergunta secreta `Cidade onde nasci?` / resposta `Belo Horizonte`.
2. Entre como Bruno → `B` (Buscar perguntas).

| Arquivo | Onde | O que precisa estar visível |
|---|---|---|
| `10-buscar.png` | Buscar perguntas | As perguntas com autor e data, **sem a arquivada**, e a tela de detalhes com "Criada em" e "Alterada em" |
| `11-respostas.png` | Detalhes → `A` → `B` | Resposta cadastrada, depois a listagem mostrando texto, autor e nota |
| `12-meus-votos.png` | Minha área → `D` | Pelo menos um voto listado |

Sequência sugerida para os três de uma vez, como Bruno:

```
B          Buscar perguntas
1          abre a primeira pergunta            → print 10
A          Ver respostas
B          Responder → escreva uma resposta
A          Listar respostas                    → print 11
R          Retornar
B          Votar nesta pergunta → digite 1
R          Retornar
A          Minha área
D          Meus votos                          → print 12
```

> Para o print 12 ficar mais rico, entre como Ana depois e vote na resposta do
> Bruno: `B` → `1` → `A` → `C` → `1` → `1`. Aí *Meus votos* da Ana mostra um voto
> em resposta, e o do Bruno, um voto em pergunta.

---

## 5. Se o vídeo passar de 5 minutos

Corte nesta ordem, sempre preservando as oito operações obrigatórias:

1. O re-login com o e-mail novo, no fim do passo ④ (15s).
2. A terceira pergunta no passo ⑤ — duas bastam para demonstrar (10s).
3. Encurte a abertura e o fechamento para uma frase cada (15s).
4. Nas explicações de código, fique nos pontos 1 a 3 de cada lista e pule o
   resto (20s).

Não corte: nenhuma das oito operações, e nenhuma das duas explicações de código
— as duas são exigidas explicitamente pelo enunciado.
