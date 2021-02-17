# API para a gestão de animais de estimação

<p align="center">
  <a href="http://nestjs.com/" target="blank"><img src="https://nestjs.com/img/logo_text.svg" width="320" alt="Nest Logo" /></a>
</p>

## Descrição

A API para a gestão de animais de estimação, utilizando Typescript, PostgreSQL, NodeJs, NestJs e Docker.

## Executando com Docker

O projeto pode ser executado usando Docker, por meio do comando a seguir.

```
docker-compose up
```

!!!IMPORTANTE!!!

Ao finalizar os testes, é necessário executar o seguinte comando.

```
docker-compose down
```

## Para executar a aplicação

Execute o comando a seguir para instalar as dependências do projeto.

```bash
$ npm install
```

Os comandos a seguir também estão habilitados no arquivo `package.json`.

```bash
# development
$ npm run start

# watch mode
$ npm run start:dev

# production mode
$ npm run start:prod
```

## Testes

```bash
# unit tests
$ npm run test

# e2e tests
$ npm run test:e2e

# test coverage
$ npm run test:cov
```

## Licença

Nest usa [a licença MIT](LICENSE).
