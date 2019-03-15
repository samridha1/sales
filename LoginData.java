
import java.awt.GridLayout;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;


public class LoginData extends JFrame implements ActionListener{
	
	JLabel l1,l2;
    JTextField t1;
    JPasswordField p1;
    JButton b1,b2;
    JPanel p2;
    Connection con;
    Statement stn;
    ResultSet rs;
    int flag=0;    
    public LoginData()
    {
    	l1=new JLabel("username");
    	l2=new JLabel("Password");
    	t1=new JTextField(25);
    	p1=new JPasswordField(25);
    	b1=new JButton("Submit");
    	b2=new JButton("Cancel");
        p2= new JPanel(new GridLayout(3,2));
    	p2.add(l1);p2.add(t1);
    	p2.add(l2);p2.add(p1);
    	p2.add(b1);p2.add(b2);
    	add(p2);
    	b1.addActionListener(this);
    	b2.addActionListener(this);
    	setSize(300,300);
    	 setVisible(true);
         setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
     }
  
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		LoginData l=new LoginData();

	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		String s1,s2;
		try
		{
			 Class.forName("com.mysql.jdbc.Driver");
		}
		catch(Exception ee)
		{
			System.out.println(ee.toString());
		}
		try
		{
			
			if(e.getSource()==b2)
			{
				p2.setVisible(false);
				System.exit(0);
			}
			
			String s="jdbc:mysql://localhost:3306/Inventory";
			con=DriverManager.getConnection(s,"Abc","123y");
			 stn=con.createStatement();
			rs=stn.executeQuery("Select * from userlist");
			String x,y;
			while(rs.next())
			{
			x=rs.getString(2);
			y=rs.getString(3);
			//System.out.println(x+ "," + y);
				if(e.getSource()==b1)
				{
				  s1=t1.getText();
				   s2=p1.getText();
				   if(s1.equalsIgnoreCase(x)&&s2.equalsIgnoreCase(y))
				   {
					   MyMenu m=new MyMenu();flag=1;break;
					 //  p2.setEnabled(false);
				   }
				}
			}
			rs.close();
			con.close();stn.close();
		}
		catch(Exception x)
		{
		}
		if(flag==0)
		JOptionPane.showMessageDialog(this, "Invalid Username or Pasword!!!","Error",JOptionPane.ERROR_MESSAGE);
			
		
		
		
}
}


