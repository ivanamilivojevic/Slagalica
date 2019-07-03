package rs.itbootcamp.gen4.nedelja9;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Slagalica {
	
	public static boolean isLegal(char[] combination, String word) {
		combination = combination.clone(); 
		word = word.toUpperCase();
		
		for(int i=0;i<word.length();i++) {
			char letter = word.charAt(i);
			
			boolean found = false;
			for(int j=0; j<combination.length && !found; j++) {
				if(combination[j] == letter) {
					found = true; 
					combination[j] = '.';
				}
			}
			if(!found) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean inDict(String word) {
		boolean found = false;
		word = word.toLowerCase();
		
		try(BufferedReader reader = new BufferedReader(new FileReader("recnik.txt"))){
			for(String line = reader.readLine(); line!=null && !found; line=reader.readLine())
				if(line.equals(word))
					found = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return found;
	}
	
	public static boolean isTimeUp(long timestamp) {
		if(System.currentTimeMillis() - timestamp > 60000)
			return true;
		return false;
	}
	
	public static void main(String[] args) {
		String azbuka = "АБВГДЂЕЖЗИЈКЛЉМНЊОПРСТЋУФХЦЧЏШ";
		Scanner sc = new Scanner(System.in);
		char[] combination = new char[12];
		
		// generisemo kombinaciju
		for(int i=0;i<combination.length;i++)
			combination[i] = azbuka.charAt((int)(Math.random()*azbuka.length()));
		
		// stampamo kombinaciju
		for(char c: combination)
			System.out.printf("%c ", c);
		System.out.println();
		
		// hvatamo pocetno vreme
		long timestamp = System.currentTimeMillis();		
		
		// cekamo korisnicki unos		
		String word = sc.next();
		
		//filtriramo nelegalne vrednosti koje ne zadovoljavaju uslov i trarzimo novi unos
		while(!isTimeUp(timestamp) && !isLegal(combination, word)) {
			System.err.println("Користили сте недозовољена слова! Пробајте опет");
			word = sc.next();
		}
		
		if(isTimeUp(timestamp))
			System.out.println("Нажалост, време је истекло!");
		else if (isLegal(combination, word) && inDict(word))
			System.out.printf("Освојили сте %d поена!\n", word.length() * 2);
		else
			System.out.println("Освојили сте 0 поена!");
	}
}
