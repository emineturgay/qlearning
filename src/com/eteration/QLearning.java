package com.eteration;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.channels.SeekableByteChannel;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;

import org.w3c.dom.Node;

public class QLearning {
	 
//	 int siradaki_durum,yeni_durum;
	 int e,r;
//	 int lineNumber;
	
	public static void main (String[] args) throws IOException{
		Fonk olustur = new Fonk();
		 final double gamma = 0.8;
		 ArrayList<ArrayList<Integer>> R = new ArrayList<ArrayList<Integer>>();
		 float[][]  Q = new float[200][200];
		 
		//char[] cumle=new char[200];
		//char[] dizi=a.toCharArray();
	    int j=0;
	    int hn = 0,bn,iterasyon;
	    
	    File file = new File("input.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
      //dosyadan okuma islemi
        FileReader fileReader = new FileReader(file);
        BufferedReader br = new BufferedReader( fileReader);
        
        //en son yaptýgým okuma islemi buydu
      int lineNumber = 0;
      try
      {
 
      String satir = br.readLine();
          while (satir!=null) {
           if(satir.length()>0){
            lineNumber++;
           }
           satir = br.readLine();  
          
          }    
      }catch(final IOException e){}
      System.out.println("Verilen Dökümandaki Satýr Sayýsý: "+lineNumber);

        
        Scanner sc = new Scanner(System.in);
        System.out.println("BASLANGIC NOKTASI GIRINIZ : ");
      
        
      
		
		do{
            bn=sc.nextInt();
            if(bn > (lineNumber-1)){
            	System.out.println("hatali giris tekrar deneyin :  ");
            }   
        }while(bn > (lineNumber-1));
        
        System.out.println("HEDEF NOKTASI GIRINIZ : ");
        
        do
		{
			hn=sc.nextInt();
			if(hn>(lineNumber-1)){
				 System.out.println("hatali giris tekrar deneyin : ");
			}
			
		}while(hn > (lineNumber-1));
        
        System.out.println("Iterasyon sayisi girin: ");
        iterasyon=sc.nextInt();
        
        int e,r;
      
       

        
       // Matrisi olusturmak icin her satirda bir arrayList tanýmlayip,
        //fordan sonra bunu yaptim.
        for(int s=0; s<lineNumber;s++){
        	 R.add(new ArrayList<Integer>());
        	for(int d=0; d<lineNumber;d++){
        		R.get(s).add(d, -1);
        	}
        }

 

  try
  {
	  	  FileReader fileReader1 = new FileReader(file);
          //create BufferedReader to read csv file
          BufferedReader br1 = new BufferedReader( fileReader1);
          String strLine = "";
          StringTokenizer st = null;
          int satir_sayisi = 0;
         
          //read comma separated file line by line
          while( (strLine = br1.readLine()) != null)
          {               	       
                  //break comma separated line using ","
                  st = new StringTokenizer(strLine, ",");
                 
                  while(st.hasMoreTokens())
                  { 
                	  String intStr = st.nextToken();
                	  int y = Integer.parseInt(intStr);
//                	  System.out.println("Excep " + y);  
//                	  R.get(0).set(4, y);
//                	  System.out.println("R  "+R.get(0).get(4));
                 	
	               	   if(y == hn){
	               		
	               		   R.get(satir_sayisi).set(y, 100);
	               		   R.get(hn).set(hn, 100);
	               		  
	                   }
	                   else {
	                    	R.get(satir_sayisi).set(y, 0);    
	                   }
                    
                    }
                  	satir_sayisi++;
              
             }
          
          br1.close();
  }
  catch(Exception e1)
  {
          System.out.println("Exception while reading csv file: " + e1);                  
  }
  

//  for(e=0; e<lineNumber; e++){
    
//      System.out.println("R matrisimizin en son hali: "+R.get(e));
      
//   }
   
 

  File file2 = new File("R2.txt");
  if (!file2.exists()) {
      file2.createNewFile();
  }

  FileWriter fileWriter = new FileWriter(file2, false);
  BufferedWriter bWriter = new BufferedWriter(fileWriter);
  //bWriter.write(Integer.toString(R.get(0).get(1)));

  PrintWriter out = new PrintWriter(bWriter);
  for(e=0; e<lineNumber; e++){
	  for(r=0; r<lineNumber; r++){
		  bWriter.write(Integer.toString(R.get(e).get(r))+"\n");
		  bWriter.write(",   ");
	  }
	  
	  out.println("\n");
  }
  bWriter.close();
  out.close();

  
  for( e = 0; e <lineNumber; e++){
      for( r = 0; r <lineNumber; r++){
          Q[e][r] = 0;
		}
	}
  
  int baslangic_durumu=bn;

  int siradaki_durum;
for( j = 0; j <= iterasyon ; j++){

         siradaki_durum = baslangic_durumu;

	 for(int i = 0; i < lineNumber; i++){
     int sonraki_hareket;

  sonraki_hareket = olustur.rastgele_yer(lineNumber,R,siradaki_durum);

	if(R.get(siradaki_durum).get(sonraki_hareket) >= 0){
      Q[siradaki_durum][sonraki_hareket] = (float) (R.get(siradaki_durum).get(sonraki_hareket) + 
    		  (gamma * olustur.max_degeri_bul(sonraki_hareket, 2,lineNumber,Q)));
      siradaki_durum = sonraki_hareket;
	}
	}
	}
		//////////////
		File file21 = new File("outQ.txt");
		if (!file21.exists()) {
		    file21.createNewFile();
		}
		
		FileWriter fileWriter1 = new FileWriter(file21, false);
		BufferedWriter bWriter1 = new BufferedWriter(fileWriter1);
		//bWriter.write(Integer.toString(R.get(0).get(1)));
		
		PrintWriter out1 = new PrintWriter(bWriter1);
		for(e=0; e<lineNumber; e++){
			  for(r=0; r<lineNumber; r++){
				  bWriter1.write(Double.toString(Q[e][r])+"\n");
				  bWriter1.write(",   ");
			  }
			  
			  out1.println("\n");
		}
		bWriter1.close();
		out1.close();
		/////////////////
		
		File file211 = new File("outPath.txt");
		if (!file211.exists()) {
		    file211.createNewFile();
		}
		
		FileWriter fileWriter11 = new FileWriter(file211, false);
		BufferedWriter bWriter11 = new BufferedWriter(fileWriter11);
		//bWriter.write(Integer.toString(R.get(0).get(1)));
		
		PrintWriter out11 = new PrintWriter(bWriter11);


      siradaki_durum = baslangic_durumu;
      int yeni_durum = 0;
      bWriter11.write(Integer.toString(siradaki_durum));
      bWriter11.write(", ");
		do {
          yeni_durum =olustur. max_degeri_bul(siradaki_durum,1,lineNumber,Q);
          bWriter11.write(Integer.toString(yeni_durum));
          bWriter11.write(", ");
          siradaki_durum = yeni_durum;
      }while(siradaki_durum != hn);
		bWriter11.close();
		out11.close();
		
		
		


        }


	
}
	
