import java.awt.*;
import java.util.ArrayList;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
public class ParentGraphics extends JFrame implements MouseListener
{
	private CurrPlay panel;
	int tracker, curr;
	private Board b;
	public ParentGraphics(Board board) throws IOException
	{
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setUndecorated(true);
		setVisible(true);
		b=board;
		tracker=0;
		curr=0;
		panel=new CurrPlay(b, WIDTH, HEIGHT);
	}
	public void turnChange()
	{
		b.changeTurns();
		//panel.turnAround(b.getP());
		curr=b.getIntP(b.getP());
		tracker=b.getIntP(b.getP());
	}
	public void showOtherPlayers(MouseEvent e)
	{
		if(curr==3)
			panel.repaint();
		else if(e.getX()<=50)
		{
			if(tracker==0)
			{
				//panel.changePerspective(b.getIntP());
				panel.repaint();
			}
			//else
				//panels[--tracker].repaint();
		}
		else
		//{
			if(tracker==2)
			//{
				//panels[0].repaint();
				tracker=0;
		//	}
			//else
			//	panels[++tracker].repaint();
		//}*/
	}
	public void mouseEntered(MouseEvent e)
	{
		panel.repaint();
		//panels[tracker].repaint();
	}
	public void mouseExited(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseClicked(MouseEvent e)
	{
		if(e.getY()<=50&&(e.getX()<=50||e.getX()>=WIDTH-50))
			showOtherPlayers(e);
		repaint();
	}
}