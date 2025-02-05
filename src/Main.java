import java.io.*;
import java.util.*;

class AddressBook {

    private final Map<String, String> contacts;

    public AddressBook() {
        contacts = new HashMap<>();
    }

    // Cargar contactos desde un archivo de texto
    public void load(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] contact = line.split(",");
                if (contact.length == 2) {
                    contacts.put(contact[0], contact[1]);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al cargar el archivo.");
        }
    }

    // Guardar contactos en un archivo de texto
    public void save(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Map.Entry<String, String> entry : contacts.entrySet()) {
                writer.write(entry.getKey() + "," + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar el archivo.");
        }
    }

    // Listar todos los contactos
    public void list() {
        if (contacts.isEmpty()) {
            System.out.println("No hay contactos en la agenda.");
        } else {
            System.out.println("Contactos:");
            for (Map.Entry<String, String> entry : contacts.entrySet()) {
                System.out.println(entry.getKey() + " : " + entry.getValue());
            }
        }
    }

    // Crear un nuevo contacto
    public void create(String phoneNumber, String name) {
        if (!contacts.containsKey(phoneNumber)) {
            contacts.put(phoneNumber, name);
            System.out.println("Contacto creado: " + phoneNumber + " : " + name);
        } else {
            System.out.println("El número ya existe en la agenda.");
        }
    }

    // Eliminar un contacto
    public void delete(String phoneNumber) {
        if (contacts.containsKey(phoneNumber)) {
            contacts.remove(phoneNumber);
            System.out.println("Contacto eliminado: " + phoneNumber);
        } else {
            System.out.println("El número no existe en la agenda.");
        }
    }

    // Menú interactivo
    public void menu() {
        Scanner scanner = new Scanner(System.in);
        String option;
        do {
            System.out.println("1. Listar contactos");
            System.out.println("2. Crear contacto");
            System.out.println("3. Eliminar contacto");
            System.out.println("4. Salir");
            System.out.print("Selecciona una opción: ");
            option = scanner.nextLine();

            switch (option) {
                case "1":
                    list();
                    break;
                case "2":
                    System.out.print("Ingresa el número de teléfono: ");
                    String phoneNumber = scanner.nextLine();
                    System.out.print("Ingresa el nombre: ");
                    String name = scanner.nextLine();
                    create(phoneNumber, name);
                    break;
                case "3":
                    System.out.print("Ingresa el número de teléfono a eliminar: ");
                    String deleteNumber = scanner.nextLine();
                    delete(deleteNumber);
                    break;
                case "4":
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción inválida. Intenta de nuevo.");
            }
        } while (!option.equals("4"));
    }

    public static void main(String[] args) {
        AddressBook addressBook = new AddressBook();
        addressBook.load("contacts.txt"); // Cargar contactos desde el archivo
        addressBook.menu(); // Mostrar el menú interactivo
        addressBook.save("contacts.txt"); // Guardar los cambios en el archivo
    }
}
