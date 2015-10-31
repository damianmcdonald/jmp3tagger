package com.github.damianmcdonald.jmp3tagger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.TagException;
import org.junit.Test;
import org.springframework.shell.core.CommandResult;

public class JMP3TaggerCommandsTest extends AbstractJMP3TaggerTest {

    @Test
    public void printGenreTest() throws IOException {
        final CommandResult result = getShell().executeCommand("printgenres");
        assertTrue("printgenres command should be true", result.isSuccess());
    }
    
    @Test
    public void listFilesTest() throws IOException {
    	final CommandResult result = getShell().executeCommand("listfiles " + pathToParameter(getMusicCliFolder()));
    	assertTrue("listfiles command should be true", result.isSuccess());
    }
    
    @Test
    public void writeFilesTest() throws IOException, CannotReadException, TagException, ReadOnlyFileException, InvalidAudioFrameException {
    	final CommandResult result = getShell().executeCommand("writefiles " + pathToParameter(getMusicCliFolder()));
    	assertTrue("writefiles command should be true", result.isSuccess());
    	final MP3File f = (MP3File) AudioFileIO.read(new File(getMusicCliFolder() + "/hip-hop/warren g/regulate/warren g - super soul sis.mp3"));
    	assertEquals("Album name should be Regulate", "Regulate", f.getID3v2Tag().getFirst(FieldKey.ALBUM));
    	assertEquals("Artist name should be Warren G", "Warren G", f.getID3v2Tag().getFirst(FieldKey.ARTIST));
    	assertEquals("Title should be Super Soul Sis", "Super Soul Sis", f.getID3v2Tag().getFirst(FieldKey.TITLE));
    	assertEquals("Genre must be Hip-Hop", "Hip-Hop", f.getID3v2Tag().getFirst(FieldKey.GENRE));
    }
    
}
