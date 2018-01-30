package exception;


/**
 * 秒杀相关业务异常
 * @author lenovo
 *
 */
public class SeckillException extends RuntimeException{

	public SeckillException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public SeckillException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

}
