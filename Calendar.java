import java.util.Scanner;

public class Calendar
{
	public static void main(String[] args)
	{

		System.out.println("Welcome to Cameron Brock's calendar application");
		System.out.println("To view the preceeding month, type 'PREV'");
		System.out.println("To view the succeeding month, type 'NEXT");
		System.out.println("To exit, type 'EXIT'");

		int year = getDate()[0];
		int month = getDate()[1];
		int day = getDate()[2];

		
		
		while (true)
		{

			Year curr_year = new Year(year);

			System.out.println(curr_year.months[month]);
			System.out.println("<-- PREV	NEXT-->");

			Scanner scanner = new Scanner(System.in);
			String user_input = scanner.nextLine().toUpperCase();
			
			if (user_input.equals("EXIT"))
				return;
			if (user_input.equals("PREV"))
			{
				if (month == 0)
				{
					--year;
					month = 11;
				}
				else
					--month;
			}
			if (user_input.equals("NEXT"))
			{
				if (month == 11)
				{
					++year;
					month = 0;
				}
				else
					++month;
			}

			
		}


		
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
		//Get the number of days that have passed since January 1st, 1970.
		//86400000 is the number of milliseconds in a day.
		Long days = System.currentTimeMillis() / 86400000;
		//Set an object of type Year equal to the year 1970.
		Year year = new Year(1970);

		//Calculate both the number of years that have passed since 1970 as
		//well as the number of days that have passed this year.
		while (days > year.days)
		{
			days -= year.days;
			year = year.next();
		}

		//Get number of months that have passed in the current year

		int months = 0; //The number of months that have passed this year

		//Calculate the number of months that have passed since January as
		//well as the number of days that have passed this month.
		//int i = 0;

		for (int i = 0; i < year.months.length && days > year.months[i].days; ++i)
		{
			days -= year.months[i].days;
			++months;
		}


		++days; //To account for the fact that the while loop
			    //counts how many days and months have passed,
			    //not what the current day or month is.


		//Return an array containing the values in year/month/day format,
		//with each value represented by an integer.
		int[] date = {year.value, months, days.intValue()};

		//Return the result
		return date;
	}

}