package fava.functionable;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.io.StringReader;
import java.nio.channels.ReadableByteChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.regex.Pattern;

import javax.xml.ws.WebServiceException;

import fava.signatures.FnMap;

import sun.nio.ch.ChannelInputStream;


public class FStringInput extends Functionable<String> implements Closeable{

	static String linebreak = "\r\n|[\n\r\u2028\u2029\u0085]";
	static String whitespace = "\\s+";
	
	static Pattern linebreakPattern = Pattern.compile(linebreak);
	static Pattern whitespacePattern = Pattern.compile(whitespace);
	
	private boolean scannerMode = true;
	private Scanner scanner;
	private CustomReader reader;
	
	
	private FStringInput(File file, Pattern delim) throws FileNotFoundException {
		scanner = new Scanner(file).useDelimiter(delim);
	}
	
	private FStringInput(Readable readable, Pattern delim) {
		scanner = new Scanner(readable).useDelimiter(delim);
	}
	
	private FStringInput(InputStream instream, Pattern delim) {
		scanner = new Scanner(instream).useDelimiter(delim);
	}
	
	private FStringInput(ReadableByteChannel channel, Pattern delim) {
		scanner = new Scanner(channel).useDelimiter(delim);
	}
	
	private FStringInput(String source, Pattern delim) {
		scanner = new Scanner(source).useDelimiter(delim);
	}
	
	
	private FStringInput(File file, String delim) throws FileNotFoundException {
		scanner = new Scanner(file).useDelimiter(delim);
	}
	
	private FStringInput(Readable readable, String delim) {
		scanner = new Scanner(readable).useDelimiter(delim);
	}
	
	private FStringInput(InputStream instream, String delim) {
		scanner = new Scanner(instream).useDelimiter(delim);
	}
	
	private FStringInput(ReadableByteChannel channel, String delim) {
		scanner = new Scanner(channel).useDelimiter(delim);
	}
	
	private FStringInput(String source, String delim) {
		scanner = new Scanner(source).useDelimiter(delim);
	}
	
	

	
	public static FStringInput lines(File file) throws FileNotFoundException {
		FStringInput f = new FStringInput(file, linebreakPattern);
		f.scannerMode = false;
		f.reader = new LinesReader(file);
		return f;
	}
	
	public static FStringInput lines(Readable readable) {
		FStringInput f =  new FStringInput(readable, linebreakPattern);
		f.scannerMode = false;
		f.reader = new LinesReader(readable);
		return f;
	}
	
	public static FStringInput lines(InputStream instream) {
		FStringInput f =  new FStringInput(instream, linebreakPattern);
		f.scannerMode = false;
		f.reader = new LinesReader(instream);
		return f;
	}
	
	public static FStringInput lines(ReadableByteChannel channel) {
		FStringInput f =  new FStringInput(channel, linebreakPattern);
		f.scannerMode = false;
		f.reader = new LinesReader(channel);
		return f;
	}
	
	public static FStringInput lines(String source) {
		FStringInput f =  new FStringInput(source, linebreakPattern);
		f.scannerMode = false;
		f.reader = new LinesReader(source);
		return f;
	}
	
	
	
	
	public static FStringInput words(File file) throws FileNotFoundException {
		FStringInput f = new FStringInput(file, whitespacePattern);
		f.scannerMode = false;
		f.reader = new WordsReader(file);
		return f;
	}
	
	public static FStringInput words(Readable readable) {
		FStringInput f =  new FStringInput(readable, whitespacePattern);
		f.scannerMode = false;
		f.reader = new WordsReader(readable);
		return f;
	}
	
	public static FStringInput words(InputStream instream) {
		FStringInput f =  new FStringInput(instream, whitespacePattern);
		f.scannerMode = false;
		f.reader = new WordsReader(instream);
		return f;
	}
	
	public static FStringInput words(ReadableByteChannel channel) {
		FStringInput f =  new FStringInput(channel, whitespacePattern);
		f.scannerMode = false;
		f.reader = new WordsReader(channel);
		return f;
	}
	
	public static FStringInput words(String source) {
		FStringInput f =  new FStringInput(source, whitespacePattern);
		f.scannerMode = false;
		f.reader = new WordsReader(source);
		return f;
	}

	
	
	/*
	public static FStringInput words(File file) throws FileNotFoundException {
		return new FStringInput(file, whitespacePattern);
	}
	
	public static FStringInput words(Readable readable) {
		return new FStringInput(readable, whitespacePattern);
	}
	
	public static FStringInput words(InputStream instream) {
		return new FStringInput(instream, whitespacePattern);
	}
	
	public static FStringInput words(ReadableByteChannel channel) {
		return new FStringInput(channel, whitespacePattern);
	}
	
	public static FStringInput words(String source) {
		return new FStringInput(source, whitespacePattern);
	}
	*/
	
	
	
	
	
	public static FStringInput tokens(File file, String delim) throws FileNotFoundException {
		return new FStringInput(file, Pattern.compile(delim));
	}
	
	public static FStringInput tokens(Readable readable, String delim) { 
		return new FStringInput(readable, Pattern.compile(delim));
	}
	
	public static FStringInput tokens(InputStream instream, String delim) { 
		return new FStringInput(instream, Pattern.compile(delim));
	}
	
	public static FStringInput tokens(ReadableByteChannel channel, String delim) { 
		return new FStringInput(channel, Pattern.compile(delim));
	}
	
	public static FStringInput tokens(String source, String delim) { 
		return new FStringInput(source, Pattern.compile(delim));
	}
	
	
	
	public static FStringInput tokens(File file, Pattern delim) throws FileNotFoundException {
		return new FStringInput(file, delim);
	}
	
	public static FStringInput tokens(Readable readable, Pattern delim) { 
		return new FStringInput(readable, delim);
	}
	
	public static FStringInput tokens(InputStream instream, Pattern delim) { 
		return new FStringInput(instream, delim);
	}
	
	public static FStringInput tokens(ReadableByteChannel channel, Pattern delim) { 
		return new FStringInput(channel, delim);
	}
	
	public static FStringInput tokens(String source, Pattern delim) { 
		return new FStringInput(source, delim);
	}
	
	
	
	
	public static String contents(File file) throws FileNotFoundException {
		FStringInput sin = tokens(file, "\\Z");
		String str = sin.iterator().next();
		try {
			sin.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	public static String contents(Readable readable) { 
		FStringInput sin = tokens(readable, "\\Z");
		String str = sin.iterator().next();
		try {
			sin.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	public static String contents(InputStream instream) { 
		FStringInput sin = tokens(instream, "\\Z");
		String str = sin.iterator().next();
		try {
			sin.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	public static String contents(ReadableByteChannel channel) { 
		FStringInput sin = tokens(channel, "\\Z");
		String str = sin.iterator().next();
		try {
			sin.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	public static String contents(String source) { 
		FStringInput sin = tokens(source, "\\Z");
		String str = sin.iterator().next();
		try {
			sin.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return str;
	}
	


	@Override
	public Iterator<String> iterator() {
		if (scannerMode){
			return scanner;
		} else {
			return reader.iterator();
		}
	}


	@Override
	public void close() throws IOException{
		if (scannerMode) { 
			scanner.close(); 
		} else { 
			reader.close();
		}
		
	}
	
	
	public static void main(String[] args) throws FileNotFoundException {
		
		
		
		long t1, t2;
		
		
		
		File file = new File("/home/nathaniel/Projects/Peakaboo Data/ScratchPlainText.txt");
		
		FStringInput f;
		String output = "";
		
		
		t1 = System.currentTimeMillis();
		for (int i = 0; i < 100; i++){ 
			
			f = FStringInput.lines(file);
			//f = new FStringInput(file, FStringInput.linebreakPattern);
			
			output = f.map(new FnMap<String, Integer>() {
	
				@Override
				public Integer f(String v) {
					return v.length();
				}
			}).take(100).show();
			
		}
		
		System.out.println(output);
		
		t2 = System.currentTimeMillis();
		System.out.println("Custom - Lines: " + (t2-t1));
		
		
		
		
		t1 = System.currentTimeMillis();
		for (int i = 0; i < 100; i++){ 
			
			//f = FStringInput.lines(file);
			f = new FStringInput(file, FStringInput.linebreakPattern);
			
			output = f.map(new FnMap<String, Integer>() {
	
				@Override
				public Integer f(String v) {
					return v.length();
				}
			}).take(100).show();
			
		}
		
		System.out.println(output);
		
		t2 = System.currentTimeMillis();
		System.out.println("Scanner - Lines: " + (t2-t1));
		
		
		
		
		t1 = System.currentTimeMillis();
		for (int i = 0; i < 100; i++){ 
			
			f = FStringInput.words(file);
			//f = new FStringInput(file, FStringInput.linebreakPattern);
			
			output = f.map(new FnMap<String, String>() {
				
				@Override
				public String f(String v) {
					return v;
				}
			}).take(100).show();
			
		}
		
		System.out.println(output);
		
		t2 = System.currentTimeMillis();
		System.out.println("Custom - Words: " + (t2-t1));
		
		
		
		
		t1 = System.currentTimeMillis();
		for (int i = 0; i < 100; i++){ 
			
			//f = FStringInput.lines(file);
			f = new FStringInput(file, FStringInput.whitespace);
			
			output = f.map(new FnMap<String, String>() {
	
				@Override
				public String f(String v) {
					return v;
				}
			}).take(100).show();
			
		}
		
		System.out.println(output);
		
		t2 = System.currentTimeMillis();
		System.out.println("Scanner - Words: " + (t2-t1));
		
	}

	
}

interface CustomReader extends Iterable<String>, Closeable
{
	
}


class LinesReader implements CustomReader
{

	private LineNumberReader reader;
	
	public LinesReader(Reader r) {
		reader = new LineNumberReader(r);
	}
	
	public LinesReader(String s) {
		this(new StringReader(s));
	}
	
	public LinesReader(ReadableByteChannel r) {
		this(new ChannelInputStream(r));
	}
	
	public LinesReader(File f) throws FileNotFoundException {
		this(new FileReader(f));
	}
	
	public LinesReader(InputStream i) {
		this(new InputStreamReader(i));
	}
	
	public LinesReader(Readable r) {
		this(new ReadableReader(r));
	}
	
	@Override
	public Iterator<String> iterator() {
		
		return new Iterator<String>(){

			private boolean done = false;
			private String line = null;
			
			//hasnext guaranteed to make the next line available in 'line'
			//if it isn't already there
			@Override
			public boolean hasNext() {
				
				if (line != null) return true;
				if (done) return false;
				
				//so line is null
				try {
					line = reader.readLine();
					if (line == null) {
						done = true;
						return false;
					}
				} catch (IOException e) {
					done = true;
					return false;
				}
				
				return true;
				
				
			}

			@Override
			public String next() {
				
				if (!hasNext()) throw new IndexOutOfBoundsException();
				
				String curLine = line;
				line = null;
				return curLine;
								
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
		
	}
	
	public void close() throws IOException{
		reader.close();
	}
	
}

class WordsReader implements CustomReader
{
	private LinesReader linesReader;
	private Iterator<String> linesIterator;

	public WordsReader(Reader r) {
		linesReader = new LinesReader(r);
		linesIterator = linesReader.iterator();
	}
	
	public WordsReader(String s) {
		this(new StringReader(s));
	}
	
	public WordsReader(ReadableByteChannel r) {
		this(new ChannelInputStream(r));
	}
	
	public WordsReader(File f) throws FileNotFoundException {
		this(new FileReader(f));
	}
	
	public WordsReader(InputStream i) {
		this(new InputStreamReader(i));
	}
	
	public WordsReader(Readable r) {
		this(new ReadableReader(r));
	}
	
	@Override
	public void close() throws IOException {
		linesReader.close();
	}

	@Override
	public Iterator<String> iterator() {
		
		return new Iterator<String>(){

			FList<String> words = new FList<String>();
			
			@Override
			public boolean hasNext() {
				
				if (words.size() > 0) return true;
				
				//words is empty
				while (words.size() == 0) {
					if (linesIterator.hasNext()){
						words = new FList<String>(linesIterator.next().split(FStringInput.whitespace));
					} else {
						return false;
					}
				}
				
				return true;
				
			}

			@Override
			public String next() {
				if (!hasNext()) throw new IndexOutOfBoundsException();
				return words.shift();
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}};
		
	}
	
}
