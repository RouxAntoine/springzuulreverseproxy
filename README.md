# Spring Zuul Reverse Proxy

### Proxy (./proxy)

Sample of spring zuul reverse proxy

### REST application (./application)

Spring boot Flux + redis spring data reactive

### Oauth2 server (./OAuth2)

Developpement front end use this http header

`Accept application/stream+json`


### test backend api serveur

create one song

`curl localhost:8090/sync/songs -XPOST -H 'Content-Type: application/json' -d '{"id": "1", "title": "Bob Marley One 
Love", "year": "1977", "musicType":"REGGAE"}'`

list all songs

`curl localhost:8090/sync/songs`

show song with id 1

`curl localhost:8090/sync/songs/1`

