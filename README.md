# Bem Vindo ao Wiki ServiceAppWeb!

## Tabela de Conteúdos
1. [O que é](#1)
2. [Objetivos](#2)
3. [Descrição do Produto](#3)
4. [Diagramas](#4)
5. [Requisitos do Sistema](#5)
6. [Login via Redes Sociais](#6)
7. [Telas da Aplicação](#7)
8. [Solução Tecnológica Escolhida](#8)
9. [Procedimentos de instalação](#9)
10. [Esperimente](#10)


## 1. <a name="1">O que é</a>:
Um dos grandes problemas encontrados na prestação de serviços autônomos é a dificuldade de se encontrar prestadores de serviços que atendam os mais diversos tipos de trabalho e mais difícil ainda, são profissionais de referência que tenhamos o mínimo de garantia na hora de contratá-los.
Esta é a premissa que levou a desenvolver o ServiceApp Web, uma rede social que fornece uma lista de profissionais de diversas áreas, com um sistema de fácil contato, através de uma interface web para computadores e smartphones, trazendo comodidade e segurança tanto para o prestador de serviço quanto para o contratante, além de proporcionar a qualquer pessoa que tenha algum tipo de conhecimento técnico a possibilidade de oferecer melhor seus serviços sem nenhum custo inicialmente.

## 2. <a name="2">Objetivo</a>

O objetivo do ServiceApp Web é centralizar os prestadores de serviço em uma única plataforma para facilitar tanto para quem busca profissionais de áreas especificas, como para quem tem algum conhecimento técnico e quer iniciar suas atividades como profissional autônomo.

## 3. <a name="3">Descrição do Produto</a>

O sistema ServiceApp Web disponibiliza uma aplicação para os profissionais onde eles se cadastram com suas especialidades, podendo ser contratados por clientes que necessitam dessas qualidades, clientes que também terão um cadastro e poderão trocar mensagens com os profissionais, proporcionado um respaldo para os prestadores que prestam um bom serviço.

### 3.1. Abrangência
O sistema abrange profissionais das mais diversas áreas, desde diplomados a pessoas com trabalhos informais, está aberto a qualquer um que queira praticar uma atividade remunerada de forma autônoma e que atenda aos requisitos de alguma especialidade.

### 3.2. Papel dos Atores
Atores são pessoas, equipamentos ou outros sistemas que interagem como sistema em questão, enviando ou recebendo mensagens.
Abaixo estão descritos de forma resumida o papel dos atores do ServiceApp Web.

#### 3.2.1.	Descrição dos Papeis
**Atores**
**- Descrição**

USUÁRIO	
- Cadastra-se
- Efetuará Login
- Pesquisar Serviço
- Pesquisar Prestador
- Enviar Mensagem
- Ler Mensagens

PRESTADOR DE SERVIÇOS
- Selecionar serviços prestados
- Responder Mensagens

ADMINISTRADOR	
- CRUD de Serviços
- Bloqueio/Desbloqueio de Usuário
- Bloqueio/Desbloqueio de Prestador

## 4. <a name="4">Diagramas</a>
### 4.1. Diagrama de Caso de Uso
![Caso de Uso](https://github.com/leandrogrego/ServiceAppWeb/blob/main/Diagrams/Caso_de_Uso.ucd.jpg?raw=true)
 
### 4.2. Diagrama de Classe
![Classes](https://github.com/leandrogrego/ServiceAppWeb/blob/main/Diagrams/Models.cld.jpg?raw=true)

### 4.3. Diagrama de Atividade
![Atividades](https://github.com/leandrogrego/ServiceAppWeb/blob/main/Diagrams/Atividade.acd.jpg?raw=true)

## 5. <a name="5">Requisitos do Sistema</a>

### 5.1. Requisitos Funcionais

- RF001 – CRUD dos Clientes/Prestadores;
- RF002 – CRUD dos Administradores;
- RF003 – Disponibilizar descrição de serviços;
- RF004 – Disponibilizar lista de profissionais;
- RF005 – Permitir a autenticação por meio de login;
- RF006 – Permitir a avaliação do profissional pelo cliente;
- RF007 – Permitir troca de mensagens entre profissional e cliente;
- RF008 – Permitir que o Profissional selecione os serviços que deseja prestar;
- RF009 – Permitir que o Administrador cadastre os serviços;
- RF010 – Permitir que o Administrador bloqueie e desbloqueie os usuários;
- RF010 – Permitir que o Administrador cadastre outros administradores.

### 5.2. Requisitos Não Funcionais
- NF001 – O sistema utilizará Interface Web/HTML;
- NF002 – O sistema utilizará geolocalização.
- NF003 – O sistema utilizará o padrão API Rest;
- RF004 – Comunicação será através do banco de dados MySql via SGBD;
- NF005 – O sistema utilizará as tecnologias Java, com Spring Framework;
- NF006 – O sistema será compatível com sistemas Windows e Android.

## 6. <a name="6">Login via Redes Sociais</a>
o Acesso ao ServiceApp é realizado por meio de login em redes sociais, o que facilita muito a utilização uma vez que dispensa a criação de cadastro e torna a validação de perfis muito mais segura.

![Logo](https://github.com/leandrogrego/ServiceAppWeb/blob/main/Diagrams/SocialLogin.png?raw=true)

## 7. <a name="7">Telas da Aplicação</a>
![Telas 1](https://github.com/leandrogrego/ServiceAppWeb/blob/main/screenshots/1.png?raw=true)
![Telas 2](https://github.com/leandrogrego/ServiceAppWeb/blob/main/screenshots/2.png?raw=true)
![Telas 3](https://github.com/leandrogrego/ServiceAppWeb/blob/main/screenshots/3.png?raw=true)
![Telas 4](https://github.com/leandrogrego/ServiceAppWeb/blob/main/screenshots/4.png?raw=true)
![Telas 5](https://github.com/leandrogrego/ServiceAppWeb/blob/main/screenshots/5.png?raw=true)
![Telas 6](https://github.com/leandrogrego/ServiceAppWeb/blob/main/screenshots/6.png?raw=true)
![Telas 7](https://github.com/leandrogrego/ServiceAppWeb/blob/main/screenshots/7.png?raw=true)
![Telas 8](https://github.com/leandrogrego/ServiceAppWeb/blob/main/screenshots/8.png?raw=true)

## 8. <a name="8">Solução Tecnológica Escolhida</a>

O sistema foi desenvolvido para a plataforma web utilizando a linguagem JAVA com o auxílio do Spring Framework, com integração a um banco de dados MySQL para armazenar todo o fluxo de dados, utilizando também o padrão API Rest para a integração das tecnologias.
![Tecnologias Utlizadas](https://github.com/leandrogrego/ServiceAppWeb/blob/main/Diagrams/Tecnologias.png?raw=true)

## 9. <a name="9">Procedimentos de instalação 

1. instalar e configurar MySQL Server
2. instlar Apache/Tomcat 9.0;
3. instalar gradlw versão 6.7;
4. configurar variáveis de ambiente;
5. copiar arquivos do projeto para o servidor;
6. acessar o prompt de comandos;
7. acessar pasta do projeto;
8. executar comando gradlw bootRuns.

## 10. <a name="10">Esperimente</a>
### [https://www.serviceapp.net.br](https://www.serviceapp.net.br)
