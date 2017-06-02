package q2p.q2pexperiments;

import java.util.Calendar;
import java.util.Random;
import java.util.TimeZone;

public final class Assist {
	static final String validLetters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	static final String validDigits = validLetters+"0123456789";

	private final static Random RANDOM = new Random();
	public final static int random(final int bound) {
		return RANDOM.nextInt(bound);
	}
	
	public static final int worldTimeFromRealTime() {
		return (int)(currentGMTmilisec()%(24*60*60*1000)/(60*60));
	}	
	private static final TimeZone gmtTimeZone = TimeZone.getTimeZone("GMT");
	public static final long currentGMTmilisec() {
		return currentGMTcalendar().getTimeInMillis();
	}
	public static final Calendar currentGMTcalendar() {
		return Calendar.getInstance(gmtTimeZone);
	}
	public static final boolean isValid(final String string, final boolean digitsAllowed) {
		final String charset = digitsAllowed?validDigits:validLetters;
		
		for(int i = string.length()-1; i != -1; i--)
			if(!charset.contains(""+string.charAt(i)))
				return false;
		
		return true;
	}
	public static final boolean isArgument(final int id, final String argument, final String[] args){
		return id < args.length && args[id].equals(argument);
	}
	public static final String stringHash(int length) {
		final StringBuilder builder = new StringBuilder(length);
		for(;length != 0; length--)
			builder.append(validDigits.charAt(RANDOM.nextInt(validDigits.length())));
		return builder.toString();
	}
	public static final void abort(final String reason) {
		System.out.println(reason);
		System.exit(1);
	}

	public static final boolean outOfRange(final int min, final int value, final int max) {
		return value < min || value > max;
	}
	public static final int limit(final int min, final int value, final int max) {
		if(value < min)
			return min;
		
		if(value > max)
			return max;
		
		return value;
	}
}