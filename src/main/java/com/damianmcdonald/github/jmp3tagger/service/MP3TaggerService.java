package com.damianmcdonald.github.jmp3tagger.service;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.datatype.Artwork;
import org.jaudiotagger.tag.id3.ID3v1Tag;
import org.springframework.stereotype.Component;

import com.damianmcdonald.github.jmp3tagger.domain.MP3Info;
import com.damianmcdonald.github.jmp3tagger.utils.JMP3TaggerConstants;
import com.damianmcdonald.github.jmp3tagger.utils.JMP3TaggerGenres;

@Component
public class MP3TaggerService {

	final Logger log = Logger.getLogger("MP3TaggerService");
	
	public MP3Info tagAudioFile(final MP3Info mp3Info) throws IOException, CannotReadException, TagException, ReadOnlyFileException, InvalidAudioFrameException, CannotWriteException{
		
		if(!new File(mp3Info.getFilePath()).exists() || !new File(mp3Info.getFilePath()).isFile()){
			log.log(Level.SEVERE, "Error! File " + mp3Info.getFilePath() + " does not exist");
			return null;
		}
		
		if(!new File(mp3Info.getAlbumArt()).exists() || !new File(mp3Info.getAlbumArt()).isFile()){
			log.log(Level.WARNING, "Warning! Album art " + mp3Info.getAlbumArt() + " does not exist");
		}
		
		final MP3File f = (MP3File) AudioFileIO.read(new File(mp3Info.getFilePath()));
		
		final Artwork artwork = new Artwork();
		artwork.setFromFile(new File(mp3Info.getAlbumArt()));
		
		final Tag v2Tag = f.getTagOrCreateAndSetDefault();
		final ID3v1Tag v1Tag  = f.getID3v1Tag();
		
		String title = "";
		
		if(v2Tag != null){	
			if(v2Tag.getFirst(FieldKey.TITLE) != null && StringUtils.isNotEmpty(v2Tag.getFirst(FieldKey.TITLE))){
				title = v2Tag.getFirst(FieldKey.TITLE);
			}
			
			if(StringUtils.isEmpty(title)) {
				title = mp3Info.getTitle();
			}
			
			v2Tag.setField(FieldKey.TITLE,title);
			v2Tag.setField(FieldKey.ARTIST,mp3Info.getArtist());
			v2Tag.setField(FieldKey.ALBUM, mp3Info.getAlbum());
			v2Tag.setField(FieldKey.COMMENT,JMP3TaggerConstants.AUDIO_TAG_COMMENT);
			v2Tag.setField(FieldKey.GENRE,JMP3TaggerGenres.getGenre(mp3Info.getGenre()));
			v2Tag.deleteArtworkField();
			v2Tag.setField(artwork);
		} else {
				log.log(Level.WARNING, "File (" + mp3Info.getFilePath() + ") does not have an id2v2 tag.");
		}
		
		if(v1Tag != null){	
				if(v1Tag.getFirstTitle() != null && StringUtils.isEmpty(title) && StringUtils.isNotEmpty(v1Tag.getFirstTitle())){
					title = v1Tag.getFirstTitle();
				}
				
				v1Tag.setTitle(title);
				v1Tag.setArtist(mp3Info.getArtist());
				v1Tag.setAlbum(mp3Info.getAlbum());
				v1Tag.setComment(JMP3TaggerConstants.AUDIO_TAG_COMMENT);
				v1Tag.setGenre(JMP3TaggerGenres.getGenre(mp3Info.getGenre()));
		} else {
			log.log(Level.WARNING, "File (" + mp3Info.getFilePath() + ") does not have an id2v1 tag.");
		}
		
		if(StringUtils.isEmpty(title)){
			title = mp3Info.getTitle();
		}
		
		mp3Info.setTitle(title);

		f.commit();
		
		return mp3Info;
		
	}
	
	public void readAudioFile(final MP3Info musicInfo) throws IOException, CannotReadException, TagException, ReadOnlyFileException, InvalidAudioFrameException, CannotWriteException{
		if(!new File(musicInfo.getFilePath()).exists() || !new File(musicInfo.getFilePath()).isFile()){
			System.err.println("File " + musicInfo.getFilePath() + " does not exist");
			return;
		}
		
		final MP3File f = (MP3File) AudioFileIO.read(new File(musicInfo.getFilePath()));

		final Tag v2Tag = f.getTag();
		final ID3v1Tag v1Tag  = f.getID3v1Tag();
		
		log.log(Level.INFO, "IDv2Tag info: " + ToStringBuilder.reflectionToString(v2Tag));
		log.log(Level.INFO, "IDv1Tag info: " + ToStringBuilder.reflectionToString(v1Tag));
	}
	
}
