```
                                                                                                              
,-----.                                 ,-----. ,--.               ,--. ,--.                                  
|  |) /_   ,---.  ,--.  ,--.  ,---.    '  .--./ |  ,---.   ,--,--. |  | |  |  ,---.  ,--,--,   ,---.   ,---.  
|  .-.  \ | .-. :  \  `'  /  (  .-'    |  |     |  .-.  | ' ,-.  | |  | |  | | .-. : |      \ | .-. | | .-. : 
|  '--' / \   --.  /  /.  \  .-'  `)   '  '--'\ |  | |  | \ '-'  | |  | |  | \   --. |  ||  | ' '-' ' \   --. 
`------'   `----' '--'  '--' `----'     `-----' `--' `--'  `--`--' `--' `--'  `----' `--''--' .`-  /   `----' 
                                                                                              `---'                    
```

> Desafio técnico da Bexs

## Requerimentos

Para buildar e rodar a aplicação é necessário:

- [Java](https://www.java.com/pt_BR/download/)

## Console Interface

### Executando

Execute o seguinte script passando como argumento o caminho completo até o arquivo de input

```shell
cd bexs-backend-challenge
./mysolution-console.sh ~/content/input-file.txt
```

## Api Interface

### Executando

Execute o seguinte script passando como argumento o caminho completo até o arquivo de input

```shell
cd bexs-backend-challenge
./mysolution-api.sh ~/content/input-file.txt
```

### Documentação das rotas

[Postman](https://documenter.getpostman.com/view/2673922/SVYxpbN2?version=latest)

### Executando requisições com o Postman

[![Run in Postman](https://run.pstmn.io/button.svg)](https://app.getpostman.com/run-collection/74acc3742466bec85af1)

##  Testando

```shell
cd bexs-backend-challenge
./gradlew test
```

## Project structure

O projeto foi estruturado usando três módulos:

### Bexs Api Interface

Esse módulo foi criado para implementar a interface REST. Usei o conceito de "package by feature" 
onde os pacotes são separados pela feature e não pela camada. A construção da aplicação foi feita 
tendo como base a arquitetura Hexagonal, logo separei meus pacotes entre a camada de aplicação 
que recebe chamadas externas, a camada core que possui as regras de negócio e abstrações e a 
camada de infra que é responsável por implementar as abstrações da camada core.

### Bexs Console Interface

Esse módulo foi criado para implementar a interface usando o console. Usei o conceito de 
"package by layer" onde a estrutura de pacotes é feita através da camada

### Bexs Core

Esse módulo foi criado para fazer a busca da melhor rota. A separação de pacotes foi feita
usando "package by layer" também e para achar a melhor rota possível usei o algoritmo de
Dijkstra.

## Contruído com

- [Kotlin](https://kotlinlang.org/) - Linguagem de programação
- [Ktor](https://ktor.io/) - Framework Web
- [IntelliJ](https://www.jetbrains.com/idea/) - IDE
- [Gradle](https://gradle.org/) - Gerenciador de dependências
