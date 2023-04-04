# Pet Management API

A Pet Management API, developed with:

- Java 17
- Spring
- Cassandra 4.0
- Docker
- Docker Compose

## Cassandra - Basic concepts and terminology

Cassandra is a distributed database that stores data across a cluster of nodes. It is a NoSQL database that is
lightweight, open-source, non-relational, and largely distributed. Some main concepts and terminology used in Cassandra
are:

- Data in Cassandra is stored as a set of rows that are organized into tables.
- Tables are also called column families.
- Each row is identified by a primary key value.
- Data is partitioned by the primary key.
- Cassandra provides high write and read throughput.
- A cassandra cluster is made up of one or more nodes.
- A cassandra node is a single instance of Cassandra.
- Cassandra nodes store data in a commit log directory.
- Keyspace is a namespace that defines data replication on nodes.
- A cassandra cluster can have multiple keyspaces.
- A keyspace can have multiple tables.
- A table can have multiple columns.
- A column is a key-value pair.
- A column can be indexed.

## Features

### User feature

- You can create a new user, providing your username, email, and password.
- You can display the user data in the profile page, with email and username.
- You can log in the application, using your email and password.
- You can delete your user account.

### Pet feature

- You can register your pet's data.
- You can retrieve a list with every pet you've registered, with their data.
- You can retrieve you can retrieve the data for a single pet you've registered.
- You can delete a pet register from the system.

### Note feature

- You can create a note for a pet.
- You can retrieve every note for a given pet.
- You can retrieve all of your notes.
- You can delete a note.
- You can edit the note content.

## How do I execute the application?

To set up the database, run:

```
docker-compose up -d --build
```

You need to create a keyspace with the name *pet_management_api* in the Cassandra database, after the containers are running.

Download the project dependencies with the following command:

```
mvn clean install
```

Execute the following command to run the application:

```
mvn spring-boot:run
```

Execute the application tests with the following command:

```
mvn test
```

## Author

Vitor Vidal - More about me [here](https://github.com/vitorvidaldev).

## License

This project uses [the MIT license](LICENSE).
