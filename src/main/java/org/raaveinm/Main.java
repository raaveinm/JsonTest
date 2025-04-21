package org.raaveinm;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final String DEFAULT_JSON_PATH = "src/main/resources/list.json";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GsonSerializable gsonSerializer = new GsonSerializable();

        boolean exit = false;
        while (!exit) {
            System.out.println("\n--- JSON Track Manager ---");
            System.out.println("""
                    Choose option:
                     1 - See example: Java Object -> JSON String
                     2 - See example: JSON String -> Java Object List
                     3 - Add track to JSON file
                     4 - Read JSON file and display content + parsed objects
                     5 - Parse JSON file into Java Object List
                     6 - Exit""");
            System.out.print("Enter option: ");

            int option = -1;
            try {
                option = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.err.println("Invalid input. Please enter a number.");
            } finally {
                scanner.nextLine();
            }

            switch (option) {
                case 1: {
                    System.out.println("\n--- Option 1: Object to JSON ---");
                    TrackInfo trackInfo = new TrackInfo("Light it up", "Camellia", "Crystallized", 194);
                    String parseJson = gsonSerializer.gsonSerialization(trackInfo);
                    System.out.println("Serialized JSON:");
                    System.out.println(parseJson);
                    break;
                }
                case 2: {
                    System.out.println("\n--- Option 2: JSON to Object List ---");
                    String json = """
                            [
                              {
                                "name": "Eclipse",
                                "artist": "Pink Floyd",
                                "album": "The Dark Side of The Moon",
                                "length": 130
                              }
                            ]""";
                    System.out.println("Input JSON String:");
                    System.out.println(json);
                    List<TrackInfo> tracks = gsonSerializer.gsonDeserialization(json);
                    System.out.println("Deserialized List:");
                    if (tracks != null) {
                        tracks.forEach(System.out::println);
                    } else {
                        System.out.println("Deserialization resulted in null.");
                    }
                    break;
                }
                case 3: {
                    System.out.println("\n--- Option 3: Add Track to File ---");
                    System.out.print("Enter json file path (leave blank to use default '" + DEFAULT_JSON_PATH + "'): ");
                    String filePathInput = scanner.nextLine();
                    String filePath = filePathInput.isBlank() ? DEFAULT_JSON_PATH : filePathInput;

                    System.out.print("Enter track name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter track artist: ");
                    String artist = scanner.nextLine();
                    System.out.print("Enter track album: ");
                    String album = scanner.nextLine();

                    Integer length = null;
                    while (length == null) {
                        System.out.print("Enter track length (as a number): ");
                        String lengthStr = scanner.nextLine();
                        try {
                            length = Integer.parseInt(lengthStr);
                        } catch (NumberFormatException e) {
                            System.err.println("Invalid input. Please enter a valid integer for length.");
                        }
                    }

                    TrackInfo trackInfoToAdd = new TrackInfo(name, artist, album, length);
                    System.out.println("\nAttempting to add track:");
                    System.out.println(gsonSerializer.gsonSerialization(trackInfoToAdd));
                    gsonSerializer.gsonBuilder(filePath, trackInfoToAdd);
                    break;
                }
                case 4: {
                    System.out.println("\n--- Option 4: Read File and Parse ---");
                    System.out.print("Enter json file path (leave blank to use default '" + DEFAULT_JSON_PATH + "'): ");
                    String filePathInput = scanner.nextLine();
                    String filePath = filePathInput.isBlank() ? DEFAULT_JSON_PATH : filePathInput;

                    try {
                        System.out.println("Reading file: " + filePath);
                        String jsonInput = Files.readString(Paths.get(filePath));

                        System.out.println("\nRaw JSON content:");
                        System.out.println(jsonInput);

                        System.out.println("\nParsed Object List:");
                        List<TrackInfo> tracks = gsonSerializer.gsonDeserialization(jsonInput);
                        if (tracks != null) {
                            tracks.forEach(System.out::println);
                        } else {
                            System.out.println("Deserialization resulted in null (maybe empty or invalid JSON?).");
                        }

                    } catch (IOException | InvalidPathException e) {
                        System.err.println("Error reading file '" + filePath + "': " + e.getMessage());
                    } catch (Exception e) {
                        System.err.println("An unexpected error occurred: " + e.getMessage());
                        e.printStackTrace();
                    }
                    break;
                }
                case 5: {
                    System.out.println("\n--- Option 5: Parse File ---");
                    System.out.print("Enter json file path (leave blank to use default '" + DEFAULT_JSON_PATH + "'): ");
                    String filePathInput = scanner.nextLine();
                    String filePath = filePathInput.isBlank() ? DEFAULT_JSON_PATH : filePathInput;

                    try {
                        System.out.println("Reading and parsing file: " + filePath);
                        String jsonInput = Files.readString(Paths.get(filePath));

                        List<TrackInfo> tracks = gsonSerializer.gsonDeserialization(jsonInput);

                        System.out.println("\nParsed Object List:");
                        if (tracks != null) {
                            tracks.forEach(System.out::println);
                        } else {
                            System.out.println("Deserialization resulted in null (maybe empty or invalid JSON?).");
                        }

                    } catch (IOException | InvalidPathException e) {
                        System.err.println("Error reading file '" + filePath + "': " + e.getMessage());
                    } catch (Exception e) {
                        System.err.println("An unexpected error occurred: " + e.getMessage());
                        e.printStackTrace();
                    }
                    break;
                }
                case 6: {
                    System.out.println("\nExiting program. Goodbye!");
                    exit = true;
                    break;
                }
                default:
                    if (option != -1) {
                        System.out.println("Invalid option selected. Please try again.");
                    }
            }
        }
        scanner.close();
    }
}
