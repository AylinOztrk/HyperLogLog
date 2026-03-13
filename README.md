HyperLogLog Cardinality Estimation
Proje Hakkında

Bu proje, büyük veri analitiğinde kullanılan HyperLogLog (HLL) algoritmasının Java dili ile sıfırdan tasarlanıp gerçeklenmesini amaçlamaktadır.

HyperLogLog algoritması, büyük veri kümelerinde distinct (benzersiz) eleman sayısını düşük bellek kullanarak yaklaşık olarak tahmin eden bir olasılıksal veri yapısıdır.

Klasik yöntemlerle distinct sayısını hesaplamak için tüm verilerin saklanması gerekir. Ancak veri miktarı milyonlarca veya milyarlarca olduğunda bu yöntem hem bellek hem de işlem maliyeti açısından verimsiz hale gelir.

HyperLogLog algoritması ise yalnızca küçük bir register dizisi kullanarak veri kümesinin büyüklüğünü yüksek doğrulukla tahmin edebilir.

Bu nedenle HyperLogLog algoritması günümüzde birçok büyük veri sisteminde kullanılmaktadır.

Örnek kullanım alanları:

Web sitelerindeki benzersiz ziyaretçi sayısını tahmin etmek

Bir veri akışındaki benzersiz IP adreslerini saymak

Event log sistemlerinde farklı olay sayısını hesaplamak

HyperLogLog Algoritmasının Çalışma Mantığı

HyperLogLog algoritması üç temel bileşenden oluşur:

1. Hash Fonksiyonu

Algoritma, veri elemanlarını rastgele bir bit dizisine dönüştürmek için bir hash fonksiyonu kullanır.

Bu projede SHA-256 hash algoritması kullanılmıştır.

Hash fonksiyonu sayesinde veriler istatistiksel olarak rastgele dağılır ve algoritmanın doğruluğu korunur.

2. Bucketing (Kovalama Mekanizması)

Hash değerinin ilk bitleri kullanılarak veriler kovalara ayrılır.

Kova sayısı şu şekilde hesaplanır:

m = 2^p

Burada:

p → precision (hassasiyet parametresi)

m → toplam kova sayısıdır.

Kova sayısı arttıkça algoritmanın doğruluğu artar ancak kullanılan bellek miktarı da artar.

3. Register Yapısı

Her kova için bir register tutulur.

Register içinde hash değerindeki ardışık sıfır sayısı (leading zeros) saklanır.

Eğer yeni gelen verinin leading zero değeri daha büyükse register güncellenir.

Cardinality Tahmini

Tüm register değerleri kullanılarak veri kümesinin büyüklüğü tahmin edilir.

HyperLogLog algoritması tahmin için harmonik ortalama kullanır.

Tahmin formülü:

E = αₘ · m² · ( Σ 2⁻ᴹⁱ )⁻¹

Burada:

m register sayısıdır

αₘ düzeltme katsayısıdır

M[i] register değeridir

Small Range Correction

Küçük veri setlerinde hata oranını azaltmak için Linear Counting yöntemi uygulanır.

Boş register sayısı kullanılarak yeni bir tahmin yapılır.

Formül:

E = m · ln(m / V)

Burada:

V boş register sayısını ifade eder.

Merge (Birleştirilebilirlik)

HyperLogLog algoritmasının önemli özelliklerinden biri birleştirilebilir olmasıdır.

İki farklı HLL yapısı şu şekilde birleştirilebilir:

Her register için maksimum değer alınır.

Bu özellik sayesinde HyperLogLog algoritması dağıtık sistemlerde paralel çalışabilir.
