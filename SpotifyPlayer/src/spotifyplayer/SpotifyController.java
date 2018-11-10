package spotifyplayer;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;


public class SpotifyController{
    final static private String SPOTIFY_CLIENT_ID     = "c57cfc910044475ea429a1033ea51a9e"; // Dummy account
    final static private String SPOTIFY_CLIENT_SECRET = "2c02603fbff749948a2bbc758ac46d7c";
    
    public static String getArtistId(String artistNameQuery)
    {
        String artistId = "";
        
        try
        {
            // TODO - From an artist string, get the spotify ID
            // Recommended Endpoint: https://api.spotify.com/v1/search
            // Parse the JSON output to retrieve the ID
        
            String endpoint = "https://api.spotify.com/v1/search";
            String params = "type=artist&q=" + artistNameQuery;
            String jsonOutput = spotifyEndpointToJson(endpoint, params);
                        
            JsonObject root = new JsonParser().parse(jsonOutput).getAsJsonObject();
            JsonObject artist = root.get("artists").getAsJsonObject();
            JsonArray items = artist.get("items").getAsJsonArray();
            
            if(items.size() == 0){
                return null;
            }
            
            JsonObject items2 = items.get(0).getAsJsonObject();

            artistId = items2.get("id").toString().replaceAll("\"", "");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return artistId;
    }
    
    public static ArrayList<String> getAlbumIdsFromArtist(String artistId)
    {
        if(artistId == null){
            return null;
        }
        
        ArrayList<String> albumIds = new ArrayList<>();
 
        try
        {
            // TODO - Retrieve album ids from an artist id
            // Recommended endpoint {id} is the id of the artist in parameter: 
            //             https://api.spotify.com/v1/artists/{id}/albums
            //
            // Arguments - Filter for the CA market, and limit to 50 albums
            
            String endpoint = "https://api.spotify.com/v1/artists/" + artistId + "/albums";
            String jsonOutput = spotifyEndpointToJson(endpoint, "");
            JsonObject albums = new JsonParser().parse(jsonOutput).getAsJsonObject();
            JsonArray items = albums.get("items").getAsJsonArray();

            for (int i = 0; i < items.size() ; i++) {
                JsonObject currentAlbum = items.get(i).getAsJsonObject();
                String id = currentAlbum.get("id").toString().replaceAll("\"", "");

                albumIds.add(id);
            }
        
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        return albumIds;
    }
    
    public static String getFirstAlbumIdFromArtist(String artistId)
    {
        if(artistId == null){
            return null;
        }
        
        String id = "";
        try
        {
            // TODO - Retrieve album ids from an artist id
            // Recommended endpoint {id} is the id of the artist in parameter: 
            //             https://api.spotify.com/v1/artists/{id}/albums
            //
            // Arguments - Filter for the CA market, and limit to 50 albums
            
            String endpoint = "https://api.spotify.com/v1/artists/" + artistId + "/albums";
            String jsonOutput = spotifyEndpointToJson(endpoint, "");
            JsonObject albums = new JsonParser().parse(jsonOutput).getAsJsonObject();
            JsonArray items = albums.get("items").getAsJsonArray();

            
            JsonObject currentAlbum = items.get(0).getAsJsonObject();
            id = currentAlbum.get("id").toString().replaceAll("\"", "");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        return id;
    }
    
    public static ArrayList<Album> getAlbumDataFromArtist(String artistId)
    {
        if(artistId == null){
            return null;
        }
        
        ArrayList<String> albumIds = getAlbumIdsFromArtist(artistId);
        ArrayList<Album> albums = new ArrayList<>();
        
        for(String albumId : albumIds)
        {
            try
            {
                // TODO - Retrieve all album data from the list of album ids for an artist
                // 
                // You can have a look at the Album class included
                // 
                // Endpoint : https://api.spotify.com/v1/albums/{id}
                // Note:      {id} is the id of the album
                //
                // Arguments - Filter for the CA market

                
                // Warning!! For the preview_url, the json item can be a string 
                //           or null, below is the code to write for parsing
                // 
                //
                //    if (item.get("preview_url").isJsonNull() == false)
                //    {
                //        previewUrl = item.get("preview_url").getAsString();
                //    }
                
                String endpoint = "https://api.spotify.com/v1/albums/" + albumId;
//                String params = "/search?market=CA"; //?? filter in finding album ids? or filter the songs?
                String jsonOutput = spotifyEndpointToJson(endpoint, "");
//                System.out.println(jsonOutput);
                
                JsonObject albumJson = new JsonParser().parse(jsonOutput).getAsJsonObject();
                
                // Artist Name
                JsonArray artists = albumJson.get("artists").getAsJsonArray();
                JsonObject artist = artists.get(0).getAsJsonObject();
                String artistName = artist.get("name").toString().replaceAll("\"", "");

                // Album Name
                String albumName = albumJson.get("name").toString().replaceAll("\"", "");
                
                // Cover image
                JsonArray images = albumJson.getAsJsonArray("images");
                JsonObject bigCover = images.get(0).getAsJsonObject();
                String coverURL = bigCover.get("url").toString().replaceAll("\"", "");

                // Album tracks
                    //int trackNumber = albumJson.get("total_tracks").getAsInt();
                JsonObject tracksInfo = albumJson.get("tracks").getAsJsonObject();
                JsonArray tracks = tracksInfo.get("items").getAsJsonArray();
                ArrayList<Track> albumTracks = new ArrayList<>();
                
                for (int i = 0; i < tracks.size(); i++) {
                    JsonObject trackInfo = tracks.get(i).getAsJsonObject();
                    int trackNumber = trackInfo.get("track_number").getAsInt();
                    String trackTitle = trackInfo.get("name").toString().replaceAll("\"", "");
                    int trackDuration = trackInfo.get("duration_ms").getAsInt();
                    String trackUrl = trackInfo.get("preview_url").toString().replaceAll("\"", ""); // only the preview
                    
                    albumTracks.add(new Track(trackNumber, trackTitle, trackDuration, trackUrl));
                }

                albums.add(new Album(artistName, albumName,  coverURL, albumTracks));                
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }            
        }
        
        return albums;
    }
    
    public static ArrayList<Album> getFirstAlbumDataFromArtist(String artistId)
    {
        if(artistId == null){
            return null;
        }
        
        String albumId = getFirstAlbumIdFromArtist(artistId);
        ArrayList<Album> albums = new ArrayList<>();
        

            try
            {
                // TODO - Retrieve all album data from the list of album ids for an artist
                // 
                // You can have a look at the Album class included
                // 
                // Endpoint : https://api.spotify.com/v1/albums/{id}
                // Note:      {id} is the id of the album
                //
                // Arguments - Filter for the CA market

                
                // Warning!! For the preview_url, the json item can be a string 
                //           or null, below is the code to write for parsing
                // 
                //
                //    if (item.get("preview_url").isJsonNull() == false)
                //    {
                //        previewUrl = item.get("preview_url").getAsString();
                //    }
                
                String endpoint = "https://api.spotify.com/v1/albums/" + albumId;
//                String params = "/search?market=CA"; //?? filter in finding album ids? or filter the songs?
                String jsonOutput = spotifyEndpointToJson(endpoint, "");
                
                JsonObject albumJson = new JsonParser().parse(jsonOutput).getAsJsonObject();
                
                // Artist Name
                JsonArray artists = albumJson.get("artists").getAsJsonArray();
                JsonObject artist = artists.get(0).getAsJsonObject();
                String artistName = artist.get("name").toString().replaceAll("\"", "");

                // Album Name
                String albumName = albumJson.get("name").toString().replaceAll("\"", "");
                
                // Cover image
                JsonArray images = albumJson.getAsJsonArray("images");
                JsonObject bigCover = images.get(0).getAsJsonObject();
                String coverURL = bigCover.get("url").toString().replaceAll("\"", "");

                // Album tracks
                    //int trackNumber = albumJson.get("total_tracks").getAsInt();
                JsonObject tracksInfo = albumJson.get("tracks").getAsJsonObject();
                JsonArray tracks = tracksInfo.get("items").getAsJsonArray();
                ArrayList<Track> albumTracks = new ArrayList<>();
                
                for (int i = 0; i < tracks.size(); i++) {
                    JsonObject trackInfo = tracks.get(i).getAsJsonObject();
                    int trackNumber = trackInfo.get("track_number").getAsInt();
                    String trackTitle = trackInfo.get("name").toString().replaceAll("\"", "");
                    int trackDuration = trackInfo.get("duration_ms").getAsInt();
                    String trackUrl = trackInfo.get("preview_url").toString().replaceAll("\"", ""); // only the preview
                    
                    albumTracks.add(new Track(trackNumber, trackTitle, trackDuration, trackUrl));
                }

                albums.add(new Album(artistName, albumName,  coverURL, albumTracks));                
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }            
        
        
        return albums;
    }


    // This code will help you retrieve the JSON data from a spotify end point
    // It takes care of the complicated parts such as the authentication and 
    // connection to the Web API
    // 
    // You shouldn't have to modify any of the code...
    private static String spotifyEndpointToJson(String endpoint, String params)
    {
        params = params.replace(' ', '+');

        try
        {
            String fullURL = endpoint;
            if (params.isEmpty() == false)
            {
                fullURL += "?"+params;
            }
            
            URL requestURL = new URL(fullURL);
            
            HttpURLConnection connection = (HttpURLConnection)requestURL.openConnection();
            String bearerAuth = "Bearer " + getSpotifyAccessToken();
            connection.setRequestProperty ("Authorization", bearerAuth);
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String inputLine;
            String jsonOutput = "";
            while((inputLine = in.readLine()) != null)
            {
                jsonOutput += inputLine;
            }
            in.close();
            
            return jsonOutput;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        return "";
    }


    // This implements the Client Credentials Authorization Flows
    // Based on the Spotify API documentation
    // 
    // It retrieves the Access Token based on the client ID and client Secret  
    //
    // You shouldn't have to modify any of this code...          
    private static String getSpotifyAccessToken()
    {
        try
        {
            URL requestURL = new URL("https://accounts.spotify.com/api/token");
            
            HttpURLConnection connection = (HttpURLConnection)requestURL.openConnection();
            String keys = SPOTIFY_CLIENT_ID+":"+SPOTIFY_CLIENT_SECRET;
            String postData = "grant_type=client_credentials";
            
            String basicAuth = "Basic " + new String(Base64.getEncoder().encode(keys.getBytes()));
            
            // Send header parameter
            connection.setRequestProperty ("Authorization", basicAuth);
            
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            // Send body parameters
            OutputStream os = connection.getOutputStream();
            os.write( postData.getBytes() );
            os.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            
            String inputLine;
            String jsonOutput = "";
            while((inputLine = in.readLine()) != null)
            {
                jsonOutput += inputLine;
            }
            in.close();
            
            JsonElement jelement = new JsonParser().parse(jsonOutput);
            JsonObject rootObject = jelement.getAsJsonObject();
            String token = rootObject.get("access_token").getAsString();

            return token;
        }
        catch(Exception e)
        {
            System.out.println("Something wrong here... make sure you set your Client ID and Client Secret properly!");
            e.printStackTrace();
        }
        
        return "";
    }
}
