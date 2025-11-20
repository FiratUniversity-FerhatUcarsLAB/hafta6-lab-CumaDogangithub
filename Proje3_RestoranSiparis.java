/*
Ad Soyad : Cuma Doğan
Öğrenci no : 250542003
*/
import java.util.Scanner;

public class Proje3_RestoranSiparis { // Sınıf ismini dosyanızla aynı bıraktım
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        // Menü Yazdırma
        System.out.println("-- Menü --");
        System.out.println("Ana Yemekler");
        System.out.println("    1: Izgara Tavuk - 85 TL");
        System.out.println("    2: Adana Kebap - 120 TL");
        System.out.println("    3: Levrek - 110 TL");
        System.out.println("    4: Mantı - 65 TL");
        System.out.println("Başlangıç");
        System.out.println("    1: Çorba - 25 TL");
        System.out.println("    2: Humus - 45 TL");
        System.out.println("    3: Sigara Böreği - 55 TL");
        System.out.println("İçecekler");
        System.out.println("    1: Kola - 15 TL");
        System.out.println("    2: Ayran - 12 TL");
        System.out.println("    3: Taze Meyve Suyu - 35 TL");
        System.out.println("    4: Limonata - 25 TL");
        System.out.println("Tatlılar");
        System.out.println("    1: Künefe - 65 TL");
        System.out.println("    2: Baklava - 55 TL");
        System.out.println("    3: Sütlaç - 35 TL");
        System.out.println("\n-------Akıllı Restoran Sipariş Sistemi-------");

        // --- GİRDİLER ---
        System.out.print("Ana Yemek (1-4) : ");
        int anaSecim = in.nextInt();

        System.out.print("Baslangic (0-3) : "); // 0 seçilirse yok sayar
        int baslangicSecim = in.nextInt();

        System.out.print("Icecek (0-4) : ");
        int icecekSecim = in.nextInt();

        System.out.print("Tatli (0-4) : ");
        int tatliSecim = in.nextInt();

        System.out.print("Saat (8-23) : ");
        int saat = in.nextInt();

        System.out.print("Ogrenci misiniz? (E/H) : ");
        char ogrenciDurumu = in.next().toUpperCase().charAt(0);
        boolean ogrenciMi = (ogrenciDurumu == 'E');

        System.out.print("Hangi gun? (1-7) : ");
        int gun = in.nextInt();
        boolean haftaIci = (gun >= 1 && gun <= 5);

        // --- FİYATLARIN ALINMASI ---
        int anaFiyat = getMainDishPrice(anaSecim);
        int baslangicFiyat = getAppetizerPrice(baslangicSecim);
        int icecekFiyat = getDrinkPrice(icecekSecim);
        int tatliFiyat = getDessertPrice(tatliSecim);

        double araToplam = anaFiyat + baslangicFiyat + icecekFiyat + tatliFiyat;

        // --- KOŞULLARIN KONTROLÜ ---
        boolean comboVar = isComboOrder(anaSecim, icecekSecim, tatliSecim);
        boolean happyHour = isHappyHour(saat);

        // --- HESAPLAMA ---
        System.out.println("\n--- Hesap Detayları ---");
        System.out.println("Ara Toplam: " + araToplam + " TL");

        // Toplam indirim miktarını hesapla
        double toplamIndirim = calculateDiscount(araToplam, comboVar, ogrenciMi, happyHour, icecekFiyat, haftaIci);
        double odenecekTutar = araToplam - toplamIndirim;

        // Bahşiş hesapla
        double bahsis = calculateServiceTip(odenecekTutar);

        System.out.println("Toplam Ödenecek: " + String.format("%.2f", odenecekTutar) + " TL");
        System.out.println("Bahşiş Önerisi (%10): " + String.format("%.2f", bahsis) + " TL");
    }

    // --- GEREKLİ METOTLAR ---

    public static int getMainDishPrice(int number){
        switch (number){
            case 1: return 85;
            case 2: return 120;
            case 3: return 110;
            case 4: return 65;
            default: return 0;
        }
    }

    public static int getAppetizerPrice(int number){
        switch (number){
            case 1: return 25;
            case 2: return 45;
            case 3: return 55;
            default: return 0;
        }
    }

    public static int getDrinkPrice(int number){
        switch (number){
            case 1: return 15;
            case 2: return 12;
            case 3: return 35;
            case 4: return 25;
            default: return 0;
        }
    }

    public static int getDessertPrice(int number){
        switch (number){
            case 1: return 65;
            case 2: return 55;
            case 3: return 35;
            default: return 0;
        }
    }

    public static boolean isComboOrder(int ana, int icecek, int tatli){
        // Ana yemek, içecek ve tatlı seçilmişse (0 değilse) true döner
        return (ana != 0 && icecek != 0 && tatli != 0);
    }

    // Happy Hour kontrolü (14:00 - 17:00 arası)
    public static boolean isHappyHour(int saat) {
        return (saat >= 14 && saat <= 17);
    }

    // İndirim Hesaplama Metodu
    // Not: İçecek fiyatı ve gün bilgisi olmadan tam hesap yapılamadığı için parametreleri genişlettik.
    public static double calculateDiscount(double tutar, boolean combo, boolean ogrenci, boolean happyHour, int icecekFiyat, boolean haftaIci) {
        double toplamIndirim = 0;
        double guncelTutar = tutar;

        // 1. Combo İndirimi veya 200 TL Üzeri İndirimi
        if (combo) {
            double indirim = tutar * 0.15;
            toplamIndirim += indirim;
            System.out.println("Combo İndirimi (%15): -" + indirim + " TL");
        } else if (tutar > 200) {
            double indirim = tutar * 0.10;
            toplamIndirim += indirim;
            System.out.println("200 TL Üzeri İndirimi (%10): -" + indirim + " TL");
        }

        // 2. Happy Hour İndirimi (Sadece içecek üzerinden)
        if (happyHour && icecekFiyat > 0) {
            double indirim = icecekFiyat * 0.20;
            toplamIndirim += indirim;
            System.out.println("Happy Hour İçecek İndirimi (%20): -" + indirim + " TL");
        }

        // Şu ana kadarki indirimleri düşüp kalan tutarı bulalım (Öğrenci indirimi için)
        guncelTutar = tutar - toplamIndirim;

        // 3. Öğrenci İndirimi (Hafta içi ise, kalan tutar üzerinden %10)
        if (ogrenci && haftaIci) {
            double indirim = guncelTutar * 0.10;
            toplamIndirim += indirim; // Toplam indirime ekle
            System.out.println("Öğrenci İndirimi (%10): -" + String.format("%.2f", indirim) + " TL");
        }

        return toplamIndirim;
    }

    // Bahşiş Hesaplama (%10)
    public static double calculateServiceTip(double tutar) {
        return tutar * 0.10;
    }
}

