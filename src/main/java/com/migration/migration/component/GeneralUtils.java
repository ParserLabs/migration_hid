package com.migration.migration.component;

import java.math.RoundingMode;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.util.StringUtils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@UtilityClass
@Slf4j
public class GeneralUtils {
	private static int offset;
	public static final String LATITUDE_PATTERN = "^(\\+|-)?(?:90(?:(?:\\.0{1,6})?)|(?:[0-9]|[1-8][0-9])(?:(?:\\.[0-9]{1,6})?))$";
	public static final String LONGITUDE_PATTERN = "^(\\+|-)?(?:180(?:(?:\\.0{1,6})?)|(?:[0-9]|[1-9][0-9]|1[0-7][0-9])(?:(?:\\.[0-9]{1,6})?))$";
	public static DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	public static final Pattern MOBILE_REGEX = Pattern.compile("[6-9][0-9]{9}");
	private static final Pattern EMAIL_PATTERN = Pattern
			.compile("[a-zA-Z0-9!#$%&'*+/=?^_`{|}~.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*");
	public final Pattern SWASTHYA_ID_PATTERN = Pattern.compile("(\\d{4})(\\d{4})(\\d{4})(\\d{4})");
	private static String healthIdSuffix = "@ndhm";

	private static final String SECURE_RANDOM_ALGO = "NativePRNG";
	private static SecureRandom secureRandom;
	public static String UNIQUE_REQUESTER_ID = "defaultUniqueId";

	static {
		try {
			secureRandom = SecureRandom.getInstance(SECURE_RANDOM_ALGO);
		} catch (NoSuchAlgorithmException e) {
			log.error("No algo ({}) found to create secure random instance", SECURE_RANDOM_ALGO);
		}
	}

	/**
	 * Format mobile number as per Indian numbers.
	 * 
	 * @param mobileNumber
	 * @return formatted mobile number
	 */
	public static String formatMobileNumber(String mobileNumber, String countryCode) {
		/*
		 * PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance(); try {
		 * Phonenumber.PhoneNumber phone = phoneUtil.parse(mobileNumber, countryCode);
		 * mobileNumber = phoneUtil.format(phone,
		 * PhoneNumberUtil.PhoneNumberFormat.NATIONAL); } catch (NumberParseException e)
		 * { logger.error("Invalid phone number " + mobileNumber, e); }
		 */
		return mobileNumber;
	}

	public static String sanetizeHealthIdStr(String healthIdStr) {
		if (!healthIdStr.contains(healthIdSuffix)) {
			healthIdStr = healthIdStr + healthIdSuffix;
		}
		return healthIdStr.toLowerCase();
	}

	public static String formatDate(Date date) {
		return DATE_FORMAT.format(date);
	}

	public static String formatTime(Long timeInMillis) {
		return DATE_FORMAT.format(new Date(((long) timeInMillis) * 1000));
	}

	public static String currentTimeAsString() {
		return formatTime(getCurrentTime());
	}

	public static Long getCurrentTime() {
		return System.currentTimeMillis() + (offset * 1000);
	}

	public static LocalDate getCurrentDate() {
		return LocalDate.now();
	}

	public static LocalDate createDate(int day, int month, int year) {
		return LocalDate.of(year, month, day);
	}

	public static LocalDate createDate(String day, String month, String year) {
		return createDate(Integer.parseInt(day), Integer.parseInt(month), Integer.parseInt(year));
	}

	/**
	 * Get DOB or YOB string based on data passed. YOB is always required.
	 * 
	 * @param day   day of birth (optional)
	 * @param month month of birth (optional)
	 * @param year
	 * @return String in format of "DD/MM/YYYY" or "YYYY"
	 */
	public static String getDobYobAsString(String day, String month, String year) {
		StringBuilder result = new StringBuilder();
		if (!isBlank(day) && !isBlank(month)) {
			result.append(day + "/" + month + "/");
		}
		result.append(year);
		return result.toString();
	}

	/**
	 * Format mobile number as per Indian numbers.
	 * 
	 * @param mobileNumber
	 * @return formatted mobile number
	 */
	public static boolean isValidMobileNumber(String mobileNumber, String countryCode) {
		/*
		 * PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
		 * Phonenumber.PhoneNumber phone = null; try { phone =
		 * phoneUtil.parse(mobileNumber, countryCode); } catch (NumberParseException e)
		 * { logger.error("Invalid phone number " + mobileNumber, e); return false; }
		 * return phoneUtil.isValidNumber(phone) && PhoneNumberType.MOBILE ==
		 * phoneUtil.getNumberType(phone);
		 */
		return MOBILE_REGEX.matcher(mobileNumber).matches();
	}

	public static boolean isValidLocation(String latitude, String longitude) {
		DecimalFormat df = new DecimalFormat("#.######");
		df.setRoundingMode(RoundingMode.UP);
		boolean isValidLat = df.format(Double.parseDouble(latitude)).matches(LATITUDE_PATTERN);
		boolean isValidLong = df.format(Double.parseDouble(longitude)).matches(LONGITUDE_PATTERN);

		return (isValidLat && isValidLong);
	}

	/**
	 * Check if string is empty (null or lenght is 0)
	 * 
	 * @param s to check
	 * @return true if string is empty
	 */
	public static boolean isEmpty(String s) {
		return s == null || s.length() == 0;
	}

	/**
	 * Check if string is blank (null or lenght is 0 or contains only white
	 * characters)
	 * 
	 * @param s to check
	 * @return true if string is blank
	 */
	public static boolean isBlank(String s) {
		return s == null || s.trim().length() == 0;
	}

	public static boolean isEmailValid(String email) {
		return EMAIL_PATTERN.matcher(email).matches();
	}

	public static String getRandomCode(long nrOfDigits) {
		if (nrOfDigits < 1) {
			throw new RuntimeException("Number of digits must be bigger than 0");
		}

		return RandomStringUtils.randomNumeric((int) nrOfDigits);
	}

	/**
	 * Get a random integer between a range (inclusive).
	 * 
	 * @param maximum
	 * @param minimum
	 * @return
	 */
	public static int getRandomInteger(int maximum, int minimum) {
		return secureRandom.nextInt(maximum - minimum) + minimum;
	}

	public static long getRandomLong(long maximum, long minimum) {
		return secureRandom.nextInt((int) (maximum - minimum)) + minimum;
	}

	public static double getRandomDouble() {
		return secureRandom.nextDouble();
	}
	
	public static int getRandomNumber(int bound) {
		return secureRandom.nextInt(bound);
	}
	/**
	 * Method to check if given Long variable is Pallindrome or not.
	 * 
	 * @return true if number is a palindrome.
	 */
	public static boolean isPalindrome(long numberToCheck) {
		long reversedNumber = 0, remainder, originalNumber;
		originalNumber = numberToCheck;

		// reversed number is stored in variable
		while (numberToCheck != 0) {
			remainder = numberToCheck % 10;
			reversedNumber = reversedNumber * 10 + remainder;
			numberToCheck /= 10;
		}

		return originalNumber == reversedNumber;
	}

	/**
	 * Method to check if given String is palindrome
	 * 
	 * @param str
	 * @return true if String is a Palindrome.
	 */
	static boolean isPalindrome(String str) {
		// Pointers pointing to the beginning and the end of the string
		int i = 0, j = str.length() - 1;

		// While there are characters to compare
		while (i < j) {
			// If there is a mismatch
			if (str.charAt(i) != str.charAt(j))
				return false;

			// Increment first pointer and
			// decrement the other
			i++;
			j--;
		}
		// Given string is a palindrome
		return true;
	}

	public static String getClientIP(HttpServletRequest request) {
		final String xfHeader = request.getHeader("X-Forwarded-For");
		if (xfHeader != null) {
			return xfHeader.split(",")[0];
		}
		return request.getRemoteAddr();
	}

	public static String stringTrimmer(String str) {
		return StringUtils.isEmpty(str) ? str : str.trim();
	}
	
	public static List<String> stringToList(String str) {
		return Arrays.asList(str.split(","));
	}
	
	public static String listToString(List<String> list) {
		return list.stream().collect(Collectors.joining(","));
	}
	
	public static String listToString(Set<String> set) {
		return set.stream().collect(Collectors.joining(","));
	}
}
