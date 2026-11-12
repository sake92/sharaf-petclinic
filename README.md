# sharaf-petclinic

This is a [PetClinic](https://spring-petclinic.github.io/) implementation  
with the [Sharaf](https://github.com/sake92/sharaf) mini framework.

Requires Java 21 and sbt.

## Run

```sh
# start a Postgres instance
docker compose up -d

# run the server
sbt run
```

Then go to http://localhost:9001

## Test

```sh
sbt testFull
```

Integration tests are written with help of [Testcontainers](https://testcontainers.com/guides/getting-started-with-testcontainers-for-java/).  
Every test suite starts a fresh docker container, executes migrations, tests, and kills the temporary container.  
See the [CI](https://github.com/sake92/sharaf-petclinic/actions) of this repo.












