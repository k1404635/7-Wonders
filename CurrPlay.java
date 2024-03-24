import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.table.*;
import java.awt.event.*;
import java.util.*;
public class CurrPlay extends JPanel implements MouseListener
{
	private Player myP, currP;
	private Board b;
	private int w, h, t, wonderPoint;
	private int[] handValues;
	private JTextArea gs;
	private boolean discard, wonder, choose;
	private ArrayList<Integer> usedLocsL, usedLocsR;
	public CurrPlay(Board board, int width, int height) throws IOException
	{
		super();
		b=board;
		usedLocsR=new ArrayList<Integer>();
		usedLocsL=new ArrayList<Integer>();
		discard=false;
		wonder=false;
		choose=true;
		handValues=new int[4];
		this.setLayout(null);
		gs=new JTextArea();
		gs.setBounds(55, 0, 200, 115);
		gs.setEditable(false);
		gs.setText(b.directions());
		JScrollPane scroll = new JScrollPane (gs);
	    scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	    scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	    scroll.setBounds(75,0,125,150);
	    scroll.setVisible(true);
	    scroll.setEnabled(true);
		add(gs);
		w=width;
		wonderPoint=w/4+35;
		h=height;
		t=0;
		myP=b.getP();
		currP=b.getP();
		setSize(w, h);
		addMouseListener(this);
		repaint();
	}
	public boolean currPlayer()
	{
		if(currP==null)
			return false;
		else if(currP.equals(myP))
			return true;
		else
			return false;
	}
	public boolean restRound()
	{
		if(b.getTurn()>2)
			return true;
		else
			return false;
	}
	public void changePerspective(Player p)
	{
		myP=p;
		t=b.getIntP(myP);
		repaint();
	}
	public void turnAround()
	{
		b.changeTurns();
		myP=b.getP();
		usedLocsR.clear();
		usedLocsL.clear();
		t=b.getIntP(myP);
		currP=b.getP();
		repaint();
	}
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Image img1=null;
		try
		{
			img1 = ImageIO.read((new File("C:\\CompSci\\brownbackground.jpg")));
		}
		catch(Exception e)
		{
			System.out.println("Error loading images: " + e.getMessage());
		}
        g.drawImage(img1, 0, 0, null);
        if(b.gameEnded())
        	ended(g);
        else if(b.getTurn()<=2)
		{
			if(myP.getWonders().getStage()==0)
				wonderPoint=w/4+25;
			else if(myP.getWonders().getStage()==1)
				wonderPoint=w/4+185;
			else if(myP.getWonders().getStage()==2)
				wonderPoint=w/4+345;
			else
				wonderPoint=w/4+505;
			if(currPlayer())
				played(g);
			else
				neighborPlayed(g);
			board(g);
			info(g);
			hand(g);
		}
		else
		{
			reveal(g);
		}
	}
	public void info(Graphics g)
	{
		BufferedImage img1=null;
		try
		{
			img1 = ImageIO.read((new File("C:\\Compsci\\testing\\coin.png")));
		}
		catch(Exception e)
		{
			System.out.println("Error loading : " + e.getMessage());
		}
		g.drawImage(img1, 50, h-325, null);
		//JTextArea coins=new JTextArea();
		//coins.setBounds(150, h-300, 100, 300);
		//String stuff=myP.getTotalCoins()+"\n\r\n\r"+myP.getM5Pts()+"\n\r\n\r"+myP.getM3Pts()+"\n\r\n\r"+myP.getM1Pts()+"\n\r\n\r"+myP.getVPoints();
		Font a=new Font("Serif", Font.BOLD, 40);
		g.setFont(a);
		g.setColor(Color.WHITE);
		g.drawString("Age "+b.getAge(), 75, h-400);
		Font c=new Font("Serif", Font.BOLD, 20);
		g.setFont(c);
		g.drawString(("Showing player " +b.getIntP(myP)), 25, h-350);
		g.setFont(a);
		g.drawString((": "+myP.getTotalCoins()), 125, h-300);
		g.drawString((": "+myP.getM5Pts()), 125,  h-230);
		g.drawString((": "+myP.getM3Pts()), 125, h-160);
		g.drawString((": "+myP.getM1Pts()), 125, h-100);
		g.drawString(": "+myP.getTotalNegPoints(), 125, h-50);
		g.drawString("Discard", w-140, h/2-15);
		try
		{
			img1 = ImageIO.read((new File("C:\\Compsci\\testing\\v5.png")));
		}
		catch(Exception e)
		{
			System.out.println("Error loading : " + e.getMessage());
		}
		g.drawImage(img1, 50, h-265, null);
		try
		{
			img1 = ImageIO.read((new File("C:\\Compsci\\testing\\victory3.png")));
		}
		catch(Exception e)
		{
			System.out.println("Error loading : " + e.getMessage());
		}
		g.drawImage(img1, 50, h-200, null);
		try
		{
			img1 = ImageIO.read((new File("C:\\Compsci\\testing\\victory1.png")));
		}
		catch(Exception e)
		{
			System.out.println("Error loading : " + e.getMessage());
		}
		g.drawImage(img1, 50, h-140, null);
		try
		{
			img1 = ImageIO.read((new File("C:\\Compsci\\testing\\victoryminus1.png")));
		}
		catch(Exception e)
		{
			System.out.println("Error loading : " + e.getMessage());
		}
		g.drawImage(img1, 50, h-85, null);
		//coins.setText(stuff);
		
	}
	public void ended(Graphics g)
	{
		String[][] scores=new String[4][11];
		String[] header= {"Player","Military points", "Coins", "Civics", "Guilds", "Commercial", "Wonders", "Sciences","Total","Place"};
		scores[0]=header;
		Font font=new Font("Serif",Font.BOLD,16);
		g.setFont(font);
		int currX=10, currY=50;
		for(int i=1; i<4; i++)
    		scores[i]=b.points(b.getPlayer(i-1));
		for(int a=0; a<scores[0].length; a++)
		{
			currY=50;
			if(a==0||a==2||a==6||a==8||a==9)
				g.setColor(Color.WHITE);
			else if(a==1)
				g.setColor(Color.RED);
			else if(a==3)
				g.setColor(Color.BLUE);
			else if(a==4)
				g.setColor(new Color(148, 0, 211));
			else if(a==5)
				g.setColor(Color.YELLOW);
			else if(a==7)
				g.setColor(Color.GREEN);
			//else if(a==8)
			
			for(int b=0; b<scores.length; b++)
			{
				currY+=100;
				g.drawString(scores[b][a], currX, currY);
			}
			currX+=w/12;
		}
		/*JFrame end=new JFrame();
		//end.setBackground(Color.brown);
		end.setVisible(true);
		end.setSize(600, 700);
		end.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		String[] header= {"Player","Military points", "Points from Coins", "Civics Cards", "Guilds", "Commercial Structures", "Wonders", "Sciences","Total Points","Place"};
    	String[][] scoreBoard=new String[3][10];
    	for(int i=0; i<3; i++)
    		scoreBoard[i]=b.points(b.getPlayer(i));
    	JTable board=new JTable(scoreBoard, header);
    	board.setRowHeight(100);
    	board.setBounds(0,0,600,700);
    	//board.setVisible(true);
    	//remove(gs);
    	//add(new JScrollPane(board));
    	end.remove(gs);
    	end.add(new JScrollPane(board));*/
	}
	public void reveal(Graphics g)
	{
		BufferedImage img1=null;
		String imgName="";
		int currX=w/4, currY=0;
		Font c=new Font("Serif", Font.BOLD, 20);
		g.setFont(c);
		g.setColor(Color.WHITE);
		for(int i=0; i<3; i++)
		{
			
			//if(i==0)
				//currY=h/4;
			//else if(i==1)
				currY=h/3;
			//else
				//currY=(3*h)/4;
			//for(int a=0; a<b.getPlayer(i).getAllPlayed().size(); a++)
			//{
				imgName="C:\\Compsci\\BestVersionYet\\"+b.getPlayer(i).getAllPlayed().get(b.getPlayer(i).getAllPlayed().size()-1).getName().toLowerCase().replaceAll(" ","")+".png";
				try
				{
					img1 = ImageIO.read((new File(imgName)));
				}
				catch(Exception e)
				{
					System.out.println("Error loading "+imgName+": " + e.getMessage());
				}
				//System.out.println(imgName);
				g.drawString("Player "+i, currX, currY-15);
				g.drawImage(img1, currX, currY, null);
				currX+=120+w/10;
			//}
		}
	}
	public void board(Graphics g)
	{
		BufferedImage img1=null;
		String wonder=myP.getWonders().getName().toLowerCase();
		String imgName="";
		int currX=0;
		currX=wonderPoint-120;
		for(int i=0; i<myP.getWonders().getStage(); i++)
		{
		imgName="C:\\Compsci\\BestVersionYet\\age"+b.getAge()+".png";
		try
		{
			img1 = ImageIO.read((new File(imgName)));
		}
		catch(Exception e)
		{
			System.out.println("Error loading "+imgName+": " + e.getMessage());
		}
		g.drawImage(img1, currX, h/2+150, null);
		currX-=160;
		}
		imgName="C:\\Compsci\\BoardsResized\\"+wonder+"A.png";
		try
		{
			img1 = ImageIO.read((new File(imgName)));
		}
		catch(Exception e)
		{
			System.out.println("Error loading "+imgName+": " + e.getMessage());
		}
		g.drawImage(img1, w/4, h/2, null);
		imgName="C:\\Compsci\\BestVersionYet\\age"+b.getAge()+".png";
		try
		{
			img1 = ImageIO.read((new File(imgName)));
		}
		catch(Exception e)
		{
			System.out.println("Error loading "+imgName+": " + e.getMessage());
		}
		g.drawImage(img1, w-140, h/2,  null);
		imgName="C:\\CompSci\\testing\\arrow-88-48.png";
		try
		{
			img1 = ImageIO.read((new File(imgName)));
		}
		catch(Exception e)
		{
			System.out.println("Error loading "+imgName+": " + e.getMessage());
		}
		g.drawImage(img1, 0, 0, null);
		imgName="C:\\CompSci\\testing\\arrow-24-48.png";
		try
		{
			img1 = ImageIO.read((new File(imgName)));
		}
		catch(Exception e)
		{
			System.out.println("Error loading "+imgName+": " + e.getMessage());
		}
		g.drawImage(img1, w-50, 0, null);
	}
	public void hand(Graphics g)
	{
		String imgName="";
		BufferedImage img1=null;
		int currX=w/4;
		int currY=(3*h)/4;
		handValues[0]=currX;
		handValues[1]=currY;
		handValues[2]=currX+120*b.getHand(currP).size();
		handValues[3]=currY+183;
		for(int d=0; d<b.getHand(currP).size(); d++)
		{
			imgName="C:\\Compsci\\BestVersionYet\\"+b.getHand(currP).get(d).getName().toLowerCase().replaceAll(" ","")+".png";
			try
			{
				img1 = ImageIO.read((new File(imgName)));
			}
			catch(Exception e)
			{
				System.out.println("Error loading "+imgName+": " + e.getMessage());
			}
			g.drawImage(img1,  currX,  currY, null);
			currX+=120;
		}
	}
	public void played(Graphics g)
	{
		ArrayList<Cards> blue=new ArrayList<Cards>();
		ArrayList<Cards> purple=new ArrayList<Cards>();
		ArrayList<Cards> red=new ArrayList<Cards>();
		ArrayList<Cards> yellow=new ArrayList<Cards>();
		ArrayList<Cards> brownAndGray=new ArrayList<Cards>();
		ArrayList<Cards> green=new ArrayList<Cards>();
		for(int c=0; c<myP.getAllPlayed().size(); c++)
		{
			if(myP.getAllPlayed().get(c).getColor().equalsIgnoreCase("blue"))
				blue.add(myP.getAllPlayed().get(c));
			else if(myP.getAllPlayed().get(c).getColor().equalsIgnoreCase("purple"))
				purple.add(myP.getAllPlayed().get(c));
			else if(myP.getAllPlayed().get(c).getColor().equalsIgnoreCase("red"))
				red.add(myP.getAllPlayed().get(c));
			else if(myP.getAllPlayed().get(c).getColor().equalsIgnoreCase("yellow"))
				yellow.add(myP.getAllPlayed().get(c));
			else if(myP.getAllPlayed().get(c).getColor().equalsIgnoreCase("green"))
				green.add(myP.getAllPlayed().get(c));
			else
				brownAndGray.add(myP.getAllPlayed().get(c));
		}
		ArrayList<Cards> temp=new ArrayList<Cards>(myP.getAllPlayed());
		int currX, currY;
		HashMap<String, int[]> pixels=new HashMap<String, int[]>();
		int[] bs= {w/4+150-blue.size()*20, h/2-140-blue.size()*40};
		pixels.put("blue", bs);
		int[] ys= {w/4+315-yellow.size()*20, h/2-140-yellow.size()*40};
		pixels.put("yellow", ys);
		int[] rs= {w/4+480-red.size()*20, h/2-140-red.size()*40};
		pixels.put("red", rs);
		int[] gs= {w/4+645-green.size()*20,h/2-140-green.size()*40};
		pixels.put("green", gs);
		int[] ps= {w/4+810-purple.size()*20, h/2-140-green.size()*40};
		pixels.put("purple", ps);
		int[] gys= {w/4-brownAndGray.size()*20, h/2-brownAndGray.size()*30};
		pixels.put("brown", gys);
		pixels.put("grey", gys);
		for(int a=temp.size()-1; a>=0; a--)
		{
			String imgName="";
			BufferedImage img1=null;
			currX=pixels.get(temp.get(a).getColor())[0];
			currY=pixels.get(temp.get(a).getColor())[1];
				imgName="C:\\Compsci\\BestVersionYet\\"+temp.get(a).getName().toLowerCase().replaceAll(" ","")+".png";
				try
				{
					img1 = ImageIO.read((new File(imgName)));
				}
				catch(Exception e)
				{
					System.out.println("Error loading "+imgName+": " + e.getMessage());
				}
				g.drawImage(img1,  currX,  currY, null);
				pixels.get(temp.get(a).getColor())[0]+=20;
				pixels.get(temp.get(a).getColor())[1]+=30;
		}
	}
	public void neighborPlayed(Graphics g)
	{
		ArrayList<Cards> blue=new ArrayList<Cards>();
		ArrayList<Cards> purple=new ArrayList<Cards>();
		ArrayList<Cards> red=new ArrayList<Cards>();
		ArrayList<Cards> yellow=new ArrayList<Cards>();
		ArrayList<Cards> brownAndGray=new ArrayList<Cards>();
		ArrayList<Cards> green=new ArrayList<Cards>();
		for(int c=0; c<myP.getAllPlayed().size(); c++)
		{
			if(myP.getAllPlayed().get(c).getColor().equalsIgnoreCase("blue"))
				blue.add(myP.getAllPlayed().get(c));
			else if(myP.getAllPlayed().get(c).getColor().equalsIgnoreCase("purple"))
				purple.add(myP.getAllPlayed().get(c));
			else if(myP.getAllPlayed().get(c).getColor().equalsIgnoreCase("red"))
				red.add(myP.getAllPlayed().get(c));
			else if(myP.getAllPlayed().get(c).getColor().equalsIgnoreCase("yellow"))
				yellow.add(myP.getAllPlayed().get(c));
			else if(myP.getAllPlayed().get(c).getColor().equalsIgnoreCase("green"))
				green.add(myP.getAllPlayed().get(c));
			else
				brownAndGray.add(myP.getAllPlayed().get(c));
		}
		ArrayList<Cards> trial=new ArrayList<Cards>(myP.getAllPlayed());
		if(b.getIntP(myP)<b.getIntP(currP))
			trial.remove(trial.size()-1);
		Collections.sort(trial);
		HashMap<String, int[]> tester=new HashMap<String, int[]>();
		int[] adder= {255, 0};
		tester.put("brown", adder);
		tester.put("grey", adder);
		int[] b={285, 50};
		tester.put("blue", b);
		int[] gr= {255, 80};
		tester.put("green", gr);
		int[] y= {285, 110};
		tester.put("yellow", y);
		int[] r={255, 140};
		tester.put("red", r);
		int[] p= {285, 170};
		tester.put("purple", p);
		int currX,currY;
		for(int a=0; a<trial.size(); a++)
		{
			String imgName="";
			BufferedImage img1=null;
			currX=tester.get(trial.get(a).getColor())[0];
			currY=tester.get(trial.get(a).getColor())[1];
				imgName="C:\\Compsci\\BestVersionYet\\"+trial.get(a).getName().toLowerCase().replaceAll(" ","")+".png";
				try
				{
					img1 = ImageIO.read((new File(imgName)));
				}
				catch(Exception e)
				{
					System.out.println("Error loading "+imgName+": " + e.getMessage());
				}
				g.drawImage(img1,  currX,  currY, null);
				if(trial.get(a).getColor().equalsIgnoreCase("brown")||trial.get(a).getColor().equalsIgnoreCase("grey"))
					tester.get(trial.get(a).getColor())[0]+=100;
				else
					tester.get(trial.get(a).getColor())[0]+=140;
		}
	}
	public void otherPlayers(MouseEvent e)
	{
		if(e.getX()<=50)
			changePerspective(b.getLeftP(b.getPlayer(t)));
		else
			changePerspective(b.getRightP(b.getPlayer(t)));
	}
	public void player(MouseEvent e)
	{
		int wonderXEnd=wonderPoint+200;
		int wonderYEnd=h/2+250;
		if(b.getAge()==3&&b.getHand(myP).size()==2&&choose)
		{
			if(myP.getCardNames().contains("Scientists Guild")||(myP.getWonders().getName().equalsIgnoreCase("Babylon")&&myP.getWonders().getStage()>=2))
			{
				String[] options= {"mathematics", "engineering", "writing"};
				int x = JOptionPane.showOptionDialog(null, "The end of the game is here. Please choose which you would like: ",
		              "Game ending",
		            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
				myP.setSciPts(options[x]);
				choose=false;
			}
		}
		if(e.getY()>=handValues[1]&&e.getY()<=handValues[3]&&e.getX()>=handValues[0]&&e.getX()<=handValues[2])
		{
			int cX=handValues[0]-120;
			for(int i=0; i<b.getHand(myP).size(); i++)
			{
				cX+=120;
				if(e.getX()>=cX&&e.getX()<=(cX+120))
				{
					Cards c=b.getHand(myP).get(i);
					if(discard)
					{
						b.discard(c);
						discard=false;
					}
					else if(wonder)
					{
						b.hide(c);
						wonder=false;
					}
					else
						b.playGame(c);
					if(!b.getHand(myP).contains(c))
						turnAround();
					break;
				}
			}
		}
		else if(e.getX()>=wonderPoint&&e.getX()<=wonderXEnd&&e.getY()<=wonderYEnd&&e.getY()>=(h/2+157)&&myP.getWonders().getStage()<3)
		{
			if(b.checkWonder())
			{
			wonder=true;
			wonderPoint+=160;
			String[] options= {"OK"};
			int x = JOptionPane.showOptionDialog(null, "The card you click next will be automatically used to build a wonder",
	              "Pick a card to build with",
	            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
			}
		}
		else if(e.getX()>=w-140&&e.getY()>=h/2&&e.getY()<=(h/2+183))
		{
			String[] options= {"OK"};
			int x = JOptionPane.showOptionDialog(null, "The card you click next will be automatically discarded",
	              "Pick a card to discard",
	            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
			discard=true;
		}
	}

	public void neighbors(MouseEvent e)
	{
		
		ArrayList<Integer> usedX=new ArrayList<Integer>();
		if(myP.equals(b.getRightP(currP)))
			usedX=usedLocsR;
		else
			usedX=usedLocsL;
		//if(myP.equals(b.getRightP(currP)))
			
		if(e.getY()<=50&&e.getX()>=255&&e.getX()<=(255+myP.getResourceCards().size()*100))
		{
			//System.out.println("hola");
			int cX=145;
			for(int i=0; i<myP.getResourceCards().size(); i++)
			{
				cX+=100;
				if(e.getX()>=cX&&e.getX()<=(cX+100)&&!(usedX.contains(cX)))
				{
					Cards c=myP.getResourceCards().get(i);
					String r="";
					if(c.getEffect().length<3)
						r=c.getEffect()[0];
					else if(c.getEffect().length==3)
					{
						String[] options= {c.getEffect()[0], c.getEffect()[2]};
						int x = JOptionPane.showOptionDialog(null, "What would you like to trade for?",
				              "Pick a resource",
				            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
						if(x==0)
							r=c.getEffect()[0];
						else
							r=c.getEffect()[2];
					}
					if(!(c.getEffect().length==2))
						usedX.add(cX);
					if(myP.equals(b.getRightP(currP)))
						usedLocsR=usedX;
					else
						usedLocsL=usedX;
					b.pay(myP, r);
					//myP.getResourceCards().set(index, element)
					break;
				}
			}
		}
		else if(e.getY()>=h/2&&e.getY()<=(h/2+25)&&e.getX()>=w/4&&e.getX()<=(w/4+120))
			b.pay(myP, myP.getResources().get(0));
	}
	
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {
		repaint();
	}
	public void mouseExited(MouseEvent e) {}
	public void mouseClicked(MouseEvent e)
	{
		if(e.getY()<=50&&(e.getX()<=50||e.getX()>=w-50))
			otherPlayers(e);
		if(currPlayer())
			player(e);
		else if(restRound())
			turnAround();
		else
			neighbors(e);
		gs.setText(b.directions());
		repaint();
	}
}