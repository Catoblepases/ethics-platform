# Éthique-Plateforme
[L'emplacement original de ce projet (version java pure avec javafx)](https://github.com/Catoblepases/EthiqueInterface)

# Démarrage rapide
Afin d'exécuter le programme correctement, il est recommandé d'exécuter d'abord le back-end puis le front-end, le back-end sur le port 8080 et le front-end sur 9000, vérifiez les résultats à l'adresse http://localhost:9000/.

```shell
echo "back-end"
cd spring
mvn clean
mvn install
mvn package
java -jar target/spring-1.0.0.jar
echo "front-end (need to start another terminal)"
cd ..
cd vue 
npm install
npm run dev
```

## Référence
Ce projet utilise les projets open source suivants :
[electron-vue-spring](https://github.com/wuruoyun/electron-vue-spring) licence: [MIT](LICENSE)
