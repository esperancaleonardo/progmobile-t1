# progmobile-t1
Implementação do Trabalho Prático 1 de PROGMOBILE - 2021.1

__________________________________________________________________

### O Projeto

Implementação de um CRUD com duas tabelas e um relacionamento entre elas utilizando SQLite para persistência de dados de um app mobile desenvolvido para android.

Foram utilizados os layouts e os conceitos vistos em aula, como  TabLayout, Fragmentos, ListView, Spinners e Adapters, além da orientação à objetos.

O CRUD implementado gerencia cadastro de alunos e cursos. Cada curso tem um nome e uma carga horária e cada aluno tem um nome, um email, um cpf, um telefone e um curso escolhido.

Cada cadastro de aluno pode ser editado e excluído, podendo ser alterados os campos: nome, e-mail, curso escolhido e telefone. Escolheu-se que o CPF não poderá ser alterado.

Cada cadastro de curso pode ser editado e excluído, podendo ser alterados os campos: nome e carga horária.

O layout do trabalho foi inspirado no layout introduzido na aula de TabLayout, com algumas alterações, pensando na alta usabilidade das interfaces do app. Todas as operações (INSERIR/EDITAR/EXCLUIR) ficam visíveis ao usuário a todo o momento.

Por fins de melhor apresentação do layout, recomenda-se um dispositivo real ou virtual com tela de 5.5 polegadas ou mais.

Por fins de apresentação, os dados de mock utilizados foram gerados automaticamente e não representam a realidade.


### Descrição do Trabalho

[Disponível Aqui](https://github.com/esperancaleonardo/progmobile-t1/blob/main/assets/desc.pdf)

### Requisitos Mínimos Implementados

Foram Atendidos os seguintes requisitos mínimos:
- [X] cada aluno só poderá se inscrever em um curso;
- [x] a aplicação permite registrar cursos;
- [x] a aplicação permite registrar alunos, escolhendo um curso registrado;
- [x] ler, editar e apagar alunos cadastrados;
- [x] ler, editar e apagar cursos registrados;
- [x] um curso com alunos cadastrados não pode ser excluído
- [x] no cadastro de um aluno, deve ser possível escolher um curso;
- [x] a aplicação deve listar os alunos e seus respectivos cursos escolhidos utilizando de um ListView.

### Requisitos Extras Implementados

Foram implementados:
- [x] inserção de dados via mock em csv;
- [ ] ~~máscaras de CPF~~; (requisito removido)
- [ ] ~~máscaras de Telefone~~; (requisito removido)
- [x] não aceitar CPFs duplicados no cadastro de alunos;
- [x] relatório de alunos por curso;
- [x] seção sobre no aplicativo com dados do aluno;
- [x] bloquear edição de texto nas exclusões;
- [x] bloquear a edição do texto até clicar em editar nas edições;
- [x] ativar edição ao clicar em 'EDITAR' que passa a ser 'SALVAR' nos contextos de edição;

### SCREENSHOTS DO APP

<p float="left">
  <img src="https://github.com/esperancaleonardo/progmobile-t1/blob/main/assets/screenshots/Screenshot_20210516-004645.png" width="180" height="280" />
  <img src="https://github.com/esperancaleonardo/progmobile-t1/blob/main/assets/screenshots/Screenshot_20210516-004653.png" width="180" height="280" />
  <img src="https://github.com/esperancaleonardo/progmobile-t1/blob/main/assets/screenshots/Screenshot_20210516-004707.png" width="180" height="280" />
  <img src="https://github.com/esperancaleonardo/progmobile-t1/blob/main/assets/screenshots/Screenshot_20210516-004718.png" width="180" height="280" />
  <img src="https://github.com/esperancaleonardo/progmobile-t1/blob/main/assets/screenshots/Screenshot_20210516-004731.png" width="180" height="280" />
</p>


### DER - Modelo do Banco

![alt text](https://github.com/esperancaleonardo/progmobile-t1/blob/main/assets/DER_RELACIONAL.png)


### Modelagem do Sistema

Seque abaixo o modelo que atende os requisitos mínimos do projeto

![alt text](https://github.com/esperancaleonardo/progmobile-t1/blob/main/assets/a.svg)

### Link Apresentação do Trabalho

[Clique aqui para ver o vídeo de apresentação!](http://www.youtube.com)

-------------------------------------------------
## Aluno Responsável Pela Entrega

Leonardo de Oliveira Esperança    ---    Engenharia de Computação   ---   2015.1905.011-9
