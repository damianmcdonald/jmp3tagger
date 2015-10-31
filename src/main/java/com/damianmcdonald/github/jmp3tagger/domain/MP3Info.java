package com.damianmcdonald.github.jmp3tagger.domain;

import java.io.File;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.damianmcdonald.github.jmp3tagger.utils.JMP3TaggerConstants;

public class MP3Info {
	
	private String title;
	private String artist;
	private String album;
	private String albumArt;
	private String genre;
	private String filePath;
	private String directoryPath;
	
	public MP3Info(final String filePath){
		this.filePath = filePath;
		this.title = FilenameUtils.removeExtension(WordUtils.capitalize(new File(filePath).getName()));
		this.artist = WordUtils.capitalize(new File(filePath).getParentFile().getParentFile().getName());
		this.album = WordUtils.capitalize(new File(filePath).getParentFile().getName());
		this.genre = WordUtils.capitalize(new File(filePath).getParentFile().getParentFile().getParentFile().getName());
		this.albumArt = new File(filePath).getParentFile() + File.separator + JMP3TaggerConstants.ALBUM_ART_FILENAME;
		this.directoryPath = new File(filePath).getParentFile().getAbsolutePath();
	}
	
	public String getArtist() { return artist; }

	public String getAlbum() { return album; }

	public String getAlbumArt() { return albumArt; }

	public String getGenre() { return genre; }

	public String getFilePath() { return filePath; }
	
	public String getTitle() { return title; }

	public void setTitle(final String title) { this.title = title; }
	
	public String getDirectoryPath() { return directoryPath; }
	
	@Override
	public String toString(){
		return ToStringBuilder.reflectionToString(this);
	}
	
}
