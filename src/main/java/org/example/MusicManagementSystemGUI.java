package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class MusicManagementSystemGUI extends JFrame {
    private MusicLibrary library;
    private JTextField titleField;
    private JTextField artistField;
    private JTextField durationField;
    private JTextArea outputArea;

    public MusicManagementSystemGUI() {
        super("Music Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLayout(new BorderLayout());

        // Create components
        titleField = new JTextField();
        artistField = new JTextField();
        durationField = new JTextField();
        JButton addButton = new JButton("Add Song");
        JButton removeButton = new JButton("Remove Song");
        JButton viewDetailsButton = new JButton("View Song Details");
        JButton viewAllButton = new JButton("View All Songs");
        JButton searchByArtistButton = new JButton("Search by Artist");
        JButton searchByDurationButton = new JButton("Search by Duration");
        outputArea = new JTextArea();

        // Create panels
        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        inputPanel.add(new JLabel("Title:"));
        inputPanel.add(titleField);
        inputPanel.add(new JLabel("Artist:"));
        inputPanel.add(artistField);
        inputPanel.add(new JLabel("Duration:"));
        inputPanel.add(durationField);
        inputPanel.add(addButton);
        inputPanel.add(removeButton);

        JPanel actionPanel = new JPanel(new GridLayout(4, 1));
        actionPanel.add(viewDetailsButton);
        actionPanel.add(viewAllButton);
        actionPanel.add(searchByArtistButton);
        actionPanel.add(searchByDurationButton);

        // Add components to the frame
        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(outputArea), BorderLayout.CENTER);
        add(actionPanel, BorderLayout.EAST);

        // Register event listeners
        // ...

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    library = new MusicLibrary();
                    String title = titleField.getText();
                    String artist = artistField.getText();
                    int duration = Integer.parseInt(durationField.getText());

                    Song song = new Song(title, artist, duration);
                    library.addSong(song);
                    outputArea.setText("Song added successfully!");
                } catch (NumberFormatException ex) {
                    outputArea.setText("Invalid input for duration! Please enter a valid integer value.");
                } catch (Exception ex) {
                    outputArea.setText("An error occurred: " + ex.getMessage());
                }
            }
        });

        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    library = new MusicLibrary();
                    String title = titleField.getText();
                    library.removeSong(title);
                    outputArea.setText("Song removed successfully!");
                } catch (Exception ex) {
                    outputArea.setText("An error occurred: " + ex.getMessage());
                }
            }
        });

        viewDetailsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    library = new MusicLibrary();
                    String title = titleField.getText();
                    Song foundSong = library.getSong(title);
                    if (foundSong != null) {
                        outputArea.setText(foundSong.toString());
                    } else {
                        outputArea.setText("Song not found!");
                    }
                } catch (Exception ex) {
                    outputArea.setText("An error occurred: " + ex.getMessage());
                }
            }
        });

        viewAllButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    library = new MusicLibrary();
                    List<Song> allSongs = library.getAllSongs();
                    if (!allSongs.isEmpty()) {
                        StringBuilder sb = new StringBuilder("All Songs:\n");
                        for (Song s : allSongs) {
                            sb.append(s.toString()).append("\n");
                        }
                        outputArea.setText(sb.toString());
                    } else {
                        outputArea.setText("No songs in the library!");
                    }
                } catch (Exception ex) {
                    outputArea.setText("An error occurred: " + ex.getMessage());
                }
            }
        });

        searchByArtistButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    library = new MusicLibrary();
                    String artist = artistField.getText();
                    List<Song> songsByArtist = library.searchByArtist(artist);
                    if (!songsByArtist.isEmpty()) {
                        StringBuilder sb = new StringBuilder("Songs by Artist:\n");
                        for (Song s : songsByArtist) {
                            sb.append(s.toString()).append("\n");
                        }
                        outputArea.setText(sb.toString());
                    } else {
                        outputArea.setText("No songs by the artist!");
                    }
                } catch (Exception ex) {
                    outputArea.setText("An error occurred: " + ex.getMessage());
                }
            }
        });

        searchByDurationButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    library = new MusicLibrary();
                    int minDuration = Integer.parseInt(titleField.getText());
                    int maxDuration = Integer.parseInt(artistField.getText());
                    List<Song> songsByDuration = library.searchByDuration(minDuration, maxDuration);
                    if (!songsByDuration.isEmpty()) {
                        StringBuilder sb = new StringBuilder("Songs by Duration:\n");
                        for (Song s : songsByDuration) {
                            sb.append(s.toString()).append("\n");
                        }
                        outputArea.setText(sb.toString());
                    } else {
                        outputArea.setText("No songs within the duration range!");
                    }
                } catch (NumberFormatException ex) {
                    outputArea.setText("Invalid input for duration! Please enter valid integer values.");
                } catch (Exception ex) {
                    outputArea.setText("An error occurred: " + ex.getMessage());
                }
            }
        });


// ...

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MusicManagementSystemGUI gui = new MusicManagementSystemGUI();
                gui.setVisible(true);
            }
        });
    }
}
