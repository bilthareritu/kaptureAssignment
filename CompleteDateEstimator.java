import java.util.*;

class MyDate {
	int date, month;
	static int noOfDays[] = { 0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
	static String nameOfMonth[] = { "", "January", "February", "March", "April", "May", "June", "July", "August",
			"Sepember", "October", "November", "December" };

	public MyDate(int date, int month) {
		this.date = date;
		this.month = month;
	}

	@Override
	public int hashCode() {
		return month * 37 + date;
	}

	@Override
	public boolean equals(Object oth) {
		MyDate other = (MyDate) oth;
		return other.date == date && other.month == month;
	}

	public int getDate() {
		return date;
	}

	public int getMonth() {
		return month;
	}

	public static int daysInMonth(int month) {
		return noOfDays[month];
	}

	public String toString() {
		return date + "," + nameOfMonth[month];
	}

	public static boolean isValidDate(int date, int month) {
		// month should be from 1 to 12
		if (month < 1) {
			return false;
		}
		if (month > 12) {
			return false;
		}
		
		// date should be from 1 to days in that month.
		if (date < 1) {
			return false;
		}
		if (date > daysInMonth(month)) {
			return false;
		}
		return true;
	}

}

public class CompleteDateEstimator {

	public static void main(String[] args) {

		/*
		 * Input format First line => no. of tasks From next line description of each
		 * task as follows. First line => no. of hours to complete task. Second line =>
		 * Time table of employee (start end) in 24 hours format. Third line => no. of
		 * leaves taken by employee. Then description of leave in (date month) format
		 * For example 
		 2
		 8
		 7 15
		 3
		 1 12 
		 5 12 
		 15 12 
		 16 
		 15 23 
		 3 
		 3 12 
		 8 12 
		 10 12
		 * 
		 */
		Scanner sc = new Scanner(System.in);
		int noOfTasks = sc.nextInt();
		for (int emp = 1; emp <= noOfTasks; emp++) {
			
			int totalHours = sc.nextInt();
			int stHour = sc.nextInt();
			int enHour = sc.nextInt();

			int noOfLeaves = sc.nextInt();

			Set<MyDate> leaves = new HashSet<>();

			int i;
			for (i = 0; i < noOfLeaves; i++) {
				int dd = sc.nextInt();
				int mm = sc.nextInt();
				
				// validate date
				if (MyDate.isValidDate(dd, mm)) {
					leaves.add(new MyDate(dd, mm));
				} else {
					// suitable error msg.
				}
			}
			MyDate startTime = new MyDate(7, 12);
			CompleteDateEstimator completeDateEstimator = new CompleteDateEstimator();
			System.out.println("Employee " + emp + " will complete his task on "
					+ completeDateEstimator.getEndTime(startTime, totalHours, stHour, enHour, leaves));

		}

	}

	private MyDate getEndTime(MyDate startTime, int totalHours, int stHour, int enHour, Set<MyDate> leaves) {

		int curMonth = startTime.getMonth();
		int curDate = startTime.getDate();
		int hoursPerDay = enHour - stHour;
		boolean completed = false;
		System.out.println("Working daily " + hoursPerDay);
		System.out.println("We have to do " + totalHours);
		System.out.println("leaves are " + leaves);
		MyDate today;
		
		// first loop iterate over month
		for (;;) {
			
			// second loop iterate over date
			for (;;) {
				today = new MyDate(curDate, curMonth);
				System.out.println("today is  " + today + " and remaining time is " + totalHours);

				if (!leaves.contains(today)) {
					
					// today is working day
					if (totalHours <= hoursPerDay) {
						completed = true;
						break;
					} else {
						totalHours -= hoursPerDay;
					}
				} else {
					System.out.println("today is leave day");
				}
				curDate++;
				if (curDate > MyDate.daysInMonth(curMonth)) {
					curMonth++;
					if (curMonth > 12) {
						curMonth = 1;
					}
					curDate = 1;
				}
			}
			if (completed) {
				break;
			}

		}

		return today;
	}

}
