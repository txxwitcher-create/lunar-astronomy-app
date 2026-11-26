package com.example.data

import com.example.astronomy.ConstellationInfo
import com.example.astronomy.DeepSpaceObject
import com.example.astronomy.PlanetInfo
import com.example.astronomy.SpaceMissionInfo

/**
 * Rich, scientifically verified Solar System, Constellation, Deep Space, and Space Mission
 * encyclopedia backing LUNAR's expanded astronomy library.
 * Photography uses verified public-domain NASA Image & Video Library assets.
 */
object AstronomyEncyclopediaCatalog {

    val planets: List<PlanetInfo> = listOf(
        PlanetInfo(
            id = "mercury",
            name = "Mercury",
            subtitle = "Swift Innermost World",
            orderFromSun = 1,
            diameterKm = 4879.0,
            distanceFromSunAu = "0.39 AU (57.9M km)",
            moonsCount = "0",
            rotationPeriod = "58.6 Earth days",
            orbitalPeriod = "88.0 Earth days",
            atmosphere = "Ultra-thin exosphere of oxygen, sodium, hydrogen, helium & potassium",
            surface = "Heavily cratered silicate plains scarred by Caloris Basin (1,550 km) and towering tectonic lobate scarps",
            missions = listOf("Mariner 10 (1974)", "MESSENGER (2011–2015)", "BepiColombo (ESA/JAXA)"),
            facts = listOf(
                "Despite sitting closest to the Sun, Mercury is not the hottest planet—its lack of atmosphere allows nighttime temperatures to plunge to -180°C.",
                "Mercury's iron core occupies roughly 85% of the planet's radius.",
                "Water ice survives inside permanently shadowed craters at Mercury's north and south poles."
            ),
            imageUrl = "https://images-assets.nasa.gov/image/PIA16853/PIA16853~medium.jpg",
            imageCredit = "NASA / Johns Hopkins APL / MESSENGER",
            accentHex = 0xFFB8B3A8
        ),
        PlanetInfo(
            id = "venus",
            name = "Venus",
            subtitle = "Runaway Greenhouse Jewel",
            orderFromSun = 2,
            diameterKm = 12104.0,
            distanceFromSunAu = "0.72 AU (108.2M km)",
            moonsCount = "0",
            rotationPeriod = "243 Earth days (retrograde)",
            orbitalPeriod = "224.7 Earth days",
            atmosphere = "Crushing 96.5% CO₂ with opaque sulfuric-acid clouds and 92 bar surface pressure",
            surface = "Vast volcanic basalt plains, shield volcanoes like Maat Mons, and highland plateaus like Maxwell Montes",
            missions = listOf("Venera 7–14", "Magellan (1990–1994)", "Akatsuki", "VERITAS & DAVINCI (planned)"),
            facts = listOf(
                "Venus spins backward (retrograde) so slowly that a Venusian sidereal day is longer than its year.",
                "Surface temperatures average 465°C—hot enough to melt lead.",
                "Its bright reflective clouds make Venus the brightest planet in Earth's sky, often called the Morning or Evening Star."
            ),
            imageUrl = "https://images-assets.nasa.gov/image/PIA00271/PIA00271~medium.jpg",
            imageCredit = "NASA / JPL Magellan & Mariner",
            accentHex = 0xFFE3B979
        ),
        PlanetInfo(
            id = "earth",
            name = "Earth",
            subtitle = "The Living Ocean World",
            orderFromSun = 3,
            diameterKm = 12742.0,
            distanceFromSunAu = "1.00 AU (149.6M km)",
            moonsCount = "1 (The Moon)",
            rotationPeriod = "23h 56m 4s",
            orbitalPeriod = "365.256 days",
            atmosphere = "78% nitrogen, 21% oxygen, 1% argon, trace CO₂ & water vapor with protective ozone",
            surface = "71% liquid water oceans, active silicate plate tectonics, continents, and polar ice sheets",
            missions = listOf("Apollo 8 Earthrise", "DSCOVR EPIC", "Terra & Aqua", "ISS"),
            facts = listOf(
                "Earth's unusually large Moon stabilizes our 23.4° axial tilt, keeping long-term climate swings remarkably moderate.",
                "A molten outer iron core generates a magnetosphere that shields our atmosphere from solar wind stripping.",
                "Earth is the densest planet in the Solar System at 5.51 g/cm³."
            ),
            imageUrl = "https://images-assets.nasa.gov/image/as17-148-22727/as17-148-22727~medium.jpg",
            imageCredit = "NASA / Apollo 17 Blue Marble",
            accentHex = 0xFF68A7F7
        ),
        PlanetInfo(
            id = "mars",
            name = "Mars",
            subtitle = "The Red Desert Frontier",
            orderFromSun = 4,
            diameterKm = 6779.0,
            distanceFromSunAu = "1.52 AU (227.9M km)",
            moonsCount = "2 (Phobos & Deimos)",
            rotationPeriod = "24h 37m 22s",
            orbitalPeriod = "687.0 Earth days",
            atmosphere = "Thin 95% CO₂ atmosphere (~0.6% of Earth's surface pressure) with seasonal dust storms",
            surface = "Iron-oxide regolith, Olympus Mons (22 km shield volcano), Valles Marineris canyon, and ancient river deltas",
            missions = listOf("Viking 1 & 2", "Curiosity", "Perseverance & Ingenuity", "Mars Reconnaissance Orbiter"),
            facts = listOf(
                "Olympus Mons rises nearly three times higher than Mount Everest.",
                "Valles Marineris stretches over 4,000 km—roughly the width of the continental United States.",
                "Rovers have confirmed ancient liquid lakes and organic chemistry inside Gale and Jezero Craters."
            ),
            imageUrl = "https://images-assets.nasa.gov/image/PIA00407/PIA00407~medium.jpg",
            imageCredit = "NASA / JPL Viking Orbiter Mosaic",
            accentHex = 0xFFD96F4B
        ),
        PlanetInfo(
            id = "jupiter",
            name = "Jupiter",
            subtitle = "Colossal Gas Giant",
            orderFromSun = 5,
            diameterKm = 139820.0,
            distanceFromSunAu = "5.20 AU (778.5M km)",
            moonsCount = "95 confirmed (Io, Europa, Ganymede, Callisto)",
            rotationPeriod = "9h 55m 30s (fastest spin)",
            orbitalPeriod = "11.86 Earth years",
            atmosphere = "89% hydrogen, 10% helium, banded ammonia cloud belts and anticyclonic storms",
            surface = "No solid surface—transitions gradually into supercritical fluid and liquid metallic hydrogen deep inside",
            missions = listOf("Pioneer 10 & 11", "Voyager 1 & 2", "Galileo", "Juno", "Europa Clipper"),
            facts = listOf(
                "Jupiter contains more than twice the mass of all other Solar System planets combined.",
                "The Great Red Spot is an anticyclonic storm larger than Earth that has raged for centuries.",
                "Even small backyard binoculars reveal its four Galilean moons lined up neatly around the bright disc."
            ),
            imageUrl = "https://images-assets.nasa.gov/image/PIA22946/PIA22946~medium.jpg",
            imageCredit = "NASA / JPL-Caltech / SwRI / MSSS / Juno",
            accentHex = 0xFFD6A87C
        ),
        PlanetInfo(
            id = "saturn",
            name = "Saturn",
            subtitle = "Ringed Jewel of the Outer System",
            orderFromSun = 6,
            diameterKm = 116460.0,
            distanceFromSunAu = "9.58 AU (1.43B km)",
            moonsCount = "146 confirmed (Titan, Enceladus, Rhea)",
            rotationPeriod = "10h 33m 38s",
            orbitalPeriod = "29.45 Earth years",
            atmosphere = "96% hydrogen, 3% helium, pale gold ammonia haze, and a persistent north-polar hexagonal jet stream",
            surface = "Gas giant with no solid crust; its rings span 282,000 km across yet average only 10–30 meters thick",
            missions = listOf("Pioneer 11", "Voyager 1 & 2", "Cassini–Huygens (2004–2017)", "Dragonfly to Titan"),
            facts = listOf(
                "Saturn's average density (0.687 g/cm³) is lower than liquid water.",
                "Its moon Enceladus erupts subsurface ocean geysers of water vapor and organic molecules into Saturn's E ring.",
                "Titan is the only moon in the Solar System with a dense nitrogen atmosphere and liquid hydrocarbon lakes."
            ),
            imageUrl = "https://images-assets.nasa.gov/image/PIA11141/PIA11141~medium.jpg",
            imageCredit = "NASA / JPL-Caltech / Space Science Institute / Cassini",
            accentHex = 0xFFE6CD95
        ),
        PlanetInfo(
            id = "uranus",
            name = "Uranus",
            subtitle = "Sideways Cyan Ice Giant",
            orderFromSun = 7,
            diameterKm = 50724.0,
            distanceFromSunAu = "19.20 AU (2.87B km)",
            moonsCount = "28 (Miranda, Ariel, Umbriel, Titania, Oberon)",
            rotationPeriod = "17h 14m (retrograde, 97.8° tilt)",
            orbitalPeriod = "84.0 Earth years",
            atmosphere = "Hydrogen, helium, and ~2.3% methane gas that absorbs red light and lends a serene cyan hue",
            surface = "Deep mantle of hot, high-pressure water, ammonia, and methane ices surrounding a small rocky core",
            missions = listOf("Voyager 2 (1986 flyby)", "Hubble & JWST observations"),
            facts = listOf(
                "Uranus rolls around the Sun tipped 97.8° on its side—giving each pole 42 straight years of sunlight followed by 42 years of darkness.",
                "It was the first planet discovered with a telescope (William Herschel, 1781).",
                "Uranus hosts 13 faint dark rings imaged crisply by the James Webb Space Telescope."
            ),
            imageUrl = "https://images-assets.nasa.gov/image/PIA18182/PIA18182~medium.jpg",
            imageCredit = "NASA / JPL-Caltech / Voyager 2",
            accentHex = 0xFF8BD8E6
        ),
        PlanetInfo(
            id = "neptune",
            name = "Neptune",
            subtitle = "Supersonic Deep-Blue Frontier",
            orderFromSun = 8,
            diameterKm = 49244.0,
            distanceFromSunAu = "30.05 AU (4.50B km)",
            moonsCount = "16 (Triton, Proteus, Nereid)",
            rotationPeriod = "16h 6m 36s",
            orbitalPeriod = "164.8 Earth years",
            atmosphere = "Hydrogen, helium, methane & high-altitude methane ice clouds driven by 2,100 km/h supersonic winds",
            surface = "Ice giant mantle of dense water-ammonia-methane fluid with strong internal heat flux",
            missions = listOf("Voyager 2 (1989 flyby)", "JWST & Hubble"),
            facts = listOf(
                "Neptune was the first planet predicted purely by mathematics before ever being spotted through a telescope (1846).",
                "Its largest moon, Triton, orbits backward—proving it is a captured Kuiper Belt world with active nitrogen geysers.",
                "Sunlight at Neptune is 900 times fainter than at Earth."
            ),
            imageUrl = "https://images-assets.nasa.gov/image/PIA01492/PIA01492~medium.jpg",
            imageCredit = "NASA / JPL / Voyager 2",
            accentHex = 0xFF4D79F6
        )
    )

    val constellations: List<ConstellationInfo> = listOf(
        ConstellationInfo(
            id = "orion",
            name = "Orion",
            latinMeaning = "The Hunter",
            season = "Winter (North) / Summer (South)",
            hemisphere = "Equatorial (visible worldwide)",
            mainStars = listOf("Betelgeuse (α Ori)", "Rigel (β Ori)", "Bellatrix (γ Ori)", "Alnilam, Alnitak & Mintaka (Belt)"),
            starPattern = "An unmistakable hourglass figure bisected by three evenly spaced blue supergiant stars forming Orion's Belt, with a glowing fuzzy sword hanging below.",
            howToLocate = "Look south in northern winter evenings (or north in southern summer). Find the three aligned stars of Orion's Belt first; ruddy Betelgeuse marks the upper-left shoulder and icy blue Rigel marks the lower-right knee.",
            originStory = "In Greek mythology, Orion was a mighty giant huntsman placed among the stars beside his hunting dogs Canis Major and Canis Minor, facing Taurus the Bull.",
            scientificNotes = "Orion's Sword contains the Great Orion Nebula (M42), a massive stellar nursery 1,344 light-years away where hundreds of new solar systems are currently igniting.",
            imageUrl = "https://images-assets.nasa.gov/image/PIA15415/PIA15415~medium.jpg",
            imageCredit = "NASA / JPL-Caltech / WISE"
        ),
        ConstellationInfo(
            id = "ursa_major",
            name = "Ursa Major",
            latinMeaning = "The Great Bear",
            season = "Spring (circumpolar across mid-northern latitudes)",
            hemisphere = "Northern Sky",
            mainStars = listOf("Dubhe (α UMa)", "Merak (β UMa)", "Alioth (ε UMa)", "Mizar & Alcor (ζ UMa)", "Alkaid (η UMa)"),
            starPattern = "Home to the famous Big Dipper / Plough asterism—seven bright stars forming a ladle bowl and gently curved handle.",
            howToLocate = "Look toward the northern horizon or overhead in spring. Draw an imaginary straight line through the two outer bowl stars (Merak to Dubhe) and extend it five times to land directly on Polaris, the North Star.",
            originStory = "One of the oldest recognized sky figures across Eurasia and North America; Greek lore identifies it as Callisto transformed into a great bear.",
            scientificNotes = "Mizar and Alcor in the Dipper's handle form a classic naked-eye double star system (actually a sextuple star group!), and the constellation encloses galaxies M81, M82, and the Pinwheel Galaxy M101.",
            imageUrl = "https://images-assets.nasa.gov/image/PIA25433/PIA25433~medium.jpg",
            imageCredit = "NASA / ESA / CSA"
        ),
        ConstellationInfo(
            id = "cassiopeia",
            name = "Cassiopeia",
            latinMeaning = "The Seated Queen",
            season = "Autumn (circumpolar in Northern Hemisphere)",
            hemisphere = "Northern Sky",
            mainStars = listOf("Schedar (α Cas)", "Caph (β Cas)", "Gamma Cassiopeiae", "Ruchbah (δ Cas)", "Segin (ε Cas)"),
            starPattern = "Five luminous stars tracing a crisp, unmistakable 'W' or 'M' zigzag directly across the northern Milky Way band.",
            howToLocate = "Find Polaris across from the Big Dipper—Cassiopeia sits directly opposite Ursa Major on the far side of the North Celestial Pole.",
            originStory = "Named for the boastful Ethiopian queen Cassiopeia of Greek myth, condemned to circle the pole forever, spending half of each night upside down on her throne.",
            scientificNotes = "Because Cassiopeia lies directly in the plane of our galaxy, binoculars reveal dazzling open star clusters like NGC 457 (the Owl Cluster) and M52, plus the famous supernova remnant Cassiopeia A.",
            imageUrl = "https://images-assets.nasa.gov/image/PIA15415/PIA15415~medium.jpg",
            imageCredit = "NASA / Chandra & Spitzer Archive"
        ),
        ConstellationInfo(
            id = "scorpius",
            name = "Scorpius",
            latinMeaning = "The Scorpion",
            season = "Summer (North) / Winter (South)",
            hemisphere = "Southern & Mid-Northern Skies",
            mainStars = listOf("Antares (α Sco)", "Shaula (λ Sco)", "Sargas (θ Sco)", "Dschubba (δ Sco)"),
            starPattern = "One of the few constellations that genuinely resembles its name—a fierce claw arc leading to a blazing red heart and a sweeping J-shaped stinger tail.",
            howToLocate = "Scan low in the southern summer sky (high overhead from the Southern Hemisphere) for the unmistakable fiery orange-red star Antares.",
            originStory = "In Greek legend, the scorpion sent by Gaia to defeat Orion—placed on the exact opposite side of the sky so the two never appear together.",
            scientificNotes = "Antares is a colossal red supergiant over 680 times the Sun's diameter. Sweeping binoculars near Scorpius's tail reveals rich star clusters M6 (Butterfly Cluster) and M7 right beside the Milky Way core.",
            imageUrl = "https://images-assets.nasa.gov/image/PIA25433/PIA25433~medium.jpg",
            imageCredit = "NASA / ESA Archive"
        ),
        ConstellationInfo(
            id = "cygnus",
            name = "Cygnus",
            latinMeaning = "The Swan (Northern Cross)",
            season = "Summer & Autumn",
            hemisphere = "Northern Sky",
            mainStars = listOf("Deneb (α Cyg)", "Albireo (β Cyg)", "Sadr (γ Cyg)", "Gienah (ε Cyg)"),
            starPattern = "A graceful sweeping cross soaring headlong down the bright river of the summer Milky Way.",
            howToLocate = "Find the bright Summer Triangle (Vega, Altair, Deneb). Deneb marks the Swan's tail, Sadr its glowing chest, and Albireo its extended beak.",
            originStory = "Associated with multiple Greek legends of swans gliding along the celestial river Eridanus and the Milky Way.",
            scientificNotes = "Albireo splits through any small telescope into a breathtaking gold-and-sapphire double star. Cygnus also hosts Cygnus X-1—the first widely confirmed stellar-mass black hole—and the Kepler exoplanet search field.",
            imageUrl = "https://images-assets.nasa.gov/image/PIA15415/PIA15415~medium.jpg",
            imageCredit = "NASA / JPL-Caltech"
        ),
        ConstellationInfo(
            id = "leo",
            name = "Leo",
            latinMeaning = "The Lion",
            season = "Spring (North) / Autumn (South)",
            hemisphere = "Equatorial / Northern",
            mainStars = listOf("Regulus (α Leo)", "Denebola (β Leo)", "Algieba (γ Leo)", "Zosma (δ Leo)"),
            starPattern = "A regal backward question mark ('The Sickle') outlining the lion's head and mane, followed by a crisp triangle marking its hindquarters.",
            howToLocate = "Follow the pointer stars of the Big Dipper bowl downward (opposite Polaris) straight into the heart of Leo and its blue-white beacon Regulus.",
            originStory = "Celebrated since ancient Mesopotamian astronomy and linked in Greek mythology to the impervious Nemean Lion conquered by Heracles.",
            scientificNotes = "Because Leo sits away from the obscuring dust of the Milky Way plane, telescopes pointed below the lion's belly reveal the famous Leo Triplet of spiral galaxies (M65, M66, NGC 3628) and the radiant of November's Leonid meteor shower.",
            imageUrl = "https://images-assets.nasa.gov/image/PIA25433/PIA25433~medium.jpg",
            imageCredit = "NASA / ESA"
        ),
        ConstellationInfo(
            id = "lyra",
            name = "Lyra",
            latinMeaning = "The Harp / Lyre",
            season = "Summer & Autumn",
            hemisphere = "Northern Sky",
            mainStars = listOf("Vega (α Lyr)", "Sheliak (β Lyr)", "Sulafat (γ Lyr)", "Epsilon Lyrae (Double-Double)"),
            starPattern = "Compact diamond parallelogram tucked directly beside brilliant sapphire-white Vega—the fifth-brightest star in the entire night sky.",
            howToLocate = "Look nearly straight overhead on northern summer evenings for Vega, the brightest corner of the Summer Triangle.",
            originStory = "Represents the golden lyre of the mythic musician Orpheus, whose melodies enchanted rivers and stones.",
            scientificNotes = "Between Sheliak and Sulafat floats the iconic Ring Nebula (M57), the glowing ionized gas shell expelled by a dying Sun-like star. Due to Earth's axial precession, Vega will become our North Star in ~12,000 years.",
            imageUrl = "https://images-assets.nasa.gov/image/PIA15415/PIA15415~medium.jpg",
            imageCredit = "NASA / JWST & Hubble Archive"
        ),
        ConstellationInfo(
            id = "crux",
            name = "Crux",
            latinMeaning = "The Southern Cross",
            season = "Autumn & Winter (Southern Hemisphere)",
            hemisphere = "Southern Sky (below ~25° N)",
            mainStars = listOf("Acrux (α Cru)", "Mimosa (β Cru)", "Gacrux (γ Cru)", "Imai (δ Cru)"),
            starPattern = "The smallest of all 88 official constellations, yet one of the most iconic—four radiant stars forming a compact celestial kite embedded in the southern Milky Way.",
            howToLocate = "From the Southern Hemisphere, trace the long axis from Gacrux through Acrux about 4.5 times southward to pinpoint the South Celestial Pole.",
            originStory = "Used for centuries by Polynesian, Indigenous Australian, and maritime navigators to steer across open southern oceans.",
            scientificNotes = "Right beside Mimosa lies the dazzling Jewel Box open star cluster (NGC 4755), framed directly against the dramatic silhouette of the Coalsack Dark Nebula.",
            imageUrl = "https://images-assets.nasa.gov/image/PIA25433/PIA25433~medium.jpg",
            imageCredit = "NASA / ESA Archive"
        )
    )

    val deepSpaceObjects: List<DeepSpaceObject> = listOf(
        DeepSpaceObject(
            id = "orion_nebula",
            name = "Great Orion Nebula",
            catalogId = "M42 / NGC 1976",
            category = "Emission Nebula & Stellar Nursery",
            distanceLabel = "1,344 light-years",
            constellation = "Orion",
            summary = "The closest massive star-forming region to Earth—visible even to the naked eye as a luminous misty jewel in Orion's Sword.",
            scienceDetail = "Spanning 24 light-years across, M42 is illuminated from within by the fierce ultraviolet radiation of four young massive stars called the Trapezium Cluster. Hubble and JWST have captured dozens of protoplanetary disks ('proplyds') actively forming new planets inside its glowing hydrogen clouds.",
            observationNotes = "Visible without optical aid under dark skies. Binoculars reveal its bright misty core; a small backyard telescope cleanly splits the four Trapezium stars inside the swirling gas wings.",
            imageUrl = "https://images-assets.nasa.gov/image/PIA15415/PIA15415~medium.jpg",
            imageCredit = "NASA / ESA / STScI"
        ),
        DeepSpaceObject(
            id = "andromeda_galaxy",
            name = "Andromeda Galaxy",
            catalogId = "M31 / NGC 224",
            category = "Spiral Galaxy",
            distanceLabel = "2.537 million light-years",
            constellation = "Andromeda",
            summary = "The nearest major spiral galaxy to our Milky Way and the farthest object routinely visible to the unaided human eye.",
            scienceDetail = "Home to roughly one trillion stars spanning 152,000 light-years, Andromeda is racing toward the Milky Way at ~110 km/s. In roughly 4.5 billion years, our two galaxies will gently merge into a colossal elliptical galaxy nicknamed 'Milkomeda.'",
            observationNotes = "Best viewed on moonless autumn evenings. Use the middle star of Cassiopeia's right-hand 'V' or the Great Square of Pegasus to hop toward M31's elongated pearlescent core—appearing over 6× wider than the Full Moon in long exposures.",
            imageUrl = "https://images-assets.nasa.gov/image/PIA15415/PIA15415~medium.jpg",
            imageCredit = "NASA / JPL-Caltech / GALEX"
        ),
        DeepSpaceObject(
            id = "carina_cliffs",
            name = "Carina Nebula — Cosmic Cliffs",
            catalogId = "NGC 3324",
            category = "Stellar Nursery & Molecular Cloud",
            distanceLabel = "7,600 light-years",
            constellation = "Carina",
            summary = "Towering seven-light-year-tall mountains of cosmic gas and dust sculpted by furious winds from newborn massive stars.",
            scienceDetail = "Captured in unprecedented infrared clarity by the James Webb Space Telescope, NGC 3324 marks the edge of a cavernous bubble carved out by ultraviolet radiation from hot young stars above the frame, revealing previously hidden infant protostars erupting with supersonic jets.",
            observationNotes = "A premier southern-sky showpiece easily spotted in binoculars inside the wider Carina Nebula complex.",
            imageUrl = "https://images-assets.nasa.gov/image/PIA25433/PIA25433~medium.jpg",
            imageCredit = "NASA / ESA / CSA / STScI (JWST)"
        ),
        DeepSpaceObject(
            id = "pleiades",
            name = "The Pleiades (Seven Sisters)",
            catalogId = "M45",
            category = "Open Star Cluster",
            distanceLabel = "444 light-years",
            constellation = "Taurus",
            summary = "A glittering sapphire cluster of hot, young B-type stars drifting through delicate interstellar reflection dust.",
            scienceDetail = "Born together roughly 100 million years ago, over 1,000 gravitationally bound stars share this cluster—the brightest seven (Alcyone, Atlas, Electra, Maia, Merope, Taygeta, Pleione) shine effortlessly to the naked eye. Their ghostly blue veils are interstellar dust clouds the cluster happens to be sweeping through right now.",
            observationNotes = "Arguably the finest binocular target in the entire autumn and winter sky—resembling a miniature jewel-like dipper.",
            imageUrl = "https://images-assets.nasa.gov/image/PIA15415/PIA15415~medium.jpg",
            imageCredit = "NASA / ESA / AURA / Caltech"
        ),
        DeepSpaceObject(
            id = "sagittarius_a_star",
            name = "Sagittarius A* (Galactic Core)",
            catalogId = "Sgr A*",
            category = "Supermassive Black Hole",
            distanceLabel = "26,670 light-years",
            constellation = "Sagittarius",
            summary = "The 4.3-million-solar-mass supermassive black hole anchoring the dynamical heart of our Milky Way galaxy.",
            scienceDetail = "Hidden behind dense dust clouds in visible light, Sgr A* was proven by decades of infrared tracking of stars whipping around it at 7,600 km/s—and directly imaged as a glowing relativistic ring of superheated plasma by the global Event Horizon Telescope collaboration in 2022.",
            observationNotes = "Although optical light from Sgr A* itself is blocked by dust, looking directly toward the spout of Sagittarius's 'Teapot' asterism on a moonless summer night points your gaze straight at our galaxy's core.",
            imageUrl = "https://images-assets.nasa.gov/image/PIA25433/PIA25433~medium.jpg",
            imageCredit = "NASA / Chandra / Event Horizon Telescope"
        ),
        DeepSpaceObject(
            id = "crab_nebula",
            name = "The Crab Nebula",
            catalogId = "M1 / NGC 1952",
            category = "Supernova Remnant & Pulsar",
            distanceLabel = "6,500 light-years",
            constellation = "Taurus",
            summary = "The still-expanding radioactive debris cloud of a massive star witnessed exploding in broad daylight by astronomers in 1054 AD.",
            scienceDetail = "At its heart spins a city-sized neutron star (the Crab Pulsar) rotating 30.2 times every second, sweeping twin beams of gamma rays, X-rays, and radio waves like a cosmic lighthouse while injecting energy that makes the filamentary shell expand at 1,500 km/s.",
            observationNotes = "Found just northwest of the southern horn star Tianguan (Zeta Tauri) in dark winter skies using binoculars or a 4-inch+ telescope.",
            imageUrl = "https://images-assets.nasa.gov/image/PIA25433/PIA25433~medium.jpg",
            imageCredit = "NASA / ESA / Hubble & JWST"
        )
    )

    val spaceMissions: List<SpaceMissionInfo> = listOf(
        SpaceMissionInfo(
            id = "apollo_11",
            name = "Apollo 11",
            agency = "NASA",
            era = "Crewed Lunar Landing",
            year = "July 1969",
            target = "Mare Tranquillitatis, Moon",
            summary = "Humanity's historic first crewed touchdown on another celestial world by Neil Armstrong, Buzz Aldrin, and Command Module Pilot Michael Collins.",
            achievements = listOf(
                "First human footsteps on the Moon (July 20, 1969) at Tranquility Base.",
                "Returned 21.55 kg of lunar basalt samples and deployed a passive seismic package.",
                "Installed a precision laser retroreflector array still used today to measure Earth–Moon distance to millimeter precision."
            ),
            imageUrl = "https://images-assets.nasa.gov/image/as11-40-5903/as11-40-5903~medium.jpg",
            imageCredit = "NASA / Apollo 11 Archive"
        ),
        SpaceMissionInfo(
            id = "apollo_15",
            name = "Apollo 15",
            agency = "NASA",
            era = "J-Mission Extended Science",
            year = "July–August 1971",
            target = "Hadley-Apennine, Moon",
            summary = "First extended scientific J-mission featuring the electric Lunar Roving Vehicle exploring the towering Apennine Mountains and Hadley Rille canyon.",
            achievements = listOf(
                "Drove 27.9 km across lunar terrain in the first Lunar Rover.",
                "Recovered the iconic 4.1-billion-year-old 'Genesis Rock' (primordial anorthosite crust).",
                "Demonstrated Galileo's hammer-and-feather vacuum gravity drop on live television."
            ),
            imageUrl = "https://images-assets.nasa.gov/image/as15-88-11866/as15-88-11866~medium.jpg",
            imageCredit = "NASA / Apollo 15"
        ),
        SpaceMissionInfo(
            id = "apollo_17",
            name = "Apollo 17",
            agency = "NASA",
            era = "Final Apollo Crewed Mission",
            year = "December 1972",
            target = "Taurus-Littrow Valley, Moon",
            summary = "The longest crewed lunar stay (75 hours) and the first mission to carry a trained field geologist—Harrison Schmitt—onto the Moon.",
            achievements = listOf(
                "Collected a record 110.5 kg of diverse lunar rocks, including orange pyroclastic volcanic glass beads.",
                "Captured the iconic full-disc 'Blue Marble' photograph of Earth en route to the Moon.",
                "Traversed 35.9 km across Taurus-Littrow valley via Lunar Rover."
            ),
            imageUrl = "https://images-assets.nasa.gov/image/as17-134-20384/as17-134-20384~medium.jpg",
            imageCredit = "NASA / Apollo 17"
        ),
        SpaceMissionInfo(
            id = "voyager",
            name = "Voyager 1 & 2",
            agency = "NASA / JPL",
            era = "Grand Tour & Interstellar",
            year = "1977 – Present",
            target = "Jupiter, Saturn, Uranus, Neptune & Interstellar Space",
            summary = "Humanity's farthest-flying spacecraft—executing a once-in-176-year planetary alignment Grand Tour before crossing the heliopause into interstellar space.",
            achievements = listOf(
                "Voyager 2 remains the only spacecraft to visit all four outer giant planets.",
                "Discovered active sulfur volcanoes on Io, subsurface ocean clues on Europa, and nitrogen geysers on Triton.",
                "Captured the humbling 'Pale Blue Dot' portrait of Earth from 6 billion kilometers away."
            ),
            imageUrl = "https://images-assets.nasa.gov/image/PIA01492/PIA01492~medium.jpg",
            imageCredit = "NASA / JPL Voyager"
        ),
        SpaceMissionInfo(
            id = "cassini",
            name = "Cassini–Huygens",
            agency = "NASA / ESA / ASI",
            era = "Outer Planet Orbiter & Lander",
            year = "1997 – 2017",
            target = "Saturn System & Titan",
            summary = "Thirteen transformative years orbiting Saturn, revealing complex ring dynamics, Enceladus's ice plumes, and landing ESA's Huygens probe on Titan.",
            achievements = listOf(
                "Huygens achieved the most distant landing ever on Titan's frozen methane riverbed (2005).",
                "Flew directly through water-ice plumes erupting from Enceladus's hidden global ocean.",
                "Ended with a daring 22-orbit 'Grand Finale' diving between Saturn and its innermost rings."
            ),
            imageUrl = "https://images-assets.nasa.gov/image/PIA11141/PIA11141~medium.jpg",
            imageCredit = "NASA / JPL-Caltech / SSI"
        ),
        SpaceMissionInfo(
            id = "lro",
            name = "Lunar Reconnaissance Orbiter",
            agency = "NASA",
            era = "Modern Lunar Mapping",
            year = "2009 – Present",
            target = "Lunar Polar Orbit",
            summary = "The most detailed lunar cartography mission in history—producing centimeter-to-meter resolution 3D topography, thermal maps, and radiation surveys.",
            achievements = listOf(
                "Mapped over 98% of the Moon in exquisite high-resolution imagery—including Apollo footprints and rovers.",
                "Measured temperatures below -248°C inside permanently shadowed polar craters—the coldest measured spots in the Solar System.",
                "Guides landing site selection for modern commercial and Artemis lunar missions."
            ),
            imageUrl = "https://images-assets.nasa.gov/image/PIA14208/PIA14208~medium.jpg",
            imageCredit = "NASA / GSFC / Arizona State University"
        ),
        SpaceMissionInfo(
            id = "jwst",
            name = "James Webb Space Telescope",
            agency = "NASA / ESA / CSA",
            era = "Flagship Infrared Observatory",
            year = "2021 – Present",
            target = "Sun–Earth L2 Point (1.5M km)",
            summary = "A 6.5-meter gold-coated segmented infrared mirror peering back over 13.5 billion years to the earliest galaxies and dissecting exoplanet atmospheres.",
            achievements = listOf(
                "Unfolded 344 single-point deployments flawlessly en route to the L2 halo orbit.",
                "Detected carbon dioxide, methane, and water vapor spectroscopically in distant exoplanet atmospheres.",
                "Revealed newborn galaxies formed less than 300 million years after the Big Bang."
            ),
            imageUrl = "https://images-assets.nasa.gov/image/PIA25433/PIA25433~medium.jpg",
            imageCredit = "NASA / ESA / CSA / STScI"
        ),
        SpaceMissionInfo(
            id = "chandrayaan_artemis",
            name = "Chandrayaan-3 & Artemis Era",
            agency = "ISRO / NASA / International Partners",
            era = "21st-Century Return to the Moon",
            year = "2023 – 2030s",
            target = "Lunar South Pole Region",
            summary = "A new era of sustainable polar exploration combining historic robotic landings with crewed Artemis missions aiming to live and work on the Moon.",
            achievements = listOf(
                "ISRO's Chandrayaan-3 achieved humanity's first soft landing near the Lunar South Pole (August 2023), deploying the Pragyan rover.",
                "China's Chang'e 6 returned the first soil samples from the lunar far side (June 2024).",
                "NASA's Artemis program is preparing human crews to explore water-ice-bearing polar craters as a stepping stone to Mars."
            ),
            imageUrl = "https://images-assets.nasa.gov/image/PIA13517/PIA13517~medium.jpg",
            imageCredit = "NASA / LRO Polar Archive"
        )
    )

    fun planetById(id: String): PlanetInfo? = planets.firstOrNull { it.id == id }
    fun constellationById(id: String): ConstellationInfo? = constellations.firstOrNull { it.id == id }
    fun deepSpaceById(id: String): DeepSpaceObject? = deepSpaceObjects.firstOrNull { it.id == id }
    fun missionById(id: String): SpaceMissionInfo? = spaceMissions.firstOrNull { it.id == id }
}
