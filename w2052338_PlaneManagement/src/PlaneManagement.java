//import essential Java classes

import java.util.InputMismatchException;
import java.util.Scanner;

//declaration of the PlaneManagement class
public class PlaneManagement {
    //providing a maximum number of tickets can be sold.
    public static final int Max_Ticket_Count = 100;

    //using an array to store the tickets sold.
    public static Ticket[] ticketsSold = new Ticket[Max_Ticket_Count];
    //this will keep how many tickets sold.
    public static int num_tickets_sold = 0;

    //representation of the number of rows and the number of seats per row
    public static final int ROWS = 4;
    public static final int[] SEATS_PER_ROW = {14, 12, 12, 14}; //seats per row for each row
    public static final int[][] seats = new int[ROWS][]; //this line creates the structure to hold these seats.but we'll fill in the details later.
    public static final Scanner scanner = new Scanner(System.in);

    //here is the start of the main method.which is the entry point of the program.
    public static void main(String[] args) {
        System.out.println("Welcome to the Plane Management application");

        //keeping all seats as available (0)
        for (int i = 0; i < ROWS; i++) {
            seats[i] = new int[SEATS_PER_ROW[i]];
            for (int j = 0; j < SEATS_PER_ROW[i]; j++) {
                seats[i][j] = 0;
            }
        }


        while (true) {
            //calling the displayMenu method to show the menu options.
            displayMenu();

            int option;
            try {
                System.out.print("Please select an option: ");
                option = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input.please enter a valid integer for the option.");
                scanner.next(); //clear the invalid input
                continue;
            }


            switch (option) {
                case 1:
                    buy_seat();
                    break;
                case 2:
                    cancel_seat();
                    break;
                case 3:
                    find_first_available();
                    break;
                case 4:
                    show_seating_plan();
                    break;
                case 5:
                    print_tickets_info();
                    break;
                case 6:
                    search_ticket();
                    break;
                case 0:
                    System.out.println("Goodbye!");
                    return; //this is uses to exit the main method.effectively terminating the program.
                default:
                    System.out.println("Invalid option.please select a valid option.");
            }
        }
    }

    private static void displayMenu() {
        System.out.println("*************************************************");
        System.out.println("*\t\t\t\t\tMENU OPTIONS\t\t\t\t*");
        System.out.println("*************************************************");
        System.out.println("\t1) Buy a seat");
        System.out.println("\t2) Cancel a seat");
        System.out.println("\t3) Find first available seat");
        System.out.println("\t4) Show seating plan");
        System.out.println("\t5) Print tickets information and total sales");
        System.out.println("\t6) Search ticket");
        System.out.println("\t0) Quit");
        System.out.println("*************************************************");
    }

    private static void buy_seat() {
        System.out.print("Enter your name:");
        String name = scanner.next();
        System.out.print("Enter your surname:");
        String surname = scanner.next();
        System.out.print("Enter your email:");
        String email = scanner.next();

        //person object
        Person person = new Person(name, surname, email);
        char row;
        while (true) {
            System.out.print("Enter the row letter between A and D :");
            String input = scanner.next().toUpperCase();
            if (input.length() == 1 && input.charAt(0) >= 'A' && input.charAt(0) <= 'D') {
                row = input.charAt(0);
                break;
            } else {
                System.out.println("Invalid row.please enter a single letter between A and D.");
            }
        }
        int rowID = row - 'A';

        int seatNumber;
        while (true) {
            System.out.print("Enter the seat number between 1 and "+SEATS_PER_ROW[rowID]+" :");
            try {
                seatNumber = scanner.nextInt();
                if (seatNumber < 1 || seatNumber > 14) {
                    System.out.println("Invalid seat number.please enter a seat number between 1 and 14.");
                } else {
                    break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input.please enter a valid integer for the seat number.");
                scanner.next(); //clears the invalid input
            }
        }

        int seatID = seatNumber - 1;
        if (seatID >= SEATS_PER_ROW[rowID]) {
            System.out.println("Invalid seat number.please enter a valid seat number");
            return;
        }
        //check whether the seat is already sold
        if (seats[rowID][seatID] == 1) {
            System.out.println("Sorry,the seat is already sold.please choose another seat.");
        } else {
            //creating a Ticket object and add it to the array.
            Ticket ticket = new Ticket(row, seatNumber, priceCalculation(row, seatNumber), person);
            ticket.save();
            ticketsSold[num_tickets_sold] = ticket;
            num_tickets_sold++;

            //keeping the seat as sold.
            seats[rowID][seatID] = 1;
            System.out.println("Seat " + row + seatNumber + " has been successfully sold.");
        }
    }
    private static void cancel_seat() {
        char row;
        while (true) {
            System.out.print("Enter the row letter between A and D: ");
            String input = scanner.next().toUpperCase();
            if (input.length() == 1 && input.charAt(0) >= 'A' && input.charAt(0) <= 'D') {
                row = input.charAt(0);
                break;
            } else {
                System.out.println("Invalid row.please enter a single letter between A and D.");
            }
        }

        int rowID = row - 'A';

        // Check if the rowID is valid
        if (rowID < 0 || rowID >= ROWS) {
            System.out.println("Invalid row ID.");
            return;
        }

        int seatNumber;
        while (true) {
            System.out.print("Enter the seat number between 1 and " + SEATS_PER_ROW[rowID] + ": ");
            try {
                seatNumber = scanner.nextInt();
                if (seatNumber < 1 || seatNumber > SEATS_PER_ROW[rowID]) {
                    System.out.println("Invalid seat number.please enter a seat number between 1 and " + SEATS_PER_ROW[rowID] + ".");
                } else {
                    break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input.please enter a valid integer for the seat number.");
                scanner.next(); //clears the invalid input
            }
        }




       // int rowID= row - 'A';
        int seatID = seatNumber - 1;

        //check if the seat is already available
        if (seats[rowID][seatID] == 0) {
            System.out.println("Seat " + row + seatNumber + " is already available.No need to cancel.");
        } else {
            //mark the seat as available (0)
            seats[rowID][seatID] = 0;
            System.out.println("Seat " + row + seatNumber + " has been successfully canceled and is now available.");

            //remove the ticket from the array of sold tickets
            for (int i = 0; i < num_tickets_sold; i++) {
                if (ticketsSold[i].getRow() == row && ticketsSold[i].getSeat() == seatNumber) {
                    //shift the elements in the array to remove the cancelled ticket
                    for (int j = i; j < num_tickets_sold - 1; j++) {
                        ticketsSold[j] = ticketsSold[j + 1];
                    }
                    ticketsSold[num_tickets_sold - 1] = null;
                    num_tickets_sold--;
                    break;
                }
            }
        }
    }


    private static void find_first_available() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < SEATS_PER_ROW[i]; j++) { //iterating through all the seats to find the first available seat.
                if (seats[i][j] == 0) {
                    char row = (char) ('A' + i); //converting integer to char
                    int seatNumber = j + 1;
                    System.out.println("First available seat: " + row + seatNumber);
                    return;
                }
            }
        }
        //if no available seats.
        System.out.println("No available seats found.");
    }

    private static void show_seating_plan() {
        for (int i = 0; i < ROWS; i++) {
            char rowLetter = (char) ('A' + i);  //this line converts the integer value of the row index into its corresponding ASCII character
            int seatsInRow = SEATS_PER_ROW[i];//this line obtains the number of seats in the current row
            if (rowLetter == 'B' || rowLetter == 'C') {
                System.out.print(" "); //add one space for alignment
            } else {
                System.out.print(" "); //add one space for alignment
            }
            for (int j = 0; j < seatsInRow; j++) { //use seatsInRow for the current row
                if (seats[i][j] == 0) {
                    System.out.print("O "); //adding an extra space after 'O' for alignment
                } else {
                    System.out.print("X "); //adding an extra space after 'X' for alignment
                }
            }
            System.out.println(); //moving to the next line for the next row
        }
    }

    private static void print_tickets_info() {
        if (num_tickets_sold == 0) {
            System.out.println("Tickets have not been sold.");
            return;
        }

        System.out.println("Sold Tickets:");
        long totalAmount = 0;
        for (int i = 0; i < num_tickets_sold; i++) {
            Ticket ticket = ticketsSold[i];
            System.out.println("Ticket for Row " + ticket.getRow() + ", Seat " + ticket.getSeat() +
                    " - Price: £" + ticket.getPrice());
            totalAmount += ticket.getPrice(); //gathering ticket prices
        }
        System.out.println("Total amount for all tickets sold: £" + totalAmount);
    }





    private static void search_ticket() {
        while (true) {
            System.out.print("Enter the row letter A and D: ");
            String inputRow = scanner.next().toUpperCase();
            if (inputRow.length() == 1 && inputRow.charAt(0) >= 'A' && inputRow.charAt(0) <= 'D') {
                char row = inputRow.charAt(0);
                int rowID = row - 'A';
                int maxSeatNumber = SEATS_PER_ROW[rowID];

                int seatNumber;
                while (true) {
                    System.out.print("Enter the seat number between 1 and " + maxSeatNumber + ": ");
                    try {
                        seatNumber = scanner.nextInt();
                        if (seatNumber < 1 || seatNumber > maxSeatNumber) {
                            System.out.println("Invalid seat number.please enter a seat number between 1 and " + maxSeatNumber + ".");
                        } else {
                            break; //breaks if the seat number is valid
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input.please enter a valid integer for the seat number.");
                        scanner.next();
                    }
                }

                boolean ticketFound = false;
                for (int i = 0; i < num_tickets_sold; i++) {
                    Ticket ticket = ticketsSold[i];
                    if (ticket.getRow() == row && ticket.getSeat() == seatNumber) {
                        ticketFound = true;
                        System.out.println("\nTicket Information:\n");
                        System.out.println("Row: " + ticket.getRow() + ", Seat: " + ticket.getSeat());
                        System.out.println("Price: £" + ticket.getPrice());
                        System.out.println("\nPerson Information:\n");
                        System.out.println("Name: " + ticket.getPerson().getName());
                        System.out.println("Surname: " + ticket.getPerson().getSurname());
                        System.out.println("Email: " + ticket.getPerson().getEmail());
                        break;
                    }
                }

                if (!ticketFound) {
                    System.out.println("This seat is available.");
                }
                return; // Exits method
            } else {
                System.out.println("Invalid row.please enter a single letter between A and D.");
            }
        }
    }



    private static int priceCalculation(char row, int seatNumber) {

        if ((row == 'A' || row == 'B' || row == 'C' || row == 'D') && seatNumber >= 1 && seatNumber <= 5) {
            return 200;
        } else if ((row == 'A' || row == 'B' || row == 'C' || row == 'D') && seatNumber >= 6 && seatNumber <= 9) {
            return 150;
        } else {
            return 180;
        }
    }
}












































