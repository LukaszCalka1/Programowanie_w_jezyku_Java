# Programowanie w języku Java

Wspólny setup JavaFX (Windows + PowerShell), żeby `comp_FX` / `run_FX` działały u każdego po sklonowaniu repo.

## Wymagania

1. **JDK 25** (ta sama major wersja co JavaFX)  
   Sprawdzenie: `java -version` i `javac -version`
2. **JavaFX SDK 25** dla Windows  
   - Pobierz: https://gluonhq.com/products/javafx/  
   - Rozpakuj do katalogu repo jako `javafx-sdk/`  
   - Musi istnieć plik: `javafx-sdk/lib/javafx.controls.jar`

Folder `javafx-sdk/` jest w `.gitignore` — **nie commitujemy** go do gita (za duży i zależny od systemu).

## Start (za każdym razem w nowym terminalu)

Z katalogu głównego repo:

```powershell
. .\fx.ps1
```

Powinno wypisać `JavaFX OK` i ścieżkę `PATH_TO_FX`.

### Kompilacja i uruchomienie

```powershell
comp_FX HelloWorld.java
run_FX HelloWorld
```


## Gdy coś nie działa

| Objaw | Co sprawdzić |
|--------|----------------|
| `Nie znaleziono javac/java` | JDK w PATH albo `JAVA_HOME` |
| `PATH_TO_FX nieustawione` | Brak `javafx-sdk/lib` — pobierz SDK do repo |
| Okno się nie otwiera / błąd native | Ta sama major wersja JDK i JavaFX (np. obie 25) |
| Skrypt się nie ładuje | `Set-ExecutionPolicy -Scope CurrentUser RemoteSigned` |

Opcjonalnie własna lokalizacja SDK:

```powershell
$env:PATH_TO_FX = 'C:\sciezka\do\javafx-sdk\lib'
. .\fx.ps1
```
