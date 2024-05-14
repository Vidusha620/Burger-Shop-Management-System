import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

public class ClassesVersion {
    private static final String[] cashier1 = new String[2];
    private static final String[] cashier2 = new String[3];
    private static final String[] cashier3 = new String[5];
    private static final String[] customers = new String[100];
    private static int burgerCount = 50;
    private static int income = 0;
    private static boolean run = true;

    public static void main(String[] args) {
        while (run) {
            if (burgerCount <= 10) {
                System.out.println("Warning!Stocks are running out.");
            }

            Menu();
            String input = check();
            option(input);
            rearrangeArrays(cashier1, cashier2, cashier3, customers);

        }

    }

    private static void rearrangeArrays(String[]... arrays) {
        for (String[] array : arrays) {
            int index = 0;

            for (int i = 0; i < array.length; i++) {
                if (array[i] != null) {
                    array[index++] = array[i];
                }
            }

            while (index < array.length) {
                array[index++] = null;
            }
        }
    }

    public static void Menu() {
        System.out.println();
        System.out.println("Menu:");
        System.out.println("100 or VFQ: View all Queues.");
        System.out.println("101 or VEQ: View all Empty Queues.");
        System.out.println("102 or ACQ: Add customer to a Queue.");
        System.out.println("103 or RCQ: Remove a customer from a Queue. (From a specific location)");
        System.out.println("104 or PCQ: Remove a served customer.");
        System.out.println("105 or VCS: View Customers Sorted in alphabetical order (Do not use library sort routine)");
        System.out.println("106 or SPD: Store Program Data into file.");
        System.out.println("107 or LPD: Load Program Data from file.");
        System.out.println("108 or STK: View Remaining burgers Stock.");
        System.out.println("109 or AFS: Add burgers to Stock.");
        System.out.println("999 or EXT: Exit the Program.");
    }

    private static String check() {
        String pattern = "^[a-zA-Z]+$";
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Choose an option: ");
            String input = scanner.nextLine();

            if (Pattern.matches(pattern, input)) {
                List<String> validAlphabetOptions = Arrays.asList("VEQ", "ACQ", "RCQ", "PCQ", "VCS", "SPD", "LPD", "STK", "AFS", "IFQ", "EXT");
                if (validAlphabetOptions.contains(input.toUpperCase())) {
                    return input.toUpperCase();
                } else {
                    System.out.println("Invalid choice. Please choose a valid option.");
                }
            } else {
                try {
                    int inputInt = Integer.parseInt(input);
                    if ((100 <= inputInt && inputInt <= 110) || inputInt == 999) {
                        return String.valueOf(inputInt);
                    } else {
                        System.out.println("Invalid choice.Please choose a valid option.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid choice.Please choose a valid option.");
                }
            }
        }
    }

    private static void option(String input) {
        switch (input) {
            case "VFQ", "100" -> viewAllQueues();
            case "VEQ", "101" -> viewEmptyQueues();
            case "ACQ", "102" -> addCustomerToAQueue();
            case "RCQ", "103" -> removeACustomerFromAQueue();
            case "PCQ", "104" -> removeAServedCustomer();
            case "VCS", "105" -> viewCustomersSortedAlphabetical();
            case "SPD", "106" -> storeProgramDataToFile();
            case "LPD", "107" -> loadProgramDataFromFile();
            case "STK", "108" -> System.out.println(burgerCount + " " + "burgers are remaining.");
            case "IFQ", "110" -> printTheIncome();
            case "EXT", "999" -> run = false;
        }

    }

    private static void viewAllQueues() {
        System.out.println("*****************");
        System.out.println("*   Cashiers   *");
        System.out.println("*****************");

        for (int i = 0; i < 5; i++) {
            System.out.print("     ");

            if (i < cashier1.length) {
                if (cashier1[i] == null || cashier1[i].equals("null")) {
                    System.out.print("X");
                } else {
                    System.out.print("O");
                }
            } else {
                System.out.print(" ");
            }

            System.out.print(" ");

            if (i < cashier2.length) {
                if (cashier2[i] == null || cashier2[i].equals("null")) {
                    System.out.print("X");
                } else {
                    System.out.print("O");
                }
            } else {
                System.out.print(" ");
            }

            System.out.print(" ");

            if (cashier3[i] == null || cashier3[i].equals("null")) {
                System.out.print("X");
            } else {
                System.out.print("O");
            }

            System.out.println();
        }
    }

    private static void viewEmptyQueues() {
        System.out.println("*****************");
        System.out.println("*   Cashiers   *");
        System.out.println("*****************");

        int maxCashiers = cashier3.length;

        for (int i = 0; i < maxCashiers; i++) {
            System.out.print("    ");

            if (i < cashier1.length && isQueueEmpty(cashier1)) {
                System.out.print("X");
            } else {
                System.out.print(" ");
            }

            System.out.print(" ");

            if (i < cashier2.length && isQueueEmpty(cashier2)) {
                System.out.print("X");
            } else {
                System.out.print(" ");
            }

            System.out.print(" ");

            if (isQueueEmpty(cashier3)) {
                System.out.print("X");
            }

            System.out.println();
        }
    }

    private static boolean isQueueEmpty(String[] array) {
        for (String element : array) {
            if (element != null) {
                return false;
            }
        }
        return true;
    }

    private static void addCustomerToAQueue() {
        int cashierNumber;

        String pattern = "^[a-zA-Z]+$";

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter the cashier number (1, 2, or 3): ");
            try {
                cashierNumber = scanner.nextInt();
                scanner.nextLine();
                if (1 <= cashierNumber && cashierNumber <= 3) {
                    break;
                } else {
                    System.out.println("Invalid cashier number. Please enter a valid cashier number.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid cashier number. Please enter a valid cashier number.");
                scanner.nextLine();
            }
        }

        String[] selectedCashier;
        int maxCustomers;
        if (cashierNumber == 1) {
            selectedCashier = cashier1;
            maxCustomers = 2;
        } else if (cashierNumber == 2) {
            selectedCashier = cashier2;
            maxCustomers = 3;
        } else {
            selectedCashier = cashier3;
            maxCustomers = 5;
        }

        if (nullCount(selectedCashier) == maxCustomers) {
            System.out.println("Cashier " + cashierNumber + " is already full.");
        } else {

            String firstName;
            do {
                System.out.print("Enter the customer's first name: ");
                firstName = scanner.nextLine();
            } while (!Pattern.matches(pattern, firstName));
            String secondName;
            do {
                System.out.print("Enter the customer's second name: ");
                secondName = scanner.nextLine();
            } while (!Pattern.matches(pattern, secondName));


            selectedCashier[nullCount(selectedCashier)] = firstName + secondName;
            System.out.println("Customer " + firstName + " " + secondName + " added to cashier " + cashierNumber + ".");
            customers[nullCount(customers)] = firstName + secondName;
            class waitingQueue {
                private final ArrayList<Customer> queue;

                public waitingQueue() {
                    // Constructor: Initializes an empty queue
                    this.queue = new ArrayList<>();
                }
            }
        }
    }

    private static void removeACustomerFromAQueue() {
        int cashierNumber;
        int customerNumber;

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter the cashier number (1, 2, or 3): ");
            try {
                cashierNumber = scanner.nextInt();
                scanner.nextLine();
                if (1 <= cashierNumber && cashierNumber <= 3) {
                    break;
                } else {
                    System.out.println("Invalid cashier number. Please enter a valid cashier number.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid cashier number. Please enter a valid cashier number.");
                scanner.nextLine();
            }
        }

        String[] selectedCashier;
        if (cashierNumber == 1) {
            selectedCashier = cashier1;
        } else if (cashierNumber == 2) {
            selectedCashier = cashier2;
        } else {
            selectedCashier = cashier3;
        }

        int customerCount = nullCount(selectedCashier);
        if (customerCount == 0) {
            System.out.println("Cashier " + cashierNumber + " is empty.");
        } else {
            while (true) {
                System.out.print("Enter the number of customers to remove (1-" + customerCount + "): ");
                try {
                    customerNumber = scanner.nextInt();
                    scanner.nextLine();
                    if (1 <= customerNumber && customerNumber <= customerCount) {
                        break;
                    } else {
                        System.out.println("Invalid number of customers. Please enter a valid number of customers.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid number of customers. Please enter a valid number of customers.");
                    scanner.nextLine();
                }
            }

            String customerName = selectedCashier[customerNumber - 1];
            selectedCashier[customerNumber - 1] = null;
            int count = 0;
            for (String name : customers) {
                if (Objects.equals(name, customerName)) {
                    customers[count] = null;
                }
                count++;
            }
            System.out.println("Customer " + customerName + " removed from cashier " + cashierNumber + ".");
        }
    }

    private static void removeAServedCustomer() {
        int cashierNumber;

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter the cashier number (1, 2, or 3): ");
            try {
                cashierNumber = scanner.nextInt();
                scanner.nextLine();
                if (1 <= cashierNumber && cashierNumber <= 3) {
                    break;
                } else {
                    System.out.println("Invalid cashier number. Please enter a valid cashier number.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid cashier number. Please enter a valid cashier number.");
                scanner.nextLine();
            }
        }

        String[] selectedCashier;
        if (cashierNumber == 1) {
            selectedCashier = cashier1;
        } else if (cashierNumber == 2) {
            selectedCashier = cashier2;
        } else {
            selectedCashier = cashier3;
        }

        if (selectedCashier[0] == null) {
            System.out.println("Cashier number" + cashierNumber + " is empty.");
        } else {
            String customerName = selectedCashier[0];
            selectedCashier[0] = null;
            System.out.println("Served customer " + customerName + " is removed from the cashier  number" + cashierNumber + ".");
            int count = 0;
            for (String name : customers) {
                if (Objects.equals(name, customerName)) {
                    customers[count] = null;
                }
                count++;
            }
            burgerCount -= 5;
            income += 650 * 5;
        }
    }

    private static void printTheIncome() {
        System.out.println("Income is" + income);
    }

    private static int nullCount(String[] list) {
        int count = 0;
        for (String item : list) {
            if (item != null) {
                count++;
            }
        }
        return count;
    }

    private static void viewCustomersSortedAlphabetical() {
        List<String> sortedList = new ArrayList<>();
        for (String name : customers) {
            if (name != null) {
                sortedList.add(name);

            }
        }

        int n = sortedList.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (sortedList.get(j).compareTo(sortedList.get(j + 1)) > 0) {
                    String temp = sortedList.get(j);
                    sortedList.set(j, sortedList.get(j + 1));
                    sortedList.set(j + 1, temp);
                }
            }
        }

        for (String name : sortedList) {
            if (name != null) {
                System.out.println(name);
            }
        }
    }

    private static void storeProgramDataToFile() {
        try (FileWriter writer = new FileWriter("program_data.txt")) {
            writer.write("Cashier 1: " + String.join(", ", cashier1) + "\n");
            writer.write("Cashier 2: " + String.join(", ", cashier2) + "\n");
            writer.write("Cashier 3: " + String.join(", ", cashier3) + "\n");
            writer.write("Customers: " + String.join(", ", customers) + "\n");
            writer.write("Burger Stock: " + burgerCount + "\n");

            System.out.println("Program data stored successfully.");
        } catch (IOException e) {
            System.out.println("Error in writing program data to file: " + e.getMessage());
        }
    }

    private static void loadProgramDataFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("program_data.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Cashier 1:")) {
                    String[] values = line.substring(line.indexOf(":") + 1).trim().split(", ");
                    System.arraycopy(values, 0, cashier1, 0, values.length);
                } else if (line.startsWith("Cashier 2:")) {
                    String[] values = line.substring(line.indexOf(":") + 1).trim().split(", ");
                    System.arraycopy(values, 0, cashier2, 0, values.length);
                } else if (line.startsWith("Cashier 3:")) {
                    String[] values = line.substring(line.indexOf(":") + 1).trim().split(", ");
                    System.arraycopy(values, 0, cashier3, 0, values.length);
                } else if (line.startsWith("Customers:")) {
                    String[] values = line.substring(line.indexOf(":") + 1).trim().split(", ");
                    System.arraycopy(values, 0, customers, 0, values.length);
                } else if (line.startsWith("Burger Stock:")) {
                    burgerCount = Integer.parseInt(line.substring(line.indexOf(":") + 1).trim());
                }
            }

            System.out.println("Program data loaded successfully.");
        } catch (IOException e) {
            System.out.println("Error in loading program data from file: " + e.getMessage());
        }

        convertNullToNull(cashier1, cashier2, cashier3, customers);
    }

    private static void convertNullToNull(String[]... arrays) {
        for (String[] array : arrays) {
            for (int i = 0; i < array.length; i++) {
                if ("null".equals(array[i])) {
                    array[i] = null;
                }
            }
        }
    }
    record Customer(String firstName, String secondName, int requiredBurgerCount) {

        public String getFullName() {
            return firstName + " " + secondName;
        }

    }


    static class FoodQueue {
        private final ArrayList<Customer> queue;
        private final ArrayList<Customer> servedCustomers;
        private final int queueSize;

        public FoodQueue(int queueSize) {
            this.queue = new ArrayList<>();
            this.queueSize = queueSize;
            this.servedCustomers = new ArrayList<>();
        }

        public int getQueueSize() {
            return this.queue.size();
        }

        private boolean queueSizeChecker() {
            return this.queue.size() < this.queueSize;
        }

        public void addCustomer(Customer customer) {
            if (queueSizeChecker())
                this.queue.add(customer);
            else {
                System.out.println("The queue is full.Please wait.");
            }

        }

        public Customer getCustomer(int customerNumber) {
            try {
                return this.queue.get(customerNumber);
            } catch (IndexOutOfBoundsException e) {
                return null;
            }
        }

        public void servedCustomer() {
            this.servedCustomers.add(this.queue.get(0));
            this.queue.remove(0);
            System.out.println("The customer has been served.");
        }

        public boolean isQueueEmpty() {
            return this.queue.isEmpty();
        }

        public boolean isQueueFull() {
            return this.queue.size() == this.queueSize;
        }

        public void removeCustomer(int index) {
            this.queue.remove(index);
        }

        public int getIncome() {
            int total = 0;

            for (Customer customer : this.queue) {
                int burgerPrice = 650;
                total += customer.requiredBurgerCount() * burgerPrice;
            }
            return total;
        }

        public void add(Customer item) {
            // Adds an item to the end of the queue
            this.queue.add(item);
        }
        public boolean isEmpty() {
            // Checks if the queue is empty
            return queue.isEmpty();
        }

        public Customer get() {
            if (isEmpty()) {
                // If the queue is empty, cannot dequeue an item
                System.out.println("Queue is empty.");
                return null;
            }

            // Removes and returns the first item from the queue
            return this.queue.remove(0);
        }

        public ArrayList<Customer> getCustomers() {
            return this.queue;
        }
    }
}

