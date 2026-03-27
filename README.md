# projeto: Sistema Simplificado da Shopee

Sistema desenvolvido em java para simular o marketplace da shopee

# Tecnologias:

Java
Swing
PostgreSQL
JDBC
# Como compilar e rodar o projeto: pre-requisitos:

Java 25 instalado
postgreSQL ou docker instalado
Maven instalado -> https://maven.apache.org/

1 - clone o repositório:
git clone git@github.com:NarioMendonca/sistema-simplificado-shopee.git

2 - crie um banco de dados postgreSQL (exemplo com docker): 
docker run --name shopee-system -e POSTGRES_PASSWORD=myPassword -e POSTGRES_USER=myUser -p 5432:5432 -d postgres

3 - configure as variáveis de ambiente
  - dentro da pasta do projeto, vá para src/main/java/resources
  -  crie um arquivo chamado config.properties com os exatos valores em config.properties.example
  -  preencha as variáveis de ambiente com os dados do banco de dados seguindo o exemplo do comando docker acima
 o arquivo config.properties ficaria assim:
  postgres_name=docker
  postgres_password=docker
  postgres_database=postgres
  postgres_port=5432
  postgres_host=localhost

4 - execute o projeto com os seguintes comandos:

mvn compile para executar com cli:
mvn exec:java -Dexec.mainClass="com.shopee.App" -Dexec.args="cli" para executar com interface swing:
mvn exec:java -Dexec.mainClass="com.shopee.App" -Dexec.args="swing"

# Credenciais padrão do sistema
Cliente padrão: email: cliente@gmail.com senha: cliente123
Vendedor padrão: email: vendedor@gmail.com senha: vendedor123

# Prints das telas do projeto
 - tela de login swing:
  https://ibb.co/jvpKzjqC
 - tela produtos swing:
  https://ibb.co/5X6w99Gk
 - tela de login cli:
  https://ibb.co/q3Hpg1zW
 - tela home de cliente cli:
  https://ibb.co/gFvkX75w
 - tela home de vendedor cli:
  https://ibb.co/RkGN4zDH
# Diagrama de classes simplificado do projeto
 - https://ibb.co/7H1W8Cw
