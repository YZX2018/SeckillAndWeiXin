package exception;

/**
 * �ظ���ɱ�쳣(����ʱ�쳣)
 * @author lenovo
 *
 */
public class RepeatKillException extends SeckillException{

	public RepeatKillException(String message, Throwable cause) {
		super(message, cause);
	}

	public RepeatKillException(String message) {
		super(message);
	}
	
}
