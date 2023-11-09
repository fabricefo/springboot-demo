# Création des Todos
curl -d "{\"title\":\"Title 1\",\"description\":\"Desc #1\"}"  -H "Content-Type: application/json" http://localhost:8080/api/todos
curl -d "{\"title\":\"Title 2\",\"description\":\"Desc #2\"}"  -H "Content-Type: application/json" http://localhost:8080/api/todos
curl -d "{\"title\":\"Title 3\",\"description\":\"Desc #3\"}"  -H "Content-Type: application/json" http://localhost:8080/api/todos

# Création des Tags
curl -d "{\"name\":\"Tag 1 to 1\"}"  -H "Content-Type: application/json" http://localhost:8080/api/todos/1/tags
curl -d "{\"name\":\"Tag 1 to 2\"}"  -H "Content-Type: application/json" http://localhost:8080/api/todos/2/tags

# Création des Comments
curl -d "{\"content\":\"Comment 1 to 1\"}"  -H "Content-Type: application/json" http://localhost:8080/api/todos/1/comments
curl -d "{\"content\":\"Comment 2 to 1\"}"  -H "Content-Type: application/json" http://localhost:8080/api/todos/1/comments
curl -d "{\"content\":\"Comment 1 to 2\"}"  -H "Content-Type: application/json" http://localhost:8080/api/todos/2/comments

# Création des Details
curl -d "{\"createdBy\":\"Fabrice Fourel\"}"  -H "Content-Type: application/json" http://localhost:8080/api/todos/1/details