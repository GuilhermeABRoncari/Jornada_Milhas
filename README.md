# Jornada_Milhas
Projeto feito durante o mês do desafio Challenge#7 BackEnd da Alura.

# API REST com Spring Boot - Challenge Back-End 7 Alura

Este é um projeto de API REST desenvolvida com Spring Boot para disponibilizar informações e recursos relacionados a possíveis destinos de viagem, exibindo fotos, textos e depoimentos de outras pessoas viajantes.

## Descrição do Desafio

O desafio proposto é a construção de uma API para gerenciar depoimentos de viagens, permitindo cadastrar novos depoimentos, buscar depoimentos existentes, atualizar e remover depoimentos. Além disso, há um endpoint adicional para obter três depoimentos aleatórios.

### Tecnologias Utilizadas

- Linguagem de Programação: Java
- Framework: Spring Boot
- Gerenciamento de dependecias: Groove + Kotlin
- Banco de Dados: Postgres

## API Endpoints

A API oferece os seguintes endpoints:

### Endpoint de Cadastro de Depoimentos

`POST /depoimentos`

Ultilizar este verbo Http (POST) permite cadastrar um novo depoimento. O corpo da requisição deve conter um objeto JSON com os seguintes campos obrigatórios: "autor" (nome do autor do depoimento), "depoimento" (texto do depoimento) e "foto" (URL da foto do autor).

Exemplo de requisição:

```json
{
  "autor": "Autor da Silva",
  "depoimento": "Uma experiencia peculiar.",
  "foto": "alguamUrlDeImagemAleatoria.com"
}
```

### Endpoint que retorna todos os depoimentos

`GET /depoimentos`

Ultilizar este verbo Http (GET) retorna uma lista contendo todos os depoimentos cadastrados. O corpo da resposta retornada vai conter uma lista de objetos JSON contendo os seguintes campos: "id" (uma string com a indentificação unica do objeto), "autor" (nome do autor do depoimento), "foto" (URL da foto do autor), "depoimento" (texto do depoimento), "editado" (booleano que diz se esse objeto já foi alterado depois da sua postagem origianl) e "postado_as" (timestamp com a data da publicação do depoimento junto do offset). 

Exemplo do corpo da resposta:

```json
[
  {
    "id": "String UUID",
    "autor": "Autor da Silva",
    "foto": "alguamUrlDeImagemAleatoria.com",
    "depoimento": "Foi uma otima viajem!",
    "editado": false,
    "postado_as": 1690139586.068311000
  },
  {
    "id": "String UUID",
    "autor": "Autor de Souza",
    "foto": "alguamUrlDeImagemAleatoria.com",
    "depoimento": "Foi uma boa viajem!",
    "editado": true,
    "postado_as": 1690139586.068311000
  }
]
```
### Endpoint de remoção de depoimento

`DELETE /depoimentos/{id}`

Ultilizar este verbo Http (DELETE) permite remover um depoimento pelo seu ID. A resposta será um codigo 204 (NO_CONTENT) como confirmação de que aquele depoimento não existe mais.

Exemplo de requisição:

DELETE /depoimentos/stringUUID

### Endpoint para atualização de um depoimento

`PUT /depoimentos`

Ultilizar este verbo Http (PUT) permite atualizar algumas informações de um depoimento atravez do ID fornecido no corpo da requisição. A requisição deve ser um objeto JSON contendo obrigatoriamente o ID do depoimento que deve ser alterado. Os campos que podem ser alterados são: "autor" (nome do autor do depoimento), "depoimento" (texto do depoimento) e "foto" (URL da foto do autor).

Exemplo de requisição:

```json
{
  "id": "string UUID obrigatoria",
  "autor": "campo opcional",
  "depoimento": "campo opcional",
  "foto": "campo opcional"
}
```

### Endpoint adicional: Depoimentos aleatoreos

`GET /depoimentos-home`

Ultilizar este verbo Http (GET) retorna até três depoimentos selecionados aleatoriamente no corpo da resposta que será uma lista de objetos JSON.

Exemplo da resposta:

```json
[
  {
    "id": "String UUID",
    "autor": "Autor da Silva",
    "foto": "alguamUrlDeImagemAleatoria.com",
    "depoimento": "Foi uma otima viajem!",
    "editado": false,
    "postado_as": 1690139586.068311000
  },
  {
    "id": "String UUID",
    "autor": "Autor de Souza",
    "foto": "alguamUrlDeImagemAleatoria.com",
    "depoimento": "Foi uma boa viajem!",
    "editado": true,
    "postado_as": 1690139586.068311000
  },
  {
    "id": "String UUID",
    "autor": "Autor de Moura",
    "foto": "alguamUrlDeImagemAleatoria.com",
    "depoimento": "Foi uma viajem terrivel!",
    "editado": false,
    "postado_as": 1690139586.068311000
  }
]
```

### Progresso de desenvolvimeto:

`Concluido`:

*PRIMEIRA SEMANA*: https://trello.com/b/3CJCzP8J/semana-1-challenge7

`Em andamento`:

*SEGUNDA SEMANA*: https://trello.com/b/lxgEDut9/alurachallengebackend7-semana-2

*TERCEIRA E QUARTA SEMANAS*: https://trello.com/b/Cuh1vI9X/alurachallengebackend7-semana-3
