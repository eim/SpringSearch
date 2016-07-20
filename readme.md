#Spring integration with the Lucene search.

##Define folder with text files, which will be indexed.
In src/main/resources/application.properties
Propery: source.files.directory

##Build commands:
>clean: ./gradlew clean
>build: ./gradlew build
>docker image: ./gradlew buildDocker
>start container: ./gradlew runDocker

all in one:
>./gradlew clean build buildDocker runDocker

##Open browser 
Open this address in a browser: http://localhost:8090

Here 3 basic requests:
>search: http://localhost:8090/search/q=term
>reindex: http://localhost:8090/indexing
>update: http://localhost:8090/updatefiles

Search - get statistics and files name.
Reindex - rebuild whole index
Update - check new files in filesystem, remove deleted data from index, rebuild index.

##Starting
If folder with test files already contains files before start they will be indexed after start automatically.
If folder is empty before start then user has to add files manually and start updating files manually.