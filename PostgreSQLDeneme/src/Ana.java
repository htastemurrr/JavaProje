
import java.sql.*;
import java.util.Scanner;



public class Ana 
{
		static Connection baglanti;  //Veritabaný ile haberleþmek için bir connection oluturduk

	public static void main(String[] args) throws SQLException 
	{
		int tkrr;
        Scanner tekrar = new Scanner (System.in);
		do
		{
			Scanner islem = new Scanner (System.in);

	        System.out.println("Lütfen Ýþlem Numarasýný Girin 1- Tabloyu Göster   2- Kayýt Ekle  3- Kayýt Sil");
	        int soru = islem.nextInt();
	        switch(soru)
	        {
	        case 1:musteriGetir();break;
	        case 2:musteriEkle(null);break;
	        case 3:musteriSil();break;
	        default : System.out.println("Doðru bir deðer girmediniz");break;
	        }
	        System.out.println("Tekrar Ýþlem Yapmak Ýster Misiniz 1 EVET 2 HAYIR");
	        tkrr=tekrar.nextInt();
	        
			
	        
		}while(tkrr!=2);
		
	
	}
	
	public static Connection baglantiKur()//Veritabanýna baðlanmak istediðimiz her an çaýracaýmýz metod
	{
		try 
		{
			baglanti = DriverManager.getConnection("jdbc:postgresql://localhost/musteri","postgres","123456");
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		
		return baglanti;
		
	}
	
    public static void baglantiKapa() throws SQLException  //balantý her zaman açýk kalmasýn diye oluþturduðumuz metod
    {
    	baglanti.close();
    }
    
    public static void musteriGetir() throws SQLException
    {
    	baglantiKur();
    	String sorgu = " select * from musteriler " ;
    	PreparedStatement parametre = baglanti.prepareStatement(sorgu); //P.statement nesnesi sql sorgumuzun programda derlenmesi için
    	ResultSet sonuc = parametre.executeQuery();						//Resultset ise sorgunun döndürdüðü sonucu tutar
    	
    	while (sonuc.next()) 
    	{
            Musteriler musteri = new Musteriler();						//musteri nesnesi Musteriler classýna ulaþmamýz için kolaylýk saðlayacak
            musteri.setMusteriid (sonuc.getInt ("musteriid"));
            musteri.setMusteriadi (sonuc.getString ("musteriadi"));
            musteri.setMusterisoyadi (sonuc.getString ("musterisoyadi")); //geter seter fonksiyonlatý sayesinde gelen datalarýn hepsini yakalayýp uygun nesnelere tanýmladýk
            musteri.setGsm (sonuc.getInt ("gsm"));
            
            System.out.println(musteri.getMusteriid()+" "+musteri.getMusteriadi()+" "+musteri.getMusterisoyadi()+" "+musteri.getGsm());//while her döndüðünde tablodaki verileri alt alta yazacak
    
    	}
    	
    	sonuc.close();
        parametre.close();
        baglantiKapa();
        
    }
	
	public static boolean musteriEkle(Musteriler musteri)
	{
	    	   
               Scanner scan1 = new Scanner(System.in);
               Scanner scan2 = new Scanner(System.in);
               Scanner scan3 = new Scanner(System.in);
               Scanner scan4 = new Scanner(System.in);
               
               System.out.println ("Müþteri ID giriniz");
               int numara = scan1.nextInt();
               
               System.out.println ("Müþteri adýný giriniz");
               String isim = scan2.nextLine();
               
               System.out.println ("Müþteri soyadýný giriniz");
               String soyisim = scan3.nextLine();
               
               System.out.println ("Müþteri GSM giriniz");
               int tel = scan4.nextInt();
                 
               try 
               {
                      Connection baglanti = baglantiKur();
                      String sorgu = "insert into musteriler(musteriid,musteriadi,musterisoyadi,gsm) values(?,?,?,?)";
                      
                      PreparedStatement parametre = baglanti.prepareStatement(sorgu);  
                      parametre.setInt(1, numara);
                      parametre.setString(2, isim);
                      parametre.setString(3, soyisim);
                      parametre.setInt(4, tel);
                      parametre.executeUpdate();
                      
                      parametre.close();
                      baglantiKapa();
                      
                      System.out.println("Kayýt Baþarýyla Eklendi");
                      return true;
            
               } 
               catch (Exception e) 
               {                  
                      e.printStackTrace();
                      return false;
               } 
               
	}
	public static boolean musteriSil()
	{
	    	   
	    	   Scanner sil = new Scanner(System.in);
	    	   System.out.println ("Silmek Ýstediðiniz Kaydýn ID numarasýný giriniz");
	    	   int silgi = sil.nextInt();
	    	   
               try 
               {
                      Connection baglanti = baglantiKur();
                      String sorgu= "delete from musteriler where musteriid=?";
                      PreparedStatement parametre = baglanti.prepareStatement(sorgu);  
                      parametre.setInt(1, silgi);
                      parametre.executeUpdate();
                      parametre.close();
                      baglantiKapa();
                      System.out.println("Kayýt Baþarýyla Silindi");
                      return true;
               } 
               
               catch (Exception e) 
               {                  
                      e.printStackTrace();
                      return false;
               } 
	    	   
	       
	}
	       
}
