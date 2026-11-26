# LUNAR — The Moon, Closer Than Ever.

**LUNAR** is a native Android astronomy companion built with Kotlin, Jetpack Compose, and Material 3. It combines offline-accurate Jean Meeus lunar & solar ephemeris calculations, an interactive lunar surface explorer, a visual lunar calendar, a local night-sky viewing guide, live Open-Meteo cloud cover, educational astronomy articles, smartphone & camera photography guides, curated wallpapers, quiet alerts, and native Android home-screen widgets.

---

## Install LUNAR Directly on Your Android Phone (No PC / No USB Required)

This repository includes an automated GitHub Actions Cloud Build workflow (`.github/workflows/android-build.yml`) that compiles and packages an installable APK directly in the cloud.

1. Open this GitHub repository in your Android phone's browser.
2. Tap the **Actions** tab at the top of the repository.
3. Open the latest **Build LUNAR Android APK** run (or tap **Run workflow** to trigger a fresh build).
4. Wait until the build shows a green checkmark, then scroll down to **Artifacts**.
5. Tap **LUNAR-debug-apk** to download the ZIP file onto your phone.
6. Open your phone's **Files** app, locate the downloaded ZIP, and tap **Extract** to unpack `app-debug.apk`.
7. Tap `app-debug.apk` to install.
8. If Android prompts you, enable **Install unknown apps** for your Files/Browser app, then confirm **Install**.
9. Tap **Open** to launch **LUNAR**!

### Enabling Signed Release APKs in GitHub Actions (Optional)
If you also want GitHub Actions to produce a signed `LUNAR-release-apk` artifact, add these three secrets under **Repository Settings → Secrets and variables → Actions**:
- `RELEASE_KEYSTORE_BASE64`: Base64-encoded `.jks` keystore containing key alias `upload`
- `STORE_PASSWORD`: Keystore password
- `KEY_PASSWORD`: Key password

When those secrets are not configured, the workflow cleanly builds and uploads the ready-to-install `LUNAR-debug-apk` without failing or fabricating secrets.

---

## Key Features
- **Home — Your Moon Today**: Realistic photorealistic Moon phase visualization, illumination %, lunar age, waxing/waning status, moonrise, moonset, Earth–Moon distance, next major lunar event countdown, tonight's sky outlook, featured lunar location, Did You Know facts, featured discovery, and photography shortcuts.
- **8 Moon Phases**: Dedicated scientifically accurate detail pages for all 8 phases with previous/next navigation.
- **Explore — Interactive Lunar Surface**: Pinch-to-zoom, pan, rotate, tap markers, toggle labels, focus on features, and inspect 10 documented lunar landmarks (Tycho, Copernicus, Aristarchus, Mare Tranquillitatis, Mare Imbrium, Mare Serenitatis, Oceanus Procellarum, Tranquility Base / Apollo 11, Plato, Clavius).
- **Lunar Calendar**: Monthly visual phase calendar with illumination %, major phases, eclipse markers, Today shortcut, and full per-day detail views.
- **Tonight — Night Sky Guide**: Clear distinction between offline-accurate calculated astronomy (Moon horizon arc, altitude/azimuth, Civil/Nautical/Astronomical twilight, visible planets, bright stars, constellations, meteor showers) and live weather-dependent cloud cover via Open-Meteo, with optional GPS + manual worldwide city search.
- **Astronomy Events**: Upcoming Full Moons, Supermoons, New Moons, Meteor Showers, and NASA Goddard Canon Lunar Eclipses (2026–2028) converted to your local timezone with full penumbral/partial/totality timelines.
- **Learn & Photography Guides**: Multi-section astronomy library across 5 categories plus Smartphone and DSLR/Mirrorless lunar photography guides.
- **Wallpapers & Favorites**: 10-category astronomy collection with full-screen swipe viewer, zero-permission MediaStore gallery saving, Android ShareSheet sharing, direct WallpaperManager application, and persistent Favorites.
- **Home Screen Widgets**: Native Small (2×2), Medium (4×2), and Large (4×3) Android widgets with 5 customizable themes.
- **Settings & Privacy**: Full persistent settings via Jetpack DataStore, quiet optional notification categories, safe cache clearing (preserving favorites), and creator profile (`@unfiltered.java`).

---

## Creator
Created by **[@unfiltered.java](https://www.instagram.com/unfiltered.java/)**
