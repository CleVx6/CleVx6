# Metronome Android APK

Ce projet Android (Kotlin) crée une application de métronome simple:

- Bouton **Démarrer / Arrêter**
- Réglage du **temps entre chaque coup** en millisecondes (150 ms à 2000 ms)
- Affichage de l'équivalent en **BPM**

## Compiler l'APK

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
