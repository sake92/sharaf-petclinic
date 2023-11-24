# sharaf-petclinic

This is a [PetClinic](https://spring-petclinic.github.io/) implementation  
with the [Sharaf](https://github.com/sake92/sharaf) mini framework.

## Run

```sh
# start a Postgres instance
docker compose up -d

# run the server
./mill app.run
```

Then you can go to 

## Test

```sh
./mill app.test
```

Integration tests are written with help of [Testcontainers](https://testcontainers.com/guides/getting-started-with-testcontainers-for-java/).  
Every test suite starts a fresh docker container, executes migrations, tests, and kills the temporary container.  
See the [CI](https://github.com/sake92/sharaf-petclinic/actions) of this repo.












