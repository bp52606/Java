package hr.fer.zemris.java.hw06.shell;

/**
 * Enumeration used to describe state of a shell. 
 * If an error has occurred, shell should be terminated.
 * If all operations went well, shell should continue with it's work
 * 
 * @author 38591
 *
 */

public enum ShellStatus {
	CONTINUE, TERMINATE;
}
