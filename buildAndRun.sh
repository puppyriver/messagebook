npm run build
./gradlew bootRepackage
java -Djava.security.egd=file:///dev/urandom -Dloader.path=deplibs/ -Dlogging.level=info  -Dspring.profiles.active=onsite  -jar  build/libs/messagebook-1.0-SNAPSHOT.jar &