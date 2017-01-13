
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

public class Main extends JFrame
{
	JButton btnOpen,btnSearch,btnHelp;
	DataForm frmData;
	Search frmSearch;
	Main(String s)
	{
		super(s);
		try
		{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		}catch(Exception e){}
		frmData=new DataForm("Address  ");
		setIconImage(getToolkit().getImage("Logo.gif"));
		Dimension d=getToolkit().getScreenSize();
		getContentPane().add(frmData);
		
		addWindowListener(new WindowAdapter()
				{
					public void windowClosing(WindowEvent we)
					{
						System.exit(0);
					}
				}
				 );

		JToolBar jt=new JToolBar();
		btnOpen=new JButton  ("   Open   ");
		btnSearch=new JButton("  Search  ");
		btnHelp=new JButton  ("   Help    ");
		//jt.setIconImage(getToolkit().getImage("Logo.gif"));
		jt.add(btnOpen);
		jt.add(btnSearch);
		jt.add(btnHelp);

		getContentPane().add("North",jt);
		getContentPane().add("East",new Search("Address  "));
		setSize(d.width,d.height);
		setVisible(true);
	}
	public static void main(String args[])
	{
		new Main("Address Book");
	}
}
