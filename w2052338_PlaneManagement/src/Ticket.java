import java.io.FileWriter;
import java.io.IOException;

public class Ticket {
    private char row;
    private int seat;
    private int price;
    private Person person;

    public Ticket(char row, int seat, int price, Person person) {
        this.row = row;
        this.seat = seat;
        this.price = price;
        this.person = person;

    }

    public void setRow(char row) {

        this.row = row;
    }

    public char getRow() {

        return row;
    }

    public void setSeat(int seat) {

        this.seat = seat;
    }

    public int getSeat() {

        return seat;
    }

    public void setPrice(int price) {

        this.price = price;
    }

    public int getPrice() {

        return price;
    }

    public void setPerson(Person person) {

        this.person = person;
    }

    public Person getPerson() {

        return person;
    }

    public void print_ticket_info() {
        System.out.println("Information of the Ticket:");
        System.out.println("Row: " + row);
        System.out.println("Seat: " + seat);
        System.out.println("Price: £" + price);
        System.out.println("Person's Information:");
        person.print_information_of_the_person();
    }

    public void save() {
        String fileName = String.valueOf(this.row) + this.seat + ".txt";
       // String filePath = "C:\\Downloads\\w2052338_PlaneManagement\\src" + fileName;
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write("Ticket Information:\n");
            writer.write("\nRow: " + row + "\n");
            writer.write("Seat: " + seat + "\n");
            writer.write("Price: £" + price + "\n");

            writer.write("\nPerson Information:\n");
            writer.write("\nName: " + person.getName() + "\n");
            writer.write("Surname: " + person.getSurname() + "\n");
            writer.write("Email: " + person.getEmail() + "\n");

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();//providing the detail about the exception
        }
    }
}
