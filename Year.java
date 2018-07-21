public class Year
{

	public final int value; //The number representing the year itself
						    //(e.g. 1995, 2020, etc.)
	
	public final int days; //The number of days in the year (can either be
						   //365 or 366).

	public final Month[] months = new Month[12]; //An array of all the months
												 //in the year.

	//Define the constructors.
	public Year()
	{
		this(1970);
	}

	public Year(Year year)
	{
		this(year.value);
	}

	public Year(int value)
	{
		//Set the year value
		this.value = value;

		months[0]  = new Month(13, this.value); // January
		months[1]  = new Month(14, this.value); // February
		months[2]  = new Month(3, this.value); // March
		months[3]  = new Month(4, this.value); // April
		months[4]  = new Month(5, this.value); // May
		months[5]  = new Month(6, this.value); // June
		months[6]  = new Month(7, this.value); // July
		months[7]  = new Month(8, this.value); // August
		months[8]  = new Month(9, this.value); // September
		months[9]  = new Month(10, this.value); // October
		months[10] = new Month(11, this.value); // November
		months[11] = new Month(12, this.value); // December

		//Initialize the number of days in this year
		if (Calendar.isLeapYear(value))
			days = 366;
		else
			days = 365;

	}

	public Year prev()
	{
		//Returns the previous year.
		return new Year(value - 1);
	}

	public Year next()
	{
		//Returns the succeeding year.
		return new Year(value + 1);
	}
}
