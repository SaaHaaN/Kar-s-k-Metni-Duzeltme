/**
 * @file Proje
 * @descripiton Harfleri karmaşık şekilde verilmiş ve bu kelimelere ait sözlük ile karşılaştırıp  harf kaydırma
 * işlemi yaptıktan sonra kelimeyi doğru şekilde yazdıran ve sözlükte bulunan bir kelimenin kaç kere geçtiğini
 * gösteren program.
 * @assignment Proje Ödevi 1
 * @date 05.12.2021
 * @author Şahan Aytekin - sahan.aytekin@stu.fsm.edu.tr
 */

import java.util.Scanner;

public class Proje {

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);
        System.out.print("Karmaşık metni girin : ");
        String karmasik = s.nextLine();
        System.out.print("Sözlüğü girin : ");
        String sozluk = s.nextLine();

        String[] duzgunMetin = metniDuzelt(diziYap(karmasik), diziYap(sozluk));     // Karmaşık metinin düzgün metin olarak yazdırıldığı String array.
        int[] kacKereDizisi = kacKereGecti(duzgunMetin, diziYap(sozluk));           // Kaç gere geçtiğini yazdıran integer olarak atanan array.

        for (int i = 0; i < duzgunMetin.length; i++) {
            System.out.print(duzgunMetin[i]);
            if (i < duzgunMetin.length - 1) {
                System.out.print(" ");
            }
        }
        System.out.println();

        for (int i = 0; i < kacKereDizisi.length; i++) {
            System.out.print(kacKereDizisi[i]);
            if (i < kacKereDizisi.length - 1) {
                System.out.print("  ");
            }
        }
    }


    /*
    Verilmiş olan karmaşık metin ve sözlük kelimelerini tek tek array indexine atmak için index adetini bulmam gerekiyordu.
    Buna karşılık olarak index adetinin yani Array.length'in kelime sayısını bulma metodunu yaptım.
     */
    public static int kelimeSayisi(String metin) {
        int kelimeSayisi = 1;  // 1 den başlattım çünkü sayma 0'dan başlayamaz
        for (int i = 0; i < metin.length(); i++) {
            if (metin.charAt(i) == ' ') {
                kelimeSayisi++;
            }
        }
        return kelimeSayisi;
    }


    /*
    Bu metot bize verilen metindeki her bir kelimeyi dizinin farklı indexine atıyor.
    Çalışma mantığı ise String ile girilen metinde boşluk görmez ise harfleri yeni bir String değişkenine eklemeye
    devam et, eğer boşluk görürse onu array elemanına at , diğer array elamnına geç ve en son olarak indeks adetini bir
    arttır. Metinler sonlanana kadar döngü devam etsin.
     */
    public static String[] diziYap(String metin) {
        int kacKelime = kelimeSayisi(metin);

        String[] kelimeDizisi = new String[kacKelime];
        String indexKelime = "";
        int indexAdet = 0;

        for (int i = 0; i < metin.length(); i++) {
            if (metin.charAt(i) != ' ') { // boşluk görmediği sürece harfleri tek tek ekle.
                indexKelime += metin.charAt(i);
            } else {
                kelimeDizisi[indexAdet] = indexKelime;
                indexKelime = "";
                indexAdet++;
            }
        }
        kelimeDizisi[indexAdet] = indexKelime;
        return kelimeDizisi;
    }

    /*
    Bu metot karmaşık metinde olan büyük harfler için yapıldı. ASCII tablosu yardımıyla çalışıyor.
    Eğer karmaşık metinde olan kelime içinde büyük harf varsa yani ASCII tablossunda değeri 65 ile 90 arasında (65 ve 90 dahil)
    olan büyük harflerin değerini 32 arttırarak 97 ile 122 arasına (97 ile 122 dahil) yükselterek küçük harf yapıyor.
     */
    public static String harfAyari(String kelime) {
        String yeni = "";
        for (int i = 0; i < kelime.length(); i++) {
            char harf = kelime.charAt(i);
            if (harf > 64 && harf < 91) {
                harf += 32;
            }
            yeni += harf;
        }
        return yeni;
    }


    /*
    Bu metotda gelen karmaşık kelimenin harflerini tek tek kaydırmak için yapıldı.
    Çalışma mantığı ise kelimenin 1.indexi yani 2.harfinden başla harfleri bir boş string değişkenine at sonra da
    ilk harfi en son indexin içine kaydet.
     */
    public static String harfKaydirma(String kelime) {
        String kaydirilanHarf = "";
        for (int i = 1; i < kelime.length(); i++) { // 2.harften yani 1.indexten başlamak için böyle yaptım
            kaydirilanHarf += kelime.charAt(i); // diğer harfler (i-1) indexine yazıldı.
        }
        kaydirilanHarf += kelime.charAt(0); // son olarak ilk harfi en sona ekledim.
        return kaydirilanHarf;
    }


    /*
    Bu metot karmaşık metinde olan kelime ile sözlükte olan karşılığını bulan kısım
    İlk olarak verilmiş karmaşık metnin büyük harflerini alıyor küçük harf olarak kaydediyor
    Çalışma mantığı ise karmaşık metinde bulunan kelimeler kaydırma yaparak sözlükteki karşılığı ile aynı olana kadar
    harf kaydırıp sözlük ile aynı yapıp bunu boş bir String değişkenine atıyor. Sonra karmaşık metinde büyük harf mi
    kontrol edip eğer büyük harf ise onu büyük harf olarak değilse normal halini yazıyor.
    */
    public static String kelimeKarsilastir(String kelime, String[] sozluk) {
        String kucukKelime = harfAyari(kelime);     // tüm harfler küçük harf olarak değiştirildi
        boolean buyukleMiBasladi = false;   // bu boolean ilk harfin büyük olup olmadığına göre çalışıyor
        String sonuc = "";
        for (int i = 0; i < kucukKelime.length(); i++) {
            if (kelime.charAt(i) > 64 && kelime.charAt(i) < 91) {
                buyukleMiBasladi = true;
                break;  // eğer bu break olmasaydı sözlükteki kelime birden fazla geçseydi onu da ekleyecekti bu yüzden break koydum.
            }
        }

        for (String s : sozluk) {
            for (int i = 0; i < kucukKelime.length(); i++) {
                kucukKelime = harfKaydirma(kucukKelime);    // küçük harfe döndürülmüş olan karmaşık metindeki kelimelerin harfleri kaydırılıyor.
                if (kucukKelime.equals(s)) {    // Eğer kücük harfe döndürülmüş ve harfleri kaydırılmış metin eşit ise sözlüktekine
                    sonuc = "";
                    if (buyukleMiBasladi) {
                        sonuc += (char) (s.charAt(0) - 32);
                        for (int j = 1; j < s.length(); j++) {
                            sonuc += s.charAt(j);
                        }
                    } else {
                        sonuc = s;
                    }
                }
            }
        }
        return sonuc;
    }


    /*
    Bu metot karmaşık olarak gelen metni sözlük kelimesi ile karşılaştırma yaptığım yer.
    Çalışma mantığı ise yeni bir diziye (karmaşık metin kelime sayısı ile aynı eleman sayısına sahip) harf kaydırması
    yapılmış ve sözlük ile aynı olan kelimeyi ekliyor. En son olarakta bu diziyi geri döndürüyor.
     */
    public static String[] metniDuzelt(String[] karmasik, String[] sozluk) {

        String[] duzeltilmisMetin = new String[karmasik.length];

        for (int i = 0; i < karmasik.length; i++) {
            duzeltilmisMetin[i] = kelimeKarsilastir(karmasik[i], sozluk);  //boş arrayin i.elemanının içine harf kaydırması ve karşılaştırılması yapılmış olan doğru kelime ekleniyor.
        }
        return duzeltilmisMetin;
    }


    /*
    Bu metotda sözlükte verilmiş olan kelimenin düzeltilmiş metinde kaç kere geçtiğini gösteren metodu yazdım.
    Bu metotda kacKereDizisi'sinin index içinde bulunan sözlük kelimelerinin bir tanesi ile karşılaştığı zaman
    değeri bir olarak arttırdığı ve işlem bitince de integer arrayi geri döndüren metot.
     */
    public static int[] kacKereGecti(String[] duzgunMetin, String[] sozluk) {
        int[] kacKereDizisi = new int[sozluk.length];
        for (int i = 0; i < sozluk.length; i++) {
            for (int j = 0; j < duzgunMetin.length; j++) {
                if (sozluk[i].equals(harfAyari(duzgunMetin[j]))) {  // sözlükte i.eleman ile harf ayarı yapılmış yani harfleri küçültülmüş olan kelime aynı ise +1 olarak kayıt et.
                    kacKereDizisi[i]++;
                }
            }
        }
        return kacKereDizisi;
    }
}