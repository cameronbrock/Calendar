import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.*;
import java.time.ZonedDateTime;

public class Calendar extends JFrame
{

	//The JLabel contains the calendar output, while the JButtons allow the user to specify
	//the month.
	private JLabel label;
	//prev allows the user to go to the previous month.
	//today allows the user to return to the current month.
	//next allows the user to go to the next month.
	private JButton prev, today, next;
	
	//curr_year and curr_month contain the current year and month, while year and month contain
	//the year and month the user is looking at at the moment.
	private final int curr_year, curr_month;
	private int year, month;	

	//Define the constructor
	public Calendar()
	{
		//Define the JLabel containing the month information
		label = new JLabel();

		//Define the current year and month
		curr_year = getDate()[0];
		curr_month = getDate()[1];

		//Define the year and month
		year = curr_year;
		month = curr_month;

		//Define the three buttons.
		prev = new JButton("<<");
		today = new JButton("Today");
		next = new JButton(">>");

		//Create the displayed GUI.
		CreateView();
	}

	//Define the main function.
	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				//Create a new instance of Calendar
				new Calendar().setVisible(true);
			}
		});

		
	}

	public static boolean isLeapYear(int year)
	{
		//Calculates whether the current Year object represents a leap year.

		//A year is a leap year if it is either divisibile by 4 and not 100, or
		//if it is divisible by 4, 100, and 400.
		boolean div_by_4 = (year % 4) == 0;
		boolean div_by_100 = (year % 100) == 0;
		boolean div_by_400 = (year % 400) == 0;

		//Returns the final calculation.
		if ((div_by_4 && !div_by_100) || (div_by_100 && div_by_400))
			return true;
		return false;
	}

	public static int[] getDate()
	{

		//Get the timezone offset, or the number of hours behind we are from Greenwich time
		Integer offset = ZonedDateTime.now().getOffset().getTotalSeconds();

		//Get the number of days that have passed since January 1st, 1970.
		//The current time is subtracted by the offset seconds times 1000 (to convert to milliseconds)
		//86400000 is the number of milliseconds in a day.
		Long days = (System.currentTimeMillis() + offset*1000) / 86400000;
		//Set an object of type Year equal to the year 1970.
		Year currYearObj = new Year();

		//Calculate both the number of years that have passed since 1970 as
		//well as the number of days that have passed this year.
		while (days > currYearObj.days)
		{
			days -= currYearObj.days;
			currYearObj = currYearObj.next();
		}

		//Get number of months that have passed in the current year

		int months = 0; //The number of months that have passed this year

		//Calculate the number of months that have passed since January as
		//well as the number of days that have passed this month.
		//int i = 0;

		for (int i = 0; i < currYearObj.months.length && days > currYearObj.months[i].days; ++i)
		{
			days -= currYearObj.months[i].days;
			++months;
		}


		++days; //To account for the fact that the while loop
			    //counts how many days and months have passed,
			    //not what the current day or month is.


		//Return an array containing the values in year/month/day format,
		//with each value represented by an integer.
		int[] date = {currYearObj.value, months, days.intValue()};

		//Return the result
		return date;
	}

	public void CreateView()
	{


		//Create a white JPanel and add it to this JFrame
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		getContentPane().add(panel);

		//Set the dimensions of the label, set the text within the label, set the alignment,
		//and add it to the JPanel.
		label.setPreferredSize(new Dimension(300, 300));
		updateLabel(); //Set the text within the label
		label.setHorizontalAlignment(JLabel.CENTER);
		panel.add(label);

		//Create a new panel containing the buttons.
		JPanel buttons = new JPanel();

		//Create two JLabels that will serve to create space between the buttons.
		JLabel lspace = new JLabel();
		JLabel rspace = new JLabel();
		//Set the size of the spaces.
		lspace.setPreferredSize(new Dimension(40, 0));
		rspace.setPreferredSize(new Dimension(40, 0));

		//Add the buttons and labels to the nuttons JPanel.
		buttons.add(prev);   //Prev button
		buttons.add(lspace); //Space between prev and Today
		buttons.add(today);  //Today button
		buttons.add(rspace); //Space between Today and next.
		buttons.add(next);   //Next button

		prev.addActionListener(new prevListener());
		today.addActionListener(new todayListener());
		next.addActionListener(new nextListener());

		//Add the buttons panel to the JFrame, positioning it at the bottom.
		add(buttons, BorderLayout.SOUTH);

		//"Pack" the JFrame, meaning the frame is resized to be as compact as it can be.
		pack();

		//Set the interface to appear at the center of the user's screen.
		setLocationRelativeTo(null);

		//Set the application to close when the user selects the close button (or "X" button)
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Set the tutle of the application
		setTitle("Calendar");

		//Disallow the user from resizing the application. This prevents awkward situations where
		//text will not fit within the window when the user resizes it.
		setResizable(false);

		//Make the window visible.
		setVisible(true);

			
	}

	private class prevListener implements ActionListener
	{
		//If the user selects the prev button
		@Override
		public void actionPerformed(ActionEvent e)
		{
			//If the current month is January, we must decrement the year and
			//set the month to December.
			if (month == 0)
			{
				--year;
				month = 11;
			}
			//Otherwise, merely decrement the month.
			else
				--month;

			//Update the label in the JFrame.
			updateLabel();
		}
	}

	private class nextListener implements ActionListener
	{
		//If the user selects the next button
		@Override
		public void actionPerformed(ActionEvent e)
		{
			//If the current month is December, we must increment the year and
			//set the month equal to January
			if (month == 11)
			{
				++year;
				month = 0;
			}
			//Otherwise, merely increment the month.
			else
				++month;

			//Update the label in the JFrame.
			updateLabel();
		}
	}

	private class todayListener implements ActionListener
	{
		//If the user selects the Today button
		@Override
		public void actionPerformed(ActionEvent e)
		{
			//Set the year and month equal to the current year and month
			year = curr_year;
			month = curr_month;

			//Update the label in the JFrame.
			updateLabel();
		}
	}

	private void updateLabel()
	{
		//Set the label equal to the selected month
		label.setText((new Year(year)).months[month].toString());
	}

}