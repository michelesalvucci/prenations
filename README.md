# Pre Nations
A sample Java 17 Spring Boot JPA application.
- **JPA** management with common `Specification` and custom `FilterField`
- auditing awareness
- API custom response management
  - DTO
  - Page DTO
  - Exception handling of custom validations and `Jakarta` validations (in `@ControllerAdvice`)
  - alert header
- Entity-DTO mapping with **MapStruct**
- Security authentication with **OICD** OAuth2 **Keycloack**
- **multi-tenant** management with **Hibernate Filter** and **Aspect**
- arranged communication with other services via `FeignClient`

Features goals in the *TODOs* section.

## Development

To start your application in the dev profile, run:

```
gradlew bootRun -Dspring.profiles.active=dev -Dspring.datasource.password=<db password>
```

## Building for production

### Packaging as jar

To build the final jar and optimize the preNations application for production, run:

```
./gradlew -Pprod clean bootJar
```

To ensure everything worked, run:

```
java -jar build/libs/*.jar
```

Refer to [Using JHipster in production][] for more details.

### Packaging as war

To package your application as a war in order to deploy it to an application server, run:

```
./gradlew -Pprod -Pwar clean bootWar
```

## Testing

### Spring Boot tests

To launch your application's tests, run:

```
./gradlew test integrationTest jacocoTestReport
```

## Authentication
Authentication is managed via OICD protocol through Keycloak configuration.

Following properties were set:

```
spring:
  security:
    oauth2:
      client:
        registration:
          keycloak:
            client-id: prenations-app
            authorization-grant-type: authorization_code
            scope: openid
        provider:
          keycloak:
            issuer-uri: http://localhost:9000/realms/PreNations
            user-name-attribute: preferred_username
        resourceserver:
          jwt:
            issuer-uri: http://localhost:9000/realms/PreNations
```

### OpenID

- **open standard** that enables decentralized digital identity
- allowing users to log into different websites using the same identity provider (SSO options where you can use your Google or Facebook account to sign in to various sites without having to create new usernames and passwords for each one).
- convenience and portability (not have to remember multiple sets of login credentials)
- risk associated with having a point of failure
- **authentication**
- app auth request -> OpenID Provider authentication -> redirect to the app
- **no further information** other than end-user identity
- commonly used for SSO via Google/Facebook
- no tokens, simpler to implement
- little customization

### What is OAuth?

- **authorization protocol** that enables users to grant limited access to their data on one site to another site or application without exposing their credentials (as a third-party app allowed to access photoso stored in a social media by authorizing it via OAuth without providing the password)
- gives users safer **delegated authorization** compared to sharing passwords directly. Users can grant limited access and revoke it at any time.
- complexity for developers and some risks for users
- users should be careful in reviewing permissions granted to apps via OAuth and not blindly authorize access to sensitive data
- **delegated authentication**
- client -> resource server -> authorization server
- grants **specific access** to proctected **resources**
- allows users to grant third-party applications access to their data
- signed tokens, complex to implement
- extensive customization
- widely adopted across the industry for mobile apps, web APIs

## Feign Clients

The service communicates with the "pre-people" service through FeignClient (`PNPeopleFeignClient`).

## TODOs
- messages and responses internationalization 
- test
- authorities and roles with Keycloak
- mapper with domain findById (as sample: Continent: Europe, Africa, etc...)
- second level cache
- Kafka messaging with Cloud Stream ? (https://www.baeldung.com/spring-cloud-stream)
- dockerfile and compose to run the application in containerized environment
  - gateway to communicate with other services through Feign and Kafka

If you have any suggestion please write in the comments.