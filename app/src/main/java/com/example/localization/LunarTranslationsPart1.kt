package com.example.localization

/**
 * Genuine, native-readable translations for Languages 1–25:
 * en, zh-CN, es, hi, ar, bn, pt, ru, ja, pa, de, jv, ko, fr, te, mr, tr, ta, vi, ur, id, it, th, gu, fa
 */
object LunarTranslationsPart1 {

    val ENGLISH = LunarStrings(
        tagline = "THE MOON, CLOSER THAN EVER.",
        navHome = "Home",
        navExplore = "Explore",
        navCalendar = "Calendar",
        navTonight = "Tonight",
        navSettings = "Settings",
        back = "Back",
        cancel = "Cancel",
        done = "Done",
        close = "Close",
        save = "Save",
        share = "Share",
        search = "Search",
        refresh = "Refresh",
        reset = "Reset",
        skip = "Skip",
        all = "All",
        today = "Today",
        allow = "Allow",
        clear = "Clear",
        favorites = "Favorites",
        noResults = "No matching results",
        phaseNewMoon = "New Moon",
        phaseWaxingCrescent = "Waxing Crescent",
        phaseFirstQuarter = "First Quarter",
        phaseWaxingGibbous = "Waxing Gibbous",
        phaseFullMoon = "Full Moon",
        phaseWaningGibbous = "Waning Gibbous",
        phaseLastQuarter = "Last Quarter",
        phaseWaningCrescent = "Waning Crescent",
        illuminated = "Illuminated",
        lunarAge = "Lunar Age",
        waxing = "Waxing",
        waning = "Waning",
        moonrise = "Moonrise",
        moonset = "Moonset",
        distance = "Distance",
        altitudeNow = "Altitude Now",
        aboveHorizon = "Above horizon",
        belowHorizon = "Below horizon",
        nextPhase = "Next Phase",
        daysUnit = "d",
        homePhaseGuide = "Today's Phase Guide",
        homeNextEvent = "Next Major Lunar Event",
        homeTonightViewing = "Tonight's Viewing",
        homeExploreMoon = "Explore The Moon",
        homeDidYouKnow = "Did You Know?",
        homeAnotherFact = "Another Fact",
        homeFeaturedDiscovery = "Featured Discovery",
        homePhotography = "Moon Photography",
        homeWallpapers = "Wallpapers",
        homeAllEvents = "All Events",
        homeFullGuide = "Full Guide",
        homeOpenAtlas = "Moon Atlas",
        homeLearnLibrary = "Learn Library",
        exploreTitle = "Moon Atlas & Cosmos",
        exploreSubtitle = "Interactive lunar surface, planetary encyclopedia, constellations & deep space",
        moonAtlas = "Moon Atlas",
        craters = "Crater Atlas",
        lunarMaria = "Lunar Maria",
        mountainsValleys = "Mountains & Valleys",
        lunarPoles = "Lunar Poles",
        rillesChannels = "Rilles & Channels",
        landingSites = "Historic Landing Sites",
        lunarGeology = "Lunar Geology",
        planets = "Planets",
        constellations = "Constellations",
        deepSpace = "Deep Space",
        meteorShowers = "Meteor Showers",
        eclipses = "Eclipses",
        spaceMissions = "Space Missions",
        milkyWay = "Milky Way",
        astronomyKnowledge = "Astronomy Knowledge",
        labelsOn = "Labels",
        labelsOff = "Hidden",
        rotate = "Rotate",
        lunarCalendar = "Lunar Calendar",
        monthHighlights = "Month Highlights",
        tonightQuestion = "What Can I See Tonight?",
        calculatedAstronomy = "Calculated Astronomy",
        weatherConditions = "Sky & Weather Conditions",
        twilightWindow = "Twilight & Darkness Window",
        celestialObjects = "Celestial Objects Tonight",
        visibleNow = "Visible",
        selectCity = "City",
        searchCityHint = "Search any city worldwide...",
        wallpapersTitle = "Astronomy Wallpapers",
        setWallpaper = "Set Wallpaper",
        homeAndLock = "Home & Lock Screens",
        homeOnly = "Home Screen Only",
        lockOnly = "Lock Screen Only",
        searchWallpapers = "Search Moon, Saturn, Nebula...",
        learnTitle = "Astronomy Library",
        photoGuideTitle = "Moon Photography",
        smartphone = "Smartphone",
        cameraDslr = "DSLR / Mirrorless",
        savedFavorites = "Saved Favorites",
        noFavoritesYet = "No Saved Favorites Yet",
        settingsTitle = "Settings",
        catPersonalization = "PERSONALIZATION",
        catAstronomy = "ASTRONOMY",
        catVisualExperience = "VISUAL EXPERIENCE",
        catApp = "APP",
        catAbout = "ABOUT",
        catCreator = "CREATOR",
        languageLabel = "Language",
        languageSearchHint = "Search 50 languages...",
        appearanceLabel = "Appearance",
        fontLabel = "Font Style",
        locationLabel = "Location",
        automaticGps = "Automatic GPS Location",
        unitsTimeLabel = "Units & Time",
        notificationsLabel = "Notifications",
        calendarPrefsLabel = "Calendar Preferences",
        observationPrefsLabel = "Observation Preferences",
        widgetsLabel = "Home Screen Widgets",
        introAnimationLabel = "Opening Animation",
        replayIntroLabel = "Replay Opening Animation",
        introStyleLabel = "Intro Moon Style",
        wallpaperQualityLabel = "Wallpaper Quality",
        reducedMotionLabel = "Reduce Motion",
        dataStorageLabel = "Data & Storage",
        clearCacheTitle = "Clear Cached Data?",
        clearCacheConfirm = "Clear Cache",
        privacyLabel = "Privacy & Permissions",
        feedbackLabel = "Feedback & Support",
        aboutLunarLabel = "About LUNAR",
        createdBy = "Created by",
        restartDialogTitle = "Change App Language?",
        restartDialogMessage = "LUNAR needs to restart to apply your new language.\n\nYour selected language will be applied after restarting the app.",
        restartDialogConfirm = "Change & Restart",
        introArtisticBadge = "ARTISTIC INTRO VARIANT"
    )

    val translations: Map<String, LunarStrings> = mapOf(
        "en" to ENGLISH,

        // 2. Simplified Chinese
        "zh-CN" to LunarStrings(
            "月球，前所未有的近距离。", "主页", "探索", "月历", "今夜", "设置",
            "返回", "取消", "完成", "关闭", "保存", "分享", "搜索", "刷新", "重置", "跳过", "全部", "今天", "允许", "清除", "收藏", "未找到结果",
            "新月", "蛾眉月", "上弦月", "盈凸月", "满月", "亏凸月", "下弦月", "残月",
            "照亮", "月龄", "盈月", "亏月", "月出", "月落", "距离", "当前高度", "地平线上", "地平线下", "下一月相", "天",
            "今日月相指南", "下一次重要月相事件", "今夜观月条件", "探索月球", "你知道吗？", "换一条知识", "精选发现", "月球摄影", "壁纸库", "所有天象", "完整指南", "月球图集", "天文图书馆",
            "月球图集与宇宙", "交互式月面地图、行星百科、星座与深空天体", "月球图集", "环形山图集", "月海", "山脉与峡谷", "月球两极", "月溪与沟壑", "历史着陆点", "月球地质",
            "行星", "星座", "深空", "流星雨", "日月食", "太空任务", "银河", "天文知识", "标签", "已隐藏", "旋转",
            "月相日历", "本月重点", "今夜星空可见什么？", "天文计算数据", "天气与云量", "暮光与暗夜窗口", "今夜可见天体", "可见", "城市", "搜索全球城市...",
            "天文壁纸", "设为壁纸", "主屏幕和锁屏", "仅主屏幕", "仅锁屏", "搜索月球、土星、星云...", "天文图书馆", "月球摄影指南", "智能手机", "单反 / 微单", "我的收藏", "暂无收藏",
            "设置", "个性化", "天文", "视觉体验", "应用", "关于", "创作者",
            "语言", "搜索 50 种语言...", "外观主题", "字体样式", "位置", "自动 GPS 定位", "单位与时间", "通知提醒", "日历偏好", "观测偏好", "桌面小组件",
            "开屏动画", "重播开屏动画", "开屏月球风格", "壁纸画质", "减弱动态效果", "数据与存储", "清除缓存数据？", "清除缓存", "隐私与权限", "反馈与支持", "关于 LUNAR", "创作者",
            "更改应用语言？", "LUNAR 需要重启以应用新语言。\n\n重启后将立即显示您选择的语言。", "更改并重启", "艺术开场视觉"
        ),

        // 3. Spanish
        "es" to LunarStrings(
            "LA LUNA, MÁS CERCA QUE NUNCA.", "Inicio", "Explorar", "Calendario", "Esta noche", "Ajustes",
            "Atrás", "Cancelar", "Listo", "Cerrar", "Guardar", "Compartir", "Buscar", "Actualizar", "Restablecer", "Omitir", "Todo", "Hoy", "Permitir", "Limpiar", "Favoritos", "Sin resultados",
            "Luna Nueva", "Luna Creciente", "Cuarto Creciente", "Gibosa Creciente", "Luna Llena", "Gibosa Menguante", "Cuarto Menguante", "Luna Menguante",
            "Iluminada", "Edad lunar", "Creciente", "Menguante", "Salida lunar", "Puesta lunar", "Distancia", "Altitud actual", "Sobre el horizonte", "Bajo el horizonte", "Próxima fase", "d",
            "Guía de fase de hoy", "Próximo evento lunar", "Observación de esta noche", "Explorar la Luna", "¿Sabías qué?", "Otro dato", "Descubrimiento destacado", "Fotografía lunar", "Fondos", "Todos los eventos", "Guía completa", "Atlas lunar", "Biblioteca",
            "Atlas Lunar y Cosmos", "Superficie lunar interactiva, planetas, constelaciones y espacio profundo", "Atlas Lunar", "Atlas de Cráteres", "Mares Lunares", "Montañas y Valles", "Polos Lunares", "Rimas y Canales", "Sitios de Alunizaje", "Geología Lunar",
            "Planetas", "Constelaciones", "Espacio Profundo", "Lluvias de Meteoros", "Eclipses", "Misiones Espaciales", "Vía Láctea", "Ciencia Astronómica", "Etiquetas", "Oculto", "Girar",
            "Calendario Lunar", "Destacados del mes", "¿Qué puedo ver esta noche?", "Astronomía calculada", "Clima y nubosidad", "Ventana de oscuridad", "Objetos celestes", "Visible", "Ciudad", "Buscar cualquier ciudad...",
            "Fondos de Astronomía", "Usar como fondo", "Inicio y bloqueo", "Solo pantalla inicio", "Solo bloqueo", "Buscar Luna, Saturno, Nebulosa...", "Biblioteca Astronómica", "Fotografía Lunar", "Teléfono", "Cámara DSLR", "Favoritos guardados", "Aún sin favoritos",
            "Ajustes", "PERSONALIZACIÓN", "ASTRONOMÍA", "EXPERIENCIA VISUAL", "APLICACIÓN", "ACERCA DE", "CREADOR",
            "Idioma", "Buscar entre 50 idiomas...", "Apariencia", "Tipografía", "Ubicación", "GPS automático", "Unidades y hora", "Notificaciones", "Preferencias del calendario", "Preferencias de observación", "Widgets de inicio",
            "Animación de inicio", "Ver animación otra vez", "Estilo lunar de inicio", "Calidad de fondos", "Reducir movimiento", "Datos y almacenamiento", "¿Borrar caché?", "Borrar caché", "Privacidad y permisos", "Comentarios y soporte", "Acerca de LUNAR", "Creado por",
            "¿Cambiar idioma de la app?", "LUNAR necesita reiniciarse para aplicar tu nuevo idioma.\n\nEl idioma seleccionado se aplicará tras reiniciar.", "Cambiar y reiniciar", "VARIANTE ARTÍSTICA"
        ),

        // 4. Hindi
        "hi" to LunarStrings(
            "चाँद, पहले से कहीं अधिक करीब।", "होम", "अन्वेषण", "कैलेंडर", "आज रात", "सेटिंग्स",
            "वापस", "रद्द करें", "पूर्ण", "बंद करें", "सहेजें", "साझा करें", "खोजें", "रीफ़्रेश", "रीसेट", "छोड़ें", "सभी", "आज", "अनुमति दें", "साफ़ करें", "पसंदीदा", "कोई परिणाम नहीं",
            "अमावस्या", "शुक्ल बालचंद्र", "प्रथम चतुर्थांश", "शुक्ल पक्ष", "पूर्णिमा", "कृष्ण पक्ष", "अंतिम चतुर्थांश", "कृष्ण बालचंद्र",
            "प्रकाशित", "चंद्र आयु", "शुक्ल", "कृष्ण", "चंद्रोदय", "चंद्रास्त", "दूरी", "वर्तमान ऊँचाई", "क्षितिज के ऊपर", "क्षितिज के नीचे", "अगला चरण", "दिन",
            "आज का चंद्र चरण", "अगली प्रमुख खगोलीय घटना", "आज रात का आकाश", "चाँद को जानें", "क्या आप जानते हैं?", "अन्य तथ्य", "प्रमुख खोज", "चंद्र फोटोग्राफी", "वॉलपेपर", "सभी घटनाएँ", "पूर्ण गाइड", "चंद्र एटलस", "ज्ञान पुस्तकालय",
            "चंद्र एटलस और ब्रह्मांड", "इंटरैक्टिव चंद्र सतह, ग्रह, नक्षत्र और गहरा अंतरिक्ष", "चंद्र एटलस", "क्रेटर एटलस", "चंद्र मारिया", "पर्वत और घाटियाँ", "चंद्र ध्रुव", "घाटियाँ और चैनल", "ऐतिहासिक लैंडिंग स्थल", "चंद्र भूविज्ञान",
            "ग्रह", "तारामंडल", "गहरा अंतरिक्ष", "उल्का वर्षा", "ग्रहण", "अंतरिक्ष मिशन", "आकाशगंगा", "खगोल ज्ञान", "लेबल", "छिपा हुआ", "घुमाएँ",
            "चंद्र कैलेंडर", "माह के मुख्य आकर्षण", "आज रात आकाश में क्या दिखेगा?", "गणित खगोल डेटा", "मौसम और बादल", "गोधूलि और अंधकार समय", "आज रात के खगोलीय पिंड", "दृश्यमान", "शहर", "दुनिया का कोई भी शहर खोजें...",
            "खगोल वॉलपेपर", "वॉलपेपर सेट करें", "होम और लॉक स्क्रीन", "केवल होम स्क्रीन", "केवल लॉक स्क्रीन", "चाँद, शनि, नेबुला खोजें...", "खगोल पुस्तकालय", "चंद्र फोटोग्राफी", "स्मार्टफ़ोन", "कैमरा / DSLR", "सहेजे गए पसंदीदा", "अभी कोई पसंदीदा नहीं",
            "सेटिंग्स", "व्यक्तिगतकरण", "खगोल विज्ञान", "दृश्य अनुभव", "ऐप", "परिचय", "निर्माता",
            "भाषा", "50 भाषाएँ खोजें...", "थीम और रूप", "फ़ॉन्ट शैली", "स्थान", "स्वचालित GPS स्थान", "इकाइयाँ और समय", "सूचनाएँ", "कैलेंडर प्राथमिकताएँ", "अवलोकन प्राथमिकताएँ", "होम स्क्रीन विजेट",
            "आरंभिक एनीमेशन", "एनीमेशन फिर से चलाएँ", "इंट्रो चंद्र शैली", "वॉलपेपर गुणवत्ता", "मोशन कम करें", "डेटा और संग्रहण", "कैश डेटा साफ़ करें?", "कैश साफ़ करें", "गोपनीयता और अनुमतियाँ", "फ़ीडबैक और सहायता", "LUNAR के बारे में", "निर्माता",
            "ऐप की भाषा बदलें?", "नई भाषा लागू करने के लिए LUNAR को पुनः आरंभ करना होगा।\n\nरीस्टार्ट होने के बाद आपकी चुनी हुई भाषा लागू हो जाएगी।", "बदलें और रीस्टार्ट करें", "कलात्मक दृश्य रूप"
        ),

        // 5. Arabic (RTL)
        "ar" to LunarStrings(
            "القمر، أقرب من أي وقت مضى.", "الرئيسية", "استكشاف", "التقويم", "الليلة", "الإعدادات",
            "رجوع", "إلغاء", "تم", "إغلاق", "حفظ", "مشاركة", "بحث", "تحديث", "إعادة ضبط", "تخطي", "الكل", "اليوم", "سماح", "مسح", "المفضلة", "لا توجد نتائج",
            "المحاق", "الهلال المتزايد", "التربيع الأول", "الأحدب المتزايد", "البدر", "الأحدب المتناقص", "التربيع الأخير", "الهلال المتناقص",
            "الإضاءة", "عمر القمر", "متزايد", "متناقص", "شروق القمر", "غروب القمر", "المسافة", "الارتفاع الآن", "فوق الأفق", "تحت الأفق", "الطور القادم", "ي",
            "دليل طور اليوم", "الحدث القمري القادم", "رصد الليلة", "استكشف القمر", "هل تعلم؟", "حقيقة أخرى", "اكتشاف مميز", "تصوير القمر", "الخلفيات", "كل الأحداث", "الدليل الكامل", "أطلس القمر", "مكتبة الفلك",
            "أطلس القمر والكون", "سطح القمر التفاعلي، الكواكب، الكوكبات والفضاء العميق", "أطلس القمر", "أطلس الفوهات", "بحار القمر", "الجبال والوديان", "قطبا القمر", "الأخاديد والقنوات", "مواقع الهبوط", "جيولوجيا القمر",
            "الكواكب", "الكوكبات", "الفضاء العميق", "زخات الشهب", "الكسوف والخسوف", "مهمات الفضاء", "درب التبانة", "علوم الفلك", "التسميات", "مخفي", "تدوير",
            "التقويم القمري", "أبرز أحداث الشهر", "ماذا يمكنني رؤية الليلة؟", "حسابات فلكية دقيقة", "الطقس والغيوم", "نافذة الشفق والظلام", "الأجرام السماوية الليلة", "مرئي", "المدينة", "ابحث عن أي مدينة...",
            "خلفيات الفلك", "تعيين كخلفية", "الشاشة الرئيسية والقفل", "الرئيسية فقط", "شاشة القفل فقط", "ابحث عن القمر، زحل، سديم...", "المكتبة الفلكية", "دليل تصوير القمر", "الهاتف الذكي", "كاميرا احترافية", "المفضلة المحفوظة", "لا توجد عناصر مفضلة بعد",
            "الإعدادات", "التخصيص", "الفلك", "التجربة البصرية", "التطبيق", "حول", "المطور",
            "اللغة", "ابحث ضمن 50 لغة...", "المظهر", "نوع الخط", "الموقع", "تحديد الموقع تلقائياً", "الوحدات والوقت", "الإشعارات", "تفضيلات التقويم", "تفضيلات الرصد", "ودجات الشاشة",
            "الرسوم الافتتاحية", "إعادة تشغيل المقدمة", "نمط القمر الافتتاحي", "جودة الخلفيات", "تقليل الحركة", "البيانات والتخزين", "مسح الذاكرة المؤقتة؟", "مسح التخزين المؤقت", "الخصوصية والأذونات", "الملاحظات والدعم", "حول LUNAR", "تطوير",
            "تغيير لغة التطبيق؟", "يحتاج تطبيق LUNAR إلى إعادة التشغيل لتطبيق اللغة الجديدة.\n\nسيتم تفعيل اللغة المختارة فور إعادة فتح التطبيق.", "تغيير وإعادة التشغيل", "نسخة فنية للمقدمة"
        ),

        // 6. Bengali
        "bn" to LunarStrings(
            "চাঁদ, আগের চেয়েও কাছে।", "হোম", "অন্বেষণ", "ক্যালেন্ডার", "আজ রাত", "সেটিংস",
            "পিছনে", "বাতিল", "সম্পন্ন", "বন্ধ", "সংরক্ষণ", "শেয়ার", "খুঁজুন", "রিফ্রেশ", "রিসেট", "এড়িয়ে যান", "সব", "আজ", "অনুমতি দিন", "মুছুন", "পছন্দসই", "কোনো ফলাফল নেই",
            "অমাবস্যা", "শুক্ল প্রতিপদ", "প্রথম চতুর্থাংশ", "শুক্লপক্ষ", "পূর্ণিমা", "কৃষ্ণপক্ষ", "শেষ চতুর্থাংশ", "কৃষ্ণ প্রতিপদ",
            "আলোকিত", "চাঁদের বয়স", "বৃদ্ধিমান", "ক্ষয়িষ্ণু", "চন্দ্রোদয়", "চন্দ্রাস্ত", "দূরত্ব", "বর্তমান উচ্চতা", "দিগন্তের উপরে", "দিগন্তের নিচে", "পরবর্তী দশা", "দিন",
            "আজকের চন্দ্র দশা", "পরবর্তী মহাজাগতিক ঘটনা", "আজ রাতের পর্যবেক্ষণ", "চাঁদ অন্বেষণ", "আপনি কি জানেন?", "আরেকটি তথ্য", "বিশেষ আবিষ্কার", "চন্দ্র ফটোগ্রাফি", "ওয়ালপেপার", "সব ঘটনা", "সম্পূর্ণ গাইড", "চন্দ্র অ্যাটলাস", "জ্যোতির্বিদ্যা লাইব্রেরি",
            "চন্দ্র অ্যাটলাস ও মহাবিশ্ব", "ইন্টারেক্টিভ চন্দ্রপৃষ্ঠ, গ্রহ, নক্ষত্রমণ্ডল ও গভীর মহাকাশ", "চন্দ্র অ্যাটলাস", "গহ্বর অ্যাটলাস", "চন্দ্র মারিয়া", "পর্বত ও উপত্যকা", "চন্দ্র মেরু", "চ্যানেল ও উপত্যকা", "ঐতিহাসিক অবতরণস্থল", "চন্দ্র ভূতত্ত্ব",
            "গ্রহসমূহ", "নক্ষত্রমণ্ডল", "গভীর মহাকাশ", "উল্কাবৃষ্টি", "গ্রহণ", "মহাকাশ অভিযান", "আকাশগঙ্গা", "জ্যোতির্বিদ্যা জ্ঞান", "লেবেল", "লুকানো", "ঘোরান",
            "চন্দ্র ক্যালেন্ডার", "মাসের বিশেষ দিন", "আজ রাতে আকাশে কী দেখা যাবে?", "গণনাকৃত জ্যোতির্বিদ্যা", "আবহাওয়া ও মেঘ", "গোধূলি ও অন্ধকার সময়", "আজ রাতের জ্যোতিষ্ক", "দৃশ্যমান", "শহর", "বিশ্বের যেকোনো শহর খুঁজুন...",
            "মহাকাশ ওয়ালপেপার", "ওয়ালপেপার সেট করুন", "হোম ও লক স্ক্রিন", "শুধু হোম স্ক্রিন", "শুধু লক স্ক্রিন", "চাঁদ, শনি, নীহারিকা খুঁজুন...", "জ্যোতির্বিদ্যা লাইব্রেরি", "চন্দ্র ফটোগ্রাফি", "স্মার্টফোন", "ডিএসএলআর ক্যামেরা", "সংরক্ষিত পছন্দ", "কোনো পছন্দ এখনো নেই",
            "সেটিংস", "ব্যক্তিগতকরণ", "জ্যোতির্বিদ্যা", "দৃশ্য অভিজ্ঞতা", "অ্যাপ", "সম্পর্কে", "নির্মাতা",
            "ভাষা", "৫০টি ভাষা খুঁজুন...", "থিম ও রূপ", "ফন্ট শৈলী", "অবস্থান", "স্বয়ংক্রিয় GPS", "একক ও সময়", "বিজ্ঞপ্তি", "ক্যালেন্ডার পছন্দ", "পর্যবেক্ষণ পছন্দ", "হোম স্ক্রিন উইজেট",
            "শুরুর অ্যানিমেশন", "আবার দেখুন", "ইন্ট্রো চাঁদের ধরন", "ওয়ালপেপার মান", "অ্যানিমেশন কমান", "ডেটা ও স্টোরেজ", "ক্যাশ মুছবেন?", "ক্যাশ মুছুন", "গোপনীয়তা ও অনুমতি", "মতামত ও সহায়তা", "LUNAR সম্পর্কে", "নির্মাতা",
            "অ্যাপের ভাষা পরিবর্তন করবেন?", "নতুন ভাষা প্রয়োগ করতে LUNAR পুনরায় চালু করতে হবে।\n\nরিস্টার্টের পর নির্বাচিত ভাষা চালু হবে।", "পরিবর্তন ও রিস্টার্ট", "শৈল্পিক ইন্ট্রো দৃশ্য"
        ),

        // 7. Portuguese
        "pt" to LunarStrings(
            "A LUA, MAIS PERTO DO QUE NUNCA.", "Início", "Explorar", "Calendário", "Esta Noite", "Configurações",
            "Voltar", "Cancelar", "Concluir", "Fechar", "Salvar", "Compartilhar", "Pesquisar", "Atualizar", "Redefinir", "Pular", "Todos", "Hoje", "Permitir", "Limpar", "Favoritos", "Sem resultados",
            "Lua Nova", "Lua Crescente", "Quarto Crescente", "Gibosa Crescente", "Lua Cheia", "Gibosa Minguante", "Quarto Minguante", "Lua Minguante",
            "Iluminada", "Idade lunar", "Crescente", "Minguante", "Nascer da Lua", "Pôr da Lua", "Distância", "Altitude agora", "Acima do horizonte", "Abaixo do horizonte", "Próxima fase", "d",
            "Guia da fase de hoje", "Próximo evento lunar", "Observação de hoje", "Explorar a Lua", "Você sabia?", "Outro fato", "Descoberta em destaque", "Fotografia lunar", "Papéis de parede", "Todos os eventos", "Guia completo", "Atlas Lunar", "Biblioteca",
            "Atlas Lunar e Cosmos", "Superfície lunar interativa, planetas, constelações e espaço profundo", "Atlas Lunar", "Atlas de Crateras", "Mares Lunares", "Montanhas e Vales", "Polos Lunares", "Rimas e Canais", "Locais de Pouso", "Geologia Lunar",
            "Planetas", "Constelações", "Espaço Profundo", "Chuvas de Meteoros", "Eclipses", "Missões Espaciais", "Via Láctea", "Ciência Astronômica", "Rótulos", "Oculto", "Girar",
            "Calendário Lunar", "Destaques do mês", "O que posso ver esta noite?", "Astronomia calculada", "Clima e nuvens", "Janela de escuridão", "Objetos celestes", "Visível", "Cidade", "Pesquisar qualquer cidade...",
            "Papéis de Parede", "Definir papel de parede", "Início e bloqueio", "Apenas tela inicial", "Apenas bloqueio", "Pesquisar Lua, Saturno, Nebulosa...", "Biblioteca Astronômica", "Fotografia Lunar", "Smartphone", "Câmera DSLR", "Favoritos salvos", "Nenhum favorito ainda",
            "Configurações", "PERSONALIZAÇÃO", "ASTRONOMIA", "EXPERIÊNCIA VISUAL", "APLICATIVO", "SOBRE", "CRIADOR",
            "Idioma", "Pesquisar 50 idiomas...", "Aparência", "Estilo de fonte", "Localização", "GPS automático", "Unidades e hora", "Notificações", "Preferências do calendário", "Preferências de observação", "Widgets iniciais",
            "Animação inicial", "Reproduzir novamente", "Estilo lunar inicial", "Qualidade dos papéis", "Reduzir movimento", "Dados e armazenamento", "Limpar cache?", "Limpar cache", "Privacidade e permissões", "Feedback e suporte", "Sobre o LUNAR", "Criado por",
            "Alterar idioma do app?", "O LUNAR precisa reiniciar para aplicar o novo idioma.\n\nO idioma escolhido será aplicado após a reinicialização.", "Alterar e reiniciar", "VARIANTE ARTÍSTICA"
        ),

        // 8. Russian
        "ru" to LunarStrings(
            "ЛУНА БЛИЖЕ, ЧЕМ КОГДА-ЛИБО.", "Главная", "Обзор", "Календарь", "Сегодня", "Настройки",
            "Назад", "Отмена", "Готово", "Закрыть", "Сохранить", "Поделиться", "Поиск", "Обновить", "Сброс", "Пропустить", "Все", "Сегодня", "Разрешить", "Очистить", "Избранное", "Ничего не найдено",
            "Новолуние", "Растущий серп", "Первая четверть", "Растущая Луна", "Полнолуние", "Убывающая Луна", "Последняя четверть", "Убывающий серп",
            "Освещённость", "Возраст Луны", "Растущая", "Убывающая", "Восход Луны", "Заход Луны", "Расстояние", "Высота сейчас", "Над горизонтом", "За горизонтом", "След. фаза", "д",
            "Фаза Луны сегодня", "Ближайшее событие", "Наблюдения этой ночью", "Исследуйте Луну", "Знаете ли вы?", "Другой факт", "Открытие дня", "Фотография Луны", "Обои", "Все события", "Подробнее", "Атлас Луны", "Библиотека",
            "Атлас Луны и Космос", "Интерактивная карта Луны, планеты, созвездия и глубокий космос", "Атлас Луны", "Кратеры", "Лунные моря", "Горы и долины", "Полюса Луны", "Борозды и каналы", "Места посадок", "Геология Луны",
            "Планеты", "Созвездия", "Глубокий космос", "Метеорные потоки", "Затмения", "Космические миссии", "Млечный Путь", "Астрономия", "Подписи", "Скрыто", "Поворот",
            "Лунный календарь", "События месяца", "Что видно на небе сегодня?", "Расчётные данные", "Погода и облачность", "Сумерки и темнота", "Небесные объекты", "Видно", "Город", "Поиск города...",
            "Астрономические обои", "Установить обои", "Главный и экран блокировки", "Только главный", "Только блокировка", "Поиск: Луна, Сатурн, Туманность...", "Библиотека астрономии", "Фотография Луны", "Смартфон", "Камера / DSLR", "Избранное", "В избранном пока пусто",
            "Настройки", "ПЕРСОНАЛИЗАЦИЯ", "АСТРОНОМИЯ", "ВИЗУАЛ", "ПРИЛОЖЕНИЕ", "О ПРОГРАММЕ", "СОЗДАТЕЛЬ",
            "Язык", "Поиск среди 50 языков...", "Оформление", "Шрифт", "Местоположение", "Авто-GPS", "Единицы и время", "Уведомления", "Настройки календаря", "Настройки наблюдений", "Виджеты",
            "Вступительная анимация", "Повторить заставку", "Стиль Луны в заставке", "Качество обоев", "Уменьшить анимацию", "Данные и кэш", "Очистить кэш?", "Очистить кэш", "Конфиденциальность", "Обратная связь", "О LUNAR", "Автор",
            "Изменить язык приложения?", "Для применения нового языка LUNAR необходимо перезапустить.\n\nВыбранный язык вступит в силу после перезапуска.", "Изменить и перезапустить", "ХУДОЖЕСТВЕННЫЙ ВАРИАНТ"
        ),

        // 9. Japanese
        "ja" to LunarStrings(
            "月を、これまで以上に近く。", "ホーム", "探索", "カレンダー", "今夜", "設定",
            "戻る", "キャンセル", "完了", "閉じる", "保存", "共有", "検索", "更新", "リセット", "スキップ", "すべて", "今日", "許可", "消去", "お気に入り", "該当なし",
            "新月", "三日月", "上弦の月", "十三夜月", "満月", "寝待月", "下弦の月", "有明の月",
            "月面輝度", "月齢", "満ちる月", "欠ける月", "月の出", "月の入り", "距離", "現在の高度", "地平線上", "地平線下", "次の月相", "日",
            "今日の月相ガイド", "次の主要天文現象", "今夜の観望条件", "月面を探索", "豆知識", "別の豆知識", "注目の発見", "月の撮影ガイド", "壁紙", "全イベント", "詳細ガイド", "月面アトラス", "天文ライブラリ",
            "月面アトラスと宇宙", "インタラクティブ月面図、惑星、星座、ディープスカイ", "月面アトラス", "クレーター図鑑", "月の海", "山脈と谷", "月の極域", "リルと谷", "歴史的着陸地点", "月面地質学",
            "惑星", "星座", "ディープスカイ", "流星群", "日食・月食", "宇宙ミッション", "天の川", "天文学の基礎", "ラベル", "非表示", "回転",
            "月齢カレンダー", "今月の注目", "今夜の夜空に見えるもの", "天文学的計算データ", "天気と雲量", "薄明と暗夜ウィンドウ", "今夜の天体", "観測可能", "都市", "世界中の都市を検索...",
            "天文壁紙コレクション", "壁紙に設定", "ホームとロック画面", "ホーム画面のみ", "ロック画面のみ", "月、土星、星雲を検索...", "天文ライブラリ", "月の撮影ガイド", "スマートフォン", "一眼レフ / ミラーレス", "お気に入り", "お気に入りはまだありません",
            "設定", "パーソナライズ", "天文学", "ビジュアル体験", "アプリ", "情報", "クリエイター",
            "言語", "50言語を検索...", "外観テーマ", "フォントスタイル", "現在地", "自動GPS位置情報", "単位と時刻", "通知", "カレンダー設定", "観望設定", "ホーム画面ウィジェット",
            "起動アニメーション", "イントロを再生", "イントロの月スタイル", "壁紙の品質", "モーションを減らす", "データとストレージ", "キャッシュを削除しますか？", "キャッシュを削除", "プライバシーと権限", "フィードバック", "LUNAR について", "制作",
            "アプリの言語を変更しますか？", "新しい言語を適用するには LUNAR を再起動する必要があります。\n\n再起動後、選択した言語で表示されます。", "変更して再起動", "アート演出スタイル"
        ),

        // 10. Punjabi
        "pa" to LunarStrings(
            "ਚੰਨ, ਪਹਿਲਾਂ ਨਾਲੋਂ ਕਿਤੇ ਨੇੜੇ।", "ਹੋਮ", "ਖੋਜ", "ਕੈਲੰਡਰ", "ਅੱਜ ਰਾਤ", "ਸੈਟਿੰਗਾਂ",
            "ਪਿੱਛੇ", "ਰੱਦ ਕਰੋ", "ਪੂਰਾ", "ਬੰਦ ਕਰੋ", "ਸੰਭਾਲੋ", "ਸਾਂਝਾ ਕਰੋ", "ਖੋਜੋ", "ਤਾਜ਼ਾ ਕਰੋ", "ਰੀਸੈੱਟ", "ਛੱਡੋ", "ਸਭ", "ਅੱਜ", "ਇਜਾਜ਼ਤ ਦਿਓ", "ਸਾਫ਼ ਕਰੋ", "ਪਸੰਦੀਦਾ", "ਕੋਈ ਨਤੀਜਾ ਨਹੀਂ",
            "ਅਮਾਵਸ", "ਵਧਦਾ ਚੰਨ", "ਪਹਿਲੀ ਚੌਥਾਈ", "ਵਧਦਾ ਪੱਖ", "ਪੂਰਨਮਾਸ਼ੀ", "ਘਟਦਾ ਪੱਖ", "ਆਖ਼ਰੀ ਚੌਥਾਈ", "ਘਟਦਾ ਚੰਨ",
            "ਰੋਸ਼ਨੀ", "ਚੰਨ ਦੀ ਉਮਰ", "ਵਧ ਰਿਹਾ", "ਘਟ ਰਿਹਾ", "ਚੰਨ ਚੜ੍ਹਨਾ", "ਚੰਨ ਡੁੱਬਣਾ", "ਦੂਰੀ", "ਮੌਜੂਦਾ ਉਚਾਈ", "ਦਿਸਹੱਦੇ ਤੋਂ ਉੱਪਰ", "ਦਿਸਹੱਦੇ ਤੋਂ ਹੇਠਾਂ", "ਅਗਲਾ ਪੜਾਅ", "ਦਿਨ",
            "ਅੱਜ ਦਾ ਚੰਨ ਪੜਾਅ", "ਅਗਲੀ ਵੱਡੀ ਘਟਨਾ", "ਅੱਜ ਰਾਤ ਦਾ ਆਕਾਸ਼", "ਚੰਨ ਦੀ ਖੋਜ", "ਕੀ ਤੁਸੀਂ ਜਾਣਦੇ ਹੋ?", "ਹੋਰ ਤੱਥ", "ਖ਼ਾਸ ਖੋਜ", "ਚੰਨ ਫੋਟੋਗ੍ਰਾਫੀ", "ਵਾਲਪੇਪਰ", "ਸਾਰੀਆਂ ਘਟਨਾਵਾਂ", "ਪੂਰੀ ਗਾਈਡ", "ਚੰਨ ਐਟਲਸ", "ਗਿਆਨ ਲਾਇਬ੍ਰੇਰੀ",
            "ਚੰਨ ਐਟਲਸ ਅਤੇ ਬ੍ਰਹਿਮੰਡ", "ਇੰਟਰਐਕਟਿਵ ਚੰਨ ਸਤ੍ਹਾ, ਗ੍ਰਹਿ, ਤਾਰਾਮੰਡਲ ਅਤੇ ਡੂੰਘਾ ਪੁਲਾੜ", "ਚੰਨ ਐਟਲਸ", "ਕ੍ਰੇਟਰ ਐਟਲਸ", "ਚੰਨ ਦੇ ਸਾਗਰ", "ਪਹਾੜ ਅਤੇ ਵਾਦੀਆਂ", "ਚੰਨ ਦੇ ਧਰੁਵ", "ਘਾਟੀਆਂ ਅਤੇ ਚੈਨਲ", "ਲੈਂਡਿੰਗ ਸਥਾਨ", "ਚੰਨ ਭੂ-ਵਿਗਿਆਨ",
            "ਗ੍ਰਹਿ", "ਤਾਰਾਮੰਡਲ", "ਡੂੰਘਾ ਪੁਲਾੜ", "ਉਲਕਾ ਵਰਖਾ", "ਗ੍ਰਹਿਣ", "ਪੁਲਾੜ ਮਿਸ਼ਨ", "ਆਕਾਸ਼ਗੰਗਾ", "ਖਗੋਲ ਗਿਆਨ", "ਲੇਬਲ", "ਲੁਕਿਆ", "ਘੁੰਮਾਓ",
            "ਚੰਨ ਕੈਲੰਡਰ", "ਮਹੀਨੇ ਦੀਆਂ ਖ਼ਾਸ ਤਾਰੀਖ਼ਾਂ", "ਅੱਜ ਰਾਤ ਆਕਾਸ਼ ਵਿੱਚ ਕੀ ਦਿਖੇਗਾ?", "ਗਣਨਾ ਕੀਤਾ ਡੇਟਾ", "ਮੌਸਮ ਅਤੇ ਬੱਦਲ", "ਹਨੇਰੇ ਦਾ ਸਮਾਂ", "ਅੱਜ ਰਾਤ ਦੇ ਤਾਰੇ", "ਦਿਖਾਈ ਦਿੰਦਾ", "ਸ਼ਹਿਰ", "ਕੋਈ ਵੀ ਸ਼ਹਿਰ ਖੋਜੋ...",
            "ਖਗੋਲ ਵਾਲਪੇਪਰ", "ਵਾਲਪੇਪਰ ਸੈੱਟ ਕਰੋ", "ਹੋਮ ਅਤੇ ਲੌਕ ਸਕ੍ਰੀਨ", "ਸਿਰਫ਼ ਹੋਮ ਸਕ੍ਰੀਨ", "ਸਿਰਫ਼ ਲੌਕ ਸਕ੍ਰੀਨ", "ਚੰਨ, ਸ਼ਨੀ, ਨੈਬੂਲਾ ਖੋਜੋ...", "ਖਗੋਲ ਲਾਇਬ੍ਰੇਰੀ", "ਚੰਨ ਫੋਟੋਗ੍ਰਾਫੀ", "ਸਮਾਰਟਫ਼ੋਨ", "ਕੈਮਰਾ / DSLR", "ਸੰਭਾਲੇ ਪਸੰਦੀਦਾ", "ਅਜੇ ਕੋਈ ਪਸੰਦੀਦਾ ਨਹੀਂ",
            "ਸੈਟਿੰਗਾਂ", "ਨਿੱਜੀਕਰਨ", "ਖਗੋਲ ਵਿਗਿਆਨ", "ਵਿਜ਼ੂਅਲ ਅਨੁਭਵ", "ਐਪ", "ਬਾਰੇ", "ਨਿਰਮਾਤਾ",
            "ਭਾਸ਼ਾ", "50 ਭਾਸ਼ਾਵਾਂ ਖੋਜੋ...", "ਦਿੱਖ", "ਫ਼ੌਂਟ ਸ਼ੈਲੀ", "ਸਥਾਨ", "ਆਟੋਮੈਟਿਕ GPS", "ਇਕਾਈਆਂ ਅਤੇ ਸਮਾਂ", "ਸੂਚਨਾਵਾਂ", "ਕੈਲੰਡਰ ਪਸੰਦ", "ਨਿਰੀਖਣ ਪਸੰਦ", "ਹੋਮ ਸਕ੍ਰੀਨ ਵਿਜੇਟ",
            "ਸ਼ੁਰੂਆਤੀ ਐਨੀਮੇਸ਼ਨ", "ਦੁਬਾਰਾ ਚਲਾਓ", "ਇੰਟਰੋ ਚੰਨ ਸ਼ੈਲੀ", "ਵਾਲਪੇਪਰ ਕੁਆਲਿਟੀ", "ਮੋਸ਼ਨ ਘਟਾਓ", "ਡੇਟਾ ਅਤੇ ਸਟੋਰੇਜ", "ਕੈਸ਼ ਸਾਫ਼ ਕਰੀਏ?", "ਕੈਸ਼ ਸਾਫ਼ ਕਰੋ", "ਗੋਪਨੀਯਤਾ", "ਫ਼ੀਡਬੈਕ", "LUNAR ਬਾਰੇ", "ਨਿਰਮਾਤਾ",
            "ਐਪ ਦੀ ਭਾਸ਼ਾ ਬਦਲਣੀ ਹੈ?", "ਨਵੀਂ ਭਾਸ਼ਾ ਲਾਗੂ ਕਰਨ ਲਈ LUNAR ਨੂੰ ਰੀਸਟਾਰਟ ਕਰਨਾ ਪਵੇਗਾ।\n\nਰੀਸਟਾਰਟ ਤੋਂ ਬਾਅਦ ਤੁਹਾਡੀ ਚੁਣੀ ਭਾਸ਼ਾ ਲਾਗੂ ਹੋ ਜਾਵੇਗੀ।", "ਬਦਲੋ ਅਤੇ ਰੀਸਟਾਰਟ ਕਰੋ", "ਕਲਾਤਮਕ ਇੰਟਰੋ ਰੂਪ"
        ),

        // 11. German
        "de" to LunarStrings(
            "DER MOND, SO NAH WIE NIE.", "Start", "Entdecken", "Kalender", "Heute Nacht", "Einstellungen",
            "Zurück", "Abbrechen", "Fertig", "Schließen", "Speichern", "Teilen", "Suchen", "Aktualisieren", "Zurücksetzen", "Überspringen", "Alle", "Heute", "Erlauben", "Leeren", "Favoriten", "Keine Ergebnisse",
            "Neumond", "Zunehmende Sichel", "Erstes Viertel", "Zunehmender Mond", "Vollmond", "Abnehmender Mond", "Letztes Viertel", "Abnehmende Sichel",
            "Beleuchtet", "Mondalter", "Zunehmend", "Abnehmend", "Mondaufgang", "Monduntergang", "Entfernung", "Höhe jetzt", "Über Horizont", "Unter Horizont", "Nächste Phase", "T",
            "Heutige Mondphase", "Nächstes Ereignis", "Beobachtung heute Nacht", "Mond entdecken", "Wusstest du schon?", "Weiterer Fakt", "Entdeckung", "Mondfotografie", "Hintergründe", "Alle Ereignisse", "Vollständiger Guide", "Mondatlas", "Bibliothek",
            "Mondatlas & Kosmos", "Interaktive Mondoberfläche, Planeten, Sternbilder & Deep Sky", "Mondatlas", "Krateratlas", "Mondmeere", "Berge & Täler", "Mondpole", "Rillen & Kanäle", "Landeplätze", "Mondgeologie",
            "Planeten", "Sternbilder", "Deep Sky", "Meteorschauer", "Finsternisse", "Raumfahrtmissionen", "Milchstraße", "Astronomiewissen", "Beschriftung", "Ausgeblendet", "Drehen",
            "Mondkalender", "Monatshighlights", "Was ist heute Nacht sichtbar?", "Berechnete Astronomie", "Wetter & Wolken", "Dämmerung & Dunkelheit", "Himmelsobjekte heute", "Sichtbar", "Stadt", "Stadt weltweit suchen...",
            "Astronomie-Wallpaper", "Als Hintergrund", "Start- & Sperrbildschirm", "Nur Startbildschirm", "Nur Sperrbildschirm", "Mond, Saturn, Nebel suchen...", "Astronomie-Bibliothek", "Mondfotografie", "Smartphone", "Kamera / DSLR", "Gespeicherte Favoriten", "Noch keine Favoriten",
            "Einstellungen", "PERSONALISIERUNG", "ASTRONOMIE", "VISUELLES", "APP", "ÜBER", "ENTWICKLER",
            "Sprache", "50 Sprachen durchsuchen...", "Erscheinungsbild", "Schriftart", "Standort", "Automatisches GPS", "Einheiten & Zeit", "Benachrichtigungen", "Kalendereinstellungen", "Beobachtung", "Startbildschirm-Widgets",
            "Startanimation", "Intro erneut abspielen", "Intro-Mondstil", "Wallpaper-Qualität", "Bewegung reduzieren", "Daten & Speicher", "Cache leeren?", "Cache leeren", "Datenschutz", "Feedback", "Über LUNAR", "Erstellt von",
            "App-Sprache ändern?", "LUNAR muss neu gestartet werden, um die neue Sprache anzuwenden.\n\nDeine Auswahl wird nach dem Neustart aktiv.", "Ändern & neu starten", "KÜNSTLERISCHE VARIANTE"
        ),

        // 12. Javanese
        "jv" to LunarStrings(
            "REMBULAN, LUWIH CEDHAK TINIMBANG SADURUNGE.", "Ngarep", "Jelajah", "Kalender", "Bengi Iki", "Setelan",
            "Bali", "Batal", "Rampung", "Tutup", "Simpen", "Bagèkaké", "Golèk", "Muat Ulang", "Reset", "Liwati", "Kabèh", "Dina Iki", "Idinaké", "Resiki", "Favorit", "Ora ana asil",
            "Wulan Anyar", "Sabit Mundhak", "Seprapat Kapisan", "Cembung Mundhak", "Purnama", "Cembung Suda", "Seprapat Pungkasan", "Sabit Suda",
            "Padhang", "Umur Wulan", "Mundhak", "Suda", "Wulan Mlethu", "Wulan Surup", "Jarak", "Dhuwur Saiki", "Ndhuwur cakrawala", "Ngisor cakrawala", "Fase Sabanjuré", "d",
            "Pandhuan Fase Dina Iki", "Prastawa Wulan Sabanjuré", "Pangamatan Bengi Iki", "Jelajah Rembulan", "Apa Kowé Ngerti?", "Fakta Liyané", "Panemuan Utama", "Fotografi Wulan", "Wallpaper", "Kabèh Prastawa", "Pandhuan Lengkap", "Atlas Wulan", "Pustaka",
            "Atlas Wulan & Jagad Raya", "Peta rembulan interaktif, planèt, rasi lintang lan ruang jero", "Atlas Wulan", "Atlas Kawah", "Segara Wulan", "Gunung & Lembah", "Kutub Wulan", "Saluran Lava", "Papan Pandharatan", "Géologi Wulan",
            "Planèt", "Rasi Lintang", "Ruang Jero", "Udan Meteor", "Grahana", "Misi Antariksa", "Bima Sakti", "Kawruh Astronomi", "Label", "Didhelikaké", "Puter",
            "Kalender Wulan", "Sorotan Sasi", "Apa Sing Katon Bengi Iki?", "Itungan Astronomi", "Cuaca & Mega", "Wektu Peteng", "Objèk Langit", "Katon", "Kutha", "Golèk kutha...",
            "Wallpaper Astronomi", "Pasang Wallpaper", "Layar Ngarep & Kunci", "Mung Layar Ngarep", "Mung Layar Kunci", "Golèk Rembulan, Saturnus...", "Pustaka Astronomi", "Fotografi Wulan", "Ponsel", "Kamera DSLR", "Favorit Kasimpen", "Durung Ana Favorit",
            "Setelan", "PERSONALISASI", "ASTRONOMI", "TAMPILAN VISUAL", "APLIKASI", "BABAGAN", "PANGGAGAWE",
            "Basa", "Golèk 50 basa...", "Tampilan", "Jinis Huruf", "Lokasi", "GPS Otomatis", "Satuan & Wektu", "Kabar", "Preferensi Kalender", "Preferensi Pengamatan", "Widget Layar",
            "Animasi Pambuka", "Puter Manèh Intro", "Gaya Wulan Intro", "Kualitas Wallpaper", "Kurangi Gerakan", "Data & Panyimpenan", "Resiki Cache?", "Resiki Cache", "Privasi", "Umpan Balik", "Babagan LUNAR", "Digawé déning",
            "Ganti Basa Aplikasi?", "LUNAR kudu diwiwiti manèh kanggo ngetrapaké basa anyar.\n\nBasa pilihanmu bakal aktif sawisé aplikasi dibukak manèh.", "Ganti & Wiwiti Manèh", "VARIAN ARTISIK"
        ),

        // 13. Korean
        "ko" to LunarStrings(
            "달을 그 어느 때보다 가까이.", "홈", "탐험", "달력", "오늘 밤", "설정",
            "뒤로", "취소", "완료", "닫기", "저장", "공유", "검색", "새로고침", "초기화", "건너뛰기", "전체", "오늘", "허용", "지우기", "즐겨찾기", "검색 결과 없음",
            "삭 (신월)", "초승달", "상현달", "차오르는 달", "보름달", "기우는 달", "하현달", "그믐달",
            "밝기", "월령", "차오름", "기움", "월출", "월몰", "거리", "현재 고도", "지평선 위", "지평선 아래", "다음 위상", "일",
            "오늘의 달 위상", "다음 주요 천문 현상", "오늘 밤 관측 조건", "달 표면 탐험", "알고 계셨나요?", "다른 상식", "주요 발견", "달 사진 가이드", "배경화면", "전체 이벤트", "전체 가이드", "달 지도", "천문 도서관",
            "달 지도 & 우주 백과", "인터랙티브 달 표면, 행성, 별자리 및 딥스카이", "달 지도", "크레이터 도감", "달의 바다", "산맥과 계곡", "달의 극지방", "월계와 협곡", "역사적 착륙지", "달 지질학",
            "행성", "별자리", "딥스카이", "유성우", "일식·월식", "우주 탐사선", "은하수", "천문학 지식", "라벨", "숨김", "회전",
            "달 위상 달력", "이달의 주요 일정", "오늘 밤 하늘에 무엇이 보일까?", "천문 계산 데이터", "날씨 및 구름량", "박명 및 관측 시간", "오늘 밤 천체", "관측 가능", "도시", "전 세계 도시 검색...",
            "천문 배경화면", "배경화면 설정", "홈 및 잠금화면", "홈 화면만", "잠금화면만", "달, 토성, 성운 검색...", "천문 도서관", "달 사진 촬영", "스마트폰", "카메라 / DSLR", "저장된 즐겨찾기", "저장된 항목이 없습니다",
            "설정", "개인화", "천문학", "시각 경험", "앱", "정보", "제작자",
            "언어", "50개 언어 검색...", "테마", "글꼴 스타일", "위치", "자동 GPS 위치", "단위 및 시간", "알림", "달력 설정", "관측 설정", "홈 화면 위젯",
            "오프닝 애니메이션", "인트로 다시 보기", "인트로 달 스타일", "배경화면 화질", "모션 줄이기", "데이터 및 저장공간", "캐시를 지우시겠습니까?", "캐시 삭제", "개인정보 및 권한", "피드백", "LUNAR 정보", "제작",
            "앱 언어를 변경할까요?", "새 언어를 적용하려면 LUNAR를 다시 시작해야 합니다.\n\n재시작 후 선택한 언어가 적용됩니다.", "변경 및 재시작", "아티스틱 인트로"
        ),

        // 14. French
        "fr" to LunarStrings(
            "LA LUNE, PLUS PROCHE QUE JAMAIS.", "Accueil", "Explorer", "Calendrier", "Ce soir", "Paramètres",
            "Retour", "Annuler", "Terminé", "Fermer", "Enregistrer", "Partager", "Rechercher", "Actualiser", "Réinitialiser", "Passer", "Tout", "Aujourd'hui", "Autoriser", "Effacer", "Favoris", "Aucun résultat",
            "Nouvelle Lune", "Premier Croissant", "Premier Quartier", "Gibbeuse Croissante", "Pleine Lune", "Gibbeuse Décroissante", "Dernier Quartier", "Dernier Croissant",
            "Illuminée", "Âge lunaire", "Croissante", "Décroissante", "Lever lunaire", "Coucher lunaire", "Distance", "Altitude actuelle", "Au-dessus de l'horizon", "Sous l'horizon", "Phase suivante", "j",
            "Phase lunaire du jour", "Prochain événement", "Observation ce soir", "Explorer la Lune", "Le saviez-vous ?", "Autre fait", "Découverte", "Photographie lunaire", "Fonds d'écran", "Tous les événements", "Guide complet", "Atlas lunaire", "Bibliothèque",
            "Atlas Lunaire & Cosmos", "Surface lunaire interactive, planètes, constellations et ciel profond", "Atlas Lunaire", "Atlas des Cratères", "Mers Lunaires", "Montagnes & Vallées", "Pôles Lunaires", "Rilles & Canaux", "Sites d'Alunissage", "Géologie Lunaire",
            "Planètes", "Constellations", "Ciel Profond", "Pluies de Météores", "Éclipses", "Missions Spatiales", "Voie Lactée", "Astronomie", "Libellés", "Masqué", "Pivoter",
            "Calendrier Lunaire", "Temps forts du mois", "Que voir dans le ciel ce soir ?", "Astronomie calculée", "Météo et nébulosité", "Crépuscule et nuit noire", "Objets célestes", "Visible", "Ville", "Rechercher une ville...",
            "Fonds d'écran Astronomie", "Définir comme fond", "Accueil et verrouillage", "Écran d'accueil", "Écran de verrouillage", "Rechercher Lune, Saturne, Nébuleuse...", "Bibliothèque", "Photographie Lunaire", "Smartphone", "Appareil Photo", "Favoris enregistrés", "Aucun favori pour l'instant",
            "Paramètres", "PERSONNALISATION", "ASTRONOMIE", "EXPÉRIENCE VISUELLE", "APPLICATION", "À PROPOS", "CRÉATEUR",
            "Langue", "Rechercher parmi 50 langues...", "Apparence", "Police", "Localisation", "GPS automatique", "Unités et heure", "Notifications", "Calendrier", "Observation", "Widgets d'accueil",
            "Animation d'ouverture", "Rejouer l'introduction", "Style lunaire d'intro", "Qualité des fonds", "Réduire les animations", "Données et stockage", "Vider le cache ?", "Vider le cache", "Confidentialité", "Commentaires", "À propos de LUNAR", "Créé par",
            "Changer la langue ?", "LUNAR doit redémarrer pour appliquer votre nouvelle langue.\n\nLa langue sélectionnée s'appliquera après le redémarrage.", "Changer et redémarrer", "VARIANTE ARTISTIQUE"
        ),

        // 15. Telugu
        "te" to LunarStrings(
            "చంద్రుడు, మునుపెన్నడూ లేనంత దగ్గరగా.", "హోమ్", "అన్వేషించు", "క్యాలెండర్", "ఈ రాత్రి", "సెట్టింగ్స్",
            "వెనుకకు", "రద్దు", "పూర్తయింది", "మూసివేయి", "సేవ్ చేయి", "షేర్ చేయి", "వెతుకు", "రిఫ్రెష్", "రీసెట్", "దాటవేయి", "అన్నీ", "ఈ రోజు", "అనుమతించు", "క్లియర్", "ఇష్టమైనవి", "ఫలితాలు లేవు",
            "అమావాస్య", "శుక్ల చంద్రవంక", "మొదటి పాదం", "శుక్ల పక్షం", "పౌర్ణమి", "కృష్ణ పక్షం", "చివరి పాదం", "కృష్ణ చంద్రవంక",
            "వెలుతురు", "చంద్ర వయస్సు", "పెరుగుతున్న", "తగ్గుతున్న", "చంద్రోదయం", "చంద్రాస్తమయం", "దూరం", "ప్రస్తుత ఎత్తు", "క్షితిజం పైన", "క్షితిజం క్రింద", "తదుపరి దశ", "రో",
            "నేటి చంద్ర దశ", "తదుపరి ప్రధాన సంఘటన", "ఈ రాత్రి ఆకాశం", "చంద్రుడిని అన్వేషించండి", "మీకు తెలుసా?", "మరో విషయం", "ప్రధాన ఆవిష్కరణ", "చంద్ర ఫోటోగ్రఫీ", "వాల్‌పేపర్లు", "అన్ని సంఘటనలు", "పూర్తి గైడ్", "చంద్ర అట్లాస్", "విజ్ఞాన గ్రంథాలయం",
            "చంద్ర అట్లాస్ & విశ్వం", "ఇంటరాక్టివ్ చంద్ర ఉపరితలం, గ్రహాలు, నక్షత్రరాశులు & అంతరిక్షం", "చంద్ర అట్లాస్", "క్రేటర్ అట్లాస్", "చంద్ర మారియా", "పర్వతాలు & లోయలు", "చంద్ర ధ్రువాలు", "కాలువలు", "ల్యాండింగ్ సైట్లు", "చంద్ర భూగర్భ శాస్త్రం",
            "గ్రహాలు", "నక్షత్రరాశులు", "లోతైన అంతరిక్షం", "ఉల్కాపాతం", "గ్రహణాలు", "అంతరిక్ష యాత్రలు", "పాలపుంత", "ఖగోళ విజ్ఞానం", "లేబుల్స్", "దాచినవి", "తిప్పు",
            "చంద్ర క్యాలెండర్", "నెల విశేషాలు", "ఈ రాత్రి ఆకాశంలో ఏమి చూడవచ్చు?", "ఖగోళ గణనలు", "వాతావరణం & మేఘాలు", "చీకటి సమయం", "ఈ రాత్రి ఖగోళ వస్తువులు", "కనిపిస్తుంది", "నగరం", "ఏ నగరాన్నైనా వెతకండి...",
            "ఖగోళ వాల్‌పేపర్లు", "వాల్‌పేపర్ సెట్ చేయి", "హోమ్ & లాక్ స్క్రీన్", "హోమ్ స్క్రీన్ మాత్రమే", "లాక్ స్క్రీన్ మాత్రమే", "చంద్రుడు, శని, నెబ్యులా...", "ఖగోళ గ్రంథాలయం", "చంద్ర ఫోటోగ్రఫీ", "స్మార్ట్‌ఫోన్", "కెమెరా / DSLR", "సేవ్ చేసినవి", "ఇష్టమైనవి ఇంకా లేవు",
            "సెట్టింగ్స్", "వ్యక్తిగతీకరణ", "ఖగోళ శాస్త్రం", "విజువల్ అనుభవం", "యాప్", "గురించి", "సృష్టికర్త",
            "భాష", "50 భాషలలో వెతకండి...", "రూపం", "ఫాంట్ శైలి", "స్థానం", "ఆటో GPS", "యూనిట్లు & సమయం", "నోటిఫికేషన్లు", "క్యాలెండర్ సెట్టింగ్స్", "పరిశీలన సెట్టింగ్స్", "హోమ్ విడ్జెట్లు",
            "ప్రారంభ యానిమేషన్", "మళ్ళీ ప్లే చేయి", "ఇంట్రో చంద్ర శైలి", "వాల్‌పేపర్ నాణ్యత", "మోషన్ తగ్గించు", "డేటా & స్టోరేజ్", "క్యాష్ క్లియర్ చేయాలా?", "క్యాష్ క్లియర్", "గోప్యత", "అభిప్రాయం", "LUNAR గురించి", "రూపకర్త",
            "యాప్ భాష మార్చాలా?", "కొత్త భాషను వర్తింపజేయడానికి LUNAR రీస్టార్ట్ కావాలి.\n\nరీస్టార్ట్ తర్వాత మీ ఎంచుకున్న భాష కనిపిస్తుంది.", "మార్చి రీస్టార్ట్ చేయి", "కళాత్మక ఇంట్రో"
        ),

        // 16. Marathi
        "mr" to LunarStrings(
            "चंद्र, पूर्वीपेक्षा अधिक जवळ.", "मुख्यपृष्ठ", "शोध", "कॅलेंडर", "आज रात्री", "सेटिंग्ज",
            "मागे", "रद्द करा", "पूर्ण", "बंद करा", "जतन करा", "शेअर करा", "शोधा", "रिफ्रेश", "रीसेट", "वगळा", "सर्व", "आज", "परवानगी द्या", "साफ करा", "आवडते", "कोणताही निकाल नाही",
            "अमावास्या", "शुक्ल चंद्रकोर", "प्रथम चतुर्थांश", "शुक्ल पक्ष", "पौर्णिमा", "कृष्ण पक्ष", "अंतिम चतुर्थांश", "कृष्ण चंद्रकोर",
            "प्रकाशमान", "चंद्र वय", "शुक्ल", "कृष्ण", "चंद्रोदय", "चंद्रास्त", "अंतर", "सध्याची उंची", "क्षितिजावर", "क्षितिजाखाली", "पुढील कला", "दि",
            "आजची चंद्रकला", "पुढील प्रमुख घटना", "आज रात्रीचे आकाश", "चंद्राचा शोध घ्या", "तुम्हाला माहित आहे का?", "आणखी माहिती", "विशेष शोध", "चंद्र छायाचित्रण", "वॉलपेपर", "सर्व घटना", "संपूर्ण मार्गदर्शक", "चंद्र अ‍ॅटलास", "ज्ञान ग्रंथालय",
            "चंद्र अ‍ॅटलास व विश्व", "परस्परसंवादी चंद्रपृष्ठ, ग्रह, तारामंडळे व खोल अंतराळ", "चंद्र अ‍ॅटलास", "विवर अ‍ॅटलास", "चंद्र मारिया", "पर्वत व दऱ्या", "चंद्र ध्रुव", "दऱ्या व प्रवाह", "ऐतिहासिक अवतरण स्थळे", "चंद्र भूशास्त्र",
            "ग्रह", "तारामंडळे", "खोल अंतराळ", "उल्कावर्षाव", "ग्रहणे", "अंतराळ मोहिमा", "आकाशगंगा", "खगोल ज्ञान", "लेबले", "लपवलेले", "फिरवा",
            "चंद्र दिनदर्शिका", "महिन्याचे विशेष", "आज रात्री आकाशात काय दिसेल?", "खगोलशास्त्रीय गणना", "हवामान व ढग", "संधिप्रकाश वेळ", "आजचे खगोल पिंड", "दृश्यमान", "शहर", "कोणतेही शहर शोधा...",
            "खगोल वॉलपेपर", "वॉलपेपर सेट करा", "होम आणि लॉक स्क्रीन", "फक्त होम स्क्रीन", "फक्त लॉक स्क्रीन", "चंद्र, शनी, नेब्युला शोधा...", "खगोल ग्रंथालय", "चंद्र छायाचित्रण", "स्मार्टफोन", "कॅमेरा / DSLR", "आवडते", "अद्याप आवडते नाहीत",
            "सेटिंग्ज", "वैयक्तिकरण", "खगोलशास्त्र", "दृश्य अनुभव", "अ‍ॅप", "विषयी", "निर्माता",
            "भाषा", "50 भाषा शोधा...", "थीम", "फॉन्ट शैली", "स्थान", "स्वयंचलित GPS", "एकके व वेळ", "सूचना", "कॅलेंडर प्राधान्ये", "निरीक्षण प्राधान्ये", "होम स्क्रीन विजेट",
            "प्रारंभिक अ‍ॅनिमेशन", "पुन्हा पहा", "इंट्रो चंद्र शैली", "वॉलपेपर गुणवत्ता", "हालचाल कमी करा", "डेटा व स्टोरेज", "कॅशे साफ करायचा?", "कॅशे साफ करा", "गोपनीयता", "अभिप्राय", "LUNAR विषयी", "निर्मिती",
            "अ‍ॅपची भाषा बदलायची?", "नवीन भाषा लागू करण्यासाठी LUNAR पुन्हा सुरू करावे लागेल.\n\nरीस्टार्टनंतर निवडलेली भाषा दिसेल.", "बदला आणि रीस्टार्ट करा", "कलात्मक दृश्य"
        ),

        // 17. Turkish
        "tr" to LunarStrings(
            "AY, HİÇ OLMADIĞI KADAR YAKIN.", "Ana Sayfa", "Keşfet", "Takvim", "Bu Gece", "Ayarlar",
            "Geri", "İptal", "Tamam", "Kapat", "Kaydet", "Paylaş", "Ara", "Yenile", "Sıfırla", "Atla", "Tümü", "Bugün", "İzin Ver", "Temizle", "Favoriler", "Sonuç bulunamadı",
            "Yeni Ay", "Hilal (Büyüyen)", "İlk Dördün", "Şişkin Ay (Büyüyen)", "Dolunay", "Şişkin Ay (Küçülen)", "Son Dördün", "Hilal (Küçülen)",
            "Aydınlanma", "Ay Yaşı", "Büyüyen", "Küçülen", "Ay Doğuşu", "Ay Batışı", "Mesafe", "Mevcut Yükseklik", "Ufuk üstünde", "Ufuk altında", "Sonraki Evre", "g",
            "Bugünün Ay Evresi", "Sonraki Önemli Olay", "Bu Gece Gözlem", "Ay'ı Keşfet", "Biliyor muydunuz?", "Başka Bilgi", "Öne Çıkan Keşif", "Ay Fotoğrafçılığı", "Duvar Kağıtları", "Tüm Olaylar", "Tam Rehber", "Ay Atlası", "Kütüphane",
            "Ay Atlası ve Kozmos", "Etkileşimli Ay yüzeyi, gezegenler, takımyıldızlar ve derin uzay", "Ay Atlası", "Krater Atlası", "Ay Denizleri", "Dağlar ve Vadiler", "Ay Kutupları", "Kanallar", "İniş Noktaları", "Ay Jeolojisi",
            "Gezegenler", "Takımyıldızlar", "Derin Uzay", "Meteor Yağmurları", "Tutulmalar", "Uzay Görevleri", "Samanyolu", "Astronomi Bilgisi", "Etiketler", "Gizli", "Döndür",
            "Ay Takvimi", "Ayın Öne Çıkanları", "Bu Gece Gökyüzünde Ne Var?", "Hesaplanan Astronomi", "Hava ve Bulutluluk", "Alacakaranlık Penceresi", "Bu Geceki Gök Cisimleri", "Görünür", "Şehir", "Dünya genelinde şehir ara...",
            "Astronomi Duvar Kağıtları", "Duvar Kağıdı Yap", "Ana ve Kilit Ekranı", "Yalnızca Ana Ekran", "Yalnızca Kilit Ekranı", "Ay, Satürn, Bulutsu ara...", "Astronomi Kütüphanesi", "Ay Fotoğrafçılığı", "Akıllı Telefon", "Kamera / DSLR", "Kayıtlı Favoriler", "Henüz favori yok",
            "Ayarlar", "KİŞİSELLEŞTİRME", "ASTRONOMİ", "GÖRSEL DENEYİM", "UYGULAMA", "HAKKINDA", "GELİŞTİRİCİ",
            "Dil", "50 dilde ara...", "Görünüm", "Yazı Tipi", "Konum", "Otomatik GPS", "Birimler ve Saat", "Bildirimler", "Takvim Tercihleri", "Gözlem Tercihleri", "Ana Ekran Widget'ları",
            "Açılış Animasyonu", "Açılışı Tekrar Oynat", "Açılış Ay Stili", "Duvar Kağıdı Kalitesi", "Hareketi Azalt", "Veri ve Depolama", "Önbellek temizlensin mi?", "Önbelleği Temizle", "Gizlilik ve İzinler", "Geri Bildirim", "LUNAR Hakkında", "Geliştirici",
            "Uygulama Dili Değiştirilsin mi?", "Yeni dilin uygulanması için LUNAR yeniden başlatılmalıdır.\n\nSeçtiğiniz dil yeniden başlatıldıktan sonra aktif olacaktır.", "Değiştir ve Yeniden Başlat", "SANATSAL GİRİŞ"
        ),

        // 18. Tamil
        "ta" to LunarStrings(
            "நிலவு, முன்பை விட மிக அருகில்.", "முகப்பு", "ஆராய்", "நாட்காட்டி", "இன்றிரவு", "அமைப்புகள்",
            "பின்", "ரத்து", "முடிந்தது", "மூடு", "சேமி", "பகிர்", "தேடு", "புதுப்பி", "மீட்டமை", "தவிர்", "அனைத்தும்", "இன்று", "அனுமதி", "அழி", "பிடித்தவை", "முடிவுகள் இல்லை",
            "அமாவாசை", "வளர்பிறை", "முதல் கால்", "வளர் நிலவு", "பௌர்ணமி", "தேய் நிலவு", "கடைசி கால்", "தேய்பிறை",
            "ஒளிர்வு", "நிலவு வயது", "வளர்பிறை", "தேய்பிறை", "நிலவு உதயம்", "நிலவு மறைவு", "தூரம்", "தற்போதைய உயரம்", "தொடுவானத்திற்கு மேல்", "தொடுவானத்திற்கு கீழ்", "அடுத்த நிலை", "நா",
            "இன்றைய நிலவு நிலை", "அடுத்த முக்கிய நிகழ்வு", "இன்றிரவு வானம்", "நிலவை ஆராய்க", "உங்களுக்குத் தெரியுமா?", "மற்றொரு தகவல்", "சிறப்பு கண்டுபிடிப்பு", "நிலவு புகைப்படம்", "வால்பேப்பர்கள்", "அனைத்து நிகழ்வுகள்", "முழு வழிகாட்டி", "நிலவு வரைபடம்", "வானியல் நூலகம்",
            "நிலவு வரைபடம் & பிரபஞ்சம்", "ஊடாடும் நிலவு மேற்பரப்பு, கோள்கள், விண்மீன் கூட்டங்கள்", "நிலவு வரைபடம்", "பள்ளங்கள்", "நிலவு மாரியா", "மலைகள் & பள்ளத்தாக்குகள்", "நிலவு துருவங்கள்", "கால்வாய்கள்", "தரையிறங்கிய இடங்கள்", "நிலவு புவியியல்",
            "கோள்கள்", "விண்மீன் கூட்டங்கள்", "ஆழமான விண்வெளி", "விண்கல் பொழிவு", "கிரகணங்கள்", "விண்வெளிப் பயணங்கள்", "பால்வெளி", "வானியல் அறிவு", "பெயர்கள்", "மறைந்தது", "சுழற்று",
            "நிலவு நாட்காட்டி", "மாத சிறப்புகள்", "இன்றிரவு வானில் என்ன பார்க்கலாம்?", "கணிக்கப்பட்ட வானியல்", "வானிலை & மேகங்கள்", "இருள் நேரம்", "இன்றிரவு விண்பொருட்கள்", "தெரியும்", "நகரம்", "உலக நகரங்களைத் தேடு...",
            "வானியல் வால்பேப்பர்கள்", "வால்பேப்பராக அமை", "முகப்பு & பூட்டு திரை", "முகப்பு திரை மட்டும்", "பூட்டு திரை மட்டும்", "நிலவு, சனி, நெபுலா தேடு...", "வானியல் நூலகம்", "நிலவு புகைப்படம்", "ஸ்மார்ட்போன்", "கேமரா / DSLR", "சேமித்த பிடித்தவை", "பிடித்தவை இன்னும் இல்லை",
            "அமைப்புகள்", "தனிப்பயனாக்கம்", "வானியல்", "காட்சி அனுபவம்", "செயலி", "பற்றி", "உருவாக்குநர்",
            "மொழி", "50 மொழிகளில் தேடு...", "தோற்றம்", "எழுத்துரு", "இடம்", "தானியங்கி GPS", "அலகுகள் & நேரம்", "அறிவிப்புகள்", "நாட்காட்டி விருப்பங்கள்", "பார்வை விருப்பங்கள்", "முகப்பு விட்ஜெட்கள்",
            "தொடக்க அனிமேஷன்", "மீண்டும் இயக்கு", "அறிமுக நிலவு பாணி", "வால்பேப்பர் தரம்", "இயக்கத்தைக் குறை", "தரவு & சேமிப்பு", "தற்காலிக தரவை அழிக்கவா?", "தற்காலிக தரவை அழி", "தனியுரிமை", "கருத்து", "LUNAR பற்றி", "உருவாக்கியவர்",
            "செயலி மொழியை மாற்றவா?", "புதிய மொழியைப் பயன்படுத்த LUNAR மீண்டும் தொடங்க வேண்டும்.\n\nமீண்டும் தொடங்கிய பிறகு உங்கள் மொழி செயல்படும்.", "மாற்றி மீண்டும் தொடங்கு", "கலை அறிமுக பாணி"
        ),

        // 19. Vietnamese
        "vi" to LunarStrings(
            "MẶT TRĂNG, GẦN HƠN BAO GIỜ HẾT.", "Trang chủ", "Khám phá", "Lịch trăng", "Đêm nay", "Cài đặt",
            "Quay lại", "Hủy", "Xong", "Đóng", "Lưu", "Chia sẻ", "Tìm kiếm", "Làm mới", "Đặt lại", "Bỏ qua", "Tất cả", "Hôm nay", "Cho phép", "Xóa", "Yêu thích", "Không có kết quả",
            "Trăng Non", "Trăng Lưỡi Liềm Đầu Tháng", "Bán Nguyệt Đầu Tháng", "Trăng Khuyết Đầu Tháng", "Trăng Tròn", "Trăng Khuyết Cuối Tháng", "Bán Nguyệt Cuối Tháng", "Trăng Lưỡi Liềm Cuối Tháng",
            "Độ sáng", "Tuổi trăng", "Trăng mọc lớn", "Trăng khuyết dần", "Trăng mọc", "Trăng lặn", "Khoảng cách", "Độ cao hiện tại", "Trên đường chân trời", "Dưới đường chân trời", "Pha tiếp theo", "n",
            "Pha trăng hôm nay", "Sự kiện sắp tới", "Quan sát đêm nay", "Khám phá Mặt Trăng", "Bạn có biết?", "Sự thật khác", "Khám phá nổi bật", "Nhiếp ảnh Mặt Trăng", "Hình nền", "Tất cả sự kiện", "Hướng dẫn đầy đủ", "Bản đồ Mặt Trăng", "Thư viện Thiên văn",
            "Bản Đồ Mặt Trăng & Vũ Trụ", "Bề mặt Mặt Trăng tương tác, hành tinh, chòm sao & vũ trụ sâu", "Bản đồ Mặt Trăng", "Hố thiên thạch", "Biển Mặt Trăng", "Núi & Thung lũng", "Hai cực Mặt Trăng", "Rãnh dung nham", "Điểm đổ bộ lịch sử", "Địa chất Mặt Trăng",
            "Hành tinh", "Chòm sao", "Vũ trụ sâu", "Mưa sao băng", "Nhật & Nguyệt thực", "Sứ mệnh Không gian", "Dải Ngân Hà", "Kiến thức Thiên văn", "Nhãn", "Đã ẩn", "Xoay",
            "Lịch Mặt Trăng", "Nổi bật trong tháng", "Đêm nay xem gì trên bầu trời?", "Thiên văn tính toán", "Thời tiết & Mây", "Cửa sổ trời tối", "Thiên thể đêm nay", "Quan sát được", "Thành phố", "Tìm thành phố bất kỳ...",
            "Hình nền Thiên văn", "Đặt hình nền", "Màn hình chính & khóa", "Chỉ màn hình chính", "Chỉ màn hình khóa", "Tìm Mặt Trăng, Sao Thổ, Tinh vân...", "Thư viện Thiên văn", "Chụp ảnh Mặt Trăng", "Điện thoại", "Máy ảnh DSLR", "Mục đã lưu", "Chưa có mục yêu thích",
            "Cài đặt", "CÁ NHÂN HÓA", "THIÊN VĂN HỌC", "TRẢI NGHIỆM HÌNH ẢNH", "ỨNG DỤNG", "GIỚI THIỆU", "NHÀ PHÁT TRIỂN",
            "Ngôn ngữ", "Tìm trong 50 ngôn ngữ...", "Giao diện", "Phông chữ", "Vị trí", "GPS tự động", "Đơn vị & Giờ", "Thông báo", "Tùy chọn lịch", "Tùy chọn quan sát", "Tiện ích màn hình chính",
            "Hoạt ảnh mở đầu", "Phát lại mở đầu", "Kiểu Mặt Trăng mở đầu", "Chất lượng hình nền", "Giảm chuyển động", "Dữ liệu & Bộ nhớ", "Xóa bộ nhớ đệm?", "Xóa bộ nhớ đệm", "Quyền riêng tư", "Phản hồi", "Giới thiệu LUNAR", "Phát triển bởi",
            "Đổi ngôn ngữ ứng dụng?", "LUNAR cần khởi động lại để áp dụng ngôn ngữ mới.\n\nNgôn ngữ bạn chọn sẽ có hiệu lực sau khi mở lại.", "Đổi & Khởi động lại", "PHIÊN BẢN NGHỆ THUẬT"
        ),

        // 20. Urdu (RTL)
        "ur" to LunarStrings(
            "چاند، پہلے سے کہیں زیادہ قریب۔", "ہوم", "دریافت", "کیلنڈر", "آج رات", "سیٹنگز",
            "واپس", "منسوخ", "مکمل", "بند کریں", "محفوظ کریں", "شیئر کریں", "تلاش کریں", "ریفریش", "ری سیٹ", "چھوڑیں", "سب", "آج", "اجازت دیں", "صاف کریں", "پسندیدہ", "کوئی نتیجہ نہیں",
            "نیا چاند", "بڑھتا ہلال", "پہلی تربیع", "بڑھتا چاند", "پورا چاند", "گھٹتا چاند", "آخری تربیع", "گھٹتا ہلال",
            "روشنی", "چاند کی عمر", "بڑھتا ہوا", "گھٹتا ہوا", "طلوعِ قمر", "غروبِ قمر", "فاصلہ", "موجودہ بلندی", "افق سے اوپر", "افق سے نیچے", "اگلا مرحلہ", "دن",
            "آج کا چاند", "اگلا اہم واقعہ", "آج رات کا آسمان", "چاند دریافت کریں", "کیا آپ جانتے ہیں؟", "مزید حقیقت", "اہم دریافت", "چاند کی فوٹوگرافی", "وال پیپرز", "تمام واقعات", "مکمل گائیڈ", "چاند کا نقشہ", "فلکیاتی کتب خانہ",
            "چاند کا نقشہ اور کائنات", "انٹرایکٹو چاند کی سطح، سیارے، برج اور گہری خلا", "چاند کا نقشہ", "گڑھے (کریٹرز)", "چاند کے سمندر", "پہاڑ اور وادیاں", "چاند کے قطبین", "لاوا چینلز", "تاریخی لینڈنگ مقامات", "قمری ارضیات",
            "سیارے", "برج (تاروں کے جھرمٹ)", "گہری خلا", "شہابِ ثاقب", "گرہن", "خلائی مشن", "کہکشاں", "فلکیاتی علم", "لیبلز", "چھپا ہوا", "گھمائیں",
            "قمری کیلنڈر", "ماہ کی اہم تاریخیں", "آج رات آسمان میں کیا نظر آئے گا؟", "فلکیاتی حساب کتاب", "موسم اور بادل", "تاریکی کا وقت", "آج رات کے اجرام", "نظر آنے والا", "شہر", "کوئی بھی شہر تلاش کریں...",
            "فلکیاتی وال پیپرز", "وال پیپر لگائیں", "ہوم اور لاک اسکرین", "صرف ہوم اسکرین", "صرف لاک اسکرین", "چاند، زحل، نیبولا تلاش کریں...", "فلکیاتی لائبریری", "چاند کی فوٹوگرافی", "اسمارٹ فون", "کیمرہ / DSLR", "محفوظ پسندیدہ", "ابھی کوئی پسندیدہ نہیں",
            "سیٹنگز", "ذاتی ترتیب", "فلکیات", "بصری تجربہ", "ایپ", "تعارف", "تخلیق کار",
            "زبان", "50 زبانیں تلاش کریں...", "تھیم", "فونٹ اسٹائل", "مقام", "خودکار GPS", "اکائیاں اور وقت", "اطلاعات", "کیلنڈر ترجیحات", "مشاہدہ ترجیحات", "ہوم اسکرین وجٹس",
            "ابتدائی اینیمیشن", "دوبارہ چلائیں", "انٹرو چاند اسٹائل", "وال پیپر کوالٹی", "حرکت کم کریں", "ڈیٹا اور اسٹوریج", "کیش صاف کریں؟", "کیش صاف کریں", "رازداری", "رائے اور مدد", "LUNAR کے بارے میں", "تخلیق کار",
            "ایپ کی زبان تبدیل کریں؟", "نئی زبان لاگو کرنے کے لیے LUNAR کو دوبارہ شروع کرنا ہوگا۔\n\nری اسٹارٹ کے بعد منتخب زبان فعال ہو جائے گی۔", "تبدیل کریں اور ری اسٹارٹ کریں", "فنی تعارفی انداز"
        ),

        // 21. Indonesian
        "id" to LunarStrings(
            "BULAN, LEBIH DEKAT DARI SEBELUMNYA.", "Beranda", "Jelajah", "Kalender", "Malam Ini", "Pengaturan",
            "Kembali", "Batal", "Selesai", "Tutup", "Simpan", "Bagikan", "Cari", "Segarkan", "Reset", "Lewati", "Semua", "Hari Ini", "Izinkan", "Hapus", "Favorit", "Tidak ada hasil",
            "Bulan Baru", "Sabit Awal", "Kuartal Pertama", "Cembung Awal", "Bulan Purnama", "Cembung Akhir", "Kuartal Akhir", "Sabit Akhir",
            "Iluminasi", "Usia Bulan", "Menuju Purnama", "Menuju Bulan Baru", "Bulan Terbit", "Bulan Terbenam", "Jarak", "Ketinggian", "Di atas cakrawala", "Di bawah cakrawala", "Fase Berikutnya", "h",
            "Panduan Fase Hari Ini", "Peristiwa Bulan Berikutnya", "Pengamatan Malam Ini", "Jelajahi Bulan", "Tahukah Anda?", "Fakta Lain", "Penemuan Unggulan", "Fotografi Bulan", "Wallpaper", "Semua Peristiwa", "Panduan Lengkap", "Atlas Bulan", "Perpustakaan",
            "Atlas Bulan & Kosmos", "Permukaan Bulan interaktif, planet, rasi bintang & ruang dalam", "Atlas Bulan", "Atlas Kawah", "Lautan Bulan", "Gunung & Lembah", "Kutub Bulan", "Saluran Lava", "Lokasi Pendaratan", "Geologi Bulan",
            "Planet", "Rasi Bintang", "Ruang Dalam", "Hujan Meteor", "Gerhana", "Misi Antariksa", "Bima Sakti", "Pengetahuan Astronomi", "Label", "Tersembunyi", "Putar",
            "Kalender Bulan", "Sorotan Bulan Ini", "Apa yang Terlihat Malam Ini?", "Astronomi Terhitung", "Cuaca & Awan", "Jendela Gelap Malam", "Objek Langit", "Terlihat", "Kota", "Cari kota di seluruh dunia...",
            "Wallpaper Astronomi", "Pasang Wallpaper", "Layar Utama & Kunci", "Layar Utama Saja", "Layar Kunci Saja", "Cari Bulan, Saturnus, Nebula...", "Perpustakaan Astronomi", "Fotografi Bulan", "Ponsel", "Kamera DSLR", "Favorit Tersimpan", "Belum ada favorit",
            "Pengaturan", "PERSONALISASI", "ASTRONOMI", "PENGALAMAN VISUAL", "APLIKASI", "TENTANG", "PEMBUAT",
            "Bahasa", "Cari 50 bahasa...", "Tampilan", "Gaya Huruf", "Lokasi", "GPS Otomatis", "Satuan & Waktu", "Notifikasi", "Preferensi Kalender", "Preferensi Pengamatan", "Widget Layar",
            "Animasi Pembuka", "Putar Ulang Intro", "Gaya Bulan Intro", "Kualitas Wallpaper", "Kurangi Gerakan", "Data & Penyimpanan", "Hapus Cache?", "Hapus Cache", "Privasi & Izin", "Masukan", "Tentang LUNAR", "Dibuat oleh",
            "Ubah Bahasa Aplikasi?", "LUNAR perlu dimulai ulang untuk menerapkan bahasa baru Anda.\n\nBahasa pilihan akan aktif setelah aplikasi dibuka kembali.", "Ubah & Mulai Ulang", "VARIAN ARTISTIK"
        ),

        // 22. Italian
        "it" to LunarStrings(
            "LA LUNA, PIÙ VICINA CHE MAI.", "Home", "Esplora", "Calendario", "Stanotte", "Impostazioni",
            "Indietro", "Annulla", "Fine", "Chiudi", "Salva", "Condividi", "Cerca", "Aggiorna", "Ripristina", "Salta", "Tutti", "Oggi", "Consenti", "Cancella", "Preferiti", "Nessun risultato",
            "Luna Nuova", "Luna Crescente", "Primo Quarto", "Gibbosa Crescente", "Luna Piena", "Gibbosa Calante", "Ultimo Quarto", "Luna Calante",
            "Illuminata", "Età lunare", "Crescente", "Calante", "Sorgere Luna", "Tramonto Luna", "Distanza", "Altitudine ora", "Sopra l'orizzonte", "Sotto l'orizzonte", "Prossima fase", "g",
            "Fase lunare di oggi", "Prossimo evento lunare", "Osservazione stanotte", "Esplora la Luna", "Lo sapevi?", "Un altro fatto", "Scoperta in evidenza", "Fotografia lunare", "Sfondi", "Tutti gli eventi", "Guida completa", "Atlante Lunare", "Biblioteca",
            "Atlante Lunare e Cosmo", "Superficie lunare interattiva, pianeti, costellazioni e spazio profondo", "Atlante Lunare", "Atlante Crateri", "Mari Lunari", "Monti e Valli", "Poli Lunari", "Rime e Canali", "Siti di Allunaggio", "Geologia Lunare",
            "Pianeti", "Costellazioni", "Spazio Profondo", "Sciami Meteorici", "Eclissi", "Missioni Spaziali", "Via Lattea", "Conoscenza Astronomica", "Etichette", "Nascoste", "Ruota",
            "Calendario Lunare", "Eventi del mese", "Cosa posso vedere stanotte?", "Astronomia calcolata", "Meteo e nuvole", "Finestra di buio", "Oggetti celesti", "Visibile", "Città", "Cerca una città...",
            "Sfondi Astronomia", "Imposta sfondo", "Home e blocco", "Solo schermata Home", "Solo schermata blocco", "Cerca Luna, Saturno, Nebulosa...", "Biblioteca Astronomica", "Fotografia Lunare", "Smartphone", "Fotocamera DSLR", "Preferiti salvati", "Nessun preferito ancora",
            "Impostazioni", "PERSONALIZZAZIONE", "ASTRONOMIA", "ESPERIENZA VISIVA", "APP", "INFO", "CREATORE",
            "Lingua", "Cerca tra 50 lingue...", "Aspetto", "Stile carattere", "Posizione", "GPS automatico", "Unità e ora", "Notifiche", "Calendario", "Osservazione", "Widget Home",
            "Animazione iniziale", "Rivedi introduzione", "Stile Luna intro", "Qualità sfondi", "Riduci movimento", "Dati e archiviazione", "Svuotare la cache?", "Svuota cache", "Privacy e permessi", "Feedback", "Info su LUNAR", "Creato da",
            "Cambiare lingua dell'app?", "LUNAR deve riavviarsi per applicare la nuova lingua.\n\nLa lingua scelta sarà attiva al riavvio.", "Cambia e riavvia", "VARIANTE ARTISTICA"
        ),

        // 23. Thai
        "th" to LunarStrings(
            "ดวงจันทร์ ใกล้กว่าที่เคยเป็นมา", "หน้าแรก", "สำรวจ", "ปฏิทิน", "คืนนี้", "ตั้งค่า",
            "กลับ", "ยกเลิก", "เสร็จสิ้น", "ปิด", "บันทึก", "แชร์", "ค้นหา", "รีเฟรช", "รีเซ็ต", "ข้าม", "ทั้งหมด", "วันนี้", "อนุญาต", "ล้าง", "รายการโปรด", "ไม่พบผลลัพธ์",
            "เดือนมืด", "ข้างขึ้นเสี้ยว", "ขึ้น 8 ค่ำ", "ข้างขึ้นค่อนดวง", "จันทร์เพ็ญ", "ข้างแรมค่อนดวง", "แรม 8 ค่ำ", "ข้างแรมเสี้ยว",
            "ความสว่าง", "อายุดวงจันทร์", "ข้างขึ้น", "ข้างแรม", "จันทร์ขึ้น", "จันทร์ตก", "ระยะทาง", "มุมเงยตอนนี้", "เหนือขอบฟ้า", "ใต้ขอบฟ้า", "ดิถีถัดไป", "วัน",
            "ดิถีจันทร์วันนี้", "ปรากฏการณ์สำคัญถัดไป", "สภาพท้องฟ้าคืนนี้", "สำรวจดวงจันทร์", "คุณรู้หรือไม่?", "เกร็ดความรู้อื่น", "การค้นพบเด่น", "การถ่ายภาพดวงจันทร์", "วอลเปเปอร์", "เหตุการณ์ทั้งหมด", "คู่มือฉบับเต็ม", "แผนที่ดวงจันทร์", "ห้องสมุดดาราศาสตร์",
            "แผนที่ดวงจันทร์และเอกภพ", "พื้นผิวดวงจันทร์แบบโต้ตอบ ดาวเคราะห์ กลุ่มดาว และห้วงอวกาศ", "แผนที่ดวงจันทร์", "หลุมอุกกาบาต", "ทะเลบนดวงจันทร์", "ภูเขาและหุบเขา", "ขั้วดวงจันทร์", "ร่องลาวา", "จุดลงจอดประวัติศาสตร์", "ธรณีวิทยาดวงจันทร์",
            "ดาวเคราะห์", "กลุ่มดาว", "ห้วงอวกาศลึก", "ฝนดาวตก", "อุปราคา", "ภารกิจอวกาศ", "ทางช้างเผือก", "ความรู้ดาราศาสตร์", "ป้ายชื่อ", "ซ่อนอยู่", "หมุน",
            "ปฏิทินจันทรคติ", "ไฮไลต์ประจำเดือน", "คืนนี้ท้องฟ้ามีอะไรให้ดู?", "ข้อมูลคำนวณทางดาราศาสตร์", "สภาพอากาศและเมฆ", "ช่วงเวลาท้องฟ้ามืด", "วัตถุท้องฟ้าคืนนี้", "มองเห็นได้", "เมือง", "ค้นหาเมืองทั่วโลก...",
            "วอลเปเปอร์ดาราศาสตร์", "ตั้งเป็นวอลเปเปอร์", "หน้าจอหลักและล็อก", "หน้าจอหลักเท่านั้น", "หน้าจอล็อกเท่านั้น", "ค้นหาดวงจันทร์ ดาวเสาร์ เนบิวลา...", "ห้องสมุดดาราศาสตร์", "คู่มือถ่ายภาพดวงจันทร์", "สมาร์ตโฟน", "กล้อง DSLR", "รายการโปรด", "ยังไม่มีรายการโปรด",
            "ตั้งค่า", "ปรับแต่ง", "ดาราศาสตร์", "ประสบการณ์ภาพ", "แอป", "เกี่ยวกับ", "ผู้สร้าง",
            "ภาษา", "ค้นหา 50 ภาษา...", "ธีมและลักษณะ", "รูปแบบฟอนต์", "ตำแหน่ง", "GPS อัตโนมัติ", "หน่วยและเวลา", "การแจ้งเตือน", "ตั้งค่าปฏิทิน", "ตั้งค่าการสังเกตการณ์", "วิดเจ็ตหน้าจอหลัก",
            "แอนิเมชันเปิดแอป", "เล่นอินโทรอีกครั้ง", "สไตล์ดวงจันทร์อินโทร", "คุณภาพวอลเปเปอร์", "ลดการเคลื่อนไหว", "ข้อมูลและที่จัดเก็บ", "ล้างแคชหรือไม่?", "ล้างแคช", "ความเป็นส่วนตัว", "ข้อเสนอแนะ", "เกี่ยวกับ LUNAR", "สร้างโดย",
            "เปลี่ยนภาษาแอปหรือไม่?", "LUNAR จำเป็นต้องรีสตาร์ตเพื่อใช้ภาษาใหม่ของคุณ\n\nภาษาที่เลือกจะมีผลหลังจากรีสตาร์ตแอป", "เปลี่ยนและรีสตาร์ต", "ภาพศิลปะเปิดแอป"
        ),

        // 24. Gujarati
        "gu" to LunarStrings(
            "ચંદ્ર, પહેલા કરતાં વધુ નજીક.", "હોમ", "અન્વેષણ", "કેલેન્ડર", "આજ રાત્રે", "સેટિંગ્સ",
            "પાછળ", "રદ કરો", "પૂર્ણ", "બંધ કરો", "સાચવો", "શેર કરો", "શોધો", "રિફ્રેશ", "રીસેટ", "છોડો", "બધા", "આજે", "મંજૂરી આપો", "સાફ કરો", "મનપસંદ", "કોઈ પરિણામ નથી",
            "અમાસ", "શુક્લ બીજ", "પ્રથમ ચતુર્થાંશ", "શુક્લ પક્ષ", "પૂનમ", "કૃષ્ણ પક્ષ", "અંતિમ ચતુર્થાંશ", "કૃષ્ણ પક્ષ ચંદ્ર",
            "પ્રકાશિત", "ચંદ્ર ઉંમર", "વધતો", "ઘટતો", "ચંદ્રોદય", "ચંદ્રાસ્ત", "અંતર", "વર્તમાન ઊંચાઈ", "ક્ષિતિજ ઉપર", "ક્ષિતિજ નીચે", "આગળની કળા", "દિ",
            "આજની ચંદ્ર કળા", "આગળની મુખ્ય ઘટના", "આજ રાતનું આકાશ", "ચંદ્રનું અન્વેષણ", "શું તમે જાણો છો?", "અન્ય હકીકત", "મુખ્ય શોધ", "ચંદ્ર ફોટોગ્રાફી", "વોલપેપર્સ", "બધી ઘટનાઓ", "સંપૂર્ણ માર્ગદર્શિકા", "ચંદ્ર એટલાસ", "ખગોળ પુસ્તકાલય",
            "ચંદ્ર એટલાસ અને બ્રહ્માંડ", "ઇન્ટરેક્ટિવ ચંદ્ર સપાટી, ગ્રહો, નક્ષત્રો અને અવકાશ", "ચંદ્ર એટલાસ", "ક્રેટર એટલાસ", "ચંદ્ર મારિયા", "પર્વતો અને ખીણો", "ચંદ્ર ધ્રુવો", "લાવા ચેનલો", "ઐતિહાસિક લેન્ડિંગ સ્થળો", "ચંદ્ર ભૂસ્તરશાસ્ત્ર",
            "ગ્રહો", "નક્ષત્રો", "ઊંડું અવકાશ", "ઉલ્કાવર્ષા", "ગ્રહણ", "અવકાશ મિશન", "આકાશગંગા", "ખગોળ જ્ઞાન", "લેબલ્સ", "છુપાયેલ", "ફેરવો",
            "ચંદ્ર કેલેન્ડર", "મહિનાની ખાસિયતો", "આજ રાત્રે આકાશમાં શું દેખાશે?", "ખગોળ ગણતરી", "હવામાન અને વાદળો", "અંધકાર સમય", "આજ રાતના પિંડો", "દૃશ્યમાન", "શહેર", "કોઈપણ શહેર શોધો...",
            "ખગોળ વોલપેપર્સ", "વોલપેપર સેટ કરો", "હોમ અને લોક સ્ક્રીન", "માત્ર હોમ સ્ક્રીન", "માત્ર લોક સ્ક્રીન", "ચંદ્ર, શનિ, નેબ્યુલા શોધો...", "ખગોળ પુસ્તકાલય", "ચંદ્ર ફોટોગ્રાફી", "સ્માર્ટફોન", "કેમેરા / DSLR", "સાચવેલ મનપસંદ", "હજી કોઈ મનપસંદ નથી",
            "સેટિંગ્સ", "વ્યક્તિગતકરણ", "ખગોળશાસ્ત્ર", "દ્રશ્ય અનુભવ", "એપ", "વિશે", "સર્જક",
            "ભાષા", "50 ભાષાઓ શોધો...", "દેખાવ", "ફોન્ટ શૈલી", "સ્થાન", "ઓટો GPS", "એકમો અને સમય", "સૂચનાઓ", "કેલેન્ડર પસંદગી", "અવલોકન પસંદગી", "હોમ સ્ક્રીન વિજેટ",
            "પ્રારંભિક એનિમેશન", "ફરી ચલાવો", "ઇન્ટ્રો ચંદ્ર શૈલી", "વોલપેપર ગુણવત્તા", "મોશન ઘટાડો", "ડેટા અને સ્ટોરેજ", "કેશ સાફ કરવી?", "કેશ સાફ કરો", "ગોપનીયતા", "પ્રતિભાવ", "LUNAR વિશે", "નિર્માતા",
            "એપની ભાષા બદલવી છે?", "નવી ભાષા લાગુ કરવા માટે LUNAR ફરી શરૂ થવું જરૂરી છે.\n\nરીસ્ટાર્ટ પછી પસંદ કરેલી ભાષા દેખાશે.", "બદલો અને રીસ્ટાર્ટ કરો", "કલાત્મક ઇન્ટ્રો"
        ),

        // 25. Persian (RTL)
        "fa" to LunarStrings(
            "ماه، نزدیک‌تر از همیشه.", "خانه", "کاوش", "تقویم", "امشب", "تنظیمات",
            "بازگشت", "لغو", "تأیید", "بستن", "ذخیره", "اشتراک‌گذاری", "جستجو", "به‌روزرسانی", "بازنشانی", "رد شدن", "همه", "امروز", "اجازه دادن", "پاک کردن", "علاقه‌مندی‌ها", "نتیجه‌ای یافت نشد",
            "ماه نو", "هلال افزاینده", "تربیع اول", "تحدب افزاینده", "ماه کامل", "تحدب کاهنده", "تربیع آخر", "هلال کاهنده",
            "روشنایی", "سن ماه", "افزاینده", "کاهنده", "طلوع ماه", "غروب ماه", "فاصله", "ارتفاع کنونی", "بالای افق", "زیر افق", "فاز بعدی", "روز",
            "راهنمای فاز امروز", "رویداد مهم بعدی", "رصد امشب", "کاوش ماه", "آیا می‌دانستید؟", "دانستنی دیگر", "کشف برگزیده", "عکاسی از ماه", "تصاویر پس‌زمینه", "همه رویدادها", "راهنمای کامل", "اطلس ماه", "کتابخانه نجوم",
            "اطلس ماه و کیهان", "سطح تعاملی ماه، سیارات، صورت‌های فلکی و اعماق فضا", "اطلس ماه", "دهانه‌های برخوردی", "دریاهای ماه", "کوه‌ها و دره‌ها", "قطب‌های ماه", "شیارها و کانال‌ها", "محل‌های فرود تاریخی", "زمین‌شناسی ماه",
            "سیارات", "صورت‌های فلکی", "اعماق فضا", "بارش‌های شهابی", "گرفتگی‌ها", "مأموریت‌های فضایی", "راه شیری", "دانش نجوم", "برچسب‌ها", "پنهان", "چرخش",
            "تقویم قمری", "رویدادهای ماه", "امشب در آسمان چه می‌بینم؟", "محاسبات نجومی", "آب‌وهوا و ابرها", "بازه تاریکی آسمان", "اجرام آسمانی امشب", "قابل رؤیت", "شهر", "جستجوی هر شهری در جهان...",
            "تصاویر پس‌زمینه نجومی", "تنظیم تصویر پس‌زمینه", "صفحه اصلی و قفل", "فقط صفحه اصلی", "فقط صفحه قفل", "جستجوی ماه، زحل، سحابی...", "کتابخانه نجوم", "عکاسی از ماه", "تلفن هوشمند", "دوربین حرفه‌ای", "علاقه‌مندی‌های ذخیره شده", "هنوز موردی ذخیره نشده",
            "تنظیمات", "شخصی‌سازی", "نجوم", "تجربه بصری", "برنامه", "درباره", "سازنده",
            "زبان", "جستجو در ۵۰ زبان...", "ظاهر", "نوع قلم", "موقعیت مکانی", "مکان‌یابی خودکار GPS", "واحدها و زمان", "اعلان‌ها", "تنظیمات تقویم", "تنظیمات رصد", "ویجت‌های صفحه اصلی",
            "انیمیشن آغازین", "پخش دوباره مقدمه", "سبک ماه در مقدمه", "کیفیت پس‌زمینه", "کاهش حرکت", "داده و حافظه", "پاک کردن حافظه پنهان؟", "پاک کردن کش", "حریم خصوصی", "بازخورد", "درباره LUNAR", "ساخته شده توسط",
            "تغییر زبان برنامه؟", "برای اعمال زبان جدید، LUNAR باید مجدداً راه‌اندازی شود.\n\nپس از راه‌اندازی مجدد، زبان انتخابی شما فعال خواهد شد.", "تغییر و راه‌اندازی مجدد", "نمای هنری مقدمه"
        )
    )
}
