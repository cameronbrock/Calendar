import java.util.Scanner;

public class Calendar
{
	public static void main(String[] args)
	{

		//This introduction informs the user of the purpose of this program as well
		//as how to operate it.
		System.out.println("Welcome to Cameron Brock's calendar application");
		System.out.println("To view the preceeding month, type 'PREV'");
		System.out.println("To view the succeeding month, type 'NEXT");
		System.out.println("To exit, type 'EXIT'");

		//Define the year, month, and day.
		int year = getDate()[0];
		int month = getDate()[1];
		int day = getDate()[2];
		
		//This while loop runs for the duration of the program.
		while (true)
		{
			//Declare a new year object with the value of the current year.
			Year curr_year = new Year(year);

			//Print the calendar.
			System.out.println(curr_year.months[month]);
			
			//This while loop waits for the user to input a command.
			while (true)
			{

				//Define a scanner and gather user input.
				Scanner scanner = new Scanner(System.in);
				String user_input = scanner.nextLine().toUpperCase();

				//If the user types "EXIT":
				if (user_input.equals("EXIT"))
					return;
				//If the user types "PREV":
				else if (user_input.equals("PREV"))
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
					break;
				}
				//If the user types "NEXT":
				else if (user_input.equals("NEXT"))
				{
					//If the current month is December, we must increment the year and
					//set the month to January.
					if (month == 11)
					{
						++year;
						month = 0;
					}
					//Otherwise, merely increment the month.
					else
						++month;
					break;
				}
				else
					System.out.println("Invalid input");
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