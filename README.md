## REST Service
#### Search for arrays of coordinates of constituent entities of the Russian Federation by specified parameters

GET http://localhost:8080/osmObjects/name={{name}}&type={{type}}

##### where
+ name - the name of the city, region, region, etc.
+ type - there are two options: region, federal

#### Request example
GET http://localhost:8080/osmObjects/name=Москва&type=region