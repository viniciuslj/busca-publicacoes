# SISTEMA WEB PARA BUSCA DE PUBLICAÇÕES EM BASE DE DOCUMENTOS PDF
Trabalho de Conclusão de Curso
Bacharelado em Sistemas de Informação
Instituto Federal do Espírito Santo
Autor: Vinicius Louzada Jacobsem

### BACK-END

This application was generated using JHipster 6.0.1, you can find documentation and help at [https://www.jhipster.tech/documentation-archive/v6.0.1](https://www.jhipster.tech/documentation-archive/v6.0.1).

### Development

To start your application in the dev profile, simply run:

    ./mvnw

For further instructions on how to develop with JHipster, have a look at [Using JHipster in development][].

### Building for production

#### Packaging as jar

To build the final jar and optimize the sbp application for production, run:

    ./mvnw -Pprod clean verify

To ensure everything worked, run:

    java -jar target/*.jar

Refer to [Using JHipster in production][] for more details.

#### Packaging as war

To package your application as a war in order to deploy it to an application server, run:

    ./mvnw -Pprod,war clean verify


