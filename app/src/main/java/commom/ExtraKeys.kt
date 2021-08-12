package commom

val BASE_URL = "http://192.168.1.102/divar/"
var MY_CITY = "all"
var MY_CATEGORY = "all"
const val EXTRA_KEY_DATA = "data"

val LIST_CITY = listOf(
    "زارچ",

    "سید میرزا",

    "حمیدیه شهر",

    "شاهدیه",

    "خضرآباد",

    "هرات",

    "اشکذر",

    "مهریز",

    "بافق",

    "طبس",

    "میبد",

    "ابرکوه",

    "اردکان",

    "تفت",

    "یزد",

    "بهار",

    "اسدآباد",

    "رزن",

    "کبودر اهنگ",

    "نهاوند",

    "تویسرکان",

    "ملایر",

    "همدان",

    "قشم",

    "پارسیان",

    "بندر خمیر",

    "تنب بزرگ",

    "بندر جاسک",

    "ابوموسی",

    "میناب",

    "انگهران",

    "دهبارز",

    "حاجی آباد",

    "بستک",

    "بندر لنگه",

    "کیش",

    "قشم",

    "بندرعباس",

    "شازند",

    "محلات",

    "سربند",

    "ساوه",

    "دلیجان",

    "خمین",

    "تفرش",

    "آشتیان",

    "اراک",

    "فریدون کنار",

    "محمود آباد",

    "پل سفید",

    "نوشهر",

    "بلده",

    "نور",

    "نکا",

    "قائم شهر",

    "سواد کوه",

    "رامسر",

    "چالوس",

    "جویبار",

    "تنکابن",

    "بهشهر",

    "بابلسر",

    "بابل",

    "آمل",

    "ساری",

    "پلدختر",

    "الشتر",

    "کوهدشت",

    "نور آباد",

    "ازنا",

    "الیگودرز",

    "دورود",

    "بروجرد",

    "دزفول",

    "ماهشهر",

    "خرم آباد",

    "بندر کیاشهر",

    "لشت نشا",

    "خمام",

    "خشک بیجار",

    "لاهیجان",

    "املش",

    "سیاهکل",

    "شفت",

    "ماسال",

    "رضوان شهر",

    "هشتپر",

    "کلاچای",

    "بندرانزلی",

    "صومعه سرا",

    "فومن",

    "رودبار",

    "آستانه اشرفیه",

    "ماسوله",

    "آستارا",

    "تالش",

    "رود سر",

    "لنگرود",

    "منجیل",

    "رشت",

    "رامیان",

    "آزاد شهر",

    "کلاله",

    "بندر گز",

    "کردکوی",

    "ترکمن",

    "مینو دشت",

    "علی آباد کتول",

    "گنبد کاووس",

    "آق قلا",

    "گرگان",

    "لیکک",

    "دهدشت",

    "سی سخت",

    "دوگنبدان",

    "دنا",

    "گچساران",

    "یاسوج",

    "شاهو",

    "جوانرود",

    "پاوه",

    "صحنه",

    "هرسین",

    "گیلان غرب",

    "قصر شیرین",

    "سنقر",

    "کنگاور",

    "سر پل ذهاب",

    "اسلام آباد غرب",

    "کرمانشاه",

    "بردسیر",

    "جیرفت",

    "بم",

    "زرند",

    "کهنوج",

    "سیرجان",

    "بافت",

    "رفسنجان",

    "کوهبنان",

    "انار",

    "بابک",

    "راور",

    "کرمان",

    "حسن آباد",

    "صلوات آباد",

    "مریوان",

    "قروه",

    "کامیاران",

    "سقز",

    "بیجار",

    "بانه",

    "دیواندره",

    "سنندج",

    "مشکین دشت",

    "ماهدشت",

    "گوهردشت",

    "نظر آباد",

    "کرج",

    "اندیشه",

    "شهرک گلستان",

    "آسارا",

    "کن",

    "هشتگرد",

    "اشتهارد",

    "نظرآباد",

    "طالقان",

    "قم",

    "بوئین زهرا",

    "آبیک",

    "تاکستان",

    "قزوین",

    "سیاخ دارنگون",

    "خنج",

    "خرامه",

    "بوانات",

    "باب انار/خفر",

    "ده بید",

    "کوار",

    "خان زنیان",

    "زرقان",

    "داریون",

    "گویم",

    "ارژن",

    "سروستان",

    "فراشبند",

    "سوریان",

    "قیروکارزین",

    "ارسنجان",

    "صفاشهر",

    "اردکان",

    "نورآباد",

    "حاجی آباد",

    "مهر",

    "لامرد",

    "استهبان",

    "نی ریز",

    "جهرم",

    "فیروز آباد",

    "لار",

    "سپیدان",

    "ممسنی",

    "کازرون",

    "آباده",

    "خرم بید",

    "مرودشت",

    "فسا",

    "داراب",

    "اقلید",

    "شیراز",

    "میرجاوه",

    "راسک",

    "ایرانشهر",

    "نیکشهر",

    "سرباز",

    "زابل",

    "سراوان",

    "خاش",

    "چابهار",

    "زاهدان",

    "بسطام",

    "دامغان",

    "ایوانکی",

    "گرمسار",

    "شاهرود",

    "سمنان",

    "قیدار",

    "آب بر",

    "زرین آباد",

    "ایجرود",

    "خرمدره",

    "ماهنشان",

    "طارم",

    "خدابنده",

    "ابهر",

    "زنجان",

    "ویسی",

    "شادگان",

    "ملاثانی",

    "دغاغله",

    "حمیدیه",

    "رامشیر",

    "لالی",

    "هندیجان",

    "باغ ملک",

    "رامهرمز",

    "بهبهان",

    "امیدیه",

    "بندر امام خمینی",

    "بندر ماهشهر",

    "شادگان",

    "دزفول",

    "هویزه",

    "سوسنگرد",

    "اندیمشک",

    "شوشتر",

    "ایذه",

    "مسجد سلیمان",

    "خرمشهر",

    "آبادان",

    "شوش",

    "ایرانشهر",

    "اهواز",

    "ساروج",

    "گرمه",

    "آشخانه",

    "شیروان",

    "جاجرم",

    "اسفراین",

    "بجنورد",

    "سر ولایت",

    "طرقبه",

    "کلات",

    "درگز",

    "چناران",

    "فریمان",

    "بردسکن",

    "سرخس",

    "قوچان",

    "تایباد",

    "تربت جام",

    "خواف",

    "تربت حیدریه",

    "طبس",

    "گناباد",

    "کاشمر",

    "سبزوار",

    "نیشابور",

    "مشهد",

    "درمیان",

    "قهستان",

    "طبس مسینا",

    "سربیشه",

    "نهبندان",

    "بیرجند",

    "فردوس",

    "قائن",

    "سامان",

    "لردگان",

    "اردل",

    "چلگرد",

    "بروجن",

    "فارسان",

    "شهرکرد",

    "ملارد",

    "قدس",

    "رباط کریم",

    "شهریار",

    "باقرشهر",

    "قرچک",

    "شریف آباد",

    "چهاردانگه",

    "پاکدشت",

    "کهریزک",

    "فشم",

    "تجریش",

    "بومهن",

    "لواسان",

    "رودهن",

    "اسلامشهر",

    "دماوند",

    "ری",

    "فیروزکوه",

    "ورامین",

    "تهران",

    "بردخون",

    "عسلویه",

    "کاکی",

    "جم",

    "خارک",

    "برازجان",

    "اهرم",

    "خورموج",

    "دشتی",

    "ریشهر",

    "گناوه",

    "کنگان",

    "دیلم",

    "دیر",

    "دشتستان",

    "تنگستان",

    "بوشهر",

    "سرابله",

    "ایوان",

    "دره شهر",

    "شیروان چرداول",

    "آبدانان",

    "دهلران",

    "مهران",

    "ایلام",

    "ورزنه",

    "تودشک",

    "حاجی آباد",

    "نهضت آباد",

    "عسگران",

    "علویجه",

    "مهردشت",

    "خوانسار",

    "باغ بهادران",

    "آران و بیدگل",

    "زرین شهر",

    "دولت آباد",

    "نجف آباد",

    "شاهین شهر",

    "خمینی شهر",

    "شهرضا",

    "مبارکه",

    "کوهپایه",

    "درچه",

    "سمیرم",

    "اردستان",

    "فولاد شهر",

    "کاشان",

    "تیران",

    "نایین",

    "نطنز",

    "دهاقان",

    "گلپایگان",

    "فلاورجان",

    "فریدون شهر",

    "فریدن",

    "اصفهان",

    "گرمی",

    "کیوی",

    "کوثر",

    "نیر",

    "نمین",

    "مغان",

    "مشگین شهر",

    "خلخال",

    "پارس آباد",

    "بیله سوار",

    "سرعین",

    "اردبیل",

    "شوط",

    "پلدشت",

    "چایپاره",

    "اشنویه",

    "سیه چشمه",

    "پیرانشهر",

    "شاهین دژ",

    "سلماس",

    "میاندوآب",

    "بوکان",

    "چالدران",

    "سر دشت",

    "مهاباد",

    "خوی",

    "تکاب",

    "ماکو",

    "نقده",

    "ارومیه",

    "سهند",

    "باسمنج",

    "خسروشهر",

    "ایلخچی",

    "صوفیان",

    "ممقان",

    "قره آغاج",

    "آذر شهر",

    "اسکو",

    "ورزقان",

    "بستان آباد",

    "ملکان",

    "هشترود",

    "عجبشیر",

    "هریس",

    "اهر",

    "تسوج",

    "کلیبر",

    "بناب",

    "هادیشهر",

    "سراب",

    "جلفا",

    "مرند",

    "شبستر",

    "میانه",

    "مراغه",

    "بندر شرفخانه",

    "کندوان",

    "تبریز"

)
