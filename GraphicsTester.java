import javax.swing.*;
import java.io.*;
public class GraphicsTester
{
	public static void main(String[] args) throws IOException
	{
		JFrame y=new JFrame("Seven Wonders");
		y.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		y.setExtendedState(JFrame.MAXIMIZED_BOTH);
		y.setUndecorated(true);
		y.setVisible(true);
		Board b=new Board();
		y.add(new CurrPlay(b, y.getWidth(), y.getHeight()));
		RepaintManager.currentManager(y);
	}
}