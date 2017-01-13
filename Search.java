
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class Search extends JInternalFrame implements ActionListener,KeyListener
{
	JLabel lblField;
	JTextField txtField;
	JButton btnView,btnOk;
	List jlField,jlResult;
	JPanel pMain,pSouth;

	Connection con;
	Statement stmt;
	ResultSet rs;

	Search(String s)
	{
		super(s,true,true);
		lblField=new JLabel("Select Field............");
		txtField =new JTextField(30);
		jlField =new List();
		jlResult =new List();
		btnView=new JButton("  View  ");
		btnOk=new JButton("   Ok   ");		
		pMain=new JPanel();
		pMain.setLayout(new GridLayout(3,1));
		pSouth=new JPanel();
		getContentPane().add("South",pSouth);
		getContentPane().add("North",lblField);
		getContentPane().add(pMain);
		JPanel tmp;
		tmp=new JPanel();
		tmp.add(jlField);
		pMain.add(tmp);
		tmp=new JPanel();
		tmp.add(txtField);
		pMain.add(tmp);

		JScrollPane jsp=new JScrollPane(jlResult);
		pMain.add(jsp);
		
		pSouth.add(btnView);
		pSouth.add(btnOk);

		txtField.addKeyListener(this);

		try
		{
			con=DriverManager.getConnection("Jdbc:Odbc:Address");
			stmt=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			rs=stmt.executeQuery("select * from Add1");
			//rs.next();
			disp();
		}
		catch(Exception e)
		{
			System.out.println("qwqw   "+e);
		}
		jlField.add("Record No                     ");
		jlField.add("Name");
		jlField.add("Address");

		setSize(200,400);
		//setResizable(false);
		setVisible(true);
	}
	void disp()
	{
		try
		{
			while(rs.next())
				jlResult.add(rs.getString(1)+"     "+rs.getString(2));
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		jlResult.select(20);
			
	}
	public void keyPressed(KeyEvent ke){}
	public void keyTyped(KeyEvent ke){}
	public void keyReleased(KeyEvent ke) 
	{
		try
		{
			int tmp=Integer.parseInt(txtField.getText());
			jlResult.select(--tmp);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	public void actionPerformed(ActionEvent ae)
	{
	}
}
