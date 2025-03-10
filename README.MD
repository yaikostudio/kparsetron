# kparsetron

🚀 **kparsetron** – A Kotlin Multiplatform Library for Parsing and Extracting Information from Popular Websites.

## 🌍 Overview

kparsetron is a powerful, lightweight, and easy-to-use **Kotlin Multiplatform** (KMP) library built on top of [ksoup](https://github.com/micahlt/ksoup) (a Kotlin-native port of BeautifulSoup). It provides a streamlined way to extract structured data from various websites, similar to what `youtube-dl` does but as a native Kotlin library.

## ✨ Features

- ✅ **Kotlin Multiplatform (JVM, Native, JS, Android, iOS, etc.)**
- ✅ **Fast and Efficient HTML Parsing** with `ksoup`
- ✅ **Supports Multiple Popular Websites**
- ✅ **Simple API for Extracting Metadata** (titles, descriptions, media links, etc.)
- ✅ **Lightweight and No External Dependencies**

## 📦 Installation

Add the dependency in your **Kotlin Multiplatform** project:

```kotlin
// Gradle (Kotlin DSL)
dependencies {
    implementation("com.yaikostudio.kparsetron:kparsetron:0.1.0")
}
```

## 🚀 Usage

### Basic Example:

```kotlin
import com.yaikostudio.kparsetron.kparsetron

fun main() {
    val parser = Kparsetron()
    val result = parser.parse("https://example.com")
    
    println("Title: ${result.title}")
    println("Description: ${result.description}")
    println("Media: ${result.mediaLinks}")
}
```

## 🌐 Supported Sites

kparsetron currently supports:

- YouTube

Support for additional sites will be added in future updates!

## 🛠 Roadmap

-

## 📝 Contributing

We welcome contributions! Feel free to fork the repo, submit PRs, or suggest new features.

## 📄 License

kparsetron is open-source and released into the public domain by the copyright holders.

## 🤖 About YaikoStudio

Built with ❤️ by [YaikoStudio](https://yaikostudio.com) for developers who love **structured data from messy web pages**.

---

*"If it's HTML, we parse it!"*

### Run Sample App

 - Desktop JVM: `./gradlew :sample:composeApp:run`
 - Android: `open project in Android Studio and run the sample app`
 - iOS: `open 'sample/iosApp/iosApp.xcodeproj' in Xcode and run the sample app`
 - JavaScript: `./gradlew :sample:composeApp:jsBrowserRun`
 - Wasm: `./gradlew :sample:composeApp:wasmJsBrowserRun`
 - Linux/Macos/Windows native: `./gradlew :sample:terminalApp:runDebugExecutable[architecture]`

### Publish to MavenLocal

1) Run `./gradlew :shared:publishToMavenLocal`
2) Open `~/.m2/repository/com/yaikostudio/kparsetron/"}`

### Publish to MavenCentral

1) Create a account and a namespace on Sonatype:
   https://central.sonatype.org/register/central-portal/#create-an-account
2) Add developer id, name, email and the project url to
   `/convention-plugins/src/main/kotlin/convention.publication.gradle.kts`
3) Generate a GPG key:
   https://getstream.io/blog/publishing-libraries-to-mavencentral-2021/#generating-a-gpg-key-pair
   ```
   gpg --full-gen-key
   gpg --keyserver keyserver.ubuntu.com --send-keys XXXXXXXX
   gpg --export-secret-key XXXXXXXX > XXXXXXXX.gpg
   ```
4) Add the secrets to `local.properties`:
   ```
   signing.keyId=XXXXXXXX
   signing.password=[key password]
   signing.secretKeyRingFile=../XXXXXXXX.gpg
   ossrhUsername=[Sonatype token_user]
   ossrhPassword=[Sonatype token_password]
   ```
5) Run `./gradlew :shared:publishAllPublicationsToSonatypeRepository`
