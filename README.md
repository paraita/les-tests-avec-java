# Les tests avec Java

Ce projet regroupe l'ensemble des ressources citées dans la présentation sur les tests avec Java pour [Tahitidevops](https://devops.pf)

## mailsender

mailsender est un CLI pour l'envoi automatisé d'un mail à plusieurs destinataires. Il supporte uniquement Sparkpost.

## Utilisation

mailsender.jar est disponible dans `build/libs` et contient toutes ses dépendances.

Pour générer le jar:

```
./gradlew fatJar
```

Les tests sont lançables directement par gradle:

```
./gradlew test
```

## License

Ce projet est sous licence MIT.
