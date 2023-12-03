package view;

import java.util.Hashtable;
import java.util.Scanner;

public class TextMenu {
    private Hashtable<String, Command> commandsTable;
    public TextMenu(){ commandsTable =new Hashtable<String, Command>(); }
    public void addCommand(Command command){ commandsTable.put(command.getKey(),command);}
    private void printMenu(){
        for(Command command : commandsTable.values()){
            String menuOption=String.format("%4s : %s", command.getKey(), command.getDescription());
            System.out.println(menuOption);
        }
    }
    public void show(){
        Scanner scanner = new Scanner(System.in);
        while (true){
            printMenu();
            System.out.print("Input the option: ");
            String key = scanner.nextLine();
            Command command = commandsTable.get(key);
            if (command == null)
            {
                System.out.println("Invalid Option");
                continue;
            }
            command.execute();
        }
    }
}
