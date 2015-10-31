package com.damianmcdonald.github.jmp3tagger.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JMP3TaggerGenres {
	
	private static final Logger log = Logger.getLogger("JMP3TaggerGenres");
	
	private static final List<String> ID3V1_GENRES = Collections.unmodifiableList(
														Arrays.asList(
																"Blues","Alternative","AlternRock","Top 40",
																"Classic Rock","Ska","Bass","Christian Rap",
																"Country","Death Metal" ,"Soul","Pop/Funk",
																"Dance" ,"Pranks","Punk","Jungle",
																"Disco","Soundtrack","Space","Native American",
																"Funk","Euro-Techno","Meditative","Cabaret",
																"Grunge","Ambient","Instrumental Pop","New Wave",
																"Hip-Hop","Trip-Hop","Instrumental Rock","Psychadelic",
																"Jazz" ,"Vocal","Ethnic","Rave",
																"Metal","Jazz+Funk","Gothic","Showtunes",
																"New Age","Fusion","Darkwave","Trailer",
																"Oldies","Trance","Techno-Industrial","Lo-Fi",
																"Other","Classical","Electronic","Tribal",
																"Pop","Instrumental","Pop-Folk","Acid Punk",
																"R&B","Acid","Eurodance","Acid Jazz",
																"Rap","House","Dream","Polka",
																"Reggae","Game","Southern Rock","Retro",
																"Rock","Sound Clip","Comedy","Musical",
																"Techno","Gospel","Cult","Rock & Roll",
																"Industrial","Noise","Gangsta","Hard Rock"
																));
	
	public static String getGenre(final String inputGenre){
		for(final String genre : ID3V1_GENRES){
			if(inputGenre.equalsIgnoreCase(genre)){
				return genre;
			}
		}
		return "Other";
	}
	
	public static void printGenres(){
		for(final String genre : ID3V1_GENRES){
				log.log(Level.INFO, genre);
			}
		}
	
}
