
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Date;
import javax.swing.*;


public class DatabaseGUI extends JFrame implements ActionListener
{
	JLabel l1,l2,l3,l4,l5;
	JTextField txtItemNo,txtItemName,txtPrice,txtQoh,txtUpdate;
	JButton btnNew, btnSave,btnDelete,btnEdit,btnUpdate,btnFirst,btnNext,btnPrevious,btnLast,btnView;
	JPanel panelFields, panelButtons,panelMain,panel_Text;
	Container c;
	Connection cn=null;
	Statement sm=null;
	ResultSet rs=null;
	PreparedStatement ps=null;
	String n;
	Date rr;
	Double p;
	int q;
	TextArea ta1;
	public DatabaseGUI()
	{
	super("Inventory Application");
	initializeCompoments();
	initializeDatabase();
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
	panelFields=new JPanel(new GridLayout(5,2,10,10));
	panelButtons=new JPanel(new GridLayout(2,5,10,10));
	panel_Text=new JPanel(new GridLayout(2,8,10,20));
	l1=new JLabel("Item Code",JLabel.RIGHT);
	l2=new JLabel("Item name",JLabel.RIGHT);
	l3=new JLabel("Item Price",JLabel.RIGHT);
	l4=new JLabel("Quantity on Hand",JLabel.RIGHT);
	l5=new JLabel("Updated On",JLabel.RIGHT);
	txtItemNo=new JTextField(25);	txtItemName=new JTextField(25);
	txtPrice=new JTextField(25);	txtQoh=new JTextField(25);
	txtUpdate=new JTextField(25);
	ta1=new TextArea(8,20);
	btnNew=new JButton("New");		btnSave=new JButton("Save");
	btnUpdate=new JButton("Update");btnView=new JButton("View");
	btnDelete=new JButton("Delete");btnEdit=new JButton("Edit-specific");
	btnFirst=new JButton("First");	btnNext=new JButton("Next");
	btnPrevious=new JButton("Previous");	btnLast=new JButton("Last");
	panel_Text.add(ta1);
	
	panelFields.add(l1);panelFields.add(txtItemNo);
	panelFields.add(l2);panelFields.add(txtItemName);
	panelFields.add(l3);panelFields.add(txtPrice);
	panelFields.add(l4);panelFields.add(txtQoh);
	panelFields.add(l5);panelFields.add(txtUpdate);
	panelFields.setBorder(BorderFactory.createLineBorder(Color.blue, 3));
	
	panelButtons.add(btnNew);panelButtons.add(btnSave);
	panelButtons.add(btnDelete); panelButtons.add(btnEdit);
	panelButtons.add(btnUpdate); 
	panelButtons.add(btnFirst); panelButtons.add(btnNext);
	panelButtons.add(btnPrevious); panelButtons.add(btnLast);
	panelButtons.add(btnView);
	panelButtons.setBorder(BorderFactory.createLineBorder(Color.red, 3));
	

	btnNew.addActionListener(this); btnSave.addActionListener(this);
	btnDelete.addActionListener(this); btnFirst.addActionListener(this);
	btnNext.addActionListener(this); btnPrevious.addActionListener(this);
	btnLast.addActionListener(this); btnEdit.addActionListener(this); 
	btnUpdate.addActionListener(this);btnView.addActionListener(this);
	txtItemNo.addKeyListener(new KeyAdapter()
	{
		public void keyTyped(KeyEvent ke)
		{
		char c=ke.getKeyChar();
		if(!Character.isDigit(c) || c==KeyEvent.VK_BACK_SPACE || c==KeyEvent.VK_DELETE)
		{ 
			ke.consume();
		}
		
		}
		
	});
		
	panelMain.add(panelFields);
	panelMain.add(panelButtons);
	panelMain.add(panel_Text);
	c.add(panelMain,"Center");
	c.add(new JLabel(""),"South");
	c.add(new JLabel(""),"North");
	c.add(new JLabel(""),"East");
	c.add(new JLabel(""),"West");
	btnUpdate.setEnabled(false);
	txtItemNo.setEditable(false);
}

	private void initializeDatabase() {
	try
	{
		Class.forName("com.mysql.jdbc.Driver");
		String cs="jdbc:mysql://localhost:3306/Inventory";
		cn=DriverManager.getConnection(cs,"ABC","123y");
		sm=cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
		rs=sm.executeQuery("Select * from Stock");
		firstClick();
		
	}
	catch(Exception e)
	{
		System.out.println(e.toString());
	}
		
}

	
	

	public static void main(String[] args) {
		new DatabaseGUI();

	}


	@Override
	public void actionPerformed(ActionEvent e) {
		String s=e.getActionCommand();
		if(s.equalsIgnoreCase("New"))
		{
			btnNew.setEnabled(false);
			txtItemNo.setEditable(true);
			resetFields();
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
		if(s.equalsIgnoreCase("Edit-specific"))
		{
			editClick();
		}
		if(s.equalsIgnoreCase("Update"))
		{
			updateClick(n,p,q,rr);
		}
		if(s.equalsIgnoreCase("View"))
		{
			viewClick();
		}
	}


	
	private void firstClick() {
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
	private void lastClick() {
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
	private void previousClick() {
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

	

	private void nextClick() {
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

	private void deleteClick() {
		int x=JOptionPane.showConfirmDialog(this,"Delete - Y/N ? ", "Delete Record",JOptionPane.YES_NO_OPTION);
		if(x==JOptionPane.YES_OPTION)
		{
			try
			{
			PreparedStatement ps=cn.prepareStatement("Delete from Stock where itemNo=?");
			ps.setInt(1,Integer.parseInt(txtItemNo.getText()));
			ps.execute();
			resetFields();
			rs=sm.executeQuery("Select *from Stock");
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
		//txtItemName.requestFocus();
			ps=cn.prepareStatement("Update Stock set ItemName=?, " + 
					" price=?,qtyonhand=?,updatedon=? where ItemNo = ? ");
			ps.setInt(5,Integer.parseInt(txtItemNo.getText()));
			ps.setString(1,txtItemName.getText());
			ps.setDouble(2,Double.parseDouble(txtPrice.getText()));
			ps.setInt(3,Integer.parseInt(txtQoh.getText()));
			ps.setDate(4,java.sql.Date.valueOf(txtUpdate.getText()));
			ps.execute();
			rs=sm.executeQuery("Select * from Stock");
			btnNew.setEnabled(true);
			}
		else
		{
			
			ps=cn.prepareStatement("Insert into Stock (itemno,itemname, " + 
				" price,qtyonhand,updatedon) values(?,?,?,?,?)");
						ps.setInt(1,Integer.parseInt(txtItemNo.getText()));
						ps.setString(2,(txtItemName.getText()));
						ps.setDouble(3,Double.parseDouble(txtPrice.getText()));
						ps.setInt(4,Integer.parseInt(txtQoh.getText()));
						ps.setDate(5, java.sql.Date.valueOf(txtUpdate.getText()));
						//ps.setString(5, txtUpdate.getText());
						
								
		
		ps.execute();
		rs=sm.executeQuery("Select * from Stock");
		btnNew.setEnabled(true);
		txtItemNo.setEditable(false);
		}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			//System.out.println(e.toString());
		}
		
	}


	private void editClick() 
	{
		int x=JOptionPane.showConfirmDialog(this,"Edit Record - Y/N ? ", "Edit Record",JOptionPane.YES_NO_OPTION);
		if(x==JOptionPane.YES_OPTION)
		{
			btnSave.setEnabled(false);
		btnUpdate.setEnabled(true);
			String x1,x2;
			x1=JOptionPane.showInputDialog("Enter Column number to edit - 1 for name, 2 for price & 3 for qoh");
			n=txtItemName.getText();
			p=Double.parseDouble(txtPrice.getText());
			q=Integer.parseInt(txtQoh.getText());
			rr=java.sql.Date.valueOf(txtUpdate.getText());
			int z=Integer.parseInt(x1);
			if(z==1)
			{
				x2=JOptionPane.showInputDialog("Enter New Name");
				n=x2;
			}
			if(z==2)
			{	x2=JOptionPane.showInputDialog("Enter New Price");
					p=Double.parseDouble(x2);
			}
			if(z==3)
			{
				x2=JOptionPane.showInputDialog("Enter New Qoh");
				q=Integer.parseInt(x2);
			}
		}
		
		
	}
	private void updateClick(String n, double p,int q,Date rr) {
		
		try
		{
			ps=cn.prepareStatement("Update Stock set ItemName=?, " + 
					" price=?,qtyonhand=?,updatedon=? where ItemNo = ? ");
			ps.setInt(5,Integer.parseInt(txtItemNo.getText()));
			ps.setString(1,n);
			ps.setDouble(2,p);
			ps.setInt(3,q);
			ps.setDate(4,(java.sql.Date) rr);
			ps.execute();
			rs=sm.executeQuery("Select * from Stock");
			btnNew.setEnabled(true);
			btnSave.setEnabled(true);
			btnUpdate.setEnabled(false);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
				
	}

	private void resetFields() 
	{
		txtItemNo.setText("");
		txtItemName.setText("");
		txtPrice.setText("");
		txtQoh.setText("");txtUpdate.setText("");
	}
	
	private void fillFields() 
	{
	try
	{
		txtItemNo.setText(rs.getString("ItemNo"));
		txtItemName.setText(rs.getString("ItemName"));
		txtPrice.setText(rs.getString("Price"));
		txtQoh.setText(rs.getString("Qtyonhand"));
		txtUpdate.setText(rs.getString("Updatedon"));
	}
	catch(Exception e)
	{
		System.out.println(e.toString());
	}
		
	}
	public void viewClick()
	{
		try
		 {
		
		  rs=sm.executeQuery("Select * from Stock");
		 ResultSetMetaData rm=rs.getMetaData();
		 int col=rm.getColumnCount();

		 String result=" ";
		while (rs.next())
		 {
		 for (int i=1;i<=col;i++)
		 {
		  result+=" \t" + rs.getString(i);
		 }
		 result+="\n";
		 }
		 ta1.setText(result); //rs.close();
		 }
		 catch(Exception ee)
		 {
		 }
	}

}

	
	


                          
