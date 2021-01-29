# projeto_escola_alfa
Projeto RESTFull desenvolvido com Spring Boot

Autora: Márcia P. Martins

### Descrição

Projeto se trata de um controle de notas e aprovação do alunos, conforme de-para de gabarito da prova e resposta da prova informada pelo aluno. 

Existem três serviços principais do projeto:

1. Gabarito da prova
  - Ao se cadastrar um gabarito, automaticamente será criado uma nova prova no sistema;
  - Para cadastrar um gabarito é necessário incluir o valor de cada respostas, também é necessário informar o peso de cada questão;
  - Não é permitido cadastrar um gabarito sem ao menos uma resposta;
  - Não é permitido cadastrar um gabarito sem informar o peso para a questão ou informar valor para o peso diferente de um inteiro ou menor de zero;

2. Aluno
   - Permite o cadastro de um aluno;
   - Permite a consulta do aluno, retornando sua nota final (até o momento da consulta);
   - Para realizar o cadastro de aluno e necessário informar seu nome;
   - São permitidos somente o cadastro de 100 alunos no sistema.

2.1. Consulta alunos aprovados
   - Através da consulta alunos/aprovados, será possível listar os alunos aprovados, com média maior que sete;

3. Resposta do aluno para as provas previamente cadastradas
   - Permite cadastrar as respostas para um gabarito e aluno previamente registrados;
   - Somente será possível cadastrar um conjunto de respostas para cada prova, obedecendo a quantidade de gabaritos registrados.

### Pré-requisitos para o bom funcionamento

	- Cadastrar o aluno - http://localhost:8080/alunos
	- Cadastrar o gabarito de cada prova - http://localhost:8080/gabaritos
	- Cadastrar as respostas de cada aluno para cada prova - http://localhost:8080/provaAluno
	- Verificar a nota final de cada aluno http://localhost:8080/alunos/{id}
	- Listar os alunos aprovados - http://localhost:8080/alunos/aprovados

incluindo a descrição de como compilar e executar o programa, além das instruções de utilização.

### Como compilar e executar o programa

- Importe o projeto a partir do repositório do Git;
- 
  
   
   

### Tecnologias utilizadas
- Java 11;
- Spring Boot;
- Maven;
- MySql 5.7;
- Junit 5;
- Mockito;
- Postman (comunicação com servidor).

