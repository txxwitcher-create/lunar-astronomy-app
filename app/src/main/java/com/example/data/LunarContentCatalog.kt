package com.example.data

import com.example.astronomy.*

object LunarContentCatalog {

    val phaseDetails: Map<LunarPhaseType, PhaseDetailContent> = mapOf(
        LunarPhaseType.NEW_MOON to PhaseDetailContent(
            phase = LunarPhaseType.NEW_MOON,
            headline = "Conjunction in Shadow",
            explanation = "The Moon does not emit its own visible light—it shines solely by reflecting sunlight. At New Moon, the Moon sits roughly between Earth and the Sun, so sunlight illuminates the far side while the Earth-facing hemisphere rests almost entirely in shadow.",
            appearanceFromEarth = "Essentially invisible to the naked eye against the daytime sky. Only when the Sun, Moon, and Earth align precisely along the lunar nodes does the Moon's dark silhouette pass in front of the Sun to create a solar eclipse.",
            whyItOccurs = "Ecliptic elongation between the Sun and Moon reaches 0°. Because the Moon's orbit is tilted about 5.1° relative to Earth's orbital plane (the ecliptic), it usually passes slightly north or south of the Sun rather than eclipsing it.",
            durationInfo = "The exact instant of New Moon lasts only a moment, though the Moon stays practically invisible for roughly 24–36 hours before and after conjunction.",
            typicalRiseSet = "Rises near sunrise (~6:00 AM) and sets near sunset (~6:00 PM).",
            observationTips = listOf(
                "Prime dark-sky window of the entire lunar month for viewing the Milky Way, Andromeda Galaxy, and faint nebulae.",
                "Watch the western horizon 24–36 hours after New Moon for the razor-thin first waxing crescent—an exciting naked-eye challenge.",
                "Never scan near the Sun with binoculars or telescopes."
            ),
            photographyHint = "Leave telephoto lunar lenses packed and switch to wide-angle f/1.8–f/2.8 lenses for crisp deep-sky and Milky Way nightscapes."
        ),
        LunarPhaseType.WAXING_CRESCENT to PhaseDetailContent(
            phase = LunarPhaseType.WAXING_CRESCENT,
            headline = "First Silver Arc & Earthshine",
            explanation = "Waxing means the illuminated portion visible from Earth is growing larger each evening. As the Moon pulls eastward away from the Sun, a slender sunlit sliver emerges along the western (right-hand in the Northern Hemisphere) edge.",
            appearanceFromEarth = "A luminous silver arc framing the darker unlit disc. Look closely and you will often see 'earthshine' (the Da Vinci glow)—sunlight reflected off Earth's clouds and oceans softly illuminating the dark lunar nightside.",
            whyItOccurs = "Sun–Moon elongation widens from roughly 10° to 80°. Sunlight strikes the eastern lunar highlands at grazing angles, casting dramatic long shadows.",
            durationInfo = "Lasts approximately 6.4 days between New Moon and First Quarter.",
            typicalRiseSet = "Rises mid-morning and sets in the western sky shortly after sunset.",
            observationTips = listOf(
                "Best viewed in the western twilight sky 30–60 minutes after sunset.",
                "Use binoculars to admire Mare Crisium (Sea of Crises) gleaming near the bright crescent limb.",
                "Earthshine is brightest when the crescent is 5%–20% illuminated."
            ),
            photographyHint = "Expose twice—once quickly for the bright silver crescent, and once for 1–2 seconds to reveal ghostly earthshine across the dark disc."
        ),
        LunarPhaseType.FIRST_QUARTER to PhaseDetailContent(
            phase = LunarPhaseType.FIRST_QUARTER,
            headline = "Half-Lit Disc, Quarter Cycle",
            explanation = "Although the First Quarter Moon appears half-illuminated from Earth, its name refers to its orbital position—one quarter (25%) of the way through the 29.53-day synodic lunar cycle.",
            appearanceFromEarth = "A crisp half-circle divided straight down the middle by the lunar terminator—the line separating lunar day from lunar night. In the Northern Hemisphere the right half is lit; in the Southern Hemisphere the left half is lit.",
            whyItOccurs = "The Moon reaches 90° eastern quadrature relative to the Sun, forming a right triangle with Earth and the Sun. Sunlight hits the central meridian almost horizontally.",
            durationInfo = "Exact quadrature is instantaneous, while the visually half-lit appearance holds for roughly a day.",
            typicalRiseSet = "Rises around solar noon and sets around local midnight—highest in the sky at dusk.",
            observationTips = listOf(
                "Arguably the finest single night of the month for binoculars and telescopes: low-angle sunlight carves razor-sharp shadows across the Lunar Apennines, Ptolemaeus, and Mare Serenitatis.",
                "Ideal for evening family stargazing because the Moon is already high overhead at sunset."
            ),
            photographyHint = "Focus directly on the jagged terminator line where crater relief looks three-dimensional. Try ISO 100, f/8, 1/125s."
        ),
        LunarPhaseType.WAXING_GIBBOUS to PhaseDetailContent(
            phase = LunarPhaseType.WAXING_GIBBOUS,
            headline = "Rising Toward Full Radiance",
            explanation = "Gibbous derives from the Latin for 'humpbacked.' More than half of the Earth-facing lunar disc is illuminated, and because the Moon is waxing, the sunlit fraction continues growing night after night.",
            appearanceFromEarth = "An asymmetrical oval glowing bright ivory across the afternoon and evening sky. Major western impact craters such as Copernicus, Plato, and Tycho dramatically emerge into sunlight one by one.",
            whyItOccurs = "Elongation increases from 95° to 170°, placing the Moon progressively behind Earth relative to the Sun so nearly the entire near side catches direct sunlight.",
            durationInfo = "Lasts roughly 6.4 days between First Quarter and Full Moon.",
            typicalRiseSet = "Rises in the afternoon and sets in the pre-dawn hours.",
            observationTips = listOf(
                "Around day 9–10 of the cycle, look for the towering terraced rim of Copernicus Crater catching morning lunar sun.",
                "Around day 11–12, Aristarchus—the brightest crater on the Moon—shines unmistakably inside Oceanus Procellarum."
            ),
            photographyHint = "Capture the Waxing Gibbous during blue hour while the sky still holds deep cobalt tones for balanced dynamic range."
        ),
        LunarPhaseType.FULL_MOON to PhaseDetailContent(
            phase = LunarPhaseType.FULL_MOON,
            headline = "Opposition & Peak Illumination",
            explanation = "At Full Moon, the Moon stands 180° opposite the Sun in Earth's sky. Direct frontal sunlight floods the entire Earth-facing hemisphere, maximizing reflected brightness and revealing bright crater ejecta ray systems.",
            appearanceFromEarth = "A complete luminous circle rising in the east just as the Sun sets in the west. Because sunlight strikes straight on, crater shadows vanish—highlighting tonal contrast between pale highlands and dark volcanic maria.",
            whyItOccurs = "Earth sits between the Sun and the Moon in ecliptic longitude. When the Moon also crosses the ecliptic plane at Full Moon, Earth's shadow falls onto it, creating a lunar eclipse.",
            durationInfo = "Astronomical opposition occurs at a single instant, though the Moon appears ~98–100% full to the eye for nearly three consecutive nights.",
            typicalRiseSet = "Rises near local sunset and sets near local sunrise, staying aloft all night.",
            observationTips = listOf(
                "Trace the sweeping bright ray system radiating thousands of kilometers from Tycho Crater in the southern highlands.",
                "Watch moonrise on the eastern horizon for warm golden atmospheric refraction and the striking Moon Illusion."
            ),
            photographyHint = "Do not let auto-exposure turn the Full Moon into a featureless white orb. Drop exposure manually using the Looney 11 rule (f/11, 1/ISO shutter)."
        ),
        LunarPhaseType.WANING_GIBBOUS to PhaseDetailContent(
            phase = LunarPhaseType.WANING_GIBBOUS,
            headline = "Evening Shadows Return",
            explanation = "Waning means the visible illuminated area is steadily shrinking each night. Having passed opposition, lunar sunset begins creeping westward across the Moon's eastern limb.",
            appearanceFromEarth = "Still brilliantly lit across more than half the disc, but the eastern limb now falls into crisp velvet shadow, restoring dramatic depth to Mare Crisium and Mare Tranquillitatis.",
            whyItOccurs = "Moon–Sun elongation narrows from 190° toward 265° as the Moon continues its eastward orbit around Earth.",
            durationInfo = "Spans roughly 6.4 days from Full Moon to Last Quarter.",
            typicalRiseSet = "Rises later each evening after sunset and remains visible well into the morning daylight sky.",
            observationTips = listOf(
                "Enjoy dramatic sunset shadows sweeping across Mare Fecunditatis and Mare Tranquillitatis—the opposite lighting direction from waxing phases.",
                "Great phase to spot the Moon suspended cleanly in a crisp blue morning sky."
            ),
            photographyHint = "Morning daylight photography of the Waning Gibbous against a pale blue sky creates clean, editorial astrophotography without high-contrast blowouts."
        ),
        LunarPhaseType.LAST_QUARTER to PhaseDetailContent(
            phase = LunarPhaseType.LAST_QUARTER,
            headline = "Third Quarter Morning Beacon",
            explanation = "Also known as Third Quarter, the Moon has completed three quarters (75%) of its orbit since New Moon. Once again it appears half-lit, now illuminating the opposite hemisphere from First Quarter.",
            appearanceFromEarth = "A sharp half-disc glowing high in the southern dawn sky (left half illuminated in the Northern Hemisphere, right half in the Southern Hemisphere).",
            whyItOccurs = "The Moon reaches 270° elongation (western quadrature), heading directly ahead of Earth along Earth's orbital path around the Sun.",
            durationInfo = "Exact Last Quarter is instantaneous, remaining visually half-lit for roughly one day.",
            typicalRiseSet = "Rises near local midnight and sets near local noon.",
            observationTips = listOf(
                "Step outside shortly before sunrise to inspect Copernicus and Oceanus Procellarum under dramatic lunar sunset shadows.",
                "Because Earth is traveling directly toward the spot the Last Quarter Moon occupies, looking straight at it shows you the direction of Earth's orbit through space!"
            ),
            photographyHint = "Dawn twilight softens contrast nicely—shoot at 1/160s, ISO 100–200 to retain crisp lunar Apennine ridge detail."
        ),
        LunarPhaseType.WANING_CRESCENT to PhaseDetailContent(
            phase = LunarPhaseType.WANING_CRESCENT,
            headline = "Dawn Sliver Before Renewal",
            explanation = "In the final days of the synodic cycle, the waning Moon draws closer to the Sun's glare in the eastern pre-dawn sky, narrowing into a delicate silver bow.",
            appearanceFromEarth = "A graceful, fragile morning crescent often accompanied by luminous earthshine softly tracing the rest of the lunar sphere before sunrise.",
            whyItOccurs = "Elongation shrinks from ~300° back toward 360° (0°), leaving only the westernmost lunar limb angled toward the Sun.",
            durationInfo = "Lasts roughly 6.4 days until the next New Moon begins a fresh lunar month.",
            typicalRiseSet = "Rises in the quiet pre-dawn hours before sunrise and sets in the afternoon.",
            observationTips = listOf(
                "Pair dawn viewing with bright morning planets such as Venus or Jupiter when they align near the ecliptic.",
                "Ideal morning skies for observing Grimaldi and Oceanus Procellarum right before sunrise washes out the horizon."
            ),
            photographyHint = "Frame the Waning Crescent low over distant mountains or calm water during civil dawn for balanced crimson-and-silver horizons."
        )
    )

    val lunarFeatures: List<LunarFeature> = listOf(
        LunarFeature(
            id = "tycho",
            name = "Tycho Crater",
            latinMeaning = "Named after astronomer Tycho Brahe",
            type = LunarFeatureType.CRATER,
            mapX = 0.44f,
            mapY = 0.80f,
            coordinates = "43.3° S, 11.2° W",
            diameterKm = 85.0,
            ageEstimate = "~108 million years (youngest major crater)",
            summary = "An iconic southern highland impact crater crowned by bright ejecta rays stretching over 1,500 km across the lunar near side.",
            formation = "Carved by a colossal asteroid impact roughly 108 million years ago—during Earth's Cretaceous period when dinosaurs roamed. The shock melted regolith and blasted fresh, bright anorthositic rock thousands of kilometers outward.",
            characteristics = "Sharp terraced inner walls rising 4.7 km above the crater floor and a striking 2 km tall central rebound peak formed as compressed rock snapped back upward.",
            scientificSignificance = "Apollo 17 astronauts collected glass-rich landslide material believed to be Tycho ejecta over 2,200 km away, allowing planetary geologists to radiometrically date the impact.",
            explorationHistory = "Surveyor 7 landed cleanly on Tycho's northern outer rim in January 1968, performing the first robotic soil chemistry analysis of the lunar highlands.",
            relatedMissions = listOf("Surveyor 7 (1968)", "Apollo 17 (ejecta samples)", "Lunar Reconnaissance Orbiter"),
            relatedFeatureIds = listOf("clavius", "copernicus"),
            bestViewingPhase = "Day 8–9 (Waxing Gibbous) for terraced shadows; Full Moon for sweeping bright rays.",
            imageUrl = "https://images-assets.nasa.gov/image/PIA14208/PIA14208~medium.jpg",
            fallbackVisualType = VisualFallbackType.LUNAR_SURFACE
        ),
        LunarFeature(
            id = "copernicus",
            name = "Copernicus Crater",
            latinMeaning = "Named after Nicolaus Copernicus",
            type = LunarFeatureType.CRATER,
            mapX = 0.35f,
            mapY = 0.43f,
            coordinates = "9.6° N, 20.1° W",
            diameterKm = 93.0,
            ageEstimate = "~800 million years",
            summary = "Often called the 'Monarch of the Moon,' Copernicus is a textbook complex impact crater surrounded by pale splash rays in eastern Oceanus Procellarum.",
            formation = "Excavated ~800 million years ago when an ~8 km wide impactor struck basaltic mare plains, punching down into lighter highland crust beneath.",
            characteristics = "Dramatic concentric terraced rim walls, a rugged hummocky floor, and a cluster of three distinct central mountain peaks rising 1.2 km high.",
            scientificSignificance = "Defines the Copernican Period of lunar geologic time—the youngest major era of lunar surface history.",
            explorationHistory = "Photographed dramatically in 1966 by Lunar Orbiter 2 in what NASA scientists dubbed 'the Picture of the Century,' and sampled indirectly via rays at the Apollo 12 site.",
            relatedMissions = listOf("Lunar Orbiter 2 (1966)", "Apollo 12 (ray material)", "LRO"),
            relatedFeatureIds = listOf("tycho", "aristarchus", "oceanus_procellarum"),
            bestViewingPhase = "Day 9–10 (Waxing Gibbous) and Day 23 (Waning Crescent).",
            imageUrl = "https://images-assets.nasa.gov/image/PIA13517/PIA13517~medium.jpg",
            fallbackVisualType = VisualFallbackType.LUNAR_SURFACE
        ),
        LunarFeature(
            id = "aristarchus",
            name = "Aristarchus Crater",
            latinMeaning = "Named after Aristarchus of Samos",
            type = LunarFeatureType.CRATER,
            mapX = 0.21f,
            mapY = 0.34f,
            coordinates = "23.7° N, 47.4° W",
            diameterKm = 40.0,
            ageEstimate = "~450 million years",
            summary = "The brightest large geological feature on the Moon—so reflective that it can be glimpsed even in earthshine on the unlit lunar disc.",
            formation = "Formed relatively recently on the edge of the volcanic Aristarchus Plateau, exposing bright, unweathered silicate rock and glass that has not yet darkened from micrometeorite space weathering.",
            characteristics = "Nearly twice the albedo of surrounding lunar terrain, perched next to Vallis Schröteri—the largest sinuous volcanic rille (ancient lava channel) on the Moon.",
            scientificSignificance = "One of the most geologically diverse volcanic provinces on the Moon, showing evidence of explosive pyroclastic fire-fountain deposits and rare trace radon outgassing.",
            explorationHistory = "Extensively mapped by Clementine, Lunar Prospector, and NASA's Lunar Reconnaissance Orbiter; once considered a prime target for canceled Apollo 18.",
            relatedMissions = listOf("Lunar Prospector", "Lunar Reconnaissance Orbiter", "Chandrayaan-1"),
            relatedFeatureIds = listOf("oceanus_procellarum", "copernicus"),
            bestViewingPhase = "Day 11–12 (Waxing Gibbous) when sunlight sweeps across the plateau.",
            imageUrl = "https://images-assets.nasa.gov/image/PIA14932/PIA14932~medium.jpg",
            fallbackVisualType = VisualFallbackType.LUNAR_SURFACE
        ),
        LunarFeature(
            id = "mare_tranquillitatis",
            name = "Mare Tranquillitatis",
            latinMeaning = "Sea of Tranquility",
            type = LunarFeatureType.MARE,
            mapX = 0.69f,
            mapY = 0.44f,
            coordinates = "8.5° N, 31.4° E",
            diameterKm = 876.0,
            ageEstimate = "~3.6–3.8 billion years",
            summary = "A vast, dark volcanic basalt plain with a subtle bluish mineral tint—forever famed as the landing ground of Apollo 11.",
            formation = "Not an ocean of water! Lunar maria ('seas') are colossal ancient impact basins that flooded with runny, iron- and titanium-rich volcanic magma billions of years ago before cooling into smooth dark basalt plains.",
            characteristics = "Exceptionally rich in titanium-bearing ilmenite basalt, giving Mare Tranquillitatis a subtly cooler slate-blue hue compared to neighboring Mare Serenitatis.",
            scientificSignificance = "Contains the deepest known lunar lava tube skylight ('Tranquility Pit'), offering natural radiation shielding and stable temperatures for future human habitats.",
            explorationHistory = "Site of Ranger 8, Surveyor 5, and humanity's first crewed Moon landing—Apollo 11—on July 20, 1969.",
            relatedMissions = listOf("Apollo 11 (1969)", "Surveyor 5 (1967)", "Ranger 8 (1965)"),
            relatedFeatureIds = listOf("apollo_11", "mare_serenitatis"),
            bestViewingPhase = "Day 5–6 (Waxing Crescent) and Day 19 (Waning Gibbous).",
            imageUrl = "https://images-assets.nasa.gov/image/as11-40-5875/as11-40-5875~medium.jpg",
            fallbackVisualType = VisualFallbackType.APOLLO_SURFACE
        ),
        LunarFeature(
            id = "mare_imbrium",
            name = "Mare Imbrium",
            latinMeaning = "Sea of Showers / Rains",
            type = LunarFeatureType.MARE,
            mapX = 0.36f,
            mapY = 0.28f,
            coordinates = "32.8° N, 15.6° W",
            diameterKm = 1145.0,
            ageEstimate = "~3.85 billion years (basin)",
            summary = "One of the largest impact basins in the entire Solar System, rimmed by towering lunar mountain chains including the Apennines, Alps, and Carpathians.",
            formation = "Blasted open ~3.85 billion years ago by a protoplanet-sized asteroid ~250 km across. Over the next several hundred million years, immense flood-basalt lava eruptions submerged the crater floor—creating a flat dark volcanic plain rather than an ocean of water.",
            characteristics = "Bordered by Montes Apenninus rising over 5 km high, with Sinus Iridum ('Bay of Rainbows') curving gracefully along its northwestern edge.",
            scientificSignificance = "Provides the chronological cornerstone of early Solar System bombardment history thanks to samples returned from its southeastern rim by Apollo 15.",
            explorationHistory = "Explored by Soviet Lunokhod 1 (first robotic rover on another world, 1970), Apollo 15 near Hadley Rille (1971), and China's Chang'e 3 Yutu rover (2013).",
            relatedMissions = listOf("Lunokhod 1 (1970)", "Apollo 15 (1971)", "Chang'e 3 (2013)"),
            relatedFeatureIds = listOf("plato", "copernicus", "oceanus_procellarum"),
            bestViewingPhase = "Day 8–10 (First Quarter to Waxing Gibbous).",
            imageUrl = "https://images-assets.nasa.gov/image/PIA00405/PIA00405~medium.jpg",
            fallbackVisualType = VisualFallbackType.FULL_MOON
        ),
        LunarFeature(
            id = "mare_serenitatis",
            name = "Mare Serenitatis",
            latinMeaning = "Sea of Serenity",
            type = LunarFeatureType.MARE,
            mapX = 0.59f,
            mapY = 0.30f,
            coordinates = "28.0° N, 17.5° E",
            diameterKm = 674.0,
            ageEstimate = "~3.8–3.9 billion years",
            summary = "A nearly circular dark volcanic plain nestled directly north of Mare Tranquillitatis, crisscrossed by long sinuous wrinkle ridges.",
            formation = "Like all lunar maria, Mare Serenitatis has never held liquid water. It is an ancient giant impact basin filled with solidified basaltic lava flows that slowly buckled into compression wrinkle ridges as the basin cooled and subsided.",
            characteristics = "Distinct color boundary where lighter basalt of Serenitatis meets darker, titanium-rich basalt of Tranquillitatis; ringed by the Haemus and Caucasus mountains.",
            scientificSignificance = "Apollo 17's Taurus-Littrow valley on its eastern margin yielded orange volcanic glass beads proving ancient explosive fire-fountain volcanism on the Moon.",
            explorationHistory = "Explored by Luna 21 / Lunokhod 2 and the final Apollo mission, Apollo 17 (December 1972).",
            relatedMissions = listOf("Apollo 17 (1972)", "Lunokhod 2 (1973)"),
            relatedFeatureIds = listOf("mare_tranquillitatis", "mare_imbrium"),
            bestViewingPhase = "Day 6–7 (First Quarter) when wrinkle ridges cast delicate shadows.",
            imageUrl = "https://images-assets.nasa.gov/image/as17-134-20384/as17-134-20384~medium.jpg",
            fallbackVisualType = VisualFallbackType.APOLLO_SURFACE
        ),
        LunarFeature(
            id = "oceanus_procellarum",
            name = "Oceanus Procellarum",
            latinMeaning = "Ocean of Storms",
            type = LunarFeatureType.OCEANUS,
            mapX = 0.22f,
            mapY = 0.49f,
            coordinates = "18.4° N, 57.4° W",
            diameterKm = 2568.0,
            ageEstimate = "~1.2–3.5 billion years (youngest maria flows)",
            summary = "The single largest dark basaltic expanse on the Moon, stretching over 2,500 kilometers north-to-south across the western near side.",
            formation = "Despite its poetic Latin name ('Ocean'), Oceanus Procellarum is completely waterless—it is a gigantic expanse of solidified ancient lava plains likely fed by magma plumbing along deep crustal rifts detected by NASA's GRAIL mission.",
            characteristics = "Encompasses over 4 million square kilometers—roughly twice the area of the Mediterranean Sea—and hosts some of the Moon's youngest basaltic flows.",
            scientificSignificance = "China's Chang'e 5 mission returned 2-billion-year-old volcanic basalts from northern Procellarum, proving the Moon stayed volcanically active far longer than previously thought.",
            explorationHistory = "Visited by Luna 9 (first soft landing on the Moon, 1966), Surveyor 1, Apollo 12, and Chang'e 5 (2020).",
            relatedMissions = listOf("Luna 9 (1966)", "Apollo 12 (1969)", "Chang'e 5 (2020)", "GRAIL"),
            relatedFeatureIds = listOf("aristarchus", "copernicus", "mare_imbrium"),
            bestViewingPhase = "Day 10–13 (Waxing Gibbous) and Day 23–25 (Waning Crescent).",
            imageUrl = "https://images-assets.nasa.gov/image/PIA00405/PIA00405~medium.jpg",
            fallbackVisualType = VisualFallbackType.FULL_MOON
        ),
        LunarFeature(
            id = "apollo_11",
            name = "Tranquility Base (Apollo 11)",
            latinMeaning = "Statio Tranquillitatis",
            type = LunarFeatureType.LANDING_SITE,
            mapX = 0.66f,
            mapY = 0.50f,
            coordinates = "0.67° N, 23.47° E",
            diameterKm = 1.0,
            ageEstimate = "Landed July 20, 1969",
            summary = "The historic basalt plain in southwestern Mare Tranquillitatis where Neil Armstrong and Buzz Aldrin became the first humans to walk on another world.",
            formation = "Situated on a smooth volcanic mare sheet chosen specifically for safe, obstacle-free descent radar reflection during the Lunar Module Eagle's approach.",
            characteristics = "Today, the descent stage of Eagle, laser retroreflector array (still bouncing Earth laser pulses to measure the Moon's 3.8 cm/year drift!), and bootprints remain preserved in airless vacuum.",
            scientificSignificance = "Returned 21.55 kg of lunar rocks and regolith, revolutionizing planetary science and laying the foundation for the Giant Impact Hypothesis of the Moon's origin.",
            explorationHistory = "Imaged repeatedly at half-meter resolution by NASA's Lunar Reconnaissance Orbiter, clearly showing Eagle's shadow and astronaut footpaths.",
            relatedMissions = listOf("Apollo 11 (1969)", "Lunar Reconnaissance Orbiter"),
            relatedFeatureIds = listOf("mare_tranquillitatis", "mare_serenitatis"),
            bestViewingPhase = "Day 5–6 (Waxing Crescent) for nearby craters Moltke and Sabine.",
            imageUrl = "https://images-assets.nasa.gov/image/as11-40-5903/as11-40-5903~medium.jpg",
            fallbackVisualType = VisualFallbackType.APOLLO_SURFACE
        ),
        LunarFeature(
            id = "plato",
            name = "Plato Crater",
            latinMeaning = "Named after philosopher Plato",
            type = LunarFeatureType.CRATER,
            mapX = 0.44f,
            mapY = 0.16f,
            coordinates = "51.6° N, 9.3° W",
            diameterKm = 101.0,
            ageEstimate = "~3.84 billion years",
            summary = "A striking, ink-dark lava-flooded crater perched along the northern rim of Mare Imbrium—famous for sawtooth morning shadows across its glassy floor.",
            formation = "Formed shortly after the Imbrium impact and subsequently flooded from below by dark volcanic basalt that buried its original rough floor and central peaks.",
            characteristics = "Appears foreshortened as a crisp dark oval near the northern limb; jagged 2 km rim peaks cast dramatic dagger-like shadows at lunar sunrise.",
            scientificSignificance = "A benchmark visual clarity test for amateur astronomers: spotting the tiny 2 km craterlets on Plato's smooth floor confirms steady atmospheric 'seeing.'",
            explorationHistory = "Studied extensively by orbital radar and thermal infrared mapping aboard LRO.",
            relatedMissions = listOf("Lunar Reconnaissance Orbiter"),
            relatedFeatureIds = listOf("mare_imbrium", "copernicus"),
            bestViewingPhase = "Day 8–9 (Waxing Gibbous) for razor-sharp rim spire shadows.",
            imageUrl = "https://images-assets.nasa.gov/image/PIA14208/PIA14208~medium.jpg",
            fallbackVisualType = VisualFallbackType.LUNAR_SURFACE
        ),
        LunarFeature(
            id = "clavius",
            name = "Clavius Crater",
            latinMeaning = "Named after mathematician Christopher Clavius",
            type = LunarFeatureType.CRATER,
            mapX = 0.43f,
            mapY = 0.87f,
            coordinates = "58.4° S, 14.4° W",
            diameterKm = 231.0,
            ageEstimate = "~3.9 billion years",
            summary = "One of the largest walled impact basins on the Moon, instantly recognizable by a graceful curving arc of progressively smaller craters inside its floor.",
            formation = "An ancient Nectarian-era impact basin scarred by billions of years of subsequent impacts, showcasing lunar crater superposition in miniature.",
            characteristics = "Despite its immense 231 km width, the curvature of the Moon means an astronaut standing at Clavius's center could barely see its distant 3.5 km tall rim walls.",
            scientificSignificance = "In 2020, NASA's airborne SOFIA observatory made the landmark discovery of molecular water (H₂O) trapped inside tiny sunlit glass grains right in Clavius Crater.",
            explorationHistory = "Observed by NASA SOFIA infrared telescope and LRO.",
            relatedMissions = listOf("NASA SOFIA (2020 water discovery)", "Lunar Reconnaissance Orbiter"),
            relatedFeatureIds = listOf("tycho"),
            bestViewingPhase = "Day 8–9 (Waxing Gibbous) and Day 22 (Last Quarter).",
            imageUrl = "https://images-assets.nasa.gov/image/PIA14208/PIA14208~medium.jpg",
            fallbackVisualType = VisualFallbackType.LUNAR_SURFACE
        ),
        LunarFeature(
            id = "mare_crisium",
            name = "Mare Crisium",
            latinMeaning = "Sea of Crises",
            type = LunarFeatureType.MARE,
            mapX = 0.80f,
            mapY = 0.36f,
            coordinates = "17.0° N, 59.1° E",
            diameterKm = 555.0,
            ageEstimate = "~3.85 billion years",
            summary = "A sharply isolated, oval volcanic basin near the Moon's eastern limb—one of the first dark maria illuminated during the young Waxing Crescent.",
            formation = "Excavated by an asteroid impact ~3.9 billion years ago and flooded by dark basaltic lava flows that formed a nearly flat basin floor ringed by steep highland cliffs.",
            characteristics = "Dramatically shifts its apparent distance from the Moon's eastern edge due to lunar libration, making it a favorite visual marker of lunar rocking.",
            scientificSignificance = "Luna 24 returned a 1.6-meter basalt core sample from Mare Crisium in 1976 containing tiny traces of indigenous lunar water.",
            explorationHistory = "Target of Soviet Luna 15 and Luna 24 sample-return missions, and commercial CLPS polar-era landers.",
            relatedMissions = listOf("Luna 24 (1976)", "Lunar Reconnaissance Orbiter"),
            relatedFeatureIds = listOf("mare_tranquillitatis", "mare_serenitatis"),
            bestViewingPhase = "Day 3–4 (Waxing Crescent) and Day 17 (Waning Gibbous).",
            imageUrl = "https://images-assets.nasa.gov/image/PIA00405/PIA00405~medium.jpg",
            fallbackVisualType = VisualFallbackType.FULL_MOON
        ),
        LunarFeature(
            id = "montes_apenninus",
            name = "Montes Apenninus",
            latinMeaning = "Lunar Apennine Mountains",
            type = LunarFeatureType.MOUNTAIN,
            mapX = 0.48f,
            mapY = 0.36f,
            coordinates = "18.9° N, 3.7° W",
            diameterKm = 600.0,
            ageEstimate = "~3.85 billion years",
            summary = "The most majestic mountain range on the Moon—a towering 600-kilometer arc of jagged peaks rising over 5.4 km along the southeastern rim of Mare Imbrium.",
            formation = "Not formed by plate tectonics like Earth's Alps or Himalayas! Lunar mountains were uplifted violently in minutes when the giant Imbrium impact folded and thrust crustal blocks kilometers skyward.",
            characteristics = "Home to Mons Huygens (the Moon's tallest mountain peak at 5.5 km) and Mons Hadley, casting dramatic needle-sharp shadows at First Quarter.",
            scientificSignificance = "Exposes ancient pre-Imbrium lower crustal rocks uplifted from deep beneath the lunar surface.",
            explorationHistory = "Explored directly on foot and by Lunar Rover during Apollo 15's breathtaking touchdown near Mons Hadley Delta.",
            relatedMissions = listOf("Apollo 15 (1971)", "Lunar Reconnaissance Orbiter"),
            relatedFeatureIds = listOf("mare_imbrium", "hadley_rille", "apollo_15"),
            bestViewingPhase = "Day 7–8 (First Quarter) when low sunrise light sculptures every summit.",
            imageUrl = "https://images-assets.nasa.gov/image/as15-88-11866/as15-88-11866~medium.jpg",
            fallbackVisualType = VisualFallbackType.LUNAR_SURFACE
        ),
        LunarFeature(
            id = "vallis_alpes",
            name = "Vallis Alpes",
            latinMeaning = "Alpine Valley",
            type = LunarFeatureType.MOUNTAIN,
            mapX = 0.51f,
            mapY = 0.17f,
            coordinates = "48.5° N, 3.2° E",
            diameterKm = 166.0,
            ageEstimate = "~3.8 billion years",
            summary = "A dramatic straight tectonic canyon slicing clean through the Lunar Alps between Mare Imbrium and Mare Frigoris.",
            formation = "A colossal graben—a block of lunar crust that fractured and dropped downward under stress from the Imbrium impact, later flooded by smooth dark lava.",
            characteristics = "Measures 166 km long and up to 10 km wide, with a hairline sinuous lava rille meandering straight down its centerline—a coveted high-magnification telescope test.",
            scientificSignificance = "Illustrates how impact-induced radial fractures guided subsequent volcanic magma plumbing across the lunar crust.",
            explorationHistory = "Mapped at sub-meter detail by Lunar Reconnaissance Orbiter Narrow Angle Cameras.",
            relatedMissions = listOf("Lunar Reconnaissance Orbiter"),
            relatedFeatureIds = listOf("plato", "mare_imbrium", "montes_apenninus"),
            bestViewingPhase = "Day 7–8 (First Quarter) and Day 21 (Last Quarter).",
            imageUrl = "https://images-assets.nasa.gov/image/PIA13517/PIA13517~medium.jpg",
            fallbackVisualType = VisualFallbackType.LUNAR_SURFACE
        ),
        LunarFeature(
            id = "lunar_south_pole",
            name = "Shackleton & Lunar South Pole",
            latinMeaning = "Polar Permanently Shadowed Region",
            type = LunarFeatureType.LUNAR_POLE,
            mapX = 0.50f,
            mapY = 0.94f,
            coordinates = "89.9° S, 0.0° E",
            diameterKm = 21.0,
            ageEstimate = "~3.6 billion years",
            summary = "Humanity's next frontier: sunlit polar ridges bordering permanently shadowed crater floors holding ancient trapped water ice.",
            formation = "Because the Moon's rotational axis tilts only 1.54°, sunlight grazes the poles almost horizontally. Deep crater interiors like Shackleton never see the Sun, creating cryogenic traps colder than Pluto (-240°C).",
            characteristics = "Adjacent rim peaks ('Peaks of Eternal Light') stay illuminated over 80–90% of the lunar year, enabling continuous solar power mere kilometers from frozen water deposits.",
            scientificSignificance = "Water ice here can be split into breathable oxygen, drinking water, and liquid hydrogen/oxygen rocket fuel—transforming the Moon into a sustainable outpost.",
            explorationHistory = "Confirmed to harbor water ice by LCROSS impactor (2009), Chandrayaan-1 M3, and LRO; explored nearby by India's Chandrayaan-3 (2023) and targeted by NASA's Artemis crewed landings.",
            relatedMissions = listOf("LCROSS (2009)", "Chandrayaan-3 (2023)", "LRO", "Artemis Program"),
            relatedFeatureIds = listOf("lunar_north_pole", "clavius"),
            bestViewingPhase = "Best observed during favorable southern lunar libration near Full Moon.",
            imageUrl = "https://images-assets.nasa.gov/image/PIA13517/PIA13517~medium.jpg",
            fallbackVisualType = VisualFallbackType.LUNAR_SURFACE
        ),
        LunarFeature(
            id = "lunar_north_pole",
            name = "Peary Crater & Lunar North Pole",
            latinMeaning = "Northern Polar Highland Region",
            type = LunarFeatureType.LUNAR_POLE,
            mapX = 0.50f,
            mapY = 0.06f,
            coordinates = "88.6° N, 33.0° E",
            diameterKm = 73.0,
            ageEstimate = "~3.9 billion years",
            summary = "Ancient battered highland terrain crowning the Moon's northern spin axis, where long grazing shadows conceal radar-bright ice deposits.",
            formation = "Carved during the intense Late Heavy Bombardment; low solar angles leave northern polar crater bottoms locked in perpetual darkness.",
            characteristics = "Peary's northern rim enjoys prolonged sunlight during northern lunar summer while smaller interior craters remain frigid cold traps.",
            scientificSignificance = "Radar instruments on Chandrayaan-1 and LRO detected anomalous circular polarization signatures consistent with dozens of small ice-rich polar craters.",
            explorationHistory = "Surveyed extensively by Clementine, Chandrayaan-1 Mini-SAR, and NASA LRO.",
            relatedMissions = listOf("Chandrayaan-1 (2008)", "Lunar Reconnaissance Orbiter"),
            relatedFeatureIds = listOf("lunar_south_pole", "plato"),
            bestViewingPhase = "Favorable northern libration during Waxing Gibbous to Full Moon.",
            imageUrl = "https://images-assets.nasa.gov/image/PIA14208/PIA14208~medium.jpg",
            fallbackVisualType = VisualFallbackType.LUNAR_SURFACE
        ),
        LunarFeature(
            id = "vallis_schroteri",
            name = "Vallis Schröteri",
            latinMeaning = "Schröter's Valley (Cobra Head)",
            type = LunarFeatureType.RILLE_CHANNEL,
            mapX = 0.19f,
            mapY = 0.31f,
            coordinates = "26.2° N, 50.8° W",
            diameterKm = 160.0,
            ageEstimate = "~3.5 billion years",
            summary = "The grandest sinuous volcanic rille on the Moon—a winding 160 km ancient lava canyon erupting from a dramatic 6 km crater dubbed the Cobra Head.",
            formation = "Thermal erosion carved by torrential rivers of ultra-hot, low-viscosity basaltic lava erupting across the Aristarchus Plateau billions of years ago.",
            characteristics = "Reaches 10 km wide and 1 km deep, with a narrower inner rille winding along its floor like a dried river channel on Earth.",
            scientificSignificance = "Demonstrates the enormous eruption rates of ancient lunar flood volcanism and reveals mantle volatile chemistry.",
            explorationHistory = "Studied in detail by Apollo 15 orbital photography and LRO stereo elevation imaging.",
            relatedMissions = listOf("Apollo 15 Orbital Survey", "Lunar Reconnaissance Orbiter"),
            relatedFeatureIds = listOf("aristarchus", "hadley_rille"),
            bestViewingPhase = "Day 11–12 (Waxing Gibbous) along the terminator.",
            imageUrl = "https://images-assets.nasa.gov/image/PIA14932/PIA14932~medium.jpg",
            fallbackVisualType = VisualFallbackType.LUNAR_SURFACE
        ),
        LunarFeature(
            id = "hadley_rille",
            name = "Rima Hadley (Hadley Rille)",
            latinMeaning = "Hadley Volcanic Channel",
            type = LunarFeatureType.RILLE_CHANNEL,
            mapX = 0.52f,
            mapY = 0.30f,
            coordinates = "25.0° N, 3.0° E",
            diameterKm = 135.0,
            ageEstimate = "~3.3 billion years",
            summary = "A sweeping 300-meter-deep winding lava channel snaking along the foot of the Lunar Apennines—visited directly by Apollo 15 astronauts.",
            formation = "Formed either as an open fast-flowing basalt lava channel or a giant collapsed underground lava tube during Mare Imbrium volcanic flooding.",
            characteristics = "Average width ~1.5 km with stratified bedrock basalt outcrops clearly exposed along its steep upper walls.",
            scientificSignificance = "Apollo 15 astronauts stood right on its rim and photographed distinct horizontal lava flow layers—providing our only ground-level view of lunar mare stratigraphy.",
            explorationHistory = "Explored on foot and by Lunar Rover by David Scott and James Irwin in July 1971.",
            relatedMissions = listOf("Apollo 15 (1971)", "Lunar Reconnaissance Orbiter"),
            relatedFeatureIds = listOf("apollo_15", "montes_apenninus", "vallis_schroteri"),
            bestViewingPhase = "Day 7–8 (First Quarter) under crisp grazing sunlight.",
            imageUrl = "https://images-assets.nasa.gov/image/as15-88-11866/as15-88-11866~medium.jpg",
            fallbackVisualType = VisualFallbackType.APOLLO_SURFACE
        ),
        LunarFeature(
            id = "apollo_15",
            name = "Hadley-Apennine (Apollo 15)",
            latinMeaning = "First Lunar Rover Expedition Site",
            type = LunarFeatureType.LANDING_SITE,
            mapX = 0.51f,
            mapY = 0.31f,
            coordinates = "26.13° N, 3.63° E",
            diameterKm = 5.0,
            ageEstimate = "Landed July 30, 1971",
            summary = "A breathtaking amphitheater framed by 4 km high Apennine peaks and Hadley Rille, where humanity first drove an electric rover across another world.",
            formation = "Sits at the geologic boundary between ancient highland mountain blocks and younger dark volcanic mare flows.",
            characteristics = "Home to the famous 'Genesis Rock' discovery and the Fallen Astronaut memorial plaque left on the lunar regolith.",
            scientificSignificance = "Returned 77 kg of lunar material spanning primordial crust and volcanic mare strata.",
            explorationHistory = "Site of Lunar Module Falcon, still clearly visible alongside rover wheel tracks in LRO orbital photos.",
            relatedMissions = listOf("Apollo 15 (1971)", "Lunar Reconnaissance Orbiter"),
            relatedFeatureIds = listOf("hadley_rille", "montes_apenninus", "apollo_11"),
            bestViewingPhase = "Day 7–8 (First Quarter).",
            imageUrl = "https://images-assets.nasa.gov/image/as15-88-11866/as15-88-11866~medium.jpg",
            fallbackVisualType = VisualFallbackType.APOLLO_SURFACE
        ),
        LunarFeature(
            id = "apollo_17",
            name = "Taurus-Littrow Valley (Apollo 17)",
            latinMeaning = "Final Apollo Crewed Site",
            type = LunarFeatureType.LANDING_SITE,
            mapX = 0.67f,
            mapY = 0.34f,
            coordinates = "20.19° N, 30.77° E",
            diameterKm = 7.0,
            ageEstimate = "Landed December 11, 1972",
            summary = "A dramatic tectonic valley deeper than Earth's Grand Canyon on the eastern rim of Mare Serenitatis—the last place humans walked on the Moon.",
            formation = "Ringed by towering North and South Massif blocks uplifted during the Serenitatis basin impact, floored by dark volcanic ash and basalt.",
            characteristics = "At Shorty Crater, geologist Harrison Schmitt discovered vivid orange volcanic glass beads ejected by fire fountains 3.64 billion years ago.",
            scientificSignificance = "Yielded 110.5 kg of samples—the largest scientific return of any Apollo mission—plus Tycho ejecta landslide material.",
            explorationHistory = "Gene Cernan and Harrison Schmitt spent 75 hours on the surface and drove 35.9 km in Lunar Rover #3.",
            relatedMissions = listOf("Apollo 17 (1972)", "Lunar Reconnaissance Orbiter"),
            relatedFeatureIds = listOf("mare_serenitatis", "apollo_11", "tycho"),
            bestViewingPhase = "Day 5–6 (Waxing Crescent) and Day 19 (Waning Gibbous).",
            imageUrl = "https://images-assets.nasa.gov/image/as17-134-20384/as17-134-20384~medium.jpg",
            fallbackVisualType = VisualFallbackType.APOLLO_SURFACE
        ),
        LunarFeature(
            id = "lunar_interior_geology",
            name = "Lunar Crust, Mantle & Core",
            latinMeaning = "Internal Planetary Structure",
            type = LunarFeatureType.LUNAR_GEOLOGY,
            mapX = 0.50f,
            mapY = 0.56f,
            coordinates = "Global Interior (Radius 1,737 km)",
            diameterKm = 3474.0,
            ageEstimate = "~4.51 billion years",
            summary = "Beneath its dusty craters lies a differentiated planetary body with a thick highland crust, olivine-rich silicate mantle, and a compact iron core.",
            formation = "Following the Giant Impact, a global magma ocean hundreds of kilometers deep slowly cooled: light plagioclase floated up to form pale anorthositic crust while dense minerals sank to form the mantle.",
            characteristics = "The near-side crust averages ~34 km thick while the far-side crust is ~43–50 km thick—explaining why lava flooded near-side basins far more easily. A tiny ~330 km solid/fluid iron core sits at the center.",
            scientificSignificance = "Apollo seismometers and NASA's twin GRAIL gravity spacecraft mapped internal crustal thickness and mass concentrations ('mascons') beneath giant basins.",
            explorationHistory = "Probed by Apollo Passive Seismic Experiments (1969–1977) and NASA GRAIL (2012).",
            relatedMissions = listOf("Apollo Seismic Network", "NASA GRAIL (2012)", "LRO"),
            relatedFeatureIds = listOf("moonquakes_regolith", "mare_imbrium"),
            bestViewingPhase = "Visible globally through near-side contrast between pale highlands and dark basalt maria.",
            imageUrl = "https://images-assets.nasa.gov/image/PIA00405/PIA00405~medium.jpg",
            fallbackVisualType = VisualFallbackType.MINIMAL_ORBIT
        ),
        LunarFeature(
            id = "moonquakes_regolith",
            name = "Moonquakes & Regolith Dust",
            latinMeaning = "Lunar Surface Mechanics",
            type = LunarFeatureType.LUNAR_GEOLOGY,
            mapX = 0.34f,
            mapY = 0.65f,
            coordinates = "Global Surface Layer (4–15m deep)",
            diameterKm = 3474.0,
            ageEstimate = "Continuously gardened over 4.4B years",
            summary = "Billions of years of micrometeorite impacts pulverized lunar bedrock into razor-sharp powdery regolith—and the Moon still trembles with long-ringing moonquakes.",
            formation = "Without wind or rain to round rock grains, every microscopic asteroid strike shatters and glass-welds surface rock into electrostatically clingy regolith dust smelling faintly of spent gunpowder.",
            characteristics = "Four types of moonquakes exist: deep tidal quakes triggered by Earth's gravity, meteorite impact tremors, thermal quakes as frozen crust heats +250°C at lunar sunrise, and shallow tectonic quakes up to magnitude 5.5 as the cooling Moon shrinks.",
            scientificSignificance = "Shallow moonquakes can vibrate dry, fractured lunar rock for over an hour—a crucial engineering factor for future Artemis lunar habitats.",
            explorationHistory = "Over 12,000 seismic events logged by Apollo 12, 14, 15, and 16 seismometers.",
            relatedMissions = listOf("Apollo 12–16 Seismometers", "LRO Lobate Scarp Survey"),
            relatedFeatureIds = listOf("lunar_interior_geology", "lunar_south_pole"),
            bestViewingPhase = "Fine regolith brightness surge ('opposition effect') peaks dramatically at Full Moon.",
            imageUrl = "https://images-assets.nasa.gov/image/as11-40-5875/as11-40-5875~medium.jpg",
            fallbackVisualType = VisualFallbackType.APOLLO_SURFACE
        )
    )

    val learnArticles: List<LearnArticle> = listOf(
        LearnArticle(
            id = "what_is_the_moon",
            title = "What Is the Moon? Origin, Gravity & Surface",
            category = LearnCategory.MOON_BASICS,
            readTimeMinutes = 5,
            introduction = "Earth's only natural satellite is the fifth-largest moon in the Solar System—and uniquely huge relative to its host planet, shaping our tides, day length, and climate stability.",
            sections = listOf(
                ArticleSection(
                    "The Giant Impact Origin",
                    "Around 4.5 billion years ago, a Mars-sized protoplanet named Theia collided obliquely with the proto-Earth. The cataclysmic impact vaporized mantle rock into an incandescent ring of debris that coalesced in orbit to form the Moon—explaining why lunar rocks share nearly identical oxygen isotopes with Earth's mantle yet contain a much smaller iron core."
                ),
                ArticleSection(
                    "Lunar Gravity & Airless Vacuum",
                    "With a radius of 1,737.4 km (about 27% of Earth's) and 1.2% of Earth's mass, lunar surface gravity is roughly 1.62 m/s²—about one-sixth of Earth's gravity. Because this weak gravity cannot retain warm gases, the Moon possesses only an ultra-tenuous exosphere, allowing daytime highs near +120°C and nighttime lows down to -130°C (and below -240°C inside permanently shadowed polar craters)."
                ),
                ArticleSection(
                    "Craters vs. Lunar Maria",
                    "Early telescopic astronomers mistook the Moon's dark flat plains for oceans and named them 'maria' (Latin for seas). We now know lunar maria contain zero liquid water—they are ancient volcanic flood-basalt plains that filled giant impact basins 3 to 4 billion years ago. By contrast, the paler, heavily cratered lunar highlands are older anorthositic crust formed as the Moon's primordial magma ocean crystallized."
                )
            ),
            keyFacts = listOf(
                "Average Earth–Moon center-to-center distance: 384,400 km (238,855 mi).",
                "Surface gravity is 16.5% of Earth's—a 60 kg person weighs ~10 kg on the Moon.",
                "Because there is no wind or liquid water, astronaut footprints can endure millions of years."
            ),
            relatedArticleIds = listOf("tidal_locking", "moon_phases_explained", "apollo_and_artemis"),
            imageUrl = "https://images-assets.nasa.gov/image/PIA00405/PIA00405~medium.jpg",
            imageCredit = "NASA / Galileo Spacecraft",
            fallbackVisualType = VisualFallbackType.FULL_MOON
        ),
        LearnArticle(
            id = "moon_phases_explained",
            title = "Why Moon Phases Happen: Sunlight & Geometry",
            category = LearnCategory.MOON_PHASES,
            readTimeMinutes = 4,
            introduction = "Moon phases are not caused by Earth's shadow. Instead, they are a geometric dance between the Sun, Earth, and Moon—revealing different fractions of the Moon's sunlit half.",
            sections = listOf(
                ArticleSection(
                    "Reflected Sunlight, Not Self-Illumination",
                    "The Moon generates no visible light of its own. At every instant (except during a lunar eclipse), exactly half of the lunar sphere faces the Sun and glows brightly while the opposite hemisphere lies in lunar night. As the Moon orbits Earth every month, our viewing angle changes, showing us anywhere from 0% (New Moon) to 100% (Full Moon) of that sunlit hemisphere."
                ),
                ArticleSection(
                    "Waxing vs. Waning & Quarter Naming",
                    "During the first half of the lunar month—from New Moon to Full Moon—the visible sunlit fraction grows each evening: this is waxing. From Full Moon back to New Moon, the lit fraction shrinks: this is waning. First Quarter and Last Quarter moons look half-illuminated, yet are named 'quarters' because the Moon has completed one-quarter or three-quarters of its orbital cycle."
                ),
                ArticleSection(
                    "Why Eclipses Don't Happen Every Month",
                    "If the Moon orbited in the exact same plane as Earth's orbit around the Sun, we would witness a solar eclipse every New Moon and a lunar eclipse every Full Moon. Because the Moon's orbit tilts 5.14° relative to the ecliptic, its shadow usually passes cleanly above or below Earth."
                )
            ),
            keyFacts = listOf(
                "Lunar regolith reflects only ~12% of incoming sunlight—comparable to worn asphalt.",
                "Earth's shadow only touches the Moon during lunar eclipses, never during ordinary phases.",
                "In the Southern Hemisphere, the waxing Moon illuminates from left to right—the mirror of northern skies."
            ),
            relatedArticleIds = listOf("synodic_vs_sidereal", "what_is_the_moon"),
            imageUrl = "https://images-assets.nasa.gov/image/GSFC_20171208_Archive_e001982/GSFC_20171208_Archive_e001982~medium.jpg",
            imageCredit = "NASA Goddard Space Flight Center",
            fallbackVisualType = VisualFallbackType.CRESCENT_MOON
        ),
        LearnArticle(
            id = "synodic_vs_sidereal",
            title = "Moon Motion: Synodic Month, Sidereal Month & Libration",
            category = LearnCategory.MOON_MOTION,
            readTimeMinutes = 5,
            introduction = "Why does a full cycle of Moon phases take 29.53 days when the Moon orbits Earth in only 27.32 days? And how can we actually see 59% of the Moon's surface from Earth?",
            sections = listOf(
                ArticleSection(
                    "Sidereal Month (27.32 Days) vs. Synodic Month (29.53 Days)",
                    "The Moon completes one 360° orbit relative to the distant background stars in 27.3217 days—one sidereal month. However, during those 27.3 days, Earth has also traveled roughly 27° further around the Sun. The Moon must orbit an extra 2.21 days to catch up and re-align with the Sun, giving a synodic (phase-to-phase) month of 29.5306 days."
                ),
                ArticleSection(
                    "Elliptical Orbit: Perigee & Apogee",
                    "The Moon's path around Earth is an ellipse with an eccentricity of 0.0549. At perigee it sweeps as close as ~356,500 km; at apogee it recedes to ~406,700 km. This ~14% distance swing drives Supermoons and noticeable speed shifts along its orbit."
                ),
                ArticleSection(
                    "Lunar Libration — Peeking Around the Edge",
                    "Although the Moon spins at a constant rate while its orbital speed varies (faster at perigee, slower at apogee), and its axis is tilted ~6.7° to its orbit, Earth observers see a gentle rocking motion called libration. Over time, libration lets us peek around the lunar edges to observe up to 59% of the Moon's total surface!"
                )
            ),
            keyFacts = listOf(
                "Synodic month: 29 days, 12 hours, 44 minutes, 3 seconds.",
                "Sidereal month: 27 days, 7 hours, 43 minutes, 12 seconds.",
                "The Moon rises approximately 50 minutes later each calendar day."
            ),
            relatedArticleIds = listOf("tidal_locking", "earth_moon_tides"),
            imageUrl = "https://images-assets.nasa.gov/image/PIA13517/PIA13517~medium.jpg",
            imageCredit = "NASA / LRO",
            fallbackVisualType = VisualFallbackType.MINIMAL_ORBIT
        ),
        LearnArticle(
            id = "tidal_locking",
            title = "Why We Always See Nearly the Same Side",
            category = LearnCategory.MOON_BASICS,
            readTimeMinutes = 4,
            introduction = "The Moon does rotate on its axis—if it didn't, we would see its entire globe every month! Instead, synchronous rotation keeps one face permanently turned toward Earth.",
            sections = listOf(
                ArticleSection(
                    "Synchronous Rotation Explained",
                    "Early in Solar System history, the molten young Moon spun much faster. Earth's immense gravity stretched the Moon into a subtle tidal bulge. Gravitational torque pulling on that bulge steadily braked the Moon's spin until its rotation period matched its orbital period: 27.32 days."
                ),
                ArticleSection(
                    "The 'Far Side' Is Not the 'Dark Side'",
                    "Every part of the Moon experiences roughly 14 Earth days of bright sunshine followed by 14 days of night. The 'far side' receives just as much sunlight as the near side—in fact, at New Moon the far side is completely bathed in daylight! It looks dramatically different from the near side: thicker crust, far fewer dark maria, and dense highland craters."
                )
            ),
            keyFacts = listOf(
                "First photographs of the lunar far side were captured by Luna 3 in October 1959.",
                "China's Chang'e 4 (2019) achieved the first soft landing on the lunar far side inside Von Kármán Crater.",
                "Tidal friction is gradually slowing Earth's rotation too—lengthening our day by ~1.7 milliseconds per century."
            ),
            relatedArticleIds = listOf("earth_moon_tides", "synodic_vs_sidereal"),
            imageUrl = "https://images-assets.nasa.gov/image/PIA14208/PIA14208~medium.jpg",
            imageCredit = "NASA / LRO",
            fallbackVisualType = VisualFallbackType.LUNAR_SURFACE
        ),
        LearnArticle(
            id = "earth_moon_tides",
            title = "Earth & Moon: Gravity, Ocean Tides & Cosmic Drift",
            category = LearnCategory.EARTH_AND_MOON,
            readTimeMinutes = 5,
            introduction = "Every coastal high tide is a direct gravitational signature of the Moon pulling across 384,400 kilometers of space—an exchange of energy slowly pushing the Moon outward.",
            sections = listOf(
                ArticleSection(
                    "How Differential Gravity Creates Two Tidal Bulges",
                    "Gravitational force weakens with distance. Earth's near-side ocean feels a slightly stronger lunar pull than Earth's solid center, creating one bulge facing the Moon; Earth's center is pulled slightly harder than far-side water, leaving a second bulge on the opposite side. As Earth rotates through both bulges, most coasts experience two high tides and two low tides every 24 hours 50 minutes."
                ),
                ArticleSection(
                    "Spring Tides & Neap Tides",
                    "At New Moon and Full Moon, the Sun and Moon pull along the same line, combining their tidal forces to create extra-high 'spring tides' (nothing to do with the spring season!). At First and Last Quarter, solar and lunar pulls act at 90° angles, partially canceling each other into milder 'neap tides.'"
                ),
                ArticleSection(
                    "The 3.8 cm Annual Drift",
                    "Earth's rapid spin drags the ocean tidal bulge slightly ahead of the Moon's orbital line. That forward bulge tugs the Moon gently forward in its orbit, boosting it into a wider orbit by 3.8 centimeters every year—measured to millimeter accuracy using Apollo retroreflector mirrors."
                )
            ),
            keyFacts = listOf(
                "Lunar tidal pull is about 2.2× stronger than solar tidal pull despite the Sun's huge mass.",
                "The Moon also stabilizes Earth's 23.4° axial tilt, keeping Earth's seasons relatively gentle over millions of years."
            ),
            relatedArticleIds = listOf("tidal_locking", "what_is_the_moon"),
            imageUrl = "https://images-assets.nasa.gov/image/as08-14-2383/as08-14-2383~medium.jpg",
            imageCredit = "NASA / Apollo 8 Earthrise",
            fallbackVisualType = VisualFallbackType.MOON_OCEAN
        ),
        LearnArticle(
            id = "apollo_and_artemis",
            title = "Moon Exploration: From Apollo Rovers to Artemis",
            category = LearnCategory.MOON_EXPLORATION,
            readTimeMinutes = 6,
            introduction = "Between 1969 and 1972, twelve astronauts walked on the Moon and returned 382 kilograms of lunar samples. Today, a global wave of robotic landers and the Artemis program is returning humanity to stay.",
            sections = listOf(
                ArticleSection(
                    "The Apollo Legacy & Lunar Rovers",
                    "Starting with Apollo 11's touchdown at Tranquility Base, six crewed landings explored progressively more daring geology—from flat mare plains to the towering Apennine Mountains (Apollo 15) and Taurus-Littrow Valley (Apollo 17). On Apollo 15, 16, and 17, electric Lunar Roving Vehicles carried astronauts up to 35.9 km across craters and rilles."
                ),
                ArticleSection(
                    "Modern Robotic Breakthroughs",
                    "NASA's Lunar Reconnaissance Orbiter has mapped the entire Moon in extraordinary detail since 2009. India's Chandrayaan-3 achieved the first soft landing near the Lunar South Pole in 2023, detecting sulfur and mapping regolith heat conductivity, while China's Chang'e 6 returned the first-ever samples from the lunar far side in 2024."
                ),
                ArticleSection(
                    "Artemis & The Lunar South Pole",
                    "Future crewed missions target permanently shadowed craters around the Lunar South Pole—where water ice trapped for billions of years could provide drinking water, breathable oxygen, and rocket propellant for deep-space exploration."
                )
            ),
            keyFacts = listOf(
                "382 kg (842 lbs) of Apollo lunar samples are still being analyzed with modern instruments today.",
                "Apollo 17 astronaut Harrison Schmitt remains the first professional geologist to walk on another world."
            ),
            relatedArticleIds = listOf("what_is_the_moon", "tidal_locking"),
            imageUrl = "https://images-assets.nasa.gov/image/as11-40-5875/as11-40-5875~medium.jpg",
            imageCredit = "NASA / Apollo 11 Archive",
            fallbackVisualType = VisualFallbackType.APOLLO_SURFACE
        ),
        LearnArticle(
            id = "moon_mysteries_illusions",
            title = "Moon Mysteries: The Moon Illusion, Red Eclipses & No-Moon Earth",
            category = LearnCategory.MOON_MYSTERIES,
            readTimeMinutes = 5,
            introduction = "Why does a rising Full Moon look enormous behind trees, why does it turn copper-red during an eclipse, and what would happen to Earth if the Moon vanished?",
            sections = listOf(
                ArticleSection(
                    "The Moon Illusion — Trick of Human Perception",
                    "A Full Moon sitting just above the horizon looks dramatically larger than when overhead—yet photographs and angular measurements prove it is actually ~1.5% smaller on the horizon (because you are one Earth-radius farther away!). Your brain perceives the sky dome as a flattened ceiling, interpreting distant horizon objects as larger than identical angular sizes overhead."
                ),
                ArticleSection(
                    "Why Lunar Eclipses Glow Crimson",
                    "During totality, the Moon sits completely inside Earth's dark umbra—yet it rarely disappears. Earth's atmosphere bends long-wavelength red and orange sunlight around the planet's rim while scattering blue light away. A Blood Moon is literally illuminated by every sunrise and sunset occurring around Earth's horizon simultaneously."
                ),
                ArticleSection(
                    "What If Earth Had No Moon?",
                    "Without our massive Moon stabilizing Earth's 23.4° axial tilt, gravitational nudges from Jupiter and Venus would cause our spin axis to wobble chaotically over millions of years—triggering extreme ice ages and tropical poles. Ocean tides would shrink by two-thirds, and Earth's day would still be roughly 6–8 hours long!"
                )
            ),
            keyFacts = listOf(
                "Hold an aspirin tablet at arm's length—it covers the entire Full Moon whether on the horizon or overhead!",
                "Volcanic eruptions on Earth darken total lunar eclipses by injecting stratospheric aerosols that block refracted red light.",
                "Molecular water ice survives on the Moon both inside polar shadows and trapped inside sunlit impact glass beads."
            ),
            relatedArticleIds = listOf("earth_moon_tides", "moon_phases_explained"),
            imageUrl = "https://images-assets.nasa.gov/image/GSFC_20171208_Archive_e001982/GSFC_20171208_Archive_e001982~medium.jpg",
            imageCredit = "NASA Goddard Space Flight Center",
            fallbackVisualType = VisualFallbackType.BLOOD_MOON
        ),
        LearnArticle(
            id = "milky_way_guide",
            title = "Our Milky Way: Galactic Structure, Dark Skies & Photography",
            category = LearnCategory.MILKY_WAY,
            readTimeMinutes = 6,
            introduction = "Every individual star you see on a dark night belongs to our home barred spiral galaxy—a rotating disk of 100 to 400 billion stars spanning over 100,000 light-years.",
            sections = listOf(
                ArticleSection(
                    "Where We Sit Inside the Galaxy",
                    "Our Solar System resides on the inner edge of the Orion–Cygnus Spur, roughly 26,670 light-years from the bustling galactic center in Sagittarius. It takes our Sun roughly 230 million years—one 'galactic year'—to complete a single orbit around the Milky Way."
                ),
                ArticleSection(
                    "Best Season & Lunar Window to See It",
                    "The bright, dust-laced Galactic Core rises highest during Northern Hemisphere summer (and Southern Hemisphere winter), from April through October. Always plan Milky Way observing near New Moon—even a 30% crescent Moon washes out delicate dark dust lanes."
                ),
                ArticleSection(
                    "Essential Milky Way Photography Settings",
                    "Use a wide fast lens (14–24mm at f/1.8–f/2.8), tripod, manual focus on a bright star, ISO 1600–3200, and 15–20 second exposures (using the NPF / 400 rule to keep pinpoint stars without trailing)."
                )
            ),
            keyFacts = listOf(
                "Under Bortle Class 1–2 skies, the Milky Way core casts a faint shadow on white surfaces.",
                "Dark rifts slicing down the Milky Way are not empty space—they are dense cold molecular dust clouds blocking starlight behind them."
            ),
            relatedArticleIds = listOf("what_is_the_moon", "moon_mysteries_illusions"),
            imageUrl = "https://images-assets.nasa.gov/image/PIA25433/PIA25433~medium.jpg",
            imageCredit = "NASA / ESA / CSA Archive",
            fallbackVisualType = VisualFallbackType.GALAXY
        )
    )

    val photoGuides: List<PhotoGuide> = listOf(
        PhotoGuide(
            id = "phone_exposure_basics",
            title = "Mastering Smartphone Moon Exposure",
            track = PhotoGuideTrack.SMARTPHONE,
            difficulty = "Beginner",
            summary = "Stop capturing blown-out glowing white blobs—learn how to lock exposure and capture real lunar maria on any modern Android phone.",
            settings = listOf(
                PhotoSettingItem("Exposure (EV)", "-2.0 to -3.5 EV", "The Moon is sunlit daylight rock against pitch black sky; auto-exposure overexposes it severely."),
                PhotoSettingItem("ISO", "50 – 100", "Keeps sensor noise minimal while preserving crisp edge contrast."),
                PhotoSettingItem("Shutter Speed", "1/125s – 1/500s", "Freezes both handheld shake and the Moon's orbital drift across the frame."),
                PhotoSettingItem("Focus", "Manual ∞ (Infinity)", "Prevents autofocus hunting in the dark.")
            ),
            steps = listOf(
                "Switch to your phone's Pro / Manual camera mode (or tap directly on the Moon and drag the sun/brightness slider all the way down).",
                "Select your phone's native optical telephoto lens (3× or 5× optical)—avoid exceeding 10× digital zoom.",
                "Set ISO to 50–100 and start shutter speed around 1/250s for a Full Moon (or 1/60s for a thin Crescent).",
                "Brace your elbows against a wall, car roof, or use a compact phone tripod clamp and a 3-second self-timer to eliminate tap vibration."
            ),
            commonMistakes = listOf(
                "Using Night Mode on a bright Moon—Night Mode stacks multi-second exposures and turns the Moon into a featureless white disc.",
                "Pinching all the way to 50×–100× digital zoom, which produces soft smeared artifacts instead of crisp craters.",
                "Shooting through indoor window glass, causing double ghost reflections."
            ),
            expectedResult = "A clean, properly exposed silver disc displaying dark volcanic maria (Mare Tranquillitatis, Imbrium, Serenitatis) with sharp limb edges.",
            imageUrl = "https://images-assets.nasa.gov/image/PIA00405/PIA00405~medium.jpg",
            imageCredit = "NASA Lunar Photography Reference",
            fallbackVisualType = VisualFallbackType.FULL_MOON
        ),
        PhotoGuide(
            id = "phone_foreground_composition",
            title = "Moonrise Composition & Foreground Drama",
            track = PhotoGuideTrack.SMARTPHONE,
            difficulty = "Intermediate",
            summary = "Use blue-hour timing, silhouettes, and reflection planes to create cinematic smartphone Moon photographs that tell a story.",
            settings = listOf(
                PhotoSettingItem("Timing Window", "15–30 min after sunset", "Sky brightness matches lunar luminance so both Moon and landscape expose cleanly in one frame."),
                PhotoSettingItem("Lens", "2×–5× Optical Telephoto", "Compresses perspective so the Moon appears imposing beside trees, lighthouses, or ridges."),
                PhotoSettingItem("Format", "RAW (DNG) if supported", "Preserves extra highlight latitude to recover subtle lunar mare texture."),
                PhotoSettingItem("Timer", "3s / 5s Shutter Delay", "Guarantees razor-sharp silhouettes without finger shake.")
            ),
            steps = listOf(
                "Check LUNAR's Tonight tab for exact local moonrise time and compass direction.",
                "Arrive 20 minutes early and position a distinct foreground silhouette (ridge, tower, ocean shoreline) along the predicted moonrise azimuth.",
                "Capture right as the Moon clears the horizon during twilight—before the sky turns pitch black and creates impossible contrast.",
                "Tap to meter on the Moon's edge, slightly nudging exposure until both sky color and lunar texture read cleanly."
            ),
            commonMistakes = listOf(
                "Waiting until full midnight darkness, when exposing for the landscape completely blows out the Moon.",
                "Using an ultra-wide 0.5× lens for a lone Moon—making it look like a tiny pixel dot."
            ),
            expectedResult = "Balanced editorial twilight astrophotography showing natural lunar detail framed above glowing cobalt horizons or shimmering water.",
            imageUrl = "https://images-assets.nasa.gov/image/as08-14-2383/as08-14-2383~medium.jpg",
            imageCredit = "NASA Orbital Archive",
            fallbackVisualType = VisualFallbackType.MOON_OCEAN
        ),
        PhotoGuide(
            id = "camera_crater_detail",
            title = "High-Detail Lunar Crater Photography",
            track = PhotoGuideTrack.CAMERA,
            difficulty = "Intermediate",
            summary = "Harness grazing terminator shadows, telephoto optics, and crisp manual focus to resolve terraced craters and mountain ranges.",
            settings = listOf(
                PhotoSettingItem("Focal Length", "300mm – 600mm+", "Fills enough of the sensor to resolve Copernicus, Tycho, and the Lunar Apennines."),
                PhotoSettingItem("Aperture", "f/8 – f/11", "Places most telephoto lenses squarely in their sharpest optical sweet spot."),
                PhotoSettingItem("Shutter Speed", "1/125s – 1/250s", "Freezes Earth's atmospheric turbulence ('seeing') and lunar diurnal motion."),
                PhotoSettingItem("ISO", "100 – 200", "Maximizes dynamic range along the dark terminator boundary.")
            ),
            steps = listOf(
                "Target First Quarter, Waxing Gibbous, or Last Quarter—crater shadows along the terminator deliver 10× more texture than a flat Full Moon.",
                "Mount securely on a sturdy tripod, turn OFF optical image stabilization when locked down, and enable electronic front-curtain shutter.",
                "Switch to Live View at 10× magnification and gently rock manual focus until the crater rims along the terminator snap razor sharp.",
                "Shoot a burst of 15–30 RAW frames so you can select the sharpest frame unaffected by shimmering atmospheric air currents."
            ),
            commonMistakes = listOf(
                "Trusting the infinity hard-stop on a telephoto lens—most lenses focus slightly past infinity due to temperature expansion.",
                "Shooting the Moon low (<20° altitude) through thick turbulent horizon air instead of waiting until it climbs high."
            ),
            expectedResult = "Crisp, high-contrast crater walls, central rebound peaks, and delicate shadow relief rivaling small backyard telescopes.",
            imageUrl = "https://images-assets.nasa.gov/image/PIA14208/PIA14208~medium.jpg",
            imageCredit = "NASA / LRO",
            fallbackVisualType = VisualFallbackType.LUNAR_SURFACE
        ),
        PhotoGuide(
            id = "camera_blood_moon_eclipse",
            title = "Photographing Total Lunar Eclipses (Blood Moon)",
            track = PhotoGuideTrack.CAMERA,
            difficulty = "Advanced",
            summary = "Adapt dynamically as the Moon dims by 10,000× during totality—capturing rich copper tones and surrounding starfields without motion blur.",
            settings = listOf(
                PhotoSettingItem("Partial Phase", "f/8 • ISO 100 • 1/250s", "Protects bright sunlit regolith while Earth's umbra slowly crosses the disc."),
                PhotoSettingItem("Totality Phase", "f/4–f/5.6 • ISO 800–1600 • 0.8–2.0s", "Gathers faint refracted red light without smearing lunar detail."),
                PhotoSettingItem("Max Untracked Shutter", "≤ 2 seconds at 400mm", "Longer exposures on a static tripod will blur the Moon due to Earth's rotation."),
                PhotoSettingItem("White Balance", "Daylight (5200K)", "Preserves authentic copper-crimson atmospheric color rather than neutralizing it.")
            ),
            steps = listOf(
                "Lock manual focus while the Moon is still bright during the partial phase, then tape the focus ring lightly so it cannot shift.",
                "As totality approaches, widen your aperture and raise ISO in stages rather than pushing shutter speed past 2 seconds (unless using a star tracker).",
                "Capture exposure brackets (±1 EV) at maximum eclipse to preserve both deep crimson shadows and any bright silver-turquoise limb.",
                "If shooting a composite sequence, keep your focal length identical from start to finish and shoot every 10–15 minutes."
            ),
            commonMistakes = listOf(
                "Leaving Auto White Balance on, which turns warm copper totality dull grey.",
                "Using 5–8 second untracked telephoto exposures that smear craters into streaks."
            ),
            expectedResult = "A velvety deep copper-amber eclipsed Moon suspended cleanly among pinpoint stars.",
            imageUrl = "https://images-assets.nasa.gov/image/GSFC_20171208_Archive_e001982/GSFC_20171208_Archive_e001982~medium.jpg",
            imageCredit = "NASA Goddard Archive",
            fallbackVisualType = VisualFallbackType.BLOOD_MOON
        )
    )

    val wallpapers: List<WallpaperItem> = listOf(
        WallpaperItem(
            id = "wp_full_moon_classic",
            title = "Full Lunar Disc — High Albedo",
            subtitle = "Near-side lunar geography in stark deep space",
            category = WallpaperCategory.MOON,
            collection = "Full Moon Collection",
            resolution = "2160 × 3840 • 4K",
            credit = "NASA / Galileo & LRO",
            imageUrl = "https://images-assets.nasa.gov/image/PIA00405/PIA00405~medium.jpg",
            fallbackVisualType = VisualFallbackType.FULL_MOON,
            phaseFractionOverride = 0.50,
            viewingAngle = "Telescope View",
            lightingMood = "Full Illumination"
        ),
        WallpaperItem(
            id = "wp_new_moon_obsidian",
            title = "New Moon — Silent Conjunction",
            subtitle = "Ghostly earthshine rim emerging from pure OLED darkness",
            category = WallpaperCategory.MINIMAL,
            collection = "New Moon & Minimal",
            resolution = "2160 × 3840 • 4K",
            credit = "LUNAR Ephemeris Studio",
            imageUrl = "",
            fallbackVisualType = VisualFallbackType.FULL_MOON,
            phaseFractionOverride = 0.03,
            viewingAngle = "Deep Space Frame",
            lightingMood = "Deep Night"
        ),
        WallpaperItem(
            id = "wp_waxing_crescent_minimal",
            title = "Silver Waxing Arc",
            subtitle = "Razor-sharp 18% sunlit limb over pure OLED black",
            category = WallpaperCategory.MINIMAL,
            collection = "Crescent Moon",
            resolution = "2160 × 3840 • 4K",
            credit = "LUNAR Ephemeris Studio",
            imageUrl = "",
            fallbackVisualType = VisualFallbackType.CRESCENT_MOON,
            phaseFractionOverride = 0.14,
            viewingAngle = "Telescope View",
            lightingMood = "Night Twilight"
        ),
        WallpaperItem(
            id = "wp_blood_moon_totality",
            title = "Copper Umbra Totality",
            subtitle = "Total lunar eclipse glowing crimson inside Earth's shadow",
            category = WallpaperCategory.BLOOD_MOON,
            collection = "Blood Moon & Eclipse",
            resolution = "2160 × 3840 • 4K",
            credit = "NASA Goddard Space Flight Center",
            imageUrl = "https://images-assets.nasa.gov/image/GSFC_20171208_Archive_e001982/GSFC_20171208_Archive_e001982~medium.jpg",
            fallbackVisualType = VisualFallbackType.BLOOD_MOON,
            phaseFractionOverride = 0.50,
            viewingAngle = "Telephoto Eclipse",
            lightingMood = "Eclipse Crimson"
        ),
        WallpaperItem(
            id = "wp_blue_moon_cobalt",
            title = "Cobalt Blue Moon Radiance",
            subtitle = "Cool sapphire-tinted rare seasonal lunar illumination",
            category = WallpaperCategory.SUPERMOON,
            collection = "Supermoon & Blue Moon",
            resolution = "2160 × 3840 • 4K",
            credit = "LUNAR Observatory Edition",
            imageUrl = "",
            fallbackVisualType = VisualFallbackType.BLUE_MOON,
            phaseFractionOverride = 0.50,
            viewingAngle = "Close-Up Orbit",
            lightingMood = "Blue Hour"
        ),
        WallpaperItem(
            id = "wp_golden_harvest_moon",
            title = "Golden Harvest Horizon",
            subtitle = "Warm amber atmospheric refraction at low perigee moonrise",
            category = WallpaperCategory.SUPERMOON,
            collection = "Golden Hour & Moonrise",
            resolution = "2160 × 3840 • 4K",
            credit = "LUNAR Observatory Edition",
            imageUrl = "",
            fallbackVisualType = VisualFallbackType.GOLDEN_MOON,
            phaseFractionOverride = 0.48,
            viewingAngle = "Horizon Telephoto",
            lightingMood = "Golden Hour"
        ),
        WallpaperItem(
            id = "wp_tycho_crater_orbit",
            title = "Tycho Central Peak in Low Sun",
            subtitle = "Grazing shadows sweeping across 85 km terraced impact walls",
            category = WallpaperCategory.LUNAR_SURFACE,
            collection = "Crater Close-Ups",
            resolution = "2400 × 3600 • Ultra HD",
            credit = "NASA / LRO",
            imageUrl = "https://images-assets.nasa.gov/image/PIA14208/PIA14208~medium.jpg",
            fallbackVisualType = VisualFallbackType.LUNAR_SURFACE,
            phaseFractionOverride = 0.30,
            viewingAngle = "Satellite Orbital View",
            lightingMood = "Grazing Terminator"
        ),
        WallpaperItem(
            id = "wp_copernicus_monarch",
            title = "Copernicus Terraced Rim",
            subtitle = "High-contrast orbital topography of the Moon's monarch crater",
            category = WallpaperCategory.LUNAR_SURFACE,
            collection = "Lunar Surface",
            resolution = "2400 × 3600 • Ultra HD",
            credit = "NASA / LRO",
            imageUrl = "https://images-assets.nasa.gov/image/PIA13517/PIA13517~medium.jpg",
            fallbackVisualType = VisualFallbackType.LUNAR_SURFACE,
            phaseFractionOverride = 0.34,
            viewingAngle = "Satellite Orbital View",
            lightingMood = "High Contrast"
        ),
        WallpaperItem(
            id = "wp_aristarchus_plateau",
            title = "Aristarchus Brilliant Highlands",
            subtitle = "Brightest crater on the Moon bordering ancient lava rilles",
            category = WallpaperCategory.LUNAR_SURFACE,
            collection = "Crater Close-Ups",
            resolution = "2400 × 3600 • Ultra HD",
            credit = "NASA / LRO",
            imageUrl = "https://images-assets.nasa.gov/image/PIA14932/PIA14932~medium.jpg",
            fallbackVisualType = VisualFallbackType.LUNAR_SURFACE,
            phaseFractionOverride = 0.38,
            viewingAngle = "Orbital Close-Up",
            lightingMood = "Lunar Morning"
        ),
        WallpaperItem(
            id = "wp_earthrise_apollo8",
            title = "Apollo 8 — Earthrise",
            subtitle = "Earth ascending above the stark monochrome lunar horizon",
            category = WallpaperCategory.EARTHRISE,
            collection = "Earthrise & Apollo",
            resolution = "2400 × 3200 • Archive HD",
            credit = "NASA / Bill Anders (1968)",
            imageUrl = "https://images-assets.nasa.gov/image/as08-14-2383/as08-14-2383~medium.jpg",
            fallbackVisualType = VisualFallbackType.APOLLO_SURFACE,
            phaseFractionOverride = 0.50,
            viewingAngle = "Lunar Orbit Horizon",
            lightingMood = "Space Sunlight"
        ),
        WallpaperItem(
            id = "wp_apollo11_tranquility",
            title = "Tranquility Base — Buzz Aldrin",
            subtitle = "Stark lunar shadows across Mare Tranquillitatis basalt",
            category = WallpaperCategory.SPACE_PHOTOGRAPHY,
            collection = "Apollo Heritage",
            resolution = "2400 × 3200 • Archive HD",
            credit = "NASA / Neil Armstrong (1969)",
            imageUrl = "https://images-assets.nasa.gov/image/as11-40-5903/as11-40-5903~medium.jpg",
            fallbackVisualType = VisualFallbackType.APOLLO_SURFACE,
            phaseFractionOverride = 0.50,
            viewingAngle = "Surface Eye-Level",
            lightingMood = "Stark Vacuum Sun"
        ),
        WallpaperItem(
            id = "wp_apollo15_apennines",
            title = "Hadley-Apennine — Rover Horizon",
            subtitle = "Towering 4 km lunar peaks behind the Apollo 15 rover",
            category = WallpaperCategory.SPACE_PHOTOGRAPHY,
            collection = "Apollo Heritage",
            resolution = "2400 × 3200 • Archive HD",
            credit = "NASA / Apollo 15 (1971)",
            imageUrl = "https://images-assets.nasa.gov/image/as15-88-11866/as15-88-11866~medium.jpg",
            fallbackVisualType = VisualFallbackType.APOLLO_SURFACE,
            phaseFractionOverride = 0.50,
            viewingAngle = "Surface Panorama",
            lightingMood = "Lunar Morning"
        ),
        WallpaperItem(
            id = "wp_moon_ocean_midnight",
            title = "Silver Tide — Midnight Ocean",
            subtitle = "Full Moon shimmer reflecting across obsidian ocean swells",
            category = WallpaperCategory.MOON_OCEAN,
            collection = "Moon & Ocean",
            resolution = "2160 × 3840 • 4K",
            credit = "LUNAR Astrophotography",
            imageUrl = "",
            fallbackVisualType = VisualFallbackType.MOON_OCEAN,
            phaseFractionOverride = 0.50,
            viewingAngle = "Wide Horizon",
            lightingMood = "Deep Night"
        ),
        WallpaperItem(
            id = "wp_moon_alpine_ridge",
            title = "Moonrise Over Alpine Peaks",
            subtitle = "Luminous waxing gibbous climbing above dark mountain ridges",
            category = WallpaperCategory.MOON_MOUNTAINS,
            collection = "Moon & Mountains",
            resolution = "2160 × 3840 • 4K",
            credit = "LUNAR Astrophotography",
            imageUrl = "",
            fallbackVisualType = VisualFallbackType.MOON_MOUNTAINS,
            phaseFractionOverride = 0.38,
            viewingAngle = "Landscape Silhouettes",
            lightingMood = "Blue Hour"
        ),
        WallpaperItem(
            id = "wp_saturn_cassini_rings",
            title = "Saturn — Cassini Grand Rings",
            subtitle = "Crystalline water-ice rings backlit against deep space",
            category = WallpaperCategory.PLANETS,
            collection = "Planets & Saturn",
            resolution = "2560 × 3840 • 4K",
            credit = "NASA / JPL-Caltech / Cassini",
            imageUrl = "https://images-assets.nasa.gov/image/PIA11141/PIA11141~medium.jpg",
            fallbackVisualType = VisualFallbackType.DEEP_SPACE,
            phaseFractionOverride = 0.50,
            viewingAngle = "Orbital Flyby",
            lightingMood = "Outer System Gold"
        ),
        WallpaperItem(
            id = "wp_jupiter_juno_storms",
            title = "Jupiter — Turbulent Cloud Belts",
            subtitle = "Swirling cobalt polar cyclones and ochre ammonia bands",
            category = WallpaperCategory.PLANETS,
            collection = "Planets & Jupiter",
            resolution = "2560 × 3840 • 4K",
            credit = "NASA / JPL-Caltech / Juno",
            imageUrl = "https://images-assets.nasa.gov/image/PIA22946/PIA22946~medium.jpg",
            fallbackVisualType = VisualFallbackType.DEEP_SPACE,
            phaseFractionOverride = 0.50,
            viewingAngle = "Close Orbital Pass",
            lightingMood = "Planetary Contrast"
        ),
        WallpaperItem(
            id = "wp_mars_crimson_globe",
            title = "Mars — Valles Marineris Sphere",
            subtitle = "The giant canyon scar across the sunlit Red Planet",
            category = WallpaperCategory.PLANETS,
            collection = "Planets & Mars",
            resolution = "2160 × 3840 • 4K",
            credit = "NASA / JPL Viking Mosaic",
            imageUrl = "https://images-assets.nasa.gov/image/PIA00407/PIA00407~medium.jpg",
            fallbackVisualType = VisualFallbackType.BLOOD_MOON,
            phaseFractionOverride = 0.50,
            viewingAngle = "Full Disc View",
            lightingMood = "Solar Amber"
        ),
        WallpaperItem(
            id = "wp_neptune_azure",
            title = "Neptune — Azure Frontier",
            subtitle = "Supersonic methane clouds on the edge of the Kuiper Belt",
            category = WallpaperCategory.PLANETS,
            collection = "Planets & Ice Giants",
            resolution = "2160 × 3840 • 4K",
            credit = "NASA / JPL Voyager 2",
            imageUrl = "https://images-assets.nasa.gov/image/PIA01492/PIA01492~medium.jpg",
            fallbackVisualType = VisualFallbackType.BLUE_MOON,
            phaseFractionOverride = 0.50,
            viewingAngle = "Spacecraft Flyby",
            lightingMood = "Deep Cobalt"
        ),
        WallpaperItem(
            id = "wp_andromeda_deep_space",
            title = "Andromeda & Galactic Dust",
            subtitle = "Quiet deep-space spiral structure bathed in subtle indigo",
            category = WallpaperCategory.GALAXIES,
            collection = "Galaxies & Milky Way",
            resolution = "2160 × 3840 • 4K",
            credit = "NASA / JPL-Caltech / WISE",
            imageUrl = "https://images-assets.nasa.gov/image/PIA15415/PIA15415~medium.jpg",
            fallbackVisualType = VisualFallbackType.GALAXY,
            phaseFractionOverride = 0.50,
            viewingAngle = "Deep Field Infrared",
            lightingMood = "Starlight Silver"
        ),
        WallpaperItem(
            id = "wp_deep_cosmos_nebula",
            title = "Carina Nebula — Cosmic Cliffs",
            subtitle = "Star-forming dust ridges illuminated in deep infrared",
            category = WallpaperCategory.NEBULAE,
            collection = "Nebulae & JWST",
            resolution = "2560 × 3840 • 4K",
            credit = "NASA / ESA / CSA / JWST",
            imageUrl = "https://images-assets.nasa.gov/image/PIA25433/PIA25433~medium.jpg",
            fallbackVisualType = VisualFallbackType.DEEP_SPACE,
            phaseFractionOverride = 0.50,
            viewingAngle = "Deep Space Telescope",
            lightingMood = "Infrared Amber & Blue"
        ),
        WallpaperItem(
            id = "wp_earth_blue_marble",
            title = "Blue Marble — Apollo 17",
            subtitle = "Earth suspended whole in the silent velvet of cislunar space",
            category = WallpaperCategory.DEEP_SPACE,
            collection = "Space Photography",
            resolution = "2400 × 3200 • Archive HD",
            credit = "NASA / Apollo 17 Crew",
            imageUrl = "https://images-assets.nasa.gov/image/as17-148-22727/as17-148-22727~medium.jpg",
            fallbackVisualType = VisualFallbackType.BLUE_MOON,
            phaseFractionOverride = 0.50,
            viewingAngle = "Deep Space Disc",
            lightingMood = "Full Daylight"
        ),
        WallpaperItem(
            id = "wp_night_sky_observatory",
            title = "Astronomical Darkness — Zenith",
            subtitle = "Pinpoint constellations above a quiet midnight observatory horizon",
            category = WallpaperCategory.NIGHT_SKY,
            collection = "Night Sky & Dawn",
            resolution = "2160 × 3840 • 4K",
            credit = "LUNAR Night Sky Collection",
            imageUrl = "",
            fallbackVisualType = VisualFallbackType.NIGHT_SKY,
            phaseFractionOverride = 0.25,
            viewingAngle = "Wide Zenith View",
            lightingMood = "Astronomical Night"
        )
    )

    val astronomyFacts: List<Pair<String, String>> = listOf(
        "The Moon Is Drifting Away" to "Laser beams bounced off Apollo retroreflectors show the Moon moves 3.8 centimeters farther from Earth every year—roughly the speed human fingernails grow.",
        "Moonlight Takes 1.28 Seconds" to "Sunlight reflected off the lunar surface travels across 384,400 kilometers of vacuum, reaching your eyes about 1.28 seconds after leaving the Moon.",
        "As Dark As Worn Asphalt" to "Despite glowing brilliantly against the night sky, lunar regolith reflects only ~12% of incoming sunlight—almost identical to charcoal-grey asphalt.",
        "Water Ice Inside Polar Shadows" to "Deep craters near the Lunar South Pole never receive direct sunlight, trapping ancient water ice at temperatures below -240°C.",
        "Moonquakes Last Up to an Hour" to "Without water to dampen seismic vibrations, shallow lunar tectonic quakes recorded by Apollo seismometers rang like a bell for over 30–60 minutes.",
        "No 'Dark Side' of the Moon" to "The far side of the Moon receives the exact same amount of sunlight as the near side—it is simply turned away from Earth due to tidal locking."
    )

    data class FeaturedDiscovery(
        val title: String,
        val tag: String,
        val summary: String,
        val articleId: String,
        val imageUrl: String,
        val fallbackVisualType: VisualFallbackType
    )

    val featuredDiscoveries: List<FeaturedDiscovery> = listOf(
        FeaturedDiscovery(
            title = "Water Trapped in Sunlit Lunar Glass",
            tag = "PLANETARY SCIENCE",
            summary = "NASA's SOFIA infrared observatory detected molecular H₂O inside Clavius Crater—proving water isn't restricted only to permanently shadowed poles.",
            articleId = "what_is_the_moon",
            imageUrl = "https://images-assets.nasa.gov/image/PIA14208/PIA14208~medium.jpg",
            fallbackVisualType = VisualFallbackType.LUNAR_SURFACE
        ),
        FeaturedDiscovery(
            title = "A Confirmed Lava Tube Cave in Mare Tranquillitatis",
            tag = "LUNAR EXPLORATION",
            summary = "Radar reanalysis of LRO data revealed an accessible subterranean conduit extending from the Mare Tranquillitatis pit—a potential natural shelter for future astronauts.",
            articleId = "apollo_and_artemis",
            imageUrl = "https://images-assets.nasa.gov/image/as11-40-5875/as11-40-5875~medium.jpg",
            fallbackVisualType = VisualFallbackType.APOLLO_SURFACE
        )
    )

    val presetCities: List<UserLocation> = listOf(
        UserLocation("Greenwich", "United Kingdom", 51.4769, -0.0005),
        UserLocation("New York", "United States", 40.7128, -74.0060),
        UserLocation("San Francisco", "United States", 37.7749, -122.4194),
        UserLocation("Tokyo", "Japan", 35.6762, 139.6503),
        UserLocation("Dubai", "UAE", 25.2048, 55.2708),
        UserLocation("Sydney", "Australia", -33.8688, 151.2093),
        UserLocation("Paris", "France", 48.8566, 2.3522),
        UserLocation("Reykjavik", "Iceland", 64.1466, -21.9426),
        UserLocation("Atacama (San Pedro)", "Chile", -22.9087, -68.1997),
        UserLocation("Cairo", "Egypt", 30.0444, 31.2357),
        UserLocation("Mumbai", "India", 19.0760, 72.8777),
        UserLocation("Toronto", "Canada", 43.6532, -79.3832)
    )
}
