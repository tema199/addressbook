
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class DataForm extends JInternalFrame implements ActionListener
{
	JLabel lblNo,lblName,lblAdd;
	JTextField txtNo,txtName,txtAdd;
	JButton btnFirst,btnPre,btnNext,btnLast,btnAdd,btnSave,btnDelete,btnCancel,btnExit;
	JPanel pNo,pName,pAdd,pNev,pSave;
	JPanel pMain,pSouth;
	Connection con;
	Statement stmt;
	ResultSet rs;
	boolean addFlag;
	DataForm(String s)
	{
		super(s,true,true);
		Dimension d=getToolkit().getScreenSize();
		pNo=new JPanel();	
		pName=new JPanel();	
		pAdd=new JPanel();	
		pNev=new JPanel();	
		pSave=new JPanel();	
		pMain=new JPanel();	
		pSouth=new JPanel();	
		pNo.setLayout(new FlowLayout(FlowLayout.LEFT));
		pName.setLayout(new FlowLayout(FlowLayout.LEFT));
		pAdd.setLayout(new FlowLayout(FlowLayout.LEFT));

		getContentPane().add(pMain);
		getContentPane().add("South",pSouth);

		pMain.setLayout(new GridLayout(13,1));
		pSouth.setLayout(new GridLayout(2,1));

		pMain.add(pNo);
		pMain.add(pName);
		pMain.add(pAdd);
		pSouth.add(pNev);
		pSouth.add(pSave);

		lblNo=new       JLabel("No                ");
		lblName=new     JLabel("Name          ");
		lblAdd=new      JLabel("Address      ");
		txtNo=new   JTextField(20);txtNo.setEditable(false);
		txtName=new JTextField(20);
		txtAdd=new  JTextField(20);

		btnFirst=new  JButton("     <<     ");
		btnPre=new    JButton("     <      ");
		btnNext=new   JButton("      >     ");
		btnLast=new   JButton("     >>     ");
		btnAdd=new    JButton("    Add     ");
		btnSave=new   JButton("    Save    ");
		btnDelete=new JButton("   Delete   ");
		btnCancel=new JButton("   Cancel   ");
		btnExit=new   JButton("    Exit    ");

		btnFirst.setMnemonic('F');
		btnPre.setMnemonic('P');
		btnNext.setMnemonic('N');
		btnLast.setMnemonic('L');
		btnAdd.setMnemonic('A');
		btnSave.setMnemonic('S');
		btnDelete.setMnemonic('D');
		btnCancel.setMnemonic('C');
		btnExit.setMnemonic('x');


		btnFirst.addActionListener(this);
		btnPre.addActionListener(this);
		btnNext.addActionListener(this);
		btnLast.addActionListener(this);
		btnFirst.setActionCommand("FPNL");
		btnPre.setActionCommand("FPNL");
		btnNext.setActionCommand("FPNL");
		btnLast.setActionCommand("FPNL");

		btnAdd.addActionListener(this);
		btnSave.addActionListener(this);
		btnDelete.addActionListener(this);
		btnCancel.addActionListener(this);
		btnExit.addActionListener(this);



		pNo.add(lblNo);
		pNo.add(txtNo);
		pName.add(lblName);
		pName.add(txtName);
		pAdd.add(lblAdd);
		pAdd.add(txtAdd);
		pNev.add(btnFirst);
		pNev.add(btnPre);
		pNev.add(btnNext);
		pNev.add(btnLast);
		pSave.add(btnAdd);
		pSave.add(btnSave);
		pSave.add(btnDelete);
		pSave.add(btnCancel);
		pSave.add(btnExit);

		try
		{
			con=DriverManager.getConnection("Jdbc:Odbc:Address");
			stmt=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			rs=stmt.executeQuery("select * from Add1");
			rs.next();
			disp();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		setSize(d.width,d.height);
		//setResizable(false);
		setVisible(true);
	}
	void disp()
	{
		try
		{
			txtNo.setText(rs.getString(1));
			txtName.setText(rs.getString(2));
			txtAdd.setText(rs.getString(3));
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
	void clear()
	{
		try
		{
			txtNo.setText("");
			txtName.setText("");
			txtAdd.setText("");
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	void enaProc(boolean b)
	{
		btnFirst.setEnabled(b);
		btnPre.setEnabled(b);
		btnNext.setEnabled(b);
		btnLast.setEnabled(b);
		btnAdd.setEnabled(b);
		btnDelete.setEnabled(b);
	}
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getActionCommand().equals("FPNL"))
		{
			try
			{
				if(ae.getSource().equals(btnFirst))	
					rs.first();
				else if(ae.getSource().equals(btnPre))
				{
					rs.previous();
					if(rs.isBeforeFirst())
						rs.first();
				}
				else if(ae.getSource().equals(btnNext))
				{
					rs.next();
					if(rs.isAfterLast())
						rs.last();
				}
				else
					rs.last();
				disp();
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
		}
		if(ae.getSource().equals(btnAdd))
		{
			try
			{
				clear();
				addFlag=true;
				enaProc(false);
				rs.last();
				txtNo.setText(""+(Integer.parseInt(rs.getString(1))+1));
				txtName.requestFocus();
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
		}
		if(ae.getSource().equals(btnSave))
		{
			try
			{
				Statement st=con.createStatement();
				if(addFlag)
				{
					int result=st.executeUpdate("insert into Add1(FName,Address)"+
							"values(\'"+
							txtName.getText()+"\',\'"+
							txtAdd.getText()+"\')");
				}
				else
				{
					int result=st.executeUpdate("update Add1 set FName = \'"+
					txtName.getText()+"\',Address = \'"+txtAdd.getText()+
					"\' where IdNo = "+txtNo.getText());
				}
				rs=stmt.executeQuery("select * from Add1");
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
			enaProc(true);					
			addFlag=false;
		}
		if(ae.getSource().equals(btnDelete))
		{
			try
			{
				rs.deleteRow();
				rs.next();
				if(rs.isAfterLast())
					rs.last();
				disp();
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
		}
		if(ae.getSource().equals(btnCancel))
		{
			try
			{
				addFlag=false;
				rs.last();
				disp();
				enaProc(true);
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
		}		
		if(ae.getSource().equals(btnExit))
		{
			dispose();
		}
	}
}
