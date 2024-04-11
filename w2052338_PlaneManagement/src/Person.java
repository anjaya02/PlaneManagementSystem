public class Person {
    //declaring instance variables
    private String name;
    private String surname;
    private String email;

    //constructor,hence no void here
    public Person(String name,String surname,String email){
        this.name=name;
        this.surname=surname;
        this.email=email;

    }
    public void setName(String name){

        this.name=name;
    }
    public String getName(){

        return name;
    }
    public void setSurname(String surname){

        this.surname=surname;
    }
    public String getSurname(){

        return surname;
    }
    public void setEmail(String email){
        this.email = email;
    } // Added a semicolon here

    public String getEmail(){

        return email;
    }
    public void print_information_of_the_person() {
        System.out.println("Name: " + name);
        System.out.println("Surname: " + surname);
        System.out.println("Email: " + email);
    }

}
