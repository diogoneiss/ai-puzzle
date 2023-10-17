# Comparação de algoritmos para a solução do problema do 8-puzzle

Diogo Neiss

## Dependências
* Java 17+ 
* Maven

## Instruções para uso

### Opção A (gráfica): Usar uma IDE

Para visualizar o código e rodar, basta abrir em sua IDE de preferência (estou usando o Intellij),
verificar as configurações de compilador do projeto, certificando-se de estar na versão correta do java, e criar uma configuração de execução
com target na classe Main.java.

No Intellij, você precisará configurar o JDK do projeto e criar uma run config no canto superior direito, para rodar a classe main.

Depois configure os argumentos de linha de comando para passar os parâmetros desejados

---------------

### Opção B (terminal): buildar com o maven
O Maven é uma solução de build para java bastante eficiente. Criei um arquivo `pom.xml` que possui as instruções para compactar tudo em um único jar executável.

1. Certifique-se que está na pasta raiz do projeto, no mesmo nível que o `pom.xml`
2. Para compilar e instalar os pacotes:
`mvn clean package`

3. Para rodar:
`java -jar target/ai-puzzle-1.0-SNAPSHOT.jar <argumentos do programa>`

Um exemplo seria
`java -jar target/ai-puzzle-1.0-SNAPSHOT.jar B 8 4 7 6 2 3 5 1 0 PRINT`


Se desejar rodar as análises com o notebook python, certifique-se de rodar todos os casos de teste antes e gerar seu próprio arquivo `results.csv`, se desejar.
Para gerar um novo arquivo, use o argumento `-a` ou `--all`, dessa forma

`java -jar target/ai-puzzle-1.0-SNAPSHOT.jar -a`