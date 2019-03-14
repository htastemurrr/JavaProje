
import java.sql.*;
import java.util.Scanner;



public class Ana 
{
		static Connection baglanti;  //Veritaban� ile haberle�mek i�in bir connection oluturduk

	public static void main(String[] args) throws SQLException 
	{
		int tkrr;
        Scanner tekrar = new Scanner (System.in);
		do
		{
			Scanner islem = new Scanner (System.in);

	        System.out.println("L�tfen ��lem Numaras�n� Girin 1- Tabloyu G�ster   2- Kay�t Ekle  3- Kay�t Sil");
	        int soru = islem.nextInt();
	        switch(soru)
	        {
	        case 1:musteriGetir();break;
	        case 2:musteriEkle(null);break;
	        case 3:musteriSil();break;
	        default : System.out.println("Do�ru bir de�er girmediniz");break;
	        }
	        System.out.println("Tekrar ��lem Yapmak �ster Misiniz 1 EVET 2 HAYIR");
	        tkrr=tekrar.nextInt();
	        
			
	        
		}while(tkrr!=2);
		
	
	}
	
	public static Connection baglantiKur()//Veritaban�na ba�lanmak istedi�imiz her an �a�raca�m�z metod
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
	
    public static void baglantiKapa() throws SQLException  //balant� her zaman a��k kalmas�n diye olu�turdu�umuz metod
    {
    	baglanti.close();
    }
    
    public static void musteriGetir() throws SQLException
    {
    	baglantiKur();
    	String sorgu = " select * from musteriler " ;
    	PreparedStatement parametre = baglanti.prepareStatement(sorgu); //P.statement nesnesi sql sorgumuzun programda derlenmesi i�in
    	ResultSet sonuc = parametre.executeQuery();						//Resultset ise sorgunun d�nd�rd��� sonucu tutar
    	
    	while (sonuc.next()) 
    	{
            Musteriler musteri = new Musteriler();						//musteri nesnesi Musteriler class�na ula�mam�z i�in kolayl�k sa�layacak
            musteri.setMusteriid (sonuc.getInt ("musteriid"));
            musteri.setMusteriadi (sonuc.getString ("musteriadi"));
            musteri.setMusterisoyadi (sonuc.getString ("musterisoyadi")); //geter seter fonksiyonlat� sayesinde gelen datalar�n hepsini yakalay�p uygun nesnelere tan�mlad�k
            musteri.setGsm (sonuc.getInt ("gsm"));
            
            System.out.println(musteri.getMusteriid()+" "+musteri.getMusteriadi()+" "+musteri.getMusterisoyadi()+" "+musteri.getGsm());//while her d�nd���nde tablodaki verileri alt alta yazacak
    
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
               
               System.out.println ("M��teri ID giriniz");
               int numara = scan1.nextInt();
               
               System.out.println ("M��teri ad�n� giriniz");
               String isim = scan2.nextLine();
               
               System.out.println ("M��teri soyad�n� giriniz");
               String soyisim = scan3.nextLine();
               
               System.out.println ("M��teri GSM giriniz");
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
                      
                      System.out.println("Kay�t Ba�ar�yla Eklendi");
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
	    	   System.out.println ("Silmek �stedi�iniz Kayd�n ID numaras�n� giriniz");
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
                      System.out.println("Kay�t Ba�ar�yla Silindi");
                      return true;
               } 
               
               catch (Exception e) 
               {                  
                      e.printStackTrace();
                      return false;
               } 
	    	   
	       
	}
	       
}
