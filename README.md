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

### Recursos importantes para a execução do projeto

  ## Cadastrar o aluno 
     - Enviar um Post através do recurso http://localhost:8080/alunos.
     - Regra campo envio: 
          1. nome - enviar nome de identificação.  
     
     Exemplo de criação aluno: 
      {
  	  "nome": "Nome de identificação do aluno"
      }
      
  ## Cadastrar o gabarito de cada prova 
     - Enviar um Post através do recurso http://localhost:8080/gabaritos
     - Regra campo envio: 
          1. dome - enviar descrição de identificação;
	  2. respostas: lista composta de resposta e peso da questão.
       
     Exemplo de criação de um gabarito:
     {
        "descricao": "Descricao de identificação do gabarito",
        "respostas": [
        	{
                "resposta": "A",
                "pesoQuestao": 2
            },
            {
                "resposta": "C",
                "pesoQuestao": 5
            }
        ]
    }
  
  ## Cadastrar as respostas de cada aluno para cada prova 
     - Enviar um Post através do recurso http://localhost:8080/provaAluno
     - Regra campo envio: 
          1. aluno: id do aluno;
	  2. prova: id da prova;
	  3. prova: lista composta de resposta para prova;
	  
	  Exemplo de criação de um gabarito:
	  {
   		"aluno": {
        		"id": 1
    		},
    		"prova": {
       			"id": 1
    		},
    		"respostasAluno": [
        		{
			  "resposta": "C"
            		},
            		{
                	     "resposta": "C"
            		}
    		]
 	}
  
  ## Verificar a nota final de cada aluno   
     - Enviar um Get através do recurso http://localhost:8080/alunos/{id} 
     - Regra campo envio: 
          1. {id}: id do aluno;
  
  ## Listar os alunos aprovados 
     - Enviar um Get através do recurso http://localhost:8080/alunos/aprovados 
     

incluindo a descrição de como compilar e executar o programa, além das instruções de utilização.

### Como compilar e executar o programa

- Importe o projeto a partir do repositório do Git;
- Impote o projeto utilizando Maven;
   

### Tecnologias utilizadas
- Java 11;
- Spring Boot;
- Maven;
- MySql 5.7;
- Junit 5;
- Mockito;
- Postman (comunicação com servidor).

