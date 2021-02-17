# Pet Management API

An pet management API, developed with:

- Typescript
- PostgreSQL
- NodeJs
- NestJs
- Docker

## Environment Configuration

This file assumes Nodejs, Nestjs and Docker are already installed.

After downloading this repository, run the following command to install all necessary dependencies.

```
npm install
```

PostgreSQL and Adminer can be executed with the following command.

```
docker-compose up
```

_Remember to end the containers processes once your done, with the following command._

```
docker-compose down
```

If it's the first time running the `docker-compose up`command in this repository, you'll have to create a database called `tcc`. Access (http://localhost:8080) to create the database.

## Available commands

```bash
# development
$ npm run start

# watch mode
$ npm run start:dev

# production mode
$ npm run start:prod
```

## Tests

```bash
# unit tests
$ npm run test

# e2e tests
$ npm run test:e2e

# test coverage
$ npm run test:cov
```

## License

Nest uses [the MIT license](LICENSE).
