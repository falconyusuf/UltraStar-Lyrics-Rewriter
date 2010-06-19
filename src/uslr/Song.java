/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uslr;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Sebastian
 */
public class Song {

    public Song() {
    }

    public void load(BufferedReader file) throws Exception {
        lyrics = new ArrayList<LyricsLine>();
        metadata = new ArrayList<MetaDataLine>();

        // Regex to extract tag and value of a metadata item
        Pattern metadataPattern = Pattern.compile("^#(.+):(.*)$");
        // Regex to separate a line in the input file into prefix and lyrics
        Pattern syllablePattern = Pattern.compile("(^. \\d+ -?\\d+ -?\\d+ )(.*)$");
        LyricsLine currentLine = new LyricsLine();

        String syllStr = file.readLine();
        boolean endReached = (syllStr == null);
        while(!endReached) {
            char firstChar = syllStr.charAt(0);
            switch(firstChar) {
                case '#': { // metadata
                    Matcher m = metadataPattern.matcher(syllStr);
                    System.out.println(syllStr);
                    if(m.matches()) {
                        String tag = m.group(1);
                        String value = m.group(2);
                        System.out.println("match: " + tag + ":" + value);
                        this.addMetaData(new MetaDataLine(tag, value));
                    }
                    else {
                        System.out.println("NO MATCH");
                    }
                    break;
                }
                case '-': // line break
                    currentLine.setLineBreakMarker(syllStr);
                    this.addLine(currentLine);
                    currentLine = new LyricsLine();
                    break;
                case ':': // regular syllable
                case '*': // golden note
                case 'F': { // freestyle note
                    // Separate the line into prefix and lyrics by way of a regular expression
                    Matcher m = syllablePattern.matcher(syllStr);
                    System.out.println(syllStr);
                    if(m.matches()) {
                        String syllPrefix = m.group(1);
                        String syllLyrics = m.group(2);
                        System.out.println("match: " + syllLyrics);
                        currentLine.addSyllable(new LyricsSyllable(currentLine, syllPrefix, syllLyrics));
                    }
                    else {
                        System.out.println("NO MATCH");
                    }
                    break;
                }
                case 'E': // end of song
                    currentLine.setLineBreakMarker(syllStr);
                    endReached = true;
                    break;
            }

            syllStr = file.readLine();
            if(syllStr == null) endReached = true;
        }
        this.addLine(currentLine);
    }

    public void save(BufferedWriter writer) throws IOException {
        for(Iterator<MetaDataLine> i = metadata.iterator(); i.hasNext();) {
            i.next().write(writer);
        }
        for(Iterator<LyricsLine> i = lyrics.iterator(); i.hasNext();) {
            i.next().write(writer);
        }
    }

    public void addLine(LyricsLine line) {
        lyrics.add(line);
    }

    public void addMetaData(MetaDataLine line) {
        metadata.add(line);
    }

    public ArrayList<LyricsLine> getLyrics() {
        return lyrics;
    }

    public LyricsSyllable getFirstSyllable() throws Exception {
        if(lyrics.size() < 1) {
            throw new Exception("Get first syllable of empty lyrics");
        }
        return lyrics.get(0).getFirstSyllable();
    }

    public LyricsSyllable getPreviousSyllable(LyricsSyllable syl) throws Exception {
        LyricsLine line = syl.getLine();
        LyricsSyllable newsyl = line.getPreviousSyllable(syl);
        if(newsyl == null) {
            int lineIdx = lyrics.indexOf(line) - 1;
            if(lineIdx < 0) {
                return syl;
            }
            else {
                return lyrics.get(lineIdx).getLastSyllable();
            }
        }
        else {
            return newsyl;
        }
    }

    public LyricsSyllable getNextSyllable(LyricsSyllable syl) throws Exception {
        LyricsLine line = syl.getLine();
        LyricsSyllable newsyl = line.getNextSyllable(syl);
        if(newsyl == null) {
            int lineIdx = lyrics.indexOf(line) + 1;
            if(lineIdx >= lyrics.size()) {
                return syl;
            }
            else {
                return lyrics.get(lineIdx).getFirstSyllable();
            }
        }
        else {
            return newsyl;
        }
    }

    private ArrayList<MetaDataLine> metadata;
    private ArrayList<LyricsLine> lyrics;
}
