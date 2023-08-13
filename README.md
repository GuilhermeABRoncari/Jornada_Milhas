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

Utilizar este verbo Http (POST) permite cadastrar um novo depoimento. O corpo da requisição deve conter um objeto JSON com os seguintes campos obrigatórios: "autor" (nome do autor do depoimento), "depoimento" (texto do depoimento) e "foto" (URL da foto do autor).

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

Utilizar este verbo Http (GET) retorna uma lista contendo todos os depoimentos cadastrados. O corpo da resposta retornada vai conter uma lista de objetos JSON contendo os seguintes campos: "id" (uma string com a indentificação unica do objeto), "autor" (nome do autor do depoimento), "foto" (URL da foto do autor), "depoimento" (texto do depoimento), "editado" (booleano que diz se esse objeto já foi alterado depois da sua postagem origianl) e "postado_as" (timestamp com a data da publicação do depoimento junto do offset). 

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

`DELETE /depoimentos/<id do depoimento>`

Utilizar este verbo Http (DELETE) permite remover um depoimento pelo seu ID. A resposta será um codigo 204 (NO_CONTENT) como confirmação de que aquele depoimento não existe mais.

Exemplo de requisição:

DELETE /depoimentos/stringUUID

### Endpoint para atualização de um depoimento

`PUT /depoimentos`

Utilizar este verbo Http (PUT) permite atualizar algumas informações de um depoimento atravez do ID fornecido no corpo da requisição. A requisição deve ser um objeto JSON contendo obrigatoriamente o ID do depoimento que deve ser alterado. Os campos que podem ser alterados são: "autor" (nome do autor do depoimento), "depoimento" (texto do depoimento) e "foto" (URL da foto do autor).

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

Utilizar este verbo Http (GET) retorna até três depoimentos selecionados aleatoriamente no corpo da resposta que será uma lista de objetos JSON.

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

### Endpoint de Cadastro de Destinos

`POST /destinos`

Utilizar este verbo Http (POST) permite cadastrar um novo destino. O corpo da requisição deve conter um objeto JSON com os seguintes campos obrigatórios: "nome" (nome do destino), "foto_1" (URL da foto do destino), "foto_2" (URL da foto do destino), "meta" (Texto breve de cabeçalho), "texto_descritivo" (texto que pode ser fornecido pelo usuario ou preenchido por IA caso venha nulo) e "preço" (valor decimal).

Exemplo de requisição:

```json
{
    "nome": "Belo Horizonte",
    "foto_1": "https://th.bing.com/th/id/OIP.v-P9aYWgMIwcZQzGezTQHAHaE9?pid=ImgDet&rs=1",
    "foto_2": "https://th.bing.com/th/id/OIP.v-P9aYWgMIwcZQzGezTQHAHaE9?pid=ImgDet&rs=1",
    "meta": "campo meta que deve ser preenchido com até 160 caracteres",
    "texto_descritivo": "",
    "preço": 300.00
}
```

### Endpoint que retona todos os destinos cadastrados de forma paginada

`GET /destinos/todos`

Utilizar este verbo Http (GET) retorna uma paginação com todos os destinos cadastrador que pode ser manipulado pelo parametros de requisição: page (pagina que é base 0).

Exemplo de resposta:

```json
{
    "content": [
        {
            "nome": "Belo Horizonte",
            "foto_1": "https://th.bing.com/th/id/OIP.v-P9aYWgMIwcZQzGezTQHAHaE9?pid=ImgDet&rs=1",
            "foto_2": "Some picture URL",
            "text_descritivo": " \n\nBelo Horizonte é um lugar incrível que vai atender às suas necessidades. Com ambiência charmosa, é a 5ª maior cidade do Brasil e conta com atrativos culturais, arquitetônicos, gastronômicos see culturais. Uma cidade jovem e moderna é o lugar ideal para quem busca vida agitada em meio a charmosa paisagem. \n\nConhecida como a \"Cidade-Canção\", Belo Horizonte tem mais de 500 anos de história e é repleta de maravilhosas atrações para todos os gostos e estilos. Com excelente infraestrutura e linda natureza, encontra-se resposta para todos os níveis de lazer e entretenimento. Um lugar para todos os gostos que diverte e enriquece quem lá vive.",
            "preço": 300.00,
            "id": "d02036c2-f0e6-41a3-a766-6191947e5220",
            "meta": "campo meta que deve ser preenchido com até 160 caracteres"
        }
    ],
    "pageable": {
        "sort": {
            "empty": true,
            "sorted": false,
            "unsorted": true
        },
        "offset": 0,
        "pageNumber": 0,
        "pageSize": 20,
        "paged": true,
        "unpaged": false
    },
    "last": true,
    "totalElements": 5,
    "totalPages": 1,
    "first": true,
    "size": 20,
    "number": 0,
    "sort": {
        "empty": true,
        "sorted": false,
        "unsorted": true
    },
    "numberOfElements": 5,
    "empty": false
}
```

### Endpoint que atualiza as informações de um destino cadastrado

`PUT /destinos/<id do destino>`

Utilizar este verbo Http (PUT) permite atualizar as informações de um destino atravez do ID fornecido na URL. A requisição deve ser um objeto JSON. Os campos que podem ser alterados são: "nome" (nome do destino), "foto" (URL da foto do autor) e "preço" (valor decimal). 

Exemplo de resposta:

```json
{
    "nome": "Belo Horizonte",
    "foto_1": "https://blog-static.infra.grancursosonline.com.br/wp-content/uploads/2015/09/03173942/Perdig%C3%A3o-MG.jpg",
    "foto_2": null,
    "text_descritivo": "\n\nBelo Horizonte é um lugar incrível que vai atender às suas necessidades. Com ambiência charmosa, é a 5ª maior cidade do Brasil e conta com atrativos culturais, arquitetônicos, gastronômicos see culturais. Uma cidade jovem e moderna é o lugar ideal para quem busca vida agitada em meio a charmosa paisagem. \n\nConhecida como a \"Cidade-Canção\", Belo Horizonte tem mais de 500 anos de história e é repleta de maravilhosas atrações para todos os gostos e estilos. Com excelente infraestrutura e linda natureza, encontra-se resposta para todos os níveis de lazer e entretenimento. Um lugar para todos os gostos que diverte e enriquece quem lá vive.",
    "preço": 50.00,
    "id": "822bd50d-db54-4d5b-a927-0da9926c7c39",
    "meta": null
}
```

### Endpoint de remoção de destinos

`DELETE /destinos/<id do destino>`

Utilizar este verbo Http (DELETE) permite remover um destino pelo seu ID. A resposta será um codigo 204 (NO_CONTENT) como confirmação de que aquele destino não existe mais.

Exemplo de requisição:

DELETE /destino/stringUUID

### Endpoint adicional: Pesquisar por nome do destino

`GET /destinos?nome=<pesquisa do usuario aqui>`

Utilizar este verbo Http (GET) com o paramentro na URL "?nome=" retorna uma lista com possiveis destinos contendo o que foi recebido no parametro. A pesquisa é sanitizada e segura, mantendo toda a API segura contra ataques de SQLInjections.

Exemplo de resposta para uma pesquisa como "GET:/destinos?nome=bel"

```json
[
    {
            "nome": "Belo Horizonte",
            "foto_1": "https://th.bing.com/th/id/OIP.v-P9aYWgMIwcZQzGezTQHAHaE9?pid=ImgDet&rs=1",
            "foto_2": "Some picture URL",
            "text_descritivo": " \n\nBelo Horizonte é um lugar incrível que vai atender às suas necessidades. Com ambiência charmosa, é a 5ª maior cidade do Brasil e conta com atrativos culturais, arquitetônicos, gastronômicos see culturais. Uma cidade jovem e moderna é o lugar ideal para quem busca vida agitada em meio a charmosa paisagem. \n\nConhecida como a \"Cidade-Canção\", Belo Horizonte tem mais de 500 anos de história e é repleta de maravilhosas atrações para todos os gostos e estilos. Com excelente infraestrutura e linda natureza, encontra-se resposta para todos os níveis de lazer e entretenimento. Um lugar para todos os gostos que diverte e enriquece quem lá vive.",
            "preço": 300.00,
            "id": "d02036c2-f0e6-41a3-a766-6191947e5220",
            "meta": "campo meta que deve ser preenchido com até 160 caracteres"
        }
]
```

Caso não seja encontrado nenhum destino que combine com a pesquisa feita será retornado um codigo 404 (NOT_FOUND) com a seguinte resposta:

```json
{
    "status": 404,
    "message": "Nenhum destino foi encontrado",
    "timestamp": 1690324744.645823600
}
```

### Progresso de desenvolvimeto:

`Concluido`:

*PRIMEIRA SEMANA*: https://trello.com/b/3CJCzP8J/semana-1-challenge7

*SEGUNDA SEMANA*: https://trello.com/b/lGCYMtrk/semana-2

*TERCEIRA E QUARTA SEMANAS*: https://trello.com/b/CWH1ELsD/semana-3

`Em andamento`:

Deploy no Render

caso queira testar a aplicação que já está no ar: https://jornada-milhas-api.onrender.com/swagger-ui/index.html#/
