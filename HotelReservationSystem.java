import java.util.*;

class Room {
    private int roomNumber;
    private String category;
    private boolean isBooked;

    public Room(int roomNumber, String category) {
        this.roomNumber = roomNumber;
        this.category = category;
        this.isBooked = false;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getCategory() {
        return category;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void book() {
        isBooked = true;
    }

    public void cancel() {
        isBooked = false;
    }

    public String toString() {
        return "Room " + roomNumber + " [" + category + "] - " + (isBooked ? "Booked" : "Available");
    }
}

class Booking {
    private String customerName;
    private int roomNumber;

    public Booking(String customerName, int roomNumber) {
        this.customerName = customerName;
        this.roomNumber = roomNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String toString() {
        return "Booking: " + customerName + " -> Room " + roomNumber;
    }
}

public class HotelReservationSystem {
    private List<Room> rooms = new ArrayList<>();
    private List<Booking> bookings = new ArrayList<>();

    public HotelReservationSystem() {
        loadRooms();
    }

    private void loadRooms() {
        rooms.add(new Room(101, "Standard"));
        rooms.add(new Room(102, "Standard"));
        rooms.add(new Room(201, "Deluxe"));
        rooms.add(new Room(202, "Deluxe"));
        rooms.add(new Room(301, "Suite"));
    }

    public void showAvailableRooms() {
        boolean found = false;
        for (Room room : rooms) {
            if (!room.isBooked()) {
                System.out.println(room);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No rooms available.");
        }
    }

    public void bookRoom(String customerName, String category) {
        for (Room room : rooms) {
            if (room.getCategory().equalsIgnoreCase(category) && !room.isBooked()) {
                room.book();
                Booking booking = new Booking(customerName, room.getRoomNumber());
                bookings.add(booking);
                System.out.println("Room booked successfully: " + booking);
                return;
            }
        }
        System.out.println("No available rooms in category: " + category);
    }

    public void cancelBooking(String customerName) {
        Iterator<Booking> iterator = bookings.iterator();
        boolean found = false;
        while (iterator.hasNext()) {
            Booking booking = iterator.next();
            if (booking.getCustomerName().equalsIgnoreCase(customerName)) {
                for (Room room : rooms) {
                    if (room.getRoomNumber() == booking.getRoomNumber()) {
                        room.cancel();
                        break;
                    }
                }
                iterator.remove();
                System.out.println("Booking cancelled for: " + customerName);
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("No booking found for: " + customerName);
        }
    }

    public void viewAllBookings() {
        if (bookings.isEmpty()) {
            System.out.println("No bookings yet.");
        } else {
            for (Booking b : bookings) {
                System.out.println(b);
            }
        }
    }

    public static void main(String[] args) {
        HotelReservationSystem hotel = new HotelReservationSystem();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nHotel Reservation Menu");
            System.out.println("1. Show Available Rooms");
            System.out.println("2. Book a Room");
            System.out.println("3. Cancel Booking");
            System.out.println("4. View All Bookings");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int choice;
            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
                continue;
            }
            scanner.nextLine();

            switch (choice) {
                case 1:
                    hotel.showAvailableRooms();
                    break;
                case 2:
                    System.out.print("Enter your name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter room category (Standard/Deluxe/Suite): ");
                    String category = scanner.nextLine();
                    hotel.bookRoom(name, category);
                    break;
                case 3:
                    System.out.print("Enter your name to cancel booking: ");
                    String nameCancel = scanner.nextLine();
                    hotel.cancelBooking(nameCancel);
                    break;
                case 4:
                    hotel.viewAllBookings();
                    break;
                case 5:
                    System.out.println("Goodbye.");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
