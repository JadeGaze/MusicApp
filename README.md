## ✨ Summary
It is an Android music player with streaming and offline playback, powered by Deezer API:
- 🎵 Background playback: Listen to music even when the app is in the background  
- 🔍 Track search: Find any song via Deezer API integration  
- ⬇️ Music downloads: Save tracks directly to your device for offline listening  
- 🎧 Built-in player: – Play/pause, seekbar, volume control, and playlist support

## 🛠 Tech Stack
#### Core:
- Kotlin (1.9.0) + Coroutines
- Jetpack Compose (2025.01.00) + Material 3
- Koin (3.4.0)
- Navigation Component (2.8.5)

#### Data:
- Room DB (2.6.1)
- Retrofit (2.9.0)
- Kotlinx Serialization (1.6.0) (JSON parsing)

#### Architecture:
- Modularization (`:app`, `:core:common`, `:core:db`, `:core:utils`, `:core:network`, `:core:designsystem`, `:core:navigation`, `:feature:notes:api`, `:feature:list`, `:feature:player`)
- MVI
