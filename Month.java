public class Month
{
	//The designation of the month. For example, March is 3, April is 4,
	//May is 5, etc. until you reach January and February, which are 13
	//and 14, respectively. A designation of 0 denotes the end of the list.
	public final int designation;

	//The number of days in this month.
	public final int days;

	//The year of the current month being considered.
	private int year;

	//The name of the month.
	String name;

	//Define the constructor
	public Month()
	{
		this(13);
	}

	public Month(int designation)
	{
		this(designation, 1970);
	}

	public Month(int designation, Year year)
	{
		this(designation, year.value);
	}

	public Month(int designation, int year)
	{
		this.designation = designation;
		this.year = year;

		this.days = Calendar.getDays(designation, year);
		this.name = Calendar.getName(designation);

	}

	public int firstDay()
	{
		//This function uses Zeller's conjecture to calculate the day of the
		//week that the month begins on

		//q represents the rate which the week progresses. In all cases,
		//this value is set to one, but is represented here as a variable
		//in case of future experimentation
		int q = 1;

		//The year being considered. Because in some calculations we use a value
		//for Y that is actually smaller than the value of the year, we will use
		//a new variable.
		int Y = year;

		//If the designation is 13 or 14, then we consider this year to
		//actually be the previous year.
		if (this.designation == 13 || this.designation == 14)
			--Y;

		int result = (int)(1 + Math.floor(2.6*(designation + 1)) + Y + Math.floor(Y / 4) - Math.floor(Y / 100) + Math.floor(Y / 400)) % 7;

		//At this point, we have defined Saturday as being zero. However, we need to
		//begin at the beginning of the week, Sunday. Therefore:

		//If result is 0 (aka Saturday), then we will redefine that as 6 and return that.
		if (result == 0)
			return 6;
		//Otherwise, return whatever the value was minus one.
		else
			return result - 1;
	}

	public String toString()
	{
		//Here, we treat a calendar as a grid, with columns representing the day of the week
		//and rows representing the week of the month. There are seven columns and six rows.
	
		//Begin the string via HTML notation. The string is written in HTML as that is how text
		//is interpreted by the JLabel class.
		String output = "<html><font face=\"Monospaced\" size = \"6\">";

		//Add the month name to the top of the calendar and center it.
		output += "<center>" + this.name + " " + this.year + "</center><br/>";

		//Print the days of the week for reference
		output += "S&nbsp;&nbsp;M&nbsp;&nbsp;T&nbsp;&nbsp;W&nbsp;&nbsp;Th&nbsp;F&nbsp;Sat<br/>";

		//For every day of the week preceding the first day, we should merely print spaces.
		for (int i = 0; i < this.firstDay(); ++i)
			output += "&nbsp;&nbsp;&nbsp;";

		//The day to be printed.
		int day = 1;

		//The column to be considered should be the first day of the week.
		int col = this.firstDay();

		for (int row = 0; row < 6; ++row)
		{
			//Because we have already defined col, we do not need to do so in this for loop.
			for (; col < 7; ++col)
			{
				//We will output each day as a two-digit number.
				//If the value of day is the current day...
				if (day <= days)
				{
					if (this.year == Calendar.getDate()[0] && (this.designation == Calendar.getDate()[1] + 1 || this.designation == Calendar.getDate()[1] + 13) && day == Calendar.getDate()[2])
					{
						//Output the day number in red font.
						output += "<font color='red'>" + String.format("%02d&nbsp;", day) + "</font>";
						++day;
					}
					//Otherwise, output it in black.
					else
					{
						output += String.format("%02d&nbsp;", day);
						++day;
					}
				}
				else
					output += "&nbsp;&nbsp;&nbsp;";
			}

			//We have now completed the week. The column will be reset to the beginning of
			//the week, zero, and we will output a newline.
			col = 0;
			output += "<br/>";
		}
		
		//Closing HTML tag
		output += "</h1></html>";

		//Return the result.
		return output;
	}


}