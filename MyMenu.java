import java.awt.*;
import java.awt.event.*;

import javax.swing.*;


public class MyMenu  extends JFrame implements ActionListener
{
    JButton b1,b2,b3;
    JPanel p1;
    public MyMenu()
    {
        b1=new JButton("Student Admission entry");
        b2=new JButton("GUI Database");
        b3=new JButton("Quit");
        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        p1=new JPanel(new GridLayout(3,1,10,10));
        p1.add(b1);
        p1.add(b2);
        p1.add(b3);
        add(p1);
        setVisible(true);
        setSize(300,300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
            new MyMenu();
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==b1)
		{
			StudentAdmnEntry s=new StudentAdmnEntry();
		}
		if(e.getSource()==b2)
		{
			DatabaseGUI s=new DatabaseGUI();
		}
		if(e.getSource()==b3)
		{
			System.exit(0);
		}
		
	}

}
