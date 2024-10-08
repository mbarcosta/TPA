package entity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import com.github.javafaker.Faker;
import org.apache.commons.lang3.StringUtils;


public class GeradorDeArquivos {

	public GeradorDeArquivos() {
		// TODO Auto-generated constructor stub
	}
	/*
	 * Gerador de Agendas
	 * Autor: Mateus Costa 
	 */
			
		public static void teste1() {	
		Faker faker = new Faker();
		 BufferedWriter buffwrite;
		int i=0;
		String name, nomearq;
		 String firstName;
		 String lastName;
		 String city;
		 String phone, registro;
		 Scanner leitor = new Scanner(System.in);
		 
		System.out.print("Nome do Arquivo de saida: "); 
		nomearq = leitor.next();  
			try {
				buffwrite = new BufferedWriter(new FileWriter(nomearq));
			
		while(i<10000) {
		  name = faker.name().fullName();
		  firstName = faker.name().firstName();
		  lastName = faker.name().lastName();
		  city = faker.address().city();
		  phone = faker.phoneNumber().cellPhone();
		  registro= firstName + "," + lastName +"," + phone + "," + city +","+ "\n";
	 	//  System.out.println(registro);
	 	try {
			buffwrite.write(registro);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 	i++;
		}
		buffwrite.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	public static void teste2() {
		Faker faker = new Faker();
		 BufferedWriter buffwrite;
		int i=0;
		String name, nomearq;
		 String firstName;
		 String lastName;
		 String city;
		 String country;
		 String phone, registro;
		 Scanner leitor = new Scanner(System.in);
		 
		System.out.print("Nome do Arquivo de saida: "); 
		nomearq = leitor.next();  
			try {
				buffwrite = new BufferedWriter(new FileWriter(nomearq));
			
		while(i<1000) {
		  name = faker.name().fullName();
		  phone = faker.phoneNumber().cellPhone();
		  city = faker.address().cityName();
		  country = faker.address().country();   
		  registro= name + "," +  phone + "," + city +","+ country + ",\n";
		  System.out.print(registro);
		try {
			buffwrite.write(registro);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		i++;
		}
		buffwrite.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	public static void main(String[] args) {
		teste2();
	}    
	

}
