# Book Parser

### Projenin Amacı
Bu projenin amacı, kullanıcıların bulmak istedikleri kitapların fiyatlarını, resimlerini ve satıcısını onlar için teker teker bulmak, bulunan kitapların arasından en ucuz olanını göstermek, kullanıcıların beğendikleri kitapları favorilere eklemesini sağlamak ve bu kitaplar arasından indirime giren olursa kullanıcının e-posta aracılığıyla haberdar olmasını sağlamaktır.

![Class Diagram Image](<imgs/diagram.png>)

### Açılış Ekranı
Program ilk açıldığında gelecek ekrandan giriş yapılabilir, kayıt olunabilir ya da kitap arama işlemi gerçekleştirilebilir. Favoriler menüsünü kullanmayacak olan kişilerin kitap aramak için üye olmaları gerekmez.

![Main Page Image](<imgs/main.png>)

### Kitap Arama Ekranı

Bu ekranda kullanıcılar giriş yapmadan arama işlemini gerçekleştirebilirler. Aranacak kelime girilip arama butonuna basıldıktan sonra program arka planda sitelerde arama işlemini yapar. Bu işlem internet hızına ve sitelerin sunucularına bağlı olarak 5 ila 10 saniye arası sürer.

![Search Tab Image](<imgs/search.png>)

### Favoriler Ekranı
Bu ekrana sadece üyeler erişebilir. Üyelerin favorilere eklemiş olduğu kitaplar, bu kitapların fiyatları, satıcıları ve adresleri gösterilir. Üyeler diledikleri zaman kitapları silebilir, kitapların web adresine erişebilir, ve kitapların fiyatlarındaki güncellemeleri denetleyebilirler.

![Favorites Tab Image](<imgs/favorites.png>)
