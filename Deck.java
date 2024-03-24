import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.io.*;
import java.util.TreeMap;

public class Deck
{
	private int age;
	private ArrayList<Cards> one;
	private ArrayList<Cards> two;
	private ArrayList<Cards> three;
	private TreeMap <Integer, ArrayList<Cards>> ageDecks;
	private ArrayList<Cards> discard;
	public Deck() throws IOException
	{
		ageDecks = new TreeMap <Integer, ArrayList<Cards>>();
		discard=new ArrayList<Cards>();
		age=1;
		one = new ArrayList<Cards>();
		two = new ArrayList<Cards>();
		three = new ArrayList<Cards>();
		Scanner scan = new Scanner(new File ("cards.txt"));
		while (scan.hasNextLine())
		{
			String data = scan.nextLine();
			Cards card = new Cards (data);
			int a = card.getAge();
			if (a == 1)
				one.add(card);
			else if (a == 2)
				two.add(card);
			else
				three.add(card);
		}
		ageDecks.put(1,one);
		ageDecks.put(2,two);
		ageDecks.put(3,three);
	}
	
	public void shuffle()
	{
		Collections.shuffle(ageDecks.get(1));
		Collections.shuffle(ageDecks.get(2));
		Collections.shuffle(ageDecks.get(3));
	}
	
	public ArrayList<Cards> ageOne()
	{
		return ageDecks.get(1);
	}
	
	public ArrayList<Cards> ageTwo()
	{
		return ageDecks.get(2);
	}
	
	public ArrayList<Cards> ageThree()
	{
		return ageDecks.get(3);
	}
	
	public Cards removeCards()
	{
		ArrayList<Cards> cards = ageDecks.get(age);
		Cards topcard = cards.get(cards.size()-1);
		cards.remove(topcard);
		ageDecks.put(age,cards);
		return topcard;
	}
	public void addDiscard(Cards c)
	{
		discard.add(c);
	}
	public ArrayList<Cards> getDiscard()
	{
		return discard;
	}
	public void age()
	{
		discard.clear();
		age++;
	}
	
	public int getAge()
	{
		return age;
	}
	
	public String toString()
	{
		return ageDecks.get(1).toString() + " " + ageDecks.get(2).toString() + " "  + ageDecks.get(3).toString();
	}
}