Instruções para uso:

Dependências: Java 17

Para visualizar o código e rodar, basta abrir em sua IDE de preferência (estou usando o Intellij),
verificar as configurações de compilador do projeto, certificando-se de estar na versão correta do java, e criar uma configuração de execução
com target na classe Main.java.

Para facilitar, criei um Dockerfile, que pode ser usado para criar uma imagem e rodar o projeto em um container, caso tenha problemas de dependências.

Para criar a imagem, basta rodar o comando `docker build -t <nome da imagem> .` no diretório raiz do projeto.