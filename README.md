# Stock Tracker

Stock Tracker é uma API backend desenvolvida em Java para gerenciar compras de ações e consultar preços de mercado em tempo real. A aplicação resolve o problema de organizar portfolios de investimento, registrando compras históricas e enriquecendo os dados com cotações externas, tudo de forma autenticada e segura.

## Tecnologias e Padrões

- Java 17
- Spring Boot 3
- Spring Data MongoDB
- Spring Security com JWT
- Spring Cloud OpenFeign
- Springdoc OpenAPI / Swagger UI
- Redis Cache
- Arquitetura em camadas (Controller, Service, Repository)
- DTOs e mapeamento explícito via `StockMapper`
- Design orientado a APIs RESTful

## Arquitetura e Decisões de Design

O projeto segue um padrão de separação entre camadas:

- `controller` expõe os endpoints REST
- `service` contém regras de negócio e integrações externas
- `repository` abstrai o acesso a dados no MongoDB
- `entity` representa os dados persistidos
- `mapper` converte DTOs para entidades e vice-versa

### Autenticação e segurança

- Autenticação via JWT em `AuthController`
- Cadastro de usuário em `RegisterController`
- Filtros Spring Security configurados em `SecurityConfig`
- Requisições a `/api/v1/stock/**` exigem token Bearer
- O token é gerado por `TokenService` e validado por `SecurityFilter`

### Integração externa e cache

- Consulta de preço de ações realizada em `FindStockDetailService`
- Conector Feign `BrapiClient` consome a API pública Brapi
- Resultados são cacheados via Redis para evitar chamadas repetidas e reduzir latência

### Modelagem de dados

- `Stock` armazena a ação e referência ao usuário proprietário
- `StockPurchase` guarda data, preço e quantidade de cada compra
- `User` guarda credenciais, roles e é usado como principal de autenticação

## Pré-requisitos e Configuração Local

### Requisitos mínimos

- JDK 17
- Gradle Wrapper (`./gradlew` já presente)
- MongoDB em execução em `localhost:27017`
- Redis em execução em `localhost:6379`
- Variáveis de ambiente:
  - `BRAPI_API_TOKEN` - token de acesso à API Brapi
  - `JWT_SECRET_KEY` - chave secreta para assinar JWT

### Executando localmente

No diretório do projeto:

```bash
./gradlew clean bootRun
```

### Profiling e portas

A aplicação roda por padrão em `http://localhost:8080`.

### Configuração de ambiente

O arquivo `src/main/resources/application.yaml` inclui:

- conexão com MongoDB
- conexão com Redis
- configuração do OpenAPI e Swagger
- leitura de `BRAPI_API_TOKEN`
- leitura de `JWT_SECRET_KEY`

## Documentação da API

A documentação interativa está disponível em:

- `http://localhost:8080/swagger/index.html`

### Endpoints principais

#### Registro de usuário

`POST /api/v1/stock/register`

Corpo de requisição JSON:

```json
{
  "name": "Gabriel Martins",
  "email": "user@example.com",
  "password": "senha-segura"
}
```

Cabeçalho opcional:

- `isAdmin: true` — registra o usuário com papel `ADMIN` além de `USER`

#### Login

`POST /api/v1/stock/auth/login`

Corpo de requisição JSON:

```json
{
  "email": "user@example.com",
  "password": "senha-segura"
}
```

Resposta de sucesso:

```json
{
  "access_token": "<JWT_TOKEN>",
  "name": "Gabriel Martins"
}
```

#### Salvar nova compra de ação

`POST /api/v1/stock`

Cabeçalho:

- `Authorization: Bearer <JWT_TOKEN>`

Corpo de requisição JSON:

```json
{
  "stock": "PETR4",
  "quantity": 100,
  "date": "2026-05-24",
  "price": 25.50
}
```

Resposta de sucesso: `201 Created`

#### Adicionar compra a uma ação existente

`POST /api/v1/stock/add`

Cabeçalho:

- `Authorization: Bearer <JWT_TOKEN>`

Corpo de requisição JSON:

```json
{
  "stockId": "6427f6f0...",
 "quantity": 50,
 "date": "2026-05-25",
 "price": 26.10
}
```

Resposta de sucesso: `204 No Content`

Resposta de erro quando o `stockId` não for encontrado:

- `422 Unprocessable Entity`

#### Listar ações do usuário

`GET /api/v1/stock`

Cabeçalho:

- `Authorization: Bearer <JWT_TOKEN>`

Resposta de sucesso:

```json
[
  {
    "id": "6427f6f0...",
    "stock": "PETR4",
    "price": 25.50,
    "quantity": 150,
    "total": 3825.00
  }
]
```

## Observações

- O projeto utiliza `@EnableCaching` para cache Redis de cotações de ações.
- A API é projetada para ser stateless, facilitando deploy em ambientes distribuídos.
- A arquitetura é simples, mas organizada, o que permite estender o sistema para notificações, processamento assíncrono ou regras de trade mais sofisticadas.
