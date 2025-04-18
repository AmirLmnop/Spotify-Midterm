import _3potify.*;

public class Main {
    public static void main(String[] args) {
        try {
            // Create users
            User user1 = new User("alex_green", "mypassword123");
            User user2 = new User("sarah_blue", "safepass789");

            // Test duplicate username
            try {
                User user3 = new User("alex_green", "differentpass");
            } catch (InvalidOperationException e) {
                System.out.println("Expected error: " + e.getMessage());
            }

            // Test short password
            try {
                User user4 = new User("new_user", "abc");
            } catch (InvalidOperationException e) {
                System.out.println("Expected error: " + e.getMessage());
            }

            // Follow user
            user1.follow(user2);
            System.out.println(user1.getUsername() + " is now following " + user2.getUsername());

            // Create music
            Music song1 = new Music("Blinding Lights");
            song1.setSinger(user2);
            Music.allMusic.add(song1);

            Music song2 = new Music("Save Your Tears");
            song2.setSinger(user2);
            Music.allMusic.add(song2);

            Music song3 = new Music("Blinding Lights");
            song3.setSinger(user1);
            Music.allMusic.add(song3);

            // Search music
            System.out.println("Search results for 'Blinding Lights': " + song1.search("Blinding Lights").size());
            System.out.println("Search results for 'Blinding Lights' by sarah_blue: " +
                    song1.search("Blinding Lights", user2).size());

            // Test regular user
            System.out.println("\nTesting regular user:");
            user1.playMusic(song1);
            user1.playMusic(song2);
            System.out.println("Stream count for song1: " + song1.getNumberOfStream());

            // Test playlist creation for regular user
            try {
                user1.creatPlayList("My Favorites", user1);
            } catch (InvalidOperationException e) {
                System.out.println("Expected error: " + e.getMessage());
            }

            // Test play limit for regular user
            try {
                for (int i = 0; i < 5; i++) {
                    user1.playMusic(song1);
                }
                System.out.println("Stream count for song1: " + song1.getNumberOfStream());
                user1.playMusic(song1); // Should throw exception
            } catch (InvalidOperationException e) {
                System.out.println("Expected error: " + e.getMessage());
            }

            // Upgrade to premium
            System.out.println("\nUpgrading to premium:");
            user1.buyPremium(user1, 3);

            // Test premium features
            System.out.println("\nTesting premium user:");
            user1.creatPlayList("My Favorites", user1);
            System.out.println("Created playlist successfully");

            // Test unlimited plays
            for (int i = 0; i < 10; i++) {
                user1.playMusic(song1);
            }
            System.out.println("Stream count for song1: " + song1.getNumberOfStream());

            // Add songs to playlist
            Playlist playlist = user1.getPlaylists().getFirst();
            playlist.addMusic(song1, "mypassword123");
            playlist.addMusic(song2, "mypassword123");

            // Test duplicate song in playlist
            try {
                playlist.addMusic(song1, "mypassword123");
            } catch (InvalidOperationException e) {
                System.out.println("Expected error: " + e.getMessage());
            }

            // Test wrong password
            try {
                playlist.addMusic(song3, "wrongpassword");
            } catch (InvalidOperationException e) {
                System.out.println("Expected error: " + e.getMessage());
            }

            // Search in playlist
            System.out.println("Search in playlist: " +
                    playlist.searchInPlaylist("Blinding Lights").size() + " results");

            // Play playlist
            System.out.println("\nPlaying playlist:");
            playlist.playPlaylist();

        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}