package org.example;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class MusicLibrary {
    private Map<String, Long> indexMap;
    private RandomAccessFile libraryFile;

    public MusicLibrary() throws IOException {
        indexMap = new HashMap<>();
        libraryFile = new RandomAccessFile("music_library.txt", "rw");
        buildIndex();
    }

    private void buildIndex() throws IOException {
        long filePointer = 0;
        String line;
        while ((line = libraryFile.readLine()) != null) {
            String[] parts = line.split(",");
            String title = parts[0];
            indexMap.put(title, filePointer);
            filePointer = libraryFile.getFilePointer();
        }
    }

    public void addSong(Song song) throws IOException {
        String songInfo = song.getTitle() + "," + song.getArtist() + "," + song.getDuration() + "\n";
        long filePointer = libraryFile.length();
        libraryFile.seek(filePointer);
        libraryFile.writeBytes(songInfo);
        indexMap.put(song.getTitle(), filePointer);
    }

    public void removeSong(String title) throws IOException {
        Long filePointer = indexMap.remove(title);
        if (filePointer != null) {
            libraryFile.setLength(filePointer);
        }
    }

    public Song getSong(String title) throws IOException {
        Long filePointer = indexMap.get(title);
        if (filePointer != null) {
            libraryFile.seek(filePointer);
            String line = libraryFile.readLine();
            String[] parts = line.split(",");
            String songTitle = parts[0];
            String artist = parts[1];
            int duration = Integer.parseInt(parts[2]);
            return new Song(songTitle, artist, duration);
        }
        return null;
    }

    public List<Song> getAllSongs() throws IOException {
        List<Song> songs = new ArrayList<>();
        libraryFile.seek(0);
        String line;
        while ((line = libraryFile.readLine()) != null) {
            String[] parts = line.split(",");
            String title = parts[0];
            String artist = parts[1];
            int duration = Integer.parseInt(parts[2]);
            songs.add(new Song(title, artist, duration));
        }
        return songs;
    }

    public List<Song> searchByArtist(String artist) throws IOException {
        List<Song> songs = new ArrayList<>();

        for (Map.Entry<String, Long> entry : indexMap.entrySet()) {
            libraryFile.seek(entry.getValue());
            String line = libraryFile.readLine();
            String[] parts = line.split(",");
            String songArtist = parts[1];

            if (songArtist.equalsIgnoreCase(artist)) {
                String title = parts[0];
                int duration = Integer.parseInt(parts[2]);
                songs.add(new Song(title, songArtist, duration));
            }
        }

        return songs;
    }

    public List<Song> searchByDuration(int minDuration, int maxDuration) throws IOException {
        List<Song> songs = new ArrayList<>();

        for (Map.Entry<String, Long> entry : indexMap.entrySet()) {
            libraryFile.seek(entry.getValue());
            String line = libraryFile.readLine();
            String[] parts = line.split(",");
            String title = parts[0];
            String artist = parts[1];
            int duration = Integer.parseInt(parts[2]);

            if (duration >= minDuration && duration <= maxDuration) {
                songs.add(new Song(title, artist, duration));
            }
        }

        return songs;
    }


    public void close() throws IOException {
        libraryFile.close();
    }
}
