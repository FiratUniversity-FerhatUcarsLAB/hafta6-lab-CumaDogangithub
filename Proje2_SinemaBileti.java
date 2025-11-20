/*
  Ad Soyad : Cuma Doğan
  Öğrenci No : 250542003
*/
import java.util.Scanner;
public class Proje2_SinemaBileti {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int gun,yas,filmTur;
        double saat;
        String meslek,isim;

        //Sistem Başlangıç
        System.out.println("\nSinema Bileti Fiyatlandırma");

        //Müşteri Bilgilendirme Başlangıç
        System.out.println("--- Fiyat Listesi ---");

        System.out.println("\nTemel Fiyatlar : ");
        System.out.println("-Hafta içi matine (12:00 öncesi) : 45TL");
        System.out.println("-Hafta içi normal : 65TL");
        System.out.println("-Hafta sonu matine : 55TL");
        System.out.println("-Hafta sonu normal : 85TL");

        System.out.println("\nİndirimler : ");
        System.out.println("-Öğrenci : %20 (Pazartesi - Perşembe), %15(Cuma-Pazar)");
        System.out.println("-65+ yaş : %30 (her gün)");
        System.out.println("-12 yaş altı : %25 (her gün)");
        System.out.println("-Öğretmen : %35 (sadece çarşamba)");

        System.out.println("\nFilm türü ekstra : ");
        System.out.println("3D film : +25TL");
        System.out.println("IMAX : +35TL");
        System.out.println("4DX : +50");
        //Müşteri Bilgilendirme Bitiş

        //Kullanıcıdan Girdi Alma Başlangıç

        //isim
        System.out.print("\n\nİsminizi Giriniz:");
        isim=in.nextLine();

        //yas
        System.out.print("\nYaşınızı Giriniz: ");
        yas = in.nextInt();

        // \n i tüketmek için eklenmiştir
        in.nextLine();

        //meslek
        System.out.print("\nMesleginizi Giriniz (Ogretmen,Ogrenci,...): ");
        meslek=in.nextLine();
        //gün
        System.out.print("\nBiletinizi hangi güne istersiniz? (lütfen rakam giriniz) (1=pazartesi,2=salı,3=çarşamba,4=perşembe,5=cuma,6=cumartesi,7=pazar): ");
        gun = in.nextInt();
        if (gun < 1 || gun > 7){
            System.out.print("\nLütfen Geçerli Bir Gün Giriniz :");
            gun=in.nextInt();
        }
        //saat
        System.out.print("\nFilm Saatinizi Giriniz (örnek -> 12.00) :");
        saat = in.nextDouble();

        System.out.print("\nEkstra film türünüzü giriniz (0 = Hiçbiri,1 = 3D , 2 = IMAX , 3 = 4DX) ");
        filmTur = in.nextInt();
        generateTicketInfo(gun,yas,filmTur, saat,meslek, isim);
    }
    //Hafta sonu mu kontrol et
    public static boolean isWeekend(int gun){
        if (gun >= 1 && gun <= 5 ) return false;
        return true;
    }
    //Matine mi kontrol et
    public static boolean isMatinee(double saat){
        if (saat < 12.00) return true;
        return false;
    }
    //Temel Fiyat
    public static int calculateBasePrice(int gun , double saat){
        int fiyat=0;
        if (!isWeekend(gun)){
            if (isMatinee(saat)){
                fiyat =45;
            }else {
                fiyat = 65;
            }
        }else {
            if (isMatinee(saat)){
                fiyat =55;
            }else {
                fiyat = 85;
            }
        }
        return fiyat;
    }
    //Indirim hesapla
    public static double calculateDiscount(int gun , int yas,String meslek,int fiyat){
        double indirim=0;
        meslek = meslek.toLowerCase().replace("ö", "o");
        if (meslek.equals("ogrenci")) {
            if (gun < 5) {
                indirim = fiyat * 0.20;
            } else {
                indirim = fiyat * 0.15;
            }
        } else if ((meslek.equals("ogretmen")) && gun == 3) {
            indirim = fiyat * 0.35;
        }

        if (yas < 12 && meslek.equals("ogrenci")){
            indirim =indirim + fiyat * 0.25;
        }
        return indirim;
    }
    //Format ekstra ücreti
    public static int getFormatExtra(int filmTur){
        int fiyat = 0;
        if (filmTur == 1 ){
            fiyat = 25;

        }else if (filmTur == 2){
            fiyat = 35;
        }else if (filmTur == 3){
            fiyat = 50;
        }
        return fiyat;
    }
    //Toplam fiyat
    public static double calculateFinalPrice (int yas ,String meslek,int gun,double saat,int filmTur ){
        int basePrice =calculateBasePrice(gun,saat);
        return basePrice - calculateDiscount(gun,yas,meslek,basePrice) + getFormatExtra(filmTur);
    }

    public static void generateTicketInfo (int gun,int yas,int filmTur,double saat,String meslek,String isim){
        int sabitFiyat=calculateBasePrice(gun, saat);
        String[] gunler = {"Pazartesi","Salı","Çarşamba","Perşembe","Cuma","Cumartesi","Pazar"};
        String[] ekstra ={"3D","IMAX","4DX"};
        System.out.println("----- Bilet Detayları -----");
        System.out.println("İsim :" + isim);
        System.out.println("Bilet günü :"+ gunler[gun - 1]);
        System.out.printf("Bilet saati %.2f :\n", saat);
        System.out.println("Bilet fiyatı :" + calculateFinalPrice(yas,meslek,gun,saat,filmTur) +" TL");
        System.out.println("Toplam indirim : "+ calculateDiscount(gun,yas,meslek,sabitFiyat) +" TL");
        System.out.println("Toplam Ekstra "+ ((filmTur != 0) ? ("("+ekstra[filmTur-1]+")") : "")+" : " + getFormatExtra(filmTur) +" TL");
    }
}

