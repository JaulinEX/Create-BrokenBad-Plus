# Create: Broken Bad +
An addon for the [Create: Broken Bad](https://www.curseforge.com/minecraft/mc-mods/create-broken-bad) mod.

## 🛠 Installation & Development Setup

To compile this mod from source, you need to manually include the dependency since it is not hosted on a public Maven repository.

1. Create a `/libs` folder in the root directory of this project.
2. Download the official **["Create: Broken Bad"](https://www.curseforge.com/minecraft/mc-mods/create-broken-bad)** JAR.
3. Place the JAR into the `/libs` folder.
> [!IMPORTANT]
> Ensure the filename matches the dependency declaration in `build.gradle`. 
`gradle
dependencies {
    implementation "blank:createbb:4.0.1" // Change this version to match your downloaded file
}`
