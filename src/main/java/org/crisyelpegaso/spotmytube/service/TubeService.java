package org.crisyelpegaso.spotmytube.service;

import java.util.ArrayList;
import java.util.List;

import org.crisyelpegaso.spotmytube.model.TubePlaylistItem;

public class TubeService {

	public List<TubePlaylistItem> getPlaylist(String playlistId){
		TubePlaylistItem amItem = new TubePlaylistItem("Crying Lightning");
		TubePlaylistItem theKillsItem = new TubePlaylistItem("Siberian Nights");
		List<TubePlaylistItem> playlistItems = new ArrayList<TubePlaylistItem>();
		playlistItems.add(amItem);
		playlistItems.add(theKillsItem);
		return playlistItems;
	}
}
