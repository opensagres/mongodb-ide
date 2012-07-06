package fr.opensagres.mongodb.ide.core.utils;

public class StringUtils {

	/**
	 * <p>
	 * Checks if a String is empty ("") or null.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.isEmpty(null) = true StringUtils.isEmpty(&quot;&quot;) = true
	 * StringUtils.isEmpty(&quot; &quot;) = false StringUtils.isEmpty(&quot;bob&quot;) = false
	 * StringUtils.isEmpty(&quot; bob &quot;) = false
	 * </pre>
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @return <code>true</code> if the String is empty or null
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}

	/**
	 * <p>
	 * Checks if a String is not empty ("") and not null.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.isNotEmpty(null) = false
	 * StringUtils.isNotEmpty(&quot;&quot;) = false
	 * StringUtils.isNotEmpty(&quot; &quot;) = true
	 * StringUtils.isNotEmpty(&quot;bob&quot;) = true
	 * StringUtils.isNotEmpty(&quot; bob &quot;) = true
	 * </pre>
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @return <code>true</code> if the String is not empty and not null
	 */
	public static boolean isNotEmpty(String str) {
		return str != null && str.length() > 0;
	}
}
