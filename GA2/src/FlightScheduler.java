import java.util.ArrayList;
import java.util.Random;

/* START: DO NOT change this part of the code */
public class FlightScheduler {

	public static void main(String[] args) {

		Schedule schedule1 = new Schedule(101, "SIN - Changi", "BKK - Suvarnabhumi", "Airbus A330-300", "22/01/2021","14:45", "CONFIRMED");
		Schedule schedule2 = new Schedule(102, "BKK - Suvarnabhumi", "SIN - Changi", "Boeing 777-300", "24/01/2021","12:30", "CONFIRMED");
		Schedule schedule3 = new Schedule(103, "KUL - KLIA", "BKK - Suvarnabhumi", "Boeing 777-300", "18/01/2021","12:30", "CONFIRMED");

		ArrayList<Schedule> scheduleList = new ArrayList<Schedule>();
		scheduleList.add(schedule1);
		scheduleList.add(schedule2);
		scheduleList.add(schedule3);

		Staff staff = new Staff("Stefan Chan", "stefan_chan", "654321", 5, "admin");

		while (true) {

			FlightScheduler.loginMenu();
			String uName = Helper.readString("Enter username > ");
			String uPassword = Helper.readString("Enter password > ");

			boolean isStaff = FlightScheduler.doStaffLogin(staff, uName, uPassword);

			if (isStaff == false) {
				System.out.println("Either your username or password was incorrect. Please try again!");
			}


			while (isStaff) {
				FlightScheduler.staffMenu();
				int staffChoice = Helper.readInt("Enter choice > ");

				if (staffChoice == 1) { 

					String allFlightSchedules = FlightScheduler.scheduleListToString(scheduleList);
					System.out.println(allFlightSchedules);
					System.out
							.println("Total number of flights: " + FlightScheduler.getNumFlightSchedules(scheduleList));

				} else if (staffChoice == 2) {

					int scheduleId = Helper.readInt("Enter flight schedule ID > ");
					String departAP = Helper.readString("Enter departure airport > ");
					String arriveAP = Helper.readString("Enter arrival airport > ");
					String aircraft = Helper.readString("Enter aircraft > ");
					String flightDate = Helper.readString("Enter flight date (dd/mm/yyyy) > ");
					String flightTime = Helper.readString("Enter flight time (hh:mm) > ");

					Schedule newSchedule = new Schedule(scheduleId, departAP, arriveAP, aircraft, flightDate,
							flightTime);

					boolean result = FlightScheduler.addFlightSchedule(scheduleList, newSchedule);

					if (result == true) {
						System.out.println("Flight schedule added!");
					} else {
						System.out.println("Flight schedule NOT added, you must include all details!");
					}

				} else if (staffChoice == 3) { 

					int editFlightID = Helper.readInt("Enter Flight Schedule ID > ");

					String flightScheduledetails = FlightScheduler.getFlightScheduleById(scheduleList, editFlightID);

					if (!flightScheduledetails.isEmpty()) {
						System.out.println(flightScheduledetails);
						String statusUpdate = Helper.readString("Enter new schedule status > ");
						boolean isEdited = FlightScheduler.editFlightScheduleStatus(scheduleList, editFlightID,
								statusUpdate);

						if (isEdited) {
							flightScheduledetails = FlightScheduler.getFlightScheduleById(scheduleList, editFlightID);
							System.out.println(String.format("Flight schedule status %d updated to %s", editFlightID,
									statusUpdate.toUpperCase()));
							System.out.println(flightScheduledetails);
						} else {
							System.out.println("The status you entered is invalid.");
						}
					} else {
						System.out.println("That Flight Schedule ID does not exist!");
					}

				} else if (staffChoice == 4) {

					int deleteFlightID = Helper.readInt("Enter Flight Schedule ID > ");

					String flightScheduledetails = FlightScheduler.getFlightScheduleById(scheduleList, deleteFlightID);

					if (!flightScheduledetails.isEmpty()) {
						System.out.println(flightScheduledetails);
						char toDelete = Helper.readChar("Do you wish to delete this flight?(y/n) > ");

						if (toDelete == 'y') {
							boolean deleted = FlightScheduler.removeFlightSchedule(scheduleList, deleteFlightID);

							if (deleted == true) {
								System.out.println(String.format("Flight schedule id %d was deleted successfully.",
										deleteFlightID));
							} else {
								System.out.println("Something went wrong, flight schedule was not deleted.");
							}
						}

					} else {
						System.out.println("That Flight Schedule ID does not exist!");
					}

				} else if (staffChoice == 5) {
					String airportSearch = Helper.readString("Enter airport to search > ");
					ArrayList<Schedule> resultList = getFlightSchedulesByAirport(scheduleList, airportSearch);

					if (!resultList.isEmpty()) {
						System.out.println(FlightScheduler.scheduleListToString(resultList));
					} else {
						System.out.println("No flight schedules with that airport.");
					}

				} else if (staffChoice == 6) {

					char resetPw = Helper.readChar("Are you sure you want to reset your password? (y/n) > ");

					if (resetPw == 'y') {
						staff = FlightScheduler.resetPassword(staff);
						System.out.println("Password reset successful.");
						System.out.println("Your new password is: " + staff.getPassword());
					}

				} else if (staffChoice == 7) {
					isStaff = false;
					System.out.println("Goodbye!");

				} else {
					System.out.println("Invalid choice");
				}

			}

		}

	}

	public static void loginMenu() {
		Helper.line(30, "-");
		System.out.println("FLIGHT SCHEDULER - LOGIN");
		Helper.line(30, "-");
	}

	public static void staffMenu() {

		Helper.line(30, "-");
		System.out.println("FLIGHT SCHEDULER - STAFF");
		Helper.line(30, "-");

		System.out.println("1. View all flight schedules");
		System.out.println("2. Add a new flight schedule");
		System.out.println("3. Update a flight schedule status");
		System.out.println("4. Remove a flight schedule");
		System.out.println("5. Search for flight schedules by airport");
		System.out.println("6. Reset password");
		System.out.println("7. Log out");

	}

	public static int getNumFlightSchedules(ArrayList<Schedule> scheduleList) {

		return scheduleList.size();

	}

	public static String getFlightScheduleById(ArrayList<Schedule> scheduleList, int flightScheduleId) {

		String output = "";

		for (int i = 0; i < scheduleList.size(); i++) {
			Schedule s = scheduleList.get(i);

			if (s.getId() == flightScheduleId) {
				output += String.format("%-3s %-20s %-20s %-15s %-15s %-20s %-10s\n", "ID", "DEPARTURE", "ARRIVAL",
						"FLIGHT DATE", "FLIGHT TIME", "AIRCRAFT", "STATUS");
				output += String.format("%-3d %-20s %-20s %-15s %-15s %-20s %-10s\n", s.getId(),
						s.getDepartureAirport(), s.getArrivalAirport(), s.getFlightDate(), s.getFlightTime(),
						s.getAircraft(), s.getStatus());
				break;
			}
		}

		return output;
	}

/* END: DO NOT change this part of the code */
	
	
	
	
/*
 *   Refer to the assignment document for the specification of the methods below.
 */

	public static boolean doStaffLogin(Staff staff, String uName, String uPassword) {
		boolean isCheck = false;
		if(uName.equals(staff.getUsername()) && uPassword.equals(staff.getPassword())) {
			isCheck = true;
		}
		return isCheck;
//done
	}

	public static ArrayList<Schedule> getFlightSchedulesByAirport(ArrayList<Schedule> scheduleList, String airport) {
		ArrayList<Schedule> check = new ArrayList<Schedule>();
		for(int i = 0; i < scheduleList.size(); i++) {
			if(scheduleList.get(i).getArrivalAirport().toUpperCase().contains(airport.toUpperCase()) || scheduleList.get(i).getDepartureAirport().toUpperCase().contains(airport.toUpperCase())) {
				check.add(scheduleList.get(i));
			}
		}
		return check;
//done
	}

	public static String scheduleListToString(ArrayList<Schedule> scheduleList) {
		String a = String.format("%s  %-20s %-20s %-20s %-20s %-20s %-20s", "ID", "DEPARTURE", "ARRIVAL", "FLIGHT DATE", "FLIGHT TIME", "AIRCRAFT", "STATUS");
		for(int i = 0;i < scheduleList.size(); i++) {
			if(scheduleList.get(i) != null) {
				a += String.format("\n%d %-20s %-20s %-20s %-20s %-20s %-20s", scheduleList.get(i).getId(), scheduleList.get(i).getDepartureAirport(), scheduleList.get(i).getArrivalAirport(), scheduleList.get(i).getFlightDate(), scheduleList.get(i).getFlightTime(), scheduleList.get(i).getAircraft(), scheduleList.get(i).getStatus());
			}
		}
		return a;
//done
	}

	public static boolean addFlightSchedule(ArrayList<Schedule> scheduleList, Schedule schedule) {
		boolean isCheck = false;
		if(schedule.getDepartureAirport().equals("") || schedule.getArrivalAirport().equals("")|| schedule.getAircraft().equals("")|| schedule.getFlightDate().equals("") || schedule.getFlightTime().equals("")) {
			isCheck = false;
		}
		else {
			isCheck = true;
			scheduleList.add(schedule);
		}
		return isCheck;
//done
	}

	public static boolean editFlightScheduleStatus(ArrayList<Schedule> scheduleList, int scheduleId, String newStatus) {
		boolean isValid = false;
		int a = 0;
		for(int i = 0; i < scheduleList.size(); i++) {
			if(scheduleId == scheduleList.get(i).getId()) {
				isValid = true;
				a = i;
				break;
			}
			else {
				isValid = false;
			}
		}
		if(isValid == true) {
			if(newStatus.equals("PENDING") || newStatus.equals("DELAYED") || newStatus.equals("CONFIRMED")) {
				isValid = true;
				scheduleList.get(a).setStatus(newStatus);
			}
			else {
				isValid = false;
			}
		}
		return isValid;
//done
	}

	public static boolean removeFlightSchedule(ArrayList<Schedule> scheduleList, int scheduleId) {
		boolean isCheck = false;
		for(int i = 0; i < scheduleList.size(); i++) {
			if(scheduleList.get(i).getId() == scheduleId) {
				isCheck = true;
				scheduleList.remove(i);
				break;
			}
			else {
				isCheck = false;
			}
		}
		
		return isCheck;
//done
	}

	public static Staff resetPassword(Staff staff) {
		Random rand = new Random();
		char a = 'a';
		String str = "";
		String total = "";
		for(int i = 0; i < 8; i++) {
			int randomInt = rand.nextInt(26);
			int randomInt1 = rand.nextInt(2) + 1;
			a = (char) (a + randomInt);
			str = Character.toString(a);
			if(randomInt1 == 1) {
				str = str.toUpperCase();		
			}
			else if(randomInt1 == 2) {
				str = str.toLowerCase();
			}
			total = total + str;
			a = 'a';
		}
		staff.setPassword(total);
		return staff;
	}
//done
}
