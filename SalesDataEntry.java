import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.Vector;
import javax.swing.*;

public class SalesDataEntry extends JFrame implements ActionListener,ItemListener
{
	JLabel l1,l2,l3,l4,l5,l6;
	JTextField txtSlno,txtItemNo,txtItemName,txtSPrice,txtQty,txtSaledate;
	JButton btnNew, btnSave,btnDelete,btnEdit,btnUpdate,btnFirst,btnNext,btnPrevious,btnLast;
	JPanel panelFields, panelButtons,panelMain;
	Container c;
	Connection cn=null;
	Statement sm=null;	Statement stn1=null;
	ResultSet rs=null;
	PreparedStatement ps=null;
	JComboBox numbox;
	Vector <Integer> results=new Vector <Integer>();
	Vector <String> name=new Vector <String>();
	int x,y;
	
	public SalesDataEntry()
	{
		super("Inventory Application");
		initializeDatabase();
		initializeCompoments();
	
		setSize(600,400);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	public void dispose()
	{
		super.dispose();
		try
		{
			rs.close();sm.close();cn.close();
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
		//System.exit(0);
	}
	
	private void initializeCompoments() 
	{
		c=this.getContentPane();
		c.setLayout(new BorderLayout());
		panelMain=new JPanel();
		panelMain.setLayout(new BoxLayout(panelMain,BoxLayout.Y_AXIS));
		panelFields=new JPanel(new GridLayout(6,2,10,10));
		panelButtons=new JPanel(new GridLayout(2,4,10,10));
		l1=new JLabel("Sl.No",JLabel.RIGHT);
		l2=new JLabel("Item Code",JLabel.RIGHT);
		l3=new JLabel("Item name",JLabel.RIGHT);
		l4=new JLabel("Quantity Sold",JLabel.RIGHT);
		l5=new JLabel("Sale Price",JLabel.RIGHT);
		l6=new JLabel("Sale Date",JLabel.RIGHT);
		//txtItemNo=new JTextField(25);
		txtItemName=new JTextField(25);
		txtSPrice=new JTextField(25);	      txtQty=new JTextField(25);
		txtSaledate=new JTextField(25);       txtSlno=new JTextField(25);
		btnNew=new JButton("New");		      btnSave=new JButton("Save");
		btnDelete=new JButton("Delete");
		btnFirst=new JButton("First");	      btnNext=new JButton("Next");
		btnPrevious=new JButton("Previous");  btnLast=new JButton("Last");
	
	
	    panelFields.add(l1);panelFields.add(txtSlno);
	    panelFields.add(l2);panelFields.add(numbox);
	    panelFields.add(l3);panelFields.add(txtItemName);
	    panelFields.add(l4);panelFields.add(txtQty);
	    panelFields.add(l5);panelFields.add(txtSPrice);
	    panelFields.add(l6);panelFields.add(txtSaledate);
	    panelFields.setBorder(BorderFactory.createLineBorder(Color.blue, 3));
	
	    panelButtons.add(btnNew);panelButtons.add(btnSave);
	    panelButtons.add(btnDelete); panelButtons.add(btnFirst); panelButtons.add(btnNext);
	    panelButtons.add(btnPrevious); panelButtons.add(btnLast);
    	panelButtons.setBorder(BorderFactory.createLineBorder(Color.red, 3));
 
	    btnNew.addActionListener(this); btnSave.addActionListener(this);
	    btnDelete.addActionListener(this); btnFirst.addActionListener(this);
	    btnNext.addActionListener(this); btnPrevious.addActionListener(this);
	    btnLast.addActionListener(this); 
	
		
     	panelMain.add(panelFields);
	    panelMain.add(panelButtons);
	    c.add(panelMain,"Center");
	    c.add(new JLabel(""),"South");
	    c.add(new JLabel(""),"North");
	    c.add(new JLabel(""),"East");
	    c.add(new JLabel(""),"West");
	    txtSlno.setEditable(false);
	    txtItemName.setEditable(false);
}

	private void initializeDatabase() 
	{
	    try
	    {
	    	Class.forName("com.mysql.jdbc.Driver");
	    	String cs="jdbc:mysql://localhost:3306/Inventory";
	    	cn=DriverManager.getConnection(cs,"ABC","123y");
	    	sm=cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
	    	rs=sm.executeQuery("Select * from Sales");
	    	firstClick();
		
		
	    	Statement stn1=cn.createStatement();
	    	ResultSet res=stn1.executeQuery("Select itemno,itemname from Stock");

		    while(res.next())
		    {
		    	results.add(res.getInt(1));    name.add(res.getString(2));
		    }
		    //stn1.close(); 

		    numbox=new JComboBox(results);    numbox.addItemListener(this);
	}
		
	catch(Exception e)
	{
		System.out.println(e.toString());
	}
		
}

	
	

	public static void main(String[] args) 
	{
		new SalesDataEntry();
	}


	@Override
	public void actionPerformed(ActionEvent e) 
	{
		String s=e.getActionCommand();
		if(s.equalsIgnoreCase("New"))
		{
			btnNew.setEnabled(false);
	        newrec();
			//resetFields();
		}
		if(s.equalsIgnoreCase("Save"))
		{
			saveClick();
		}
		if(s.equalsIgnoreCase("Delete"))
		{
			deleteClick();
		}
		if(s.equalsIgnoreCase("First"))
		{
			firstClick();
		}
		if(s.equalsIgnoreCase("Next"))
		{
			nextClick();
		}
		if(s.equalsIgnoreCase("Last"))
		{
			lastClick();
		}
		if(s.equalsIgnoreCase("Previous"))
		{
			previousClick();
		}
		
	}

        private void firstClick()
        {
        	try
        	{
        		if(rs.first())
        		{
        			fillFields();
        		}
        	}
        	catch(Exception e)
        	{
        		System.out.println(e.toString());
        	}
		
        }
        private void lastClick() 
        {
        	try
        	{
        		if(rs.last())
        		{
        			fillFields();
        		}
        	}
        	catch(Exception e)
        	{
        		System.out.println(e.toString());
        	}
		
        }
        private void previousClick()
        {
        	try
        	{
        		if(rs.previous())
        		{
        			fillFields();
        		}
        		if(rs.isBeforeFirst())
        		{
        			lastClick();return;
        		}
        	}
        	catch(Exception e)
        	{
        		System.out.println(e.toString());
        	}
		}

	

	    private void nextClick() 
	    {
	    	try
	    	{
	    		if(rs.next())
	    		{
	    			fillFields();
	    		}
	    		if(rs.isAfterLast())
	    		{
	    			firstClick();return;
	    		}
	    	}
	    	catch(Exception e)
	    	{
	    		System.out.println(e.toString());
	    	}
		}

	    private void deleteClick() 	
	    {
	    	int x=JOptionPane.showConfirmDialog(this,"Delete - Y/N ? ", "Delete Record",JOptionPane.YES_NO_OPTION);
	    	if(x==JOptionPane.YES_OPTION)
	    	{
	    		try
	    		{
	    			PreparedStatement ps=cn.prepareStatement("Delete from sales where Slno=?");
	    			ps.setInt(1,Integer.parseInt(txtSlno.getText()));
	    			ps.execute();
	    			resetFields();
	    			rs=sm.executeQuery("Select *from sales");
	    			previousClick();
	    		}
	    		catch(Exception e)
	    		{
	    			e.printStackTrace();
	    		}
	    	}
		}

	private void saveClick() 
	{
		try
		{
			if(btnNew.isEnabled())
			{
				txtItemNo.setEditable(false);
		txtItemName.requestFocus();
			ps=cn.prepareStatement("Update sales set Itemno=?, " + 
					" qtysold=?,saleprice=?, saledate=? where Slno = ? ");
			ps.setInt(5,Integer.parseInt(txtSlno.getText()));
			ps.setInt(1,Integer.parseInt(txtItemNo.getText()));
			ps.setInt(2,Integer.parseInt(txtQty.getText()));
			ps.setDouble(3,Double.parseDouble(txtSPrice.getText()));
		  //ps.setDouble(3,Double.parseDouble(txtQoh.getText()));
			ps.setDate(4,java.sql.Date.valueOf(txtSaledate.getText()));
			ps.execute();
			rs=sm.executeQuery("Select * from sales");
			btnNew.setEnabled(true);
			}
		else
		{
			ps=cn.prepareStatement("Insert into sales (slno,itemno,qtysold, " + 
				" salePrice,saledate) values(?,?,?,?,?)");
			ps.setInt(1,Integer.parseInt(txtSlno.getText()));
			//ps.setInt(2,Integer.parseInt(numbox.getText()));
			ps.setInt(2,y);
			ps.setInt(3,Integer.parseInt(txtQty.getText()));
			ps.setDouble(4,Double.parseDouble(txtSPrice.getText()));
			ps.setDate(5, java.sql.Date.valueOf(txtSaledate.getText()));
			//ps.setString(5, txtUpdate.getText());			
								
		
		ps.execute();
		rs=sm.executeQuery("Select * from Sales");
		btnNew.setEnabled(true);
		txtSlno.setEditable(false);
		}
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
		
	}


	

	private void resetFields() 
	{
		txtSlno.setText("");
		//txtItemNo.setText("");
		numbox.setSelectedItem("");
		txtSPrice.setText("");
		txtQty.setText("");txtSaledate.setText("");
	}
	
	private void fillFields() 
	{
	try
	{
		txtSlno.setText(rs.getString("Slno"));
		//txtItemNo.setText(rs.getString("itemno"));
		numbox.setSelectedItem(rs.getString("Itemno"));
		txtQty.setText(rs.getString("qtysold"));
		txtSPrice.setText(rs.getString("salePrice"));
		txtSaledate.setText(rs.getString("saledate"));
	}
	catch(Exception e)
	{
		System.out.println(e.toString());
	}
		
	}

	@Override
	public void itemStateChanged(ItemEvent arg0) {
		x=numbox.getSelectedIndex();  
		Object c=numbox.getSelectedItem();
		String s=c.toString();
		y=Integer.parseInt(s);
		   txtItemName.setText(name.elementAt(x));
		
	}

	public void newrec()
	{
		int n=0;
		 try
		 {
       	  Class.forName("com.mysql.jdbc.Driver");
		 }
		 catch(Exception ex)
		 {
		 System.out.println(ex.toString());
		 }
         try
		{String s="jdbc:mysql://localhost:3306/Inventory";
		cn=DriverManager.getConnection(s,"ABC","123y");
		 stn1=cn.createStatement();
		 String sq="Select max(slno) from Sales";
		 ResultSet res=stn1.executeQuery(sq);
		 while(res.next())
		 {
		 
			 n=res.getInt(1);
		 }
		
		 if(n==0)
		  n=1001;
		  else
		  n=n+1;
		  txtSlno.setText(""+n); 
		}
         catch(Exception e)
         {
        	 System.out.println(e.toString());
         }
	}
}

	


                          
