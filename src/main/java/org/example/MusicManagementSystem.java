package org.example;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class MusicManagementSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MusicLibrary library = null;

        try {
            library = new MusicLibrary();
            boolean exit = false;

            while (!exit) {
                System.out.println("---- Music Management System ----");
                System.out.println("1. Add Song");
                System.out.println("2. Remove Song");
                System.out.println("3. View Song Details");
                System.out.println("4. View All Songs");
                System.out.println("5. Search by artist");
                System.out.println("6. Search by Duration");
                System.out.println("7. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        System.out.print("Enter song title: ");
                        String title = scanner.nextLine();
                        System.out.print("Enter artist name: ");
                        String artist = scanner.nextLine();
                        System.out.print("Enter duration in seconds: ");
                        int duration = scanner.nextInt();
                        scanner.nextLine();

                        Song song = new Song(title, artist, duration);
                        library.addSong(song);
                        System.out.println("Song added successfully!");
                        break;

                    case 2:
                        System.out.print("Enter song title to remove: ");
                        title = scanner.nextLine();
                        library.removeSong(title);
                        System.out.println("Song removed successfully!");
                        break;

                    case 3:
                        System.out.print("Enter song title to view details: ");
                        title = scanner.nextLine();
                        Song foundSong = library.getSong(title);
                        if (foundSong != null) {
                            System.out.println(foundSong);
                        } else {
                            System.out.println("Song not found!");
                        }
                        break;

                    case 4:
                        List<Song> allSongs = library.getAllSongs();
                        if (!allSongs.isEmpty()) {
                            System.out.println("All Songs:");
                            for (Song s : allSongs) {
                                System.out.println(s);
                            }
                        } else {
                            System.out.println("No songs in the library!");
                        }
                        break;
                    case 5:
                        System.out.print("Enter artist name to search: ");
                        String artists = scanner.nextLine();
                        library.searchByArtist(artists);
                        break;

                    case 6:
                        System.out.print("Enter minimum duration (in seconds): ");
                        int minDuration = scanner.nextInt();
                        System.out.print("Enter maximum duration (in seconds): ");
                        int maxDuration = scanner.nextInt();
                        scanner.nextLine();
                        library.searchByDuration(minDuration, maxDuration);
                        break;
                    case 7:
                        exit = true;
                        break;

                    default:
                        System.out.println("Invalid choice!");
                }

                System.out.println();
            }



        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        } finally {
            if (library != null) {
                try {
                    library.close();
                } catch (IOException e) {
                    System.out.println("An error occurred while closing the library: " + e.getMessage());
                }
            }
            scanner.close();
        }
    }
}
