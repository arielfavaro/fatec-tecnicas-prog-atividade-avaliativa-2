# Respostas — Sistema de Reserva de Laboratorio

Nome completo: ______________________________________  RA: ______________

> Preencha **todos** os campos. Campo vazio é campo não avaliado.
> Não apague os títulos nem os rótulos em negrito: a correção procura por eles.

---

# Etapa 1 — Diagnóstico

## Problema 1 — a conversão do inventario patrimonial

**Em que consiste o problema, e o que acontece quando o sistema cresce:**

A conversão da matriz `String[][]` do `InventarioLegadoWS` para `Equipamento` foi
escrita duas vezes, em `CatalogoEquipamentos.listarDisponiveis()` e em
`RelatorioAcervo.listarTodos()`. E as duas possuem diferenças: o catálogo usa
`"D".equals(linha[2])` e o relatório usa `"D".equalsIgnoreCase(linha[2])`.
Foi corrigida a letra minúscula no relatório e a correção não foi aplicada no
catálogo, por isso o PAT-1003, que vem com `"d"`, aparece indisponível no
catálogo e disponível no relatório.

Quando o sistema cresce, cada nova parte que usa os equipamentos tende a copiar a
conversão de novo, e qualquer mudança no formato do legado precisa ser corrigida
em todas as cópias. A que for esquecida passa a responder diferente das outras.


**Princípio SOLID violado:**

Princípio da Responsabilidade Única (SRP). As duas classes têm dois motivos para
mudar: a regra dela mesma, listar os disponíveis ou montar o relatório e o formato
da matriz do inventário. Traduzir o formato externo é uma responsabilidade
separada, que hoje está misturada com a regra de negócio em dois lugares.

**Classes afetadas (pelo nome):**

`CatalogoEquipamentos` e `RelatorioAcervo`.

**Que outra dependência indevida essas classes têm em relação ao
inventario patrimonial, e por que ela é um problema:**

As duas dependem da classe concreta `InventarioLegadoWS`: recebem esse tipo no
construtor e chamam `consultarEquipamentos()` diretamente. Isso viola o Princípio
da Inversão de Dependência (DIP), porque a regra de negócio depende de um detalhe
de outra equipe e não de uma abstração do próprio sistema.
Se o serviço mudar ou for trocado, as duas classes precisam ser alteradas, e não dá
para usar outra fonte de dados nem testá-las sem o serviço real.

## Problema 2 — o ciclo de vida de Reserva

**Em que consiste o problema, e o que acontece quando o sistema cresce:**

A `Reserva` guarda a fase num campo `String situacao` e `retirar()`,
`devolver()` e `cancelar()` decidem com `if`/`else` sobre esse texto se a
operação é permitida e qual é a próxima fase.
As regras ficam espalhadas por operação: para saber o que vale em `EM_USO` é preciso
ler os três métodos, e um erro de digitação na `String` só aparece em execução.

Quando o sistema cresce, cada fase nova, como `ATRASADA`, obriga a alterar todos
os métodos públicos, e os condicionais aumentam com o número de fases e de
operações, arriscando quebrar transições que já funcionam.

**Princípio SOLID violado:**

Princípio do Aberto/Fechado (OCP). Para estender o ciclo de vida é preciso modificar
o código que já existe em `Reserva`, em vez de acrescentar código novo.

**Classes afetadas (pelo nome):**

`Reserva`: o campo `situacao` e os métodos `retirar()`, `devolver()` e `cancelar()`.

---

# Etapa 4 — Justificativa

## Padrão aplicado ao Problema 1

**O problema que ele resolve (descreva antes de nomear o padrão):**

O sistema precisa usar um serviço externo que não pode ser alterado e cuja
interface não é a que ele espera: o serviço devolve uma matriz de texto com a
situação em letra, e os clientes querem objetos `Equipamento`. O padrão coloca
uma classe no meio, `AdaptadorInventarioLegado`, que implementa a interface do
sistema, `FonteDeEquipamentos`, chama o serviço por composição e faz a conversão
uma vez só. `CatalogoEquipamentos` e `RelatorioAcervo` passam a depender só da
interface.

**Nome do padrão:**

Adapter, padrão estrutural.

**Alternativa descartada e por que ela não resolveria por completo:**

Alterar o `InventarioLegadoWS` para sempre mandar a letra em maiúscula ou já
devolver `Equipamento`. Além de não ser possível porque o serviço é de outra
equipe, não resolveria por completo. A divergência do PAT-1003 sumiria, mas a
conversão continuaria duplicada e as duas classes continuariam dependendo da
classe concreta.
A próxima mudança de formato teria que ser corrigida de novo em
dois lugares e trocar o serviço ainda exigiria alterar os clientes.

## Padrão aplicado ao Problema 2

**O problema que ele resolve (descreva antes de nomear o padrão):**

Um objeto cujo comportamento depende da fase em que está: a mesma operação é
permitida numa fase, recusada em outra e leva a fases diferentes. O padrão
transforma cada fase numa classe, `FaseSolicitada`, `FaseEmUso` e
`FaseEncerrada`, que diz se cada operação é permitida e qual é a próxima fase.
`Reserva` só guarda a fase atual e delega a ela, sem nenhum `if`. Uma fase nova
vira uma classe nova, e só mudam as fases que levam até ela.

**Nome do padrão:**

State, padrão comportamental.

**Alternativa descartada e por que ela não resolveria por completo:**

Trocar a `String` por um `enum` com `SOLICITADA`, `EM_USO` e `ENCERRADA`. Isso
resolveria o erro de digitação, porque o compilador passaria a conferir os
nomes, mas os `if` ou `switch` continuariam nos três métodos de `Reserva`. As
regras continuariam espalhadas por operação e uma fase nova ainda exigiria
alterar todos os métodos públicos, então a violação do OCP continuaria.
