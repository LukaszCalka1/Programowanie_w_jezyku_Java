# Portable JavaFX helpers — uruchom z katalogu repo:
#   . .\fx.ps1
#
# Wymaga: JDK 21+ (u nas 25) w PATH oraz folder javafx-sdk/ w repo
# (albo ustaw PATH_TO_FX na katalog lib SDK).

$ErrorActionPreference = 'Stop'
$RepoRoot = $PSScriptRoot

function Resolve-JavaFxLib {
    if ($env:PATH_TO_FX -and (Test-Path (Join-Path $env:PATH_TO_FX 'javafx.controls.jar'))) {
        return (Resolve-Path $env:PATH_TO_FX).Path
    }

    $candidates = @(
        (Join-Path $RepoRoot 'javafx-sdk\lib'),
        (Join-Path $RepoRoot '..\javafx-sdk\lib')
    )

    foreach ($c in $candidates) {
        if (Test-Path (Join-Path $c 'javafx.controls.jar')) {
            return (Resolve-Path $c).Path
        }
    }

    return $null
}

function Ensure-Java {
    $javac = Get-Command javac -ErrorAction SilentlyContinue
    $java = Get-Command java -ErrorAction SilentlyContinue
    if ($javac -and $java) { return }

    $jdkCandidates = @(
        $env:JAVA_HOME,
        "$env:USERPROFILE\.jdks\openjdk-25.0.1",
        "$env:USERPROFILE\.jdks\openjdk-25",
        'C:\Program Files\Java\jdk-25'
    ) | Where-Object { $_ }

    foreach ($raw in $jdkCandidates) {
        if (-not (Test-Path $raw)) { continue }
        $bin = Join-Path $raw 'bin'
        if (Test-Path (Join-Path $bin 'javac.exe')) {
            $env:JAVA_HOME = (Resolve-Path $raw).Path
            if ($env:Path -notlike "*$bin*") {
                $env:Path = "$bin;" + $env:Path
            }
            return
        }
    }

    throw "Nie znaleziono javac/java. Zainstaluj JDK 25 i dodaj bin do PATH (lub ustaw JAVA_HOME)."
}

Ensure-Java
$fxLib = Resolve-JavaFxLib
if (-not $fxLib) {
    Write-Host @"
Brak JavaFX SDK.
1. Pobierz JavaFX SDK (wersja jak JDK, np. 25) dla Windows z:
   https://gluonhq.com/products/javafx/
2. Rozpakuj do: $RepoRoot\javafx-sdk
   (musi istnieć plik javafx-sdk\lib\javafx.controls.jar)
Albo ustaw: `$env:PATH_TO_FX = 'C:\sciezka\do\javafx-sdk\lib'
"@ -ForegroundColor Yellow
    throw "PATH_TO_FX nieustawione / javafx-sdk nie znalezione."
}

$env:PATH_TO_FX = $fxLib
$script:FxModules = 'javafx.controls,javafx.graphics'

function comp_FX {
    param(
        [Parameter(Mandatory = $true, Position = 0, ValueFromRemainingArguments = $true)]
        [string[]]$Files
    )

    $resolved = @()
    foreach ($f in $Files) {
        $file = $f.TrimStart('@')
        if (-not $file.EndsWith('.java')) { $file = "$file.java" }
        if (-not (Test-Path $file)) {
            Write-Error "Nie znaleziono pliku: $file"
            return
        }
        $resolved += (Resolve-Path $file).Path
    }

    & javac --module-path $env:PATH_TO_FX --add-modules $script:FxModules @resolved
    if ($LASTEXITCODE -eq 0) {
        $names = ($resolved | ForEach-Object { Split-Path $_ -Leaf }) -join ', '
        Write-Host "OK: skompilowano $names" -ForegroundColor Green
    }
}

function run_FX {
    param(
        [Parameter(Mandatory = $true, Position = 0)]
        [string]$File
    )

    $File = $File.TrimStart('@')
    $Class = [System.IO.Path]::GetFileNameWithoutExtension($File)

    & java --module-path $env:PATH_TO_FX --add-modules $script:FxModules `
        --enable-native-access=javafx.graphics `
        $Class
}

Write-Host "JavaFX OK" -ForegroundColor Green
Write-Host "  PATH_TO_FX = $env:PATH_TO_FX"
Write-Host "  Uzycie:  comp_FX HelloWorld.java   |   run_FX HelloWorld"
Write-Host "           comp_FX Ball.java Simple_game_2.java   (kilka plikow)"
