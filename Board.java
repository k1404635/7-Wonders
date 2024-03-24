import java.util.*;
import java.io.*;
public class Board
{
	  private Deck deck;
	  private Player[] players;
	  private ArrayList<Integer> wonders;
	  private ArrayList<String> resources;
	  private HashMap<Player, ArrayList<Cards>> hands;
	  private int p, age;
	  private boolean gameEnd;
	  public Board() throws IOException
	  {
		  deck=new Deck();
		  deck.shuffle();
		  resources=new ArrayList<String>();
		  age=1;
		  gameEnd=false;
		  p=0;
		  players=new Player[3];
		  hands=new HashMap<Player, ArrayList<Cards>>();
		  for(int s=0; s<3; s++)
			  players[s]=new Player();
		  deal();
		  wonders=new ArrayList<Integer>();
		  for(int a=0; a<6; a++)
			  wonders.add(a);
		  for(int b=0; b<3; b++)
		  {
			  players[b].setWonders(chooseWonder());
			  players[b].addResource(players[b].getWonders().getRes());
		  }
	  }
	  public Deck getDeck()
	  {
		  return deck;
	  }
	  public int getIntP(Player pl)
	  {
		  for(int i=0;i<3; i++)
			  if(players[i].equals(pl))
				  return i;
		  return p;
	  }
	  public int getAge()
	  {
		  return age;
	  }
	  public int chooseWonder()
	  {
		  int ret=(int)(Math.random()*wonders.size());
		  int re=wonders.get(ret);
		  wonders.remove(ret);
		  return re;
	  }
	  public void deal()
	  {
		  for(int i=0; i<3; i++)
		  {
			  ArrayList<Cards> temp=new ArrayList<Cards>();
			  for(int s=0; s<7; s++)
				  temp.add(deck.removeCards());
			  hands.put(players[i], temp);
		  }
	  }
	  public void resetAge()
	  {
		  if(age<3)
		  {
			  war();
			  for(int a=0; a<3; a++)
			  {
				  if(players[a].getWonders().getName().equalsIgnoreCase("Olympia")&&players[a].getWonders().getStage()>=2)
					  players[a].setPlayed(true);
			  }
			  age++;
			  deck.age();
			  deal();
		  }
		  else
		  {
			  gameEnd=true;
		  }
	  }
	  public boolean gameEnded()
	  {
		  return gameEnd;
	  }
	  public void changeTurns()
	  {
		  if(resources.size()>0)
		  {
			  int x=getP().getResources().size()-resources.size();
			  for(int i=getP().getResources().size()-1; i>=x; i--)
				  getP().removeResources(i);
		  }
		  resources.clear();
		  if(p<3)
			  p++;
		  else
			  p=0;
		  if(p==0)
		  {
			  if(getHand(getP()).size()==1)
				  resetAge();
			  else if(age==1||age==3)
			  {
				  ArrayList<Cards> temp=hands.get(getP());
				  hands.put(getP(), getHand(getRightP(getP())));
				  ArrayList<Cards> te=hands.get(getLeftP(getP()));
				  hands.put(getLeftP(getP()), temp);
				  hands.put(getRightP(getP()), te);
			  }
			  else
			  {
				  ArrayList<Cards> temp=hands.get(getP());
				  hands.put(getP(), getHand(getLeftP(getP())));
				  ArrayList<Cards> te=hands.get(getRightP(getP()));
				  hands.put(getRightP(getP()), temp);
				  hands.put(getLeftP(getP()), te);
			  }
		  }
	  }
	  public Player getP()
	  {
		  if(p<3)
			  return players[p];
		  else
			  return null;
	  }
	  public void playGame(Cards c)
	  {
		  if(check(c))
		  {
			  hands.get(getP()).remove(c);
		  	  getP().addToAllPlayed(c);
		  	  doEffect(c);
		  }
	  }
	  public ArrayList<Cards> getHand(Player pp)
	  {
		  return hands.get(pp);
	  }
	  public boolean check(Cards c)
	  {	
		 String cost=c.getCost();
		 String[] oth=c.getTotalCost();
		 ArrayList<String> arr=new ArrayList<String>();
		 for(int ab=0; ab<10; ab++)
			 arr.add(""+ab);
		 if(cost.equals(" "))
			 return true;
		 else if(cost.contains(" or "))
		 {
			 if(getP().getCardNames().contains(oth[oth.length-1]))
				 return true;
			 else if(c.getName().equalsIgnoreCase("forum")&&(getP().getCardNames().contains("East Trading Post")||getP().getCardNames().contains("West Trading Post")))
				 return true;
			 else
			 {
				 cost=cost.substring(0,cost.indexOf(" or "));
				 String[] temp=new String[oth.length-2];
				 for(int i=0; i<oth.length-2; i++)
					 temp[i]=oth[i];
				 oth=temp;
			 }
		 }
		if(oth.length>1)
	  	{
	  		ArrayList<String> t=new ArrayList<String>();
	  		ArrayList<String> first=new ArrayList<String>();
	  		for(int a=0; a<getP().getResources().size(); a++)
	  		{
	  			if(getP().getResources().get(a).contains(" "))
	  				t.add(getP().getResources().get(a));
	  			else
	  				first.add(getP().getResources().get(a));
	  		}
	  		for(int b=0; b<first.size(); b++)
	  			t.add(first.get(b));
	  		ArrayList<String> paidFor=new ArrayList<String>(Arrays.asList(oth));
	  		for(int a=0; a<oth.length; a++)
	  		{
	  			for(int b=t.size()-1; b>=0; b--)
	  			{
	  				if(b>=t.size())
		  				b--;
	  				if(b>=t.size())
		  				b--;
	  				if(t.get(b).equalsIgnoreCase(oth[a]))
	  				{
	  					t.remove(t.get(b));
	  					paidFor.remove(oth[a]);
	  					break;
	  				}
	  				else if(t.get(b).contains(" "))
	  				{
	  					String[] or=t.get(b).split(" ");
	  					for(int k=0; k<or.length; k++)
	  						if(or[k].equalsIgnoreCase(oth[a]))
	  						{
	  							t.remove(t.get(b));
	  							paidFor.remove(oth[a]);
	  						}
	  				}
	  			if(paidFor.size()==0)
  					return true;
	  			}
	  		}
	  		if(paidFor.size()==0)
				return true;
	  	}
	  	else if(getP().getResources().contains(c.getCost()))
	  		return true;
	  	else if(arr.contains(c.getCost())&&getP().getTotalCoins()>=Integer.parseInt(c.getCost()))
	  	{
	  		getP().removeCoins(Integer.parseInt(c.getCost()));
	  		return true;
	  	}
	  	else
	  	{
	  		String[] temp;
	  		for(int i=0; i<getP().getResources().size(); i++)
	  		{
	  			if(getP().getResources().get(i).contains(" "))
	  			{
	  				temp=getP().getResources().get(i).split(" ");
	  			if(Arrays.asList(temp).contains(c.getCost()))
	  				return true;
	  			}
	  		}
	  	}
		if(getP().getPlayed())
		{
			getP().setPlayed(false);
			return true;
		}
		else
			return false;
	  }
	  public Player getRightP(Player pl)
	  {
		  if(getIntP(pl)==2)
			  return players[0];
		  else
			  return players[getIntP(pl)+1];
	  }
	  public Player getLeftP(Player pl)
	  {
		  if(getIntP(pl)==0)
			  return players[2];
		  else
			  return players[getIntP(pl)-1];
	  }
	  public Player getPlayer(int i)
	  {
		  return players[i];
	  }
	  public int getTurn()
	  {
		  return p;
	  }
	  public void discard(Cards c)
	  {
		  hands.get(getP()).remove(c);
		  getP().addCoins(3);
	  }
	  public void hide(Cards c)
	  {
		  hands.get(getP()).remove(c);
		  if(checkWonder())
		  {
			  doWonder();
			  getP().buildWonders();
		  }
	  }
	  public void pay(Player plyr, String r)
	  {
		  int left=p-1;
		  int right=p+1;
		  int cost=2;
		  if(p==0)
			  left=2;
		  else if(p==2)
			  right=0;
		  ArrayList<String> browns=new ArrayList<String>();
		  browns.add("wood");
		  browns.add("ore");
		  browns.add("stone");
		  browns.add("clay");
		  ArrayList<String> grays=new ArrayList<String>();
		  grays.add("glass");
		  grays.add("papyrus");
		  grays.add("loom");
		  if(players[left].equals(plyr)&&browns.contains(r))
			  cost=getP().getLeftB();
		  else if(players[left].equals(plyr)&&grays.contains(r))
			  cost=getP().getLeftG();
		  else if(players[right].equals(plyr)&&browns.contains(r))
			  cost=getP().getRightB();
		  else
			  cost=getP().getRightG();
		  if(getP().getTotalCoins()>=cost)
		  {
			  plyr.addCoins(cost);
		  getP().removeCoins(cost);
		  resources.add(r);
		  getP().addResource(r);
		  }
	  }
	  public void war()
	  {
		  int pwin0=0, pwin1=0, pwin2=0, ploss0=0, ploss1=0, ploss2=0;
		  int agenum;
		  if(age==1)
			  agenum=1;
		  else if(age==2)
			  agenum=3;
		  else
			  agenum=5;
		  if(players[0].getMCards()>players[1].getMCards())
		  {
			  pwin0++;
			  ploss1++;
		  }
		  else if(players[0].getMCards()<players[1].getMCards())
		  {
			  ploss0++;
			  pwin1++;
		  }
		  if(players[0].getMCards()>players[2].getMCards())
		  {
			  pwin0++;
			  ploss2++;
		  }
		  else if(players[0].getMCards()<players[2].getMCards())
		  {
			  ploss0++;
			  pwin2++;
		  }
		  if(players[1].getMCards()>players[2].getMCards())
		  {
			  pwin1++;
			  ploss2++;
		  }
		  else if(players[1].getMCards()<players[2].getMCards())
		  {
			  ploss1++;
			  pwin2++;
		  }
		  players[0].setNeg(-ploss0);
		  players[1].setNeg(-ploss1);
		  players[2].setNeg(-ploss2);
		  if(age==1)
		  {
			  players[0].setWins1(pwin0*agenum);
			  players[1].setWins1(pwin1*agenum);
			  players[2].setWins1(pwin2*agenum);
		  }
		  else if(age==2)
		  {
			  players[0].setWins3(pwin0*agenum);
			  players[1].setWins3(pwin1*agenum);
			  players[2].setWins3(pwin2*agenum);
		  }
		  else
		  {
			  players[0].setWins5(pwin0*agenum);
			  players[1].setWins5(pwin1*agenum);
			  players[2].setWins5(pwin2*agenum);
		  }
	  }
	  public void doEffect(Cards c)
	  {
		  getP().addToFree(c);
		  if(c.getColor().equalsIgnoreCase("grey")||c.getColor().equalsIgnoreCase("brown")||c.getName().equalsIgnoreCase("Caravansery")||c.getName().equalsIgnoreCase("Forum"))
		  {
			  if(c.getEffect().length>1&&c.getEffect()[1].equalsIgnoreCase("or"))
			  {
				  String temp="";
				  for(int b=0; b<c.getEffect().length; b+=2)
					  temp+=c.getEffect()[b]+" ";
				  temp=temp.substring(0, temp.length()-1);
				  getP().addResource(temp);
			  }
			  else
				  for(int b=0; b<c.getEffect().length; b++)
					  getP().addResource(c.getEffect()[b]);
		  }
		  else if(c.getColor().equalsIgnoreCase("blue"))
			  getP().addVPoints(c.getEffect()[0].length());
		  else if(c.getColor().equalsIgnoreCase("red"))
		  {
			  getP().setMCards(c.getEffect().length);
			  getP().addToContinues(c);
		  }
		  else if(c.getColor().equalsIgnoreCase("green"))
			  getP().addToContinues(c);
		  else
		  {
			  if(c.getName().equalsIgnoreCase("Tavern"))
				  getP().addCoins(5);
			  else if(c.getName().equalsIgnoreCase("East Trading Post"))
				  getP().setRightB(getP().getRightB()-1);
			  else if(c.getName().equalsIgnoreCase("West Trading Post"))
				  getP().setLeftB(getP().getLeftB()-1);
			  else if(c.getName().equalsIgnoreCase("Marketplace"))
			  {
				  getP().setRightG(getP().getRightG()-1);
				  getP().setLeftG(getP().getLeftG()-1);
			  }
			 String[] arr=c.getEffect();
			 if(age==2)
			 {
				 int d=Integer.parseInt(arr[0].substring(1,2)), cnt=0;
				 String[] fixed=arr[0].split("_");
				 String e=fixed[1];
				 for(int a=0; a<3; a++)
				 {
					 for(int b=0; b<players[a].getAllPlayed().size(); b++)
						 if(players[a].getAllPlayed().get(b).getColor().equalsIgnoreCase(e))
							 cnt++;
				 }
				 getP().addCoins(cnt*d);
			 }
			 }
		  }
	  public int yellowPts(Player j)
	  {
		  int total=0;
		  for(int i=0; i<j.getAllPlayed().size(); i++)
		  {
			  Cards c=j.getAllPlayed().get(i);
		  if(c.getAge()==3&&c.getColor().equalsIgnoreCase("yellow"))
			 {
				if(c.getName().equalsIgnoreCase("Arena"))
					total+=j.getWonders().getStage();
				else
				{
					int num=Integer.parseInt(c.getEffect()[0].substring(1,2)),cnt=0;
					for(int f=0; f<j.getAllPlayed().size(); f++)
					{
						String[] eff=c.getEffect()[0].split("_");
						if(j.getAllPlayed().get(f).getColor().equalsIgnoreCase(eff[2]))
							cnt++;
					}
					total+=cnt*num;
				}
			 }
		  }
		  return total;
	  }
	  public int purplePts(Player j)
	  {
		  int total=0;
		  for(int a=0; a<j.getAllPlayed().size(); a++)
		  {
			  Cards c=j.getAllPlayed().get(a);
		  if(c.getName().equalsIgnoreCase("Scientists Guild"))
				 getP().addToContinues(c);
		  else if(c.getName().equalsIgnoreCase("Builders Guild"))
				 for(int b=0; b<3; b++)
					 total+=players[b].getWonders().getStage();
			 else if(c.getName().equalsIgnoreCase("Strategists Guild"))
			 {
				 for(int b=0; b<3; b++)
					 if(!(b==getIntP(j)))
						 total+=-players[b].getTotalNegPoints();
			 }
			 else if(c.getName().equalsIgnoreCase("Shipowners Guild"))
			 {
				 for(int b=0; b<j.getAllPlayed().size(); b++)
				 {
					 String col=j.getAllPlayed().get(b).getColor();
					 if(col.equalsIgnoreCase("brown")||col.equalsIgnoreCase("grey")||col.equalsIgnoreCase("purple"))
						 total++;
				 }
			 }
			 else if(c.getColor().equalsIgnoreCase("purple"))
			 {
					 for(int d=0; d<3; d++)
					 {
						 if(!(d==getIntP(j)))
						 {
							 String[] eff;
							 for(int e=0; e<players[d].getAllPlayed().size(); e++)
							 {
								 eff=c.getEffect()[0].split("_");
								 if(eff.length>0&&players[d].getAllPlayed().get(e).getColor().equalsIgnoreCase(eff[1]))
									 total++;
							 }
						 }
					 }
				 }
			 }
		  return total;
	  }
	  public boolean checkWonder()
	  {
		  Wonders c=getP().getWonders();
		  int a=c.getStage()+1;
		  if(getP().getResources().contains(c.getReq(a)))
			  return true;
		  else if(c.getReq(a).length>1)
		  {
			  String[] res=c.getReq(a);
			  TreeMap<String, Integer> x=new TreeMap<String, Integer>();
			  for(int b=0; b<res.length; b++)
			  {
				  int num=0;
				  for(int d=0; d<getP().getResources().size(); d++)
				  {
					  if(res[b].equalsIgnoreCase(getP().getResources().get(d)))
						  num++;
					  else if(getP().getResources().get(d).contains(" "))
						{
							if(res[b].equalsIgnoreCase(getP().getResources().get(d).substring(0, getP().getResources().get(d).indexOf(" ")-1)))
								num++;
							else if(res[b].equalsIgnoreCase(getP().getResources().get(d).substring(getP().getResources().get(d).indexOf(" ")+1)))
								num++;
						}
				  }
				  x.put(res[b], num);
			  }
			  for(int s=0; s<res.length; s++)
			  {
				  if(!(getP().getResources().contains(res[s]))&&x.get(res[s])<=0)
					  return false;
				  else
				  {
					  x.put(res[s], x.get(res[s])-1);
				  }
			  }
			  return true;
		  }
		  else
			  return false;
	  }
	  public void doWonder()
	  {
		  Wonders w=getP().getWonders();
		  if(checkWonder())
		  {
		  int i=w.getStage()+1;
		  if(w.getEff(i)[0].contains("V"))
		  {
			  getP().addVPoints(w.getEff(i)[0].length());
			  getP().setWonderPts(w.getEff(i)[0].length());
		  }
		  else if(w.getEff(i)[0].contains("army"))
		  {
			  String[] temp=w.getEff(i)[0].split(" ");
			  getP().setMCards(temp.length);
		  }
		  else if(w.getEff(i)[0].contains("+"))
			  getP().addCoins(Integer.parseInt(w.getEff(i)[0].substring(1)));
		  else if(w.getEff(i).length==4)
			  getP().addResource("clay ore wood stone");
		  else if(w.getEff(i)[0].equalsIgnoreCase("1FreePerAge"))
			  getP().setPlayed(true);
		  else
			  getP().setDiscard(true);
		  }
	  }
	  public int getLoc(ArrayList<Integer> ab, int ac)
	  {
		  for(int s=0; s<ab.size(); s++)
			  if(ab.get(s)==ac)
				  return s;
		  return -1;
	  }
	  public String gameState()
	  {
		  if(getP()!=null)
			  return "Age "+age+"\r\n\r\nPlayer playing: "+p+"\r\n\r\nCoins: "+getP().getTotalCoins()+"\r\nVictory pts: "+
				  getP().getVPoints()+"\r\n\r\nMilitary points: "+getP().getMPoints()+"\r\n\r\nRounds remaining: "+(hands.get(getP()).size()-1)+"\r\n\r\n";
		  else
			  return "Age "+age+"\r\n\r\nReveal round \r\n\r\nRounds remaining: "+hands.get(players[0]).size()+"\r\n\r\n";
	  }
	  public String directions()
	  {
		  return "Build a stage: \r\nclick on stage, then click on card you¡¯ll use\r\n" + 
		  		 "Trade: \r\nclick on card, then on neighbor\r\nDiscard: \r\nclick on discard pile,"+
				 "then on card to discard";
	  }
	  public int getTotal(Player j)
	  {
		  return j.getMPoints()+j.getTotalCoins()/3+j.getBluePoints()+j.getWonderPts()+j.getSciPts()+purplePts(j)+yellowPts(j);
	  }
	  public int endGame(Player j)
	  {
		  int  ret=getTotal(j);
		  if(ret>getTotal(this.getRightP(j)))
		  {
			  if(ret>getTotal(this.getLeftP(j)))
				  return 1;
			  else
				  return 2;
		  }
		  else if(ret>getTotal(this.getLeftP(j)))
			  return 2;
		  else
			  return 3;
	  }
	  public String[] points(Player j)
	  {
		  String[] pts=new String[10];
		  pts[0]=""+getIntP(j);
		  pts[1]=""+j.getMPoints();
		  pts[2]=""+j.getTotalCoins()/3;
		  pts[3]=""+j.getBluePoints();
		  pts[4]=""+purplePts(j);
		  pts[5]=""+yellowPts(j);
		  pts[6]=""+j.getWonderPts();
		  pts[7]=""+j.getSciPts();
		  pts[8]=""+getTotal(j);
		  pts[9]=""+endGame(j);
		  return pts;
	  }
}