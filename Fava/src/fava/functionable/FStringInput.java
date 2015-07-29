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
import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.regex.Pattern;

import sun.nio.ch.ChannelInputStream;
import fava.Functions;


public class FStringInput implements Iterator<String>, Closeable{

	static String linebreak = "\r\n|[\n\r\u2028\u2029\u0085]";
	static String whitespace = "\\s+";
	
	static Pattern linebreakPattern = Pattern.compile(linebreak);
	static Pattern whitespacePattern = Pattern.compile(whitespace);
	
	private Iterator<String> backingIterator;
	private boolean isClosed = false; 
	

	private FStringInput() {}

	private FStringInput(File file, Pattern delim) throws FileNotFoundException {
		backingIterator = new Scanner(file).useDelimiter(delim);
	}
	
	private FStringInput(Readable readable, Pattern delim) {
		backingIterator = new Scanner(readable).useDelimiter(delim);
	}
	
	private FStringInput(InputStream instream, Pattern delim) {
		backingIterator = new Scanner(instream).useDelimiter(delim);
	}
	
	private FStringInput(ReadableByteChannel channel, Pattern delim) {
		backingIterator = new Scanner(channel).useDelimiter(delim);
	}
	
	private FStringInput(String source, Pattern delim) {
		backingIterator = new Scanner(source).useDelimiter(delim);
	}
	
	
	private FStringInput(File file, String delim) throws FileNotFoundException {
		backingIterator = new Scanner(file).useDelimiter(delim);
	}
	
	private FStringInput(Readable readable, String delim) {
		backingIterator = new Scanner(readable).useDelimiter(delim);
	}
	
	private FStringInput(InputStream instream, String delim) {
		backingIterator = new Scanner(instream).useDelimiter(delim);
	}
	
	private FStringInput(ReadableByteChannel channel, String delim) {
		backingIterator = new Scanner(channel).useDelimiter(delim);
	}
	
	private FStringInput(String source, String delim) {
		backingIterator = new Scanner(source).useDelimiter(delim);
	}
	
	

	
	public static FStringInput lines(File file) throws FileNotFoundException {
		FStringInput f =  new FStringInput();
		f.backingIterator = new LinesReader(file).iterator();
		return f;
	}
	
	public static FStringInput lines(Readable readable) {
		FStringInput f =  new FStringInput();
		f.backingIterator = new LinesReader(readable).iterator();
		return f;
	}
	
	public static FStringInput lines(InputStream instream) {
		FStringInput f =  new FStringInput();
		f.backingIterator = new LinesReader(instream).iterator();
		return f;
	}
	
	public static FStringInput lines(ReadableByteChannel channel) {
		FStringInput f =  new FStringInput();
		f.backingIterator = new LinesReader(channel).iterator();
		return f;
	}
	
	public static FStringInput lines(String source) {
		FStringInput f =  new FStringInput();
		f.backingIterator = new LinesReader(source).iterator();
		return f;
	}
	
	
	
	
	public static FStringInput words(File file) throws FileNotFoundException {
		FStringInput f = new FStringInput();
		f.backingIterator = new WordsReader(file).iterator();
		return f;
	}
	
	public static FStringInput words(Readable readable) {
		FStringInput f = new FStringInput();
		f.backingIterator = new WordsReader(readable).iterator();
		return f;
	}
	
	public static FStringInput words(InputStream instream) {
		FStringInput f = new FStringInput();
		f.backingIterator = new WordsReader(instream).iterator();
		return f;
	}
	
	public static FStringInput words(ReadableByteChannel channel) {
		FStringInput f = new FStringInput();
		f.backingIterator = new WordsReader(channel).iterator();
		return f;
	}
	
	public static FStringInput words(String source) {
		FStringInput f = new FStringInput();
		f.backingIterator = new WordsReader(source).iterator();
		return f;
	}


	
	
	
	
	
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
		return tokens(file, "\\Z").toSink().head();
	}
	
	public static String contents(Readable readable) { 
		return tokens(readable, "\\Z").toSink().head();
	}
	
	public static String contents(InputStream instream) { 
		return tokens(instream, "\\Z").toSink().head();
	}
	
	public static String contents(ReadableByteChannel channel) { 
		return tokens(channel, "\\Z").toSink().head();
	}
	
	public static String contents(String source) { 
		return tokens(source, "\\Z").toSink().head();
	}
	
	
	/**
	 * Dumps the contents to an FList, and calls close on the backing interface
	 */
	public FList<String> toSink()
	{
		FList<String> list = new FList<>(this);
		try {
			close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	/**
	 * Dumps the contents to an FList, and calls close on the backing interface
	 */
	public FList<String> dump()
	{
		return toSink();
	}

	
	@Override
	public void close() throws IOException{
		
		isClosed = true;
		
		if (backingIterator instanceof Scanner)
		{
			((Scanner)backingIterator).close();
			return;
		}
		if (backingIterator instanceof CustomReader)
		{
			((CustomReader)backingIterator).close();
		}
	}
	

	public static void test(boolean verbose, int times, String filename) throws FileNotFoundException
	{

		long t1, t2;
		
		File file = new File(filename);
		
		FList<String> o1 = null;
		FList<String> o2 = null;
		
		
		
		
		
		//LINES
		
		t1 = System.currentTimeMillis();
		for (int i = 0; i < 1; i++){ 
			
			o1 = FStringInput.lines(file).dump();
			
		}
		
				
		t2 = System.currentTimeMillis();
		if (verbose) System.out.println("Custom - Lines: " + (t2-t1) + "ms");
		
		
		
		
		t1 = System.currentTimeMillis();
		for (int i = 0; i < 1; i++){ 
			
			o2 = new FStringInput(file, FStringInput.linebreakPattern).dump();
					
		}
		
		
		t2 = System.currentTimeMillis();
		if (verbose) System.out.println("Scanner - Lines: " + (t2-t1) + "ms");
		
		if (verbose) System.out.println(o1.zipEquiv(o2).fold(Functions.and()));
		
		
		
		
		
		
		
		//WORDS
		
		t1 = System.currentTimeMillis();
		for (int i = 0; i < 1; i++){ 
			
			o1 = FStringInput.words(file).dump();
			
		}
		
		
		t2 = System.currentTimeMillis();
		if (verbose) System.out.println("Custom - Words: " + (t2-t1) + "ms");
		
		
		System.out.println(o1.size());
		
		
		
		
		t1 = System.currentTimeMillis();
		for (int i = 0; i < 1; i++){ 
			
			o2 = new FStringInput(file, FStringInput.whitespacePattern).dump();
			
		}
		
		
		
		
		t2 = System.currentTimeMillis();
		if (verbose) System.out.println("Scanner - Words: " + (t2-t1) + "ms");

		
		System.out.println(o2.size());
		
		if (verbose) System.out.println(o1.zipEquiv(o2).fold(Functions.and()));
		
		
	}
	
	
	public static void main(String[] args) throws IOException {
		
		
		//test(false, 1, "/home/nathaniel/Downloads/LocalTorrents/hugo/1953 - Alfred Bester - The Demolished Man (HTML).htm");
		test(true, 1, "/home/nathaniel/Downloads/LocalTorrents/hugo/1953 - Alfred Bester - The Demolished Man (HTML).htm");
		

		test(true, 1, "/home/nathaniel/Projects/Peakaboo Data/ScratchPlainText.txt");
		
	}

	@Override
	public boolean hasNext() {
		return backingIterator.hasNext();
	}

	@Override
	public String next() {
		return backingIterator.next();
	}

	@Override
	public void remove() {
		backingIterator.remove();
	}
	
	
	


	public void each(Consumer<String> f)
	{
		if (isClosed) throw new ClosedInputException();
		
		while (this.hasNext())
		{
			f.accept(this.next());
		}
		
		try {
			this.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public <S2> FList<S2> map(Function<String, S2> f)
	{
		
		if (isClosed) throw new ClosedInputException();
		FList<S2> target = new FList<>();
		
		while (this.hasNext())
		{
			target.add(   f.apply(this.next())   );
		}
		
		try {
			this.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return target;
	}
	
	
	public FList<String> filter(Function<String, Boolean> f)
	{
		if (isClosed) throw new ClosedInputException();
		FList<String> target = new FList<>();
		
		while (this.hasNext())
		{
			String token = this.next();
			if (   f.apply(token)   ) target.add(   token   );
		}
		
		try {
			this.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return target;
	}
	
	
	public String fold(BiFunction<String, String, String> f)
	{
		
		
		if (isClosed) throw new ClosedInputException();
		String acc = null;
		boolean first = true;
		
		while (this.hasNext())
		{
			String s = this.next();
			if (first) { acc = s; }
			else { acc = f.apply(s, acc); }
		}
		
		try {
			this.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return acc;

	}
	
	
	public <S2> S2 fold(S2 base, BiFunction<String, S2, S2> f)
	{
		if (isClosed) throw new ClosedInputException();
		S2 acc = base;
				
		while (this.hasNext())
		{
			String s = this.next();
			acc = f.apply(s, acc);
		}
		
		
		return acc;
	}
	
	
	
	
	public FList<String> take(int number)
	{
		
		if (isClosed) throw new ClosedInputException();
		FList<String> target = new FList<>();
		int count = 0;
		
		while (this.hasNext())
		{
			if (count == number) break;
			String token = this.next();
			target.add(   token   );
			count++;
		}
		
		
		//if we couldn't take enough, we close it
		if (count < number) try {
			this.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return target;
		
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
				if (done) {
					try {
						close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					return false;
				}
				
				//so line is null
				try {
					line = reader.readLine();
					if (line == null) {
						done = true;
						try {
							close();
						} catch (IOException e) {
							e.printStackTrace();
						}
						return false;
					}
				} catch (IOException e) {
					done = true;
					try {
						close();
					} catch (IOException e2) {
						e.printStackTrace();
					}
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

			FList<String> words = new FList<>();
			int wordIndex = 0;
			
			@Override
			public boolean hasNext() {
				
				if (wordIndex < words.size()) return true;
				
				//words is empty
				//while word is empty, or only holding a blank line
				while (wordIndex >= words.size() || words.size() == 0 || (words.size() == 1 && words.get(0).equals(""))) {
					if (linesIterator.hasNext()){
						words = FList.wrap(Arrays.asList(linesIterator.next().trim().split(FStringInput.whitespace)));
						wordIndex = 0;
					} else {
						try {
							close();
						} catch (IOException e) {
							e.printStackTrace();
						}
						return false;
					}
										
				}
				
				return true;
				
			}

			@Override
			public String next() {
				if (!hasNext()) throw new IndexOutOfBoundsException();
				return words.get(wordIndex++);
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}};
		
	}
	
}

class ClosedInputException extends RuntimeException
{
	
}
