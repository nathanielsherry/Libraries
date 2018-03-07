package plural.streams;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import eventful.Eventful;
import plural.streams.swing.StreamExecutorPanel;
import plural.streams.swing.StreamExecutorView;
import swidget.Swidget;
import swidget.stratus.StratusLookAndFeel;

public class StreamExecutor<T> extends Eventful implements Predicate<Object>{

	public enum State {
		RUNNING,
		ABORTED,
		COMPLETED,
	}
	
	
	private Thread thread;
	private StreamExecutor<?> next;
	
	private int count = 0;
	private int size = -1;
	private int interval = 100;
	Optional<T> result = Optional.empty();
	
	private State state = State.RUNNING;
	
	public StreamExecutor() {
		this(100);
	}
	
	public StreamExecutor(int notificationInterval) {
		this.interval = notificationInterval;
	}
	
	@Override
	public boolean test(Object t) {
		count++;
		if (count % interval == 0) {
			updateListeners();
		}
		return state == State.RUNNING;
	}
	

	public <S> Stream<S> observe(Stream<S> stream) {
		return stream.parallel().filter(this);
	}
	
	
	public void abort() {
		if (state == State.RUNNING) {
			state = State.ABORTED;
			updateListeners();
			removeAllListeners();
		}
		
	}
	
	public void complete() {
		
		if (state == State.RUNNING) {
			state = State.COMPLETED;
			updateListeners();
			removeAllListeners();
		}
	}
	
	
	public State getState() {
		return state;
	}
	
	public int getCount() {
		return count;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public Optional<T> getResult() {
		return result;
	}

	public void setResult(T result) {
		if (state == State.RUNNING) {
			this.result = Optional.ofNullable(result);
			complete();
		}

	}

	
	public StreamExecutor<?> then(StreamExecutor<?> next) {
		setNext(next);
		return next;
	}
	
	public void setNext(StreamExecutor<?> next) {
		this.next = next;
	}
	
	public StreamExecutor<?> getNext() {
		return this.next;
	}
	
	
	/**
	 * Sets the execution task to the given {@link Supplier} function. The stream used 
	 * by this function will have to be created manually and wrapped with the 
	 * {@link StreamExecutor#observe(Stream)} function. The size will also have to be
	 * set manually by calling {@link StreamExecutor#setSize(int)}
	 * @param task
	 */
	public void setTask(Supplier<T> task) {
		thread = new Thread(() -> {
			setResult(task.get());
			
			//If another StreamExecutor is specified to run after this is done, kick it off now
			if (this.next != null && state == State.COMPLETED) {
				next.start();
			}
		});		
	}
	
	/**
	 * Sets the execution task to the given {@link Function}. The stream to be used
	 * by this function will be supplied as the function's argument, constructed from 
	 * the {@link Iterable} source. 
	 * @param source The {@link Iterable} to stream over
	 * @param task The task to perform over the iterable's stream
	 */
	public <S> void setTask(Iterable<S> source, Function<Stream<S>, T> task) {

		//If size hasn't already been manually set, figure it out now
		if (size == -1) {
			if (source instanceof Collection<?>) {
				setSize(((Collection<?>) source).size());
			} else {
				int count = 0;
				for (S s : source) {
					count++;
				}
				setSize(count);
			}
		}
		
		thread = new Thread(() -> {
			setResult(
				task.apply(
					observe(
						StreamSupport.stream(source.spliterator(), true)
					)
				)
			);
			
			//If another StreamExecutor is specified to run after this is done, kick it off now
			if (this.next != null && state == State.COMPLETED) {
				next.start();
			}
		});		
	}

	/**
	 * Convenience method for {@link StreamExecutor#setTask(Iterable, Function)} to accept arrays as input
	 */
	public <S> void setTask(S[] source, Function<Stream<S>, T> task) {
		setTask(Arrays.asList(source), task);
	}
	
	public void start() {
		thread.start();
	}
	

	public static void main(String[] args) throws InterruptedException {
		
		Swidget.initialize(() -> {
			int size = 10000;
			
			List<Integer> ints = new ArrayList<>();
			for (int i = 0; i < size; i++) {
				ints.add(i);
			}
			System.out.println("Starting...");
			
			
			StreamExecutor<List<Integer>> e1 = new StreamExecutor<>();
			e1.setSize(size);
			
			e1.addListener(() -> {
				if (e1.getState() == State.RUNNING) {
					System.out.println("E1 Processed: " + e1.getCount());
				}
				
				if (e1.getState() == State.COMPLETED) {
					System.out.println("E1 Done!");
				}
			});
			
			e1.setTask(() -> {
				return e1.observe(ints.stream()).map(v -> {
					float f = v;
					for (int i = 0; i < 100000; i++) {
						f = (int)Math.pow(f, 1.0001);
					}
					return (int)f;
				}).collect(Collectors.toList());
			});
			
	
			
			
			StreamExecutor<List<Integer>> e2 = new StreamExecutor<>();
			e2.setSize(size);
			
			e2.addListener(() -> {
				if (e2.getState() == State.RUNNING) {
					System.out.println("E2 Processed: " + e2.getCount());
				}
				
				if (e2.getState() == State.COMPLETED) {
					System.out.println("E2 Done!");
				}
			});
			
			e2.setTask(() -> {
				return e2.observe(e1.getResult().get().stream()).map(v -> {
					float f = v;
					for (int i = 0; i < 100000; i++) {
						f = (int)Math.pow(f, 1.0001);
					}
					return (int)f;
				}).collect(Collectors.toList());
			});
			
			e1.then(e2);
			
			
			StreamExecutorView v1 = new StreamExecutorView(e1, "First Task");
			StreamExecutorView v2 = new StreamExecutorView(e2, "Second Task");
			
			JFrame frame = new JFrame();
			StreamExecutorPanel panel = new StreamExecutorPanel("Two Tasks", v1, v2);
			frame.getContentPane().setLayout(new BorderLayout());
			frame.getContentPane().add(panel, BorderLayout.CENTER);
	
			e2.addListener(() -> {
				if (e2.getState() != State.RUNNING) {
					frame.setVisible(false);
				}
			});
			
		
			frame.pack();
			frame.setVisible(true);
			
			e1.start();
		});

		

		
		
		Thread.currentThread().sleep(5000);
		
	}
	
}
