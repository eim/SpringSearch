#Spring integration with the Lucene search.

Requirements:
- Java 8
- Gradle
- Docker

Desired OS:
- Linux
- MacOS (with Docker ver. 1.12 preferable)

##Define properties:
In src/main/resources/application.properties
define a folder with the text files, which will be indexed:
- <em>source.files.directory</em>
and an index folder:
- <em>spring.jpa.properties.hibernate.search.default.indexBase</em>


##Build commands:
- <em>clean:</em> ./gradlew clean
- <em>build:</em> ./gradlew build
- <em>docker image:</em> ./gradlew buildDocker
- <em>start container:</em> ./gradlew runDocker
- <em>all in one:</em> ./gradlew clean build buildDocker runDocker

##Integration test command:
- <em>test:</em> ./gradlew integrationTest

##Open browser 
Open this address in a browser: http://localhost:8090

Here are 3 basic requests::
* search: http://localhost:8090/search/q=term
* reindex: http://localhost:8090/indexing
* update: http://localhost:8090/updatefiles

<em>Search</em> - get statistics and files name.
<em>Reindex</em> - rebuild whole index
<em>Update</em> - check new files in filesystem, remove deleted data from index, rebuild index.

##Starting
If folder with test files already contains files before start, they will be indexed after <em>start automatically</em>.
If folder is empty before start, then user has to <em>add files manually and start updating files manually</em>.