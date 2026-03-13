# HyperLogLog
Bu proje, büyük veri setlerinde benzersiz öğe sayısını (cardinality) düşük bellek kullanımı ve yüksek doğrulukla tahmin etmeyi sağlayan HyperLogLog (HLL) algoritmasının Java üzerinde sıfırdan gerçeklenmesini içermektedir.
Projenin Amacı:
Geleneksel yöntemlerle (HashSet vb.) milyonlarca benzersiz öğeyi saymak GB'larca bellek gerektirirken, HLL algoritması bu işlemi sabit bir hata payı ile sadece birkaç KB bellek kullanarak gerçekleştirir. Bu ödev kapsamında algoritmanın teorik temelleri, harmonik ortalama düzeltmeleri ve birleştirilebilirlik (mergeable) özellikleri incelenmiştir.
Teknik Bileşenler Yüksek Kaliteli Hash Fonksiyonu:
Verilerin bit düzeyinde rastgele dağılımını sağlamak amacıyla SHA-256 hashing algoritması kullanılmıştır.
Bucketing (Kovalama): Hash değerinin ilk p biti kullanılarak veriler m = 2^p adet kovaya dağıtılmıştır.
Register Yapısı: Her kova (register), o gruptaki veriler arasında gözlemlenen en uzun "ardışık sıfır" sayısını (ilk 1 bitinin konumu) saklar.
Harmonik Ortalama: Uç değerlerin (outliers) tahmini saptırmasını engellemek için nihai hesaplamada Harmonik Ortalama formülü kullanılmıştır.
Düzeltme Faktörleri: * Küçük Veri Setleri: Boş kova sayısına dayalı Linear Counting yöntemi.
Büyük Veri Setleri: 2^32 sınırı için doygunluk düzeltmeleri.
