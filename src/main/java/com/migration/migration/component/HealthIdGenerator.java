package com.migration.migration.component;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.jboss.logging.Logger;

import com.migration.migration.entity.UserEntity;

public class HealthIdGenerator implements IdentifierGenerator {
	// Max Length of HealthID.
	private static final int HEALTH_ID_LENGTH = 14;

	private static String HYPHEN = "-";

	private static final String HEALTH_ID_PATTERN = "(\\d{2})(\\d{4})(\\d{4})(\\d{4})";
	private static final String HEALTH_ID_PATTERN_GROUPS = "$1-$2-$3-$4";

	private static Logger logger = Logger.getLogger(HealthIdGenerator.class);

	/**
	 * Health Id cannot start from 0 or 9. Must not be Palindrome and must be
	 * unique.
	 * 
	 * Hence taking bin as Random between 1-8 only and keep generating a number
	 * until its not palindrome and This number is not already in use.
	 */
	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {

		long bin = GeneralUtils.getRandomInteger(8, 1);
		String generatedId = generateHealthIdNumber(bin, HEALTH_ID_LENGTH);
		String generatedIdWithHyphon = formatHealthIdNumbner(generatedId);
		while (GeneralUtils.isPalindrome(generatedId) || !passesLuhnCheck(generatedId)
				|| existsUserWithHealthId(session, generatedIdWithHyphon.toString())) {
			logger.infof("Generated Id %s is either Palindrome or Currupt or already in use so Generating new.",
					generatedId);
			generatedId = generateHealthIdNumber(bin, HEALTH_ID_LENGTH);
			generatedIdWithHyphon = formatHealthIdNumbner(generatedId);
		}
		return generatedIdWithHyphon;
	}

	private String formatHealthIdNumbner(String healthIdNumber) {
		return healthIdNumber.replaceAll(HEALTH_ID_PATTERN, HEALTH_ID_PATTERN_GROUPS);
	}

	
	private boolean existsUserWithHealthId(SharedSessionContractImplementor session, String healthid) {
		return session.createCriteria(UserEntity.class).add(Restrictions.idEq(healthid)).setProjection(Projections.id())
				.uniqueResult() != null;
	}

	/**
	 * Generate Health Id Number using Luhn Algorithm
	 * 
	 * @param prefix Prefix to be used.
	 * @param length Total length of generated id.
	 * @return Id generated with Luhn's algorith.
	 */
	private static String generateHealthIdNumber(final Long prefix, final int length) {
		final StringBuffer num = new StringBuffer(prefix.toString());

		final int howManyMore = length - num.toString().length() - 1;

		for (int i = 0; i < howManyMore; i++) {
			num.append(Integer.valueOf(GeneralUtils.getRandomNumber(9)));
		}

		num.append(calculateCheckDigit(num.toString()));

		return num.toString();
	}

	/**
	 * Calculate checksum digit for given number using luhn algorith,.
	 * 
	 * @param str containing the number
	 * @return checksum digit.
	 */
	private static int calculateCheckDigit(final String str) {
		final int sum = calculateLuhnSum(str, false);
		final int checkDigit = calculateCheckDigit(sum);

		return checkDigit;
	}

	/**
	 * Calculate checksum digit for given number using luhn algorith,.
	 * 
	 * @param number
	 * @return checksum digit.
	 */

	private static int calculateCheckDigit(final int luhnSum) {
		final int checkDigit = (luhnSum * 9) % 10;
		return checkDigit;
	}

	/**
	 * Calculate Luhn algorithm based checksum number.
	 * 
	 * @param str           containing number
	 * @param hasCheckDigit true if the number passed already have a checksum.
	 * @return calculated checksum.
	 */
	private static int calculateLuhnSum(final String str, final boolean hasCheckDigit) {
		final int luhnNums[] = new int[str.length()];
		final int start = str.length() - (hasCheckDigit ? 2 : 1);
		int sum = 0;

		boolean doubleMe = true;

		for (int i = start; i >= 0; i--) {
			final int num = Integer.parseInt(str.substring(i, i + 1));

			if (doubleMe) {
				int x2 = num * 2;
				luhnNums[i] = x2 > 9 ? x2 - 9 : x2;
			} else {
				luhnNums[i] = num;
			}

			sum += luhnNums[i];
			doubleMe = !doubleMe;
		}

		return sum;
	}

	/**
	 * Check if given string has a number which passes Luhn's algorithm checksum
	 * check.
	 * 
	 * @param num String containing the trget number.
	 * @return true if its a valid Number with correct checksum.
	 */
	public boolean passesLuhnCheck(final String num) {
		if (num == null || num.isEmpty())
			throw new IllegalArgumentException("Number is null or empty");

		final int sum = calculateLuhnSum(num, true);
		final int checkDigit = calculateCheckDigit(sum);

		return (sum + checkDigit) % 10 == 0 && Integer.parseInt(num.substring(num.length() - 1)) == checkDigit;
	}

}