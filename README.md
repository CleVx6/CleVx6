# Metronome Android APK

Ce projet Android (Kotlin) crée une application de métronome simple:

- Bouton **Démarrer / Arrêter**
- Réglage du **temps entre chaque coup** en millisecondes (150 ms à 2000 ms)
- Affichage de l'équivalent en **BPM**

## Compiler l'APK en local

1. Installer Android Studio (ou Android SDK + JDK 17)
2. Ouvrir ce dossier comme projet Gradle
3. Lancer la tâche:

```bash
gradle :app:assembleDebug
```

L'APK généré se trouve ensuite ici:

```text
app/build/outputs/apk/debug/app-debug.apk
```

## Générer un APK installable automatiquement (GitHub Actions)

Le workflow `.github/workflows/build-apk.yml` compile l'application et publie l'APK en artifact téléchargeable.

1. Pousser le dépôt sur GitHub.
2. Aller dans **Actions** > **Build Android APK**.
3. Lancer **Run workflow** (ou pousser une branche).
4. Télécharger l'artifact `app-debug-apk`.

## Pourquoi je ne peux pas te livrer directement l'APK depuis cet environnement

Dans cet environnement d'exécution, l'accès au dépôt Maven Google est bloqué (HTTP 403), ce qui empêche de récupérer le plugin Android Gradle nécessaire à la compilation.
