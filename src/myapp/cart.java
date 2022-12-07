package myapp;

//import java.io.Console;
import java.util.*;
import myapp.Database;

public class cart {
    private static String currentUser;
    private static List<String> cart;
    // TODO
    // login
    //     - Set Current User
    //     - If user does not exist, create user
    //     - Load user
    // save
    //     - Check Current User
    //     - Write cart to file
    //     - If file does not exist, create
    // users
    //     - print all registered users

    public static void main(String[] args) {
        
        Database.setupDirectory();
        System.out.println("Welcome to your shopping cart");
        //cart = new LinkedList<String>();
        
        while (true) {
            String input = System.console().readLine().trim().replace(",", "");
            String[] words = input.split(" ");
            int len = words.length;

            if (len == 0) continue;
            eventHandler(words);
        }
    }

    public static void eventHandler(String[] words) {

        String trigger = words[0].toLowerCase().trim();
        
        if ((currentUser == null) && !(trigger.equals("login") || trigger.equals("users") || trigger.equals("exit"))) {
            System.out.println("Please login to use cart");
            System.out.println("type <users> to see all registered users");
            return;
        }

        if (trigger.equals("exit") || trigger.equals("quit")) {
            System.out.println("Application Closed");
            System.exit(0);
        }

        if (trigger.equals("list")) {
            listCart();
            return;
        }

        if (trigger.equals("add")) {
            addToCart(words);
            listCart();
            return;
        }

        if (trigger.equals("delete") || trigger.equals("remove")) {
            deleteFromCart(words);
            listCart();
            return;
        }

        if (trigger.equals("login")) {
            loginUser(words);
            return;
        }

        if (trigger.equals("users")) {
            Database.listUsers();
            return;
        }

        if (trigger.equals("save")) {
            saveUser(currentUser);
            return;
        }

        // No one of the key words
        System.out.println("Invalid Trigger");
    }

    public static void listCart() {
        if (cart.isEmpty()) {
            System.out.println("Your cart is empty");
        } else {
            for(int i = 0; i < cart.size(); i++) {
                System.out.println((i+1) + ". " + cart.get(i));
            }
        }
    }

    public static void addToCart(String[] words){
        if (words.length < 2) {
            System.out.println("No items added");
        } else {
            int i = 1;
            while (i < words.length) {
                cart.add(words[i]);
                i++;
            }
        }
    }
    
    public static void deleteFromCart(String[] words) {
        if (words.length < 2) {
            System.out.println("No items deleted");
            return;
        }

        List<Integer> indices =  new LinkedList<Integer>();
        int i = 1;

        while (i < words.length) {
            try {
                indices.add(Integer.parseInt(words[i]));
            } catch (Exception e) {
                System.out.println(words[i] + " cannot be deleted"); 
                continue;
            }
            i++;
        }
        indices.sort(Comparator.reverseOrder());
        System.out.println(indices);
        //int last = -1;

        for (int j = 0; j < indices.size(); j++) {
            //if(indices.get(j) == last) continue;
            //last = indices.get(j);

            if (indices.get(j) <= 0 || indices.get(j) > cart.size()) continue;
            cart.remove(indices.get(j) - 1);
        }
    }

    public static void loginUser(String[] words) {
        if (words.length < 2) {
            System.out.println("No username given");
            return;
        }
        currentUser = words[1];
        cart = Database.loadUser(words[1]);

    }

    public static void saveUser(String user) {
        if (cart.isEmpty()) {
            System.out.println("Nothing to save, cart is empty");
            return;
        }
        
        String cartContents = "";
        for (int i = 0; i < cart.size(); i++) {
            cartContents += cart.get(i) + "\n";
        }
        Database.overwriteFile(user + ".db", cartContents);
        System.out.println("Cart saved!");
    }
}
