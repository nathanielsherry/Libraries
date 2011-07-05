package fava.functionable;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.ReadableByteChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.regex.Pattern;

import javax.xml.ws.WebServiceException;


public class FStringInput extends Functionable<String> implements Closeable{

	private static String linebreak = "\r\n|[\n\r\u2028\u2029\u0085]";
	private static String whitespace = "\\s+";
	
	private static Pattern linebreakPattern = Pattern.compile(linebreak);
	private static Pattern whitespacePattern = Pattern.compile(whitespace);
	
	private Scanner scanner;
	
	
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
		return new FStringInput(file, linebreakPattern);
	}
	
	public static FStringInput lines(Readable readable) {
		return new FStringInput(readable, linebreakPattern);
	}
	
	public static FStringInput lines(InputStream instream) {
		return new FStringInput(instream, linebreakPattern);
	}
	
	public static FStringInput lines(ReadableByteChannel channel) {
		return new FStringInput(channel, linebreakPattern);
	}
	
	public static FStringInput lines(String source) {
		return new FStringInput(source, linebreakPattern);
	}
	

	
	
	
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
		sin.close();
		return str;
	}
	
	public static String contents(Readable readable) { 
		FStringInput sin = tokens(readable, "\\Z");
		String str = sin.iterator().next();
		sin.close();
		return str;
	}
	
	public static String contents(InputStream instream) { 
		FStringInput sin = tokens(instream, "\\Z");
		String str = sin.iterator().next();
		sin.close();
		return str;
	}
	
	public static String contents(ReadableByteChannel channel) { 
		FStringInput sin = tokens(channel, "\\Z");
		String str = sin.iterator().next();
		sin.close();
		return str;
	}
	
	public static String contents(String source) { 
		FStringInput sin = tokens(source, "\\Z");
		String str = sin.iterator().next();
		sin.close();
		return str;
	}
	


	@Override
	public Iterator<String> iterator() {
		return scanner;
	}


	@Override
	public void close() throws WebServiceException {
		scanner.close();
	}

	
}
