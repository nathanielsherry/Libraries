package fava.datatypes;


import java.util.List;



/**
 * The StateMachine class models a simple state machine which alters its state
 * based on provided input.
 * 
 * @author Nathaniel Sherry
 *
 * @param <STATE> The datatype representing the state of the StateMachine
 * @param <INPUT> the type of input that this StateMachine operates on
 */

public abstract class StateMachine<STATE, INPUT> {

	private STATE current;
	private STATE original;

	/**
	 * Constructs a new state machine with the given state
	 * @param initial the initial state
	 */
	public StateMachine(STATE initial)
	{
		resetState(initial);
	}
	
	
	
	/**
	 * This method should be implemented by implementations of StateMachine. This is
	 * where specific state machines define their behaviour on input.
	 * @param input the input to the state machine
	 * @param state the current state
	 * @return the new state of the StateMachine
	 */
	protected abstract STATE processInput(INPUT input, STATE state);
	
	
	
	/**
	 * Gets the current state value
	 * @return current state of the StateMachine
	 */
	public STATE getState()
	{
		return current;
	}
	
	
	/**
	 * Sets the current state of the StateMachine
	 * @param s the new state of the StateMachine
	 */
	public void setState(STATE s)
	{
		current = s;
	}
	
	/**
	 * Resets the state of the StateMachine by holding a reference to the 
	 * state that was given to the StateMachine when it was initialised, or when
	 * it was last reset by calling {@link StateMachine#resetState(STATE s)}
	 * <br/><br/>
	 * If the state data structure being used by an implementation is itself stateful,
	 * and is modified by new input, rather than being replaced the resetState method should 
	 * be overridden to set the state to a fresh instance of the state data structure 
	 * by creating the new instance, initialising it, and calling
	 * {@link StateMachine#resetState(STATE s)} 
	 */
	public void resetState()
	{
		current = original;
	}
	
	/**
	 * This method is used to set the state of the StateMachine, but also to set
	 * the initial state of the machine. If {@link StateMachine#resetState()} is
	 * called after a call to this method, the StateMachine's state will reset
	 * to the value provided to this method, rather than a previous value provided
	 * at initialisation. 
	 * <br/><br/>
	 * Subclasses of StateMachine overridding the {@link StateMachine#resetState()}
	 * method should call this method once they have initialsed the desired state.
	 * 
	 * @param s the new value and default/initial value of this StateMachine
	 */
	protected void resetState(STATE s)
	{
		current = s;
		original = s;
	}
	
	
	
	/**
	 * This method transitions the StateMachine from the current state to a new
	 * state based on the input i
	 * @param i the input to the StateMachine
	 * @return the new state of the StateMachine
	 */
	public STATE transition(INPUT i)
	{
		current = processInput(i, current);
		return current;
	}
	
	/**
	 * This method accepts a list of inputs to the StateMachine, and transitions the
	 * StateMachine based on the input one item at a time
	 * @param is the list of input for the StateMachine
	 * @return the final state after transitioning for all input
	 */
	public STATE transition(List<INPUT> is)
	{
		for (INPUT i : is)
		{
			transition(i);
		}
		return current;
	}

	/**
	 * This method accepts an array of inputs to the StateMachine, and transitions the
	 * StateMachine based on the input one item at a time
	 * @param is the array of input for the StateMachine
	 * @return the final state after transitioning for all input
	 */
	public STATE transition(INPUT[] is)
	{
		for (INPUT i : is)
		{
			transition(i);
		}
		return current;
	}
	
	
	
	
	/**
	 * This method returns what the state would be if the 
	 * StateMachine were to be transitioned on this input
	 * <br/><br/>
	 * Note: if a StateMachine updates a data structure rather than returning
	 * a new state value, a call to speculate will have the same effect as
	 * a call to {@link StateMachine#transition(INPUT i))}
	 * @param i the input to the StateMachine
	 * @return the state of the StateMachine, were it to be given this input
	 */
	public STATE speculate(INPUT i)
	{
		return speculate(i, current);
	}
	
	/**
	 * This method returns what the state would be if the 
	 * StateMachine were to be transitioned on this list of 
	 * inputs sequentially from a given starting input
	 * <br/><br/>
	 * Note: if a StateMachine updates a data structure rather than returning
	 * a new state value, a call to speculate will alter the provided starting state.
	 * @param i the input to the StateMachine
	 * @param starting the prospective initial state of the StateMachine
	 * @return the state of the StateMachine, were it to be given this input
	 */
	public STATE speculate(INPUT i, STATE starting)
	{
		return processInput(i, starting);
	}
	
	/**
	 * This method returns what the state would be if the 
	 * StateMachine were to be transitioned on this list of 
	 * inputs sequentially
	 * <br/><br/>
	 * Note: if a StateMachine updates a data structure rather than returning
	 * a new state value, a call to speculate will have the same effect as
	 * a call to {@link StateMachine#transition(INPUT i)}
	 * @param is the inputs to the StateMachine
	 * @return the state of the StateMachine, were it to be given this input
	 */
	public STATE speculate(List<INPUT> is)
	{
		
		STATE temp = current;
		
		for (INPUT i : is)
		{
			temp = speculate(i, temp);
		}
		
		return temp;
	}
	
	
	/**
	 * This method returns what the state would be if the 
	 * StateMachine were to be transitioned on this list of 
	 * inputs sequentially
	 * <br/><br/>
	 * Note: if a StateMachine updates a data structure rather than returning
	 * a new state value, a call to speculate will have the same effect as
	 * a call to {@link StateMachine#transition(INPUT i)}
	 * @param is the inputs to the StateMachine
	 * @return the state of the StateMachine, were it to be given this input
	 */
	public STATE speculate(INPUT[] is)
	{
		
		STATE temp = current;
		
		for (INPUT i : is)
		{
			temp = speculate(i, temp);
		}
		
		return temp;
	}
	
	
}
