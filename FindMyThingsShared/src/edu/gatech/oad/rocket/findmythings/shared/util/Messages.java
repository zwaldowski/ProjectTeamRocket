package edu.gatech.oad.rocket.findmythings.shared.util;

public final class Messages {

	private Messages() {}

	public enum Status implements StringedEnum {
		OK("ok"),
		FAILED("nope"),
		UNAUTHORIZED("nopeNopeNope");

		private final String text;

		private Status(final String text) {
			this.text = text;
		}
		
		@Override
		public String getText() {
			return text;
		}

		@Override
		public String toString() {
			return text;
		}

	}

	public enum Login implements StringedEnum {
		NO_SUCH_USER("noSuchUser"),
		BAD_PASSWORD("badPassword"),
		ACCOUNT_LOCKED("accountLocked"),
		ACCT_DISABLE("accountDisabled"),
		MANY_ATTEMPT("tooManyAttempts"),
		INVALID_DATA("invalidData");

		private final String text;

		private Login(final String text) {
			this.text = text;
		}
		
		@Override
		public String getText() {
			return text;
		}

		@Override
		public String toString() {
			return text;
		}
	}

	public enum Register implements StringedEnum {
		ALREADY_USER("alreadyUser"),
		BAD_EMAIL_ADDRESS("badEmailAdd"),
		BAD_PASSWORD("badPassword"),
		PASSWORDS_MATCH("passwordMatch"),
		INVALID_PHONE("badPhoneNum"),
		INVALID_DATA("invalidData"),
		NO_SUCH_MEMBER("superForgot");

		private final String text;

		private Register(final String text) {
			this.text = text;
		}
		
		@Override
		public String getText() {
			return text;
		}

		@Override
		public String toString() {
			return text;
		}
	}

	public enum Activate implements StringedEnum {
		CODE_EXPIRED("expiredCode"),
		NO_SUCH_USER("noSuchUser"),
		BAD_PASSWORD("badPassword"),
		PASSWORDS_MATCH("passwordMatch"),
		INVALID_DATA("invalidData");

		private final String text;

		private Activate(final String text) {
			this.text = text;
		}
		
		@Override
		public String getText() {
			return text;
		}

		@Override
		public String toString() {
			return text;
		}
	}

}
