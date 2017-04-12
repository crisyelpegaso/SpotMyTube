package org.crisyelpegaso.spotmytube.service;

import java.util.ArrayList;
import java.util.List;

import org.crisyelpegaso.spotmytube.model.TubePlaylistItem;
import org.crisyelpegaso.spotmytube.org.crisyelpegaso.spotmytube.App;

import com.wrapper.spotify.Api;
import com.wrapper.spotify.methods.AddTrackToPlaylistRequest;
import com.wrapper.spotify.methods.PlaylistCreationRequest;
import com.wrapper.spotify.methods.TrackSearchRequest;
import com.wrapper.spotify.models.Page;
import com.wrapper.spotify.models.Playlist;
import com.wrapper.spotify.models.Track;

public class SpotService {

	final static String SPOTIFY_PLAYLIST_NAME = "SpotTest";
	private Api api;
	
	public SpotService(Api api){
		this.api = api;
	}
	public boolean createPlaylistWithTubeItem(List<TubePlaylistItem> playlistItems, String playlistId) {
		
		final PlaylistCreationRequest request = api.createPlaylist(App.MY_USER_ID, SPOTIFY_PLAYLIST_NAME)
      		  .publicAccess(true)
      		  .build();
      
		try {
			final Playlist playlist = request.get();
			System.out.println("You just created this playlist!");
			System.out.println("Its title is " + playlist.getName());
      	  	
      	  	List<Track> spotItems = new ArrayList<Track>(playlistItems.size());
      	  	for (TubePlaylistItem tubeItem : playlistItems) {
      	  		Track track = this.findSimilarTrack(tubeItem);
      	  		if (track != null) {
      	  			spotItems.add(track);	
      	  		}
      	  	}
      	  
      	  	return this.saveTracksInPlaylist(spotItems, playlist.getId());
      	  	
		} catch (Exception e) {
			System.out.println("Something went wrong!" + e.getMessage());
		}
		return false;
		
	}
	
	public Track findSimilarTrack(TubePlaylistItem playlistItem) {
		final TrackSearchRequest request = api.searchTracks(playlistItem.getTitle()).market("AR").build();

		   Page<Track> trackSearchResult;
		try {
			trackSearchResult = request.get();
			System.out.println("I got " + trackSearchResult.getTotal() + " results!");
			   Track track = trackSearchResult.getItems().get(0);
			   System.out.println("Returning first with name" + track.getName() + "");
			   return track;
		} catch (Exception e) {
			System.out.println("OH NO!! :| " + e.getMessage());
			return null;
		}
		   
		
	}
	
	public boolean saveTracksInPlaylist(List<Track> tracks, String playlistId){
		
		final List<String> tracksToAdd = new ArrayList<String>();
		for (Track track : tracks){
			tracksToAdd.add("spotify:track:" + track.getId());
		}
		
		final int insertIndex = 0;

		final AddTrackToPlaylistRequest request = api.addTracksToPlaylist(App.MY_USER_ID, playlistId, tracksToAdd)
				.position(insertIndex)
				.build();

		try {
			request.get();
		} catch (Exception e) {
			System.out.println("Something went wrong!" + e.getMessage());
			return false;
		}	
		return true;
	}
}
