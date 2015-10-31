package com.github.damianmcdonald.jmp3tagger.service;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.TagException;
import org.junit.Test;

import com.damianmcdonald.github.jmp3tagger.domain.MP3Info;
import com.github.damianmcdonald.jmp3tagger.AbstractJMP3TaggerTest;

public class MP3TaggerServiceTest extends AbstractJMP3TaggerTest {

	@Test
	 public void readAudioFileTest() throws IOException, CannotReadException, TagException, ReadOnlyFileException, InvalidAudioFrameException, CannotWriteException {
		MP3Info mp3 = new MP3Info(getMusicFolder() + "/hip-hop/dr. dre/compton/dr. dre - talking to my diary with taginfo.mp3");
		getMp3TaggerService().readAudioFile(mp3);
		assertEquals("Artist name must be dr. dre", "Dr. Dre", mp3.getArtist());
		assertEquals("Artist name must be Dr. Dre", "Dr. Dre", mp3.getArtist());
		assertEquals("Album name must be Compton", "Compton", mp3.getAlbum());
		assertEquals("Title must be Dr. Dre - Talking To My Diary With Taginfo", "Dr. Dre - Talking To My Diary With Taginfo", mp3.getTitle());
		assertEquals("Genre must be Hip-hop", "Hip-hop", mp3.getGenre());
	 }
	
	@Test
	 public void tagAudioFileTest() throws IOException, CannotReadException, TagException, ReadOnlyFileException, InvalidAudioFrameException, CannotWriteException {
		MP3Info mp3 = new MP3Info(getMusicFolder() + "/hip-hop/dr. dre/compton/dr. dre - talking to my diary no tag info.mp3");
		MP3Info taggedMp3 = getMp3TaggerService().tagAudioFile(mp3);
		assertEquals("Artist name must be Dr. Dre", "Dr. Dre", taggedMp3.getArtist());
		assertEquals("Album name must be Compton", "Compton", taggedMp3.getAlbum());
		assertEquals("Title must be Dr. Dre - Talking To My Diary No Tag Info", "Dr. Dre - Talking To My Diary No Tag Info", taggedMp3.getTitle());
		assertEquals("Title must be Hip-hop", "Hip-hop", taggedMp3.getGenre());
		final MP3File f = (MP3File) AudioFileIO.read(new File(taggedMp3.getFilePath()));
    	assertEquals("Album name must be Compton", "Compton", f.getID3v2Tag().getFirst(FieldKey.ALBUM));
    	assertEquals("Artist name must be Dr. Dre", "Dr. Dre", f.getID3v2Tag().getFirst(FieldKey.ARTIST));
    	assertEquals("Title must be Dr. Dre - Talking To My Diary No Tag Info", "Dr. Dre - Talking To My Diary No Tag Info", f.getID3v2Tag().getFirst(FieldKey.TITLE));
    	assertEquals("Genre must be Hip-Hop", "Hip-Hop", f.getID3v2Tag().getFirst(FieldKey.GENRE));
	}

}
